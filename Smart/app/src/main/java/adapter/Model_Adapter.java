package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.tyhj.smart.R;
import com.squareup.picasso.Picasso;

import java.util.List;
import Api_sours.CircularImage;
import activity_for_adapter.For_Model;
import savephoto.GetModelHeadImage;

/**
 * Created by Tyhj on 2016/4/3.
 */
public class Model_Adapter extends ArrayAdapter<For_Model> {
    int resourseId;
    View view;
    ViewH viewH;
    int[] headImages= GetModelHeadImage.modelhead;
    public Model_Adapter(Context context, int resource, List<For_Model> objects) {
        super(context, resource, objects);
        resourseId=resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        For_Model for_model=getItem(position);
        String name=for_model.getName();
        int x=for_model.getHeahImage();
        if(convertView==null) {
            view = LayoutInflater.from(getContext()).inflate(resourseId, null);
            viewH = new ViewH();
            viewH.headImage= (CircularImage) view.findViewById(R.id.cl_model);
            viewH.name= (TextView) view.findViewById(R.id.tv_model_name);
            view.setTag(viewH);
        }else{
            view=convertView;
            viewH= (ViewH) view.getTag();
        }
        viewH.name.setText(name);
        Picasso.with(getContext())
                .load(headImages[x])
                .resize(160,160)
                .centerCrop()
                .into(viewH.headImage);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //详情
            }
        });
        return view;
    }
}
class ViewH{
    CircularImage headImage;
    TextView name;
    TextView content;
}