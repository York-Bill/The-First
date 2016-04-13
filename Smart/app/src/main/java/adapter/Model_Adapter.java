package adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tyhj.smart.R;
import com.squareup.picasso.Picasso;

import java.util.List;
import Api_sours.CircularImage;
import activity_for_adapter.For_Model;
import dataBase.MyDateBase;
import savephoto.GetModelHeadImage;

import static com.example.tyhj.smart.R.id.bt_toast_change;

/**
 * Created by Tyhj on 2016/4/3.
 */
public class Model_Adapter extends ArrayAdapter<For_Model> {
    int resourseId;
    View view;
    ViewH viewH;
    public MyDateBase mydb;
    SQLiteDatabase mysdb;
    int[] headImages= GetModelHeadImage.modelhead;
    List<For_Model> obj;
    public Model_Adapter(Context context, int resource, List<For_Model> objects) {
        super(context, resource, objects);
        resourseId=resource;
        obj=objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        For_Model for_model=getItem(position);
        final String name=for_model.getName();
        int x=for_model.getHeahImage();
        if(convertView==null) {
            view = LayoutInflater.from(getContext()).inflate(resourseId, null);
            viewH = new ViewH();
            viewH.ll= (LinearLayout) view.findViewById(R.id.ll_for_press);
            viewH.headImage= (ImageView) view.findViewById(R.id.cl_model);
            viewH.name= (TextView) view.findViewById(R.id.tv_model_name);
            view.setTag(viewH);
        }else{
            view=convertView;
            viewH= (ViewH) view.getTag();
        }
        final LinearLayout ll=viewH.ll;
        viewH.name.setText(name);
        viewH.name.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mydb = new MyDateBase(getContext(), GetModelHeadImage.getUserId()+".db", null, 1);
                mysdb = mydb.getWritableDatabase();
                Vibrator vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(30);
                final AlertDialog.Builder di = new AlertDialog.Builder(getContext());
                di.setCancelable(true);
                //布局转view
                LayoutInflater inflater = LayoutInflater.from(getContext());
                View layout = inflater.inflate(R.layout.toast_manage_model, null);
                Button bt_change = (Button) layout.findViewById(bt_toast_change);
                Button bt_delete = (Button) layout.findViewById(R.id.bt_toast_delete);
                di.setView(layout);
                di.create();
                final Dialog dialog = di.show();
                bt_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mysdb.delete("Model", "id=?", new String[]{name});
                        obj.remove(position);
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                return false;
            }
        });
        Picasso.with(getContext())
                .load(headImages[x])
                .into(viewH.headImage);
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
        return view;
    }
    class ViewH{
        LinearLayout ll;
        ImageView headImage;
        TextView name;
        TextView content;
    }
}
