package com.hiparty.Utils;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by lurunfa on 2016/11/4.
 */

public class OkHttpUtils {
    private  static final OkHttpClient mOkHttpClient = new OkHttpClient();
    static {
        mOkHttpClient.setConnectTimeout(30, TimeUnit.SECONDS);
    }

    /**
     * 不会开启异步线程访问网络
     * @param request
     * @return
     * @throws IOException
     */
    public static Response execute(Request request) throws IOException{
        return mOkHttpClient.newCall(request).execute();
    }

    /**
     * 开启异步线程访问网络
     * @param request
     * @param callback
     */
    public static void enqueue(Request request, Callback callback){
        mOkHttpClient.newCall(request).enqueue(callback);
    }



}
