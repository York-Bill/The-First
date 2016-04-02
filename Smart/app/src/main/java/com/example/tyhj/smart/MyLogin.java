package com.example.tyhj.smart;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import Api_sours.StatusBarUtil;

/**
 * Created by Tyhj on 2016/4/2.
 */
public class MyLogin extends Activity {
    LinearLayout ll;
    ImageView iv_login_bg;
    Button bt_register,bt_login,bt_forgetPas;
    Animation av,bc;
    Thread thread;
    ImageView iv_user,iv_pas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        initWidget();
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        initThread();
        clik();
        thread.start();
    }

    private void initWidget() {
        av=AnimationUtils.loadAnimation(MyLogin.this,R.anim.login_bg_move);
        bc=AnimationUtils.loadAnimation(MyLogin.this, R.anim.login_bg_moveback);
        ll= (LinearLayout) findViewById(R.id.ll);
        iv_user= (ImageView) findViewById(R.id.iv_user);
        iv_pas= (ImageView) findViewById(R.id.iv_pas);
        iv_login_bg= (ImageView) findViewById(R.id.bg_login);
        bt_register= (Button) findViewById(R.id.bt_register_1);
        bt_forgetPas= (Button) findViewById(R.id.bt_forgetPas);
        bt_login= (Button) findViewById(R.id.bt_login_1);
        av.setFillAfter(true);
        bc.setFillAfter(true);
        Picasso.with(MyLogin.this).load(R.drawable.im_user).
                resize(60, 60).centerCrop().into(iv_user);
        Picasso.with(MyLogin.this).load(R.drawable.im_pas).
                resize(60, 60).centerCrop().into(iv_pas);
    }

    private void initThread() {
        thread=new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    Message ms=new Message();
                    ms.what=1;
                    handler.sendMessage(ms);
                    try {
                        Thread.sleep(30000);
                        Message ms1=new Message();
                        ms1.what=2;
                        handler.sendMessage(ms1);
                        Thread.sleep(30000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    private void clik() {
        bt_register.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        bt_register.setTextColor(Color.parseColor("#ff3333"));
                        break;
                    case MotionEvent.ACTION_UP:
                        bt_register.setTextColor(Color.parseColor("#ffffff"));
                        break;
                }
                return false;
            }
        });
        bt_forgetPas.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        bt_forgetPas.setTextColor(Color.parseColor("#ff3333"));
                        break;
                    case MotionEvent.ACTION_UP:
                        bt_forgetPas.setTextColor(Color.parseColor("#ffffff"));
                        break;
                }
                return false;
            }
        });
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(MyLogin.this,MainActivity.class);
                startActivity(in);
                MyLogin.this.finish();
            }
        });
    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    iv_login_bg.startAnimation(av);
                    break;
                case 2:
                    iv_login_bg.startAnimation(bc);
                    break;
                default:
                    break;
            }
        }
    };
}
