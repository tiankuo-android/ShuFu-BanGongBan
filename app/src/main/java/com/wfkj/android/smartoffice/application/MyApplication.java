package com.wfkj.android.smartoffice.application;

import android.app.Application;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.gizwits.gizwifisdk.api.GizWifiSDK;
import com.wfkj.android.smartoffice.receiver.ConnectionChangeReceiver;
import com.wfkj.android.smartoffice.util.Constants;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by wangdongyang on 16/11/17.
 */
public class MyApplication extends Application {


    private ConnectionChangeReceiver connectionChangeReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        // 初始化sdk,传入appId,登录机智云官方网站查看产品信息获得 AppID
        Fresco.initialize(this);
        GizWifiSDK.sharedInstance().startWithAppID(getApplicationContext(),
                Constants.APPID);
        // 设定日志打印级别,日志保存文件名，是否在后台打印数据.
//        GizWifiSDK.sharedInstance().setLogLevel(GizLogPrintLevel.GizLogPrintAll);

        IntentFilter filter = new IntentFilter(
                ConnectivityManager.CONNECTIVITY_ACTION);
        connectionChangeReceiver = new ConnectionChangeReceiver();
        this.registerReceiver(connectionChangeReceiver, filter);
    }

}
