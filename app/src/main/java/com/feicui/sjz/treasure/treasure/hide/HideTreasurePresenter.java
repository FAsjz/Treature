package com.feicui.sjz.treasure.treasure.hide;

import com.feicui.sjz.treasure.net.NetClient;
import com.feicui.sjz.treasure.treasure.TreasureApi;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 16-7-21.
 */
public class HideTreasurePresenter extends MvpNullObjectBasePresenter<HideTreasureView> {
    private Call<HideTreasureResult> call;

    public void hideTreasure(HideTreasure hideTreasure){
        getView().showProgress();
        TreasureApi treasureApi = NetClient.getInstance().getTreasureApi();
        if (call != null) call.cancel();
        call = treasureApi.hideTreasure(hideTreasure);
        call.enqueue(callback);

    }
    private Callback<HideTreasureResult> callback = new Callback<HideTreasureResult>() {
        @Override
        public void onResponse(Call<HideTreasureResult> call, Response<HideTreasureResult> response) {
            getView().hideProgress();
            if (response.isSuccessful() && response != null) {
                HideTreasureResult result = response.body();
                if (result == null){
                    getView().showMessage("unkonwn error");
                    return;
                }
                getView().showMessage(result.getMsg());
                if (result.getCode() == 1) {
                    getView().navigateToHome();
                }

            }
        }

        @Override
        public void onFailure(Call<HideTreasureResult> call, Throwable t) {
            getView().hideProgress();
            getView().showMessage(t.getMessage());

        }
    };
    @Override
    public void detachView(boolean retainInstance) {
        if (call != null) call.cancel();
        super.detachView(retainInstance);
    }
}
