package fragement;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tyhj.smart.R;

import java.util.ArrayList;
import java.util.List;

import activity_for_adapter.For_Model;
import adapter.Model_Adapter;
import dataBase.MyDateBase;
import savephoto.GetModelHeadImage;

/**
 * Created by Tyhj on 2016/3/31.
 */
public class Activity_1 extends Fragment {
    MyDateBase myDateBase;
    public SQLiteDatabase mydb;
    ListView list_model,list_collect;
    For_Model fm;
    List<For_Model> mylist;
    Model_Adapter mydp;
    public View view;
    View headview_model,headview_collect;
    Cursor cursor;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_1, null);
        list_model= (ListView) view.findViewById(R.id.model);
        mylist=new ArrayList<For_Model>();
        headview_model = LayoutInflater.from(getActivity()).inflate(R.layout.listhead_model, null, true);
        //添加收藏的东西
        headview_collect= LayoutInflater.from(getActivity()).inflate(R.layout.listhead_collect, null, true);
        list_collect.addHeaderView(headview_collect);
        //
        getModelList();
        mydp=new Model_Adapter(getActivity(),R.layout.model_for_listview,mylist);
        list_model.addHeaderView(headview_model);
        list_model.setAdapter(mydp);
        return view;
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
        cursor.close();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        mylist.clear();
        getModelList();
        mydp.notifyDataSetChanged();
    }
}
