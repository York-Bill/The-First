package fragement;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tyhj.smart.Add;
import com.example.tyhj.smart.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import activity_for_adapter.For_ModelHead;
import activity_for_adapter.For_collect_room;
import adapter.Collect_room_Adapter;
import adapter.Model_HeadImage_Adapter;
import dataBase.MyDateBase;
import savephoto.GetModelHeadImage;

/**
 * Created by Tyhj on 2016/3/31.
 */
public class Activity_2 extends Fragment {
    View view;
    ListView list_room;
    Collect_room_Adapter room_adapter;
    List<For_collect_room> list;
    For_collect_room room;
    int[] length= GetModelHeadImage.modelhead;
    MyDateBase myDateBase;
    SQLiteDatabase mydb;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_2,null);
        initWidget();
        return view;
    }

    private void initWidget() {
        list=new ArrayList<For_collect_room>();
        list_room= (ListView) view.findViewById(R.id.lv_equipment_room);
        myDateBase=new MyDateBase(getActivity(),"Model.db",null,1);
        mydb=myDateBase.getWritableDatabase();
        Cursor cursor= mydb.rawQuery("select * from Room", null);
        while (cursor.moveToNext()){
            room=new For_collect_room(cursor.getString(0),length[cursor.getInt(1)],cursor.getInt(2));
            list.add(room);
        }
        room_adapter=new Collect_room_Adapter(getActivity(),R.layout.room_for_listview,list);
        list_room.setAdapter(room_adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    @Override
    public void onStart() {
        super.onStart();
        list.clear();
        Cursor cursor= mydb.rawQuery("select * from Room", null);
        while (cursor.moveToNext()){
            room=new For_collect_room(cursor.getString(0),length[cursor.getInt(1)],cursor.getInt(2));
            list.add(room);
        }
        room_adapter.notifyDataSetChanged();
    }
}
