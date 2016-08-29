package com.feicui.sjz.treasure.user.regist;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;


import com.feicui.sjz.treasure.MainActivity;
import com.feicui.sjz.treasure.R;
import com.feicui.sjz.treasure.commons.ActivityUtils;
import com.feicui.sjz.treasure.commons.RegexUtils;
import com.feicui.sjz.treasure.components.AlertDialogFragment;
import com.feicui.sjz.treasure.treasure.home.HomeActivity;
import com.feicui.sjz.treasure.user.User;
import com.hannesdorfmann.mosby.mvp.MvpActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class RegisterActivity extends MvpActivity<RegisterView,RegisterPretenter> implements RegisterView{
    @Bind(R.id.et_Username) EditText etUsername;
    @Bind(R.id.et_Password) EditText etPassword;
    @Bind(R.id.et_Confirm) EditText etConfirm;
    @Bind(R.id.btn_Register)
    Button btnRegister;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private String username; // 用来保存编辑框内的用户名
    private String password; // 用来保存编辑框内的密码

    private ActivityUtils activityUtils;// Activity常用工具集

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils = new ActivityUtils(this);
        setContentView(R.layout.activity_register);
    }

    @Override public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        etConfirm.addTextChangedListener(mTextWatcher); // EditText监听
        etPassword.addTextChangedListener(mTextWatcher); // EditText监听
        etUsername.addTextChangedListener(mTextWatcher); // EditText监听
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getTitle());
        }
    }

    @NonNull
    @Override
    public RegisterPretenter createPresenter() {
        return new RegisterPretenter();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private final TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            username = etUsername.getText().toString();
            password = etPassword.getText().toString();
            String confirm = etConfirm.getText().toString();
            boolean canRegister = !TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)
                    && password.equals(confirm);
            btnRegister.setEnabled(canRegister);// 注意：在布局内注册按钮默认是不可用的
        }
    };

    @OnClick(R.id.btn_Register)
    public void register() {
        // 正则进行判断输入的用户名是否有效
        if (RegexUtils.verifyUsername(username) != RegexUtils.VERIFY_SUCCESS) {
            showUserNameError();
            return;
        }
        // 正则进行判断输入的密码是否有效
        if (RegexUtils.verifyPassword(password) != RegexUtils.VERIFY_SUCCESS) {
            showPasswordError();
            return;
        }
        // 执行注册业务逻辑
        getPresenter().Register(new User(username,password));
    }
    public void showUserNameError(){
        String title = getString(R.string.username_error);
        String msg = getString(R.string.username_rules);
        AlertDialogFragment alertDialogFragment = AlertDialogFragment.instance(title,msg);
        alertDialogFragment.show(getFragmentManager(),"showUserNameError");
        etUsername.setText("");
        etPassword.setText("");
        etConfirm.setText("");

    }
    public void showPasswordError(){
        String title = getString(R.string.password_error);
        String msg = getString(R.string.password_rules);
        AlertDialogFragment alertDialogFragment = AlertDialogFragment.instance(title,msg);
        alertDialogFragment.show(getFragmentManager(), "showPasswordError");
        etUsername.setText("");
        etPassword.setText("");
        etConfirm.setText("");
    }
    private ProgressDialog progressDialog;
    @Override
    public void showProgress() {
        activityUtils.hideSoftKeyboard();
        progressDialog = ProgressDialog.show(this,"","正在注册...");
    }

    @Override
    public void hintProgress() {

        progressDialog.dismiss();
    }

    @Override
    public void showMessage(String msg) {
        activityUtils.showToast(msg);
    }

    @Override
    public void navigateToHome() {
        activityUtils.startActivity(HomeActivity.class);
        finish();
        Intent it = new Intent(MainActivity.ACTIION_ENTER_HOME);
        LocalBroadcastManager.getInstance(this).sendBroadcast(it);
    }
}

