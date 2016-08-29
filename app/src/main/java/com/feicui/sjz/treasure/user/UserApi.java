package com.feicui.sjz.treasure.user;

import com.feicui.sjz.treasure.user.account.UpData;
import com.feicui.sjz.treasure.user.account.UpDataResult;
import com.feicui.sjz.treasure.user.account.UpLoadResult;
import com.feicui.sjz.treasure.user.login.LoginResult;
import com.feicui.sjz.treasure.user.regist.RegisterResult;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Administrator on 16-7-15.
 */
public interface UserApi {


    @POST("/Handler/UserHandler.ashx?action=register")
    Call<RegisterResult> register(@Body User user);

    @POST("/Handler/UserHandler.ashx?action=login")
    Call<LoginResult> login(@Body User user);

    @Multipart
    @POST("/Handler/UserLoadPicHandler1.ashx")
    Call<UpLoadResult> upLoadPhoto(@Part MultipartBody.Part part);//上传

    @POST("/Handler/UserHandler.ashx?action=update")
    Call<UpDataResult> upData(@Body UpData upData);
}
