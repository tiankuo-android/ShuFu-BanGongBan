package com.wfkj.android.smartoffice.my_interface;

/**
 * Created by wangdongyang on 16/11/24.
 */
public interface LightperatureFragmentAdapterUpDate {

    /**
     * roomPosition  第几个房间
     *deviceId 设备id
     * @param flg 1-普通灯  2-彩灯  3-窗帘 4-窗户
     * @param tag 1-关    2-开  3-自动 4-关暂停   5-开暂停
     *            number 亮度值
     * @param colorNumber 颜色值
     */
    void upDate(int roomPosition, int deviceId, int flg, int tag, int number, int colorNumber);

}
