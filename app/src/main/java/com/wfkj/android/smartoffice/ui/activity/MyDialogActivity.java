package com.wfkj.android.smartoffice.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.wfkj.android.smartoffice.R;

public class MyDialogActivity extends Activity implements OnClickListener {

    private TextView my_dialog_btn_no;//取消按钮
    private TextView my_dialog_btn_ok;//确认按钮
    private TextView my_dialog_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_dialog);
        MyDialogActivity.this.setFinishOnTouchOutside(false);
        init();
    }

    private void init() {
        my_dialog_text = (TextView) this.findViewById(R.id.my_dialog_text);
        my_dialog_btn_no = (TextView) this.findViewById(R.id.my_dialog_btn_no);
        my_dialog_btn_ok = (TextView) this.findViewById(R.id.my_dialog_btn_ok);


        my_dialog_btn_no.setOnClickListener(this);
        my_dialog_btn_ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.my_dialog_btn_no:
                intent.putExtra("TAG_DELETE", 1);
                break;
            case R.id.my_dialog_btn_ok:
                intent.putExtra("TAG_DELETE", 0);
                break;

        }
        setResult(121, intent);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 如果按下的是返回键，并且没有重复
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
