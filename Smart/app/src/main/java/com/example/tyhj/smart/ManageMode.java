package com.example.tyhj.smart;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Tyhj on 2016/4/3.
 */
public class ManageMode extends Activity{
    FloatingActionButton fab;
    Animation rotate1,rotate2;
    boolean roteta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_model);
        initWidget();
        onClik();
    }

    private void onClik() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!roteta) {
                    fab.startAnimation(rotate1);
                }else {
                    fab.startAnimation(rotate2);
                }
                addModle();
                roteta=!roteta;
            }
        });
    }

    private void addModle() {
        final AlertDialog.Builder di = new AlertDialog.Builder(ManageMode.this);
        di.setCancelable(true);
        //布局转view
        LayoutInflater inflater = LayoutInflater.from(ManageMode.this);
        View layout = inflater.inflate(R.layout.jump, null);
        ImageButton ib_save= (ImageButton) layout.findViewById(R.id.ib_save);
        final EditText et_add_name= (EditText) layout.findViewById(R.id.et_add_name);
        final TextView et_add_number= (TextView) layout.findViewById(R.id.et_add_number);
        di.setView(layout);
        di.create();
        final Dialog dialog =di.show();

    }

    private void initWidget() {
        fab= (FloatingActionButton) findViewById(R.id.fab);
        rotate1 = AnimationUtils.loadAnimation(ManageMode.this, R.anim.rotate);
        rotate2 = AnimationUtils.loadAnimation(ManageMode.this, R.anim.rotatepositin);
        rotate1.setFillAfter(true);
        rotate2.setFillAfter(true);
    }
}
