package com.wfkj.android.smartoffice.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gizwits.gizwifisdk.api.GizWifiDevice;
import com.gizwits.gizwifisdk.api.GizWifiSDK;
import com.gizwits.gizwifisdk.enumration.GizWifiDeviceNetStatus;
import com.gizwits.gizwifisdk.enumration.GizWifiErrorCode;
import com.google.gson.Gson;
import com.wfkj.android.smartoffice.MainActivity;
import com.wfkj.android.smartoffice.R;
import com.wfkj.android.smartoffice.model.ginseng.BindCodeGinseng;
import com.wfkj.android.smartoffice.model.ginseng.RegisteredGinseng;
import com.wfkj.android.smartoffice.model.sever_model.OutModel;
import com.wfkj.android.smartoffice.model.sever_model.OutModel_Binding;
import com.wfkj.android.smartoffice.my_interface.HttpInterface;
import com.wfkj.android.smartoffice.util.AppUtils;
import com.wfkj.android.smartoffice.util.Constants;
import com.wfkj.android.smartoffice.util.FileUtil;
import com.wfkj.android.smartoffice.util.HttpUtils;
import com.wfkj.android.smartoffice.util.http_util.RetrofitAPIManager;
import com.wfkj.android.smartoffice.util.jzy_util.CmdCenter;
import com.wfkj.android.smartoffice.util.view_util.LoadingDialog;
import com.wfkj.android.smartoffice.util.view_util.MyCount;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 注册页面
 * Created by wangdongyang on 16/12/5.
 */
public class RegisteredActivity extends BaseFragmentActivity implements OnClickListener {

    private final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1112;

    private String phone;
    private String code;
    private String password;
    private String password_confirm;


    private ImageView head_back;
    private TextView head_title;
    private ImageView head_img;

    private EditText registered_username;//用户名
    private EditText registered_verification;//密码
    private EditText registered_password;//输入密码
    private EditText registered_password_confirm;//确认密码
    private TextView registered_verification_btn;//获取验证码
    private TextView registered_verification_txt;//显示验证码倒计时
    private TextView registered_btn;//注册按钮
    private LinearLayout registered_login_btn;//已有账号登录按钮

    private LoadingDialog loadingDialog;

