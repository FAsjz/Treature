package com.feicui.sjz.treasure.user.regist;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by Administrator on 16-7-12.
 */
public interface RegisterView extends MvpView{

    void showProgress();
    void hintProgress();
    void showMessage(String msg);
    void navigateToHome();
}
