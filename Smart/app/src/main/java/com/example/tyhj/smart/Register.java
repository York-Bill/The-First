package com.example.tyhj.smart;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SignUpCallback;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import dataBase.MyDateBase;
import savephoto.GetModelHeadImage;

/**
 * Created by Tyhj on 2016/4/10.
 */
public class Register extends Activity {
    Button bt_creatUser;
    ImageButton ib_back_register;
    EditText et_register_phoneNumber,et_register_name;
    EditText et_register_email,et_register_password,et_register_password_check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        initWidget();
        setClik();
    }

    private void initWidget() {
        bt_creatUser= (Button) findViewById(R.id.bt_creatUser);
        ib_back_register= (ImageButton) findViewById(R.id.ib_back_register);
        et_register_phoneNumber= (EditText) findViewById(R.id.et_register_phoneNumber);
        et_register_name= (EditText) findViewById(R.id.et_register_name);
        et_register_email= (EditText) findViewById(R.id.et_register_email);
        et_register_password= (EditText) findViewById(R.id.et_register_password);
        et_register_password_check= (EditText) findViewById(R.id.et_register_password_check);
    }
    private void setClik() {
        //返回
        ib_back_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register.this.finish();
            }
        });
        //注册
        bt_creatUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(internet()){
                    register(v);
                }else{
                    Snackbar.make(v, "网络已断开，请连接到网络", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                }
            }
        });
    }
    //注册
    private void register(final View v) {
        if (input()) {
            Snackbar.make(v, "请正确输入必须信息", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
        } else if (et_register_phoneNumber.getText().toString().length() != 11) {
            Snackbar.make(v, "请输入正确的手机号码", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
        }else if(!et_register_password.getText().toString().equals(et_register_password_check.getText().toString())){
            Snackbar.make(v, "两次输入密码不一样", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
        }else if(et_register_password.getText().toString().length()<8){
            Snackbar.make(v, "密码必须不小于8位", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
        }else{
            //注册
            AVUser user = new AVUser();
            user.setUsername(et_register_phoneNumber.getText().toString());
            user.setMobilePhoneNumber(et_register_phoneNumber.getText().toString());
            user.setPassword(et_register_password.getText().toString());
            user.setEmail(et_register_email.getText().toString());
            user.signUpInBackground(new SignUpCallback() {
                public void done(AVException e) {
                    if (e == null) {
                        save_into_clude();
                        Toast.makeText(Register.this,"注册成功，请到邮箱验证",Toast.LENGTH_LONG).show();
                        Register.this.finish();
                    } else {
                        registerError(e, et_register_email, et_register_phoneNumber);
                    }
                }
            });
        }
    }
    //保存数据
    private void save_into_clude() {
        MyDateBase mydateBase=new MyDateBase(this,et_register_phoneNumber.getText().toString()+".db",null,1);
        SQLiteDatabase sqLiteDatabase=mydateBase.getWritableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("select * from User where id=?",new String[]{et_register_phoneNumber.getText().toString()});
        if (!cursor.moveToNext())
        saveIntoSqldataBase();
        saveIntoClude();
    }

    private void saveIntoClude() {

    }

    private void saveIntoSqldataBase() {
        Random random = new Random();
        int db_headcount = random.nextInt(GetModelHeadImage.userHeadImage.length);
        MyDateBase mydateBase=new MyDateBase(this,et_register_phoneNumber.getText().toString()+".db",null,1);
        SQLiteDatabase sqLiteDatabase=mydateBase.getWritableDatabase();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
        String data = df.format(new Date()).substring(0, 19);
        String date = data.substring(0, 4) + "/" + data.substring(5, 7) + "/"
                 + data.substring(8, 10) + "  " + data.substring(11, 13);
        sqLiteDatabase.execSQL("insert into User values (?,?,?,?,?,?,?,?)",new Object[]{
                et_register_phoneNumber.getText().toString(),
                et_register_name.getText().toString(),
                et_register_phoneNumber.getText().toString(),
                et_register_password.getText().toString(),
                date,
                0,
                et_register_email.getText().toString(),
                db_headcount
        });
    }

    //网络
    public boolean internet(){
        ConnectivityManager con=(ConnectivityManager)getSystemService(Activity.CONNECTIVITY_SERVICE);
        boolean internet=con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        boolean wifi=con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        if(internet||wifi) return true;
        else return false;
    }
    //错误提醒
    private void registerError(AVException e, EditText et_add_email, EditText et_add_name) {
        if (e.getMessage().equals(
                "java.net.UnknownHostException")) {
            Toast.makeText(getApplicationContext(),
                    "网络出错，请检查网络连接", Toast.LENGTH_SHORT).show();
        } else if (e.getMessage().substring(8, 11)
                .equals("203")) {
            Toast.makeText(getApplicationContext(),
                    "邮箱已被注册，请重新输入",  Toast.LENGTH_SHORT).show();
            et_add_email.setText("");
        } else if (e.getMessage().substring(8, 11)
                .equals("125")) {
            Toast.makeText(getApplicationContext(),
                    "未找到该邮箱，请核对后输入",  Toast.LENGTH_SHORT).show();
            et_add_email.setText("");
        } else if (e.getMessage().substring(8, 11)
                .equals("127")) {
            Toast.makeText(getApplicationContext(),
                    "未找到该手机号，请核对后输入",  Toast.LENGTH_SHORT).show();
            et_add_name.setText("");
        } else if (e.getMessage().substring(8, 11)
                .equals("214")) {
            Toast.makeText(getApplicationContext(),
                    "手机号已被注册，请重新输入",  Toast.LENGTH_SHORT).show();
            et_add_name.setText("");
        } else {
            Toast.makeText(getApplicationContext(),
                    e.getMessage(),  Toast.LENGTH_SHORT).show();
        }
    }
    //输入出错
    public boolean input(){
        return et_register_email.getText().toString().equals("")||
                et_register_password.getText().toString().equals("")||
                et_register_name.getText().toString().equals("")||
                et_register_password.getText().toString().equals("")||
                et_register_password_check.getText().toString().equals("");
    }
}
