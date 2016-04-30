package com.example.tyhj.smart;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

import Api_sours.StatusBarUtil;
import dataBase.MyDateBase;
import savephoto.GetModelHeadImage;

/**
 * Created by Tyhj on 2016/4/2.
 */
public class MyLogin extends Activity {
    ImageButton ib_user_headImage;
    LinearLayout ll;
    ImageView iv_login_bg;
    Button bt_register,bt_login,bt_forgetPas;
    Animation av,bc;
    Thread thread;
    EditText et_number_1,et_password_1;
    ImageView iv_user,iv_pas;
    MyDateBase mydateBase;
    int headImage=0;
    boolean ifShowImage;
    String phonenumber,email;
    int[] headimage=GetModelHeadImage.userHeadImage;
    boolean ifInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AVOSCloud.initialize(this, "fsbc53ABzyGIW7BMeCIVhlk4-gzGzoHsz", "i0tswVfwEsaEBR06nKmBRxxV");
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        initWidget();
        initThread();
        clik();
        thread.start();
    }

    private void initWidget() {
        ib_user_headImage= (ImageButton) findViewById(R.id.ib_user_headImage);
        et_password_1= (EditText) findViewById(R.id.et_password_1);
        et_number_1= (EditText) findViewById(R.id.et_number_1);
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
        mydateBase=new MyDateBase(this,GetModelHeadImage.getUserId()+".db",null,1);
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
                        Thread.sleep(38000);
                        Message ms1=new Message();
                        ms1.what=2;
                        handler.sendMessage(ms1);
                        Thread.sleep(38000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    private void clik() {
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        et_number_1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    ifInput=true;
                }else {
                    if(ifInput){
                        MyDateBase myDateBase=new MyDateBase(MyLogin.this,et_number_1.getText().toString()+".db",null,1);
                        SQLiteDatabase sqLiteDatabase=myDateBase.getWritableDatabase();
                        Cursor cursor=sqLiteDatabase.rawQuery("select * from User where id=?",new String[]{et_number_1.getText().toString()});
                        if(cursor.moveToNext()){
                            headImage=cursor.getInt(8);
                            ib_user_headImage.setBackgroundResource(GetModelHeadImage.userHeadImage[headImage]);
                        }
                    }
                }
            }
        });
        //更改头像
        ib_user_headImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                headImage++;
                ib_user_headImage.setBackgroundResource(headimage[headImage]);
                if(headImage==(headimage.length-1)){
                    headImage=0;
                }
            }
        });
        //注册
        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(MyLogin.this,Register.class);
                startActivity(in);
            }
        });
        bt_forgetPas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(MyLogin.this,ForgetPas.class);
                startActivity(in);
            }
        });
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
        //登陆
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(!et_number_1.getText().toString().equals("")&&!et_number_1.getText().toString().equals("")){
                    AVUser.logInInBackground(et_number_1.getText().toString(),
                            et_password_1.getText().toString(), new LogInCallback() {
                                public void done(AVUser user, AVException e) {
                                    if (e == null) {
                                        Intent in=new Intent(MyLogin.this,MainActivity.class);
                                        GetModelHeadImage.setUserId(et_number_1.getText().toString());
                                        save();
                                        startActivity(in);
                                        MyLogin.this.finish();
                                    } else {
                                        // 登录失败
                                        loginFail(e,v);
                                    }
                                }
                            });
                }else{
                    Snackbar.make(v, "账号或密码不能为空", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                }
            }
        });
    }

    private void save() {
        email = AVUser.getCurrentUser().getEmail();
        MyDateBase mydateBase = new MyDateBase(this, et_number_1.getText().toString() + ".db", null, 1);
        SQLiteDatabase sqLiteDatabase = mydateBase.getWritableDatabase();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
        String data = df.format(new Date()).substring(0, 19);
        String date = data.substring(0, 4) + "/" + data.substring(5, 7) + "/"
                + data.substring(8, 10) + "  " + data.substring(11, 13);
        Cursor cursor = sqLiteDatabase.rawQuery("select * from User where id=?", new String[]{et_number_1.getText().toString()});
        if (!cursor.moveToNext()){
            sqLiteDatabase.execSQL("insert into User values (?,?,?,?,?,?,?,?,?)", new Object[]{
                    et_number_1.getText().toString(),
                    et_number_1.getText().toString(),
                    "这个同学很懒，什么都没有留下~",
                    et_number_1.getText().toString(),
                    et_password_1.getText().toString(),
                    date,
                    0,
                    email,
                    headImage
            });
    }else{
            sqLiteDatabase.execSQL("update User set password=?",new String[]{et_password_1.getText().toString()});
            sqLiteDatabase.execSQL("update User set headImage=?",new Object[]{headImage});
        }
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
    private void loginFail(AVException e,View v) {
        if (e.getMessage()
                .equals("java.net.UnknownHostException")) {
            Snackbar.make(v, "网络出错，请检查网络连接", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
        } else if (e.getMessage()
                .substring(8, 11).equals("211")) {
            Snackbar.make(v, "未找到该账号", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
        } else if (e.getMessage()
                .substring(8, 11).equals("210")) {
            Snackbar.make(v, "账号或密码错误", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
        } else if (e.getMessage()
                .substring(8, 11).equals("216")) {
            Snackbar.make(v, "请先到邮箱完成邮箱验证", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
