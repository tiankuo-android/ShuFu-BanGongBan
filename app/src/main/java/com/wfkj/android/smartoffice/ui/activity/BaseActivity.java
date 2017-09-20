package com.wfkj.android.smartoffice.ui.activity;

import android.app.Activity;
import android.os.Bundle;

//import com.gizwits.gizwifisdk.api.GizSchedulerInfo;
import com.gizwits.gizwifisdk.api.GizUserInfo;
import com.gizwits.gizwifisdk.api.GizWifiDevice;
import com.gizwits.gizwifisdk.api.GizWifiGroup;
import com.gizwits.gizwifisdk.api.GizWifiSSID;
import com.gizwits.gizwifisdk.enumration.GizEventType;
import com.gizwits.gizwifisdk.enumration.GizScheduleStatus;
import com.gizwits.gizwifisdk.enumration.GizWifiDeviceNetStatus;
import com.gizwits.gizwifisdk.enumration.GizWifiErrorCode;
import com.gizwits.gizwifisdk.listener.GizWifiDeviceListener;
import com.gizwits.gizwifisdk.listener.GizWifiSDKListener;
import com.wfkj.android.smartoffice.util.jzy_util.CmdCenter;
import com.wfkj.android.smartoffice.util.jzy_util.SettingManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by wangdongyang on 16/11/17.
 */
public class BaseActivity extends Activity {

    /**
     * 设备列表.
     */
    protected static List<GizWifiDevice> deviceslist = new ArrayList<GizWifiDevice>();

    /**
     * 指令管理器.
     */
    public CmdCenter mCenter;
    /**
     * SharePreference处理类.
     */
    public SettingManager setmanager;

    /** 当前操作的设备 */
    public static GizWifiDevice mXpgWifiDevice;

//    @Override
//    protected void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
//        super.onCreate(savedInstanceState, persistentState);
//        setmanager = new SettingManager(getApplicationContext());
//        mCenter = CmdCenter.getInstance(getApplicationContext());
//        // 每次返回activity都要注册一次sdk监听器，保证sdk状态能正确回调
//        mCenter.getGizWifiSDK().setListener(sdkListener);
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setmanager = new SettingManager(getApplicationContext());
        mCenter = CmdCenter.getInstance(getApplicationContext());
        // 每次返回activity都要注册一次sdk监听器，保证sdk状态能正确回调
        mCenter.getGizWifiSDK().setListener(sdkListener);

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    GizWifiDeviceListener deviceListener2;

    /**
     * GizWifiDeviceListener
     * <p/>
     * 设备属性监听器。 设备连接断开、获取绑定参数、获取设备信息、控制和接受设备信息相关.
     */
    protected GizWifiDeviceListener deviceListener = new GizWifiDeviceListener() {

        @Override
        public void didExitProductionTesting(GizWifiErrorCode result, GizWifiDevice device) {
            BaseActivity.this.didExitProductionTesting(result, device);
        }

        @Override
        public void didReceiveData(GizWifiErrorCode result, GizWifiDevice device, ConcurrentHashMap<String, Object> dataMap, int sn) {
            BaseActivity.this.didReceiveData(result, device, dataMap, sn);
        }

        @Override
        public void didSetSubscribe(GizWifiErrorCode result, GizWifiDevice device, boolean isSubscribed) {
            BaseActivity.this.didSetSubscribe(result, device, isSubscribed);
        }

        @Override
        public void didGetHardwareInfo(GizWifiErrorCode result, GizWifiDevice device, ConcurrentHashMap<String, String> hardwareInfo) {
            BaseActivity.this.didGetHardwareInfo(result, device, hardwareInfo);
        }

        @Override
        public void didUpdateNetStatus(GizWifiDevice device, GizWifiDeviceNetStatus netStatus) {
            BaseActivity.this.didUpdateNetStatus(device, netStatus);
        }

        @Override
        public void didSetCustomInfo(GizWifiErrorCode result, GizWifiDevice device) {
            BaseActivity.this.didSetCustomInfo(result, device);
        }

    };



