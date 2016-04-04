package fragement;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tyhj.smart.ManageMode;
import com.example.tyhj.smart.R;

/**
 * Created by Tyhj on 2016/3/31.
 */
public class Activity_3 extends Fragment {
    View view;
    LinearLayout ll_model,ll_equipment,ll_preset,ll_collect,ll_contral;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_3,null);
        initWidget();
        return view;
    }

    private void initWidget() {
        ll_model= (LinearLayout) view.findViewById(R.id.ll_manage_model);
        ll_equipment= (LinearLayout) view.findViewById(R.id.ll_manage_equipment);
        ll_preset= (LinearLayout) view.findViewById(R.id.ll_manage_preset);
        ll_collect= (LinearLayout) view.findViewById(R.id.ll_manage_collect);
        ll_contral= (LinearLayout) view.findViewById(R.id.ll_manage_contral);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setClik();
    }

    private void setClik() {
        ll_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ll_preset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ll_equipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ll_contral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ll_model.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), ManageMode.class);
                startActivity(intent);
            }
        });
    }
}
