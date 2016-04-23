package com.example.tyhj.smart;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.AnalogClock;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import activity_for_adapter.For_Preset;
import adapter.Preset_Adapter;
import dataBase.MyDateBase;
import savephoto.GetModelHeadImage;

/**
 * Created by Tyhj on 2016/4/17.
 */
public class Preset extends Activity {
    For_Preset for_preset;
    Preset_Adapter preset_adapter;
    List<For_Preset> list;
    String date;
    ListView listView;
    TextView time;
    AnalogClock analogClock;
    MyDateBase myDateBase;
    SQLiteDatabase sqLiteDatabase;
    String xq[]={"一","二","三","四","五","六","日"};
    int[] ifchose;
    int[] headImage=GetModelHeadImage.modelhead;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preset);
        ifchose=new int[7];
        initWidget();
        Cursor cursor=sqLiteDatabase.rawQuery("select * from Preset",null);
        while (cursor.moveToNext()){
            boolean b1=false;
            Cursor cursor2=sqLiteDatabase.rawQuery("select * from Days where id=? and ifop=?",new String[]{cursor.getString(0),""+cursor.getInt(8)});
            String str="周";
            if(cursor2.moveToNext()) {
                for (int i = 0; i < xq.length; i++) {
                    if (cursor2.getInt(i + 2) == 1) str = str + xq[i] + " ";
                }
            }
            if(cursor.getInt(8)==1) str=str+"开";
            else str=str+"关";
            if(cursor.getInt(5)==1){
                b1=true;
            }
            int h=cursor.getInt(1);
            int m=cursor.getInt(2);
            String string;
            if(h<10&&m>=10){
                 string="0"+h+":"+m;
            }else if(h>10&&m<10){
                 string=h+":"+"0"+m;
            }else if(h<10&&h<10){
                 string="0"+h+":"+"0"+m;
            }else{
                 string=h+":"+m;
            }

            Cursor cursor3=sqLiteDatabase.rawQuery("select * from Days where id=? and ifop=?",new String[]{cursor.getString(1),""+cursor.getInt(8)});
            for_preset=new For_Preset(cursor.getString(9),string, b1,headImage[cursor.getInt(6)],str,cursor.getString(7));
            list.add(for_preset);
        }
        preset_adapter=new Preset_Adapter(this,R.layout.list_for_preset,list);
        listView.setAdapter(preset_adapter);
    }

    private void initWidget() {
        time= (TextView) findViewById(R.id.time);
        analogClock= (AnalogClock) findViewById(R.id.analogClock);
        listView= (ListView) findViewById(R.id.lv_preset);
        myDateBase=new MyDateBase(this, GetModelHeadImage.getUserId()+".db",null,1);
        sqLiteDatabase=myDateBase.getWritableDatabase();
        list=new ArrayList<For_Preset>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
                    String data = df.format(new Date()).substring(0, 19);
                    date = data.substring(11, 13) + ":" + data.substring(14, 16);
                    Bundle bundle = new Bundle();
                    bundle.putString("time", date);
                    Message ms = new Message();
                    ms.setData(bundle);
                    handler.sendMessage(ms);
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            time.setText(msg.getData().getString("time"));
        }
    };
}
