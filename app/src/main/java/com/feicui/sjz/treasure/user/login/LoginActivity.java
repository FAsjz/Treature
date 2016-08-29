package com.feicui.sjz.treasure.user.login;

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
import com.feicui.sjz.treasure.components.AlertDialogFragment;

import com.feicui.sjz.treasure.MainActivity;
import com.feicui.sjz.treasure.R;
import com.feicui.sjz.treasure.commons.ActivityUtils;
import com.feicui.sjz.treasure.commons.RegexUtils;
import com.feicui.sjz.treasure.treasure.home.HomeActivity;
import com.feicui.sjz.treasure.user.User;
import com.hannesdorfmann.mosby.mvp.MvpActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends MvpActivity<LoginView,LoginPresenter> implements LoginView{

    private ActivityUtils activityUtils;
    private String UserName;
    private String Password;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.btn_Login) Button btnLogin;
    @Bind(R.id.et_Username) EditText etUsername;
    @Bind(R.id.et_Password) EditText etPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils = new ActivityUtils(this);
        setContentView(R.layout.activity_login);
        etPassword.addTextChangedListener(mTextWatcher);
        etUsername.addTextChangedListener(mTextWatcher);
    }
    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
             UserName = etUsername.getText().toString();
             Password = etPassword.getText().toString();
            if (!(TextUtils.isEmpty(UserName) || TextUtils.isEmpty(Password))){
                btnLogin.setEnabled(true);
            }

        }
    };

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getTitle());

        }

    }

    @NonNull
    @Override
    public LoginPresenter createPresenter() {
        return new LoginPresenter();
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

    @OnClick(R.id.btn_Login)
    public void login(){
        activityUtils.hideSoftKeyboard();
        if (RegexUtils.verifyUsername(UserName) != RegexUtils.VERIFY_SUCCESS){
            showUserNameError();
            return;
        }
        if (RegexUtils.verifyPassword(Password) != RegexUtils.VERIFY_SUCCESS){
            showPasswordError();
            return;
        }
        getPresenter().Login(new User(UserName,Password));
    }
    public void showUserNameError(){
        String title = getString(R.string.username_error);
        String msg = getString(R.string.username_rules);
        AlertDialogFragment alertDialogFragment = AlertDialogFragment.instance(title,msg);
        alertDialogFragment.show(getFragmentManager(),"showUserNameError");
        etUsername.setText("");

    }
    public void showPasswordError(){
        String title = getString(R.string.password_error);
        String msg = getString(R.string.password_rules);
        AlertDialogFragment alertDialogFragment = AlertDialogFragment.instance(title,msg);
        alertDialogFragment.show(getFragmentManager(), "showPasswordError");
        etPassword.setText("");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
    private ProgressDialog progressDialog;
    @Override
    public void showProgress() {

        progressDialog = ProgressDialog.show(this,"","正在登录...");
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
