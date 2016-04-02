package com.example.tyhj.smart;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.baidu.voicerecognition.android.ui.BaiduASRDigitalDialog;
import com.baidu.voicerecognition.android.ui.DialogRecognitionListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;

import Api_sours.Config;
import Api_sours.StatusBarUtil;
import fragement.Activity_3;
import fragement.Activity_1;
import fragement.Activity_2;
import twoCode.activity.CaptureActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ViewPager vp_home;
    View headerView;
    ImageButton im_1,im_2,im_3;
    ImageView menu_bg;
    DrawerLayout drawer;
    private static final int TAB_COUNT = 3;
    Intent intent_TwoCode,intent_Suggest,intent_Login;
    Thread thread_voice,thread_TowCode,thread_Suggest,thread_Login;
    //语音
    private DialogRecognitionListener mRecognitionListener;
    BaiduASRDigitalDialog mDialog=null;

    //
    int[] menubg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        mRecognitionListener = new DialogRecognitionListener() {

            @Override
            public void onResults(Bundle results) {
                ArrayList<String> rs = results != null ? results
                        .getStringArrayList(RESULTS_RECOGNITION) : null;
                if (rs != null && rs.size() > 0) {
                    Toast.makeText(MainActivity.this,rs.get(0),Toast.LENGTH_SHORT).show();
                }

            }
        };
        initMenu();
        initArray();
        initWidget();
        initThread();
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
        initThread();
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
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
        switch (id){
            case R.id.nav_camera:
                new Thread(thread_TowCode).start();
                intent_TwoCode=new Intent(MainActivity.this, CaptureActivity.class);
                break;
            case R.id.nav_gallery:
                startVoice();
                new Thread(thread_voice).start();
                break;
            case R.id.logout:
                new Thread(thread_Login).start();
                intent_Login=new Intent(MainActivity.this,Login.class);
                break;
            case R.id.nav_manage:

                break;
            case R.id.nav_share:
                share();
                Toast.makeText(MainActivity.this,R.string.share_connction,Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_send:
                new Thread(thread_Suggest).start();
                intent_Suggest=new Intent(MainActivity.this,Suggest.class);
                break;
            default:
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    //识别开始
    private void startVoice() {
            if (mDialog != null) {
                mDialog.dismiss();
            }
            // 参数，其中apiKey和secretKey为必须配置参数，其他根据实际需要配置
            Bundle params = new Bundle();
            // 配置apkKey
            params.putString(BaiduASRDigitalDialog.PARAM_API_KEY, "hBgoW6nNWAqMbTXWxeDkvlQB");
            // 配置secretKey
            params.putString(BaiduASRDigitalDialog.PARAM_SECRET_KEY, "bef55254cb72adad89232aec29d49419");
            params.putInt(BaiduASRDigitalDialog.PARAM_DIALOG_THEME, BaiduASRDigitalDialog.THEME_BLUE_LIGHTBG);
            // 创建百度语音识别对话框
            mDialog = new BaiduASRDigitalDialog(this, params);
            mDialog.setDialogRecognitionListener(mRecognitionListener);
            mDialog.getParams().putInt(BaiduASRDigitalDialog.PARAM_PROP, Config.CURRENT_PROP);
            mDialog.getParams().putString(BaiduASRDigitalDialog.PARAM_LANGUAGE,
                    Config.getCurrentLanguage());
            Log.e("DEBUG", "Config.PLAY_START_SOUND = " + Config.PLAY_START_SOUND);
            mDialog.getParams().putBoolean(BaiduASRDigitalDialog.PARAM_START_TONE_ENABLE, Config.PLAY_START_SOUND);
            mDialog.getParams().putBoolean(BaiduASRDigitalDialog.PARAM_END_TONE_ENABLE, Config.PLAY_END_SOUND);
            mDialog.getParams().putBoolean(BaiduASRDigitalDialog.PARAM_TIPS_TONE_ENABLE, Config.DIALOG_TIPS_SOUND);
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
    //线程初始化
    private void initThread() {
        thread_voice=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(270);
                    Message message1=new Message();
                    message1.what=1;
                    handler.sendMessage(message1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread_TowCode=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(270);
                    Message message1=new Message();
                    message1.what=2;
                    handler.sendMessage(message1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread_Suggest=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(270);
                    Message message1=new Message();
                    message1.what=3;
                    handler.sendMessage(message1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread_Login=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(270);
                    Message message1=new Message();
                    message1.what=4;
                    handler.sendMessage(message1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    //复制链接到粘贴板
    private void share(){
        ClipboardManager clipboard = (ClipboardManager)
                getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("simple text", "Hello, World!");
        clipboard.setPrimaryClip(clip);
    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    mDialog.show();
                    break;
                case 2:
                    startActivityForResult(intent_TwoCode,0);
                    overridePendingTransition(R.anim.activity_in_right, R.anim.activity_out_left);
                    break;
                case 3:
                    startActivity(intent_Suggest);
                    overridePendingTransition(R.anim.activity_in_right, R.anim.activity_out_left);
                    break;
                case 4:
                    startActivity(intent_Login);
                    overridePendingTransition(R.anim.activity_in_left, R.anim.activity_out_right);
                    MainActivity.this.finish();
                default:
                    break;
            }
        }
    };
}
