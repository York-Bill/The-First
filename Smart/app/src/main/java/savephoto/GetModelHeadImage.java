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
        R.drawable.ic_avatar
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
            R.mipmap.ic_headimage_18,
            R.mipmap.ic_headimage_19,
            R.mipmap.ic_headimage_20,
            R.mipmap.ic_headimage_21,
            R.mipmap.ic_headimage_22,
            R.mipmap.ic_headimage_23,
            R.mipmap.ic_headimage_24,
            R.mipmap.ic_headimage_25,
            R.mipmap.ic_headimage_26,
            R.mipmap.ic_headimage_27,
            R.mipmap.ic_headimage_28,
            R.mipmap.ic_headimage_29,
            R.mipmap.ic_headimage_30,
            R.mipmap.ic_headimage_31,
            R.mipmap.ic_headimage_32,
            R.mipmap.ic_headimage_33,
            R.mipmap.ic_headimage_34,
            R.mipmap.ic_headimage_35,
            R.mipmap.ic_headimage_36,
            R.mipmap.ic_headimage_37,
            R.mipmap.ic_headimage_38,
            R.mipmap.ic_headimage_39,
            R.mipmap.ic_headimage_40,
            R.mipmap.ic_headimage_41,
            R.mipmap.ic_headimage_42,
            R.mipmap.ic_headimage_43,
            R.mipmap.ic_headimage_44,
            R.mipmap.ic_headimage_45,
            R.mipmap.ic_headimage_46,
            R.mipmap.ic_headimage_47,
            R.mipmap.ic_headimage_48,
            R.mipmap.ic_headimage_49,
            R.mipmap.ic_headimage_50,
            R.mipmap.ic_headimage_51,
            R.mipmap.ic_headimage_52,
            R.mipmap.ic_headimage_53,
            R.mipmap.ic_headimage_54,
    };
}
