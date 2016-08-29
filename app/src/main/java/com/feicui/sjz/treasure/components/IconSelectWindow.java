package com.feicui.sjz.treasure.components;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.feicui.sjz.treasure.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/7/17.
 */
public class IconSelectWindow extends PopupWindow {

    private final Activity activity;
    private final Listener listener;
    public interface Listener{
        void camera();
        void gallery();
    }
    public  IconSelectWindow(Activity activity,Listener listener){
        super(activity.getLayoutInflater().inflate(R.layout.window_select_icon,null)
        ,ViewGroup.LayoutParams.MATCH_PARENT
        ,ViewGroup.LayoutParams.WRAP_CONTENT
        ,true);
        ButterKnife.bind(this,getContentView());
        this.listener = listener;
        this.activity = activity;

        setBackgroundDrawable(new BitmapDrawable());

    }
    public void show(){
        showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }
    @OnClick({R.id.btn_camera,R.id.btn_gallery,R.id.btn_cancel})
    public void onClick(View view){
        dismiss();
        switch (view.getId()){
            case R.id.btn_cancel:
                break;
            case R.id.btn_gallery:
                listener.gallery();
                break;
            case R.id.btn_camera:
                listener.camera();
                break;

        }

    }

}
