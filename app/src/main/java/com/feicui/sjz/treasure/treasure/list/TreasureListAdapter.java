package com.feicui.sjz.treasure.treasure.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.mapapi.clusterutil.MarkerManager;
import com.feicui.sjz.treasure.components.TreasureView;
import com.feicui.sjz.treasure.treasure.Treasure;
import com.feicui.sjz.treasure.treasure.detail.DetailTreasureActivity;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Administrator on 16-7-25.
 */
public class TreasureListAdapter extends RecyclerView.Adapter<TreasureListAdapter.MyViewHolder>{
    private ArrayList<Treasure> datas = new ArrayList<Treasure>();
    public final void addDatas(Collection<Treasure> list) {
        if (list != null) {
            datas.addAll(list);
            notifyItemRangeChanged(0,datas.size());
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TreasureView treasureView = new TreasureView(parent.getContext());
        return new MyViewHolder(treasureView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Treasure treasure = datas.get(position);
        holder.treasureView.bindTreasure(treasure);
        holder.treasureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailTreasureActivity.open(v.getContext(),treasure);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TreasureView treasureView;
        public MyViewHolder(View itemView) {
            super(itemView);
            this.treasureView = (TreasureView)itemView;
        }
    }
}
