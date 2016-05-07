package savephoto;

import android.app.Activity;

import com.example.tyhj.smart.R;

/**
 * Created by Tyhj on 2016/4/4.
 */
public class GetModelHeadImage {
    private static Activity activity;

    public static void setActivity(Activity activity) {
        GetModelHeadImage.activity = activity;
    }

    public static void finishActivity(){
        GetModelHeadImage.activity.finish();
    }
    private static String address;
    private static String city;

    public static String getAddress() {
        return address;
    }

    public static String getCity() {
        return city;
    }

    public static void setAddress(String address) {
        GetModelHeadImage.address = address;
    }

    public static void setCity(String city) {
        GetModelHeadImage.city = city;
    }

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
                "开关","电灯","冰箱","空调"
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
    public static int[] mosiheah={
            R.mipmap.ic_mosi_0,
            R.mipmap.ic_mosi_1,
            R.mipmap.ic_mosi_2,
            R.mipmap.ic_mosi_3,
            R.mipmap.ic_mosi_4,
            R.mipmap.ic_mosi_5,
            R.mipmap.ic_mosi_6,
            R.mipmap.ic_mosi_7,
            R.mipmap.ic_mosi_8,
            R.mipmap.ic_mosi_9,
            R.mipmap.ic_mosi_10,
            R.mipmap.ic_mosi_11,
            R.mipmap.ic_mosi_12,
            R.mipmap.ic_mosi_13,
            R.mipmap.ic_mosi_14,
            R.mipmap.ic_mosi_15,
            R.mipmap.ic_mosi_16,
            R.mipmap.ic_mosi_17,
            R.mipmap.ic_mosi_18,
            R.mipmap.ic_mosi_19,
            R.mipmap.ic_mosi_20,
            R.mipmap.ic_mosi_21,
            R.mipmap.ic_mosi_22,
            R.mipmap.ic_mosi_23,
            R.mipmap.ic_mosi_24,
            R.mipmap.ic_mosi_25,
            R.mipmap.ic_mosi_26,
            R.mipmap.ic_mosi_27
    };
    public static int[] equipmenthead={
            R.mipmap.ic_equipment_5,
            R.mipmap.ic_equipment_8,
            R.mipmap.ic_equipment_13,
            R.mipmap.ic_equipment_22
    };
    public static int[] roomhead={
            R.mipmap.ic_room_0,
            R.mipmap.ic_room_1,
            R.mipmap.ic_room_2,
            R.mipmap.ic_room_3,
            R.mipmap.ic_room_4,
            R.mipmap.ic_room_5,
            R.mipmap.ic_room_6,
            R.mipmap.ic_room_7,
            R.mipmap.ic_room_8,
            R.mipmap.ic_room_9,
            R.mipmap.ic_room_10,
            R.mipmap.ic_room_11,
            R.mipmap.ic_room_12,
            R.mipmap.ic_room_13,
            R.mipmap.ic_room_14,
            R.mipmap.ic_room_15,
            R.mipmap.ic_room_16,
            R.mipmap.ic_room_17,
            R.mipmap.ic_room_18,
            R.mipmap.ic_room_19,
            R.mipmap.ic_room_20,
    };
}
