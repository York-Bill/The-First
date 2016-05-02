package adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tyhj.smart.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import activity_for_adapter.For_collect_room;
import savephoto.GetModelHeadImage;

/**
 * Created by Tyhj on 2016/4/6.
 */
public class Room_Adapter extends ArrayAdapter<For_collect_room> {
    View view;
    int resourseId;
    int[] headimage=GetModelHeadImage.roomhead;
    List<For_collect_room> list;
    public Room_Adapter(Context context, int resource, List<For_collect_room> objects) {
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
            viewH.headImage= (ImageView) view.findViewById(R.id.cl_room_headImage);
            viewH.name= (TextView) view.findViewById(R.id.tv_room_name);
            view.setTag(viewH);
        }else{
            view=convertView;
            viewH= (ViewH) view.getTag();
        }
        viewH.name.setText(for_collect_room.getName());
        Picasso.with(getContext())
                .load(headimage[for_collect_room.getHeadimage()])
                .into(viewH.headImage);
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
