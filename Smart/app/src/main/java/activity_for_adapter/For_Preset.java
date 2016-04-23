package activity_for_adapter;

/**
 * Created by Tyhj on 2016/4/20.
 */
public class For_Preset {
    String timefrom;
    String presetId;
    boolean myswitch;
    String name;
    int headImage;
    String days;
    String where;
    public For_Preset(String str1,String str2,boolean str4,int str6,String str7,String str8){
        presetId=str1;
        timefrom=str2;
        myswitch=str4;
        headImage=str6;
        days=str7;
        where=str8;
    }

    public String getWhere() {
        return where;
    }

    public String getName() {
        return name;
    }

    public int getHeadImage() {
        return headImage;
    }

    public boolean isMyswitch() {
        return myswitch;
    }

    public String getId() {
        return presetId;
    }

    public String getTimefrom() {
        return timefrom;
    }

    public String getDays() {
        return days;
    }
}
