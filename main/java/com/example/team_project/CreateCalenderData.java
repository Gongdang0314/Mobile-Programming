package com.example.team_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateCalenderData extends AppCompatActivity {
    Button select_program;
    Button generate_canlender;

    private Map<String, EditText> weightFields = new HashMap<>();
    private Map<String, Button> repButtons = new HashMap<>();

    private WorkoutDAO workoutDAO;

    private static class ExerciseUI {
        final String key;
        final String name;
        final String type;
        final int weightId;
        final int repsId;

        ExerciseUI(String key, String name, String type, int weightId, int repsId) {
            this.key = key; this.name = name; this.type = type; this.weightId = weightId; this.repsId = repsId;
        }
    }

    private final ExerciseUI[] exerciseUIs = {
            new ExerciseUI("스쿼트", "스쿼트", "LOWER", R.id.set_squat_weight, R.id.set_squat_maxreps),
            new ExerciseUI("벤치프레스", "벤치프레스", "UPPER", R.id.set_bench_weight, R.id.set_bench_maxreps),
            new ExerciseUI("데드리프트", "데드리프트", "LOWER", R.id.set_dead_weight, R.id.set_dead_maxreps),
            new ExerciseUI("오버헤드프레스", "오버헤드프레스", "UPPER", R.id.set_ohp_weight, R.id.set_ohp_maxreps),
            new ExerciseUI("바벨로우", "바벨로우", "UPPER", R.id.set_bbrow_weight, R.id.set_bbrow_maxreps)
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.input_user_status);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.input_user_status_data), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button btnGoCalendar = findViewById(R.id.btn_go_home);
        btnGoCalendar.setOnClickListener(v -> {
            Intent intent = new android.content.Intent(CreateCalenderData.this, MainActivity.class);
            startActivity(intent);
        });

        workoutDAO = new WorkoutDAO(this);

        select_program = findViewById(R.id.select_program);
        generate_canlender = findViewById(R.id.generate_canlender);

        for (ExerciseUI ui : exerciseUIs) {
            EditText weightField = findViewById(ui.weightId);
            Button repButton = findViewById(ui.repsId);

            weightFields.put(ui.key, weightField);
            repButtons.put(ui.key, repButton);

            repButton.setOnClickListener(this::set_reps);
        }

        select_program.setOnClickListener(this::set_program);

        generate_canlender.setOnClickListener(this::generateAndSaveWorkout);

        if (savedInstanceState != null) {
            select_program.setText(savedInstanceState.getString("selectedProgram"));
            for (ExerciseUI ui : exerciseUIs) {
                repButtons.get(ui.key).setText(savedInstanceState.getString(ui.key + "Reps"));
                weightFields.get(ui.key).setText(savedInstanceState.getString(ui.key + "Weight"));
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("selectedProgram", select_program.getText().toString());
        for (ExerciseUI ui : exerciseUIs) {
            outState.putString(ui.key + "Reps", repButtons.get(ui.key).getText().toString());
            outState.putString(ui.key + "Weight", weightFields.get(ui.key).getText().toString());
        }
    }

    private void set_program(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.getMenuInflater().inflate(R.menu.select_program_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(item -> {
            select_program.setText(item.getTitle());
            return true;
        });
        popup.show();
    }

    private void set_reps(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        Button set_text = (Button)v;
        popup.getMenuInflater().inflate(R.menu.set_maxreps_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(item -> {
            set_text.setText(item.getTitle());
            return true;
        });
        popup.show();
    }

    private void generateAndSaveWorkout(View v) {
        String programText = select_program.getText().toString();
        WorkoutProgram programInstance;

        switch (programText) {
            case "Madcow 5x5 (초보자 추천)":
                programInstance = new Madcow5x5();
                break;
            case "Jim Wendler's 5/3/1":
                programInstance = new Wendler531();
                break;
            default:
                Toast.makeText(this, "프로그램을 선택해주세요.", Toast.LENGTH_SHORT).show();
                return;
        }

        Map<String, Exercise> exercises = new HashMap<>();
        final double MAX_SAFE_WEIGHT = 600.0;

        for (ExerciseUI ui : exerciseUIs) {
            EditText weightField = weightFields.get(ui.key);
            Button repButton = repButtons.get(ui.key);

            String weightStr = weightField.getText().toString();
            String repsStr = repButton.getText().toString().replace("회", "");

            if (weightStr.isEmpty()) {
                Toast.makeText(this, "입력 오류: " + ui.name + " 중량을 입력해주세요.", Toast.LENGTH_LONG).show();
                return;
            }

            if (repsStr.isEmpty()) {
                Toast.makeText(this, "입력 오류: " + ui.name + " 횟수를 선택해주세요.", Toast.LENGTH_LONG).show();
                return;
            }

            double weight = Double.parseDouble(weightStr);
            int reps = Integer.parseInt(repsStr);

            if (weight < 0) {
                Toast.makeText(this, "입력 오류: " + ui.name + " 입력된 중량이 유효하지 않습니다.", Toast.LENGTH_LONG).show();
                return;
            }

            double calculated1RM = WeightCalculator.calculateEstimated1RM(weight, reps);

            if (calculated1RM > MAX_SAFE_WEIGHT) {
                Toast.makeText(this, "입력 오류: " + ui.name + "의 계산된 1RM(" + calculated1RM + "kg)이 너무 높습니다. 중량이나 횟수를 확인해주세요.", Toast.LENGTH_LONG).show();
                return;
            }

            exercises.put(ui.key, new Exercise(ui.name, calculated1RM, ui.type));
        }

        List<WorkoutEntry> workoutEntries = programInstance.generateFull4WeekCalendar(exercises);
        String programType = programInstance.getProgramType();

        boolean saveSuccess = workoutDAO.saveWorkoutPlan(programType, workoutEntries);

        if (saveSuccess) {
            android.content.SharedPreferences prefs = getSharedPreferences("WorkoutPrefs", MODE_PRIVATE);
            prefs.edit().putLong("StartDate", System.currentTimeMillis()).apply();
            Toast.makeText(this, programText + " 4주 계획이 성공적으로 저장되었습니다.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "운동 계획 저장에 실패했습니다.", Toast.LENGTH_LONG).show();
        }
    }
}
