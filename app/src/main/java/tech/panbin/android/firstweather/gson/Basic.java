package tech.panbin.android.firstweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by PanBin on 2018/01/13.
 */

public class Basic {

    @SerializedName("city")
    public String cityName;

    @SerializedName("id")
    public String weatherId;

    @SerializedName("update")
    public Update update;

    public class Update{

        @SerializedName("loc")
        public String updateTime;
    }
}
