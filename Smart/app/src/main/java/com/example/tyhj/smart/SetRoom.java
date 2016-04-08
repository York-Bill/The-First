package com.example.tyhj.smart;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Vibrator;
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
import java.util.List;
import java.util.Random;

import activity_for_adapter.For_ModelHead;
import activity_for_adapter.For_collect_equipment;
import adapter.Collect_equipment_Adapter;
import adapter.Model_HeadImage_Adapter;
import dataBase.MyDateBase;
import savephoto.GetModelHeadImage;

import static com.example.tyhj.smart.R.id.bt_toast_auto;
import static com.example.tyhj.smart.R.id.bt_toast_change;

/**
 * Created by Tyhj on 2016/4/6.
 */
public class SetRoom extends Activity {
    GridView gv_head;
    int x;
    EditText et_change_name;
    View headView;
    ListView lv_set_room;
    LinearLayout ll_head_room,ll_change;
    boolean bl;
    ImageButton ib_back_add_room;
    ImageView cl;
    MyDateBase myDateBase;
    SQLiteDatabase mydb;
    For_collect_equipment equipment;
    List<For_collect_equipment> mylist;
    Collect_equipment_Adapter collect_equipment_adapter;
    For_ModelHead fmd;
    Model_HeadImage_Adapter simpleAdapter;
    List<For_ModelHead> list;
    int headImage[]= GetModelHeadImage.modelhead;
    View footview;
    Button bt_add_equipment;
    String roomName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_room);
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
                SetRoom.this.finish();
            }
        });
        bt_add_equipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder di = new AlertDialog.Builder(SetRoom.this);
                di.setCancelable(true);
                //布局转view
                LayoutInflater inflater = LayoutInflater.from(SetRoom.this);
                View layout = inflater.inflate(R.layout.toast_add_equipment, null);
                Button bt_manual = (Button) layout.findViewById(R.id.bt_toast_manual);
                Button bt_auto = (Button) layout.findViewById(bt_toast_auto);
                di.setView(layout);
                di.create();
                final Dialog dialog = di.show();
                bt_manual.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                bt_auto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        });
    }

    private void initwidget() {
        roomName=getIntent().getStringExtra("roomName");
        lv_set_room= (ListView) findViewById(R.id.lv_set_room);
        ll_change= (LinearLayout) findViewById(R.id.ll_change);
        ll_head_room= (LinearLayout) findViewById(R.id.ll_head_room);
        list=new ArrayList<For_ModelHead>();
        myDateBase=new MyDateBase(this,"Model.db",null,1);
        mydb=myDateBase.getWritableDatabase();
        List<For_collect_equipment> list=new ArrayList<For_collect_equipment>();
        cl= (ImageView) findViewById(R.id.cl_addall_headImage);
        gv_head= (GridView) findViewById(R.id.gv_head_room);
        headView=LayoutInflater.from(this).inflate(R.layout.listhead_setroom,null,true);
        et_change_name= (EditText) headView.findViewById(R.id.et_change_name);
        et_change_name.setText(roomName);
        footview= LayoutInflater.from(this).inflate(R.layout.listfoot_add,null,true);
        bt_add_equipment= (Button) footview.findViewById(R.id.bt_addall);
        ib_back_add_room= (ImageButton) findViewById(R.id.ib_back_add_room);
        mylist=new ArrayList<For_collect_equipment>();
        Cursor cursor=mydb.rawQuery("select * from Room where id=?",new String[]{roomName});
        if(cursor.moveToNext()){
            Picasso.with(SetRoom.this)
                    .load(headImage[cursor.getInt(1)])
                    .into(cl);
        }
         cursor=mydb.rawQuery("select * from Equipment where belong=?",new String[]{roomName});
        while (cursor.moveToNext()){
            equipment=new For_collect_equipment(cursor.getString(0),cursor.getString(1),headImage[cursor.getInt(2)],cursor.getInt(5));
            mylist.add(equipment);
        }
        lv_set_room.addHeaderView(headView);
        lv_set_room.addFooterView(footview);
        collect_equipment_adapter=new Collect_equipment_Adapter(this,R.layout.model_for_listview,mylist);
        lv_set_room.setAdapter(collect_equipment_adapter);
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
        simpleAdapter=new Model_HeadImage_Adapter(SetRoom.this, R.layout.headimage_for_gridview,list);
        gv_head.setAdapter(simpleAdapter);
        gv_head.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Picasso.with(SetRoom.this)
                        .load(headImage[position])
                        .into(cl);
                x = position;
            }
        });
    }
}
