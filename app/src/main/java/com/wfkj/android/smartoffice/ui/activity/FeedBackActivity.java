package com.wfkj.android.smartoffice.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.wfkj.android.smartoffice.R;
import com.wfkj.android.smartoffice.adapter.PhotoAdapter;
import com.wfkj.android.smartoffice.model.sever_model.OutHeadPortraits;
import com.wfkj.android.smartoffice.my_interface.HttpInterface;
import com.wfkj.android.smartoffice.util.AppUtils;
import com.wfkj.android.smartoffice.util.Constants;
import com.wfkj.android.smartoffice.util.EventBus_Account;
import com.wfkj.android.smartoffice.util.FileUtil;
import com.wfkj.android.smartoffice.util.HttpUtils;
import com.wfkj.android.smartoffice.util.http_util.RetrofitAPIManager;
import com.wfkj.android.smartoffice.util.view_util.LoadingDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import me.iwf.photopicker.PhotoPicker;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 反馈页面
 * Created by wangdongyang on 16/8/24.
 */
public class FeedBackActivity extends Activity implements OnClickListener {

    private LoadingDialog loadingDialog;

    private ImageView head_back;
    private TextView head_title;
    private ImageView head_img;
    private TextView head_submit;

    private PhotoAdapter adapter;
    private ArrayList<String> list;

    private EditText feed_back_edit;//输入框
    private GridView noScrollgridview;//展示图片的gridview


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        loadingDialog = new LoadingDialog(FeedBackActivity.this);
        EventBus.getDefault().register(this);
        init();
    }

    public void init() {
        head_back = (ImageView) this.findViewById(R.id.head_back);
        head_title = (TextView) this.findViewById(R.id.head_title);
        head_img = (ImageView) this.findViewById(R.id.head_img);
        head_submit = (TextView) this.findViewById(R.id.head_submit);

        feed_back_edit = (EditText) this.findViewById(R.id.feed_back_edit);
        noScrollgridview = (GridView) this.findViewById(R.id.noScrollgridview);


        head_title.setText("建议反馈");
        head_img.setVisibility(View.GONE);
        head_submit.setVisibility(View.VISIBLE);
        head_submit.setOnClickListener(this);
        head_back.setOnClickListener(this);


        feed_back_edit.addTextChangedListener(new EditChangedListener());

        list = new ArrayList<>();
        adapter = new PhotoAdapter(this, list);
        noScrollgridview.setAdapter(adapter);

        noScrollgridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (list.size() < 3 && position == list.size()) {
                    PhotoPicker.builder()
                            .setPhotoCount(3 - list.size())
                            .setShowCamera(true)
                            .setShowGif(true)
                            .setPreviewEnabled(false)
                            .start(FeedBackActivity.this, PhotoPicker.REQUEST_CODE);
                } else {
                    Intent intent = new Intent();
                    intent.setClass(FeedBackActivity.this, BabyDetailImageGalleryActivity.class);
                    intent.putStringArrayListExtra("imgUrls", list);
                    intent.putExtra("index", position);
                    startActivity(intent);
                    overridePendingTransition(R.anim.zoon_in_out, 0);
                }
            }
        });
    }




    private void getUpLoadImg() {


        loadingDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HttpUtils.HOSTUTIL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(RetrofitAPIManager.getOkHttpClient(getApplicationContext()))
                .build();

        HttpInterface httpInterface = retrofit.create(HttpInterface.class);

        List<MultipartBody.Part> parts = new ArrayList<>();
        for(int i =0;i<list.size();i++){
            File file = new File(list.get(i));
            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file["+i+"]", file.getName(), requestFile);
            parts.add(body);
        }

        // 创建 RequestBody，用于封装 请求RequestBody
//        RequestBody requestFile =
//                RequestBody.create(MediaType.parse("multipart/form-data"), file);



        Call<OutHeadPortraits> call = httpInterface.uploadImages("smart/v1/Public/MultiUpload?accessToken=" + FileUtil.loadString(FeedBackActivity.this, Constants.ACCESSTOKEN), parts);

        call.enqueue(new Callback<OutHeadPortraits>() {
            @Override
            public void onResponse(Call<OutHeadPortraits> call, Response<OutHeadPortraits> response) {

                loadingDialog.dismiss();
                OutHeadPortraits outHeadPortraits = response.body();

                System.out.println("-----json--------" + response.body());
            }

            @Override
            public void onFailure(Call<OutHeadPortraits> call, Throwable t) {
                loadingDialog.dismiss();
                AppUtils.show(FeedBackActivity.this, "失败");
            }
        });
    }





    class EditChangedListener implements TextWatcher {
        private CharSequence temp;//监听前的文本
        private int editStart;//光标开始位置
        private int editEnd;//光标结束位置
        private final int charMaxNum = 200;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            temp = s;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void getEventBus(EventBus_Account message) {
        int tag = message.tag;
        int number = message.number;
        String address = message.address;
        if (tag == Constants.EVENT_BUS_MODIFY_PERSONAL_DELETE_IMG_ID) {
            list.remove(address);
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_back://返回
                this.finish();
                break;
            case R.id.head_submit://提交
                getUpLoadImg();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            ArrayList<String> photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
            list.addAll(photos);
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
