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
        final boolean[] ifcollect = new boolean[1];
        if(convertView==null) {
            view = LayoutInflater.from(getContext()).inflate(resourseId, null);
            viewH = new ViewH();
            viewH.collect= (ImageView) view.findViewById(R.id.iv_room_collect);
            viewH.ll= (LinearLayout) view.findViewById(R.id.ll_for_press);
            viewH.headImage= (ImageView) view.findViewById(R.id.cl_model);
            viewH.name= (TextView) view.findViewById(R.id.tv_model_name);
            view.setTag(viewH);
        }else{
            view=convertView;
            viewH= (ViewH) view.getTag();
        }
        viewH.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getContext(), SetRoom.class);
                in.putExtra("roomName",name);
                getContext().startActivity(in);
            }
        });
        MyDateBase myDateBase = new MyDateBase(getContext(), "Model.db", null, 1);
        final SQLiteDatabase mydb = myDateBase.getWritableDatabase();
        Cursor cursor=mydb.rawQuery("select * from Room where ifCollect=? and id=?", new String[]{"1",name});
        if(cursor.moveToNext()){
            Picasso.with(getContext())
                    .load(R.drawable.ic_collect)
                    .into(viewH.collect);
            ifcollect[0] =true;
        }else{
            Picasso.with(getContext())
                    .load(R.drawable.ic_notcollect)
                    .into(viewH.collect);
            ifcollect[0] =false;
        }
        Picasso.with(getContext())
                .load(headImage)
                .into(viewH.headImage);
        viewH.name.setText(name);
        listItem_Clik_Change(viewH.ll, viewH);
        viewH.collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ifcollect[0]) {
                    mydb.execSQL("update Room set ifCollect=? where id=?", new String[]{"0", name});
                    Picasso.with(getContext())
                            .load(R.drawable.ic_notcollect)
                            .into(viewH.collect);
                } else {
                    mydb.execSQL("update Room set ifCollect=? where id=?", new String[]{"1", name});
                    Picasso.with(getContext())
                            .load(R.drawable.ic_collect)
                            .into(viewH.collect);
                }
                ifcollect[0] =!ifcollect[0];
            }
        });
        viewH.name.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final AlertDialog.Builder di = new AlertDialog.Builder(getContext());
                Vibrator vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(30);
                MyDateBase myDateBase = new MyDateBase(getContext(), "Model.db", null, 1);
                final SQLiteDatabase mydb = myDateBase.getWritableDatabase();
                di.setCancelable(true);
                //布局转view
                LayoutInflater inflater = LayoutInflater.from(getContext());
                View layout = inflater.inflate(R.layout.toast_manage_room, null);
                Button bt_collect = (Button) layout.findViewById(R.id.bt_toast_collect);
                Button bt_change = (Button) layout.findViewById(bt_toast_change);
                Button bt_delete = (Button) layout.findViewById(R.id.bt_toast_delete);
                if(ifcollect[0]){
                    bt_collect.setText("取消收藏");
                }else{
                    bt_collect.setText("收藏此房间");
                }
                di.setView(layout);
                di.create();
                final Dialog dialog = di.show();
                bt_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mydb.execSQL("delete  from Room where id=?", new String[]{name});
                        list.remove(position);
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                bt_collect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ifcollect[0]) {
                            mydb.execSQL("update Room set ifCollect=? where id=?", new String[]{"0", name});
                            Picasso.with(getContext())
                                    .load(R.drawable.ic_notcollect)
                                    .into(viewH.collect);
                        } else {
                            mydb.execSQL("update Room set ifCollect=? where id=?", new String[]{"1", name});
                            Picasso.with(getContext())
                                    .load(R.drawable.ic_collect)
                                    .into(viewH.collect);
                        }
                        ifcollect[0] =!ifcollect[0];
                        dialog.dismiss();
                    }
                });
                bt_change.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent in=new Intent(getContext(), SetRoom.class);
                        in.putExtra("roomName",name);
                        getContext().startActivity(in);
                        dialog.dismiss();
                    }
                });
                return false;
            }
        });
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
        LinearLayout ll;
        ImageView headImage;
        TextView name;
        ImageView collect;
    }
}
