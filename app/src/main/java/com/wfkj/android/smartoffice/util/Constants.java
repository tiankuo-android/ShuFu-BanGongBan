package com.wfkj.android.smartoffice.util;

import com.wfkj.android.smartoffice.model.sever_model.House;
import com.wfkj.android.smartoffice.model.sever_model.OutModel_PersonalInForMation;

/**
 * Created by wangdongyang on 16/11/17.
 */
public class Constants {

    //静态常量
    public static final String APP_NAME = "smartOffice";


    public static final int EVENT_BUS_MODIFY_PERSONAL_INFORMATION = 111;//修改个人信息的通知
    public static final int EVENT_BUS_MODIFY_PERSONAL_HOUSE = 112;//修改房屋信息的通知
    public static final int EVENT_BUS_MODIFY_PERSONAL_REMARKS = 113;//修改个人信息的通知
    public static final int EVENT_BUS_MODIFY_PERSONAL_DELETE_IMG_ID = 114;//删除图片

    /**
     * sp存储 键
     */

    //个人信息
    public static final String SP_USER_TOKEN = "sp_user_token";//登录成功获取到的token
    public static final String SP_APPID = "sp_appid";//房屋对应的机制云appid
    public static final String SP_APPSECRET = "sp_appsecret";//房屋对应的机制云appsecret
    public static final String SP_APP_PRODUCT_KEY = "ap_app_product-key";
    public static final String SP_APP_PRODUCT_SECRET = "sp_app_reoduct_secret";
    public static final String SP_ROLE = "sp_sole";//权限

    public static final String SP_OUTHOUSE = "sp_outhouse";
    public static final String SP_HOUSE = "sp_house";
    public static final String SP_HOUSE_ID = "sp_house_id";


    public static final String SP_UID = "uid";
    public static final String SP_TOKEN = "token";
    public static final String SP_WEATHER = "weather";
    public static final String SP_NUMBER_TEMPERATURE = "sp_number_temperature";//记录温度数值

    public static final String SP_GOTOWORK = "sp_gotowork";//上下班记录


    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String HOUSEJSON = "housejson";
    public static final String JZYSTR = "jzystr";
    public static final String ACCESSTOKEN = "accesstoken";


    public static final String HeWeatherApiKey = "c45d91706110494d81b7dc282cca1076";

    /*******************************************************************************/


    /**
     * 设定AppID，参数为机智云官网中查看产品信息得到的AppID.
     */
    public static final String APPID = "b0e606d389cf4ca58c39261f83523695";

    public static final String AppSerret = "1d7ab2f535bc49dca1461b079caf12e4";
//    public static final String AppSerret = "6934edc9e1534a059b9f657f3879d912";

    public static final String UserNameEnd = "wangfu";
    public static final String Password = "12345678wangfu";

    public static final String UID = "GizwitzUID";
    public static final String TOKEN = "GizwitzToken  ";

    /**
     * 指定该app对应设备的product_key，如果设定了过滤，会过滤出该peoduct_key对应的设备.
     */
    public static final String PRODUCT_KEY = "f88edfe309694f8db7a66085e841dcc5";

