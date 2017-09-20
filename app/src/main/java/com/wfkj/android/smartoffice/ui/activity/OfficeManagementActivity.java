package com.wfkj.android.smartoffice.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wfkj.android.smartoffice.R;
import com.wfkj.android.smartoffice.adapter.OfficeManagementAdapter;
import com.wfkj.android.smartoffice.model.sever_model.OutModel_PersonalInForMation;
import com.wfkj.android.smartoffice.my_interface.ManagementInterface;
import com.wfkj.android.smartoffice.util.Constants;
import com.wfkj.android.smartoffice.util.FileUtil;

/**
 * Created by wangdongyang on 17/2/7.
 */
public class OfficeManagementActivity extends Activity implements OnClickListener {

    private Gson gson = new Gson();

    private ImageView head_back;
    private TextView head_title;
    private ImageView head_img;

    private ListView office_management_listview;
    private OfficeManagementAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_office_management);
        init();
    }

    private void init() {
        head_back = (ImageView) this.findViewById(R.id.head_back);
        head_title = (TextView) this.findViewById(R.id.head_title);
        head_img = (ImageView) this.findViewById(R.id.head_img);
        office_management_listview = (ListView) this.findViewById(R.id.office_management_listview);

        head_back.setOnClickListener(this);
        head_title.setText("房屋管理");
        head_img.setVisibility(View.GONE);

        adapter = new OfficeManagementAdapter(OfficeManagementActivity.this, Constants.outHouse.getResult().getHouses(), new ManagementInterface() {
            @Override
            public void getClick(int tag, String id, String name) {
                Intent intent = new Intent();
                intent.setClass(OfficeManagementActivity.this, ModifyActivity.class);
                intent.putExtra("TAG", tag);
                intent.putExtra("ID", id);
                intent.putExtra("NAME", name);
                startActivityForResult(intent, 100);
            }
        });
        office_management_listview.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.head_back:
                this.finish();
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 111:
            case 112:
                if (Constants.outHouse == null) {
                    Constants.outHouse = gson.fromJson(FileUtil.loadString(OfficeManagementActivity.this, Constants.SP_OUTHOUSE), OutModel_PersonalInForMation.class);
                }
                adapter.notifyDataSetChanged();
                break;
        }
    }
}
