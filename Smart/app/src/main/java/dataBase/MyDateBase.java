package dataBase;

import android.content.Context;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Tyhj on 2016/4/4.
 */
public class MyDateBase extends SQLiteOpenHelper{
    public Context context;
    public String CREAT_TABLE_MODEL="create table Model ("
            +"id text primary key ,"
            +"headImage integer)";
    public  String CREAT_TABLE_ROOM="create table Room(" +
            "id text primary key," +
            "headImage integer," +
            "ifCollect integer)";
    public String CREAT_TABLE_EQUIPMENT="create table Equipment(" +
            "id text primary key," +
            "name text," +
            "headImage integer," +
            "type text,"+
            "belong text,"+
            "ifCollect integer)";
    public String CREAT_TABLE_USER="create table User(" +
            "id text primary key," +
            "name text," +
            "phoneNumber text," +
            "password text," +
            "signTime text," +
            "isMaster boolean," +
            "email text," +
            "headImage integer)";
    public MyDateBase(Context context, String name, SQLiteDatabase.CursorFactory factory,int version) {
        super(context, name, factory, version);
        this.context=context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREAT_TABLE_MODEL);
        db.execSQL(CREAT_TABLE_EQUIPMENT);
        db.execSQL(CREAT_TABLE_ROOM);
        db.execSQL(CREAT_TABLE_USER);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
