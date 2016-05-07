package com.example.tyhj.smart;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.Snackbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import adapter.Collect_equipment_Adapter_addRoom;
import adapter.Equipment_Adapter_AddToModel;
import adapter.Model_HeadImage_Adapter;
import dataBase.MyDateBase;
import savephoto.GetModelHeadImage;
import twoCode.activity.CaptureActivity;

/**
 * Created by Tyhj on 2016/4/6.
 */
public class SetModel extends Activity {
    GridView gv_head;
    EditText et_change_name;
    View headView;
    ListView lv_set_room;
    LinearLayout ll_head_room,ll_change,llRom,llEquipment;
    boolean bl;
    int x,z;
    List<String> strList;
    ListView lvRoom,lvEquipment;
    int y,b;
    ImageButton ib_back_add_room;
    ImageView cl;
    MyDateBase myDateBase;
    SQLiteDatabase mydb;
    For_collect_equipment equipment;
    List<For_collect_equipment> mylist;
    Equipment_Adapter_AddToModel collect_equipment_adapter;
    For_ModelHead fmd;
    Model_HeadImage_Adapter simpleAdapter;
    List<For_ModelHead> list;
    int headImage[]= GetModelHeadImage.mosiheah;
    int equipheadimaeg[]=GetModelHeadImage.equipmenthead;
    String equipmentName[]=GetModelHeadImage.equipmentName;
    View footview;
    Button bt_add_equipment;
    String roomName;
    String name1;
    List<Eq> eqList=new ArrayList<Eq>();
    Eq eq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setmodel);
        initwidget();
        onClick();
        setAdapter();
    }

    private void onClick() {
        lvEquipment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!ifhave(eqList.get(position).id))
                mydb.execSQL("insert into InModel values (?,?,?,?)",new Object[]{roomName,eqList.get(position).id,name1,0});
                Snackbar.make(view, "已添加", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                mylist.clear();
                Cursor cursor2=mydb.rawQuery("select * from InModel where modelId=?",new String[]{roomName});
                while (cursor2.moveToNext()){
                    Cursor cursor= mydb.rawQuery("select * from Equipment where id=?", new String[]{cursor2.getString(1)});
                    if (cursor.moveToNext()){
                        equipment=new For_collect_equipment(cursor.getString(0),cursor.getString(1),equipheadimaeg[cursor.getInt(2)],cursor.getInt(6),cursor.getString(4));
                        mylist.add(equipment);
                    }
                }
                collect_equipment_adapter.notifyDataSetChanged();
            }
        });
        lvRoom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                name1=strList.get(position);
                Cursor cursor=mydb.rawQuery("select * from Equipment where room=?",new String[]{name1});
                while (cursor.moveToNext()){
                    eq=new Eq(cursor.getString(0),cursor.getString(1));
                    eqList.add(eq);
                }
                String[] data=new String[eqList.size()];
                for(int i=0;i<eqList.size();i++){
                    data[i]=eqList.get(i).name;
                }
                ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(SetModel.this,android.R.layout.simple_list_item_1,data);
                lvEquipment.setAdapter(arrayAdapter);
                llEquipment.setVisibility(View.VISIBLE);
                llRom.setVisibility(View.GONE);
            }
        });
        llEquipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llEquipment.setVisibility(View.GONE);
                llRom.setVisibility(View.VISIBLE);
                eqList.clear();
            }
        });
        bt_add_equipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llRom.setVisibility(View.VISIBLE);
            }
        });
        llRom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llRom.setVisibility(View.GONE);
            }
        });
        ll_head_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_head_room.setVisibility(View.INVISIBLE);
                bl = false;
            }
        });
        ll_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bl) {
                    ll_head_room.setVisibility(View.VISIBLE);
                } else {
                    ll_head_room.setVisibility(View.INVISIBLE);
                }
                bl = !bl;
            }
        });

        ib_back_add_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(b!=z) mydb.execSQL("update Model set headImage=? where id=?",new Object[]{b,roomName});
                if(!et_change_name.getText().toString().equals(roomName)) {
                    mydb.execSQL("update InModel set modelId=? where modelId=?", new Object[]{et_change_name.getText().toString(), roomName});
                    mydb.execSQL("update Model set id=? where id=?", new Object[]{et_change_name.getText().toString(), roomName});
                }
                SetModel.this.finish();
            }
        });
    }
    //网络
    private boolean haveInternet() {
        ConnectivityManager con=(ConnectivityManager)getSystemService(Activity.CONNECTIVITY_SERVICE);
        boolean wifi=con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        boolean internet=con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        if(wifi|internet){
            return true;
        }else{
            return false;
        }
    }
    public boolean ifhave(String str){
        Cursor cursor=mydb.rawQuery("select * from InModel where equipmentId=?",new String[]{str});
        if (cursor.moveToNext())
            return true;
        else
            return false;
    }
    private void initwidget() {
        Random random = new Random();
        x = random.nextInt(equipmentName.length);
        y=random.nextInt(4)+1;
        llRom= (LinearLayout) findViewById(R.id.llRom);
        llEquipment= (LinearLayout) findViewById(R.id.llEquipment);
        roomName=getIntent().getStringExtra("modelName");
        lvRoom= (ListView) findViewById(R.id.lvRoom);
        lvEquipment= (ListView) findViewById(R.id.lvEquipment);
        lv_set_room= (ListView) findViewById(R.id.x_lv_set_room);
        ll_change= (LinearLayout) findViewById(R.id.x_ll_change);
        ll_head_room= (LinearLayout) findViewById(R.id.x_ll_head_room);
        list=new ArrayList<For_ModelHead>();
        myDateBase=new MyDateBase(this,GetModelHeadImage.getUserId()+".db",null,1);
        mydb=myDateBase.getWritableDatabase();
        List<For_collect_equipment> list=new ArrayList<For_collect_equipment>();
        cl= (ImageView) findViewById(R.id.x_cl_addall_headImage);
        gv_head= (GridView) findViewById(R.id.x_gv_head_room);
        headView=LayoutInflater.from(this).inflate(R.layout.listhead_setroom,null,true);
        et_change_name= (EditText) headView.findViewById(R.id.et_change_name);
        et_change_name.setText(roomName);
        footview= LayoutInflater.from(this).inflate(R.layout.listfoot_add,null,true);
        bt_add_equipment= (Button) footview.findViewById(R.id.bt_addall);
        ib_back_add_room= (ImageButton) findViewById(R.id.x_ib_back_add_room);
        mylist=new ArrayList<For_collect_equipment>();
        z=0;
        Cursor cursor2=mydb.rawQuery("select * from Model where id=?",new String[]{roomName});
        if(cursor2.moveToNext()){
            Picasso.with(SetModel.this)
                    .load(headImage[cursor2.getInt(1)])
                    .into(cl);
            z=cursor2.getInt(1);
            b=z;
        }
        cursor2=mydb.rawQuery("select * from InModel where modelId=?",new String[]{roomName});
        while (cursor2.moveToNext()){
            Cursor cursor= mydb.rawQuery("select * from Equipment where id=?", new String[]{cursor2.getString(1)});
            if (cursor.moveToNext()){
                equipment=new For_collect_equipment(cursor.getString(0),cursor.getString(1),equipheadimaeg[cursor.getInt(2)],cursor.getInt(6),cursor.getString(4));
                mylist.add(equipment);
            }
        }
        strList=new ArrayList<String>();
        cursor2.close();
        cursor2=mydb.rawQuery("select * from Room",null);
        while (cursor2.moveToNext()){
            strList.add(cursor2.getString(0));
        }
        String[] data=new String[strList.size()];
        for(int i=0;i<strList.size();i++){
            data[i]=strList.get(i);
        }
        lv_set_room.addHeaderView(headView);
        lv_set_room.addFooterView(footview);
        collect_equipment_adapter=new Equipment_Adapter_AddToModel(this,R.layout.equipment_for_listview,mylist);
        lv_set_room.setAdapter(collect_equipment_adapter);
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(SetModel.this,android.R.layout.simple_list_item_1,data);
        lvRoom.setAdapter(arrayAdapter);
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
        simpleAdapter=new Model_HeadImage_Adapter(SetModel.this, R.layout.headimage_for_gridview,list);
        gv_head.setAdapter(simpleAdapter);
        gv_head.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Picasso.with(SetModel.this)
                        .load(headImage[position])
                        .into(cl);
                b = position;
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            if(b!=z) mydb.execSQL("update Room set headImage=? where id=?",new Object[]{b,roomName});
            if(!et_change_name.getText().toString().equals(roomName))
                mydb.execSQL("update Room set id=? where id=?",new Object[]{et_change_name.getText().toString(),roomName});
            mydb.execSQL("update Equipment set room=? where room=?",new Object[]{et_change_name.getText().toString(),roomName});
            SetModel.this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }
    class Eq{
        Eq(String str1,String str2){
            id=str1;
            name=str2;
        }
        String id;
        String name;
    }
}
