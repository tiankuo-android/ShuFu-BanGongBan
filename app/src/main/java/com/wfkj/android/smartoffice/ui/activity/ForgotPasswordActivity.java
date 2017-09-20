package com.wfkj.android.smartoffice.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wfkj.android.smartoffice.R;
import com.wfkj.android.smartoffice.model.ginseng.ResetPasswordGinseng;
import com.wfkj.android.smartoffice.model.sever_model.ModifyPassword;
import com.wfkj.android.smartoffice.model.sever_model.OutModel;
import com.wfkj.android.smartoffice.my_interface.HttpInterface;
import com.wfkj.android.smartoffice.util.AppUtils;
import com.wfkj.android.smartoffice.util.HttpUtils;
import com.wfkj.android.smartoffice.util.http_util.RetrofitAPIManager;
import com.wfkj.android.smartoffice.util.view_util.LoadingDialog;
import com.wfkj.android.smartoffice.util.view_util.MyCount;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 忘记密码页面
 * Created by wangdongyang on 16/12/5.
 */
public class ForgotPasswordActivity extends BaseActivity implements OnClickListener {

    private LoadingDialog loadingDialog;

    private String usernameStr;
    private String verificationStr;
    private String password;
    private String password_confirmStr;

    private ImageView head_back;
    private TextView head_title;
    private ImageView head_img;

