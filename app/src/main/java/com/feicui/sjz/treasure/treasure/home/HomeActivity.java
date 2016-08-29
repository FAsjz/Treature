package com.feicui.sjz.treasure.treasure.home;

import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;


import com.feicui.sjz.treasure.R;
import com.feicui.sjz.treasure.commons.ActivityUtils;
import com.feicui.sjz.treasure.treasure.TreasureRepo;
import com.feicui.sjz.treasure.treasure.list.TreasureListFragment;
import com.feicui.sjz.treasure.treasure.map.MapFragment;
import com.feicui.sjz.treasure.user.account.AccountActivity;
import com.feicui.sjz.treasure.user.UserPrefs;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityUtils activityUtils;
    private ButterKnife butterKnife;
    private ImageView imageView;
    private FragmentManager fragmentManager;
    private MapFragment mapFragment;
    private TreasureListFragment listFragment;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.nav_view)
    NavigationView navigationView;
    @Bind(R.id.fragment_container)FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        fragmentManager = getSupportFragmentManager();
        mapFragment = (MapFragment) fragmentManager.findFragmentById(R.id.mapFragment);
        TreasureRepo.getInstance().clear();
    }

    @Override
    protected void onStart() {
        super.onStart();
        String photoUrl = UserPrefs.getInstance().getPhoto();
        if (photoUrl != null) {
            ImageLoader.getInstance().displayImage(photoUrl,imageView);
        }
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        butterKnife.bind(this);
        activityUtils = new ActivityUtils(this);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        imageView = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.iv_userIcon);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityUtils.startActivity(AccountActivity.class);
            }
        });


    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_hide :
//                activityUtils.showToast(R.string.hide_treasure);
                mapFragment.goHideTreasure();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.menu_item_my_list :
                activityUtils.showToast(R.string.my_list);
                break;
            case R.id.menu_item_help :
                activityUtils.showToast(R.string.about_help);
                break;
            case R.id.menu_item_logout :
                activityUtils.showToast(R.string.log_out);
                break;
        }


        // 返回true,当前选项变为checked状态
        return false;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_toggle);
        if (listFragment != null && listFragment.isAdded()) {
            item.setIcon(R.drawable.ic_map);
        }else {
            item.setIcon(R.drawable.ic_view_list);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_toggle:
                showListFragment();
                // 通过此方法,将使得onPrepareOptionsMenu方法得到触发
                invalidateOptionsMenu();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showListFragment() {
        if (listFragment != null && listFragment.isAdded()) {
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragmentManager.beginTransaction().remove(listFragment).commit();
            return;
        }
        listFragment = new TreasureListFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, listFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return;
        }
        if (!mapFragment.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }
}