    /**
     * GizWifiSDKListener
     * <p>
     * sdk监听器。 配置设备上线、注册登录用户、搜索发现设备、用户绑定和解绑设备相关.
     */
    private GizWifiSDKListener sdkListener = new GizWifiSDKListener() {

        @Override
        public void didGetCurrentCloudService(GizWifiErrorCode result, ConcurrentHashMap<String, String> cloudServiceInfo) {
            BaseActivity.this.didGetCurrentCloudService(result, cloudServiceInfo);
        }

        @Override
        public void didGetUserInfo(GizWifiErrorCode result, GizUserInfo userInfo) {
            BaseActivity.this.didGetUserInfo(result, userInfo);
        }

        @Override
        public void didDisableLAN(GizWifiErrorCode result) {
            BaseActivity.this.didDisableLAN(result);
        }

        @Override
        public void didChannelIDBind(GizWifiErrorCode result) {
            BaseActivity.this.didChannelIDBind(result);
        }

        @Override
        public void didChannelIDUnBind(GizWifiErrorCode result) {
            BaseActivity.this.didChannelIDUnBind(result);
        }

        @Override
        public void didCreateScheduler(GizWifiErrorCode result, String sid) {
            BaseActivity.this.didCreateScheduler(result, sid);
        }

        @Override
        public void didDeleteScheduler(GizWifiErrorCode result) {
            BaseActivity.this.didDeleteScheduler(result);
        }

//        @Override
//        public void didGetSchedulers(GizWifiErrorCode result, List<GizSchedulerInfo> scheduleTaskList) {
//            BaseActivity.this.didGetSchedulers(result, scheduleTaskList);
//        }

        @Override
        public void didGetSchedulerStatus(GizWifiErrorCode result, String sid, String datetime, GizScheduleStatus status, ConcurrentHashMap<String, Boolean> statusDetail) {
            BaseActivity.this.didGetSchedulerStatus(result, sid, datetime, status, statusDetail);
        }

        @Override
        public void didBindDevice(GizWifiErrorCode result, String did) {
            BaseActivity.this.didBindDevice(result, did);
        }

        @Override
        public void didUnbindDevice(GizWifiErrorCode result, String did) {
            BaseActivity.this.didUnbindDevice(result, did);
        }

        @Override
        public void didChangeUserInfo(GizWifiErrorCode result) {
            BaseActivity.this.didChangeUserInfo(result);
        }

        @Override
        public void didTransAnonymousUser(GizWifiErrorCode result) {
            BaseActivity.this.didTransAnonymousUser(result);
        }

        @Override
        public void didUpdateProduct(GizWifiErrorCode result, String productKey, String productUI) {
            BaseActivity.this.didUpdateProduct(result, productKey, productUI);
        }

        @Override
        public void didDiscovered(GizWifiErrorCode result, List<GizWifiDevice> deviceList) {
            BaseActivity.this.didDiscovered(result, deviceList);
        }


        @Override
        public void didUserLogin(GizWifiErrorCode result, String uid, String token) {
            BaseActivity.this.didUserLogin(result, uid, token);
        }

        @Override
        public void didNotifyEvent(GizEventType eventType, Object eventSource, GizWifiErrorCode eventID, String eventMessage) {
            BaseActivity.this.didNotifyEvent(eventType, eventSource, eventID, eventMessage);
        }

        @Override
        public void didSetDeviceOnboarding(GizWifiErrorCode result, String mac, String did, String productKey) {
            BaseActivity.this.didSetDeviceOnboarding(result, mac, did, productKey);
        }

        @Override
        public void didGetSSIDList(GizWifiErrorCode result, List<GizWifiSSID> ssidInfoList) {
            BaseActivity.this.didGetSSIDList(result, ssidInfoList);
        }

        @Override
        public void didGetCaptchaCode(GizWifiErrorCode result, String token, String captchaId, String captchaURL) {
            BaseActivity.this.didGetCaptchaCode(result, token, captchaId, captchaURL);
        }

        @Override
        public void didVerifyPhoneSMSCode(GizWifiErrorCode result) {
            BaseActivity.this.didVerifyPhoneSMSCode(result);
        }

        @Override
        public void didRegisterUser(GizWifiErrorCode result, String uid, String token) {
            BaseActivity.this.didRegisterUser(result, uid, token);
        }

        @Override
        public void didChangeUserPassword(GizWifiErrorCode result) {
            BaseActivity.this.didChangeUserPassword(result);
        }

        @Override
        public void didGetGroups(GizWifiErrorCode result, List<GizWifiGroup> groupList) {
            BaseActivity.this.didGetGroups(result, groupList);
        }

//        public void didReceiveData(GizWifiErrorCode result, GizWifiDevice device, ConcurrentHashMap<String, Object> dataMap, int sn) {
//            BaseActivity.this.didReceiveData(result, device, dataMap, sn);
//        }

    };


