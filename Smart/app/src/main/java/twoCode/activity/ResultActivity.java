package twoCode.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.example.tyhj.smart.MyLogin;
import com.example.tyhj.smart.R;
import com.google.zxing.WriterException;

import java.util.List;

import Api_sours.StatusBarUtil;
import savephoto.GetModelHeadImage;

/**
 * Created by Tyhj on 2016/4/1.
 */
public class ResultActivity extends Activity {
    String userId;
    LinearLayout ll_twocode;
    ImageView mytwocode;
    TextView pname,phone_number,email,place,sex,area,psign,tvWho;
    ImageButton two_image;
    ImageButton ic_back;
    boolean show;
    Button turnoff;
    List<AVUser> list1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_smarte);
        initWidget();
    }
    private void initWidget() {
        mytwocode= (ImageView) findViewById(R.id.mytwocode1);
        ll_twocode= (LinearLayout) findViewById(R.id.ll_twocode1);
        ic_back= (ImageButton) findViewById(R.id.button_back1);
        two_image= (ImageButton) findViewById(R.id.two_image1);
        pname= (TextView) findViewById(R.id.pname1);
        phone_number= (TextView) findViewById(R.id.phone_number1);
        email= (TextView) findViewById(R.id.email1);
        place= (TextView) findViewById(R.id.place1);
        sex= (TextView) findViewById(R.id.sex1);
        area= (TextView) findViewById(R.id.area1);
        psign= (TextView) findViewById(R.id.psign1);
        tvWho= (TextView) findViewById(R.id.tvWho);
        turnoff= (Button) findViewById(R.id.turnoff1);
        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            userId = extras.getString("result");
            Bitmap qrCodeBitmap = null;
            Bitmap qrCodeBitmap1 = null;
            try {
                qrCodeBitmap = EncodingHandler.createQRCode(userId, 200);
                qrCodeBitmap1 = EncodingHandler.createQRCode(userId, 500);
            } catch (WriterException e) {
                e.printStackTrace();
            }
            two_image.setImageBitmap(qrCodeBitmap);
            mytwocode.setImageBitmap(qrCodeBitmap1);
        }
        initUser();
        setOnclick();
    }
    private void initUser() {
        AVQuery<AVUser> userQuery = new AVQuery<>("_User");
        userQuery.whereEqualTo("username",userId);
        userQuery.findInBackground(new FindCallback<AVUser>() {
            @Override
            public void done(List<AVUser> list, AVException e) {
            if(e==null){
                list1=list;
                set(list);
            }else{
                Toast.makeText(ResultActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
            }
        });
    }

    private void set(List<AVUser> list) {
        pname.setText(list.get(0).get("pname").toString());
        phone_number.setText(list.get(0).get("username").toString());
        email.setText(list.get(0).get("email").toString());
        place.setText(list.get(0).get("dizi").toString());
        sex.setText(list.get(0).get("sex").toString());
        area.setText(list.get(0).get("area").toString());
        tvWho.setText(list.get(0).get("pname").toString()+"的信息");
        String str=list.get(0).get("signature").toString();
        if(str.length()>17)
            str=str.substring(0,17)+"~";
        psign.setText(str);
    }

    private void setOnclick() {
        turnoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //授权
            }
        });
        turnoff.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        turnoff.setBackgroundColor(Color.parseColor("#f0ff3333"));
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_BUTTON_PRESS:
                        turnoff.setBackgroundColor(Color.parseColor("#b0ff3333"));
                }
                return false;
            }
        });
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResultActivity.this.finish();
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
    Handler hander=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    set(list1);
                    break;
            }
        }
    };
}

