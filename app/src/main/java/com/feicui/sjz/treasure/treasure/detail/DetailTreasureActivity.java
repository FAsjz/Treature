package com.feicui.sjz.treasure.treasure.detail;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ScrollView;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.navi.BaiduMapAppNotSupportNaviException;
import com.baidu.mapapi.navi.BaiduMapNavigation;
import com.baidu.mapapi.navi.NaviParaOption;
import com.baidu.mapapi.utils.OpenClientUtil;
import com.feicui.sjz.treasure.R;
import com.feicui.sjz.treasure.commons.ActivityUtils;
import com.feicui.sjz.treasure.components.TreasureView;
import com.feicui.sjz.treasure.treasure.Treasure;
import com.feicui.sjz.treasure.treasure.map.MapFragment;
import com.hannesdorfmann.mosby.mvp.MvpActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailTreasureActivity extends MvpActivity<DetailTreasureView,DetailTreasurePresenter> implements DetailTreasureView {
    private ActivityUtils activityUtils;
    private Treasure treasure;
    private static final String KEY_TREASURE = "key_treasure";
    private final BitmapDescriptor iconExpanded = BitmapDescriptorFactory.fromResource(R.drawable.treasure_expanded);
    public static void open(@NonNull Context context,@NonNull Treasure treasure){
        Intent intent = new Intent(context,DetailTreasureActivity.class);
        intent.putExtra(KEY_TREASURE,treasure);
        context.startActivity(intent);

    }
    @Bind(R.id.toolbar)Toolbar toolbar;
    @Bind(R.id.treasureView)TreasureView treasureView;
    @Bind(R.id.frameLayout)FrameLayout frameLayout;
    @Bind(R.id.tv_detail_description)TextView tvDescription;
    @Bind(R.id.scrollView)ScrollView scrollView;
    @Bind(R.id.iv_navigation)ImageView navigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils = new ActivityUtils(this);
        setContentView(R.layout.activity_treasure_detail);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        treasure = (Treasure)getIntent().getSerializableExtra(KEY_TREASURE);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(treasure.getTitle());
        }
        treasureView.bindTreasure(treasure);
        initBaiduMap();
        //宝藏的详细信息
        DetailTreasure detailTreasure = new DetailTreasure(treasure.getId());
        getPresenter().getDetailTreasure(detailTreasure);
        scrollView.scrollTo(0, tvDescription.getBottom());

    }
    @OnClick(R.id.iv_navigation)
    public void showPopupMenu(View view){
        PopupMenu popupMenu = new PopupMenu(this,view);
        popupMenu.setOnMenuItemClickListener(onMenuItemClickListener);
        popupMenu.inflate(R.menu.menu_navigation);
        popupMenu.show();
    }
    private PopupMenu.OnMenuItemClickListener onMenuItemClickListener = new PopupMenu.OnMenuItemClickListener() {

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            LatLng startPt = MapFragment.myLocation;
            String startAdr = MapFragment.getMyAddress();
            Log.e("DetailTreasureActivity","startAdr:" + startAdr);
            LatLng endPt = new LatLng(treasure.getLatitude(), treasure.getLongitude());
            String endAdr = treasure.getLocation();
            Log.e("DetailTreasureActivity","endAdr:" + endAdr);
            switch (item.getItemId()) {
                case R.id.walking_navi:
                    startWalkingNavi(startPt,startAdr,endPt,endAdr);
                    break;
                case R.id.biking_navi:
                    startBikingNavi(startPt, startAdr,endPt,endAdr);
                    break;
            }

            return false;
        }
    };
        @NonNull
    @Override
    public DetailTreasurePresenter createPresenter() {
        return new DetailTreasurePresenter();
    }

    private void initBaiduMap() {
        LatLng latLng = new LatLng(treasure.getLatitude(),treasure.getLongitude());
        MapStatus status = new MapStatus.Builder()
                .target(latLng)
                .zoom(19)
                .overlook(-20)
                .build();
        BaiduMapOptions option = new BaiduMapOptions()
                .mapStatus(status)
                .compassEnabled(false)// compass
                .scrollGesturesEnabled(false)   // scroll
                .zoomControlsEnabled(false)// zoom
                .zoomGesturesEnabled(false)// zoom
                .rotateGesturesEnabled(false)//rotate
                .scaleControlEnabled(false);//scale
        MapView mapView = new MapView(this,option);
        frameLayout.addView(mapView,0);
        //添加标记marker
        BaiduMap baiduMap = mapView.getMap();
        MarkerOptions markerOptions = new MarkerOptions()
                .icon(iconExpanded)
                .anchor(0.5f,0.5f)
                .position(latLng);
        baiduMap.addOverlay(markerOptions);

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

    @Override
    public void showMessage(String msg) {
        activityUtils.showToast(msg);
    }

    @Override
    public void setData(List<DetailTreasureResult> data) {
        if (data.size() >= 1) {
            DetailTreasureResult detailTreasureResult = data.get(0);
            tvDescription.setText(detailTreasureResult.description);
            return;
        }
        activityUtils.showToast("无宝藏描述信息");
    }
    /**
     * 启动百度地图步行导航(Native)
     */
    public void startWalkingNavi(LatLng startPt, String startAdr, LatLng endPt, String endAdr) {
        // 构建 导航参数
        NaviParaOption para = new NaviParaOption()
                .startPoint(startPt).endPoint(endPt)
                .startName(startAdr).endName(endAdr);
        try {
            BaiduMapNavigation.openBaiduMapWalkNavi(para, this);
        } catch (BaiduMapAppNotSupportNaviException e) {
            e.printStackTrace();
        }
        if (!BaiduMapNavigation.openBaiduMapWalkNavi(para, this)) {
            startWebNavi(startPt, startAdr, endPt, endAdr);
        }

    }

    /**
     * 启动百度地图骑行导航(Native)
     */
    public void startBikingNavi(LatLng startPt, String startAdr, LatLng endPt, String endAdr) {
        // 构建 导航参数
        NaviParaOption para = new NaviParaOption()
                .startPoint(startPt).endPoint(endPt)
                .startName(startAdr).endName(endAdr);

        try {
            BaiduMapNavigation.openBaiduMapBikeNavi(para, this);
        } catch (BaiduMapAppNotSupportNaviException e) {
            e.printStackTrace();
        }
        if (!BaiduMapNavigation.openBaiduMapBikeNavi(para, this)) {
            showDialog();
        }
    }
    /**
     * 启动百度地图导航(Web)
     */
    public void startWebNavi(LatLng startPt, String startAdr, LatLng endPt, String endAdr) {
        // 构建 导航参数
        NaviParaOption para = new NaviParaOption()
                .startPoint(startPt).endPoint(endPt)
                .startName(startAdr).endName(endAdr);

        BaiduMapNavigation.openWebBaiduMapNavi(para, this);
    }

    /**
     * 提示未安装百度地图app或app版本过低
     */
    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("您尚未安装百度地图app或app版本过低，点击确认安装？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                OpenClientUtil.getLatestBaiduMapApp(DetailTreasureActivity.this);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
