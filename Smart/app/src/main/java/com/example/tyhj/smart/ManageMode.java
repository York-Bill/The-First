package com.example.tyhj.smart;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import activity_for_adapter.For_ModelHead;
import adapter.Model_Adapter;
import adapter.Model_HeadImage_Adapter;
import savephoto.GetModelHeadImage;

/**
 * Created by Tyhj on 2016/4/3.
 */
public class ManageMode extends Activity{
    ImageView image_chose;
    GridView gv_image;
    FloatingActionButton fab;
    Animation rotate1,rotate2,show1,show2;
    ImageButton ib_back_add_model;
    boolean roteta;
    For_ModelHead fmd;
    Model_HeadImage_Adapter simpleAdapter;
    List<For_ModelHead> list;
    LinearLayout ll_add;
    int[] length=GetModelHeadImage.modelhead;
    int width;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_model);
        initWidget();
        onClik();
        setAdapter();
    }

    private void setAdapter() {
        list=new ArrayList<For_ModelHead>();
        for(int i=0;i<length.length;i++){
            fmd=new For_ModelHead(i);
            list.add(fmd);
        }
        simpleAdapter=new Model_HeadImage_Adapter(ManageMode.this, R.layout.headimage_for_gridview,list);
        gv_image.setAdapter(simpleAdapter);
        gv_image.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                image_chose.setBackgroundResource(length[position]);
            }
        });
    }

    //点击事件
    private void onClik(){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!roteta){
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
        ll_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_add.setVisibility(View.INVISIBLE);
                ll_add.startAnimation(show2);
                fab.startAnimation(rotate2);
                roteta = !roteta;
            }
        });
        ib_back_add_model.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_add.setVisibility(View.INVISIBLE);
                ll_add.startAnimation(show2);
                fab.startAnimation(rotate2);
                roteta = !roteta;
            }
        });
    }
    //初始化控件
    private void initWidget() {
        WindowManager wm = (WindowManager) ManageMode.this
                .getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
        image_chose= (ImageView) findViewById(R.id.ib_chose_head);
        gv_image= (GridView) findViewById(R.id.gv_head);
        ib_back_add_model= (ImageButton) findViewById(R.id.ib_back_add_model);
        ll_add= (LinearLayout) findViewById(R.id.ll_addmodel);
        fab= (FloatingActionButton) findViewById(R.id.fab);
        rotate1 = AnimationUtils.loadAnimation(ManageMode.this, R.anim.rotate);
        rotate2 = AnimationUtils.loadAnimation(ManageMode.this, R.anim.rotatepositin);
        show1=AnimationUtils.loadAnimation(ManageMode.this, R.anim.show);
        show2=AnimationUtils.loadAnimation(ManageMode.this, R.anim.noshow);
        rotate1.setFillAfter(true);
        rotate2.setFillAfter(true);
        image_chose.setBackgroundResource(length[0]);
    }
}
