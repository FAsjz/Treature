package com.feicui.sjz.treasure.user.account;

import android.util.Log;

import com.feicui.sjz.treasure.net.NetClient;
import com.feicui.sjz.treasure.user.UserApi;
import com.feicui.sjz.treasure.user.UserPrefs;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import java.io.File;



import okhttp3.MultipartBody;
;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 16-7-18.
 */
public class AccountPresenter extends MvpNullObjectBasePresenter<AccountView> {

    private Call<UpLoadResult> upLoadResultCall;
    private Call<UpDataResult> upDataResultCall;


    public void upLoadPhoto(File file){
        Log.e("File",file.getPath());
        getView().showProgress();
        UserApi userApi = NetClient.getInstance().getUserApi();

        RequestBody body = RequestBody.create(null, file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("image", "photo.png", body);
//        MultipartBody.Part part = MultipartBody.Part.createFormData("a", "b", body);
        Log.e("upLoadPhoto",part.toString());
        if (upLoadResultCall != null) upLoadResultCall.cancel();
        upLoadResultCall = userApi.upLoadPhoto(part);
        upLoadResultCall.enqueue(callback);
        Log.e("upLoadPhoto",upLoadResultCall.toString());

    }
    private Callback<UpLoadResult> callback = new Callback<UpLoadResult>() {
        @Override
        public void onResponse(Call<UpLoadResult> call, Response<UpLoadResult> response) {
            Log.e("upLoadPhoto","33333333");
            if (response != null && response.isSuccessful()) {
                UpLoadResult result = response.body();
                if (result == null){
                    getView().showMessage("unknown error");
                    getView().hintProgress();
                    return;
                }
                Log.e("result",result.toString());
                getView().showMessage(result.getErrcode());
                if (result.getUrlcount() != 1) {
                    getView().hintProgress();
                    return;
                }
                //获取头像地址
                getView().upDataPhoto(NetClient.BASE_URL + result.getSmallImgUrl());
                //储存头像地址
                UserPrefs.getInstance().setPhoto(NetClient.BASE_URL + result.getSmallImgUrl());
                //获取更新参数
                int Tokenid = UserPrefs.getInstance().getToken();
                String PhotoUrl = result.getSmallImgUrl();
                Log.e("PhotoUrl",PhotoUrl);
                String PhotoName = PhotoUrl.substring(PhotoUrl.lastIndexOf("/") + 1, PhotoUrl.length());

                UserApi userApi = NetClient.getInstance().getUserApi();
                upDataResultCall = userApi.upData(new UpData(PhotoName,Tokenid));
                upDataResultCall.enqueue(upDataCallback);

            }
        }

        @Override
        public void onFailure(Call<UpLoadResult> call, Throwable t) {
            getView().hintProgress();
//            getView().showMessage(t.getMessage());
            Log.e("onFailure1",t.getMessage());
              getView().showMessage("我在这里错啦");
        }
    };
    private final Callback<UpDataResult> upDataCallback = new Callback<UpDataResult>() {
        @Override
        public void onResponse(Call<UpDataResult> call, Response<UpDataResult> response) {
            Log.e("upLoadPhoto","33333333");
            getView().hintProgress();
            if (response != null && response.isSuccessful()) {
                UpDataResult result = response.body();
                if (result == null) {
                    getView().showMessage("UNKONWN ERROR");
                    return;
                }
                getView().showMessage(result.getErrmsg());


            }
        }

        @Override
        public void onFailure(Call<UpDataResult> call, Throwable t) {

            getView().hintProgress();
            getView().showMessage(t.getMessage());
            Log.e("onFailure2", t.getMessage());
            getView().showMessage("我在那里错啦");
        }
    };

    @Override
        public void detachView(boolean retainInstance) {
            if (upLoadResultCall != null) {
                upLoadResultCall.cancel();
            }
            if (upDataResultCall != null) {
                upDataResultCall.cancel();
            }


    }
}
