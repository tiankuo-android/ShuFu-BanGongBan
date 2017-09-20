package com.wfkj.android.smartoffice.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wfkj.android.smartoffice.R;
import com.wfkj.android.smartoffice.model.ginseng.ForgotPasswordGinseng;
import com.wfkj.android.smartoffice.model.sever_model.ModifyPassword;
import com.wfkj.android.smartoffice.my_interface.HttpInterface;
import com.wfkj.android.smartoffice.util.AppUtils;
import com.wfkj.android.smartoffice.util.Constants;
import com.wfkj.android.smartoffice.util.FileUtil;
import com.wfkj.android.smartoffice.util.HttpUtils;
import com.wfkj.android.smartoffice.util.http_util.RetrofitAPIManager;
import com.wfkj.android.smartoffice.util.view_util.LoadingDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wangdongyang on 17/2/8.
 */
public class ModifyPasswordActivity extends Activity implements OnClickListener {


    private LoadingDialog loadingDialog;

    private String passwordOldStr;
    private String passwordStr;
    private String passwordConfirmStr;

    private ImageView head_back;
    private TextView head_title;
    private ImageView head_img;
    private EditText edit_password_old;
    private EditText edit_password;
    private EditText edit_password_confirm;
    private TextView password_submit_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);
        loadingDialog = new LoadingDialog(ModifyPasswordActivity.this);
        init();
    }


    private void init() {
        head_back = (ImageView) this.findViewById(R.id.head_back);
        head_title = (TextView) this.findViewById(R.id.head_title);
        head_img = (ImageView) this.findViewById(R.id.head_img);
        edit_password_old = (EditText) this.findViewById(R.id.edit_password_old);
        edit_password = (EditText) this.findViewById(R.id.edit_password);
        edit_password_confirm = (EditText) this.findViewById(R.id.edit_password_confirm);
        password_submit_btn = (TextView) this.findViewById(R.id.password_submit_btn);

        head_img.setVisibility(View.GONE);

        head_back.setOnClickListener(this);
        password_submit_btn.setOnClickListener(this);

    }


    private void getSubmit() {
        passwordOldStr = edit_password_old.getText().toString().trim();
        passwordStr = edit_password.getText().toString().trim();
        passwordConfirmStr = edit_password_confirm.getText().toString().trim();

        if (passwordOldStr == null || passwordOldStr.equals("") || passwordStr == null || passwordStr.equals("") || passwordConfirmStr == null || passwordConfirmStr.equals("")) {
            AppUtils.show(ModifyPasswordActivity.this, "输入不能为空");
        } else {
            if (passwordStr.equals(passwordConfirmStr)) {
                getSubmitData();
            } else {
                AppUtils.show(ModifyPasswordActivity.this, "新密码输入不一致");
            }
        }
    }


    private void getSubmitData() {

            loadingDialog.show();

            ForgotPasswordGinseng forgotPasswordGinseng = new ForgotPasswordGinseng();
            forgotPasswordGinseng.setPassword(passwordOldStr);
            forgotPasswordGinseng.setPassword_new(passwordStr);
            forgotPasswordGinseng.setPassword_confirm(passwordConfirmStr);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(HttpUtils.HOSTUTIL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(RetrofitAPIManager.getOkHttpClient(getApplicationContext()))
                    .build();

            HttpInterface httpInterface = retrofit.create(HttpInterface.class);

            Call<ModifyPassword> call = httpInterface.getForgotPassword("smart/v1/Account/Password?accessToken="+ FileUtil.loadString(ModifyPasswordActivity.this, Constants.ACCESSTOKEN),forgotPasswordGinseng);

            call.enqueue(new Callback<ModifyPassword>() {
                @Override
                public void onResponse(Call<ModifyPassword> call, Response<ModifyPassword> response) {
                    loadingDialog.dismiss();

                    ModifyPassword modifyPassword = response.body();
                    if(modifyPassword.getCode() == 200){
                        AppUtils.show(ModifyPasswordActivity.this,"修改密码成功");
                        FileUtil.saveString(ModifyPasswordActivity.this,Constants.PASSWORD,passwordStr);
                        finish();
                    }else{
                        AppUtils.show(ModifyPasswordActivity.this,"修改密码失败");
                    }

                    System.out.println("-----json--------"+response.body());
                }

                @Override
                public void onFailure(Call<ModifyPassword> call, Throwable t) {
                    loadingDialog.dismiss();
                    AppUtils.show(ModifyPasswordActivity.this,"修改密码失败");
                }
            });


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.head_back:
                this.finish();
                break;
            case R.id.password_submit_btn:
                getSubmit();
                break;
        }

    }
}
