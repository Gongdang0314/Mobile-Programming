package com.example.team_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button btnCalendar = findViewById(R.id.btnCalendar);

        findViewById(R.id.btnTodayRoutine).setOnClickListener(v->
            {Intent intent = new Intent(MainActivity.this, CreateCalenderData.class);
            startActivity(intent);});
        findViewById(R.id.btnStats).setOnClickListener(v->
        {Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
            startActivity(intent);});
        Button btnSettings = findViewById(R.id.btnSettings);
        btnSettings.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            startActivity(intent);
        });

        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Checklist.class);
                startActivity(intent);
            }
        });
    }
}
