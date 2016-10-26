package com.dalong.commentdialog;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dalong.dialoglib.BottomDialog;
import com.dalong.dialoglib.RightDialog;
import com.dalong.dialoglib.UpDialog;


public class MainActivity extends AppCompatActivity {

    private EditText mEditText;
    private TextView mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 评论
     *
     * @param view
     */
    public void comment(View view) {
        showCommentDialog();
    }

    /**
     * 从上往下dialog
     *
     * @param view
     */
    public void upClick(View view) {
        showUpDialog();
    }
    /**
     * 从右往左dialog
     *
     * @param view
     */
    public void right(View view) {
        showRightDialog();
    }

    private void showRightDialog() {
        RightDialog.create(getSupportFragmentManager())
                .setLayoutRes(R.layout.dialog_right_view)//设置显示布局
                .setDimAmount(0.6f) //设置背景透明度
                .setCancelOutside(true) // 设置是否可以点击其他区域关闭dialog
                .setTag("comment")
                .show();
    }

    /**
     * 从上往下dialog
     */
    private void showUpDialog() {
        UpDialog.create(getSupportFragmentManager())
                .setLayoutRes(R.layout.dialog_up_view)//设置显示布局
                .setCutDownTime(3000)//设置显示时间
                .setDimAmount(0.6f) //设置背景透明度
                .setCancelOutside(false) // 设置是否可以点击其他区域关闭dialog
                .setTag("comment")
                .show();
    }

    /**
     * 弹出评论dialog
     */
    private void showCommentDialog() {
        BottomDialog.create(getSupportFragmentManager())
                .setLayoutRes(R.layout.dialog_comment_view)
                .setViewListener(new BottomDialog.ViewListener() {
                    @Override
                    public void bindView(View v) {
                        initView(v);
                    }
                })
                .setDimAmount(0.6f)
                .setCancelOutside(false)
                .setTag("comment")
                .show();
    }

    private void initView(View v) {
        mEditText = (EditText) v.findViewById(R.id.edit_text);
        mButton = (TextView) v.findViewById(R.id.comment_btn);
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString())) {
                    mButton.setBackgroundResource(R.drawable.dialog_send_btn);
                    mButton.setEnabled(false);
                } else {
                    mButton.setBackgroundResource(R.drawable.dialog_send_btn_pressed);
                    mButton.setEnabled(true);
                }
            }
        });
        mEditText.post(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) MainActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(mEditText, 0);
            }
        });
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "评论：" + mEditText.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
