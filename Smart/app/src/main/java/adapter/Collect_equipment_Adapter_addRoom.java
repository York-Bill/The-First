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
public class Collect_equipment_Adapter_addRoom extends ArrayAdapter<For_collect_equipment> {
    Cursor cursor;
    ViewH viewH;
    MyDateBase myDateBase;
    View view;
    int resourseId;
    List<For_collect_equipment> list;
    public Collect_equipment_Adapter_addRoom(Context context, int resource, List<For_collect_equipment> objects) {
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
                final ImageButton collect= (ImageButton) layout.findViewById(R.id.ib_save_equipment);
                if(ifCollect) Picasso.with(getContext()).load(R.drawable.ic_collect).resize(60,60).centerCrop().into(collect);
                else Picasso.with(getContext()).load(R.drawable.ic_notcollect).resize(60,60).centerCrop().into(collect);
                di.setView(layout);
                di.create();
                final Dialog dialog = di.show();
                bt_toast_addTimePreset_equipment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent in=new Intent(getContext(), CreatePreset.class);
                        in.putExtra("presetId",id);
                        dialog.dismiss();
                        getContext().startActivity(in);
                    }
                });
                bt_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mydb[0] = myDateBase.getWritableDatabase();
                        mydb[0].execSQL("delete from Equipment where id=?",new Object[]{id});
                        list.remove(position);
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                final boolean[] finalIfCollect = {ifCollect};
                bt_collect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(finalIfCollect[0]){
                            Picasso.with(getContext()).load(R.drawable.ic_notcollect).resize(60,60).centerCrop().into(collect);
                            mydb[0].execSQL("update Equipment set ifCollect=?",new Object[]{0});
                            list.remove(position);
                            notifyDataSetChanged();
                        }else {
                            mydb[0].execSQL("update Equipment set ifCollect=?",new Object[]{1});
                            Picasso.with(getContext()).load(R.drawable.ic_collect).resize(60,60).centerCrop().into(collect);
                        }
                        finalIfCollect[0] =!finalIfCollect[0];
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
