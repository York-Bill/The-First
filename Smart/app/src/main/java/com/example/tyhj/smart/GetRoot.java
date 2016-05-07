package com.example.tyhj.smart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;

import java.util.List;

import savephoto.GetModelHeadImage;
import twoCode.activity.CaptureActivity;
import twoCode.activity.ResultActivity;

/**
 * Created by Tyhj on 2016/4/23.
 */
public class GetRoot extends Activity {
    ImageButton ib_back_addRoot;
    ImageView ivsaoTwo;
    Button btfinduser;
    EditText etgetuserid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.getroot);
        btfinduser= (Button) findViewById(R.id.btfinduser);
        ivsaoTwo= (ImageView) findViewById(R.id.ivsaoTwo);
        etgetuserid= (EditText) findViewById(R.id.etgetuserid);
        ib_back_addRoot= (ImageButton) findViewById(R.id.ib_back_addRoot);
        ib_back_addRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetRoot.this.finish();
            }
        });
        btfinduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!etgetuserid.getText().toString().equals("")&& GetModelHeadImage.getInternet(GetRoot.this))
                getUser();
                else if(!GetModelHeadImage.getInternet(GetRoot.this))
                    Toast.makeText(GetRoot.this,"网络已断开，请检查网络",Toast.LENGTH_SHORT).show();
            }
        });
        ivsaoTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_TwoCode=new Intent(GetRoot.this, CaptureActivity.class);
                startActivity(intent_TwoCode);
            }
        });
    }

    private void getUser() {
        AVQuery<AVUser> userQuery = new AVQuery<>("_User");
        userQuery.whereEqualTo("username",etgetuserid.getText().toString());
        userQuery.findInBackground(new FindCallback<AVUser>() {
            @Override
            public void done(List<AVUser> list, AVException e) {
                if (e == null && list.size() > 0) {
                    Bundle bundle=new Bundle();
                    bundle.putString("result", etgetuserid.getText().toString());
                    startActivity(new Intent(GetRoot.this, ResultActivity.class).putExtras(bundle));
                    GetRoot.this.finish();
                } else if(e==null){
                    Toast.makeText(GetRoot.this,"未找到该用户",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
