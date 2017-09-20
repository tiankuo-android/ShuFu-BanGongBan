package com.wfkj.android.smartoffice.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
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
import com.wfkj.android.smartoffice.model.ginseng.ForgotPasswordGinseng;
import com.wfkj.android.smartoffice.model.ginseng.LoginGinseng;
import com.wfkj.android.smartoffice.model.sever_model.House;
import com.wfkj.android.smartoffice.model.sever_model.OutModel_Binding;
import com.wfkj.android.smartoffice.model.sever_model.OutModel_PersonalInForMation;
import com.wfkj.android.smartoffice.my_interface.HttpInterface;
import com.wfkj.android.smartoffice.util.AppUtils;
import com.wfkj.android.smartoffice.util.Constants;
import com.wfkj.android.smartoffice.util.FileUtil;
import com.wfkj.android.smartoffice.util.HttpUtils;
import com.wfkj.android.smartoffice.util.http_util.RetrofitAPIManager;
import com.wfkj.android.smartoffice.util.jzy_util.CmdCenter;
import com.wfkj.android.smartoffice.util.view_util.LoadingDialog;
import com.wfkj.android.smartoffice.util.view_util.ResizeLayout;

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
//15321507650

/**
 * Created by wangdongyang on 16/11/17.
 */
public class LogInActivity extends BaseFragmentActivity implements OnClickListener, ResizeLayout.OnSizeChangedListenner {

    private final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1111;

    private LoadingDialog loadingDialog;
    public static LogInActivity logInActivity;

    private EditText login_et_username;//输入用户名
    private EditText login_et_password;//输入密码
    private TextView login_btn_login;//登录按钮
    private TextView login_btn_registered;//注册按钮
    private LinearLayout login_btn_forgot_password;//忘记密码按钮

    private ResizeLayout layout_out;
    private Gson gson = new Gson();


    private String username;
    private String password;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 500:
                    ActivityCompat.requestPermissions(LogInActivity.this,
                            new String[]{Manifest.permission.READ_CONTACTS},
                            MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loadingDialog = new LoadingDialog(this);
        if (logInActivity == null) {
            logInActivity = this;
        }
        init();
    }

    private void login() {
        CmdCenter.getInstance(this).setXpgWifiDevice(mXpgWifiDevice);
        FileUtil.saveString(LogInActivity.this, Constants.HOUSEJSON, gson.toJson(Constants.house));
        FileUtil.saveString(LogInActivity.this, Constants.USERNAME, username);
        FileUtil.saveString(LogInActivity.this, Constants.PASSWORD, password);
        turnActivity(MainActivity.class, REQUEST_CODE_MAIN);
        this.finish();
    }

    private final int REQUEST_CODE_MAIN = 0X01;
    private final int REQUEST_CODE_REGIST = 0X02;
    private final int REQUEST_CODE_RESET = 0X03;
    private final int REQUEST_CODE_SCAN = 0X04;

    private void turnActivity(Class<?> activity, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(LogInActivity.this, activity);
        startActivityForResult(intent, requestCode);
    }

    private void init() {
        login_et_username = (EditText) this.findViewById(R.id.login_et_username);
        login_et_password = (EditText) this.findViewById(R.id.login_et_password);
        login_btn_login = (TextView) this.findViewById(R.id.login_btn_login);
        layout_out = (ResizeLayout) this.findViewById(R.id.layout_out);
        login_btn_registered = (TextView) this.findViewById(R.id.login_btn_registered);
        login_btn_forgot_password = (LinearLayout) this.findViewById(R.id.login_btn_forgot_password);

        login_btn_login.setOnClickListener(this);
        login_btn_registered.setOnClickListener(this);
        layout_out.setOnSizeChangedListenner(this);
        login_btn_forgot_password.setOnClickListener(this);

        String userName = FileUtil.loadString(LogInActivity.this, Constants.USERNAME);
        String passWord = FileUtil.loadString(LogInActivity.this, Constants.PASSWORD);

        if (userName != null && !userName.equals("") && passWord != null && !passWord.equals("")) {
            login_et_username.setText(userName);
            login_et_password.setText(passWord);
            username = userName;
            password = passWord;
            loadingDialog.show();
            getLogInData();
        }


    }

