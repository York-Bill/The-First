package adapter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tyhj.smart.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import activity_for_adapter.For_collect_room;
import dataBase.MyDateBase;
import savephoto.GetModelHeadImage;

/**
 * Created by Tyhj on 2016/4/6.
 */
public class Collect_room_Adapter extends ArrayAdapter<For_collect_room> {
    Cursor cursor;
    ViewH viewH;
    public MyDateBase mydb;
    SQLiteDatabase mysdb;
    int[] headImages= GetModelHeadImage.modelhead;
    View view;
    int resourseId;
    List<For_collect_room> list;
    public Collect_room_Adapter(Context context, int resource, List<For_collect_room> objects) {
        super(context, resource, objects);
        resourseId=resource;
        list=objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        For_collect_room for_collect_room=getItem(position);
        int headImage=for_collect_room.getHeadimage();
        final String name=for_collect_room.getName();
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
        Picasso.with(getContext())
                .load(headImage)
                .into(viewH.headImage);
        viewH.name.setText(name);
        viewH.name.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                list.remove(position);
                Vibrator vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(30);
                MyDateBase myDateBase=new MyDateBase(getContext(),"Model.db",null,1);
                SQLiteDatabase mydb=myDateBase.getWritableDatabase();
                mydb.execSQL("delete  from Room where id=?", new String[]{name});
                return false;
            }
        });
        return view;
    }

    class ViewH{
        ImageView headImage;
        TextView name;
        TextView content;
    }
}
