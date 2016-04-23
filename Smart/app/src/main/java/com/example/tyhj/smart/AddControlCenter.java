package com.example.tyhj.smart;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by Tyhj on 2016/4/23.
 */
public class AddControlCenter extends Activity implements View.OnClickListener {
    ImageButton ib_back_controlCenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcontrolcenter);
        ib_back_controlCenter= (ImageButton) findViewById(R.id.ib_back_controlCenter);
        ib_back_controlCenter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_back_controlCenter:
                AddControlCenter.this.finish();
                break;
        }
    }
}
