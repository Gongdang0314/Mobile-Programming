package com.example.team_project;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    RecyclerView rvHistory;
    Button btnGoMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        rvHistory = findViewById(R.id.rvHistory);
        btnGoMain = findViewById(R.id.btnGoMain);

        btnGoMain.setOnClickListener(v -> finish());


        rvHistory.setLayoutManager(new LinearLayoutManager(this));
        rvHistory.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        );

        WorkoutHistoryDAO dao = new WorkoutHistoryDAO(this);
        ArrayList<WorkoutHistory> list = dao.getAll();

        rvHistory.setAdapter(new HistoryAdapter(list));
    }
}
