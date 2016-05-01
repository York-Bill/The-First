package Api_sours;

/**
 * Created by Tyhj on 2016/5/1.
 */
import android.content.Context;
import android.content.SharedPreferences;

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

public class Wether {
    Context context;
    String wendu="";
    String fengxiang="";
    String tianqi="";
    String fengji="";
    String high="";
    String low="";
    public  Wether(Context context){
        this.context=context;
        try {
            URL url=new URL("http://wthrcdn.etouch.cn/WeatherApi?city=杭州");
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
            System.out.println(reBuilder.toString());
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
        editor.commit();
    }
}
