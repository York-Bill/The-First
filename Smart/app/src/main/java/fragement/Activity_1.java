package fragement;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tyhj.smart.ManageMode;
import com.example.tyhj.smart.R;

import java.util.ArrayList;
import java.util.List;

import Api_sours.MyLocation;
import Api_sours.NoScrollGridView;
import Api_sours.Wether;
import activity_for_adapter.For_Model;
import activity_for_adapter.For_collect_equipment;
import activity_for_adapter.For_collect_room;
import adapter.Collect_equipment_Adapter_addRoom;
import adapter.Model_Adapter;
import adapter.Model_Adapter_2;
import dataBase.MyDateBase;
import savephoto.GetModelHeadImage;

/**
 * Created by Tyhj on 2016/3/31.
 */
public class Activity_1 extends Fragment {
    boolean x=true;
    TextView location,city;
    MyDateBase myDateBase;
    public SQLiteDatabase mydb;
    ListView list_model;
    ImageView sun;
    For_collect_equipment for_collect_equipment1;
    List<For_Model> mylist;
    Collect_equipment_Adapter_addRoom collect_equipment_adapter1;
    List<For_collect_equipment> list1;
    For_Model fm;
    Model_Adapter_2 mydp2;
    public View view;
    View headview_model;
    Cursor cursor;
    TextView temperature,wind,wether;
    NoScrollGridView gv_model;
    int[] length=GetModelHeadImage.equipmenthead;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_1, null);
        list_model= (ListView) view.findViewById(R.id.model);
        mylist=new ArrayList<For_Model>();
        list1=new ArrayList<For_collect_equipment>();
        headview_model = LayoutInflater.from(getActivity()).inflate(R.layout.activity1_head, null, true);
        gv_model= (NoScrollGridView) headview_model.findViewById(R.id.gv_model);
        temperature= (TextView) headview_model.findViewById(R.id.temperature);
        location= (TextView) headview_model.findViewById(R.id.location);
        city= (TextView) headview_model.findViewById(R.id.city);
        wind= (TextView) headview_model.findViewById(R.id.wind);
        wether= (TextView) headview_model.findViewById(R.id.wether);
        wether.setText("晴\n"+"20℃~25℃ ");
        sun= (ImageView) headview_model.findViewById(R.id.sun);
        getModelList();
        mydp2=new Model_Adapter_2(getActivity(),R.layout.gridview_for_collect,mylist);
        collect_equipment_adapter1=new Collect_equipment_Adapter_addRoom(getActivity(),R.layout.equipment_for_listview,list1);
        list_model.addHeaderView(headview_model);
        getWether();
        gv_model.setAdapter(mydp2);
        list_model.setAdapter(collect_equipment_adapter1);
        return view;
    }

    private void getWether() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Wether w=new Wether(getActivity(),handler);
                return;
            }
        }).start();
    }

    private void getModelList() {
        myDateBase=new MyDateBase(getActivity(), GetModelHeadImage.getUserId()+".db",null,1);
        mydb=myDateBase.getWritableDatabase();
        cursor=mydb.query("Model", null, null, null, null, null, null);
        while(cursor.moveToNext()){
                String name=cursor.getString(0);
                int headImage=cursor.getInt(1);
                fm=new For_Model(name,headImage);
                mylist.add(fm);
        }
        if(mylist.size()%4!=0){
            for(int i=0;i<mylist.size()%4;i++){
                fm=new For_Model("",100);
                mylist.add(fm);
            }
        }
        cursor.close();
        cursor= mydb.rawQuery("select * from Equipment where ifCollect=?", new String[]{"1"});
        while (cursor.moveToNext()){
            for_collect_equipment1=new For_collect_equipment(cursor.getString(0),cursor.getString(1),length[cursor.getInt(2)],cursor.getInt(6),cursor.getString(4));
            list1.add(for_collect_equipment1);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        mylist.clear();
        list1.clear();
        getModelList();
        getWether();
        collect_equipment_adapter1.notifyDataSetChanged();
        mydp2=new Model_Adapter_2(getActivity(),R.layout.gridview_for_collect,mylist);
        gv_model.setAdapter(mydp2);
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    SharedPreferences preferences=getActivity().getSharedPreferences("wether", Context.MODE_PRIVATE);
                    temperature.setText(preferences.getString("wendu","20")+"°");
                    String mysun=preferences.getString("tianqi","晴");
                    wether.setText(preferences.getString("tianqi","晴")+"\n"+preferences.getString("hignlow",""));
                    wind.setText(preferences.getString("fengxiang","西北风")+"/"+preferences.getString("fengji","2级"));
                    location.setText(MyLocation.getPlace().Sheng+MyLocation.getPlace().Shi+MyLocation.getPlace().Xian);
                    city.setText(preferences.getString("city","杭州"));
                    switch (mysun){
                        case "晴":
                            sun.setImageResource(R.drawable.sun);
                            break;
                        case "多云":
                            sun.setImageResource(R.drawable.cloudy);
                            break;
                        case "雨":
                        case "小雨":
                        case "大雨":
                        case "暴雨":
                        case "中雨":
                        case "阵雨":
                            sun.setImageResource(R.drawable.rain);
                            break;
                        case "雪":
                        case "小雪":
                        case "中雪":
                        case "大雪":
                            sun.setImageResource(R.drawable.snow);
                            break;
                        case "阴":
                            sun.setImageResource(R.drawable.yinday);
                            break;
                    }
                break;
            }
        }
    };
}
