package com.example.team_project;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private final List<WorkoutHistory> list;

    public HistoryAdapter(List<WorkoutHistory> list) {
        this.list = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvMemo, tvDate, tvRate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvMemo = itemView.findViewById(R.id.tvMemo);
            tvRate = itemView.findViewById(R.id.tvRate);
        }
    }

    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder holder, int position) {

        WorkoutHistory item = list.get(position);

        holder.tvDate.setText(item.date);
        holder.tvMemo.setText("메모: " + item.memo);
        holder.tvRate.setText("달성률: " + item.achievement + "%");

        // 리스트 클릭하면 상세 화면으로 이동
        holder.itemView.setOnClickListener(v -> {
            Intent it = new Intent(v.getContext(), HistoryDetailActivity.class);
            it.putExtra("date", item.date);
            v.getContext().startActivity(it);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
