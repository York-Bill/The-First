package twoCode.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tyhj.smart.R;

import Api_sours.StatusBarUtil;

/**
 * Created by Tyhj on 2016/4/1.
 */
public class ResultActivity extends Activity {
    TextView tv_result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_smarte);
        //系统栏透明
        StatusBarUtil.setTransparent(this);
        tv_result= (TextView) findViewById(R.id.tv_result);
        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            String result = extras.getString("result");
            tv_result.setText(result);
        }
    }
}

