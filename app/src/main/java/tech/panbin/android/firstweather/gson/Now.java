package tech.panbin.android.firstweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by PanBin on 2018/01/13.
 */

public class Now {

    @SerializedName("tmp")
    public String nowTemperature;

    @SerializedName("cond")
    public NowCond nowCond;

    public class NowCond{

        @SerializedName("txt")
        public String info;
    }
}
