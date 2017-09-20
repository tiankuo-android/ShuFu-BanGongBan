package com.wfkj.android.smartoffice.util.view_util;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

/**
 * Created by wangdongyang on 16/12/5.
 */
public class MyCount extends CountDownTimer {
    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */

    private TextView time,verification;
    public MyCount(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }
    public MyCount(TextView time,TextView verification,long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.time=time;
        this.verification=verification;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        time.setText(millisUntilFinished / 1000+"s");
    }

    @Override
    public void onFinish() {
        time.setText("");
        time.setVisibility(View.INVISIBLE);
        verification.setVisibility(View.VISIBLE);

    }
}
