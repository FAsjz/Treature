package com.feicui.sjz.treasure.treasure.detail;

import com.feicui.sjz.treasure.net.NetClient;
import com.feicui.sjz.treasure.treasure.TreasureApi;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 16-7-22.
 */
public class DetailTreasurePresenter extends MvpNullObjectBasePresenter<DetailTreasureView> {

    private Call<List<DetailTreasureResult>> call;
    public void getDetailTreasure(DetailTreasure detailTreasure){
        TreasureApi treasureApi = NetClient.getInstance().getTreasureApi();
        if (call != null) call.cancel();
        call = treasureApi.getDetailTreasure(detailTreasure);
        call.enqueue(callBack);

    }
    private final Callback<List<DetailTreasureResult>> callBack = new Callback<List<DetailTreasureResult>>() {
        @Override
        public void onResponse(Call<List<DetailTreasureResult>> call, Response<List<DetailTreasureResult>> response) {
            if (response != null && response.isSuccessful()) {
                List<DetailTreasureResult> results = response.body();
                if (results == null){
                    getView().showMessage("unkonwn error");
                    return;
                }
                getView().setData(results);
            }
        }

        @Override
        public void onFailure(Call<List<DetailTreasureResult>> call, Throwable t) {
            getView().showMessage(t.getMessage());
        }
    };
    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (call != null) call.cancel();
    }
}
