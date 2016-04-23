package adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tyhj.smart.CreatePreset;
import com.example.tyhj.smart.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import activity_for_adapter.For_Preset;

/**
 * Created by Tyhj on 2016/4/20.
 */
public class Preset_Adapter extends ArrayAdapter<For_Preset> {
    int resorceId;
    List<For_Preset> list;
    public Preset_Adapter(Context context, int resource, List<For_Preset> objects) {
        super(context, resource, objects);
        resorceId=resource;
        list=objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final For_Preset for_preset=getItem(position);
        View view = null;
        ViewH viewH;
        if(convertView==null){
            viewH=new ViewH();
            view= LayoutInflater.from(getContext()).inflate(resorceId,null);
            viewH.imIfhave= (ImageView) view.findViewById(R.id.imIfhave);
            viewH.headImage= (ImageView) view.findViewById(R.id.cl_headimage_equipment_preset);
            viewH.days= (TextView) view.findViewById(R.id.tv_day_preset);
            viewH.name= (TextView) view.findViewById(R.id.tv_equipment_name_preset);
            viewH.time= (TextView) view.findViewById(R.id.tv_time_preset);
            viewH.tv_where_the_equipment_preset= (TextView) view.findViewById(R.id.tv_where_the_equipment_preset);
            viewH.aSwitch= (Switch) view.findViewById(R.id.sw_equipment_preset);
            view.setTag(viewH);
        }else{
            view=convertView;
            viewH= (ViewH) view.getTag();
        }
        Picasso.with(getContext()).load(for_preset.getHeadImage()).into(viewH.headImage);
        viewH.time.setText(for_preset.getTimefrom());
        viewH.name.setText(for_preset.getId());
        viewH.days.setText(for_preset.getDays());
        if(for_preset.getWhere()!=null)
        viewH.tv_where_the_equipment_preset.setText(for_preset.getWhere());
        else {
            viewH.imIfhave.setVisibility(View.GONE);
            viewH.tv_where_the_equipment_preset.setVisibility(View.GONE);
        }
        viewH.aSwitch.setChecked(for_preset.isMyswitch());
        viewH.aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(getContext(),"开",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(),"关",Toast.LENGTH_SHORT).show();
                }
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getContext(), CreatePreset.class);
                in.putExtra("presetId",for_preset.getId());
                getContext().startActivity(in);
            }
        });
        return view;
    }
    class ViewH{
        ImageView headImage;
        TextView name;
        TextView time;
        ImageView imIfhave;
        TextView tv_where_the_equipment_preset;
        TextView days;
        Switch aSwitch;
    }
}
