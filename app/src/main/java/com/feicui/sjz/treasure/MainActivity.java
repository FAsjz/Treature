package com.feicui.sjz.treasure;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.feicui.sjz.treasure.commons.ActivityUtils;
import com.feicui.sjz.treasure.user.login.LoginActivity;
import com.feicui.sjz.treasure.user.regist.RegisterActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private ActivityUtils activityUtils;
    public final static String ACTIION_ENTER_HOME = "action_enter_home";

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils = new ActivityUtils(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        IntentFilter intentFilter = new IntentFilter(MainActivity.ACTIION_ENTER_HOME);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver,intentFilter);
    }
    @OnClick({R.id.btn_Register , R.id.btn_Login})
    public void click(View view){
        switch (view.getId()){
            case R.id.btn_Login :
                activityUtils.startActivity(LoginActivity.class);
                break;
            case R.id.btn_Register :
                activityUtils.startActivity(RegisterActivity.class);
                break;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
