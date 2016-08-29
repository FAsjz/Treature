package com.feicui.sjz.treasure.user.login;



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
public class LoginPresenter extends MvpNullObjectBasePresenter<LoginView>{


    private Call<LoginResult> call;


    public void Login(User user) {
        getView().showProgress();
        UserApi userApi = NetClient.getInstance().getUserApi();
        if (call != null) call.cancel();
        call = userApi.login(user);
        call.enqueue(callback);

    }

    private Callback callback = new Callback() {
        @Override
        public void onResponse(Call call, Response response) {
            getView().hintProgress();
            if (response.isSuccessful()) {
                LoginResult result = (LoginResult)response.body();
                if (result == null) {
                    getView().showMessage("");
                    return;
                }
                getView().showMessage(result.getErrmsg());
                if (result.getErrcode() == 1) {
                    UserPrefs.getInstance().setToken( result.getTokenid());
                    UserPrefs.getInstance().setPhoto(NetClient.BASE_URL +result.getHeadpic());
                    getView().navigateToHome();
                }
            }else {
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
