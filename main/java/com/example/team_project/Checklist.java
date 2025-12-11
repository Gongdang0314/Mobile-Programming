package com.example.team_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Checklist extends AppCompatActivity {

    private RecyclerView rvWorkoutList;
    private EditText etMemo;
    private Button btnSave;
    private Button btnGoHome;

    private WorkoutDAO workoutDAO;
    private WorkoutHistoryDAO historyDAO;

    private ArrayList<WorkoutItem> workoutList;
    private WorkoutAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checklist);

        rvWorkoutList = findViewById(R.id.rvWorkoutList);
        etMemo = findViewById(R.id.etMemo);
        btnSave = findViewById(R.id.btnSave);
        btnGoHome = findViewById(R.id.btnGoHome);

        workoutDAO = new WorkoutDAO(this);
        historyDAO = new WorkoutHistoryDAO(this);

        int dayIndex = getIntent().getIntExtra("dayIndex", -1);
        String realDate = getIntent().getStringExtra("realDate");

        if (dayIndex == -1) {
            Toast.makeText(this, "운동 데이터를 불러올 수 없습니다.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        List<WorkoutEntry> allEntries = workoutDAO.getAllWorkoutEntries();
        List<WorkoutEntry> todayWorkouts = findWorkoutByDayIndex(allEntries, dayIndex);

        workoutList = new ArrayList<>();

        if (todayWorkouts.isEmpty()) {
            Toast.makeText(this, "오늘 운동 계획이 없습니다.", Toast.LENGTH_LONG).show();
        } else {
            for (WorkoutEntry entry : todayWorkouts) {
                String itemText = entry.getExerciseName() + " - " + entry.getSetDetail();
                workoutList.add(new WorkoutItem(itemText));
            }
        }

        adapter = new WorkoutAdapter(workoutList);
        rvWorkoutList.setLayoutManager(new LinearLayoutManager(this));
        rvWorkoutList.setAdapter(adapter);

        btnSave.setOnClickListener(v -> saveWorkoutHistory(dayIndex));

        btnGoHome.setOnClickListener(v -> {
            Intent intent = new Intent(Checklist.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private List<WorkoutEntry> findWorkoutByDayIndex(List<WorkoutEntry> allEntries, int dayIndex) {
        List<WorkoutEntry> result = new ArrayList<>();
        int currentDayCount = -1;
        String lastDayName = "";

        for (WorkoutEntry entry : allEntries) {
            if (!entry.getDayName().equals(lastDayName)) {
                currentDayCount++;
                lastDayName = entry.getDayName();
            }
            if (currentDayCount == dayIndex) {
                result.add(entry);
            }
        }
        return result;
    }

    private void saveWorkoutHistory(int dayIndex) {
        String memo = etMemo.getText().toString();

        String realDate = getIntent().getStringExtra("realDate");
        if (realDate == null) realDate = "unknown";

        String dateKey = realDate;   // ← 핵심!!!

        historyDAO.deleteByDate(dateKey);

        for (WorkoutItem item : workoutList) {
            historyDAO.insert(dateKey, item, memo);
        }

        Toast.makeText(this, "운동 기록이 저장되었습니다.", Toast.LENGTH_LONG).show();
    }

}
