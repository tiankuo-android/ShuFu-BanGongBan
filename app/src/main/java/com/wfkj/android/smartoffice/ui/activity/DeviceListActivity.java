package com.wfkj.android.smartoffice.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.wfkj.android.smartoffice.R;

/**
 *
 * 设备列表
 * Created by wangdongyang on 17/2/13.
 */
public class DeviceListActivity extends Activity implements OnClickListener{

    private ImageView head_back;
    private TextView head_title;
    private ImageView head_img;
    private ListView device_list_listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);
        init();
    }

    private void init(){
        head_back = (ImageView) this.findViewById(R.id.head_back);
        head_title = (TextView) this.findViewById(R.id.head_title);
        head_img = (ImageView) this.findViewById(R.id.head_img);
        device_list_listview = (ListView) this.findViewById(R.id.device_list_listview);

        head_title.setText("设备列表");
        head_img.setVisibility(View.GONE);
        head_back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.head_back:
                this.finish();
                break;
        }

    }
}
