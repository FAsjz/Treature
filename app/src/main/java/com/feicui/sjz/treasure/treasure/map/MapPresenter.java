package com.feicui.sjz.treasure.treasure.map;

import android.util.Log;

import com.feicui.sjz.treasure.treasure.TreasureRepo;
import com.feicui.sjz.treasure.net.NetClient;
import com.feicui.sjz.treasure.treasure.Area;
import com.feicui.sjz.treasure.treasure.Treasure;
import com.feicui.sjz.treasure.treasure.TreasureApi;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import java.util.List;



import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 16-7-20.
 */
public class MapPresenter extends MvpNullObjectBasePresenter<MapMvpView> {

    private Call<List<Treasure>> call;
    private Area area;

    public void getTreasure(Area area){
        this.area = area;
        // 当前这个区域是否已获取过
        if(TreasureRepo.getInstance().isCached(area)){
            return;
        }
        TreasureApi treasureApi = NetClient.getInstance().getTreasureApi();
        if(call !=null)call.cancel();
        call = treasureApi.getTreasureInArea(area);
        call.enqueue(callback);


    }
    private Callback<List<Treasure>> callback = new Callback<List<Treasure>>() {
        @Override
        public void onResponse(Call<List<Treasure>> call, Response<List<Treasure>> response) {
            if (response != null && response.isSuccessful()) {
                List<Treasure> result = response.body();
                Log.e("MapPresenter",result.toString()+"11111");
                if (result == null) {
                    getView().showMessage("unkonwn error");
                    return;
                }
                //添加缓存
                TreasureRepo.getInstance().addTreasure(result);
                TreasureRepo.getInstance().cache(area);
                getView().setData(result);

            }
        }

        @Override
        public void onFailure(Call<List<Treasure>> call, Throwable t) {
            Log.e("MapPresenter","MapPresenter报错");
            getView().showMessage(t.getMessage());
        }
    };
    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (call != null) {
            call.cancel();
        }
    }
}
