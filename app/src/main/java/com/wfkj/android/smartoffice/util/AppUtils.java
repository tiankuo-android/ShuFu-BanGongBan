package com.wfkj.android.smartoffice.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by wangdongyang on 16/11/25.
 * 封装的全局的方法
 */
public class AppUtils {


    /**
     * 弹出Toast
     */
    private static Toast mToast;

    public static void show(Context ctx, String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(ctx, msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
        }
        mToast.show();
    }

    public static void show(Context ctx, String msg, int millisecond) {
        if (mToast == null) {
            mToast = Toast.makeText(ctx, msg, millisecond);
        } else {
            mToast.setText(msg);
        }
        mToast.show();
    }

    public static String getWeek(String dateStr) {
        final String dayNames[] = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五",
                "星期六"};
        SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        try {
            date = sdfInput.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek < 0) dayOfWeek = 0;
        return dayNames[dayOfWeek];
    }

    public static String getWeekL(String dateStr) {
        final String dayNames[] = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五",
                "星期六"};
        SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        try {
            date = sdfInput.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek < 0) dayOfWeek = 0;
        return dayNames[dayOfWeek];
    }

//    public static void upDateHouse(JSONObject jsonObject) {
//        try {
//            for (Room room : Constants.house.getRooms()) {
////                    for (Equipment equipment : room.getEquipments()) {
////                        equipment.setNumber(jsonObject.get(equipment.getTag()).toString());
////                    }
//                if (room.getLights() != null && room.getLights().size() > 0) {
//                    for (Light light : room.getLights()) {
//
//                        light.setSwitchState((Boolean) jsonObject.get(light.getDataPoint()));
//
//                    }
//                }
//
//
//                if (room.getColorLights() != null && room.getColorLights().size() > 0) {
//                    for (ColorLight colorLight : room.getColorLights()) {
//                        colorLight.setSwitchState((Boolean) jsonObject.get(colorLight.getDataPoint()));
//                    }
//                }
//
//
//                Temperature temperature = room.getTemperature();
//                if (temperature != null) {
//                    temperature.setModleNumber(jsonObject.get(temperature.getDataModleNumber()) + "");
//                }
//
//                Humidity humidity = room.getHumidity();
//                if (humidity != null) {
//                    humidity.setModleNumber(jsonObject.get(humidity.getDataModleNumber()) + "");
//                }
//                if (room.getAirClean() != null) {
//
//                    AirClean airClean = room.getAirClean();
//                    airClean.setModbus_co2(jsonObject.get(airClean.getDataModbus_co2()) + "");
//                    airClean.setModbus_HCHO(jsonObject.get(airClean.getDataModbus_HCHO()) + "");
//                    airClean.setModbus_pm25(jsonObject.get(airClean.getDataModbus_pm25()) + "");
//                    airClean.setModbus_voc(jsonObject.get(airClean.getDataModbus_voc()) + "");
//                }
//
//            }
//
//            System.out.println("-------apputils------" + new Gson().toJson(Constants.house));
//
////            PersonalInformation personalInformation = Constants.outHouse.getResult();
////            if (personalInformation.getHouses() != null && personalInformation.getHouses().size() >0) {
////                com.wfkj.android.smartoffice.model.sever_model.House house = personalInformation.getHouses().get(0);
////                if (house.getRooms() != null && house.getRooms().size() > 0) {
////                    for (com.wfkj.android.smartoffice.model.sever_model.Room room : house.getRooms()) {
////                        if (room.getCategories() != null && room.getCategories().size() > 0){
////                            for (Categorie categorie : room.getCategories()) {
////
////                            }
////                        }
////                    }
////                }
////            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * 获取系统sdk版本
     *
     * @return
     */
    public static int getAndroidOSVersion() {
        int osVersion;
        try {
            osVersion = Integer.valueOf(android.os.Build.VERSION.SDK);
        } catch (NumberFormatException e) {
            osVersion = 0;
        }

        return osVersion;
    }


    /**
     * 获取手机型号
     *
     * @return
     */
    public static String getPhoneModel() {
        String osVersion;

        osVersion = Build.MODEL;

        return osVersion;
    }

    /**
     * 获取系统版本
     *
     * @return
     */
    public static String getSystemVersion() {
        String osVersion;

        osVersion = Build.VERSION.RELEASE;

        return osVersion;
    }

    /**
     * getIMEI 获取手机IMEI号码
     *
     * @param context
     * @return
     */
    public static String getIMEI(Context context) {
        TelephonyManager mTelephonyMgr = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String imei = mTelephonyMgr.getDeviceId();
        return imei == null ? "" : imei;
    }

    /**
     * getIMSI 获取手机IMSI号码
     *
     * @param context
     * @return
     */
    public static String getIMSI(Context context) {
        TelephonyManager mTelephonyMgr = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
//		String imsi="";
//		System.out.println("-----"+mTelephonyMgr.getSimState());
//		if (mTelephonyMgr.getSimState()==5) {
//			imsi = mTelephonyMgr.getSubscriberId();
//
//		}else {
//			imsi = "";
//		}
        String imsi = mTelephonyMgr.getSubscriberId();

        return imsi == null ? "" : imsi;
    }

    /**
     * getAppVersion 获取当前版本
     *
     * @param context
     * @return
     */
    public static String getAppVersion(Context context) {

        String appVersion = null;

        PackageManager manager = context.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(),
                    0);
            appVersion = info.versionName; // 版本名
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return appVersion;
    }

    /**
     * getAppVersionCode 获取当前版本Code
     *
     * @param context
     * @return
     */
    public static int getAppVersionCode(Context context) {

        int appVersion = 0;

        PackageManager manager = context.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(),
                    0);
            appVersion = info.versionCode; // 版本名
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return appVersion;
    }

    public static String toUtf8(String str) {
        String result = null;
        try {
            result = new String(str.getBytes("UTF-8"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }


    public static String getMd5(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            //32位加密
            return buf.toString();
            // 16位的加密
            //return buf.toString().substring(8, 24);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {

        }
    }

}
