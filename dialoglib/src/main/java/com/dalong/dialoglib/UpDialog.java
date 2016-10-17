package com.dalong.dialoglib;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.View;

/**
 * 从上往下弹出的dialog
 * Created by zhouweilong on 2016/10/13.
 */

public class UpDialog extends BaseDialog {
    //tag
    private  String TAG = "comment_dialog";

    private static final int DISSMISS_MSG_CODE=100;
    //layout key
    private static final String KEY_LAYOUT_RES = "comment_layout_res";
    //遮罩透明度 key
    private static final String KEY_DIM = "comment_dim";
    //是否触摸关闭key
    private static final String KEY_CANCEL_OUTSIDE = "comment_cancel_outside";
    //关闭时间
    private static final String KEY_CANCEL_CUTDOWN = "comment_cancel_cutdown";
    //FragmentManager
    private FragmentManager mFragmentManager;
    //是否点击其他区域关闭 默认关闭
    private boolean mIsCancelOutside = true;
    //遮罩透明度 默认是0.2f
    private float mDimAmount=0.2f;
    // layout布局
    @LayoutRes
    private int mLayoutRes;
    //显示时间  默认是秒值
    private long mCutDown=-1;

    private CutDownTime cutDownTime;


    public static UpDialog create(FragmentManager mFragmentManager){
        UpDialog commentDialog=new UpDialog();
        commentDialog.setFragmentManager(mFragmentManager);
        return commentDialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mLayoutRes = savedInstanceState.getInt(KEY_LAYOUT_RES);
            mDimAmount = savedInstanceState.getFloat(KEY_DIM);
            mIsCancelOutside = savedInstanceState.getBoolean(KEY_CANCEL_OUTSIDE);
            mCutDown = savedInstanceState.getLong(KEY_CANCEL_CUTDOWN,-1);
        }
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(KEY_LAYOUT_RES, mLayoutRes);
        outState.putFloat(KEY_DIM, mDimAmount);
        outState.putBoolean(KEY_CANCEL_OUTSIDE, mIsCancelOutside);
        outState.putLong(KEY_CANCEL_CUTDOWN, mCutDown);
        super.onSaveInstanceState(outState);
    }


    @Override
    public int getLayoutRes() {
        return mLayoutRes;
    }

    @Override
    public void bindView(View v) {
        if(mViewListener!=null){
            mViewListener.bindView(v);
        }
    }

    @Override
    public int getGravity() {
        return Gravity.TOP;
    }

    @Override
    public int getStyleRes() {
        return R.style.UpDialog;
    }

    public UpDialog setFragmentManager(FragmentManager manager) {
        mFragmentManager = manager;
        return this;
    }

    public UpDialog setViewListener(ViewListener listener) {
        mViewListener = listener;
        return this;
    }

    public UpDialog setLayoutRes(@LayoutRes int layoutRes) {
        mLayoutRes = layoutRes;
        return this;
    }

    public UpDialog setCancelOutside(boolean cancel) {
        mIsCancelOutside = cancel;
        return this;
    }

    public UpDialog setTag(String tag) {
        TAG = tag;
        return this;
    }

    public UpDialog setDimAmount(float dim) {
        mDimAmount = dim;
        return this;
    }

    public UpDialog setCutDownTime(long time) {
        mCutDown = time/1000;
        return this;
    }

    public long getCutDownTime(){
        return  mCutDown;
    }

    public UpDialog show() {
        setTag(TAG);
        show(mFragmentManager);
        if(mCutDown!=-1){
            if(mHandle!=null&&mHandle.hasMessages(DISSMISS_MSG_CODE))
                mHandle.removeMessages(DISSMISS_MSG_CODE);
            mCutDown=getCutDownTime();
            cutDownTime = new CutDownTime();
            cutDownTime.start();
        }
        return this;
    }


    private ViewListener mViewListener;

    public interface ViewListener {
        void bindView(View v);
    }

    private class CutDownTime extends Thread {

        @Override
        public void run() {
            super.run();
            while (mCutDown > 0) {
                try {
                    Thread.sleep(1000);
                    mCutDown--;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (mCutDown == 0) {
                mHandle.sendEmptyMessage(DISSMISS_MSG_CODE);
            }

        }
    }

    public Handler mHandle=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case DISSMISS_MSG_CODE:
                    dismiss();
                    break;
            }
        }
    };

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        mCutDown = -1;
        if(cutDownTime!=null)
            cutDownTime.interrupt();
    }
}
