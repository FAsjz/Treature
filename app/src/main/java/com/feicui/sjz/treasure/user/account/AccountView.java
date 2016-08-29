package com.feicui.sjz.treasure.user.account;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by Administrator on 16-7-18.
 */
public interface AccountView extends MvpView{

    public void showProgress();
    public void hintProgress();
    public void showMessage(String msg);
    public void upDataPhoto(String photoUrl);
}
