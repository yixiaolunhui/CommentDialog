package com.dalong.dialoglib;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by zhouweilong on 2016/10/13.
 */

public abstract class BaseDialog extends DialogFragment {

    //tag
    private static final String TAG = "BaseDialog";

    //默认遮罩透明度
    private static final float DEFAULT_MASK_TRANS=0.2f;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE,getStyleRes());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(getCancelOutside());
        View v = inflater.inflate(getLayoutRes(), container, false);
        bindView(v);
        return v;
    }

    /**
     * 获取布局文件
     * @return
     */
    @LayoutRes
    public abstract int getLayoutRes();

    /**
     * 绑定的view处理
     * @param v
     */
    public abstract void bindView(View v);

    /**
     * 获取主题
     * @return
     */
    public abstract int getStyleRes();

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.dimAmount = getDimAmount();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = getGravity();
        window.setAttributes(params);
    }

    /**
     * 获取点击其他区域是否关闭
     * @return
     */
    public boolean getCancelOutside() {
        return true;
    }
    /**
     * 获取遮罩透明度
     * @return
     */
    public float getDimAmount() {
        return DEFAULT_MASK_TRANS;
    }

    /**
     * 获取对齐方式
     * @return
     */
    public int  getGravity(){
        return Gravity.BOTTOM;
    }

    /**
     * 获取tag
     * @return
     */
    public String getFragmentTag() {
        return TAG;
    }

    /**
     * show显示dialog
     * @param fragmentManager
     */
    public void show(FragmentManager fragmentManager) {
        show(fragmentManager, TAG);
    }
}
