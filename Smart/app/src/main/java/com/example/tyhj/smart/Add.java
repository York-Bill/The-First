package com.example.tyhj.smart;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import Api_sours.CircularImage;
import activity_for_adapter.For_Model;
import activity_for_adapter.For_ModelHead;
import activity_for_adapter.For_collect_equipment;
import adapter.Collect_equipment_Adapter;
import adapter.Room_HeadImage_Adapter;
import dataBase.MyDateBase;
import savephoto.GetModelHeadImage;

/**
 * Created by Tyhj on 2016/4/6.
 */
public class Add extends Activity {

    EditText et_add_name_room;
    Button ib_save_add_room;
    GridView gv_head;
    int x;
    LinearLayout ll_head_room,ll_change;
    boolean bl;
    ImageButton ib_back_add_room;
    ImageView cl;
    MyDateBase myDateBase;
    SQLiteDatabase mydb;
    For_ModelHead fmd;
    Room_HeadImage_Adapter simpleAdapter;
    List<For_ModelHead> list;
    int headImage[]= GetModelHeadImage.roomhead;
    View view;
    Button add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_room);
        initwidget();
        onClick();
        setAdapter();
    }

    private void onClick() {
        ll_head_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_head_room.setVisibility(View.INVISIBLE);
                bl=false;
            }
        });
        ib_save_add_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRoom(v);
            }
        });
        ll_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bl) {
                    ll_head_room.setVisibility(View.VISIBLE);
                }else {
                    ll_head_room.setVisibility(View.INVISIBLE);
                }
                bl=!bl;
            }
        });

        ib_back_add_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Add.this.finish();
            }
        });
    }

    private void saveRoom(View v) {
        String name=et_add_name_room.getText().toString();
        if(!name.equals("")&&isnull(name)){
            mydb.execSQL("insert into Room values(?,?,?)",new Object[]{name,x,0});
            Toast.makeText(Add.this,"成功",Toast.LENGTH_SHORT).show();
            Add.this.finish();
        }else if(name.equals("")){
            Snackbar.make(v, "房间名字不能为空", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
        }else{
            Toast.makeText(this,"房间已存在，请重新设置房间名",Toast.LENGTH_SHORT).show();
        }
    }
    private void initwidget() {
        ll_change= (LinearLayout) findViewById(R.id.ll_change);
        ll_head_room= (LinearLayout) findViewById(R.id.ll_head_room);
        list=new ArrayList<For_ModelHead>();
        et_add_name_room= (EditText) findViewById(R.id.et_add_name);
        myDateBase=new MyDateBase(this,GetModelHeadImage.getUserId()+".db",null,1);
        mydb=myDateBase.getWritableDatabase();
        List<For_collect_equipment> list=new ArrayList<For_collect_equipment>();
        cl= (ImageView) findViewById(R.id.cl_addall_headImage);
        gv_head= (GridView) findViewById(R.id.gv_head_room);
        ib_save_add_room= (Button) findViewById(R.id.bt_addRoom);
        Random random = new Random();
        view= LayoutInflater.from(this).inflate(R.layout.listfoot_add,null,true);
        add= (Button) view.findViewById(R.id.bt_addall);
        ib_back_add_room= (ImageButton) findViewById(R.id.ib_back_add_room);
        x = random.nextInt(headImage.length);
        Picasso.with(Add.this)
                .load(headImage[x])
                .into(cl);
    }
    private boolean isnull(String name) {
        Cursor cursor=mydb.rawQuery("select * from Room where id=?",new String[]{name});
        if(cursor.moveToNext()){
            cursor.close();
            return false;
        }else{
            cursor.close();
            return true;
        }
    }
    //初始化控件
    private void setAdapter() {
        list=new ArrayList<For_ModelHead>();
        for(int i=0;i<headImage.length;i++){
            fmd=new For_ModelHead(i);
            list.add(fmd);
        }
        simpleAdapter=new Room_HeadImage_Adapter(Add.this, R.layout.headimage_for_gridview,list);
        gv_head.setAdapter(simpleAdapter);
        gv_head.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Picasso.with(Add.this)
                        .load(headImage[position])
                        .into(cl);
                x = position;
            }
        });
    }
}
