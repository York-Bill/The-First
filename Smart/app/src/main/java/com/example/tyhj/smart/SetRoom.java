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
import twoCode.activity.CaptureActivity;

import static com.example.tyhj.smart.R.id.bt_finish_add_equipment;
import static com.example.tyhj.smart.R.id.bt_toast_auto;
import static com.example.tyhj.smart.R.id.bt_toast_change;

/**
 * Created by Tyhj on 2016/4/6.
 */
public class SetRoom extends Activity {
    GridView gv_head;
    EditText et_change_name;
    View headView;
    ListView lv_set_room;
    LinearLayout ll_head_room,ll_change;
    boolean bl;
    int x,z;
    int y,b;
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
    String equipmentName[]=GetModelHeadImage.equipmentName;
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
        lv_set_room.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

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
                if(b!=z) mydb.execSQL("update Room set headImage=? where id=?",new Object[]{b,roomName});
                if(!et_change_name.getText().toString().equals(roomName))
                    mydb.execSQL("update Room set id=? where id=?",new Object[]{et_change_name.getText().toString(),roomName});
                mydb.execSQL("update Equipment set room=? where room=?",new Object[]{et_change_name.getText().toString(),roomName});
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
                View layout = inflater.inflate(R.layout.toast_the_wey_add_equipment, null);
                Button bt_manual = (Button) layout.findViewById(R.id.bt_toast_manual);
                Button bt_auto = (Button) layout.findViewById(bt_toast_auto);
                di.setView(layout);
                di.create();
                final Dialog dialog = di.show();
                bt_manual.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        final AlertDialog.Builder di = new AlertDialog.Builder(SetRoom.this);
                        di.setCancelable(true);
                        //布局转view
                        LayoutInflater inflater = LayoutInflater.from(SetRoom.this);
                        View layout = inflater.inflate(R.layout.toast_add_equipment, null);
                        final EditText into = (EditText) layout.findViewById(R.id.et_toast_into_equipment_number);
                        Button finish = (Button) layout.findViewById(bt_finish_add_equipment);
                        di.setView(layout);
                        di.create();
                        final Dialog dialog1 = di.show();
                        finish.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Random random = new Random();
                                x = random.nextInt(equipmentName.length);
                                String id = into.getText().toString();
                                if (!haveInternet()) {
                                    Snackbar.make(v, "网络未连接", Snackbar.LENGTH_SHORT)
                                            .setAction("Action", null).show();
                                } else if (id.equals("")) {
                                    Snackbar.make(v, "电器编号不能为空", Snackbar.LENGTH_SHORT)
                                            .setAction("Action", null).show();
                                } else if (!ifHaveNumber()) {
                                    Snackbar.make(v, "未找到此编号所对应的电器", Snackbar.LENGTH_SHORT)
                                            .setAction("Action", null).show();
                                } else if (ifexist(id)) {
                                    Snackbar.make(v, "已添加过此电器", Snackbar.LENGTH_SHORT)
                                            .setAction("Action", null).show();
                                } else{
                                    mydb.execSQL("insert into Equipment values(?,?,?,?,?,?,?)",new Object[]{id,equipmentName[x],
                                    x,y,roomName," ",0});
                                    equipment=new For_collect_equipment(id,equipmentName[x], headImage[x],0,roomName);
                                    mylist.add(equipment);
                                    collect_equipment_adapter.notifyDataSetChanged();
                                    dialog1.dismiss();
                                }
                            }
                        });
                    }
                });
                bt_auto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Intent intent_TwoCode = new Intent(SetRoom.this, CaptureActivity.class);
                        startActivity(intent_TwoCode);
                    }
                });
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
    //编号
    private boolean ifHaveNumber() {
        return true;
    }
    //第一次添加
    private boolean ifexist(String str) {
       Cursor cursor=mydb.rawQuery("select * from Equipment where id=?", new String[]{str});
        if(cursor.moveToNext()){
            return true;
        }else {
            return false;
        }
    }

    private void initwidget() {
        Random random = new Random();
        x = random.nextInt(equipmentName.length);
        y=random.nextInt(4)+1;
        roomName=getIntent().getStringExtra("roomName");
        lv_set_room= (ListView) findViewById(R.id.lv_set_room);
        ll_change= (LinearLayout) findViewById(R.id.ll_change);
        ll_head_room= (LinearLayout) findViewById(R.id.ll_head_room);
        list=new ArrayList<For_ModelHead>();
        myDateBase=new MyDateBase(this,GetModelHeadImage.getUserId()+".db",null,1);
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
        z=0;
        Cursor cursor=mydb.rawQuery("select * from Room where id=?",new String[]{roomName});
        if(cursor.moveToNext()){
            Picasso.with(SetRoom.this)
                    .load(headImage[cursor.getInt(1)])
                    .into(cl);
            z=cursor.getInt(1);
            b=z;
        }
         cursor=mydb.rawQuery("select * from Equipment where room=?",new String[]{roomName});
        while (cursor.moveToNext()){
            equipment=new For_collect_equipment(cursor.getString(0),cursor.getString(1),headImage[cursor.getInt(2)],cursor.getInt(6),cursor.getString(4));
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
            SetRoom.this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
