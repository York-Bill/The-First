package adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tyhj.smart.R;
import com.example.tyhj.smart.SetRoom;
import com.squareup.picasso.Picasso;

import java.util.List;

import activity_for_adapter.For_collect_room;
import dataBase.MyDateBase;
import savephoto.GetModelHeadImage;

import static com.example.tyhj.smart.R.id.bt_toast_change;

/**
 * Created by Tyhj on 2016/4/6.
 */
public class Collect_room_Adapter extends ArrayAdapter<For_collect_room> {
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
        final int headImage=for_collect_room.getHeadimage();
        final ViewH viewH;
        final String name=for_collect_room.getName();
        if(convertView==null) {
            view = LayoutInflater.from(getContext()).inflate(resourseId, null);
            viewH = new ViewH();
            viewH.headImage= (ImageView) view.findViewById(R.id.cl_collect_headimage);
            viewH.name= (TextView) view.findViewById(R.id.tv_roomName);
            view.setTag(viewH);
        }else{
            view=convertView;
            viewH= (ViewH) view.getTag();
        }
        return view;
    }
    private void listItem_Clik_Change(final LinearLayout ll,ViewH viewH) {
        viewH.name.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ll.setBackgroundColor(Color.parseColor("#dedede"));
                        break;
                    case MotionEvent.ACTION_UP:
                        ll.setBackgroundColor(Color.parseColor("#00000000"));
                        break;
                    case MotionEvent.ACTION_BUTTON_PRESS:
                        ll.setBackgroundColor(Color.parseColor("#dedede"));
                        break;
                    case MotionEvent.ACTION_BUTTON_RELEASE:
                        ll.setBackgroundColor(Color.parseColor("#00000000"));
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        ll.setBackgroundColor(Color.parseColor("#00000000"));
                        break;
                }
                return false;
            }
        });
    }

    class ViewH{
        ImageView headImage;
        TextView name;
    }
}
