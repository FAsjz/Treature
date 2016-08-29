package com.feicui.sjz.treasure.treasure.detail;

import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

/**
 * Created by Administrator on 16-7-22.
 */
public interface DetailTreasureView extends MvpView{
    void showMessage(String msg);
    void setData(List<DetailTreasureResult> data);
}
