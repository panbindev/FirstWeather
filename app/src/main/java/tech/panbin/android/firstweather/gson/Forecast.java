package tech.panbin.android.firstweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by PanBin on 2018/01/13.
 */

public class Forecast {

    @SerializedName("date")
    public String date;

    @SerializedName("cond")
    public ForecastCond forecastCond;

    public class ForecastCond{

        @SerializedName("txt_d")
        public String info;
    }

    @SerializedName("tmp")
    public ForecastTmp forecastTmp;

    public class ForecastTmp{

        @SerializedName("max")
        public String forecastMaxTmp;

        @SerializedName("min")
        public String forecastMinTmp;

    }
}
