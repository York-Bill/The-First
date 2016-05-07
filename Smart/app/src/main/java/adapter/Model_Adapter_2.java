package adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tyhj.smart.CreatePreset;
import com.example.tyhj.smart.R;
import com.example.tyhj.smart.SetModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import Api_sours.CircularImage;
import activity_for_adapter.For_Model;
import dataBase.MyDateBase;
import savephoto.GetModelHeadImage;

/**
 * Created by Tyhj on 2016/4/3.
 */
public class Model_Adapter_2 extends ArrayAdapter<For_Model> {
    int resourseId;
    ViewH viewH;
    public MyDateBase mydb;
    SQLiteDatabase mysdb;
    int[] headImages= GetModelHeadImage.mosiheah;
    List<For_Model> obj;
    public Model_Adapter_2(Context context, int resource, List<For_Model> objects) {
        super(context, resource, objects);
        resourseId=resource;
        obj=objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final boolean[] finalOp = {false};
        For_Model for_model=getItem(position);
        final View view;
        final String name=for_model.getName();
        final int x=for_model.getHeahImage();
        if(convertView==null) {
            view = LayoutInflater.from(getContext()).inflate(resourseId, null);
            viewH = new ViewH();
            viewH.ll= (LinearLayout) view.findViewById(R.id.ll_head_activity1);
            viewH.headImage= (ImageView) view.findViewById(R.id.cl_collect_headimage);
            viewH.name= (TextView) view.findViewById(R.id.tv_roomName);
            view.setTag(viewH);
        }else{
            view=convertView;
            viewH= (ViewH) view.getTag();
        }
        final LinearLayout ll=viewH.ll;
        viewH.name.setText(name);
        if(x==100)
            viewH.headImage.setVisibility(View.INVISIBLE);
        else
        Picasso.with(getContext())
                .load(headImages[x])
                .into(viewH.headImage);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (x != 100) {
                    if (!finalOp[0]) {
                        view.setBackgroundColor(Color.parseColor("#ccff33"));
                        Snackbar.make(v, name + "模式已开启", Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                    } else {
                        view.setBackgroundColor(Color.parseColor("#fafafa"));
                        Snackbar.make(v, name + "模式已关闭", Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                    }
                    finalOp[0] = !finalOp[0];
                }
            }
        });

        return view;
    }
    class ViewH{
        LinearLayout ll;
        ImageView headImage;
        TextView name;
        TextView content;
    }
}
