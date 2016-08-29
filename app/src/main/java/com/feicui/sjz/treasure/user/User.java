package com.feicui.sjz.treasure.user;

/**
 * Created by Administrator on 16-7-14.
 */
public class User {
//    "UserName":"qjd",
//            "Password":"654321"
    private String UserName;
    private String Password;

    public String getPassword() {
        return Password;
    }

    public String getUserName() {
        return UserName;
    }

    public User(String userName, String password) {
        UserName = userName;
        Password = password;
    }
}
