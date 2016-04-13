package com.example.tyhj.smart;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import activity_for_adapter.For_Model;
import activity_for_adapter.For_ModelHead;
import adapter.Model_Adapter;
import adapter.Model_HeadImage_Adapter;
import dataBase.MyDateBase;
import savephoto.GetModelHeadImage;

/**
 * Created by Tyhj on 2016/4/3.
 */
public class ManageMode extends Activity{
    ImageView image_chose;
    EditText add_name;
    GridView gv_image;
    FloatingActionButton fab;
    ImageButton ib_back_manageModel;
    Animation rotate1,rotate2,show1,show2;
    ImageButton ib_back_add_model,ib_save;
    boolean roteta,show;
    For_ModelHead fmd;
    Model_HeadImage_Adapter simpleAdapter;
    List<For_ModelHead> list;
    LinearLayout ll_add;
    int[] length=GetModelHeadImage.modelhead;
    int width;
    int db_headcount=0;
    String db_name;
    MyDateBase myDateBase;
    public SQLiteDatabase mydb;
    ListView list_model;
    For_Model fm;
    List<For_Model> mylist;
    Model_Adapter mydp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_model);
        initWidget();
        onClik();
        setMyModelAdapter();
        setAdapter();
    }

    private void setMyModelAdapter() {
        mylist=new ArrayList<For_Model>();
        Cursor cursor=mydb.query("Model",null,null,null,null,null,null);
        if(cursor.moveToNext()){
            do{
                String name=cursor.getString(0);
                int headImage=cursor.getInt(1);
                fm=new For_Model(name,headImage);
                mylist.add(fm);
            }while (cursor.moveToNext());
        }
        mydp=new Model_Adapter(ManageMode.this,R.layout.model_for_listview,mylist);
        list_model.setAdapter(mydp);
        cursor.close();
    }

    private void initWidget() {
        WindowManager wm = (WindowManager) ManageMode.this
                .getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
        ib_back_manageModel= (ImageButton) findViewById(R.id.ib_back_manage_model);
        image_chose= (ImageView) findViewById(R.id.ib_chose_head);
        gv_image= (GridView) findViewById(R.id.gv_head);
        ib_back_add_model= (ImageButton) findViewById(R.id.ib_back_add_model);
        ll_add= (LinearLayout) findViewById(R.id.ll_addmodel);
        ib_save= (ImageButton) findViewById(R.id.ib_save);
        add_name= (EditText) findViewById(R.id.et_add_name);
        list_model= (ListView) findViewById(R.id.lv_model);
        fab= (FloatingActionButton) findViewById(R.id.fab);
        rotate1 = AnimationUtils.loadAnimation(ManageMode.this, R.anim.rotate);
        rotate2 = AnimationUtils.loadAnimation(ManageMode.this, R.anim.rotatepositin);
        show1=AnimationUtils.loadAnimation(ManageMode.this, R.anim.show);
        show2=AnimationUtils.loadAnimation(ManageMode.this, R.anim.noshow);
        rotate1.setFillAfter(true);
        rotate2.setFillAfter(true);
        myDateBase=new MyDateBase(this,GetModelHeadImage.getUserId()+".db",null,1);
        mydb=myDateBase.getWritableDatabase();
    }
    //点击事件
    private void onClik(){
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
        ib_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db_name=add_name.getText().toString();
                if(!db_name.equals("")&&ifnull(db_name)){
                    InsertIntoDatabase();
                    ll_add.setVisibility(View.INVISIBLE);
                    ll_add.startAnimation(show2);
                    fab.startAnimation(rotate2);
                    roteta = !roteta;
                }else if(db_name.equals("")){
                    Snackbar.make(v, "名字不能为空", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                }else if(!ifnull(db_name)){
                    Snackbar.make(v, "该模式已存在", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                }
            }
        });
        ib_back_manageModel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ManageMode.this.finish();
            }
        });
    }

    private boolean ifnull(String str) {
        int x=0;
        Cursor cursor=mydb.query("Model",null,null,null,null,null,null);
        if(cursor.moveToNext()){
            do{
                if(cursor.getString(0).equals(str)) {
                    return false;
                }
            }while (cursor.moveToNext());
            cursor.close();
           return true;
        }else {
            cursor.close();
            return true;
        }
    }
    //插入Model
    private void InsertIntoDatabase() {
        mydb.execSQL("insert into Model values (?,?)", new Object[]{db_name,db_headcount});
        fm=new For_Model(db_name,db_headcount);
        mylist.add(fm);
        mydp.notifyDataSetChanged();
    }

    //初始化控件
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
                db_headcount = position;
            }
        });
    }
}
