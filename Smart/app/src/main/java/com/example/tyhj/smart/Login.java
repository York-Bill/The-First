package com.example.tyhj.smart;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

/**
 * Created by Tyhj on 2016/3/29.
 */
public class Login extends Activity {
    Button bt_register,bt_login,bt_forgetPas;
    ImageView iv_user,iv_pas;
    int width;
    int height;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_1);
        init();
        clik();
    }
    private void init() {
        WindowManager wm = (WindowManager) Login.this
                .getSystemService(Context.WINDOW_SERVICE);
         width = wm.getDefaultDisplay().getWidth();
         height = wm.getDefaultDisplay().getHeight();
        iv_user= (ImageView) findViewById(R.id.iv_user);
        iv_pas= (ImageView) findViewById(R.id.iv_pas);
        bt_register= (Button) findViewById(R.id.bt_register_1);
        bt_forgetPas= (Button) findViewById(R.id.bt_forgetPas);
        bt_login= (Button) findViewById(R.id.bt_login_1);
        Picasso.with(Login.this).load(R.drawable.im_user).
                resize(width / 20, height/20).centerCrop().into(iv_user);
        Picasso.with(Login.this).load(R.drawable.im_pas).
                resize(width / 20, height / 20).centerCrop().into(iv_pas);
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
                Intent in=new Intent(Login.this,MainActivity.class);
                startActivity(in);
                Login.this.finish();
            }
        });
    }
}
