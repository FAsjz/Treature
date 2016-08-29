package com.feicui.sjz.treasure.net;

import com.feicui.sjz.treasure.treasure.TreasureApi;
import com.feicui.sjz.treasure.user.UserApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 16-7-14.
 */
public class NetClient {
    public static final String BASE_URL = "http://admin.syfeicuiedu.com";
    private OkHttpClient okHttpClient;
    private static NetClient netClient;
    private static Retrofit retrofit;
    private Gson gson;
    private UserApi userApi;
    private TreasureApi treasureApi;

    private NetClient() {
        //非严格模式
        gson = new GsonBuilder().setLenient().create();
        okHttpClient = new OkHttpClient.Builder().build();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static NetClient getInstance() {
        if (netClient == null) {
            netClient = new NetClient();
        }
        return netClient;
    }

    public OkHttpClient getClient() {
        return okHttpClient;
    }

    public UserApi getUserApi() {
        if (userApi == null) {
            userApi = retrofit.create(UserApi.class);
        }
        return userApi;
    }
    public TreasureApi getTreasureApi() {
        if (treasureApi == null) {
            treasureApi = retrofit.create(TreasureApi.class);
        }
        return treasureApi;
    }
}
