package com.feicui.sjz.treasure.treasure.detail;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 16-7-22.
 */
public class DetailTreasure {


    @SerializedName("TreasureID")
    private final int treasureId;

    @SerializedName("PagerSize")
    private final int pageSize;

    @SerializedName("currentPage")
    private final int currentPage;

    public DetailTreasure(int treasureId) {
        this.treasureId = treasureId;
        this.pageSize = 20;
        this.currentPage = 1;
    }


}
