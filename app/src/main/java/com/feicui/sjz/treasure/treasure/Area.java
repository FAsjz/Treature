package com.feicui.sjz.treasure.treasure;

import com.google.gson.annotations.SerializedName;

/**
 * 获取宝藏时将使用到的RequestBody
 * 作者：yuanchao on 2016/7/19 0019 10:36
 * 邮箱：yuanchao@feicuiedu.com
 */
public class Area {
    //    {
//        "PagerSize":3,
//            "currentPage":1,
//            "XlineMin":39.000991,
//            "XlineMax":41.000991,
//            "YlineMin":39.000991,
//            "YlineMax":41.000991
//    }
    @SerializedName("currentPage")
    private int currentPage = 1;

    @SerializedName("PagerSize")
    private int pagerSize = 100;

    @SerializedName("XlineMin")
    private double minLng;

    @SerializedName("XlineMax")
    private double maxLng;

    @SerializedName("YlineMin")
    private double minLat;

    @SerializedName("YlineMax")
    private double maxLat;

    public void setMinLng(double minLng) {
        this.minLng = minLng;
    }

    public void setMaxLng(double maxLng) {
        this.maxLng = maxLng;
    }

    public void setMinLat(double minLat) {
        this.minLat = minLat;
    }

    public void setMaxLat(double maxLat) {
        this.maxLat = maxLat;
    }

    @Override public int hashCode() {
        return (int)maxLat;
    }

    @Override public boolean equals(Object o) {
        if(!(o instanceof Area))return false;
        if(o == this)return true;
        Area other = (Area)o;
        return (int)maxLat == (int)other.maxLat && (int)maxLng == (int)other.maxLng;
    }
}
