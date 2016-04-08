package adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tyhj.smart.ManageMode;
import com.example.tyhj.smart.R;

import java.util.List;

import activity_for_adapter.For_Activity_3;

/**
 * Created by Tyhj on 2016/4/8.
 */
public class Activity_3_Adapter extends ArrayAdapter<For_Activity_3> {
    int resourseId;
    View view;
    ViewH viewH;
    For_Activity_3 for_activity_3;
    public Activity_3_Adapter(Context context, int resource, List<For_Activity_3> objects) {
        super(context, resource, objects);
        resourseId=resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        for_activity_3=getItem(position);
        final String name=for_activity_3.getName();
        if(convertView==null){
            view = LayoutInflater.from(getContext()).inflate(resourseId, null);
            viewH = new ViewH();
            viewH.ll= (LinearLayout) view.findViewById(R.id.ll_for_activity3);
            viewH.head= (ImageView) view.findViewById(R.id.cl_lv_activity3);
            viewH.name= (TextView) view.findViewById(R.id.tv_activity3);
            view.setTag(viewH);
        }else{
            view=convertView;
            viewH= (ViewH) view.getTag();
        }
        final LinearLayout ll=viewH.ll;
        viewH.name.setText(for_activity_3.getName());
        viewH.head.setBackgroundResource(for_activity_3.getHeadIameg());
      /*  viewH.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (name){
                    case "情景模式":
                        Intent in=new Intent(getContext(), ManageMode.class);
                        getContext().startActivity(in);
                }
            }
        });*/
        return view;
    }
    class ViewH{
        LinearLayout ll;
        ImageView head;
        TextView name;
    }
}
