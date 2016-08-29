package com.feicui.sjz.treasure.treasure.hide;

import com.google.gson.annotations.SerializedName;

/**
 * 埋藏宝藏响应数据
 * 作者：yuanchao on 2016/7/20 0020 17:25
 * 邮箱：yuanchao@feicuiedu.com
 */
public class HideTreasureResult {

//    {
//        "errcode":0,                                    //返回值
//            "errmsg":"参数格式不正确!请检测传入参数格式"   //返回信息
//    }

    @SerializedName("errcode")
    private int code;

    @SerializedName("errmsg")
    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
