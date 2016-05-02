package fragement;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

import com.example.tyhj.smart.Add;
import com.example.tyhj.smart.CreatePreset;
import com.example.tyhj.smart.MyRoom;
import com.example.tyhj.smart.R;
import com.example.tyhj.smart.SetRoom;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import Api_sours.NoScrollGridView;
import Api_sours.NoScrollListView;
import activity_for_adapter.For_ModelHead;
import activity_for_adapter.For_collect_equipment;
import activity_for_adapter.For_collect_room;
import adapter.Collect_equipment_Adapter;
import adapter.Collect_equipment_Adapter_addRoom;
import adapter.Collect_room_Adapter;
import adapter.Model_HeadImage_Adapter;
import dataBase.MyDateBase;
import savephoto.GetModelHeadImage;

/**
 * Created by Tyhj on 2016/3/31.
 */
public class Activity_2 extends Fragment {
    NoScrollGridView gv_room;
    TextView tv_type_count_1,tv_type_count_2,tv_type_count_3,tv_type_count_4;
    ImageButton ib_extend_1,ib_extend_2,ib_extend_3,ib_extend_4;
    TextView tv_ad_equipment_count_1,tv_ad_equipment_count_2,tv_ad_equipment_count_3,tv_ad_equipment_count_4;
    NoScrollListView lv_equipmentType_1,lv_equipmentType_2,lv_equipmentType_3,lv_equipmentType_4;
    View view;
    LinearLayout llIfFind;
    int[] length= GetModelHeadImage.equipmenthead;
    MyDateBase myDateBase;
    SQLiteDatabase mydb;
    For_collect_room for_collect_room;
    Collect_room_Adapter collect_room_adapter;
    List<For_collect_room> list;
    boolean b1,b2,b3,b4;
    int i1,i2,i3,i4;
    For_collect_equipment for_collect_equipment1;
    Collect_equipment_Adapter_addRoom collect_equipment_adapter1,collect_equipment_adapter2,collect_equipment_adapter3,collect_equipment_adapter4;
    List<For_collect_equipment> list1,list2,list3,list4;
    List<For_collect_equipment> flist1,flist2,flist3,flist4;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_2,null);
        initWidget();
        collect_room_adapter=new Collect_room_Adapter(getActivity(),R.layout.gridview_for_collect,list);
        gv_room.setAdapter(collect_room_adapter);
        if(list.size()>0) llIfFind.setVisibility(View.GONE);
        else llIfFind.setVisibility(View.VISIBLE);
        //x.setListViewHeightBasedOnChildren(gv_room,getActivity());
        return view;
    }
    private void initWidget() {
        i1=0;i2=0;i3=0;i4=0;
        llIfFind= (LinearLayout) view.findViewById(R.id.llIfFind);
        ib_extend_1= (ImageButton) view.findViewById(R.id.ib_extend_1);
        ib_extend_2= (ImageButton) view.findViewById(R.id.ib_extend_2);
        ib_extend_3= (ImageButton) view.findViewById(R.id.ib_extend_3);
        ib_extend_4= (ImageButton) view.findViewById(R.id.ib_extend_4);

        tv_ad_equipment_count_1= (TextView) view.findViewById(R.id.tv_type_1);
        tv_ad_equipment_count_2= (TextView) view.findViewById(R.id.tv_type_2);
        tv_ad_equipment_count_3= (TextView) view.findViewById(R.id.tv_type_3);
        tv_ad_equipment_count_4= (TextView) view.findViewById(R.id.tv_type_4);
        lv_equipmentType_1= (NoScrollListView) view.findViewById(R.id.lv_equipmentType_1);
        lv_equipmentType_2= (NoScrollListView) view.findViewById(R.id.lv_equipmentType_2);
        lv_equipmentType_3= (NoScrollListView) view.findViewById(R.id.lv_equipmentType_3);
        lv_equipmentType_4= (NoScrollListView) view.findViewById(R.id.lv_equipmentType_4);
        tv_type_count_1= (TextView) view.findViewById(R.id.tv_ad_equipment_count_1);
        tv_type_count_2= (TextView) view.findViewById(R.id.tv_ad_equipment_count_2);
        tv_type_count_3= (TextView) view.findViewById(R.id.tv_ad_equipment_count_3);
        tv_type_count_4= (TextView) view.findViewById(R.id.tv_ad_equipment_count_4);
        list1=new ArrayList<For_collect_equipment>();
        list2=new ArrayList<For_collect_equipment>();
        list3=new ArrayList<For_collect_equipment>();
        list4=new ArrayList<For_collect_equipment>();
        flist1=new ArrayList<For_collect_equipment>();
        flist2=new ArrayList<For_collect_equipment>();
        flist3=new ArrayList<For_collect_equipment>();
        flist4=new ArrayList<For_collect_equipment>();
        setInitIcon();
        list=new ArrayList<For_collect_room>();
        gv_room= (NoScrollGridView) view.findViewById(R.id.gv_room);
        myDateBase=new MyDateBase(getActivity(),GetModelHeadImage.getUserId()+".db",null,1);
        mydb=myDateBase.getWritableDatabase();
        Cursor cursor= mydb.rawQuery("select * from Room", null);
        while (cursor.moveToNext()){
            for_collect_room=new For_collect_room(cursor.getString(0),cursor.getInt(1),cursor.getInt(2));
            list.add(for_collect_room);
        }
        if(list.size()%4!=0){
            for(int i=0;i<list.size()%4;i++){
                for_collect_room=new For_collect_room("",100,0);
                list.add(for_collect_room);
            }
        }
        initAdapter();
        initCount();
    }

    private void initCount() {
        tv_type_count_1.setText(""+i1);
        tv_type_count_2.setText(""+i2);
        tv_type_count_3.setText(""+i3);
        tv_type_count_4.setText(""+i4);
    }

    private void initAdapter() {
        //1
        Cursor cursor= mydb.rawQuery("select * from Equipment where type=?", new String[]{"1"});
        while (cursor.moveToNext()){
            for_collect_equipment1=new For_collect_equipment(cursor.getString(0),cursor.getString(1),length[cursor.getInt(2)],cursor.getInt(6),cursor.getString(4));
            list1.add(for_collect_equipment1);
            i1++;
        }
        collect_equipment_adapter1=new Collect_equipment_Adapter_addRoom(getActivity(),R.layout.equipment_for_listview,flist1);
        //lv_equipmentType_1.addHeaderView(headview1);
        lv_equipmentType_1.setAdapter(collect_equipment_adapter1);

        cursor.close();
        //2
        cursor= mydb.rawQuery("select * from Equipment where type=?", new String[]{"2"});
        while (cursor.moveToNext()){
            for_collect_equipment1=new For_collect_equipment(cursor.getString(0),cursor.getString(1),length[cursor.getInt(2)],cursor.getInt(6),cursor.getString(4));
            list2.add(for_collect_equipment1);
            i2++;
        }
        collect_equipment_adapter2=new Collect_equipment_Adapter_addRoom(getActivity(),R.layout.equipment_for_listview,flist2);
        lv_equipmentType_2.setAdapter(collect_equipment_adapter2);

        cursor.close();
        //3
        cursor= mydb.rawQuery("select * from Equipment where type=?", new String[]{"3"});
        while (cursor.moveToNext()){
            for_collect_equipment1=new For_collect_equipment(cursor.getString(0),cursor.getString(1),length[cursor.getInt(2)],cursor.getInt(6),cursor.getString(4));
            list3.add(for_collect_equipment1);
            i3++;
        }
        collect_equipment_adapter3=new Collect_equipment_Adapter_addRoom(getActivity(),R.layout.equipment_for_listview,flist3);
        lv_equipmentType_3.setAdapter(collect_equipment_adapter3);
        //4
        cursor= mydb.rawQuery("select * from Equipment where type=?", new String[]{"4"});
        while (cursor.moveToNext()){
            for_collect_equipment1=new For_collect_equipment(cursor.getString(0),cursor.getString(1),length[cursor.getInt(2)],cursor.getInt(6),cursor.getString(4));
            list4.add(for_collect_equipment1);
            i4++;
        }
        collect_equipment_adapter4=new Collect_equipment_Adapter_addRoom(getActivity(),R.layout.equipment_for_listview,flist4);
        lv_equipmentType_4.setAdapter(collect_equipment_adapter4);
        cursor.close();
    }

    private void setInitIcon() {
        Picasso.with(getActivity())
                .load(R.drawable.ic_close)
                .resize(40, 40)
                .centerCrop()
                .into(ib_extend_1);
        Picasso.with(getActivity())
                .load(R.drawable.ic_close)
                .resize(40, 40)
                .centerCrop()
                .into(ib_extend_2);
        Picasso.with(getActivity())
                .load(R.drawable.ic_close)
                .resize(40, 40)
                .centerCrop()
                .into(ib_extend_3);
        Picasso.with(getActivity())
                .load(R.drawable.ic_close)
                .resize(40, 40)
                .centerCrop()
                .into(ib_extend_4);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setClikEvent();
    }

    private void setClikEvent() {
        llIfFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getActivity(),MyRoom.class);
                getActivity().startActivity(in);
            }
        });
        gv_room.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(list.get(position).getHeadimage()!=100) {
                    Intent in = new Intent(getActivity(), SetRoom.class);
                    in.putExtra("roomName", list.get(position).getName());
                    getActivity().startActivity(in);
                }
            }
        });
        tv_ad_equipment_count_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!b1){
                    Picasso.with(getActivity())
                            .load(R.drawable.ic_open)
                            .resize(50, 50)
                            .centerCrop()
                            .into(ib_extend_1);
                    for(int i=0;i<list1.size();i++){
                        flist1.add(list1.get(i));
                    }
                }else{
                    Picasso.with(getActivity())
                            .load(R.drawable.ic_close)
                            .resize(40, 40)
                            .centerCrop()
                            .into(ib_extend_1);
                    list1.clear();
                    for(int i=0;i<flist1.size();i++){
                        list1.add(flist1.get(i));
                    }
                    flist1.clear();
                }
                tv_type_count_1.setText(list1.size()+"");
                collect_equipment_adapter1.notifyDataSetChanged();
                b1=!b1;
            }
        });
        tv_ad_equipment_count_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!b2){
                    Picasso.with(getActivity())
                            .load(R.drawable.ic_open)
                            .resize(50, 50)
                            .centerCrop()
                            .into(ib_extend_2);
                    for(int i=0;i<list2.size();i++){
                        flist2.add(list2.get(i));
                    }
                }else{
                    Picasso.with(getActivity())
                            .load(R.drawable.ic_close)
                            .resize(40, 40)
                            .centerCrop()
                            .into(ib_extend_2);
                    list2.clear();
                    for(int i=0;i<flist2.size();i++){
                        list2.add(flist2.get(i));
                    }
                    flist2.clear();
                }
                tv_type_count_2.setText(list2.size()+"");
                collect_equipment_adapter2.notifyDataSetChanged();
                b2=!b2;
            }
        });
        tv_ad_equipment_count_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!b3){
                    Picasso.with(getActivity())
                            .load(R.drawable.ic_open)
                            .resize(50, 50)
                            .centerCrop()
                            .into(ib_extend_3);
                    for(int i=0;i<list3.size();i++){
                        flist3.add(list3.get(i));
                    }
                }else{
                    Picasso.with(getActivity())
                            .load(R.drawable.ic_close)
                            .resize(40, 40)
                            .centerCrop()
                            .into(ib_extend_3);
                    list3.clear();
                    for(int i=0;i<flist3.size();i++){
                        list3.add(flist3.get(i));
                    }
                    flist3.clear();
                }
                tv_type_count_3.setText(list3.size()+"");
                collect_equipment_adapter3.notifyDataSetChanged();
                b3=!b3;
            }
        });
        tv_ad_equipment_count_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!b4){
                    Picasso.with(getActivity())
                            .load(R.drawable.ic_open)
                            .resize(50, 50)
                            .centerCrop()
                            .into(ib_extend_4);
                    for(int i=0;i<list4.size();i++){
                        flist4.add(list4.get(i));
                    }
                }else{
                    Picasso.with(getActivity())
                            .load(R.drawable.ic_close)
                            .resize(40, 40)
                            .centerCrop()
                            .into(ib_extend_4);
                    list4.clear();
                    for(int i=0;i<flist4.size();i++){
                        list4.add(flist4.get(i));
                    }
                    flist4.clear();
                }
                tv_type_count_4.setText(list4.size()+"");
                collect_equipment_adapter4.notifyDataSetChanged();
                b4=!b4;
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        i1=0;i2=0;i3=0;i4=0;
        list.clear();
        list1.clear();
        list2.clear();
        list3.clear();
        list4.clear();
        Cursor cursor= mydb.rawQuery("select * from Room", null);
        while (cursor.moveToNext()){
            for_collect_room=new For_collect_room(cursor.getString(0),cursor.getInt(1),cursor.getInt(2));
            list.add(for_collect_room);
        }
        if(list.size()%4!=0){
            for(int i=0;i<list.size()%4;i++){
                for_collect_room=new For_collect_room("",100,0);
                list.add(for_collect_room);
            }
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                startThread();
            }
        }).start();
        collect_room_adapter=new Collect_room_Adapter(getActivity(),R.layout.gridview_for_collect,list);
        gv_room.setAdapter(collect_room_adapter);
        flist1.clear();
        flist2.clear();
        flist4.clear();
        flist3.clear();
        b1=false;
        b2=false;
        b3=false;
        b4=false;
        setInitIcon();
        if(list.size()>0) llIfFind.setVisibility(View.GONE);
        else llIfFind.setVisibility(View.VISIBLE);
    }

    private void startThread() {
        Cursor cursor;
        cursor= mydb.rawQuery("select * from Equipment where type=?", new String[]{"1"});
        while (cursor.moveToNext()){
            for_collect_equipment1=new For_collect_equipment(cursor.getString(0),cursor.getString(1),length[cursor.getInt(2)],cursor.getInt(6),cursor.getString(4));
            list1.add(for_collect_equipment1);
            i1++;
        }
        cursor= mydb.rawQuery("select * from Equipment where type=?", new String[]{"2"});
        while (cursor.moveToNext()){
            for_collect_equipment1=new For_collect_equipment(cursor.getString(0),cursor.getString(1),length[cursor.getInt(2)],cursor.getInt(6),cursor.getString(4));
            list2.add(for_collect_equipment1);
            i2++;
        }
        cursor= mydb.rawQuery("select * from Equipment where type=?", new String[]{"3"});
        while (cursor.moveToNext()){
            for_collect_equipment1=new For_collect_equipment(cursor.getString(0),cursor.getString(1),length[cursor.getInt(2)],cursor.getInt(6),cursor.getString(4));
            list3.add(for_collect_equipment1);
            i3++;
        }
        cursor= mydb.rawQuery("select * from Equipment where type=?", new String[]{"4"});
        while (cursor.moveToNext()){
            for_collect_equipment1=new For_collect_equipment(cursor.getString(0),cursor.getString(1),length[cursor.getInt(2)],cursor.getInt(6),cursor.getString(4));
            list4.add(for_collect_equipment1);
            i4++;
        }
        collect_equipment_adapter1.notifyDataSetChanged();
        collect_equipment_adapter2.notifyDataSetChanged();
        collect_equipment_adapter3.notifyDataSetChanged();
        collect_equipment_adapter4.notifyDataSetChanged();
        handler.sendEmptyMessage(1);
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    initCount();
                    break;
            }
        }
    };
}
