package tech.panbin.android.firstweather.gson;

/**
 * Created by PanBin on 2018/01/13.
 */

public class AQI {

    public AQICity city;

    public class AQICity{

        /*
        * 空气质量指数
        * */
        public String aqi;

        /*
        * PM2.5
        * */
        public String pm25;
    }
}
