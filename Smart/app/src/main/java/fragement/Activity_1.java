package fragement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tyhj.smart.R;

/**
 * Created by Tyhj on 2016/3/31.
 */
public class Activity_1 extends Fragment {
    public View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_1,null);
        return view;
    }
}
