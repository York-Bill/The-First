package activity_for_adapter;

/**
 * Created by Tyhj on 2016/4/5.
 */
public class For_collect_equipment {
    String id;
    String name;
    int headImage;
    public For_collect_equipment(String str1, String str, int str2){
        name=str;
        id=str1;
        headImage=str2;
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
}
