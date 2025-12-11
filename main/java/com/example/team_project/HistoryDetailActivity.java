package com.example.team_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HistoryDetailActivity extends AppCompatActivity {

    TextView tvDate, tvMemo, tvRate, tvDetail;
    ProgressBar progressRate;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_detail);

        tvDate = findViewById(R.id.tvDate);
        tvMemo = findViewById(R.id.tvMemo);
        tvRate = findViewById(R.id.tvRate);
        tvDetail = findViewById(R.id.tvDetail);
        progressRate = findViewById(R.id.progressRate);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setText("홈으로 가기");
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(HistoryDetailActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        String date = getIntent().getStringExtra("date");

        WorkoutHistoryDAO dao = new WorkoutHistoryDAO(this);
        WorkoutHistory item = dao.getByDate(date);

        tvDate.setText(item.date);
        tvMemo.setText("메모: " + item.memo);

        progressRate.setProgress(item.achievement);
        tvRate.setText(item.achievement + "%");

        StringBuilder sb = new StringBuilder();
        for (WorkoutItem ex : item.exercises) {
            sb.append(ex.getName())
                    .append(" - ").append(ex.getSetDetail())
                    .append(ex.isDone() ? " → 완료" : " → 미완료")
                    .append("\n");
        }
        tvDetail.setText(sb.toString());
    }
}
