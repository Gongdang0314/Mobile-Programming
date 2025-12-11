package com.example.team_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class WorkoutDetailAdapter extends BaseAdapter {
    private List<WorkoutEntry> workouts = new ArrayList<>();
    private final Context context;

    public WorkoutDetailAdapter(Context context) {
        this.context = context;
    }

    public void setWorkouts(@NonNull List<WorkoutEntry> workouts) {
        this.workouts = workouts;
    }

    @Override public int getCount() { return workouts.size(); }
    @Override public Object getItem(int i) { return workouts.get(i); }
    @Override public long getItemId(int i) { return i; }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_workout_detail, viewGroup, false);
        }

        WorkoutEntry entry = workouts.get(i);

        ((TextView)view.findViewById(R.id.tv_exercise_name)).setText(entry.getExerciseName());
        ((TextView)view.findViewById(R.id.tv_set_detail)).setText(entry.getSetDetail());

        return view;
    }
}
