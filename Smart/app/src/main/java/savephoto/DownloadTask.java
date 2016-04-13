package savephoto;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;

import java.util.List;

/**
 * Created by Tyhj on 2016/3/12.
 */
public class DownloadTask extends AsyncTask<String,Integer,Boolean> {
    Context mycontext=null;
    String id;
    public DownloadTask(Context context,String id) {
        mycontext=context;
        this.id=id;
    }
    @Override
    protected Boolean doInBackground(String... params) {

        return true;
    }
}