    /**
     * 机智云登录操作
     *
     * @param
     */
    private void getLogIn() {
        GizWifiSDK.sharedInstance().userLogin(AppUtils.getMd5(username) + Constants.UserNameEnd, Constants.Password);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn_login://点击登录
                username = login_et_username.getText().toString();
                password = login_et_password.getText().toString();
                if (username != null && !username.trim().equals("")) {
                    if (password != null && !password.trim().equals("")) {
                        loadingDialog.show();
                        getLogInData();
                    } else {
                        AppUtils.show(LogInActivity.this, "密码不能为空");
                    }
                } else {
                    AppUtils.show(LogInActivity.this, "用户名不能为空");
                }
                break;
            case R.id.login_btn_registered://注册
                turnActivity(RegisteredActivity.class, REQUEST_CODE_REGIST);
                break;
            case R.id.login_btn_forgot_password:
                turnActivity(ForgotPasswordActivity.class, REQUEST_CODE_RESET);
                break;
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
        if (result == GizWifiErrorCode.GIZ_SDK_SUCCESS) {
            //登陆成功，获取到uid和token
            FileUtil.saveString(LogInActivity.this, Constants.SP_UID, uid);
            FileUtil.saveString(LogInActivity.this, Constants.SP_TOKEN, token);
            setmanager.setUid(uid);
            setmanager.setToken(token);
            //更新配置文件
            List<String> list = new ArrayList<>();
            list.add(Constants.APPID);
            GizWifiSDK.sharedInstance().getBoundDevices(FileUtil.loadString(LogInActivity.this, Constants.SP_UID),
                    FileUtil.loadString(LogInActivity.this, Constants.SP_TOKEN), null);
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
            GizWifiDevice mDevice = null;
            for (GizWifiDevice gizWifiDevice : deviceList) {
                if (Constants.PRODUCT_KEY.equals(gizWifiDevice.getProductKey())) {
                    mDevice = gizWifiDevice;
                }
            }

            if (mDevice != null) {
                if (!mDevice.isBind()) {
                    mDevice.setSubscribe(true);
                } else {
                    mXpgWifiDevice.setListener(deviceListener);
                    if (mXpgWifiDevice.getNetStatus() == GizWifiDeviceNetStatus.GizDeviceControlled) {
                        GizWifiSDK.sharedInstance().updateDeviceFromServer(Constants.PRODUCT_KEY);
                        GizWifiSDK.sharedInstance().bindRemoteDevice(FileUtil.loadString(LogInActivity.this, Constants.SP_UID), FileUtil.loadString(LogInActivity.this, Constants.SP_TOKEN),
                                mDevice.getMacAddress(), mDevice.getProductKey(), Constants.PRODUCT_SECRET);
                    } else {
                        mXpgWifiDevice.setSubscribe(true);
                    }
                }

            } else {
                AppUtils.show(LogInActivity.this, "登录失败");
            }


        } else {
            loadingDialog.dismiss();
        }
    }

    //
    @Override
    protected void didReceiveData(GizWifiErrorCode result, GizWifiDevice device, final ConcurrentHashMap<String, Object> dataMap, int sn) {
        super.didReceiveData(result, device, dataMap, sn);
        if (isFinishing())
            return;
        if (dataMap != null && !dataMap.toString().equals("{}")) {
            String str = gson.toJson(dataMap);
            FileUtil.saveString(LogInActivity.this, Constants.JZYSTR, str);
            CmdCenter.getInstance(this).setXpgWifiDevice(mXpgWifiDevice);
            login();
        }

    }

    @Override
    protected void didBindDevice(GizWifiErrorCode result, String did) {
        super.didBindDevice(result, did);
        if (isFinishing())
            return;
        if (result == GizWifiErrorCode.GIZ_SDK_SUCCESS) {

            GizWifiDevice mDevice = deviceslist.get(0);
            mXpgWifiDevice = mDevice;
            mXpgWifiDevice.setListener(deviceListener);
            if (mXpgWifiDevice.getNetStatus() == GizWifiDeviceNetStatus.GizDeviceControlled) {
            } else {
                mXpgWifiDevice.setSubscribe(true);
            }
        } else {
        }
    }

    /**
     * 跳转二维码扫描页面
     */
    private void getGoMipca() {
        Intent intent = new Intent();
        intent.setClass(LogInActivity.this, MipcaActivityCapture.class);
        startActivityForResult(intent, 100);
    }
    /**
     * 登录请求
     */

