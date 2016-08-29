package com.feicui.sjz.treasure.treasure;

import com.feicui.sjz.treasure.treasure.detail.DetailTreasure;
import com.feicui.sjz.treasure.treasure.detail.DetailTreasureResult;
import com.feicui.sjz.treasure.treasure.hide.HideTreasure;
import com.feicui.sjz.treasure.treasure.hide.HideTreasureResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Administrator on 16-7-20.
 */
public interface TreasureApi {

    @POST("/Handler/TreasureHandler.ashx?action=show")
    Call<List<Treasure>> getTreasureInArea(@Body Area area);



    @POST("/Handler/TreasureHandler.ashx?action=hide")
    Call<HideTreasureResult> hideTreasure(@Body HideTreasure body);

    @POST("/Handler/TreasureHandler.ashx?action=tdetails")
    Call<List<DetailTreasureResult>> getDetailTreasure(@Body DetailTreasure body);
}
