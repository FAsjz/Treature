package com.feicui.sjz.treasure.treasure.hide;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.baidu.mapapi.model.LatLng;
import com.feicui.sjz.treasure.R;
import com.feicui.sjz.treasure.commons.ActivityUtils;
import com.feicui.sjz.treasure.treasure.TreasureRepo;
import com.feicui.sjz.treasure.treasure.home.HomeActivity;
import com.feicui.sjz.treasure.user.UserPrefs;
import com.hannesdorfmann.mosby.mvp.MvpActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HideTreasureActivity extends MvpActivity<HideTreasureView,HideTreasurePresenter> implements HideTreasureView {
    private static final String EXTRA_KEY_TITLE = "key_title";
    private static final String EXTRA_KEY_LOCATION = "key_location";
    private static final String EXTRA_KEY_LAT_LNG = "key_latlng";
    private static final String EXTRA_KEY_ALTITUDE = "key_altitude";

    /**
     * 进入当前Activity
     */
    public static void open(Context context, String title, String location, LatLng latLng, double altitude) {
        Intent intent = new Intent(context, HideTreasureActivity.class);
        intent.putExtra(EXTRA_KEY_TITLE, title);
        intent.putExtra(EXTRA_KEY_LOCATION, location);
        intent.putExtra(EXTRA_KEY_LAT_LNG, latLng);
        intent.putExtra(EXTRA_KEY_ALTITUDE, altitude);
        context.startActivity(intent);
    }

    private ActivityUtils activityUtils;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.et_description)
    EditText etDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils = new ActivityUtils(this);
        setContentView(R.layout.activity_hide_treasure);

    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getIntent().getStringExtra(EXTRA_KEY_TITLE));
        }

    }

    @NonNull
    @Override
    public HideTreasurePresenter createPresenter() {
        return new HideTreasurePresenter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_hide_treasure,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.action_send:
                LatLng latLng = getIntent().getParcelableExtra(EXTRA_KEY_LAT_LNG);
                double altitude = getIntent().getDoubleExtra(EXTRA_KEY_ALTITUDE, 0);
                String location = getIntent().getStringExtra(EXTRA_KEY_LOCATION);
                String title = getIntent().getStringExtra(EXTRA_KEY_TITLE);
                int tokenId = UserPrefs.getInstance().getToken();
                String descroption = etDescription.getText().toString();
                // 执行业务
                HideTreasure hideTreasure = new HideTreasure();
                hideTreasure.setLatitude(latLng.latitude);
                hideTreasure.setLongitude(latLng.longitude);
                hideTreasure.setAltitude(altitude);
                hideTreasure.setLocation(location);
                hideTreasure.setTitle(title);
                hideTreasure.setTokenId(tokenId);
                hideTreasure.setDescription(descroption);
                getPresenter().hideTreasure(hideTreasure);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private ProgressDialog dialog;
    @Override
    public void showProgress() {
        activityUtils.hideSoftKeyboard();
        if (dialog == null) {
        dialog = ProgressDialog.show(this, "", "正在埋藏宝藏,请稍候...");
        }
    }

    @Override
    public void hideProgress() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override
    public void showMessage(String msg) {
        activityUtils.showToast(msg);
    }

    @Override
    public void navigateToHome() {
        TreasureRepo.getInstance().clear();
        finish();
    }
}
