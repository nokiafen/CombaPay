package com.comba.android.combapay.common;

import android.content.Context;
import android.util.Log;

import com.comba.android.combapay.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by chenhailin on 2017/6/20.
 */

public class RetrofitWrapper {
    private static RetrofitWrapper instance;
    private Context mContext;
    private Retrofit retrofit;

    private static final int DEFAULT_TIME_OUT = 10;//超时时间 10s
    private static final int DEFAULT_READ_TIME_OUT = 10;

    private RetrofitWrapper() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(
                new HttpLoggingInterceptor.Logger() {
            @Override public void log(String message) { if (BuildConfig.DEBUG) Log.d("Http", message+""); } }); loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);//连接超时时间
        builder.writeTimeout(DEFAULT_READ_TIME_OUT,TimeUnit.SECONDS);//写操作 超时时间
        builder.readTimeout(DEFAULT_READ_TIME_OUT,TimeUnit.SECONDS);//读操作超时时间
        builder.addInterceptor(loggingInterceptor);
        retrofit = new Retrofit.Builder().client(builder.build()).baseUrl(Constant.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .
                addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create()).build();

    }

    public static RetrofitWrapper getInstance() {
        if (instance == null) {
            synchronized (RetrofitWrapper.class) {
                instance = new RetrofitWrapper();
            }
        }
        return instance;
    }

    public <T> T create(Class<T> service) {
        return retrofit.create(service);
    }

    public class Constant {
        public static final String BASE_URL = "http://172.16.228.54:8080/platform-web/";
    }
}
