package fragement;

import android.app.AlertDialog;
import android.app.Dialog;
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

import com.example.tyhj.smart.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import activity_for_adapter.For_ModelHead;
import adapter.Model_HeadImage_Adapter;
import savephoto.GetModelHeadImage;

/**
 * Created by Tyhj on 2016/3/31.
 */
public class Activity_2 extends Fragment {
    LinearLayout tv;
    View view;
    GridView gv_image;
    int db_headcount=0;
    TextView tv_room,tv_type;
    ListView list_room;
    FloatingActionButton fab;
    boolean roteta,show;
    int[] length= GetModelHeadImage.modelhead;
    For_ModelHead fmd;
    Model_HeadImage_Adapter simpleAdapter;
    List<For_ModelHead> list;
    Animation rotate1,rotate2,show1,show2;
    LinearLayout ll_add;
    ImageView image_chose;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_2,null);
        tv_room= (TextView) view.findViewById(R.id.tv_type_room);
        tv_type= (TextView) view.findViewById(R.id.tv_type_type);
        fab= (FloatingActionButton) view.findViewById(R.id.fab_equipment);
        list_room= (ListView) view.findViewById(R.id.lv_equipment_room);
        rotate1 = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate);
        rotate2 = AnimationUtils.loadAnimation(getActivity(), R.anim.rotatepositin);
        show1=AnimationUtils.loadAnimation(getActivity(), R.anim.show);
        show2=AnimationUtils.loadAnimation(getActivity(), R.anim.noshow);
        rotate1.setFillAfter(true);
        rotate2.setFillAfter(true);
        tv= (LinearLayout) view.findViewById(R.id.tv_idonotneed);
        tv.setVisibility(View.GONE);
        image_chose= (ImageView) view.findViewById(R.id.ib_chose_head);
        ll_add= (LinearLayout) view.findViewById(R.id.ll_addRoom);
        gv_image= (GridView) view.findViewById(R.id.gv_head);
        setAdapter();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tv_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_room.setBackgroundResource(R.drawable.text_select);
                tv_type.setBackgroundColor(Color.parseColor("#00000000"));
            }
        });
        tv_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_type.setBackgroundResource(R.drawable.text_select);
                tv_room.setBackgroundColor(Color.parseColor("#00000000"));
            }
        });
        image_chose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!show){
                    gv_image.setVisibility(View.VISIBLE);
                }else {
                    gv_image.setVisibility(View.INVISIBLE);
                }
                show=!show;
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!roteta){
                    Random random = new Random();
                    db_headcount = random.nextInt(length.length);
                    image_chose.setBackgroundResource(length[db_headcount]);
                    ll_add.setVisibility(View.VISIBLE);
                    ll_add.startAnimation(show1);
                    fab.startAnimation(rotate1);
                }else{
                    ll_add.setVisibility(View.INVISIBLE);
                    ll_add.startAnimation(show2);
                    fab.startAnimation(rotate2);
                }
                roteta=!roteta;
            }
        });
    }
    //初始化控件
    private void setAdapter() {
        list=new ArrayList<For_ModelHead>();
        for(int i=0;i<length.length;i++){
            fmd=new For_ModelHead(i);
            list.add(fmd);
        }
        simpleAdapter=new Model_HeadImage_Adapter(getActivity(), R.layout.headimage_for_gridview,list);
        gv_image.setAdapter(simpleAdapter);
        gv_image.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                image_chose.setBackgroundResource(length[position]);
                db_headcount = position;
            }
        });
    }
}
