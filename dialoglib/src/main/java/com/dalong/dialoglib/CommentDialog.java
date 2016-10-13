package com.dalong.dialoglib;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.View;

/**
 *
 * Created by zhouweilong on 2016/10/13.
 */

public class CommentDialog extends BaseDialog {
    //tag
    private  String TAG = "comment_dialog";
    //layout key
    private static final String KEY_LAYOUT_RES = "comment_layout_res";
    //遮罩透明度 key
    private static final String KEY_DIM = "comment_dim";
    //是否触摸关闭key
    private static final String KEY_CANCEL_OUTSIDE = "comment_cancel_outside";
    //FragmentManager
    private FragmentManager mFragmentManager;
    //是否点击其他区域关闭 默认关闭
    private boolean mIsCancelOutside = true;
    //遮罩透明度 默认是0.2f
    private float mDimAmount=0.2f;
    // layout布局
    @LayoutRes
    private int mLayoutRes;

    public static CommentDialog create(FragmentManager mFragmentManager){
        CommentDialog commentDialog=new CommentDialog();
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
        }
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(KEY_LAYOUT_RES, mLayoutRes);
        outState.putFloat(KEY_DIM, mDimAmount);
        outState.putBoolean(KEY_CANCEL_OUTSIDE, mIsCancelOutside);
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

    public CommentDialog setFragmentManager(FragmentManager manager) {
        mFragmentManager = manager;
        return this;
    }

    public CommentDialog setViewListener(ViewListener listener) {
        mViewListener = listener;
        return this;
    }

    public CommentDialog setLayoutRes(@LayoutRes int layoutRes) {
        mLayoutRes = layoutRes;
        return this;
    }

    public CommentDialog setCancelOutside(boolean cancel) {
        mIsCancelOutside = cancel;
        return this;
    }

    public CommentDialog setTag(String tag) {
        TAG = tag;
        return this;
    }

    public CommentDialog setDimAmount(float dim) {
        mDimAmount = dim;
        return this;
    }

    public CommentDialog show() {
        show(mFragmentManager, TAG);
        return this;
    }


    private ViewListener mViewListener;

    public interface ViewListener {
        void bindView(View v);
    }
}
