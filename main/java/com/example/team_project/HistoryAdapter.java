package com.example.team_project;

import android.util.Log;
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

        TextView tvMemo, tvDate, tvExerciseList, tvComplete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvExerciseList = itemView.findViewById(R.id.tvExerciseList);
            tvComplete = itemView.findViewById(R.id.tvComplete);
            tvMemo = itemView.findViewById(R.id.tvMemo);
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

        StringBuilder sb = new StringBuilder();

        int totalCount = 0;
        int completedCount = 0;

        for (WorkoutItem ex : item.exercises) {
            Log.d("DEBUG_HISTORY", ex.getName() + " / detail=" + ex.getSetDetail() + " / done=" + ex.isDone());

            // 운동 한 줄 구성
            sb.append(ex.getName());

            if (ex.getSetDetail() != null && !ex.getSetDetail().isEmpty()) {
                sb.append(" - ").append(ex.getSetDetail());
            }

            if (ex.isDone()) {
                sb.append("  → 완료\n");
                completedCount++;
            } else {
                sb.append("  → 미완료\n");
            }

            totalCount++;
        }

        holder.tvExerciseList.setText(sb.toString().trim());

        // 달성률 계산
        int rate = totalCount == 0 ? 0 : (int)((completedCount * 100.0) / totalCount);
        item.achievement = rate;

        holder.tvMemo.setText("메모: " + item.memo);

        holder.tvComplete.setText("달성률: " + rate + "%");
        holder.tvComplete.setTextColor(rate >= 70 ? 0xFF4CAF50 : 0xFFD32F2F);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
