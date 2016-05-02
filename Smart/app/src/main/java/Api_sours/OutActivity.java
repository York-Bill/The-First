package Api_sours;

import android.app.Activity;

/**
 * Created by Tyhj on 2016/5/3.
 */
public class OutActivity {
    private static Activity activity;
    public static void setAc(Activity ac){
        activity=ac;
    }
    public static void out(){
        activity.finish();
    }
}
