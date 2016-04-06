package adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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
    Boolean ifcollect;
    View view;
    Cursor cursor;
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
            viewH.headImage= (ImageView) view.findViewById(R.id.cl_model);
            viewH.name= (TextView) view.findViewById(R.id.tv_model_name);
            view.setTag(viewH);
        }else{
            view=convertView;
            viewH= (ViewH) view.getTag();
        }
        viewH.name.setText(name);
        viewH.name.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ifcollect=false;
                mydb=new MyDateBase(getContext(),"Model.db",null,1);
                mysdb=mydb.getWritableDatabase();
                cursor=mysdb.rawQuery("select * from Model where ifCollect=? and id=?", new String[]{"1", name});
                if(cursor.moveToNext()){
                    ifcollect=true;
                }
                cursor.close();
                Vibrator vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(30);
                final AlertDialog.Builder di = new AlertDialog.Builder(getContext());
                di.setCancelable(true);
                //布局转view
                LayoutInflater inflater = LayoutInflater.from(getContext());
                View layout = inflater.inflate(R.layout.toast_manage_model, null);
                Button bt_change= (Button) layout.findViewById(bt_toast_change);
                Button bt_delete= (Button) layout.findViewById(R.id.bt_toast_delete);
                final Button bt_collect= (Button) layout.findViewById(R.id.bt_toast_collect);
                final ImageButton ib_save = (ImageButton) layout.findViewById(R.id.ib_ifcollect);
                if(ifcollect){
                    Picasso.with(getContext()).load(R.drawable.ic_iscollect).resize(80, 80).centerCrop().into(ib_save);
                }else{
                    Picasso.with(getContext()).load(R.drawable.ic_nocollect).resize(80, 80).centerCrop().into(ib_save);
                }
                di.setView(layout);
                di.create();
                final Dialog dialog = di.show();
                bt_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mysdb.delete("Model","id=?",new String[]{name});
                        obj.remove(position);
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                bt_collect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(ifcollect){
                            Picasso.with(getContext()).load(R.drawable.ic_nocollect).resize(80, 80).centerCrop().into(ib_save);
                            mysdb.execSQL("update Model set ifCollect=? where id=?",new String[]{"0",name});
                        }else{
                            mysdb.execSQL("update Model set ifCollect=? where id=?",new String[]{"1",name});
                            Picasso.with(getContext()).load(R.drawable.ic_iscollect).resize(80, 80).centerCrop().into(ib_save);
                        }
                        ifcollect=!ifcollect;
                    }
                });
                ib_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(ifcollect){
                            Picasso.with(getContext()).load(R.drawable.ic_nocollect).resize(80, 80).centerCrop().into(ib_save);
                            mysdb.execSQL("update Model set ifCollect=? where id=?",new String[]{"0",name});
                        }else{
                            mysdb.execSQL("update Model set ifCollect=? where id=?",new String[]{"1",name});
                            Picasso.with(getContext()).load(R.drawable.ic_iscollect).resize(80, 80).centerCrop().into(ib_save);
                        }
                        ifcollect=!ifcollect;
                    }
                });
                return false;
            }
        });
        Picasso.with(getContext())
                .load(headImages[x])
                .into(viewH.headImage);
        return view;
    }
    class ViewH{
        ImageView headImage;
        TextView name;
        TextView content;
    }
}
