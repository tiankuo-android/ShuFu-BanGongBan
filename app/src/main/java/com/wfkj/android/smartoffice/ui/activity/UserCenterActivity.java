package com.wfkj.android.smartoffice.ui.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.wfkj.android.smartoffice.MainActivity;
import com.wfkj.android.smartoffice.R;
import com.wfkj.android.smartoffice.model.ginseng.AccountChangeGinseng;
import com.wfkj.android.smartoffice.model.sever_model.House;
import com.wfkj.android.smartoffice.model.sever_model.OutHeadPortrait;
import com.wfkj.android.smartoffice.model.sever_model.OutModel_AccountChange;
import com.wfkj.android.smartoffice.model.sever_model.OutModel_PersonalInForMation;
import com.wfkj.android.smartoffice.model.sever_model.SignOut;
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

import de.greenrobot.event.EventBus;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 用户中心
 * Created by wangdongyang on 17/1/5.
 */
public class UserCenterActivity extends Activity implements OnClickListener {

    private LoadingDialog loadingDialog;
    private Gson gson = new Gson();

    /***
     * 使用照相机拍照获取图片
     */
    public static final int SELECT_PIC_BY_TACK_PHOTO = 0;
    /***
     * 使用相册中的图片
     */
    public static final int SELECT_PIC_BY_PICK_PHOTO = 1;


    public static final int NICKNAME_CODE = 101;
    public static final int PHONE_CODE = 102;

    public static final int NAME_CODE = 103;
    public static final int ID_CODE = 104;
    public static final int PHOTOZOOM = 0; //
    public static final int PHOTOTAKE = 1; //
    public static final int IMAGE_COMPLETE = 2; //
    public static final int CROPREQCODE = 3; //

    /**
     * 获取到的图片路径
     */
    private String picPath;

    /***
     * 从Intent获取图片路径的KEY
     */
    public static final String KEY_PHOTO_PATH = "photo_path";
    private Uri photoUri;

    private PopupWindow popWindow;
    private LayoutInflater layoutInflater;
    private TextView photograph, albums;
    private LinearLayout cancel;

    private ImageView head_back;//返回按钮
    private TextView head_title;//标题
    private ImageView headimg;

    private SimpleDraweeView head_userimg;//头像
    private TextView name;//名称
    private TextView phone;//电话
    private ImageView img_identity;//身份
    private LinearLayout user_management_btn;//用户管理按钮
    private LinearLayout modify_passwrod_btn;//修改密码按钮
    private LinearLayout cancellation_btn;//注销按钮
    private ImageView edit_name_btn;//修改名称按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_center);
        loadingDialog = new LoadingDialog(UserCenterActivity.this);
        init();
    }


    private void init() {
        layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        head_back = (ImageView) this.findViewById(R.id.head_back);
        head_title = (TextView) this.findViewById(R.id.head_title);
        headimg = (ImageView) this.findViewById(R.id.head_img);

        head_userimg = (SimpleDraweeView) this.findViewById(R.id.head_userimg);
        name = (TextView) this.findViewById(R.id.name);
        phone = (TextView) this.findViewById(R.id.phone);
        img_identity = (ImageView) this.findViewById(R.id.img_identity);
        user_management_btn = (LinearLayout) this.findViewById(R.id.user_management_btn);
        modify_passwrod_btn = (LinearLayout) this.findViewById(R.id.modify_passwrod_btn);
        cancellation_btn = (LinearLayout) this.findViewById(R.id.cancellation_btn);
        edit_name_btn = (ImageView) this.findViewById(R.id.edit_name_btn);

        head_title.setText("用户中心");
        headimg.setVisibility(View.GONE);
        head_back.setOnClickListener(this);
        user_management_btn.setOnClickListener(this);
        modify_passwrod_btn.setOnClickListener(this);
        cancellation_btn.setOnClickListener(this);
        edit_name_btn.setOnClickListener(this);
        head_userimg.setOnClickListener(this);

        if (FileUtil.loadString(UserCenterActivity.this, Constants.SP_ROLE).equals("owner")) {
            img_identity.setImageResource(R.mipmap.administrators_icon);
        } else {
            img_identity.setImageResource(R.mipmap.user);
        }


        String imgUrl = Constants.outHouse.getResult().getProfile().getAvatarPath();
        if (imgUrl != null) {
            imgUrl = imgUrl.replace("https", "http");
            head_userimg.setImageURI(imgUrl);
        }

        String nameStr = Constants.outHouse.getResult().getProfile().getFirstName();
        if (nameStr == null || nameStr.equals("")) {
            name.setText("火星人");
        } else {
            name.setText(nameStr);
        }

        phone.setText(FileUtil.loadString(UserCenterActivity.this, Constants.USERNAME));
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.head_userimg:
                showPopupWindow(head_userimg);
                break;
            case R.id.head_back:
                this.finish();
                break;
            case R.id.user_management_btn:
                intent.setClass(UserCenterActivity.this, UserManagementActivity.class);
                startActivity(intent);
                break;
            case R.id.modify_passwrod_btn:
                intent.setClass(UserCenterActivity.this, ModifyPasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.cancellation_btn:
                getSingOut();
                break;
            case R.id.edit_name_btn:
                intent.setClass(UserCenterActivity.this, ModifyActivity.class);
                intent.putExtra("TAG", 3);
                startActivityForResult(intent, 2000);
                break;
        }
    }

    @SuppressLint("InflateParams")
    @SuppressWarnings("deprecation")
    private void showPopupWindow(View parent) {
        if (popWindow == null) {
            View view = layoutInflater.inflate(R.layout.pop_select_photo, null);
            popWindow = new PopupWindow(view, ActionBar.LayoutParams.MATCH_PARENT,
                    ActionBar.LayoutParams.MATCH_PARENT, true);
            initPop(view);
        }
        popWindow.setAnimationStyle(android.R.style.Animation_InputMethod);
        popWindow.setFocusable(true);
        popWindow.setOutsideTouchable(true);
        popWindow.setBackgroundDrawable(new BitmapDrawable());
        popWindow
                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);
    }

    // 实例化pop
    public void initPop(View view) {
        photograph = (TextView) view.findViewById(R.id.photograph);
        albums = (TextView) view.findViewById(R.id.albums);
        cancel = (LinearLayout) view.findViewById(R.id.cancel);
        photograph.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                popWindow.dismiss();
                takePhoto();
            }
        });
        albums.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                popWindow.dismiss();
