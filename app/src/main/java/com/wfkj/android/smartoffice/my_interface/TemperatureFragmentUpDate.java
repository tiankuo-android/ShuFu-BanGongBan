package com.wfkj.android.smartoffice.my_interface;

/**
 * Created by wangdongyang on 16/11/24.
 */
public interface TemperatureFragmentUpDate {
    /**
     *
     * @param flg 1-温度  2-湿度
     * @param tag 1-加    2-减
     * @param number 温度或者湿度值
     */
    void upDate(int position,int flg,int tag,int number);

    /**
     *
     * @param flg 1-温度 2-湿度
     * @param tag
     */
    void upDateBtn(int position,int flg,int tag);

}
