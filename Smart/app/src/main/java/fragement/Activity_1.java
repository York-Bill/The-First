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

/**
 * Created by Tyhj on 2016/3/31.
 */
public class Activity_1 extends Fragment {
    MyDateBase myDateBase;
    public SQLiteDatabase mydb;
    ListView list_model;
    For_Model fm;
    List<For_Model> mylist;
    Model_Adapter mydp;
    public View view;
    View headview_model;
    Cursor cursor;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_1, null);
        list_model= (ListView) view.findViewById(R.id.model);
        mylist=new ArrayList<For_Model>();
        headview_model = LayoutInflater.from(getActivity()).inflate(R.layout.listhead_model, null, true);
        getModelList();
        mydp=new Model_Adapter(getActivity(),R.layout.model_for_listview,mylist);
        list_model.addHeaderView(headview_model);
        list_model.setAdapter(mydp);
        cursor.close();
        return view;
    }

    private void getModelList() {
        myDateBase=new MyDateBase(getActivity(),"Model.db",null,1);
        mydb=myDateBase.getWritableDatabase();
        cursor=mydb.query("Model", null, null, null, null, null, null);
        if(cursor.moveToNext()){
            do{
                String name=cursor.getString(0);
                int headImage=cursor.getInt(1);
                fm=new For_Model(name,headImage);
                mylist.add(fm);
            }while (cursor.moveToNext());
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        list_model.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        final SwipeRefreshLayout swiperefresh = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        swiperefresh.setColorSchemeResources(
                android.R.color.holo_blue_light);// 设置刷新动画的颜色
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // TODO 自动生成的方法存根
                swiperefresh.setRefreshing(true);// 开始刷新
                // 执行刷新后需要完成的操作

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mylist.clear();
                        getModelList();
                        mydp.notifyDataSetChanged();
                        swiperefresh.setRefreshing(false);// 结束刷新
                    }
                }, 1000);// 刷新动画持续2秒
            }
        });
    }
}
