package com.android.byc.myhousecoins.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.byc.myhousecoins.R;
import com.android.byc.myhousecoins.db.UserModelEntity;
import com.android.byc.myhousecoins.utility.ListUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yu
 * @version 1.0
 * @date 2019/2/21 9:06
 * @description
 */
public class RankingListAdapter extends RecyclerView.Adapter<RankingListAdapter.ViewHolder> {

    private List<UserModelEntity> userModels = new ArrayList<>();
    private Context context;

    private int[] drawables = new int[] {
            R.mipmap.ic_gold_medal,
            R.mipmap.ic_silver_medal,
            R.mipmap.ic_copper_medal
    };

    public RankingListAdapter() {
    }

    public RankingListAdapter(List<UserModelEntity> userModels) {
        this.userModels = ListUtils.filterThenSort(userModels);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_ranking_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        UserModelEntity entity = userModels.get(i);

        if (i < 3) {
            viewHolder.tvRanking.setVisibility(View.GONE);
            viewHolder.ivRanking.setVisibility(View.VISIBLE);
            viewHolder.ivRanking.setImageResource(drawables[i]);
        } else {
            viewHolder.tvRanking.setVisibility(View.VISIBLE);
            viewHolder.tvRanking.setText(String.valueOf(i + 1));
            viewHolder.ivRanking.setVisibility(View.GONE);
        }
        viewHolder.tvName.setText(entity.getUserName());
        viewHolder.tvAmount.setText(String.valueOf(entity.getCurrencyAmount()));
        viewHolder.tvRanking.setTextColor(context.getResources().getColor(R.color.text_black));
        viewHolder.tvName.setTextColor(context.getResources().getColor(R.color.text_black));
        viewHolder.tvAmount.setTextColor(context.getResources().getColor(R.color.text_black));

    }

    public void setUserModels(List<UserModelEntity> userModels) {
        this.userModels = ListUtils.filterThenSort(userModels);
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return userModels == null ? 0 : userModels.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvRanking;
        TextView tvName;
        TextView tvAmount;
        ImageView ivRanking;

        public ViewHolder( View itemView) {
            super(itemView);
            tvRanking = itemView.findViewById(R.id.tv_my_ranking);
            tvName = itemView.findViewById(R.id.tv_my_name);
            tvAmount = itemView.findViewById(R.id.tv_my_coins);
            ivRanking = itemView.findViewById(R.id.iv_my_ranking);
        }
    }
}
