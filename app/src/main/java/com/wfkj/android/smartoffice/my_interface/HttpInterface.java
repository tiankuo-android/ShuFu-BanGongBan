package com.wfkj.android.smartoffice.my_interface;


import com.google.gson.JsonObject;
import com.wfkj.android.smartoffice.model.ginseng.AccountChangeGinseng;
import com.wfkj.android.smartoffice.model.ginseng.BindCodeGinseng;
import com.wfkj.android.smartoffice.model.ginseng.ForgotPasswordGinseng;
import com.wfkj.android.smartoffice.model.ginseng.LoginGinseng;
import com.wfkj.android.smartoffice.model.ginseng.Modify;
import com.wfkj.android.smartoffice.model.ginseng.RegisteredGinseng;
import com.wfkj.android.smartoffice.model.ginseng.ResetPasswordGinseng;
import com.wfkj.android.smartoffice.model.sever_model.ModifyPassword;
import com.wfkj.android.smartoffice.model.sever_model.OutHeadPortrait;
import com.wfkj.android.smartoffice.model.sever_model.OutHeadPortraits;
import com.wfkj.android.smartoffice.model.sever_model.OutModel;
import com.wfkj.android.smartoffice.model.sever_model.OutModel_AccountChange;
import com.wfkj.android.smartoffice.model.sever_model.OutModel_Binding;
import com.wfkj.android.smartoffice.model.sever_model.OutModel_PersonalInForMation;
import com.wfkj.android.smartoffice.model.sever_model.OutModel_QRCode;
import com.wfkj.android.smartoffice.model.sever_model.SignOut;
import com.wfkj.android.smartoffice.model.sever_model.UpDateHouse;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;


/**
 * Created by wangdongyang on 16/11/17.
 */
public interface HttpInterface {

    @GET("spot/serchCity")
    Call<JsonObject> getDemo();

    @GET("weather?")
    Call<JsonObject> getWeather(@Query("city") String city, @Query("key") String key);

    @FormUrlEncoded
    @POST("smart/v1/Account/SignIn")
    Call<JsonObject> LgoinDemo(@Field("mobile") String mobile, @Field("password") String password);


    /**
     * 获取验证码
     * @param mobile
     * @return
     */
//    @FormUrlEncoded
    @POST("smart/v1/Account/VerifyCode/{mobile}")
    Call<OutModel> getCode(@Path("mobile") String mobile);


    /**
     * 修改密码时候获取验证码
     * @param mobile
     * @return
     */
//    @FormUrlEncoded
    @POST("smart/v1/Account/ResetCode/{mobile}")
    Call<OutModel> getCodeForgotPassword(@Path("mobile") String mobile);


    /**
     * 注册
     * @param body
     * @return
     */
    @POST("smart/v1/Account/SignUp")
    Call<OutModel> getRegistered(@Body RegisteredGinseng body);

    /**
     * 登录
     * @return
     */
    @POST("smart/v1/Account/SignIn")
    Call<OutModel_PersonalInForMation> getLogIn(@Body LoginGinseng body);

    /**
     * 获取个人信息
     * @param accessToken
     * @return
     */
    @GET("smart/v1/Account/Profile")
    Call<JsonObject> getUserInformation(@Query("Authorization") String accessToken);

    /**
     * 绑定二维码
     * @return
     */
    @POST
    Call<OutModel_Binding> getBindCode(@Url String url, @Body BindCodeGinseng body);

    /**
     * 修改密码
     * @return
     */
    @PUT
    Call<ModifyPassword> getForgotPassword(@Url String url, @Body ForgotPasswordGinseng body);

    /**
     * 忘记密码
     * @return
     */
    @POST("smart/v1/Account/ResetPassword")
    Call<ModifyPassword> getResetPassword( @Body ResetPasswordGinseng body);



    @Multipart
    @POST
    Call<OutHeadPortrait> uploadImage(@Url String url, @Part MultipartBody.Part file);

    @Multipart
    @POST
    Call<OutHeadPortraits> uploadImages(@Url String url, @Part() List<MultipartBody.Part> parts);

    /**
     * @return
     * 更新房屋信息
     */
    @PUT
    Call<UpDateHouse> getModifyHouse(@Url String url, @Body Modify body);


    /**
     * @return
     * 更新用户备注
     */
    @PUT
    Call<UpDateHouse> getModifyUser(@Url String url, @Body Modify body);


    @PUT
    Call<OutModel_AccountChange> getAccountChange(@Url String url, @Body AccountChangeGinseng body);


    /**
     * 注销
     * @return
     */
    @POST
    Call<SignOut> getSignOut(@Url String url);


    /**
     * @return
     * 获取房屋二维码
     */
    @GET
    Call<OutModel_QRCode> getQRCode(@Url String url);

    /**
     * @return
     * 重置房屋二维码
     */
    @PUT
    Call<OutModel_QRCode> upDateQRCode(@Url String url);


    /**
     * @return
     * 删除用户
     */
    @DELETE
    Call<UpDateHouse> deleteUser(@Url String url);

}
