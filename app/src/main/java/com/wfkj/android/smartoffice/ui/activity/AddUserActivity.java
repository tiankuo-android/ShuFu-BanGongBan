package com.wfkj.android.smartoffice.ui.activity;

import android.app.Activity;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.google.gson.Gson;
import com.wfkj.android.smartoffice.R;
import com.wfkj.android.smartoffice.model.sever_model.OutModel_QRCode;
import com.wfkj.android.smartoffice.my_interface.HttpInterface;
import com.wfkj.android.smartoffice.util.AppUtils;
import com.wfkj.android.smartoffice.util.Constants;
import com.wfkj.android.smartoffice.util.FileUtil;
import com.wfkj.android.smartoffice.util.HttpUtils;
import com.wfkj.android.smartoffice.util.http_util.RetrofitAPIManager;
import com.wfkj.android.smartoffice.util.view_util.LoadingDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wangdongyang on 17/2/9.
 */
public class AddUserActivity extends Activity implements OnClickListener{

    private LoadingDialog loadingDialog;
    private Gson gson = new Gson();

    private int houseId;

    private ImageView head_back;
    private TextView head_title;
    private ImageView head_img;
    private TextView head_submit;

    private SimpleDraweeView simpleDraweeView;

    //监听图片下载过程
    private ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
        @Override
        public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo, @Nullable Animatable anim) {
            System.out.println("------------------1111111---------------");
        }

        @Override
        public void onIntermediateImageSet(String id, @Nullable ImageInfo imageInfo) {
            System.out.println("------------------2222222---------------");
        }

        @Override
        public void onFailure(String id, Throwable throwable) {
            System.out.println("------------------3333333---------------");
            loadingDialog.dismiss();
        }
    };

   private DraweeController draweeController = Fresco.newDraweeControllerBuilder()
            .setAutoPlayAnimations(true)
            .setControllerListener(controllerListener)
            .build();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        loadingDialog = new LoadingDialog(AddUserActivity.this);
        init();
    }


    private void init(){
        houseId = getIntent().getIntExtra("HOUSEID",-1);

        head_back = (ImageView) this.findViewById(R.id.head_back);
        head_title = (TextView) this.findViewById(R.id.head_title);
        head_img = (ImageView) this.findViewById(R.id.head_img);
        head_submit = (TextView) this.findViewById(R.id.head_submit);
        simpleDraweeView = (SimpleDraweeView) this.findViewById(R.id.add_user_qrcode);

        head_title.setText("添加用户");
        head_img.setVisibility(View.GONE);
        head_submit.setVisibility(View.VISIBLE);
        head_submit.setText("重置");

        head_back.setOnClickListener(this);
        head_submit.setOnClickListener(this);
        simpleDraweeView.setController(draweeController);

        getQRCodeData();
    }

    private void getQRCodeData() {

        loadingDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HttpUtils.HOSTUTIL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(RetrofitAPIManager.getOkHttpClient(getApplicationContext()))
                .build();

        HttpInterface httpInterface = retrofit.create(HttpInterface.class);

        Call<OutModel_QRCode> call = httpInterface.getQRCode("smart/v1/Account/Qrcode/"+houseId+"?accessToken="+ FileUtil.loadString(AddUserActivity.this, Constants.ACCESSTOKEN));

        call.enqueue(new Callback<OutModel_QRCode>() {
            @Override
            public void onResponse(Call<OutModel_QRCode> call, Response<OutModel_QRCode> response) {


                OutModel_QRCode outModel_qrCode = response.body();
                if(outModel_qrCode.getCode() == 200){
                    String imgUrl = outModel_qrCode.getResult().getUrl();
                    if (imgUrl != null) {
//                        imgUrl = imgUrl.replace("https", "http");
                        simpleDraweeView.setImageURI(imgUrl);
                    }
                }else{
                    loadingDialog.dismiss();
                    AppUtils.show(AddUserActivity.this,"失败");
                }
                System.out.println("-----json--------"+response.body());
            }

            @Override
            public void onFailure(Call<OutModel_QRCode> call, Throwable t) {
                loadingDialog.dismiss();
                AppUtils.show(AddUserActivity.this,"失败");
            }
        });


    }


    private void upDateQRCodeData() {

        loadingDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HttpUtils.HOSTUTIL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(RetrofitAPIManager.getOkHttpClient(getApplicationContext()))
                .build();

        HttpInterface httpInterface = retrofit.create(HttpInterface.class);

        Call<OutModel_QRCode> call = httpInterface.upDateQRCode("smart/v1/Account/Qrcode/"+houseId+"?accessToken="+ FileUtil.loadString(AddUserActivity.this, Constants.ACCESSTOKEN));

        call.enqueue(new Callback<OutModel_QRCode>() {
            @Override
            public void onResponse(Call<OutModel_QRCode> call, Response<OutModel_QRCode> response) {


                OutModel_QRCode outModel_qrCode = response.body();
                if(outModel_qrCode.getCode() == 200){
                    String imgUrl = outModel_qrCode.getResult().getUrl();
                    if (imgUrl != null) {
//                        imgUrl = imgUrl.replace("https", "http");
                        simpleDraweeView.setImageURI(imgUrl);
                    }
                }else{
                    loadingDialog.dismiss();
                    AppUtils.show(AddUserActivity.this,"失败");
                }
                System.out.println("-----json--------"+response.body());
            }

            @Override
            public void onFailure(Call<OutModel_QRCode> call, Throwable t) {
                loadingDialog.dismiss();
                AppUtils.show(AddUserActivity.this,"失败");
            }
        });


    }



    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.head_back:
                this.finish();
                break;
            case R.id.head_submit:
                upDateQRCodeData();
                break;
        }

    }
}
