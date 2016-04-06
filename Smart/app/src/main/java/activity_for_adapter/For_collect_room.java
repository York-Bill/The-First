package activity_for_adapter;

/**
 * Created by Tyhj on 2016/4/3.
 */
public class For_collect_room {
    String name;
    int headimage;
    int ifCollect;
    public For_collect_room(String str,int str1,int str2){
        this.name=str;
        headimage=str1;
        ifCollect=str2;
    }
    public String getName() {
        return name;
    }
    public int getHeadimage() {
        return headimage;
    }

    public int getIfCollect() {
        return ifCollect;
    }
}
