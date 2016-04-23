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
            +"headImage integer," +
            "switch integer)";
    public  String CREAT_TABLE_ROOM="create table Room(" +
            "id text primary key," +
            "headImage integer," +
            "ifCollect integer)";
    public String CREAT_TABLE_EQUIPMENT="create table Equipment(" +
            "id text primary key," +
            "name text," +
            "headImage integer," +
            "type integer,"+
            "room text,"+
            "model text,"+
            "ifCollect integer," +
            "switch int)";
    public String CREAT_TABLE_USER="create table User(" +
            "id text primary key," +
            "name text," +
            "phoneNumber text," +
            "password text," +
            "signTime text," +
            "isMaster integer," +
            "email text," +
            "headImage integer)";
    public String CREAT_TABLE_PRESET="create table Preset(" +
            "id text primary key," +
            "timeFromHour integer," +
            "timeFrimMinit integer," +
            "timeToHour integer,"+
            "timeToMinit integer," +
            "switch integer," +
            "headImage integer," +
            "room text," +
            "ifop integer," +
            "name text)";
    public String CREAT_TABLE_DAYS="create table Days(" +
            "id text," +
            "ifop integer,"+
            "xq1 integer," +
            "xq2 integer," +
            "xq3 integer," +
            "xq4 integer," +
            "xq5 integer," +
            "xq6 integer," +
            "xq7 interger," +
            "name text)";
    public String CREAT_TABLE_INMODEL="create table InModel(" +
            "modelId text," +
            "equipmentId text primary key," +
            "room text," +
            "switch integer)";
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
        db.execSQL(CREAT_TABLE_PRESET);
        db.execSQL(CREAT_TABLE_DAYS);
        db.execSQL(CREAT_TABLE_INMODEL);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
