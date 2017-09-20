package com.wfkj.android.smartoffice.util;

/**
 * Created by wangdongyang on 17/2/8.
 */
public class EventBus_Account {

    public final int tag;
    public final int number;
    public final String address;

    public EventBus_Account(int tag) {
        this.tag = tag;
        number = 0;
        address = "";
    }

    public EventBus_Account(int tag, int number) {
        this.tag = tag;
        this.number = number;
        address = null;
    }

    public EventBus_Account(int tag, String address) {
        this.tag = tag;
        number = 0;
        this.address = address;
    }
}