    private void getLogInData() {
        LoginGinseng loginGinseng = new LoginGinseng();
        loginGinseng.setMobile(username);
        loginGinseng.setPassword(password);
        loginGinseng.setHouse_type("2");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HttpUtils.HOSTUTIL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(RetrofitAPIManager.getOkHttpClient(getApplicationContext()))
                .build();

        HttpInterface httpInterface = retrofit.create(HttpInterface.class);

        Call<OutModel_PersonalInForMation> call = httpInterface.getLogIn(loginGinseng);

        call.enqueue(new Callback<OutModel_PersonalInForMation>() {
            @Override
            public void onResponse(Call<OutModel_PersonalInForMation> call, Response<OutModel_PersonalInForMation> response) {
                loadingDialog.dismiss();

                Constants.outHouse = response.body();
                if (Constants.outHouse != null) {
                    if (Constants.outHouse.getCode() == 200) {

                        System.out.println("-------json-------" + gson.toJson(Constants.outHouse));
                        FileUtil.saveString(LogInActivity.this, Constants.SP_OUTHOUSE, gson.toJson(Constants.outHouse));
                        FileUtil.saveString(LogInActivity.this, Constants.ACCESSTOKEN, Constants.outHouse.getResult().getOauth().getAccessToken());
                        if (Constants.outHouse.getResult().getHouses() != null && Constants.outHouse.getResult().getHouses().size() > 0) {
                            int houseid = FileUtil.loadInt(LogInActivity.this, Constants.SP_HOUSE_ID, 0);
                            House house = Constants.outHouse.getResult().getHouses().get(houseid);
                            Constants.house = house;
                            FileUtil.saveString(LogInActivity.this, Constants.SP_HOUSE, gson.toJson(house));
                            FileUtil.saveString(LogInActivity.this, Constants.SP_ROLE, house.getRole());


                            Set<String> tags = new HashSet<String>();
                            tags.add(Constants.house.getGateways().get(0).getExtid());
                            JPushInterface.setAliasAndTags(LogInActivity.this, null, tags, new TagAliasCallback() {
                                @Override
                                public void gotResult(int i, String s, Set<String> set) {

                                }
                            });
                            getLogIn();
                        } else {
                            getGoMipca();
                        }

                    } else {
                        loadingDialog.dismiss();
                        AppUtils.show(getApplication(), Constants.outHouse.getMessage());
                    }
                }

            }

            @Override
            public void onFailure(Call<OutModel_PersonalInForMation> call, Throwable t) {
                loadingDialog.dismiss();
                System.out.println("---aaaaa--failure--bbbbb---" + t.getMessage());
            }
        });

    }

    /**
     * 绑定房屋请求
     *
     * @param code
     */
    private void getBindCodeData(String code) {
        loadingDialog.show();
        BindCodeGinseng bindCodeGinseng = new BindCodeGinseng();
        bindCodeGinseng.setCode(code);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HttpUtils.HOSTUTIL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(RetrofitAPIManager.getOkHttpClient(getApplicationContext()))
                .build();

        HttpInterface httpInterface = retrofit.create(HttpInterface.class);

        Call<OutModel_Binding> call = httpInterface.getBindCode("smart/v1/Account/Scan?accessToken=" + FileUtil.loadString(LogInActivity.this, Constants.ACCESSTOKEN), bindCodeGinseng);

        call.enqueue(new Callback<OutModel_Binding>() {
            @Override
            public void onResponse(Call<OutModel_Binding> call, Response<OutModel_Binding> response) {
                loadingDialog.dismiss();
                OutModel_Binding outModel_binding = response.body();
                if (outModel_binding.getCode() == 200) {
                    Constants.house = outModel_binding.getResult();
                    FileUtil.saveString(LogInActivity.this, Constants.SP_HOUSE, gson.toJson(Constants.house));
                    FileUtil.saveString(LogInActivity.this, Constants.SP_ROLE, Constants.house.getRole());
                    Set<String> tags = new HashSet<String>();
                    tags.add(Constants.house.getGateways().get(0).getExtid());
                    JPushInterface.setAliasAndTags(LogInActivity.this, null, tags, new TagAliasCallback() {
                        @Override
                        public void gotResult(int i, String s, Set<String> set) {

                        }
                    });
                    getLogIn();
                } else {
                    AppUtils.show(LogInActivity.this, "绑定房屋失败");
                }
                System.out.println("-----binding--------" + outModel_binding.toString());
            }

            @Override
            public void onFailure(Call<OutModel_Binding> call, Throwable t) {
                loadingDialog.dismiss();
                AppUtils.show(LogInActivity.this, "绑定房屋失败");
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }


    @Override
    public void onSizeChange(boolean paramBoolean, int w, int h) {
        if (paramBoolean) {// 键盘弹出时
            DisplayMetrics mDisplayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
            int W = mDisplayMetrics.widthPixels;
            int H = mDisplayMetrics.heightPixels;
            if (H >= 1910) {
                layout_out.setPadding(0, -400, 0, 0);
            } else {
                layout_out.setPadding(0, -300, 0, 0);
            }

        } else { // 键盘隐藏时
            layout_out.setPadding(0, 0, 0, 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_MAIN:
                    break;
                case REQUEST_CODE_REGIST:
                    break;
                case REQUEST_CODE_RESET:
                    break;
                case REQUEST_CODE_SCAN:
                    String code = data.getStringExtra("CODE");
                    getBindCodeData(code);
                    break;
            }
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
                getGoMipca();
            } else {
                AppUtils.show(this, "请在应用管理中打开“相机”访问权限！");
            }
        }
    }
}