    private Gson gson;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 200:
                    List<String> list = new ArrayList<>();
                    list.add(Constants.APPID);
                    GizWifiSDK.sharedInstance().getBoundDevices(FileUtil.loadString(RegisteredActivity.this, Constants.SP_UID),
                            FileUtil.loadString(RegisteredActivity.this, Constants.SP_TOKEN), null);
                    break;
                case 500:
                    ActivityCompat.requestPermissions(RegisteredActivity.this,
                            new String[]{Manifest.permission.READ_CONTACTS},
                            MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);
        gson = new Gson();
        loadingDialog = new LoadingDialog(RegisteredActivity.this);
        init();
    }

    private void init() {
        registered_username = (EditText) this.findViewById(R.id.registered_username);
        registered_verification = (EditText) this.findViewById(R.id.registered_verification);
        registered_password = (EditText) this.findViewById(R.id.registered_password);
        registered_password_confirm = (EditText) this.findViewById(R.id.registered_password_confirm);
        registered_verification_btn = (TextView) this.findViewById(R.id.registered_verification_btn);
        registered_verification_txt = (TextView) this.findViewById(R.id.registered_verification_txt);
        registered_btn = (TextView) this.findViewById(R.id.registered_btn);
        registered_login_btn = (LinearLayout) this.findViewById(R.id.registered_login_btn);

        head_back = (ImageView) this.findViewById(R.id.head_back);
        head_title = (TextView) this.findViewById(R.id.head_title);
        head_img = (ImageView) this.findViewById(R.id.head_img);

        registered_verification_btn.setOnClickListener(this);
        registered_login_btn.setOnClickListener(this);
        registered_btn.setOnClickListener(this);
        head_back.setOnClickListener(this);
        head_title.setText("注册");

        head_img.setVisibility(View.GONE);
    }


    /**
     * 倒计时方法
     */
    private void countdown() {
        registered_verification_btn.setVisibility(View.INVISIBLE);
        registered_verification_txt.setVisibility(View.VISIBLE);
        MyCount myCount = new MyCount(registered_verification_txt, registered_verification_btn,
                60000, 1000);
        myCount.start();
    }
    private void login() {
        CmdCenter.getInstance(this).setXpgWifiDevice(mXpgWifiDevice);
//        Constants.house = gson.fromJson(Constants.hoseJsonStr, House.class);
        FileUtil.saveString(RegisteredActivity.this, Constants.HOUSEJSON, gson.toJson(Constants.house));
        System.out.println("-------------loginHouse---------" + Constants.house);

        FileUtil.saveString(RegisteredActivity.this, Constants.USERNAME, phone);
        FileUtil.saveString(RegisteredActivity.this, Constants.PASSWORD, password);


        Intent intent = new Intent();
        intent.setClass(RegisteredActivity.this, MainActivity.class);
        startActivity(intent);
        if(LogInActivity.logInActivity != null){
            LogInActivity.logInActivity.finish();
        }
        this.finish();
    }


    /**
     * 机智云登录操作
     *
     * @param
     */

    private void getLogIn() {
        GizWifiSDK.sharedInstance().userLogin(AppUtils.getMd5(phone) + Constants.UserNameEnd, Constants.Password);

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

        Call<OutModel> call = httpInterface.getCode(phone);


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
                        countdown();
                        AppUtils.show(getApplication(), outModel.getMessage());
                    }
                }

            }

            @Override
            public void onFailure(Call<OutModel> call, Throwable t) {
                loadingDialog.dismiss();
                countdown();
                System.out.println("---aaaaa--failure--bbbbb---" + t.getMessage());
            }
        });
    }


    /**
     * 注册请求
     *
     */

    private void getRegisteredData() {

        RegisteredGinseng registeredGinseng = new RegisteredGinseng();
        registeredGinseng.setMobile(phone);
        registeredGinseng.setPassword(password);
        registeredGinseng.setVerify_code(code);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HttpUtils.HOSTUTIL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(RetrofitAPIManager.getOkHttpClient(getApplicationContext()))
                .build();

        HttpInterface httpInterface = retrofit.create(HttpInterface.class);

        Call<OutModel> call = httpInterface.getRegistered(registeredGinseng);

        call.enqueue(new Callback<OutModel>() {
            @Override
            public void onResponse(Call<OutModel> call, Response<OutModel> response) {
                System.out.println("-------bbbbbbbbbb--aaaaaaaaa-----" + response.body().toString());
                loadingDialog.dismiss();

                OutModel outModel = response.body();
                if (outModel != null) {
                    if (outModel.getCode() == 200) {
                        FileUtil.saveString(RegisteredActivity.this,Constants.ACCESSTOKEN,outModel.getResult().getOauth().getAccessToken());
                        if (ContextCompat.checkSelfPermission(RegisteredActivity.this,
                                Manifest.permission.READ_CONTACTS)
                                != PackageManager.PERMISSION_GRANTED) {
                            getGoMipca();
                        }else{
                            //

                            Message msg = new Message();
                            msg.what = 500;
                            mHandler.sendMessage(msg);
                        }


                    } else {
                        loadingDialog.dismiss();
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


    private void getBindCodeData(String code){
        loadingDialog.show();
        BindCodeGinseng bindCodeGinseng = new BindCodeGinseng();
        bindCodeGinseng.setCode(code);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HttpUtils.HOSTUTIL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(RetrofitAPIManager.getOkHttpClient(getApplicationContext()))
                .build();

        HttpInterface httpInterface = retrofit.create(HttpInterface.class);

        Call<OutModel_Binding> call = httpInterface.getBindCode("smart/v1/Account/Scan?accessToken="+FileUtil.loadString(RegisteredActivity.this,Constants.ACCESSTOKEN),bindCodeGinseng);

        call.enqueue(new Callback<OutModel_Binding>() {
            @Override
            public void onResponse(Call<OutModel_Binding> call, Response<OutModel_Binding> response) {
                loadingDialog.dismiss();

                System.out.println("-----json--------"+response.body());

                OutModel_Binding outModel_binding = response.body();

                if(outModel_binding.getCode()==200){
                    Constants.house = outModel_binding.getResult();
                    FileUtil.saveString(RegisteredActivity.this, Constants.SP_HOUSE, gson.toJson(Constants.house));
                    FileUtil.saveString(RegisteredActivity.this, Constants.SP_ROLE, Constants.house.getRole());

                    Set<String> tags = new HashSet<String>();
                    tags.add(Constants.house.getGateways().get(0).getExtid());
                    JPushInterface.setAliasAndTags(RegisteredActivity.this, null, tags, new TagAliasCallback() {
                        @Override
                        public void gotResult(int i, String s, Set<String> set) {

                        }
                    });

                    getLogIn();
                }else{
                    AppUtils.show(RegisteredActivity.this, "绑定房屋失败");
                }
            }

            @Override
            public void onFailure(Call<OutModel_Binding> call, Throwable t) {
                loadingDialog.dismiss();
                AppUtils.show(RegisteredActivity.this,"绑定失败");
            }
        });
    }

    /**
     * 执行注册操作
     */
    private void getRegistered() {
        phone = registered_username.getText().toString();
        code = registered_verification.getText().toString();
        password = registered_password.getText().toString();
        password_confirm = registered_password_confirm.getText().toString();

        if (phone != null && !phone.equals("") && code != null && !code.equals("") && password != null && !password.equals("") && password_confirm != null && !password_confirm.equals("")) {
            if (password.equals(password_confirm)) {
                loadingDialog.show();
                mCenter.cRegisterDefaultUser(AppUtils.getMd5(phone) + Constants.UserNameEnd, Constants.Password);
            } else {
                AppUtils.show(RegisteredActivity.this, "两次密码输入不一致");
            }
        } else {
            AppUtils.show(RegisteredActivity.this, "输入信息不能为空");
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registered_verification_btn://倒计时
                loadingDialog.show();
                getCodeData(registered_username.getText().toString());
                break;
            case R.id.registered_login_btn://登录
                this.finish();
                break;
            case R.id.head_back://返回
                this.finish();
                break;
            case R.id.registered_btn://注册
                getRegistered();
//                getGoMipca();
                break;
        }
    }

    /**
     * 跳转二维码扫描页面
     */
    private void getGoMipca(){
        Intent intent = new Intent();
        intent.setClass(RegisteredActivity.this,MipcaActivityCapture.class);
        startActivityForResult(intent,100);
    }


    @Override
    protected void didRegisterUser(GizWifiErrorCode result, String uid, String token) {
        super.didRegisterUser(result, uid, token);
        if (isFinishing()){
            loadingDialog.dismiss();
            return;
        }

        System.out.println("-----" + result.toString() + "----" + uid + "-----" + token);
        if (result == GizWifiErrorCode.GIZ_SDK_SUCCESS) {
            //注册成功，获取到uid和token
            FileUtil.saveString(RegisteredActivity.this, Constants.SP_UID, uid);
            FileUtil.saveString(RegisteredActivity.this, Constants.SP_TOKEN, token);
            FileUtil.saveString(RegisteredActivity.this, Constants.USERNAME, phone);
            FileUtil.saveString(RegisteredActivity.this, Constants.PASSWORD, password);
            getRegisteredData();
        } else if (result == GizWifiErrorCode.GIZ_OPENAPI_USERNAME_UNAVALIABLE) {
            FileUtil.saveString(RegisteredActivity.this, Constants.USERNAME, phone);
            FileUtil.saveString(RegisteredActivity.this, Constants.PASSWORD, password);
            getRegisteredData();
        } else {
            Toast.makeText(getApplicationContext(), "注册失败", Toast.LENGTH_SHORT).show();
            loadingDialog.dismiss();
        }
    }



    /**
     * 登录机智云的回调
     *
     * @param result
     * @param uid
     * @param token
     */
    @Override
    protected void didUserLogin(GizWifiErrorCode result, String uid, String token) {
        super.didUserLogin(result, uid, token);
        if (isFinishing())
            return;
        System.out.println("-----denglu----" + result.toString());
        if (result == GizWifiErrorCode.GIZ_SDK_SUCCESS) {
            //登陆成功，获取到uid和token
            FileUtil.saveString(RegisteredActivity.this, Constants.SP_UID, uid);
            FileUtil.saveString(RegisteredActivity.this, Constants.SP_TOKEN, token);
            setmanager.setUid(uid);
            setmanager.setToken(token);




//            login();
            //更新配置文件

            Message msg = new Message();
            msg.what = 200;
            mHandler.sendMessage(msg);
        } else {
            //登陆失败，弹出错误信息
            loadingDialog.dismiss();
            Toast.makeText(getApplicationContext(), "登录失败", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    protected void didDiscovered(GizWifiErrorCode result, List<GizWifiDevice> deviceList) {
        super.didDiscovered(result, deviceList);
        if (isFinishing()) return;
        if (result == GizWifiErrorCode.GIZ_SDK_SUCCESS && (deviceList.size() > 0)) {
            //更新配置文件成功，返回设备列表
            deviceslist = deviceList;
            System.out.println("---4-----" + result.toString());
            System.out.println("---5-----" + deviceslist.toString());
            GizWifiDevice mDevice = deviceslist.get(0);


            if (!mDevice.isBind()) {
                System.out.println("--{}{}{}{}----");
                System.out.println("-----uid---" + FileUtil.loadString(RegisteredActivity.this, Constants.SP_UID));
                System.out.println("------token----" + FileUtil.loadString(RegisteredActivity.this, Constants.SP_TOKEN));
                mDevice.setSubscribe(true);
//                GizWifiSDK.sharedInstance().bindRemoteDevice(FileUtil.loadString(LogInActivity.this, Constants.SP_UID), FileUtil.loadString(LogInActivity.this, Constants.SP_TOKEN),
//                        mDevice.getMacAddress(), mDevice.getProductKey(),Constants.AppSerret);
            } else {
                System.out.println("--[][][][][]----");
                mXpgWifiDevice = mDevice;
                mXpgWifiDevice.setListener(deviceListener);
                if (mXpgWifiDevice.getNetStatus() == GizWifiDeviceNetStatus.GizDeviceControlled) {
                    GizWifiSDK.sharedInstance().updateDeviceFromServer(Constants.PRODUCT_KEY);
                    GizWifiSDK.sharedInstance().bindRemoteDevice(FileUtil.loadString(RegisteredActivity.this, Constants.SP_UID), FileUtil.loadString(RegisteredActivity.this, Constants.SP_TOKEN),
                            mDevice.getMacAddress(), mDevice.getProductKey(), null);
                    Thread thread = new Thread() {

                        @Override
                        public void run() {
                            super.run();
                            //TODO:获取配置文件

                        }
                    };
                    thread.start();
                } else {
                    mHandler.sendEmptyMessageDelayed(107, 30000);
                    mXpgWifiDevice.setSubscribe(true);
                }
            }

        } else {
            System.out.println("---7-----" + result.toString());
            loadingDialog.dismiss();
//            AppUtils.show(LogInActivity.this,"失败");
        }
    }

    @Override
    protected void didReceiveData(GizWifiErrorCode result, GizWifiDevice device, final ConcurrentHashMap<String, Object> dataMap, int sn) {
        super.didReceiveData(result, device, dataMap, sn);
        if (isFinishing())
            return;
        System.out.println("-----Aaaaaaaa----" + dataMap.toString());
//        Constants.house = gson.fromJson(Constants.hoseJsonStr, House.class);
        if (dataMap != null && !dataMap.toString().equals("{}")) {

            String str = gson.toJson(dataMap);
            System.out.println("------str-------" + str);
            FileUtil.saveString(RegisteredActivity.this, Constants.JZYSTR, str);
            CmdCenter.getInstance(this).setXpgWifiDevice(mXpgWifiDevice);
            login();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case 1000:
                String code = data.getStringExtra("CODE");
                getBindCodeData(code);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doNext(requestCode, grantResults);
    }

    private void doNext(int requestCode, int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                getGoMipca();
            } else {
                // Permission Denied
                //  displayFrameworkBugMessageAndExit();
                AppUtils.show(this, "请在应用管理中打开“相机”访问权限！");
            }
        }
    }
}
