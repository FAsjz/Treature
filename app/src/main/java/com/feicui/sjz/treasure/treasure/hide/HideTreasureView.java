package com.feicui.sjz.treasure.treasure.hide;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by Administrator on 16-7-21.
 */
public interface HideTreasureView extends MvpView {
    public void showProgress();
    public void hideProgress();
    public void showMessage(String msg);
    public void navigateToHome();
}
