package com.example.tyhj.smart;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
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
import activity_for_adapter.For_collect_equipment;
import adapter.Collect_equipment_Adapter;
import dataBase.MyDateBase;
import savephoto.GetModelHeadImage;

/**
 * Created by Tyhj on 2016/4/6.
 */
public class Add extends Activity {
    EditText et_add_name_room;
    ImageButton ib_save_add_room;
    GridView gv_head;
    String equipment_id,equipment_name;
    int db_headcount,x;
    ImageView ib_chose_head;
    TextView tv_ad_equipment_title;
    ImageButton ib_back_addequipment,ib_back_add_room,ib_save;
    LinearLayout ll_add_equipments,ll_show_headImages;
    ImageView cl;
    EditText et_add_name,et_add_id;
    ListView listview;
    For_collect_equipment equipment;
    List<For_collect_equipment> list,forsava;
    Collect_equipment_Adapter adapter;
    View listheadview;
    MyDateBase myDateBase;
    SQLiteDatabase mydb;
    int headImage[]= GetModelHeadImage.modelhead;
    int bgcolor[]=GetModelHeadImage.bgcolor;
    View view;
    Button add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);
        initwidget();
        //setMydapter();
        adapter=new Collect_equipment_Adapter(this,R.layout.model_for_listview,list);
        listview.addFooterView(view);
        listview.setAdapter(adapter);
        onClick();
    }

    private void onClick() {
        ib_save_add_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRoom();
                Add.this.finish();
            }
        });
        cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                x = random.nextInt(headImage.length);
                Picasso.with(Add.this)
                        .load(headImage[x])
                        .into(cl);
            }
        });
        //添加电器
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_add_equipments.setVisibility(View.VISIBLE);
                Random random = new Random();
                db_headcount = random.nextInt(headImage.length);
                Picasso.with(Add.this)
                        .load(headImage[db_headcount])
                        .into(ib_chose_head);
            }
        });
        ib_back_addequipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_add_equipments.setVisibility(View.INVISIBLE);
            }
        });
        ib_back_add_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Add.this.finish();
            }
        });
        //保存电器
        ib_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                equipment_id = et_add_id.getText().toString();
                equipment_name = et_add_name.getText().toString();
                if ((!equipment_id.equals("")) && (!equipment_name.equals("")) && ifnull(equipment_id)) {
                    InsertIntoDatabase();
                    ll_add_equipments.setVisibility(View.INVISIBLE);
                } else if (equipment_id.equals("") || equipment_name.equals("")) {
                    Toast.makeText(Add.this, "名字或电器编号不能为空", Toast.LENGTH_SHORT).show();
                } else if (!ifnull(equipment_id)) {
                    Toast.makeText(Add.this, "该模式已存在", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ib_chose_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void saveRoom() {
        String name=et_add_name_room.getText().toString();
        if(!name.equals("")&&isnull(name)){
            for(int i=0;i<forsava.size();i++){
                mydb.execSQL("insert into Equipment values (?,?,?,?,?,?)", new Object[]{forsava.get(i).getId(),
                        forsava.get(i).getName(), forsava.get(i).getHeadImage(), forsava.get(i).getType(), name, 0
                });
            }
            mydb.execSQL("insert into Room values(?,?,?)",new Object[]{name,x,0});
            Toast.makeText(Add.this,"成功",Toast.LENGTH_SHORT).show();
        }else if(name.equals("")){
            Toast.makeText(this,"房间名不能为空",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"房间已存在，请重新设置房间名",Toast.LENGTH_SHORT).show();
        }
    }

    /*private void setMydapter() {
        Cursor cursor=mydb.rawQuery("select * from Equipment where belong=", new String[]{});
        while (cursor.moveToNext()){
            String id=cursor.getString(0);
            String name=cursor.getString(1);
            int headImageid=cursor.getInt(2);
            String type=cursor.getString(3);
            int ifcollect=cursor.getInt(4);
            equipment=new For_collect_equipment(id,name,headImage[headImageid],type,ifcollect);
            list.add(equipment);
        }
        cursor.close();
    }*/
    private void initwidget() {
        et_add_name_room= (EditText) findViewById(R.id.et_add_name);
        myDateBase=new MyDateBase(this,"Model.db",null,1);
        mydb=myDateBase.getWritableDatabase();
        forsava=new ArrayList<For_collect_equipment>();
        list=new ArrayList<For_collect_equipment>();
        List<For_collect_equipment> list=new ArrayList<For_collect_equipment>();
        cl= (ImageView) findViewById(R.id.cl_addall_headImage);
        ib_save= (ImageButton) findViewById(R.id.ib_save);
        listview= (ListView) findViewById(R.id.lv_listforall);
        ll_add_equipments= (LinearLayout) findViewById(R.id.ll_add_equipment);
        tv_ad_equipment_title= (TextView) findViewById(R.id.tv_ad_equipment_title);
        tv_ad_equipment_title.setText("为房间添加智能电器");
        ib_chose_head= (ImageView) findViewById(R.id.ib_chose_head_addequipment);
        gv_head= (GridView) findViewById(R.id.gv_head);
        ib_save_add_room= (ImageButton) findViewById(R.id.ib_save_add_room);
        //电器名字
        et_add_id= (EditText) findViewById(R.id.et_add_id);
        et_add_name= (EditText) findViewById(R.id.et_add_name_equipment);
        Random random = new Random();
        view= LayoutInflater.from(this).inflate(R.layout.listfoot_add,null,true);
        add= (Button) view.findViewById(R.id.bt_addall);
        ib_back_addequipment= (ImageButton) findViewById(R.id.ib_back_add_model);
        ib_back_add_room= (ImageButton) findViewById(R.id.ib_back_add_room);
        int x = random.nextInt(headImage.length);
        Picasso.with(Add.this)
                .load(headImage[x])
                .into(cl);
    }
    private boolean ifnull(String str) {
        Cursor cursor=mydb.rawQuery("select * from Equipment where id=?",new String[]{str});
        if(cursor.moveToNext()){
            cursor.close();
            return false;
        }else{
            cursor.close();
            return true;
        }
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
    //插入电器
    private void InsertIntoDatabase() {
        equipment=new For_collect_equipment(equipment_id,equipment_name,headImage[db_headcount],"1",0);
        forsava.add(equipment);
        list.add(equipment);
        adapter.notifyDataSetChanged();
    }
}
