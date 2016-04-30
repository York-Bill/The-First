package com.example.tyhj.smart;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import Api_sours.StatusBarUtil;
import dataBase.MyDateBase;
import savephoto.GetModelHeadImage;

/**
 * Created by Tyhj on 2016/4/20.
 */
public class CreatePreset extends Activity {
    MyDateBase myDateBase;
    SQLiteDatabase sqLiteDatabase;
    Button[] bt=new Button[8];
    int[] b=new int[8];
    int[] a=new int[8];
    TimePicker timePicker;
    Button bt_op,bt_cls;
    boolean op=true;
    int oph,opm,clh,clm;
    int headImage;
    String room;
    boolean what;
    String name;
    ImageButton ib_back_createpreset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createpreset);
        initWidget();
        setClickEvent();
    }

    private void setClickEvent() {
        for(int i=1;i<8;i++){
            final int finalI = i;
            bt[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(op) {
                        if (b[finalI] == 0) {
                            bt[finalI].setBackgroundResource(R.drawable.circle);
                            b[finalI] = 1;
                        } else {
                            bt[finalI].setBackgroundResource(R.drawable.circle2);
                            b[finalI] = 0;
                        }
                    }else{
                        if (a[finalI] == 0) {
                            bt[finalI].setBackgroundResource(R.drawable.circle);
                            a[finalI] = 1;
                        } else {
                            bt[finalI].setBackgroundResource(R.drawable.circle2);
                            a[finalI] = 0;
                        }
                    }

                }
            });
        }
        bt[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int z;
                if(op) z=1;
                else z=0;
                        sqLiteDatabase.execSQL("delete from Preset where id=? and ifop=?",new Object[]{getIntent().getStringExtra("presetId"),z});
                        sqLiteDatabase.execSQL("delete from Days where id=? and ifop=?",new Object[]{getIntent().getStringExtra("presetId"),z});
                if(op) {
                    if (what) {
                        sqLiteDatabase.execSQL("insert into Preset values (?,?,?,?,?,?,?,?)", new Object[]{getIntent().getStringExtra("presetId"),
                                timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0, headImage, room, z, name});
                        sqLiteDatabase.execSQL("insert into Days values (?,?,?,?,?,?,?,?,?,?)", new Object[]{getIntent().getStringExtra("presetId"),
                                z, b[1], b[2], b[3], b[4], b[5], b[6], b[7], name});
                    } else {
                        sqLiteDatabase.execSQL("insert into Preset values (?,?,?,?,?,?,?,?)", new Object[]{getIntent().getStringExtra("presetId"),
                                timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0, headImage, null, z, name});
                        sqLiteDatabase.execSQL("insert into Days values (?,?,?,?,?,?,?,?,?,?)", new Object[]{getIntent().getStringExtra("presetId"),
                                z, b[1], b[2], b[3], b[4], b[5], b[6], b[7], name});
                    }
                }else {
                    if (what) {
                        sqLiteDatabase.execSQL("insert into Preset values (?,?,?,?,?,?,?,?)", new Object[]{getIntent().getStringExtra("presetId"),
                                timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0, headImage, room, z, name});
                        sqLiteDatabase.execSQL("insert into Days values (?,?,?,?,?,?,?,?,?,?)", new Object[]{getIntent().getStringExtra("presetId"),
                                z, a[1], a[2], a[3], a[4], a[5], a[6], a[7], name});
                    } else {
                        sqLiteDatabase.execSQL("insert into Preset values (?,?,?,?,?,?,?,?)", new Object[]{getIntent().getStringExtra("presetId"),
                                timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0, headImage, null, z, name});
                        sqLiteDatabase.execSQL("insert into Days values (?,?,?,?,?,?,?,?,?,?)", new Object[]{getIntent().getStringExtra("presetId"),
                                z, a[1], a[2], a[3], a[4], a[5], a[6], a[7], name});
                    }
                }

                Toast.makeText(CreatePreset.this,"已保存",Toast.LENGTH_SHORT).show();
                CreatePreset.this.finish();
            }
        });
        bt_op.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                op=true;
                bt_cls.setBackgroundColor(Color.parseColor("#aaaaaa"));
                bt_op.setBackgroundColor(Color.parseColor("#2a9e09"));
                timePicker.setCurrentHour(oph);
                timePicker.setCurrentMinute(opm);
                for(int i=1;i<b.length;i++){
                    bt[i].setBackgroundResource(R.drawable.circle2);
                }
                for(int i=1;i<b.length;i++){
                    if(b[i]==1){
                        bt[i].setBackgroundResource(R.drawable.circle);
                    }
                }
            }
        });
        ib_back_createpreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreatePreset.this.finish();
            }
        });
        bt_cls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=1;i<a.length;i++){
                    bt[i].setBackgroundResource(R.drawable.circle2);
                }
                op=false;
                bt_op.setBackgroundColor(Color.parseColor("#aaaaaa"));
                bt_cls.setBackgroundColor(Color.parseColor("#2a9e09"));
                timePicker.setCurrentHour(clh);
                timePicker.setCurrentMinute(clm);
                for(int i=1;i<a.length;i++) {
                    if (a[i] == 1) {
                        bt[i].setBackgroundResource(R.drawable.circle);
                    }
                }
            }
        });
    }

    private void initWidget() {
        oph=0;opm=0;clh=0;clm=0;
        for(int i=1;i<b.length;i++){
            b[i]=0;
        }
        for(int i=1;i<b.length;i++){
            a[i]=0;
        }
        initPreset();
        ib_back_createpreset= (ImageButton) findViewById(R.id.ib_back_createpreset);
        bt[1]= (Button) findViewById(R.id.bt_xq1);
        bt[2]= (Button) findViewById(R.id.bt_xq2);
        bt[3]= (Button) findViewById(R.id.bt_xq3);
        bt[4]= (Button) findViewById(R.id.bt_xq4);
        bt[5]= (Button) findViewById(R.id.bt_xq5);
        bt[6]= (Button) findViewById(R.id.bt_xq6);
        bt[7]= (Button) findViewById(R.id.bt_xq7);
        bt[0]= (Button) findViewById(R.id.bt_createPreset);
        timePicker= (TimePicker) findViewById(R.id.timePicker1);
        bt_op= (Button) findViewById(R.id.bt_preset_open);
        bt_cls= (Button) findViewById(R.id.bt_preset_close);
        timePicker.setCurrentHour(oph);
        timePicker.setCurrentMinute(opm);
        for(int i=1;i<a.length;i++) {
            if (b[i] == 1) {
                bt[i].setBackgroundResource(R.drawable.circle);
            }
        }
    }

    private void initPreset() {
        myDateBase=new MyDateBase(this, GetModelHeadImage.getUserId()+".db",null,1);
        sqLiteDatabase=myDateBase.getWritableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("select * from Preset where id=? and ifop=?",new String[]{getIntent().getStringExtra("presetId"),"1"});
        if (cursor.moveToNext()){
            oph=cursor.getInt(1);
            opm=cursor.getInt(2);
        }
        cursor=sqLiteDatabase.rawQuery("select * from Preset where id=? and ifop=?",new String[]{getIntent().getStringExtra("presetId"),"0"});
        if (cursor.moveToNext()){
            clh=cursor.getInt(1);
            clm=cursor.getInt(2);
        }
        cursor.close();
        cursor=sqLiteDatabase.rawQuery("select * from Equipment where id =?",new String[]{getIntent().getStringExtra("presetId")});
        if (cursor.moveToNext()){
            headImage=cursor.getInt(2);
            room=cursor.getString(4);
            what=true;
            name=cursor.getString(1);
        }else{
            cursor.close();
            what=false;
            cursor=sqLiteDatabase.rawQuery("select * from Model where id=?",new String[]{getIntent().getStringExtra("presetId")});
            if(cursor.moveToNext()){
                headImage=cursor.getInt(1);
                name=cursor.getString(0);
            }
        }
        cursor.close();
        cursor=sqLiteDatabase.rawQuery("select * from Days where id=? and ifop=?",new String[]{getIntent().getStringExtra("presetId"),"1"});
        if(cursor.moveToNext()){
            b[1]=cursor.getInt(2);b[2]=cursor.getInt(3);
            b[3]=cursor.getInt(4);b[4]=cursor.getInt(5);
            b[5]=cursor.getInt(6);b[6]=cursor.getInt(7);
            b[7]=cursor.getInt(8);
        }
        cursor=sqLiteDatabase.rawQuery("select * from Days where id=? and ifop=?",new String[]{getIntent().getStringExtra("presetId"),"0"});
        if(cursor.moveToNext()){
            a[1]=cursor.getInt(2);a[2]=cursor.getInt(3);
            a[3]=cursor.getInt(4);a[4]=cursor.getInt(5);
            a[5]=cursor.getInt(6);a[6]=cursor.getInt(7);
            a[7]=cursor.getInt(8);
        }
    }
}
