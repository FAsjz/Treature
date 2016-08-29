package com.feicui.sjz.treasure.user.account;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.feicui.sjz.treasure.R;
import com.feicui.sjz.treasure.commons.ActivityUtils;
import com.feicui.sjz.treasure.components.IconSelectWindow;
import com.feicui.sjz.treasure.user.UserPrefs;
import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.pkmmte.view.CircularImageView;

import org.hybridsquad.android.library.CropHandler;
import org.hybridsquad.android.library.CropHelper;
import org.hybridsquad.android.library.CropParams;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AccountActivity extends MvpActivity<AccountView,AccountPresenter> implements AccountView {

    private ActivityUtils activityUtils;
    private IconSelectWindow iconSelectWindow;


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.iv_userIcon)
    CircularImageView
    imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils = new ActivityUtils(this);
        setContentView(R.layout.activity_account);

        String photoUrl = UserPrefs.getInstance().getPhoto();
        if (photoUrl != null) {
            ImageLoader.getInstance().displayImage(photoUrl,imageView);
        }
    }



    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getTitle());
        }

    }

    @NonNull
    @Override
    public AccountPresenter createPresenter() {
        return new AccountPresenter();
    }

    @OnClick(R.id.iv_userIcon)
    public void onClick() {
        if (iconSelectWindow == null) {
            iconSelectWindow = new IconSelectWindow(this,listener);
        }
        if (iconSelectWindow.isShowing()) {
            iconSelectWindow.dismiss();
            return;
        }
        iconSelectWindow.show();

    }
    private IconSelectWindow.Listener listener = new IconSelectWindow.Listener() {
        @Override
        public void camera() {
            CropHelper.clearCachedCropFile(cropHandler.getCropParams().uri);
            Intent intent = CropHelper.buildCaptureIntent(cropHandler.getCropParams().uri);
            startActivityForResult(intent, CropHelper.REQUEST_CAMERA);
        }

        @Override
        public void gallery() {
            CropHelper.clearCachedCropFile(cropHandler.getCropParams().uri);
            Intent intent = CropHelper.buildCropFromGalleryIntent(cropHandler.getCropParams());
            startActivityForResult(intent,CropHelper.REQUEST_CROP);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        CropHelper.handleResult(cropHandler,requestCode, resultCode, data);
    }

    private CropHandler cropHandler = new CropHandler() {
        @Override
        public void onPhotoCropped(Uri uri) {
            File file = new File(uri.getPath());
//            activityUtils.showToast(file.getPath());
            Log.e("File0", file.getPath());
            getPresenter().upLoadPhoto(file);
        }

        @Override
        public void onCropCancel() {

        }

        @Override
        public void onCropFailed(String message) {
//            activityUtils.showToast(message);
            activityUtils.showToast("裁剪错误");
        }

        @Override
        public CropParams getCropParams() {
            CropParams cropParams = new CropParams();
//            cropParams.aspectX = 300;
//            cropParams.aspectY = 300;
            return cropParams;
        }

        @Override
        public Activity getContext() {
            return AccountActivity.this;
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        if (cropHandler.getCropParams().uri != null) {
            CropHelper.clearCachedCropFile(cropHandler.getCropParams().uri);
        }
        super.onDestroy();
    }
    private ProgressDialog dialog;
    @Override
    public void showProgress() {
        if (dialog == null)
        dialog = ProgressDialog.show(this,"","正在加载...");
    }

    @Override
    public void hintProgress() {
        dialog.dismiss();
    }

    @Override
    public void showMessage(String msg) {
        activityUtils.showToast(msg);
    }

    @Override
    public void upDataPhoto(String photoUrl) {
        ImageLoader.getInstance().displayImage(photoUrl,imageView);

    }
}
