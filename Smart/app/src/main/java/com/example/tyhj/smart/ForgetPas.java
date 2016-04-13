package com.example.tyhj.smart;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.RequestPasswordResetCallback;

/**
 * Created by Tyhj on 2016/4/10.
 */
public class ForgetPas extends Activity {
    ImageButton ib_back_resetPassword;
    Button bt_resetPassword;
    EditText et_forgetPas_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgetpassword);
        initWidget();
        setClick();
    }
    private void initWidget() {
        ib_back_resetPassword= (ImageButton) findViewById(R.id.ib_back_resetPassword);
        bt_resetPassword= (Button) findViewById(R.id.bt_resetPassword);
        et_forgetPas_email= (EditText) findViewById(R.id.et_forgetPas_email);
    }
    private void setClick() {
        //重设密码
        bt_resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AVUser.requestPasswordResetInBackground(et_forgetPas_email.getText().toString(), new RequestPasswordResetCallback() {
                    public void done(AVException e) {
                        if (e == null) {
                            Snackbar.make(v, "请求成功，请先到邮箱进行密码重设", Snackbar.LENGTH_SHORT)
                                    .setAction("Action", null).show();
                            ForgetPas.this.finish();
                        } else {
                            registerError(e,et_forgetPas_email);
                        }
                    }
                });
            }
        });
        ib_back_resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForgetPas.this.finish();
            }
        });
    }
    //错误提醒
    private void registerError(AVException e, EditText et_add_email) {
        if (e.getMessage().equals(
                "java.net.UnknownHostException")) {
            Toast.makeText(getApplicationContext(),
                    "网络出错，请检查网络连接", Toast.LENGTH_SHORT).show();
        } else if (e.getMessage().substring(8, 11)
                .equals("204")) {
            Toast.makeText(getApplicationContext(),
                    "请先输入邮箱地址",  Toast.LENGTH_SHORT).show();
            et_add_email.setText("");
        } else if (e.getMessage().substring(8, 11)
                .equals("125")) {
            Toast.makeText(getApplicationContext(),
                    "未找到该邮箱，请核对后输入",  Toast.LENGTH_SHORT).show();
            et_add_email.setText("");
        }else if(e.getMessage().substring(8, 11).equals("205")){
            Toast.makeText(getApplicationContext(),
                    "请输入该账号注册时填写邮箱地址",  Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(),
                    e.getMessage(),  Toast.LENGTH_SHORT).show();
        }
    }
}
