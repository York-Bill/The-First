package activity_for_adapter;

/**
 * Created by Tyhj on 2016/4/5.
 */
public class For_collect_equipment {
    String id;
    String name;
    int headImage;
    String type;
    int ifcollect;
    public For_collect_equipment(String str1, String str, int str2,int str4){
        name=str;
        id=str1;
        headImage=str2;
        ifcollect=str4;
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
}
