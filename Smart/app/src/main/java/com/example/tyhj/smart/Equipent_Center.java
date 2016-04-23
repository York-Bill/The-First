package com.example.tyhj.smart;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import Api_sours.ListHeightUtils;
import activity_for_adapter.For_collect_equipment;
import activity_for_adapter.For_collect_room;
import adapter.Collect_equipment_Adapter_addRoom;
import adapter.Collect_room_Adapter;
import dataBase.MyDateBase;
import savephoto.GetModelHeadImage;

/**
 * Created by Tyhj on 2016/4/16.
 */
public class Equipent_Center extends Activity {
    ImageButton ib_back_equipment_center;
    TextView tv_type_c_count_1,tv_type_c_count_2,tv_type_c_count_3,tv_type_c_count_4;
    ImageButton ib_extend_c_1,ib_extend_c_2,ib_extend_c_3,ib_extend_c_4;
    TextView tv_ad_equipment_count_c_1,tv_ad_equipment_count_c_2,tv_ad_equipment_count_c_3,tv_ad_equipment_count_c_4;
    ListView lv_equipmentType_c_1,lv_equipmentType_c_2,lv_equipmentType_c_3,lv_equipmentType_c_4;
    int[] length= GetModelHeadImage.modelhead;
    MyDateBase myDateBase;
    SQLiteDatabase mydb;
    boolean b1,b2,b3,b4;
    int i1,i2,i3,i4;
    For_collect_equipment for_collect_equipment1;
    Collect_equipment_Adapter_addRoom collect_equipment_adapter1,collect_equipment_adapter2,collect_equipment_adapter3,collect_equipment_adapter4;
    List<For_collect_equipment> list1,list2,list3,list4;
    List<For_collect_equipment> flist1,flist2,flist3,flist4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.equipment_center);
        initWidget();
        initCount();
    }
    private void initCount() {
        tv_type_c_count_1.setText(""+i1);
        tv_type_c_count_2.setText(""+i2);
        tv_type_c_count_3.setText(""+i3);
        tv_type_c_count_4.setText(""+i4);
    }
    private void initWidget() {
        i1=0;i2=0;i3=0;i4=0;
        ib_back_equipment_center= (ImageButton) findViewById(R.id.ib_back_equipment_center);
        myDateBase=new MyDateBase(this,GetModelHeadImage.getUserId()+".db",null,1);
        mydb=myDateBase.getWritableDatabase();
        ib_extend_c_1= (ImageButton) findViewById(R.id.ib_extend_c_1);
        ib_extend_c_2= (ImageButton) findViewById(R.id.ib_extend_c_2);
        ib_extend_c_3= (ImageButton) findViewById(R.id.ib_extend_c_3);
        ib_extend_c_4= (ImageButton) findViewById(R.id.ib_extend_c_4);
        tv_ad_equipment_count_c_1= (TextView) findViewById(R.id.tv_type_c_1);
        tv_ad_equipment_count_c_2= (TextView) findViewById(R.id.tv_type_c_2);
        tv_ad_equipment_count_c_3= (TextView) findViewById(R.id.tv_type_c_3);
        tv_ad_equipment_count_c_4= (TextView) findViewById(R.id.tv_type_c_4);
        lv_equipmentType_c_1= (ListView) findViewById(R.id.lv_equipmentType_c_1);
        lv_equipmentType_c_2= (ListView) findViewById(R.id.lv_equipmentType_c_2);
        lv_equipmentType_c_3= (ListView) findViewById(R.id.lv_equipmentType_c_3);
        lv_equipmentType_c_4= (ListView) findViewById(R.id.lv_equipmentType_c_4);
        tv_type_c_count_1= (TextView) findViewById(R.id.tv_ad_equipment_count_c_1);
        tv_type_c_count_2= (TextView) findViewById(R.id.tv_ad_equipment_count_c_2);
        tv_type_c_count_3= (TextView) findViewById(R.id.tv_ad_equipment_count_c_3);
        tv_type_c_count_4= (TextView) findViewById(R.id.tv_ad_equipment_count_c_4);
        list1=new ArrayList<For_collect_equipment>();
        list2=new ArrayList<For_collect_equipment>();
        list3=new ArrayList<For_collect_equipment>();
        list4=new ArrayList<For_collect_equipment>();
        flist1=new ArrayList<For_collect_equipment>();
        flist2=new ArrayList<For_collect_equipment>();
        flist3=new ArrayList<For_collect_equipment>();
        flist4=new ArrayList<For_collect_equipment>();
        setInitIcon();
        initAdapter();
        setClikEvent();
    }

    private void setClikEvent() {
        ib_back_equipment_center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Equipent_Center.this.finish();
            }
        });
        tv_ad_equipment_count_c_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!b1){
                    Picasso.with(Equipent_Center.this)
                            .load(R.drawable.ic_open)
                            .resize(50, 50)
                            .centerCrop()
                            .into(ib_extend_c_1);
                    for(int i=0;i<list1.size();i++){
                        flist1.add(list1.get(i));
                    }
                }else{
                    Picasso.with(Equipent_Center.this)
                            .load(R.drawable.ic_close)
                            .resize(40, 40)
                            .centerCrop()
                            .into(ib_extend_c_1);
                    flist1.clear();
                }
                collect_equipment_adapter1.notifyDataSetChanged();
                ListHeightUtils.setListViewHeightBasedOnChildren(lv_equipmentType_c_1);
                b1=!b1;
            }
        });
        tv_ad_equipment_count_c_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!b2){
                    Picasso.with(Equipent_Center.this)
                            .load(R.drawable.ic_open)
                            .resize(50, 50)
                            .centerCrop()
                            .into(ib_extend_c_2);
                    for(int i=0;i<list2.size();i++){
                        flist2.add(list2.get(i));
                    }
                }else{
                    Picasso.with(Equipent_Center.this)
                            .load(R.drawable.ic_close)
                            .resize(40, 40)
                            .centerCrop()
                            .into(ib_extend_c_2);
                    flist2.clear();
                }
                collect_equipment_adapter2.notifyDataSetChanged();
                ListHeightUtils.setListViewHeightBasedOnChildren(lv_equipmentType_c_2);
                b2=!b2;
            }
        });
        tv_ad_equipment_count_c_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!b3){
                    Picasso.with(Equipent_Center.this)
                            .load(R.drawable.ic_open)
                            .resize(50, 50)
                            .centerCrop()
                            .into(ib_extend_c_3);
                    for(int i=0;i<list3.size();i++){
                        flist3.add(list3.get(i));
                    }
                }else{
                    Picasso.with(Equipent_Center.this)
                            .load(R.drawable.ic_close)
                            .resize(40, 40)
                            .centerCrop()
                            .into(ib_extend_c_3);
                    flist3.clear();
                }
                collect_equipment_adapter3.notifyDataSetChanged();
                ListHeightUtils.setListViewHeightBasedOnChildren(lv_equipmentType_c_3);
                b3=!b3;
            }
        });
        tv_ad_equipment_count_c_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!b4){
                    Picasso.with(Equipent_Center.this)
                            .load(R.drawable.ic_open)
                            .resize(50, 50)
                            .centerCrop()
                            .into(ib_extend_c_4);
                    for(int i=0;i<list4.size();i++){
                        flist4.add(list4.get(i));
                    }
                }else{
                    Picasso.with(Equipent_Center.this)
                            .load(R.drawable.ic_close)
                            .resize(40, 40)
                            .centerCrop()
                            .into(ib_extend_c_4);
                    flist4.clear();
                }
                collect_equipment_adapter4.notifyDataSetChanged();
                ListHeightUtils.setListViewHeightBasedOnChildren(lv_equipmentType_c_4);
                b4=!b4;
            }
        });
    }

    private void initAdapter() {
        //1
        Cursor cursor= mydb.rawQuery("select * from Equipment where type=?", new String[]{"1"});
        while (cursor.moveToNext()){
            for_collect_equipment1=new For_collect_equipment(cursor.getString(0),cursor.getString(1),length[cursor.getInt(2)],cursor.getInt(6),cursor.getString(4));
            list1.add(for_collect_equipment1);
            i1++;
        }
        collect_equipment_adapter1=new Collect_equipment_Adapter_addRoom(this,R.layout.equipment_for_listview,flist1);
        //lv_equipmentType_c_1.addHeaderView(headview1);
        lv_equipmentType_c_1.setAdapter(collect_equipment_adapter1);
        ListHeightUtils.setListViewHeightBasedOnChildren(lv_equipmentType_c_1);

        cursor.close();
        //2
        cursor= mydb.rawQuery("select * from Equipment where type=?", new String[]{"2"});
        while (cursor.moveToNext()){
            for_collect_equipment1=new For_collect_equipment(cursor.getString(0),cursor.getString(1),length[cursor.getInt(2)],cursor.getInt(6),cursor.getString(4));
            list2.add(for_collect_equipment1);
            i2++;
        }
        collect_equipment_adapter2=new Collect_equipment_Adapter_addRoom(this,R.layout.equipment_for_listview,flist2);
        lv_equipmentType_c_2.setAdapter(collect_equipment_adapter2);
        ListHeightUtils.setListViewHeightBasedOnChildren(lv_equipmentType_c_2);

        cursor.close();
        //3
        cursor= mydb.rawQuery("select * from Equipment where type=?", new String[]{"3"});
        while (cursor.moveToNext()){
            for_collect_equipment1=new For_collect_equipment(cursor.getString(0),cursor.getString(1),length[cursor.getInt(2)],cursor.getInt(6),cursor.getString(4));
            list3.add(for_collect_equipment1);
            i3++;
        }
        collect_equipment_adapter3=new Collect_equipment_Adapter_addRoom(this,R.layout.equipment_for_listview,flist3);
        lv_equipmentType_c_3.setAdapter(collect_equipment_adapter3);
        ListHeightUtils.setListViewHeightBasedOnChildren(lv_equipmentType_c_3);

        //4
        cursor= mydb.rawQuery("select * from Equipment where type=?", new String[]{"4"});
        while (cursor.moveToNext()){
            for_collect_equipment1=new For_collect_equipment(cursor.getString(0),cursor.getString(1),length[cursor.getInt(2)],cursor.getInt(6),cursor.getString(4));
            list4.add(for_collect_equipment1);
            i4++;
        }
        collect_equipment_adapter4=new Collect_equipment_Adapter_addRoom(this,R.layout.equipment_for_listview,flist4);
        lv_equipmentType_c_4.setAdapter(collect_equipment_adapter4);
        ListHeightUtils.setListViewHeightBasedOnChildren(lv_equipmentType_c_4);
        cursor.close();
    }
    private void setInitIcon() {
        Picasso.with(this)
                .load(R.drawable.ic_close)
                .resize(40, 40)
                .centerCrop()
                .into(ib_extend_c_1);
        Picasso.with(this)
                .load(R.drawable.ic_close)
                .resize(40, 40)
                .centerCrop()
                .into(ib_extend_c_2);
        Picasso.with(this)
                .load(R.drawable.ic_close)
                .resize(40, 40)
                .centerCrop()
                .into(ib_extend_c_3);
        Picasso.with(this)
                .load(R.drawable.ic_close)
                .resize(40, 40)
                .centerCrop()
                .into(ib_extend_c_4);
    }
}
