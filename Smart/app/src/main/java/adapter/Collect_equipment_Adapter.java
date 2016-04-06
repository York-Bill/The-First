package adapter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tyhj.smart.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import activity_for_adapter.For_collect_equipment;
import dataBase.MyDateBase;
import savephoto.GetModelHeadImage;

/**
 * Created by Tyhj on 2016/4/5.
 */
public class Collect_equipment_Adapter extends ArrayAdapter<For_collect_equipment> {
    Cursor cursor;
    ViewH viewH;
    public MyDateBase mydb;
    SQLiteDatabase mysdb;
    View view;
    int resourseId;
    List<For_collect_equipment> list;
    public Collect_equipment_Adapter(Context context, int resource, List<For_collect_equipment> objects) {
        super(context, resource, objects);
        resourseId=resource;
        list=objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        For_collect_equipment collect=getItem(position);
        int headimage=collect.getHeadImage();
        String name=collect.getName();
        if(convertView==null) {
            view = LayoutInflater.from(getContext()).inflate(resourseId, null);
            viewH = new ViewH();
            viewH.headImage= (ImageView) view.findViewById(R.id.cl_model);
            viewH.name= (TextView) view.findViewById(R.id.tv_model_name);
            view.setTag(viewH);
        }else{
            view=convertView;
            viewH= (ViewH) view.getTag();
        }
        viewH.name.setText(name);
        Picasso.with(getContext())
                .load(headimage)
                .into(viewH.headImage);
        return view;
    }
    class ViewH{
        ImageView headImage;
        TextView name;

    }
}
