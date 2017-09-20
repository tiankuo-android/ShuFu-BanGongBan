package com.wfkj.android.smartoffice.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.wfkj.android.smartoffice.util.AppUtils;

/**
 * Created by wangdongyang on 16/11/25.
 */
public class ConnectionChangeReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        ConnectivityManager connectivityManager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobNetInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo  wifiNetInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//	        if (mInflater!=null) {
//	        	mInflater = LayoutInflater.from(context).inflate(R.layout.my_toast_center, null);
//			}
        if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
            // 子线程
//	        	toastCommom = ToastCommom.createToastConfig();
//	        	toastCommom.ToastShow(context, (ViewGroup) mInflater.findViewById(R.id.mytoast_layout), "网络异常，请检查网络设置", 3000);
//            Toast.makeText(context, "网络异常，请检查网络设置", Toast.LENGTH_SHORT).show();
            AppUtils.show(context,"网络异常，请检查网络设置");
//            new Thread(){
//                public void run() {
//                    EventBus.getDefault().post(new MsgEvent1("1"));
//                };
//            }.start();
        }else {
//	        	Toast.makeText(context, "有网了", Toast.LENGTH_SHORT).show();
//            new Thread(){
//                public void run() {
//                    EventBus.getDefault().post(new MsgEvent1("2"));
//                };
//            }.start();
        }
    }

}
