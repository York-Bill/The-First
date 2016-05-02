package fragement;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tyhj.smart.AddControlCenter;
import com.example.tyhj.smart.Equipent_Center;
import com.example.tyhj.smart.ManageMode;
import com.example.tyhj.smart.MyRoom;
import com.example.tyhj.smart.Preset;
import com.example.tyhj.smart.R;
import com.example.tyhj.smart.SetRoom;
import com.example.tyhj.smart.UserMessage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import Api_sours.NoScrollGridView;
import Api_sours.PullToZoomListViewEx;
import activity_for_adapter.For_Activity_3;
import adapter.Activity_3_Adapter;
import dataBase.MyDateBase;
import savephoto.GetModelHeadImage;

/**
 * Created by Tyhj on 2016/3/31.
 */
public class Activity_3 extends Fragment {
    View view;
    LinearLayout ll1,ll2,ll3,ll4,ll5,ll6;
    ImageView iv1,iv2,iv3,iv4,iv5,iv6;
    ImageView cl_activity3_headImage;
    TextView tv_activity3_name,signnature,tv_collect_count,tv_all_count,tv_room_count;
    int[] headImage=GetModelHeadImage.userHeadImage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_3,null);
        initWidget();
        return view;
    }

    private void initWidget() {
        tv_room_count= (TextView) view.findViewById(R.id.tv_room_count);
        tv_all_count= (TextView) view.findViewById(R.id.tv_all_count);
        tv_collect_count= (TextView) view.findViewById(R.id.tv_collect_count);
        signnature= (TextView) view.findViewById(R.id.signnature);
        tv_activity3_name= (TextView) view.findViewById(R.id.tv_activity3_name);
        cl_activity3_headImage= (ImageView) view.findViewById(R.id.cl_activity3_headImage);
        ll1= (LinearLayout) view.findViewById(R.id.ll_activity3_1);
        ll2= (LinearLayout) view.findViewById(R.id.ll_activity3_2);
        ll3= (LinearLayout) view.findViewById(R.id.ll_activity3_3);
        ll4= (LinearLayout) view.findViewById(R.id.ll_activity3_4);
        ll5= (LinearLayout) view.findViewById(R.id.ll_activity3_5);
        ll6= (LinearLayout) view.findViewById(R.id.ll_activity3_6);
        setTuch(ll1);
        setTuch(ll2);
        setTuch(ll3);
        setTuch(ll4);
        setTuch(ll5);
        setTuch(ll6);
        iv1= (ImageView) view.findViewById(R.id.iv3_1);
        iv2= (ImageView) view.findViewById(R.id.iv3_2);
        iv3= (ImageView) view.findViewById(R.id.iv3_3);
        iv4= (ImageView) view.findViewById(R.id.iv3_4);
        iv5= (ImageView) view.findViewById(R.id.iv3_5);
        iv6= (ImageView) view.findViewById(R.id.iv3_6);
        setMessage();
    }

    private void setMessage() {
        MyDateBase mydateBase = new MyDateBase(getActivity(), GetModelHeadImage.getUserId() + ".db", null, 1);
        SQLiteDatabase sqLiteDatabase = mydateBase.getWritableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("select * from User",null);
        if(cursor.moveToNext()){
            cl_activity3_headImage.setImageResource(headImage[cursor.getInt(8)]);
            tv_activity3_name.setText(cursor.getString(1));
            signnature.setText(cursor.getString(2));
        }
        cursor=sqLiteDatabase.rawQuery("select * from Equipment where ifCollect=?",new String[]{"1"});
        int i=0;
        while (cursor.moveToNext()){
            i++;
        }
        tv_collect_count.setText(i+"个收藏");
        cursor=sqLiteDatabase.rawQuery("select * from Equipment",null);
        i=0;
        while (cursor.moveToNext()){
            i++;
        }
        tv_all_count.setText(i+"个设备");
        i=0;
        cursor=sqLiteDatabase.rawQuery("select * from Model",null);
        while (cursor.moveToNext()){
            i++;
        }
        tv_room_count.setText(i+"个模式");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setClik();
    }

    private void setClik() {

        ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getContext(), ManageMode.class);
                getActivity().startActivity(in);
            }
        });
        ll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in2=new Intent(getContext(), Equipent_Center.class);
                getActivity().startActivity(in2);
            }
        });
        ll3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in1=new Intent(getActivity(), MyRoom.class);
                getActivity().startActivity(in1);
            }
        });
        ll4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in3=new Intent(getActivity(), Preset.class);
                getActivity().startActivity(in3);
            }
        });
        ll5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in5=new Intent(getActivity(), AddControlCenter.class);
                getActivity().startActivity(in5);
            }
        });
        ll6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in6=new Intent(getActivity(), UserMessage.class);
                getActivity().startActivity(in6);
            }
        });
    }
    public void setTuch(final LinearLayout ll){
        ll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        ll.setBackgroundColor(Color.parseColor("#dddddd"));
                        break;
                    case MotionEvent.ACTION_UP:
                        ll.setBackgroundColor(Color.parseColor("#00000000"));
                        break;
                    case  MotionEvent.ACTION_CANCEL:
                        ll.setBackgroundColor(Color.parseColor("#00000000"));
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        setMessage();
    }
}
