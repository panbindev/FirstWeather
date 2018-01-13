package tech.panbin.android.firstweather.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by PanBin on 2018/01/13.
 */

public class HttpUtil {

    public static void sendOkHttpRequest(String address, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
}