    public static final String PRODUCT_SECRET = "73a11bd7c8ae435eaf3d2e97a8e68fbc";

//    public static final String PRODUCT_KEY = "f9283b5fb5d44f12bd2b970511d3b14a";
    /*******************************************************************************/


//    public static final String hoseJsonStr = "{\"name\":\"清华大学研究室\",\"rooms\":[{\"equipments\":[{\"number\":\"0\",\"room\":\"1\",\"tag\":\"modbus_air_sensor_pm25\",\"title\":\"pm2.5监测\",\"type\":\"2\"},{\"number\":\"0\",\"room\":\"1\",\"tag\":\"modbus_air_sensor_co2\",\"title\":\"co2监测\",\"type\":\"2\"},{\"number\":\"0\",\"room\":\"1\",\"tag\":\"modbus_air_sensor_voc\",\"title\":\"voc监测\",\"type\":\"2\"},{\"number\":\"0\",\"room\":\"1\",\"tag\":\"modbus_temperature_sensor\",\"title\":\"温度监测\",\"type\":\"2\"},{\"number\":\"0\",\"room\":\"1\",\"tag\":\"modbus_humidity_sensor\",\"title\":\"湿度监测\",\"type\":\"2\"},{\"number\":\"0\",\"room\":\"1\",\"tag\":\"modbus_air_sensor_HCHO\",\"title\":\"甲醛监测\",\"type\":\"2\"}],\"name\":\"研究室\",\"tag\":\"1\"}]}";
//    public static final String hoseJsonStr = "{\"name\":\"清华大学研究室\",\"rooms\":[{\"equipments\":[{\"number\":\"0\",\"room\":\"1\",\"tag\":\"modbus_pm25_sensor\",\"title\":\"pm2.5监测\",\"type\":\"2\"},{\"number\":\"0\",\"room\":\"1\",\"tag\":\"modbus_co2_sensor\",\"title\":\"co2监测\",\"type\":\"2\"},{\"number\":\"0\",\"room\":\"1\",\"tag\":\"modbus_voc_sensor\",\"title\":\"voc监测\",\"type\":\"2\"},{\"number\":\"0\",\"room\":\"1\",\"tag\":\"modbus_temperature_sensor\",\"title\":\"温度监测\",\"type\":\"2\"},{\"number\":\"0\",\"room\":\"1\",\"tag\":\"modbus_humidity_sensor\",\"title\":\"湿度监测\",\"type\":\"2\"},{\"number\":\"0\",\"room\":\"1\",\"tag\":\"modbus_HCHO_sensor\",\"title\":\"甲醛监测\",\"type\":\"2\"},{\"number\":\"0\",\"room\":\"1\",\"tag\":\"modbus_pressure_sensor\",\"title\":\"压差\",\"type\":\"2\"}],\"name\":\"研究室\",\"tag\":\"1\"}]}";public static final String hoseJsonStr = "{\"name\":\"清华大学研究室\",\"rooms\":[{\"equipments\":[{\"number\":\"0\",\"room\":\"1\",\"tag\":\"modbus_pm25_sensor\",\"title\":\"pm2.5监测\",\"type\":\"2\"},{\"number\":\"0\",\"room\":\"1\",\"tag\":\"modbus_co2_sensor\",\"title\":\"co2监测\",\"type\":\"2\"},{\"number\":\"0\",\"room\":\"1\",\"tag\":\"modbus_voc_sensor\",\"title\":\"voc监测\",\"type\":\"2\"},{\"number\":\"0\",\"room\":\"1\",\"tag\":\"modbus_temperature_sensor\",\"title\":\"温度监测\",\"type\":\"2\"},{\"number\":\"0\",\"room\":\"1\",\"tag\":\"modbus_humidity_sensor\",\"title\":\"湿度监测\",\"type\":\"2\"},{\"number\":\"0\",\"room\":\"1\",\"tag\":\"modbus_HCHO_sensor\",\"title\":\"甲醛监测\",\"type\":\"2\"},{\"number\":\"0\",\"room\":\"1\",\"tag\":\"modbus_pressure_sensor\",\"title\":\"压差\",\"type\":\"2\"}],\"name\":\"研究室\",\"tag\":\"1\"}]}";
    public static final String hoseJsonStr = "{\"address\":\"北京－海淀－中关村创业大厦\",\"houseID\":\"10\",\"houseName\":\"中关村创业大厦\",\"rooms\":[{\"airClean\":{\"dataModbus_HCHO\":\"modbus_air_sensor_HCHO\",\"dataModbus_co2\":\"modbus_air_sensor_co2\",\"dataModbus_pm25\":\"modbus_air_sensor_pm25\",\"dataModbus_voc\":\"modbus_air_sensor_voc\",\"event_HCHO\":\"event_air_sensor_HCHO\",\"event_co2\":\"event_air_sensor_co2\",\"event_pm25\":\"event_air_sensor_pm25\",\"event_voc\":\"event_air_sensor_voc\",\"id\":\"1500\",\"type\":\"1500\"},\"colorLights\":[{\"dataPoint\":\"zigbee_l304_led_lamp\",\"lightID\":\"1200\",\"lightName\":\"彩灯\",\"switchState\":false,\"type\":\"1200\"}],\"curtains\":[{\"curatinID\":\"1401\",\"curatinName\":\"办公室卷帘\",\"dataPointClose\":\"modbus_scroll_c304_close_window\",\"dataPointOpen\":\"modbus_scroll_o304_open_left_window\",\"type\":\"1400\"}],\"humidity\":{\"dataModleNumber\":\"modbus_humidity_sensor\",\"dataPointNumber\":\"event_humidity_sensor\",\"id\":\"1800\",\"name\":\"湿度控制\",\"type\":\"1800\"},\"lights\":[{\"dataPoint\":\"zigbee_d304_down_lamp\",\"lightID\":\"1004\",\"lightName\":\"筒灯\",\"switchState\":false,\"type\":\"1000\"}],\"roomID\":\"101\",\"roomName\":\"304室\",\"temperature\":{\"dataModleNumber\":\"modbus_temperature_sensor\",\"dataPointNumber\":\"event_temperature_sensor\",\"id\":\"1600\",\"name\":\"温度控制\",\"type\":\"1600\"}},{\"curtains\":[{\"curatinID\":\"1402\",\"curatinName\":\"会议室左卷帘\",\"dataPointClose\":\"modbus_scroll_c302_close_left_window\",\"dataPointOpen\":\"modbus_scroll_o302_open_left_window\",\"type\":\"1400\"},{\"curatinID\":\"1403\",\"curatinName\":\"会议室右卷帘\",\"dataPointClose\":\"modbus_scroll_c302_close_right_window\",\"dataPointOpen\":\"modbus_scroll_o302_open_right_window\",\"type\":\"1400\"}],\"lights\":[{\"dataPoint\":\"zigbee_doorway_down_lamp\",\"lightID\":\"1000\",\"lightName\":\"进门筒灯\",\"switchState\":false,\"type\":\"1000\"},{\"dataPoint\":\"zigbee_doorway_led_lamp\",\"lightID\":\"1001\",\"lightName\":\"进门LED灯\",\"switchState\":false,\"type\":\"1000\"},{\"dataPoint\":\"zigbee_l302_left_led_lamp\",\"lightID\":\"1002\",\"lightName\":\"左LED灯\",\"switchState\":false,\"type\":\"1000\"},{\"dataPoint\":\"zigbee_r302_right_led_lamp\",\"lightID\":\"1003\",\"lightName\":\"右LED灯\",\"switchState\":false,\"type\":\"1000\"}],\"roomID\":\"100\",\"roomName\":\"302室\"}],\"weatherAddarss\":\"haidian\"}";

    public static OutModel_PersonalInForMation outHouse;
    public static House house;


}
