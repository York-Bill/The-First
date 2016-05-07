package com.example.tyhj.smart;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;

import dataBase.MyDateBase;
import savephoto.DownloadTask;
import savephoto.GetModelHeadImage;

/**
 * Created by Tyhj on 2016/4/23.
 */
public class AddControlCenter extends Activity implements View.OnClickListener {
    ImageButton ib_back_controlCenter,ib_control_twocode;
    Button bt_add_center;
    MyDateBase myDateBase;
    SQLiteDatabase mydb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcontrolcenter);
        bt_add_center= (Button) findViewById(R.id.bt_add_center);
        ib_back_controlCenter= (ImageButton) findViewById(R.id.ib_back_controlCenter);
        ib_control_twocode= (ImageButton) findViewById(R.id.ib_control_twocode);
        ib_back_controlCenter.setOnClickListener(this);
        bt_add_center.setOnClickListener(this);
        myDateBase=new MyDateBase(this,GetModelHeadImage.getUserId()+".db",null,1);
        mydb=myDateBase.getWritableDatabase();
        if(!internet()){
            Toast.makeText(getApplicationContext(),
                    "网络出错，请检查网络连接", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_back_controlCenter:
                AddControlCenter.this.finish();
                break;
            case R.id.bt_add_center:
                set();
                Snackbar.make(v, "配置成功", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                break;
        }
    }

    private void set() {
        add();
        AVUser.getCurrentUser().saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                AVUser.getCurrentUser().put("times", 1);
                AVUser.getCurrentUser().saveInBackground();
            }
        });
    }
    private void add() {
        mydb.execSQL("insert into Room values(?,?,?)",new Object[]{"客厅",16,0});
        mydb.execSQL("insert into Room values(?,?,?)",new Object[]{"卧室",12,0});
        mydb.execSQL("insert into Room values(?,?,?)",new Object[]{"厨房",14,0});
        mydb.execSQL("insert into Room values(?,?,?)",new Object[]{"浴室",19,0});
        mydb.execSQL("insert into Model values(?,?,?)",new Object[]{"看电影",6,0});
        mydb.execSQL("insert into Model values(?,?,?)",new Object[]{"听音乐",25,0});
        mydb.execSQL("insert into Model values(?,?,?)",new Object[]{"休闲模式",27,0});
        mydb.execSQL("insert into Model values(?,?,?)",new Object[]{"春天模式",26,0});
        mydb.execSQL("insert into Model values(?,?,?)",new Object[]{"冬天模式",13,0});
        mydb.execSQL("insert into Model values(?,?,?)",new Object[]{"用餐模式",0,0});
        mydb.execSQL("insert into Model values(?,?,?)",new Object[]{"夜间模式",12,0});
        mydb.execSQL("insert into Model values(?,?,?)",new Object[]{"离家模式",17,0});
    }

    public boolean internet(){
        ConnectivityManager con=(ConnectivityManager)getSystemService(Activity.CONNECTIVITY_SERVICE);
        boolean internet=con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        boolean wifi=con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        if(internet||wifi) return true;
        else return false;
    }
}
