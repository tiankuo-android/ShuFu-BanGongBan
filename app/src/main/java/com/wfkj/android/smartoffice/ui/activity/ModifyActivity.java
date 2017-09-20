package com.wfkj.android.smartoffice.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wfkj.android.smartoffice.R;
import com.wfkj.android.smartoffice.model.ginseng.Modify;
import com.wfkj.android.smartoffice.model.sever_model.OutModel_PersonalInForMation;
import com.wfkj.android.smartoffice.model.sever_model.UpDateHouse;
import com.wfkj.android.smartoffice.my_interface.HttpInterface;
import com.wfkj.android.smartoffice.util.AppUtils;
import com.wfkj.android.smartoffice.util.Constants;
import com.wfkj.android.smartoffice.util.EventBus_Account;
import com.wfkj.android.smartoffice.util.FileUtil;
import com.wfkj.android.smartoffice.util.HttpUtils;
import com.wfkj.android.smartoffice.util.http_util.RetrofitAPIManager;
import com.wfkj.android.smartoffice.util.view_util.LoadingDialog;

import de.greenrobot.event.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wangdongyang on 17/2/7.
 */
public class ModifyActivity extends Activity implements OnClickListener {

    private LoadingDialog loadingDialog;
    private Gson gson = new Gson();

    private Intent getIntent;
    private int tag;
    private String id;
    private String uid;

    private ImageView head_back;
    private TextView head_title;
    private ImageView head_img;
    private TextView head_submit;

