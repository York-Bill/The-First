package Donghua;

import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

/**
 * Created by Tyhj on 2016/4/1.
 */
public class BgChange{
    public void Bt_press(MenuItem bt){
       bt.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
           @Override
           public boolean onMenuItemClick(MenuItem item) {
               return false;
           }
       });
    }
}
