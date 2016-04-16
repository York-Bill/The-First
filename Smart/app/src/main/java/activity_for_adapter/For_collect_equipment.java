package activity_for_adapter;

/**
 * Created by Tyhj on 2016/4/5.
 */
public class For_collect_equipment {
    String id;
    String name;
    int headImage;
    String room;
    int ifcollect;
    //0，1，2，6
    public For_collect_equipment(String str1, String str, int str2,int str4,String room){
        name=str;
        id=str1;
        headImage=str2;
        ifcollect=str4;
        this.room=room;
    }
    public String getName() {
        return name;
    }
    public int getHeadImage() {
        return headImage;
    }
    public String getId() {
        return id;
    }
    public int getIfcollect() {
        return ifcollect;
    }

    public String getRoom() {
        return room;
    }
}