//                Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
//                openAlbumIntent
//                        .setDataAndType(
//                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                                "image/*");

                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, SELECT_PIC_BY_PICK_PHOTO);


//                startActivityForResult(openAlbumIntent,
//                        SELECT_PIC_BY_PICK_PHOTO);
            }
        });
        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                popWindow.dismiss();

            }
        });
    }

    /**
     * 拍照获取图片
     */
    private void takePhoto() {
        // 执行拍照前，应该先判断SD卡是否存在
        String SDState = Environment.getExternalStorageState();
        if (SDState.equals(Environment.MEDIA_MOUNTED)) {

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// "android.media.action.IMAGE_CAPTURE"
            /***
             * 需要说明一下，以下操作使用照相机拍照，拍照后的图片会存放在相册中的 这里使用的这种方式有一个好处就是获取的图片是拍照后的原图
             * 如果不实用ContentValues存放照片路径的话，拍照后获取的图片为缩略图不清晰
             */
            ContentValues values = new ContentValues();
            photoUri = this.getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, photoUri);
            /** ----------------- */
            startActivityForResult(intent, SELECT_PIC_BY_TACK_PHOTO);
        } else {
            Toast.makeText(this, "内存卡不存在", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 选择图片后，获取图片的路径
     *
     * @param requestCode
     * @param data
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @SuppressLint("NewApi")
    private void doPhoto(int requestCode, Intent data) {


        if (requestCode == SELECT_PIC_BY_PICK_PHOTO) // 从相册取图片，有些手机有异常情况，请注意
        {

            if (data != null) {
                photoUri = data.getData();
            }


            if (photoUri == null) {
                Toast.makeText(this, "选择图片文件出错", Toast.LENGTH_LONG).show();
                return;
            }
        }


        if (requestCode == SELECT_PIC_BY_TACK_PHOTO) // 从相册取图片，有些手机有异常情况，请注意
        {

            if (data != null) {
                photoUri = data.getData();
            }


            if (photoUri == null) {
                Toast.makeText(this, "选择图片文件出错", Toast.LENGTH_LONG).show();
                return;
            }
        }


        try {
            if (DocumentsContract.isDocumentUri(UserCenterActivity.this,
                    photoUri)) {

                String wholeID = DocumentsContract.getDocumentId(photoUri);

                String id = wholeID.split(":")[1];

                String[] column = {MediaStore.Images.Media.DATA};

                String sel = MediaStore.Images.Media._ID + "=?";

                Cursor cursor = UserCenterActivity.this.getContentResolver()
                        .query(

                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, column,
                                sel,

                                new String[]{id}, null);

                int columnIndex = cursor.getColumnIndex(column[0]);

                if (cursor.moveToFirst()) {

                    picPath = cursor.getString(columnIndex);

                }

                if (Build.VERSION.SDK_INT < 14) {
                    cursor.close();
                }

            } else {

                String[] pojo = {MediaStore.Images.Media.DATA};
                @SuppressWarnings("deprecation")

                // ContentResolver cr = this.getContentResolver();
                        // Cursor  cursor = cr.query(photoUri, pojo, null, null, null);
                        Cursor cursor = managedQuery(photoUri, pojo, null, null, null);
                try {
                    if (cursor != null) {
                        int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);
                        cursor.moveToFirst();
                        picPath = cursor.getString(columnIndex);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (null != cursor) {
                        if (Build.VERSION.SDK_INT < 14) {
                            cursor.close();
                        }
                    }
                }

            }
        } catch (NoClassDefFoundError e) {

            String[] pojo = {MediaStore.Images.Media.DATA};
            @SuppressWarnings("deprecation")
            Cursor cursor = managedQuery(photoUri, pojo, null, null, null);
            try {
                if (cursor != null) {
                    int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);
                    cursor.moveToFirst();
                    picPath = cursor.getString(columnIndex);
                }
            } catch (NullPointerException d) {
                Toast.makeText(this, "选择图片文件出错", Toast.LENGTH_LONG).show();

            } catch (Exception i) {
                i.printStackTrace();
            } finally {
                if (null != cursor) {
                    if (Build.VERSION.SDK_INT < 14) {
                        cursor.close();
                    }
                }
            }
        }

        System.out.println("-----picpath------" + picPath);
        if (picPath != null
                && (picPath.endsWith(".png") || picPath.endsWith(".PNG")
                || picPath.endsWith(".jpg") || picPath.endsWith(".JPG"))) {
            Intent intent3 = new Intent(UserCenterActivity.this,
                    ClipActivity.class);
            intent3.putExtra("path", picPath);
            startActivityForResult(intent3, IMAGE_COMPLETE);
        } else {
            Toast.makeText(this, "选择图片文件不正确", Toast.LENGTH_LONG).show();
        }
    }


    private void getUpLoadImg(File file) {
        loadingDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HttpUtils.HOSTUTIL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(RetrofitAPIManager.getOkHttpClient(getApplicationContext()))
                .build();

        HttpInterface httpInterface = retrofit.create(HttpInterface.class);


        // 创建 RequestBody，用于封装 请求RequestBody
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

// MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        Call<OutHeadPortrait> call = httpInterface.uploadImage("smart/v1/Public/FileUpload?accessToken=" + FileUtil.loadString(UserCenterActivity.this, Constants.ACCESSTOKEN), body);

        call.enqueue(new Callback<OutHeadPortrait>() {
            @Override
            public void onResponse(Call<OutHeadPortrait> call, Response<OutHeadPortrait> response) {


                OutHeadPortrait outHeadPortrait = response.body();
                if (outHeadPortrait.getCode() == 200) {
                    getSubmitHeadPortrait(outHeadPortrait.getResult().getUrl(), outHeadPortrait.getResult().getBase_url());
                } else {
                    loadingDialog.dismiss();
                    AppUtils.show(UserCenterActivity.this, "失败");
                }

                System.out.println("-----json--------" + response.body());
            }

            @Override
            public void onFailure(Call<OutHeadPortrait> call, Throwable t) {
                loadingDialog.dismiss();
                AppUtils.show(UserCenterActivity.this, "失败");
            }
        });
    }


    private void getSubmitHeadPortrait(String imgUrl, String baseUrl) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HttpUtils.HOSTUTIL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(RetrofitAPIManager.getOkHttpClient(getApplicationContext()))
                .build();

        HttpInterface httpInterface = retrofit.create(HttpInterface.class);

        AccountChangeGinseng accountChangeGinseng = new AccountChangeGinseng();
        accountChangeGinseng.setAvatar_path(imgUrl);
        accountChangeGinseng.setName(Constants.outHouse.getResult().getProfile().getFirstName());
        accountChangeGinseng.setAvatar_base_url(baseUrl);
        accountChangeGinseng.setEmail("1052539046@qq.com");
        accountChangeGinseng.setMobile(FileUtil.loadString(UserCenterActivity.this, Constants.USERNAME));


        Call<OutModel_AccountChange> call = httpInterface.getAccountChange("smart/v1/Account/Profile?accessToken=" + FileUtil.loadString(UserCenterActivity.this, Constants.ACCESSTOKEN), accountChangeGinseng);

        call.enqueue(new Callback<OutModel_AccountChange>() {
            @Override
            public void onResponse(Call<OutModel_AccountChange> call, Response<OutModel_AccountChange> response) {
                loadingDialog.dismiss();
                OutModel_AccountChange outModel_accountChange = response.body();
                if (outModel_accountChange.getCode() == 200) {

                    if (Constants.outHouse == null) {
                        Constants.outHouse = gson.fromJson(FileUtil.loadString(UserCenterActivity.this, Constants.SP_OUTHOUSE), OutModel_PersonalInForMation.class);
                    }
                    Constants.outHouse.getResult().setInfo(outModel_accountChange.getResult().getInfo());
                    Constants.outHouse.getResult().setHouses((ArrayList<House>) outModel_accountChange.getResult().getHouses());
                    Constants.outHouse.getResult().setProfile(outModel_accountChange.getResult().getProfile());

                    FileUtil.saveString(UserCenterActivity.this, Constants.SP_OUTHOUSE, gson.toJson(Constants.outHouse));

                    String imgUrl = Constants.outHouse.getResult().getProfile().getAvatarPath();
                    if (imgUrl != null) {
                        imgUrl = imgUrl.replace("https", "http");
                        head_userimg.setImageURI(imgUrl);
                    }
                    EventBus.getDefault().post(new EventBus_Account(Constants.EVENT_BUS_MODIFY_PERSONAL_INFORMATION));
                } else {
                    AppUtils.show(UserCenterActivity.this, "失败");
                }

                System.out.println("-----json---2222222-----" + response.body());
            }

            @Override
            public void onFailure(Call<OutModel_AccountChange> call, Throwable t) {
                loadingDialog.dismiss();
                AppUtils.show(UserCenterActivity.this, "失败");
            }
        });

    }


    private void getSubmitName(String nameStr) {
        loadingDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HttpUtils.HOSTUTIL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(RetrofitAPIManager.getOkHttpClient(getApplicationContext()))
                .build();

        HttpInterface httpInterface = retrofit.create(HttpInterface.class);


        if (Constants.outHouse == null) {
            Constants.outHouse = gson.fromJson(FileUtil.loadString(UserCenterActivity.this, Constants.SP_OUTHOUSE), OutModel_PersonalInForMation.class);
        }

        AccountChangeGinseng accountChangeGinseng = new AccountChangeGinseng();
        accountChangeGinseng.setAvatar_path(Constants.outHouse.getResult().getProfile().getAvatarPath());
        accountChangeGinseng.setName(nameStr);
        accountChangeGinseng.setAvatar_base_url(Constants.outHouse.getResult().getProfile().getAvatarBaseUrl());
        accountChangeGinseng.setEmail("1052539046@qq.com");
        accountChangeGinseng.setMobile(FileUtil.loadString(UserCenterActivity.this, Constants.USERNAME));


        Call<OutModel_AccountChange> call = httpInterface.getAccountChange("smart/v1/Account/Profile?accessToken=" + FileUtil.loadString(UserCenterActivity.this, Constants.ACCESSTOKEN), accountChangeGinseng);

        call.enqueue(new Callback<OutModel_AccountChange>() {
            @Override
            public void onResponse(Call<OutModel_AccountChange> call, Response<OutModel_AccountChange> response) {
                loadingDialog.dismiss();
                OutModel_AccountChange outModel_accountChange = response.body();
                if (outModel_accountChange.getCode() == 200) {

                    if (Constants.outHouse == null) {
                        Constants.outHouse = gson.fromJson(FileUtil.loadString(UserCenterActivity.this, Constants.SP_OUTHOUSE), OutModel_PersonalInForMation.class);
                    }
                    Constants.outHouse.getResult().setInfo(outModel_accountChange.getResult().getInfo());
                    Constants.outHouse.getResult().setHouses((ArrayList<House>) outModel_accountChange.getResult().getHouses());
                    Constants.outHouse.getResult().setProfile(outModel_accountChange.getResult().getProfile());

                    FileUtil.saveString(UserCenterActivity.this, Constants.SP_OUTHOUSE, gson.toJson(Constants.outHouse));

                    String nameStr = Constants.outHouse.getResult().getProfile().getFirstName();
                    if (nameStr == null || nameStr.equals("")) {
                        name.setText("火星人");
                    } else {
                        name.setText(nameStr);
                    }
                    EventBus.getDefault().post(new EventBus_Account(Constants.EVENT_BUS_MODIFY_PERSONAL_INFORMATION));
                } else {
                    AppUtils.show(UserCenterActivity.this, "失败");
                }

                System.out.println("-----json---2222222-----" + response.body());
            }

            @Override
            public void onFailure(Call<OutModel_AccountChange> call, Throwable t) {
                loadingDialog.dismiss();
                AppUtils.show(UserCenterActivity.this, "失败");
            }
        });

    }


    private void getSingOut() {
        loadingDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HttpUtils.HOSTUTIL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(RetrofitAPIManager.getOkHttpClient(getApplicationContext()))
                .build();

        HttpInterface httpInterface = retrofit.create(HttpInterface.class);
        Call<SignOut> call = httpInterface.getSignOut("smart/v1/Account/SignOut?accessToken=" + FileUtil.loadString(UserCenterActivity.this, Constants.ACCESSTOKEN));

        call.enqueue(new Callback<SignOut>() {
            @Override
            public void onResponse(Call<SignOut> call, Response<SignOut> response) {
                loadingDialog.dismiss();
                SignOut signOut = response.body();


                if (signOut.getCode() == 200) {
                    if (MainActivity.mainActivity != null) {
                        MainActivity.mainActivity.finish();
                    }
                    getSingOutDelete();
                    Intent intent = new Intent();
                    intent.setClass(UserCenterActivity.this, LogInActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    AppUtils.show(UserCenterActivity.this, "失败");
                }
                System.out.println("-----json---2222222-----" + response.body());


            }

            @Override
            public void onFailure(Call<SignOut> call, Throwable t) {
                loadingDialog.dismiss();
                AppUtils.show(UserCenterActivity.this, "失败");
            }
        });

    }

    private void getSingOutDelete() {
        FileUtil.clearValue(UserCenterActivity.this, Constants.SP_ROLE);
        FileUtil.clearValue(UserCenterActivity.this, Constants.SP_OUTHOUSE);
        FileUtil.clearValue(UserCenterActivity.this, Constants.SP_HOUSE);
        FileUtil.clearValue(UserCenterActivity.this, Constants.SP_HOUSE_ID);
        FileUtil.clearValue(UserCenterActivity.this, Constants.SP_UID);
        FileUtil.clearValue(UserCenterActivity.this, Constants.SP_TOKEN);
        FileUtil.clearValue(UserCenterActivity.this, Constants.SP_WEATHER);
        FileUtil.clearValue(UserCenterActivity.this, Constants.SP_NUMBER_TEMPERATURE);
        FileUtil.clearValue(UserCenterActivity.this, Constants.SP_GOTOWORK);
        FileUtil.clearValue(UserCenterActivity.this, Constants.USERNAME);
        FileUtil.clearValue(UserCenterActivity.this, Constants.PASSWORD);
        FileUtil.clearValue(UserCenterActivity.this, Constants.HOUSEJSON);
        FileUtil.clearValue(UserCenterActivity.this, Constants.JZYSTR);
        FileUtil.clearValue(UserCenterActivity.this, Constants.ACCESSTOKEN);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if (resultCode != RESULT_OK || resultCode!=101) {
//            return;
//        }
        System.out.println("---------1------" + requestCode + "-------2-------" + resultCode);
        Uri uri = null;
        switch (requestCode) {
            case SELECT_PIC_BY_PICK_PHOTO:
                doPhoto(requestCode, data);
                break;
            case SELECT_PIC_BY_TACK_PHOTO:
                doPhoto(requestCode, data);
                break;
            case IMAGE_COMPLETE:
//                final String temppath = data.getStringExtra("path");


//                img_head_portrait.setImageBitmap(getLoacalBitmap(temppath));
//                try {
//                    uploadFile(temppath);
//                } catch (Exception e) {
//                    e.printStackTrace();
//        }
//                FileUtil.saveString(PersonalDataActivity.this,
//                        Constants.LOGIN_HEAD_IMG,
//                        temppath);

//                EventBus.getDefault().post(new MessageEvent(Constants.CHAGEHEADIMG));
                //AppUtil.sendChangeImgIntent();

                final String temppath = data.getStringExtra("path");
                File file = new File(temppath);
                getUpLoadImg(file);
                break;
            case 2000:
                if (resultCode == 101) {
                    getSubmitName(data.getStringExtra("NAME"));
                }
                break;


        }

    }

}
