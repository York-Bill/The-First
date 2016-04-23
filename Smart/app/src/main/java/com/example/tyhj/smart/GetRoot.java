package com.example.tyhj.smart;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by Tyhj on 2016/4/23.
 */
public class GetRoot extends Activity {
    ImageButton ib_back_addRoot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.getroot);
        ib_back_addRoot= (ImageButton) findViewById(R.id.ib_back_addRoot);
        ib_back_addRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetRoot.this.finish();
            }
        });

    }
}
