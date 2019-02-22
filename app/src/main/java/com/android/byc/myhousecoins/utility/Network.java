package com.android.byc.myhousecoins.utility;


import com.android.byc.myhousecoins.api.CoinsTaskApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author yu
 * @version 1.0
 * @date 2019/2/21 11:08
 * @description
 */
public class Network {
    public CoinsTaskApi coinsTaskApi;
    private static String BASE_URL = "http://stanew.fooww.com/";
    public static Network network;
    public Retrofit retrofit;

    public Network() {
        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                //.addConverterFactory(GsonConverterFactory.create())//设置 Json 转换器
                //.addCallAdapterFactory(RxJavaCallAdapterFactory.create())//RxJava
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //.addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        coinsTaskApi = retrofit.create(CoinsTaskApi.class);

    }
    public static Network getInstance() {
        if (network == null) {
            network = new Network();

        }

        return network;
    }

    public CoinsTaskApi getCoinsTaskApi() {
        return coinsTaskApi;
    }

}
