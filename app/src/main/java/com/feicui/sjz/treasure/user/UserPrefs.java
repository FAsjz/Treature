package com.feicui.sjz.treasure.user;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/7/17.
 */
public class UserPrefs {
    private SharedPreferences sharedPreferences;
    private static UserPrefs userPrefs;
    public static final String USER_INFO = "user_info";
    public static final String KEY_TOKEN = "key_token";
    public static final String KEY_PHOTO = "key_photo";

    public static void init(Context context){
        userPrefs = new UserPrefs(context);
    }
    private UserPrefs(Context context){
        sharedPreferences = context.getApplicationContext().getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);

    }
    public static UserPrefs getInstance(){
        return userPrefs;
    }

    public void setToken(int token){
        sharedPreferences.edit().putInt(KEY_TOKEN,token).apply();
    }
    public void setPhoto(String photoUrl){
        sharedPreferences.edit().putString(KEY_PHOTO, photoUrl).apply();
    }
    public int getToken(){
        return sharedPreferences.getInt(KEY_TOKEN,-1);
    }
    public String getPhoto(){
        return sharedPreferences.getString(KEY_PHOTO,"");
    }
}