    protected void didGetUserInfo(GizWifiErrorCode result, GizUserInfo userInfo) {

    }

    protected void didDisableLAN(GizWifiErrorCode result) {

    }

    protected void didChannelIDBind(GizWifiErrorCode result) {

    }

    protected void didChannelIDUnBind(GizWifiErrorCode result) {

    }

    protected void didCreateScheduler(GizWifiErrorCode result, String sid) {

    }

    protected void didDeleteScheduler(GizWifiErrorCode result) {

    }

//    protected void didGetSchedulers(GizWifiErrorCode result, List<GizSchedulerInfo> scheduleTaskList) {
//
//    }

    protected void didGetSchedulerStatus(GizWifiErrorCode result, String sid, String datetime, GizScheduleStatus status, ConcurrentHashMap<String, Boolean> statusDetail) {

    }

    protected void didChangeUserInfo(GizWifiErrorCode result) {

    }

    protected void didTransAnonymousUser(GizWifiErrorCode result) {

    }

    protected void didNotifyEvent(GizEventType eventType, Object eventSource, GizWifiErrorCode eventID, String eventMessage) {

    }

    protected void didSetDeviceOnboarding(GizWifiErrorCode result, String mac, String did, String productKey) {

    }

    protected void didGetCaptchaCode(GizWifiErrorCode result, String token, String captchaId, String captchaURL) {

    }

    protected void didVerifyPhoneSMSCode(GizWifiErrorCode result) {

    }

    protected void didGetCurrentCloudService(GizWifiErrorCode result, ConcurrentHashMap<String, String> cloudServiceInfo) {

    }

    protected void didGetGroups(GizWifiErrorCode result, List<GizWifiGroup> groupList) {
        // TODO Auto-generated method stub

    }

    /**
     * 用户登陆回调接口.
     */
    protected void didUserLogin(GizWifiErrorCode result, String uid, String token) {

    }

    /**
     * 设备解除绑定回调接口.
     */
    protected void didUnbindDevice(GizWifiErrorCode result, String did) {

    }


    /**
     * 注册用户结果回调接口.
     */
    protected void didRegisterUser(GizWifiErrorCode result, String uid, String token) {
        // TODO Auto-generated method stub

    }

    /**
     * 获取ssid列表回调接口.
     */
    protected void didGetSSIDList(GizWifiErrorCode result, List<GizWifiSSID> ssidInfoList) {
        // TODO Auto-generated method stub

    }

    /**
     * 搜索设备回调接口.
     */
    protected void didDiscovered(GizWifiErrorCode result, List<GizWifiDevice> deviceList) {
        // TODO Auto-generated method stub

    }


    /**
     * 更换密码回调接口.
     */
    protected void didChangeUserPassword(GizWifiErrorCode result) {
        // TODO Auto-generated method stub
    }


    /**
     * 绑定设备结果回调.
     */
    protected void didBindDevice(GizWifiErrorCode result, String did) {

    }

    /**
     * 接收指令回调
     * <p>
     * sdk接收到模块传入的数据回调该接口.
     *
     * @param device  设备对象
     * @param dataMap json数据表
     * @param result  状态代码
     */
    protected void didReceiveData(GizWifiErrorCode result, GizWifiDevice device, final ConcurrentHashMap<String, Object> dataMap, int sn) {
    }


    protected void didUpdateProduct(GizWifiErrorCode result, String productKey, String productUI) {
    }

    protected void didExitProductionTesting(GizWifiErrorCode result, GizWifiDevice device) {

    }

    protected void didSetSubscribe(GizWifiErrorCode result, GizWifiDevice device, boolean isSubscribed) {

    }
    protected void didGetHardwareInfo(GizWifiErrorCode result, GizWifiDevice device, ConcurrentHashMap<String, String> hardwareInfo) {

    }

    protected void didUpdateNetStatus(GizWifiDevice device, GizWifiDeviceNetStatus netStatus) {

    }
    protected void didSetCustomInfo(GizWifiErrorCode result, GizWifiDevice device) {

    }

}
