package savephoto;

import com.example.tyhj.smart.R;

/**
 * Created by Tyhj on 2016/4/4.
 */
public class GetModelHeadImage {
    private static String userId;
    private static boolean ifFirst;
    public static  void setUserId(String userId) {
        GetModelHeadImage.userId = userId;
    }
    public static String getUserId() {
        return userId;
    }
    public static void setIfFirst(boolean ifFirst) {
        GetModelHeadImage.ifFirst = ifFirst;
    }
    public static boolean isIfFirst() {
        return ifFirst;
    }
    public static int[] bgcolor={
                R.color.bg1,
                R.color.bg2,
                R.color.bg3,
                R.color.bg4,
                R.color.bg5,
                R.color.bg6,
                R.color.bg7,
                R.color.bg8,

        };
        public static String[] equipmentName={
                "电灯","冰箱","洗衣机","电视机","台灯","吊灯","饮水机","空调","电风扇","窗帘"
        };
    public static int[] userHeadImage={
            R.mipmap.ic_userheadimage1,
            R.mipmap.ic_userheadimage2,
            R.mipmap.ic_userheadimage3,
            R.mipmap.ic_userheadimage4,
            R.mipmap.ic_userheadimage5,
            R.mipmap.ic_userheadimage6,
            R.mipmap.ic_userheadimage7,
            R.mipmap.ic_userheadimage8,
            R.mipmap.ic_userheadimage9,
    };
    public static int[] modelhead={
            R.mipmap.ic_headimage_1,
            R.mipmap.ic_headimage_2,
            R.mipmap.ic_headimage_3,
            R.mipmap.ic_headimage_4,
            R.mipmap.ic_headimage_5,
            R.mipmap.ic_headimage_6,
            R.mipmap.ic_headimage_7,
            R.mipmap.ic_headimage_8,
            R.mipmap.ic_headimage_9,
            R.mipmap.ic_headimage_10,
            R.mipmap.ic_headimage_11,
            R.mipmap.ic_headimage_12,
            R.mipmap.ic_headimage_13,
            R.mipmap.ic_headimage_14,
            R.mipmap.ic_headimage_15,
            R.mipmap.ic_headimage_16,
            R.mipmap.ic_headimage_17,
    };
}
