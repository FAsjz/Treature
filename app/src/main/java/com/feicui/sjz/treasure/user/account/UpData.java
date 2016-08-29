package com.feicui.sjz.treasure.user.account;

/**
 * Created by Administrator on 2016/7/18.
 */
public class UpData {
    //        "Tokenid":3,"
//        "HeadPic": "05a1a7e18ab940679dbd0e506be31add.jpg"
//
    private int Tokenid;
    private String HeadPic;

    public UpData(String headPic, int tokenid) {
        HeadPic = headPic;
        Tokenid = tokenid;
    }
}
