package tech.panbin.android.firstweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by PanBin on 2018/01/13.
 */

public class Weather {

    @SerializedName("status")
    public String status;

    @SerializedName("basic")
    public Basic basic;

    @SerializedName("aqi")
    public AQI aqi;

    @SerializedName("daily_forecast")
    public Forecast forecast;

    @SerializedName("now")
    public Now now;

    @SerializedName("suggestion")
    public Suggestion suggestion;
}
