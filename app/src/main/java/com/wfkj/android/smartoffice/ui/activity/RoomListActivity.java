package com.wfkj.android.smartoffice.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.wfkj.android.smartoffice.R;

/**
 * Created by wangdongyang on 2017/5/15.
 */

public class RoomListActivity extends BaseActivity implements OnClickListener{

    private JsonObject jsonObject = null;

    private ImageView head_back;
    private TextView head_title;
    private ImageView head_img;

    private ListView room_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init(){
        head_back = (ImageView) this.findViewById(R.id.head_back);
        head_title = (TextView) this.findViewById(R.id.head_title);
        head_img = (ImageView) this.findViewById(R.id.head_img);
        room_list = (ListView) this.findViewById(R.id.room_list);

        head_back.setOnClickListener(this);
        head_title.setText("房屋列表");
        head_img.setVisibility(View.GONE);

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
