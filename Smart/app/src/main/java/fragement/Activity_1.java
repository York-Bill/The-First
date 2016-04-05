package fragement;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_1,null);
        headview_model= getActivity().getLayoutInflater().inflate(R.layout.listhead_model, null);
        list_model= (ListView) view.findViewById(R.id.model);
        mylist=new ArrayList<For_Model>();
        myDateBase=new MyDateBase(getActivity(),"Model.db",null,1);
        mydb=myDateBase.getWritableDatabase();
        Cursor cursor=mydb.query("Model",null,null,null,null,null,null);
        if(cursor.moveToNext()){
            do{
                String name=cursor.getString(0);
                int headImage=cursor.getInt(1);
                fm=new For_Model(name,headImage);
                mylist.add(fm);
            }while (cursor.moveToNext());
        }
        mydp=new Model_Adapter(getActivity(),R.layout.model_for_listview,mylist);
        list_model.addHeaderView(headview_model);
        list_model.setAdapter(mydp);
        cursor.close();
        return view;
    }
}
