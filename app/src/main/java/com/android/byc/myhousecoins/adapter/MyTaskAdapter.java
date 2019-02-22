package com.android.byc.myhousecoins.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.byc.myhousecoins.R;
import com.android.byc.myhousecoins.db.CurrencyTaskRecordsEntity;
import com.android.byc.myhousecoins.db.CurrencyTasksEntity;
import com.android.byc.myhousecoins.model.Strategy;
import com.android.byc.myhousecoins.model.StrategyMapper;

import java.util.List;

/**
 * @author yu
 * @version 1.0
 * @date 2019/2/20 13:05
 * @description
 */
public class MyTaskAdapter extends RecyclerView.Adapter<MyTaskAdapter.ViewHolder> {

    private Context context;
    private List<CurrencyTasksEntity> currencyTasks;

    public MyTaskAdapter() {
    }

    public MyTaskAdapter(List<CurrencyTasksEntity> currencyTasks) {
        this.currencyTasks = currencyTasks;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.activity_main_item_watch, viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CurrencyTasksEntity currencyTask = currencyTasks.get(position);
        holder.tvPlusScore.setText(String.format("+%s", String.valueOf(currencyTask.getAmount())));
        holder.tvTitle.setText(currencyTask.getTitle());


        List<CurrencyTaskRecordsEntity> taskRecords = currencyTask.getCurrencyTaskRecords();
        String description;
        if (taskRecords != null && taskRecords.size() > 0) {
            description = currencyTask.getDescription()
                    .replace("*", String.valueOf(currencyTask.getCurrencyTaskRecords().get(0).count));
        } else {
            description = currencyTask.getDescription().replace("*", String.valueOf(0));
        }
        holder.tvDescribrion.setText(description);
        final Strategy strategy = StrategyMapper.getStrategy(currencyTask.getId().toString());
        holder.btButton.setText(strategy.getButtonTitle());
        holder.btButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strategy.act(context);
            }
        });
        if (position == getItemCount() - 1) {
            holder.divideLine.setVisibility(View.GONE);
            holder.btButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    strategy.act(context);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return currencyTasks == null ? 0 : currencyTasks.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvPlusScore;
        TextView tvTitle;
        TextView tvDescribrion;
        Button btButton;
        View divideLine;

        public ViewHolder( View itemView) {
            super(itemView);
            tvPlusScore = itemView.findViewById(R.id.plus_score);
            tvTitle = itemView.findViewById(R.id.my_task_title);
            tvDescribrion = itemView.findViewById(R.id.my_task_describtion);
            btButton = itemView.findViewById(R.id.my_task_button);
            divideLine = itemView.findViewById(R.id.divide_line);
        }
    }

    public void setCurrencyTasks(List<CurrencyTasksEntity> currencyTasks) {
        this.currencyTasks = currencyTasks;
        this.notifyDataSetChanged();
    }
}
