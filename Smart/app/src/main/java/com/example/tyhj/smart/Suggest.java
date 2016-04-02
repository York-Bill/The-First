package com.example.tyhj.smart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Created by Tyhj on 2016/4/2.
 */
public class Suggest extends Activity {
    Button bt_saveMessage;
    ImageButton ib_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suggest);
        initWidget();
        initClik();
    }
    //点击事件
    private void initClik() {
        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(Suggest.this,MainActivity.class);
                startActivity(in);
                overridePendingTransition(R.anim.activity_in_left,R.anim.activity_out_right);
                Suggest.this.finish();
            }
        });
        bt_saveMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(Suggest.this,MainActivity.class);
                Toast.makeText(Suggest.this, "提交成功，感谢您的意见", Toast.LENGTH_SHORT).show();
                startActivity(in);
                overridePendingTransition(R.anim.activity_in_left,R.anim.activity_out_right);
                Suggest.this.finish();
            }
        });
    }
   // 初始化控件
    private void initWidget() {
        ib_back= (ImageButton) findViewById(R.id.ib_back_suggeset);
        bt_saveMessage= (Button) findViewById(R.id.bt_saveSuggest);
    }
}
