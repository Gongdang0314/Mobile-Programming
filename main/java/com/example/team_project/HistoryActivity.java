package com.example.team_project;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    RecyclerView rvHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rvHistory = new RecyclerView(this);
        rvHistory.setLayoutManager(new LinearLayoutManager(this));
        setContentView(rvHistory);

        WorkoutHistoryDAO dao = new WorkoutHistoryDAO(this);
        ArrayList<WorkoutHistory> list = dao.getAll();

        rvHistory.setAdapter(new HistoryAdapter(list));
    }
}
