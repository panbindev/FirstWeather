package tech.panbin.android.firstweather;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.prefs.PreferenceChangeEvent;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import tech.panbin.android.firstweather.gson.Forecast;
import tech.panbin.android.firstweather.gson.Weather;
import tech.panbin.android.firstweather.util.HttpUtil;
import tech.panbin.android.firstweather.util.Utility;

public class WeatherActivity extends AppCompatActivity {

    private final String weatherApiKey = "ef0300aabb6b491faa97c64305af3ad3";

    private ScrollView weatherLayout;

    private TextView titleCity;

    private TextView titleUpdateTime;

    private TextView degreeText;

    private TextView weatherInfoText;

    private TextView aqiText;

    private TextView pm25Text;

    private TextView comfortText;

    private TextView carWashText;

    private TextView sportText;

    private LinearLayout forecastLayout;

    /*
    *背景图片
    * */
    private ImageView bingPicImg;

    /*
    * 下拉刷新
    * */
    public SwipeRefreshLayout swipeRefreshLayout;
    private String mWeatherId;

    /*
    * 切换城市
    * */
    public DrawerLayout drawerLayout;
    private Button navButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

//        初始化各个控件
        weatherLayout = (ScrollView) findViewById(R.id.weather_layout);
        titleCity = findViewById(R.id.title_city);
        titleUpdateTime = findViewById(R.id.update_time);
        degreeText = findViewById(R.id.degree_text);
        weatherInfoText = findViewById(R.id.weather_info_text);
        aqiText = findViewById(R.id.aqi_text);
        pm25Text = findViewById(R.id.pm25_text);
        comfortText = findViewById(R.id.comfort_text);
        carWashText = findViewById(R.id.car_wash_text);
        sportText = findViewById(R.id.sport_text);
        forecastLayout = (LinearLayout) findViewById(R.id.forecast_layout);

        bingPicImg = findViewById(R.id.bing_pic_img);

        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navButton = findViewById(R.id.nav_button);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = preferences.getString("weather",null);
        if(null != weatherString){
            /*
            * 有缓存时直接解析天气数据
            * */
            Weather weather = Utility.handleWeatherResponse(weatherString);

            mWeatherId = weather.basic.weatherId;/*下拉刷新*/

            showWeatherInfo(weather);
        }
        else{
            /*
            * 无缓存时查服务器
            * */
//            String weatherId = getIntent().getStringExtra("weather_id");
            mWeatherId = getIntent().getStringExtra("weather_id");/*下拉刷新*/
            weatherLayout.setVisibility(View.INVISIBLE);
//            requestWeather(WeatherId);
            requestWeather(mWeatherId);
        }
        /*
        * 下拉刷新
        * */
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestWeather(mWeatherId);
            }
        });

        /*
        * 背景图片
        * */
        String bingPic = preferences.getString("bing_pic",null);
        if(null != bingPic){
            Glide.with(this).load(bingPic).into(bingPicImg);
        }
        else{
            loadBingPic();
        }

        /*
        * 切换城市
        * */
        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

    }

    /*
    * 根据城市id请求城市天气信息
    * */
    public void requestWeather(final String weatherId){

        String weatherUrl = "http://guolin.tech/api/weather?cityid=" + weatherId + "&key=" + weatherApiKey;

        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this,"网络请求失败，无法获取城市"+weatherId+"天气信息",Toast.LENGTH_SHORT).show();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String responseText = response.body().string();
                final Weather weather = Utility.handleWeatherResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(null != weather && "ok".equals(weather.status)){
                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                            editor.putString("weather",responseText);
                            editor.apply();

                            mWeatherId = weather.basic.weatherId;

                            showWeatherInfo(weather);
                        }
                        else{
//                            Toast.makeText(WeatherActivity.this,"获取城市"+weatherId+"天气信息失败",Toast.LENGTH_SHORT).show();

                            /*
                            * Debug
                            * */
                            Toast.makeText(WeatherActivity.this,"数据解析失败！\n返回值：\n"+responseText,Toast.LENGTH_LONG).show();
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });

            }
        });

        loadBingPic();
    }

    /*
    * 处理并战士Weather实体类中的数据
    * */
    private void showWeatherInfo(Weather weather){
        String cityName = weather.basic.cityName;
        String updateTime = weather.basic.update.updateTime;
        String degree = weather.now.nowTemperature + "℃";
        String weatherInfo = weather.now.nowCond.info;

        titleCity.setText(cityName);
        titleUpdateTime.setText(updateTime);
        degreeText.setText(degree);
        weatherInfoText.setText(weatherInfo);
        forecastLayout.removeAllViews();
        for (Forecast forecast: weather.forecastList){

            View view = LayoutInflater.from(this).inflate(R.layout.forecast_item, forecastLayout, false);
            TextView dateText = view.findViewById(R.id.date_text);
            TextView infoText = view.findViewById(R.id.info_text);
            TextView maxText = view.findViewById(R.id.max_text);
            TextView minText = view.findViewById(R.id.min_text);

            dateText.setText(forecast.date);
            infoText.setText(forecast.forecastCond.info);
            maxText.setText(forecast.forecastTmp.forecastMaxTmp);
            minText.setText(forecast.forecastTmp.forecastMinTmp);
            forecastLayout.addView(view);
        }

        if(null != weather.aqi){

            aqiText.setText(weather.aqi.city.aqi);
            pm25Text.setText(weather.aqi.city.pm25);
        }

        String comfort = "舒适度：" + weather.suggestion.comfort.info;
        String carWash = "洗车指数：" + weather.suggestion.carWash.info;
        String sport = "运动建议：" + weather.suggestion.sport.info;

        comfortText.setText(comfort);
        carWashText.setText(carWash);
        sportText.setText(sport);
        weatherLayout.setVisibility(View.VISIBLE);
    }

    /*
    * 加载Bing每日一图
    * */
    private void loadBingPic(){
        String requestBingPic = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(requestBingPic, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String bingPic = response.body().string();
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();

                editor.putString("bing_pic",bingPic);
                editor.apply();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(WeatherActivity.this).load(bingPic).into(bingPicImg);
                    }
                });
            }
        });
    }
}
