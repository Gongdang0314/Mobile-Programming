    package com.example.team_project;

    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.CheckBox;
    import android.widget.TextView;

    import androidx.annotation.NonNull;
    import androidx.recyclerview.widget.RecyclerView;

    import java.util.ArrayList;

    public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.ViewHolder> {

        private ArrayList<WorkoutItem> list;

        public WorkoutAdapter(ArrayList<WorkoutItem> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_workout, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            WorkoutItem item = list.get(position);

            holder.name.setText(item.getName());

            // 🔥 기존 리스너 제거 (중복 호출 방지)
            holder.cbDone.setOnCheckedChangeListener(null);
            holder.cbNotDone.setOnCheckedChangeListener(null);

            // 🔥 현재 저장된 상태 반영
            holder.cbDone.setChecked(item.isDone());
            holder.cbNotDone.setChecked(item.isNotDone());

            // 🔥 리스너 다시 등록
            holder.cbDone.setOnCheckedChangeListener((btn, checked) -> {
                item.setDone(checked);
                if (checked) {
                    item.setNotDone(false);
                    holder.cbNotDone.setChecked(false);
                }
            });

            holder.cbNotDone.setOnCheckedChangeListener((btn, checked) -> {
                item.setNotDone(checked);
                if (checked) {
                    item.setDone(false);
                    holder.cbDone.setChecked(false);
                }
            });
        }


        @Override
        public int getItemCount() {
            return list.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            TextView name;
            CheckBox cbDone, cbNotDone;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                name = itemView.findViewById(R.id.tvWorkoutName);

                cbDone = itemView.findViewById(R.id.cbSet1);
                cbNotDone = itemView.findViewById(R.id.cbSet2);
            }
        }
    }
