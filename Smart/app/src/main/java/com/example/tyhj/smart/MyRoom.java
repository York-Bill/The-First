package com.example.tyhj.smart;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import activity_for_adapter.For_collect_room;
import adapter.Room_Adapter;
import dataBase.MyDateBase;
import savephoto.GetModelHeadImage;

import static com.example.tyhj.smart.R.id.bt_toast_change;

/**
 * Created by Tyhj on 2016/4/13.
 */
public class MyRoom extends Activity {
    MyDateBase myDateBase;
    SQLiteDatabase mydb;
    ImageButton ib_back_myroom;
    ListView lv_myroom;
    FloatingActionButton fab;
    Room_Adapter myadpter;
    List<For_collect_room> list;
    For_collect_room room;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myroom);
        initWidget();
        setClickEvent();
    }
    private void initWidget() {
        list=new ArrayList<For_collect_room>();
        ib_back_myroom= (ImageButton) findViewById(R.id.ib_back_myroom);
        lv_myroom= (ListView) findViewById(R.id.lv_myroom);
        fab= (FloatingActionButton) findViewById(R.id.fab);
        initAdapter();
    }

    private void initAdapter() {
        myDateBase=new MyDateBase(this, GetModelHeadImage.getUserId()+".db",null,1);
        mydb=myDateBase.getWritableDatabase();
        Cursor cursor=mydb.rawQuery("select * from Room",null);
        while (cursor.moveToNext()){
            room=new For_collect_room(cursor.getString(0),cursor.getInt(1),cursor.getInt(2));
            list.add(room);
        }
        myadpter=new Room_Adapter(this,R.layout.room_for_listview,list);
        lv_myroom.setAdapter(myadpter);
    }

    private void setClickEvent() {
        lv_myroom.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                mydb = myDateBase.getWritableDatabase();
                final AlertDialog.Builder di = new AlertDialog.Builder(MyRoom.this);
                di.setCancelable(true);
                //布局转view
                LayoutInflater inflater = LayoutInflater.from(MyRoom.this);
                View layout = inflater.inflate(R.layout.toast_manage_room, null);
                Button bt_change = (Button) layout.findViewById(R.id.bt_toast_change_room);
                Button bt_delete = (Button) layout.findViewById(R.id.bt_toast_delete_room);
                di.setView(layout);
                di.create();
                final Dialog dialog = di.show();
                bt_change.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent in=new Intent(MyRoom.this,SetRoom.class);
                        in.putExtra("roomName",list.get(position).getName());
                        startActivity(in);
                        dialog.dismiss();
                    }
                });
                bt_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mydb.execSQL("delete from Equipment where room=?",new String[]{list.get(position).getName()});
                        mydb.execSQL("delete from Room where id=?",new String[]{list.get(position).getName()});
                        list.remove(position);
                        myadpter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                return true;
            }
        });
        lv_myroom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent in=new Intent(MyRoom.this,SetRoom.class);
                in.putExtra("roomName",list.get(position).getName());
                startActivity(in);
            }
        });
        ib_back_myroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyRoom.this.finish();
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(MyRoom.this,Add.class);
                startActivity(in);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        list.clear();
        Cursor cursor=mydb.rawQuery("select * from Room",null);
        while (cursor.moveToNext()){
            room=new For_collect_room(cursor.getString(0),cursor.getInt(1),cursor.getInt(2));
            list.add(room);
        }
        myadpter.notifyDataSetChanged();
    }
}
