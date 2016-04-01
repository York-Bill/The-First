package com.example.tyhj.smart;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Random;

import Api_sours.StatusBarUtil;
import fragement.Activity_3;
import fragement.Activity_1;
import fragement.Activity_2;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
        ViewPager vp_home;
    View headerView;
    ImageButton im_1,im_2,im_3;
    ImageView menu_bg;
    private static final int TAB_COUNT = 3;
    int[] menubg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initMenu();
        initArray();
        initWidget();
    }
    //初始化数组
    private void initArray() {
        menubg=new int[8];
        menubg[0]=R.drawable.bg_menu_0;
        menubg[1]=R.drawable.bg_menu_1;
        menubg[2]=R.drawable.bg_menu_2;
        menubg[3]=R.drawable.bg_menu_3;
        menubg[4]=R.drawable.bg_menu_5;
        menubg[5]=R.drawable.bg_menu_8;
        menubg[6]=R.drawable.bg_menu_11;
        menubg[7]=R.drawable.bg_menu_13;

    }
    //初始化控件
    private void initWidget() {
        vp_home= (ViewPager) findViewById(R.id.vp_home);
        vp_home.setOffscreenPageLimit(3);
        im_1= (ImageButton) findViewById(R.id.ib_1);
        im_2= (ImageButton) findViewById(R.id.ib_2);
        im_3= (ImageButton) findViewById(R.id.ib_3);
        menu_bg= (ImageView) headerView.findViewById(R.id.menu_bg);
        onClick();
        setIcon();
        initViewPager();
    }
    //初始化viewpager
    private void initViewPager() {
        vp_home.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return new Activity_1();
                    case 1:
                        return new Activity_2();
                    case 2:
                        return new Activity_3();
                    default:
                        break;
                }
                return null;
            }

            @Override
            public int getCount() {
                return TAB_COUNT;
            }
        });
    }

    //设置点击事件
    private void onClick() {
        im_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vp_home.setCurrentItem(0, false);
            }
        });
        im_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vp_home.setCurrentItem(1, false);
            }
        });
        im_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vp_home.setCurrentItem(2, false);
            }
        });
        menu_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                int num = random.nextInt(7);
                Picasso.with(MainActivity.this)
                        .load(menubg[num])
                        .into(menu_bg);
            }
        });
    }

    //设置小图标
    private void setIcon() {
        Picasso.with(MainActivity.this)
                .load(R.drawable.ic_fly)
                .resize(80,80)
                .centerCrop()
                .into(im_1);
        Picasso.with(MainActivity.this)
                .load(R.drawable.ic_home)
                .resize(80,80)
                .centerCrop()
                .into(im_2);
        Picasso.with(MainActivity.this)
                .load(R.drawable.ic_set)
                .resize(80,80)
                .centerCrop()
                .into(im_3);
        Picasso.with(MainActivity.this)
                .load(R.drawable.bg_menu_0)
                .into(menu_bg);
    }

    //初始化侧栏菜单
    private void initMenu() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.menu);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        headerView = navigationView.getHeaderView(0);
        //StatusBarUtil.setColor(MainActivity.this, );
        StatusBarUtil.setColorForDrawerLayout(MainActivity.this, drawer, Color.parseColor("#2a9e09"), 2);
    }

    //侧栏菜单返回键的监听
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    //侧栏菜单的监听
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        //二维码
        if (id == R.id.nav_camera) {

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private long exitTime = 0;
    //监听返回键，退出系统
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK){

            if((System.currentTimeMillis()- exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                MainActivity.this.finish();
            }
            return true;
        }else{
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("result");
            Toast.makeText(MainActivity.this,scanResult,Toast.LENGTH_SHORT).show();
        }
    }
}
