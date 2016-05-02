package com.example.tyhj.smart;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVUser;
import com.google.zxing.WriterException;

import savephoto.GetModelHeadImage;
import twoCode.activity.EncodingHandler;

/**
 * Created by Tyhj on 2016/5/2.
 */
public class UserMessage extends Activity {
    LinearLayout ll_twocode;
    ImageView mytwocode;
    TextView pname,phone_number,email,place,sex,area,psign;
    ImageButton two_image;
    ImageButton ic_back;
    Button change;
    AVUser user;
    boolean show;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personalcenter);
        initWidget();
        String name= GetModelHeadImage.getUserId();
        Bitmap qrCodeBitmap = null;
        Bitmap qrCodeBitmap1 = null;
        try {
            qrCodeBitmap = EncodingHandler.createQRCode(name, 200);
            qrCodeBitmap1 = EncodingHandler.createQRCode(name, 500);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        two_image.setImageBitmap(qrCodeBitmap);
        mytwocode.setImageBitmap(qrCodeBitmap1);
        user=AVUser.getCurrentUser();
        pname.setText(user.getString("pname")+"");
        phone_number.setText(user.getUsername()+"");
        email.setText(user.getEmail());
        place.setText(user.getString("dizi")+"");
        sex.setText(user.getString("sex")+"");
        area.setText(user.getString("area")+"");
        String str=user.getString("signature");
        if(str.length()>18)
            str=str.substring(0,18)+"~";
        psign.setText(str);
    }

    private void initWidget() {
        mytwocode= (ImageView) findViewById(R.id.mytwocode);
        ll_twocode= (LinearLayout) findViewById(R.id.ll_twocode);
        ic_back= (ImageButton) findViewById(R.id.button_back);
        change= (Button) findViewById(R.id.change);
        two_image= (ImageButton) findViewById(R.id.two_image);
        pname= (TextView) findViewById(R.id.pname);
        phone_number= (TextView) findViewById(R.id.phone_number);
        email= (TextView) findViewById(R.id.email);
        place= (TextView) findViewById(R.id.place);
        sex= (TextView) findViewById(R.id.sex);
        area= (TextView) findViewById(R.id.area);
        psign= (TextView) findViewById(R.id.psign);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!pname.getText().toString().equals(""))
                    user.put("pname",pname.getText().toString());
                if(!place.getText().toString().equals(""))
                    user.put("dizi",place.getText().toString());
                if(!sex.getText().toString().equals("")){
                    if(sex.getText().toString().equals("男")||sex.getText().toString().equals("女")||sex.getText().toString().equals("man")||sex.getText().toString().equals("woman"))
                        user.put("sex",sex.getText().toString());
                }
                if(!area.getText().toString().equals(""))
                    user.put("area",area.getText().toString());
                if(!psign.getText().toString().equals(""))
                    user.put("signature",psign.getText().toString());
                user.saveInBackground();
                Toast.makeText(UserMessage.this,"已保存",Toast.LENGTH_SHORT).show();
            }
        });
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserMessage.this.finish();
            }
        });
        two_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(show){
                    ll_twocode.setVisibility(View.GONE);
                }else {
                    ll_twocode.setVisibility(View.VISIBLE);
                }
                show=!show;
            }
        });
        ll_twocode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(show){
                    ll_twocode.setVisibility(View.GONE);
                }else {
                    ll_twocode.setVisibility(View.VISIBLE);
                }
                show=!show;
            }
        });
    }
}
