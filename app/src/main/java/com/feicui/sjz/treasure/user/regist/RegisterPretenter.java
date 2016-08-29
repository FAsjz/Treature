package com.feicui.sjz.treasure.user.regist;

import com.feicui.sjz.treasure.net.NetClient;
import com.feicui.sjz.treasure.user.User;
import com.feicui.sjz.treasure.user.UserApi;
import com.feicui.sjz.treasure.user.UserPrefs;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Administrator on 16-7-12.
 */
public class RegisterPretenter extends MvpNullObjectBasePresenter<RegisterView> {

    private Call<RegisterResult> call;


    public void Register(User user) {
        UserApi userApi = NetClient.getInstance().getUserApi();
        if (call != null) call.cancel();
        call = userApi.register(user);
        getView().showProgress();
        call.enqueue(callback);

    }

    private Callback callback = new Callback() {
        @Override
        public void onResponse(Call call, Response response) {
            if (response.isSuccessful()) {
                getView().hintProgress();
                RegisterResult result = (RegisterResult) response.body();
                if (result == null) {
                    getView().showMessage("未知错误");
                    return;
                }
                getView().showMessage(result.getErrmsg());
                if (result.getErrcode() == 1) {
                    UserPrefs.getInstance().setToken(result.getTokenid());
                    getView().navigateToHome();
                }
            } else {
                getView().showMessage("网络异常");
            }
        }

        @Override
        public void onFailure(Call call, Throwable t) {

            getView().hintProgress();
            getView().showMessage(t.getMessage());
        }
    };


    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (call != null) {
            call.cancel();
        }
    }
}
