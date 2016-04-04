package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tyhj.smart.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import Api_sours.CircularImage;
import activity_for_adapter.For_Model;
import activity_for_adapter.For_ModelHead;
import savephoto.GetModelHeadImage;

/**
 * Created by Tyhj on 2016/4/4.
 */
public class Model_HeadImage_Adapter extends ArrayAdapter<For_ModelHead> {
    int[] imageId= GetModelHeadImage.modelhead;
    int resourceId;
    View view;
    For_ModelHead fmd;
    ViewHold viewH;
    WindowManager wm = (WindowManager) getContext()
            .getSystemService(Context.WINDOW_SERVICE);
     int width = wm.getDefaultDisplay().getWidth();
    public Model_HeadImage_Adapter(Context context, int resource, List<For_ModelHead> objects) {
        super(context, resource, objects);
        resourceId=resource;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        fmd=getItem(position);
        int x=fmd.getCount();
        if(convertView==null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewH = new ViewHold();
            viewH.iv= (ImageView) view.findViewById(R.id.iv_for_gridview);
            view.setTag(viewH);
        }else{
            view=convertView;
            viewH= (ViewHold) view.getTag();
        }
        Picasso.with(getContext())
                .load(imageId[x])
                .resize(width/12, width/12)
                .centerCrop()
                .into(viewH.iv);
        return view;
    }
    class ViewHold{
        ImageView iv;
    }
}