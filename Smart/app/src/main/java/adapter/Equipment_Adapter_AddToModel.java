package adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tyhj.smart.CreatePreset;
import com.example.tyhj.smart.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import activity_for_adapter.For_collect_equipment;
import dataBase.MyDateBase;
import savephoto.GetModelHeadImage;

/**
 * Created by Tyhj on 2016/4/5.
 */
public class Equipment_Adapter_AddToModel extends ArrayAdapter<For_collect_equipment> {
    Cursor cursor;
    ViewH viewH;
    MyDateBase myDateBase;
    View view;
    int resourseId;
    List<For_collect_equipment> list;
    public Equipment_Adapter_AddToModel(Context context, int resource, List<For_collect_equipment> objects) {
        super(context, resource, objects);
        resourseId=resource;
        list=objects;
        myDateBase=new MyDateBase(getContext(), GetModelHeadImage.getUserId()+".db",null,1);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final For_collect_equipment collect=getItem(position);
        final String id=collect.getId();
        final SQLiteDatabase[] mydb = {myDateBase.getWritableDatabase()};
        int headimage=collect.getHeadImage();
        String name=collect.getName();
        if(convertView==null) {
            view = LayoutInflater.from(getContext()).inflate(resourseId, null);
            viewH = new ViewH();
            viewH.headImage= (ImageView) view.findViewById(R.id.cl_headimage_equipment);
            viewH.name= (TextView) view.findViewById(R.id.tv_equipment_name);
            viewH.room= (TextView) view.findViewById(R.id.tv_where_the_equipment);
            view.setTag(viewH);
        }else{
            view=convertView;
            viewH= (ViewH) view.getTag();
        }
        viewH.room.setText(collect.getRoom());
        viewH.name.setText(name);
        Picasso.with(getContext())
                .load(headimage)
                .into(viewH.headImage);
                view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                boolean ifCollect=false;
                mydb[0] = myDateBase.getWritableDatabase();
                if(collect.getIfcollect()==1) ifCollect=true;
                final AlertDialog.Builder di = new AlertDialog.Builder(getContext());
                di.setCancelable(true);
                //布局转view
                LayoutInflater inflater = LayoutInflater.from(getContext());
                View layout = inflater.inflate(R.layout.toast_collect_equipment, null);
                Button bt_toast_addTimePreset_equipment= (Button) layout.findViewById(R.id.bt_toast_addTimePreset_equipment);
                Button bt_delete = (Button) layout.findViewById(R.id.bt_toast_delete_equipment);
                Button bt_collect = (Button) layout.findViewById(R.id.bt_toast_collect_equipment);
                ImageButton image= (ImageButton) layout.findViewById(R.id.ib_save_equipment);
                bt_collect.setVisibility(View.GONE);
                image.setVisibility(View.GONE);
                bt_toast_addTimePreset_equipment.setVisibility(View.GONE);
                bt_delete.setText("移除电器");
                di.setView(layout);
                di.create();
                final Dialog dialog = di.show();
                bt_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        list.remove(position);
                        mydb[0].execSQL("delete from InModel where equipmentId=?",new Object[]{id});
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                return true;
            }
        });
        return view;
    }
    class ViewH{
        ImageView headImage;
        TextView name;
        TextView room;
    }
}