    private EditText modify_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actviity_modify);
        loadingDialog = new LoadingDialog(ModifyActivity.this);
        init();
    }

    private void init() {
        getIntent = getIntent();
        tag = getIntent.getIntExtra("TAG", -1);
        id = getIntent.getStringExtra("ID");
        uid = getIntent.getStringExtra("UID");

        head_back = (ImageView) this.findViewById(R.id.head_back);
        head_title = (TextView) this.findViewById(R.id.head_title);
        head_img = (ImageView) this.findViewById(R.id.head_img);
        head_submit = (TextView) this.findViewById(R.id.head_submit);
        modify_edit = (EditText) this.findViewById(R.id.modify_edit);

        head_title.setText("修改备注名称");
        head_img.setVisibility(View.GONE);
        head_submit.setVisibility(View.VISIBLE);

        head_back.setOnClickListener(this);
        head_submit.setOnClickListener(this);

    }

    /**
     * tag  1--房子  2-－房间
     *
     * @param tag
     */
    private void getManagementDataHouse(final int tag) {

        loadingDialog.show();
        final Modify modify = new Modify();
        modify.setName(modify_edit.getText().toString());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HttpUtils.HOSTUTIL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(RetrofitAPIManager.getOkHttpClient(getApplicationContext()))
                .build();

        HttpInterface httpInterface = retrofit.create(HttpInterface.class);

        String urlEnd = "";
        switch (tag) {
            case 1:
                urlEnd = "smart/v1/House/";
                break;
            case 2:
                urlEnd = "smart/v1/Room/";
                break;
        }

        Call<UpDateHouse> call = httpInterface.getModifyHouse(urlEnd + id + "?accessToken=" + FileUtil.loadString(ModifyActivity.this, Constants.ACCESSTOKEN), modify);

        call.enqueue(new Callback<UpDateHouse>() {
            @Override
            public void onResponse(Call<UpDateHouse> call, Response<UpDateHouse> response) {
                System.out.println("-------bbbbbbbbbb--aaaaaaaaa-----" + response.body().toString());
                loadingDialog.dismiss();
                UpDateHouse upDateHouse = response.body();
                if (upDateHouse.getCode() == 200) {
                    if (Constants.outHouse == null) {
                        Constants.outHouse = gson.fromJson(FileUtil.loadString(ModifyActivity.this, Constants.SP_OUTHOUSE), OutModel_PersonalInForMation.class);
                    }

                    switch (tag) {
                        case 1:
                            for (int i = 0; i < Constants.outHouse.getResult().getHouses().size(); i++) {

                                if (Constants.outHouse.getResult().getHouses().get(i).getId() == Integer.valueOf(id)) {
                                    Constants.outHouse.getResult().getHouses().get(i).setName(modify.getName());
                                    break;
                                }
                            }
                            break;
                        case 2:
                            outer:
                            for (int i = 0; i < Constants.outHouse.getResult().getHouses().size(); i++) {
                                inner:
                                for (int j = 0; i < Constants.outHouse.getResult().getHouses().get(i).getRooms().size(); j++) {
                                    if (Constants.outHouse.getResult().getHouses().get(i).getRooms().get(j).getId() == Integer.valueOf(id)) {
                                        Constants.outHouse.getResult().getHouses().get(i).getRooms().get(j).setName(modify.getName());
                                        break outer;
                                    }
                                }

                            }
                            break;

                    }

                    FileUtil.saveString(ModifyActivity.this, Constants.SP_OUTHOUSE, gson.toJson(Constants.outHouse));
                    Intent intent = new Intent();
                    switch (tag) {
                        case 1:
                            setResult(111, intent);
                            break;
                        case 2:
                            setResult(112, intent);
                            break;
                    }

                    EventBus.getDefault().post(new EventBus_Account(Constants.EVENT_BUS_MODIFY_PERSONAL_HOUSE));
                    getFinish();
                } else {
                    AppUtils.show(ModifyActivity.this, "修改失败");
                }


            }

            @Override
            public void onFailure(Call<UpDateHouse> call, Throwable t) {
                loadingDialog.dismiss();
                AppUtils.show(ModifyActivity.this, "修改失败");
                System.out.println("---aaaaa--failure--bbbbb---" + t.getMessage());
            }
        });

    }


    /**
     */
    private void getManagementDataUser() {

        loadingDialog.show();
        final Modify modify = new Modify();
        modify.setName(modify_edit.getText().toString());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HttpUtils.HOSTUTIL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(RetrofitAPIManager.getOkHttpClient(getApplicationContext()))
                .build();

        HttpInterface httpInterface = retrofit.create(HttpInterface.class);

        String urlEnd = "smart/v1/House/User/" + id + "/" + uid;

        Call<UpDateHouse> call = httpInterface.getModifyUser(urlEnd + "?accessToken=" + FileUtil.loadString(ModifyActivity.this, Constants.ACCESSTOKEN), modify);

        call.enqueue(new Callback<UpDateHouse>() {
            @Override
            public void onResponse(Call<UpDateHouse> call, Response<UpDateHouse> response) {
                System.out.println("-------bbbbbbbbbb--aaaaaaaaa-----" + response.body().toString());
                loadingDialog.dismiss();
                UpDateHouse upDateHouse = response.body();
                if (upDateHouse.getCode() == 200) {
                    if (Constants.outHouse == null) {
                        Constants.outHouse = gson.fromJson(FileUtil.loadString(ModifyActivity.this, Constants.SP_OUTHOUSE), OutModel_PersonalInForMation.class);
                    }
                    outer:
                    for (int i = 0; i < Constants.outHouse.getResult().getHouses().size(); i++) {

                        if (Constants.outHouse.getResult().getHouses().get(i).getId() == Integer.valueOf(id)) {
                            inner:
                            for (int j = 0; j < Constants.outHouse.getResult().getHouses().get(i).getUserHouses().size(); j++) {
                                if(Constants.outHouse.getResult().getHouses().get(i).getUserHouses().get(j).getUser_id() == Integer.valueOf(uid)){
                                    Constants.outHouse.getResult().getHouses().get(i).getUserHouses().get(j).setUsername(modify.getName());
                                    AppUtils.show(ModifyActivity.this, "修改成功");
                                    break outer;
                                }

                            }
                        }
                    }


                    FileUtil.saveString(ModifyActivity.this, Constants.SP_OUTHOUSE, gson.toJson(Constants.outHouse));

                    EventBus.getDefault().post(new EventBus_Account(Constants.EVENT_BUS_MODIFY_PERSONAL_REMARKS));
                    getFinish();
            }else{
                AppUtils.show(ModifyActivity.this, "修改失败");
            }


        }

        @Override
        public void onFailure (Call < UpDateHouse > call, Throwable t){
            loadingDialog.dismiss();
            AppUtils.show(ModifyActivity.this, "修改失败");
            System.out.println("---aaaaa--failure--bbbbb---" + t.getMessage());
        }
    }

    );

}


    private void getFinish() {
        this.finish();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.head_back:
                this.finish();
                break;
            case R.id.head_submit:

                String name = modify_edit.getText().toString().trim();
                if (name != null && !name.equals("")) {
                    switch (tag) {
                        case 0:
                            getManagementDataUser();
                            break;
                        case 1:
                            getManagementDataHouse(1);
                            break;
                        case 2:
                            getManagementDataHouse(2);
                            break;
                        case 3://修改自己的用户名
                            Intent intent = new Intent();
                            intent.putExtra("NAME", name);
                            setResult(101, intent);
                            this.finish();
                            break;
                    }
                } else {
                    AppUtils.show(ModifyActivity.this, "输入不能为空");
                }

                break;
        }

    }
}
