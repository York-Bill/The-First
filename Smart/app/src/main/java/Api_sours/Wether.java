package Api_sours;

/**
 * Created by Tyhj on 2016/5/1.
 */
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;

import com.baidu.location.LocationClient;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import savephoto.GetModelHeadImage;

public class Wether {
    Context context;
    String wendu="";
    String fengxiang="";
    String tianqi="";
    String fengji="";
    String high="";
    String low="";
    Handler handler;
    public  Wether(Context context, Handler handler){
        this.handler=handler;
        this.context=context;
        String str="http://wthrcdn.etouch.cn/WeatherApi?city=";
        while (GetModelHeadImage.getAddress()==null||GetModelHeadImage.getCity()==null){

        }
        str=str+GetModelHeadImage.getCity().substring(0,GetModelHeadImage.getCity().length()-1);
        try {
            URL url=new URL(str);
            HttpURLConnection connection=(HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);
            InputStream in=connection.getInputStream();
            BufferedReader reader=new BufferedReader(new InputStreamReader(in));
            StringBuilder reBuilder=new StringBuilder();
            String line;
            while ((line=reader.readLine())!=null) {
                reBuilder.append(line);
            }
            x(reBuilder.toString());
        } catch (MalformedURLException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        } catch (IOException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }
    public void x(String str){
        try {
            XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser=factory.newPullParser();
            xmlPullParser.setInput(new StringReader(str));
            int eventType=xmlPullParser.getEventType();
            int x=0;
            int y=0;
            while (eventType!=XmlPullParser.END_DOCUMENT){
                String nodename=xmlPullParser.getName();
                switch (eventType){
                    case XmlPullParser.START_TAG:
                        if("fengli".equals(nodename)&&y==0){
                            fengji=xmlPullParser.nextText();
                            y++;
                        }else if("fengxiang".equals(nodename)&&x==0){
                            fengxiang=xmlPullParser.nextText();
                            x++;
                        }else if("wendu".equals(nodename)){
                            wendu=xmlPullParser.nextText();
                        }else if("type".equals(nodename)){
                            tianqi=xmlPullParser.nextText();
                        }else if("high".equals(nodename)){
                            high=xmlPullParser.nextText();
                            high=high.substring(3,high.length());
                        }else if("low".equals(nodename)){
                            low=xmlPullParser.nextText();
                            low=low.substring(3,low.length());
                        }

                }
                if(nodename!=null&&nodename.equals("night"))
                    break;
                eventType=xmlPullParser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SharedPreferences.Editor editor=context.getSharedPreferences("wether",Context.MODE_PRIVATE).edit();
        editor.putString("wendu",wendu);
        editor.putString("fengxiang",fengxiang);
        editor.putString("tianqi",tianqi);
        editor.putString("fengji",fengji);
        editor.putString("hignlow",low+"~"+high);
        editor.putString("location", GetModelHeadImage.getAddress());
        editor.putString("city",GetModelHeadImage.getCity());
        handler.sendEmptyMessage(1);
        editor.commit();
    }
}
