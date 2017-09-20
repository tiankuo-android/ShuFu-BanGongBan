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
import com.wfkj.android.smartoffice.adapter.UserManagementAdapter;
import com.wfkj.android.smartoffice.model.sever_model.OutModel_PersonalInForMation;
import com.wfkj.android.smartoffice.model.sever_model.UpDateHouse;
import com.wfkj.android.smartoffice.my_interface.DeleteUserInterface;
import com.wfkj.android.smartoffice.my_interface.HttpInterface;
import com.wfkj.android.smartoffice.util.AppUtils;
import com.wfkj.android.smartoffice.util.Constants;
import com.wfkj.android.smartoffice.util.EventBus_Account;
import com.wfkj.android.smartoffice.util.FileUtil;
import com.wfkj.android.smartoffice.util.HttpUtils;
import com.wfkj.android.smartoffice.util.http_util.RetrofitAPIManager;
import com.wfkj.android.smartoffice.util.view_util.LoadingDialog;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wangdongyang on 17/2/7.
 */
public class UserManagementActivity extends Activity implements OnClickListener {

    private Gson gson = new Gson();
    private LoadingDialog loadingDialog;

    private int deleteId;
    private int deleteUid;

    private ImageView head_back;
    private TextView head_title;
    private ImageView head_img;

    private ListView user_management_listview;
    private UserManagementAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_management);
        EventBus.getDefault().register(this);
        loadingDialog = new LoadingDialog(this);
        init();
    }

    private void init() {
        head_back = (ImageView) this.findViewById(R.id.head_back);
        head_title = (TextView) this.findViewById(R.id.head_title);
        head_img = (ImageView) this.findViewById(R.id.head_img);
        user_management_listview = (ListView) this.findViewById(R.id.user_management_listview);

        head_back.setOnClickListener(this);
        head_title.setText("人员管理");
        head_img.setVisibility(View.GONE);

        adapter = new UserManagementAdapter(UserManagementActivity.this, Constants.outHouse.getResult().getHouses(), new DeleteUserInterface() {
            @Override
            public void getDeleteUser(int id, int uid) {
                deleteId = id;
                deleteUid = uid;
                Intent intent = new Intent();
                intent.setClass(UserManagementActivity.this,MyDialogActivity.class);
                startActivityForResult(intent,1005);
            }
        });
        user_management_listview.setAdapter(adapter);

    }



    /**
     */
    private void deleteUser() {

        loadingDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HttpUtils.HOSTUTIL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(RetrofitAPIManager.getOkHttpClient(getApplicationContext()))
                .build();

        HttpInterface httpInterface = retrofit.create(HttpInterface.class);

        String urlEnd = "smart/v1/House/User/" + deleteId + "/" + deleteUid;

        Call<UpDateHouse> call = httpInterface.deleteUser(urlEnd + "?accessToken=" + FileUtil.loadString(UserManagementActivity.this, Constants.ACCESSTOKEN));

        call.enqueue(new Callback<UpDateHouse>() {
                         @Override
                         public void onResponse(Call<UpDateHouse> call, Response<UpDateHouse> response) {
                             System.out.println("-------bbbbbbbbbb--aaaaaaaaa-----" + response.body().toString());
                             loadingDialog.dismiss();
                             UpDateHouse upDateHouse = response.body();
                             if (upDateHouse.getCode() == 200) {
                                 if (Constants.outHouse == null) {
                                     Constants.outHouse = gson.fromJson(FileUtil.loadString(UserManagementActivity.this, Constants.SP_OUTHOUSE), OutModel_PersonalInForMation.class);
                                 }
                                 outer:
                                 for (int i = 0; i < Constants.outHouse.getResult().getHouses().size(); i++) {

                                     if (Constants.outHouse.getResult().getHouses().get(i).getId() == deleteId) {
                                         inner:
                                         for (int j = 0; j < Constants.outHouse.getResult().getHouses().get(i).getUserHouses().size(); j++) {
                                             if(Constants.outHouse.getResult().getHouses().get(i).getUserHouses().get(j).getUser_id() == deleteUid){
                                                 Constants.outHouse.getResult().getHouses().get(i).getUserHouses().remove(Constants.outHouse.getResult().getHouses().get(i).getUserHouses().get(j));
                                                 AppUtils.show(UserManagementActivity.this, "删除成功");
                                                 break outer;
                                             }

                                         }
                                     }
                                 }
                                 FileUtil.saveString(UserManagementActivity.this, Constants.SP_OUTHOUSE, gson.toJson(Constants.outHouse));
                                 adapter.notifyDataSetChanged();
                             }else{
                                 AppUtils.show(UserManagementActivity.this, "删除失败");
                             }


                         }

                         @Override
                         public void onFailure (Call < UpDateHouse > call, Throwable t){
                             loadingDialog.dismiss();
                             AppUtils.show(UserManagementActivity.this, "删除失败");
                             System.out.println("---aaaaa--failure--bbbbb---" + t.getMessage());
                         }
                     }

        );

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.head_back:
                this.finish();
                break;
        }

    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void getEventBus(EventBus_Account message) {
        int tag = message.tag;

        if (tag == Constants.EVENT_BUS_MODIFY_PERSONAL_REMARKS) {
//            System.out.println("----------999999--------"+Constants.outHouse.getResult().getHouses().get(0).getUserHouses().toString());
//            adapter = new UserManagementAdapter(UserManagementActivity.this, Constants.outHouse.getResult().getHouses());
//            user_management_listview.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case 121:
                int tag = data.getIntExtra("TAG_DELETE",-1);
                switch (tag){
                    case 0:
                        deleteUser();
                        break;
                    case 1:
                        break;
                }
                break;
        }
    }
}
