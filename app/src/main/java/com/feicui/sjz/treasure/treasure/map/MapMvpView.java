package com.feicui.sjz.treasure.treasure.map;

import com.feicui.sjz.treasure.treasure.Treasure;
import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

/**
 * Created by Administrator on 16-7-20.
 */
public interface MapMvpView extends MvpView{

    public void showMessage(String msg);
    public void setData(List<Treasure> data);
}
