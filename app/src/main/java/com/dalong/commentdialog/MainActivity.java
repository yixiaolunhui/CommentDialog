package com.dalong.commentdialog;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.dalong.dialoglib.CommentDialog;

public class MainActivity extends AppCompatActivity {

    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 评论
     * @param view
     */
    public void comment(View view){
        showCommentDialog();
    }

    private void showCommentDialog() {
        CommentDialog.create(getSupportFragmentManager())
                .setLayoutRes(R.layout.dialog_comment_view)
                .setViewListener(new CommentDialog.ViewListener() {
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
        mEditText.post(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager)MainActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(mEditText, 0);
            }
        });
        v.findViewById(R.id.comment_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "评论："+mEditText.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
