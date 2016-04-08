package fragement;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tyhj.smart.ManageMode;
import com.example.tyhj.smart.R;

import java.util.ArrayList;
import java.util.List;

import Api_sours.PullToZoomListViewEx;
import activity_for_adapter.For_Activity_3;
import adapter.Activity_3_Adapter;

/**
 * Created by Tyhj on 2016/3/31.
 */
public class Activity_3 extends Fragment {
    View view;
    PullToZoomListViewEx lv;
    List<For_Activity_3> list;
    For_Activity_3 for_activity_3;
    Activity_3_Adapter activity_3_adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_3,null);
        initWidget();
        lv.setAdapter(activity_3_adapter);
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        int mScreenHeight = localDisplayMetrics.heightPixels;
        int mScreenWidth = localDisplayMetrics.widthPixels;
        AbsListView.LayoutParams localObject = new AbsListView.LayoutParams(mScreenWidth, (int) (9.0F * (mScreenWidth / 16.0F)));
        lv.setHeaderLayoutParams(localObject);
        return view;
    }

    private void initWidget() {
        list=new ArrayList<For_Activity_3>();
        lv= (PullToZoomListViewEx) view.findViewById(R.id.lv_activity_3);
        for_activity_3=new For_Activity_3("情景模式",R.drawable.model_manage);
        list.add(for_activity_3);
        for_activity_3=new For_Activity_3("智能设备中心",R.drawable.equipment_manage);
        list.add(for_activity_3);
        for_activity_3=new For_Activity_3("预约定时",R.drawable.ic_preset);
        list.add(for_activity_3);
        for_activity_3=new For_Activity_3("收藏管理",R.drawable.ic_collect_manage);
        list.add(for_activity_3);
        for_activity_3=new For_Activity_3("控制中心",R.drawable.ic_manage_control);
        list.add(for_activity_3);
        for_activity_3=new For_Activity_3("个人设置",R.drawable.ic_myset);
        list.add(for_activity_3);
        activity_3_adapter=new Activity_3_Adapter(getActivity(),R.layout.list_for_activity3,list);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setClik();
    }

    private void setClik() {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 1:
                        Intent in=new Intent(getContext(), ManageMode.class);
                        getActivity().startActivity(in);
                }
            }
        });
    }
}