    private EditText forgot_password_username;//用户名
    private EditText forgot_password_verification;//验证码
    private EditText forgot_password_password;//新密码
    private EditText forgot_password_password_confirm;//确认密码
    private TextView forgot_password_verification_btn;//获取验证码按钮
    private TextView forgot_password_verification_txt;//显示验证码倒计时
    private TextView forgot_password_submit_btn;//提交按钮
    private LinearLayout forgot_password_login_btn;//已有账号请登录按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        loadingDialog = new LoadingDialog(this);
        init();
    }

    private void init() {
        forgot_password_username = (EditText) this.findViewById(R.id.forgot_password_username);
        forgot_password_verification = (EditText) this.findViewById(R.id.forgot_password_verification);
        forgot_password_password = (EditText) this.findViewById(R.id.forgot_password_password);
        forgot_password_password_confirm = (EditText) this.findViewById(R.id.forgot_password_password_confirm);
        forgot_password_verification_btn = (TextView) this.findViewById(R.id.forgot_password_verification_btn);
        forgot_password_verification_txt = (TextView) this.findViewById(R.id.forgot_password_verification_txt);
        forgot_password_submit_btn = (TextView) this.findViewById(R.id.forgot_password_submit_btn);
        forgot_password_login_btn = (LinearLayout) this.findViewById(R.id.forgot_password_login_btn);

        head_back = (ImageView) this.findViewById(R.id.head_back);
        head_title = (TextView) this.findViewById(R.id.head_title);
        head_img = (ImageView) this.findViewById(R.id.head_img);

        forgot_password_login_btn.setOnClickListener(this);
        forgot_password_verification_btn.setOnClickListener(this);
        forgot_password_submit_btn.setOnClickListener(this);
        head_back.setOnClickListener(this);
        head_title.setText("忘记密码");
        head_img.setVisibility(View.GONE);

    }

    private void countdown(){
        forgot_password_verification_btn.setVisibility(View.INVISIBLE);
        forgot_password_verification_txt.setVisibility(View.VISIBLE);
        MyCount myCount = new MyCount(forgot_password_verification_txt, forgot_password_verification_btn,
                60000, 1000);
        myCount.start();
    }


    /**
     * 获取验证码请求
     *
     * @param phone
     */
    private void getCodeData(String phone) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HttpUtils.HOSTUTIL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(RetrofitAPIManager.getOkHttpClient(getApplicationContext()))
                .build();

        HttpInterface httpInterface = retrofit.create(HttpInterface.class);

        Call<OutModel> call = httpInterface.getCodeForgotPassword(phone);


        call.enqueue(new Callback<OutModel>() {
            @Override
            public void onResponse(Call<OutModel> call, Response<OutModel> response) {
                System.out.println("-------bbbbbbbbbb--aaaaaaaaa-----" + response.body().toString());
                loadingDialog.dismiss();

                OutModel outModel = response.body();
                if (outModel != null) {
                    if (outModel.getCode() == 200) {
                        countdown();
                    } else {
                        AppUtils.show(getApplication(), outModel.getMessage());
                    }
                }

            }

            @Override
            public void onFailure(Call<OutModel> call, Throwable t) {
                loadingDialog.dismiss();
                System.out.println("---aaaaa--failure--bbbbb---" + t.getMessage());
            }
        });
    }


    private void getSubmitData() {

        loadingDialog.show();

        ResetPasswordGinseng resetPasswordGinseng = new ResetPasswordGinseng();
        resetPasswordGinseng.setMobile(usernameStr);
        resetPasswordGinseng.setVerify_code(verificationStr);
        resetPasswordGinseng.setPassword_new(password);
        resetPasswordGinseng.setPassword_confirm(password_confirmStr);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HttpUtils.HOSTUTIL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(RetrofitAPIManager.getOkHttpClient(getApplicationContext()))
                .build();

        HttpInterface httpInterface = retrofit.create(HttpInterface.class);

        Call<ModifyPassword> call = httpInterface.getResetPassword(resetPasswordGinseng);

        call.enqueue(new Callback<ModifyPassword>() {
            @Override
            public void onResponse(Call<ModifyPassword> call, Response<ModifyPassword> response) {
                loadingDialog.dismiss();

                ModifyPassword modifyPassword = response.body();
                if(modifyPassword.getCode() == 200){
                    AppUtils.show(ForgotPasswordActivity.this,"修改密码成功");
                    finish();
                }else{
                    AppUtils.show(ForgotPasswordActivity.this,modifyPassword.getMessage());
                }

                System.out.println("-----json--------"+response.body());
            }

            @Override
            public void onFailure(Call<ModifyPassword> call, Throwable t) {
                loadingDialog.dismiss();
                AppUtils.show(ForgotPasswordActivity.this,"修改密码失败");
            }
        });


    }




    private void getSubmit(){

        usernameStr = forgot_password_username.getText().toString().trim();
        verificationStr = forgot_password_verification.getText().toString().trim();
        password = forgot_password_password.getText().toString().trim();
        password_confirmStr = forgot_password_password_confirm.getText().toString().trim();


        if(usernameStr == null || usernameStr.equals("")){
            AppUtils.show(ForgotPasswordActivity.this,"用户名不能为空");
        }else{
            if(verificationStr == null || verificationStr.equals("")){
                AppUtils.show(ForgotPasswordActivity.this,"验证码不能为空");
            }else{
                if(password == null || password.equals("")){
                    AppUtils.show(ForgotPasswordActivity.this,"密码不能为空");
                }else{
                    if(password_confirmStr == null || password_confirmStr.equals("")){
                        AppUtils.show(ForgotPasswordActivity.this,"确认密码不能为空");
                    }else{
                        if(password.equals(password_confirmStr)){
                            getSubmitData();
                        }else{
                            AppUtils.show(ForgotPasswordActivity.this,"两次密码输入不一致");
                        }
                    }
                }
            }
        }


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.forgot_password_verification_btn://获取验证码
                String phone = forgot_password_username.getText().toString().trim();
                if(phone != null && !phone.equals("")){
                    getCodeData(phone);
                }else{
                    AppUtils.show(ForgotPasswordActivity.this,"用户名不能为空");
                }
                break;
            case R.id.head_back://返回
                this.finish();
                break;
            case R.id.forgot_password_login_btn://直接登录
                this.finish();
                break;
            case R.id.forgot_password_submit_btn://提交
                getSubmit();
                break;
        }
    }
}
