package com.example.team_project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CalendarActivity extends AppCompatActivity {

    private TextView tvHeaderDate;
    private GridView gridCalendar;
    private CalendarAdapter calendarAdapter;
    private WorkoutDetailAdapter detailAdapter;

    private WorkoutDAO workoutDAO;
    private List<WorkoutEntry> allWorkoutEntries;
    private Calendar currentMonthCalendar;
    private Calendar startDate;
    private Calendar selectedDate;

    private static final SimpleDateFormat HEADER_FORMAT = new SimpleDateFormat("yyyy년 M월", Locale.KOREA);
    private static final SimpleDateFormat DETAIL_FORMAT = new SimpleDateFormat("M월 d일 E요일", Locale.KOREA);
    private static final String PREFS_NAME = "WorkoutPrefs";
    private static final String START_DATE_KEY = "StartDate";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        initData();
        initUI();
        setupListeners();

        selectedDate = Calendar.getInstance();
        DateUtils.resetTime(selectedDate);
        showWorkoutDetails(selectedDate);

        calendarAdapter.setSelectedDate(selectedDate);

        calendarAdapter.notifyDataSetChanged();
    }

    private void initData() {
        workoutDAO = new WorkoutDAO(this);
        allWorkoutEntries = workoutDAO.getAllWorkoutEntries();

        currentMonthCalendar = Calendar.getInstance();
        currentMonthCalendar.set(Calendar.DATE, 1);
        DateUtils.resetTime(currentMonthCalendar);

        loadStartDate();
    }

    private void initUI() {
        tvHeaderDate = findViewById(R.id.tv_header_date);
        gridCalendar = findViewById(R.id.grid_calendar);
        ListView listWorkoutDetail = findViewById(R.id.list_workout_detail);

        calendarAdapter = new CalendarAdapter(this, this::getWorkoutForDate, currentMonthCalendar);
        gridCalendar.setAdapter(calendarAdapter);

        detailAdapter = new WorkoutDetailAdapter(this);
        listWorkoutDetail.setAdapter(detailAdapter);

        updateCalendarHeader();
    }

    private void setupListeners() {
        ImageButton btnPrev = findViewById(R.id.btn_prev);
        ImageButton btnNext = findViewById(R.id.btn_next);
        Button btnStart = findViewById(R.id.btn_start);
        Button btnHome = findViewById(R.id.btn_home);

        btnPrev.setOnClickListener(v -> handleMonthChange(-1));
        btnNext.setOnClickListener(v -> handleMonthChange(1));

        gridCalendar.setOnItemClickListener((parent, view, position, id) -> {
            Integer day = (Integer) calendarAdapter.getItem(position);

            if (day != 0) {
                Calendar clickedCal = (Calendar) currentMonthCalendar.clone();
                clickedCal.set(Calendar.DATE, day);
                DateUtils.resetTime(clickedCal);

                selectedDate = clickedCal;
                showWorkoutDetails(clickedCal);
                calendarAdapter.setSelectedDate(selectedDate);
                calendarAdapter.notifyDataSetChanged();
            }
        });

        btnStart.setOnClickListener(v -> {
            if (selectedDate == null) {
                Toast.makeText(this, "날짜를 선택해주세요", Toast.LENGTH_SHORT).show();
                return;
            }

            if (detailAdapter.getCount() > 0) {

                long diffDays = DateUtils.getDaysBetween(startDate, selectedDate);
                int dayIndex = (int) diffDays;

                String realDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        .format(selectedDate.getTime());

                Intent intent = new Intent(CalendarActivity.this, Checklist.class);
                intent.putExtra("dayIndex", dayIndex);
                intent.putExtra("realDate", realDate);

                startActivity(intent);

            } else {
                Toast.makeText(this, "운동이 없는 날입니다.", Toast.LENGTH_SHORT).show();
            }
        });
        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(CalendarActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

    }

    private void handleMonthChange(int monthOffset) {
        currentMonthCalendar.add(Calendar.MONTH, monthOffset);
        updateCalendarHeader();

        selectedDate = null;
        showWorkoutDetails(selectedDate);

        calendarAdapter.setCurrentMonthCalendar(currentMonthCalendar);
        calendarAdapter.setSelectedDate(selectedDate);
        calendarAdapter.notifyDataSetChanged();
    }

    private void loadStartDate() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        long startDateMillis = settings.getLong(START_DATE_KEY, 0);

        startDate = Calendar.getInstance();
        if (startDateMillis != 0) {
            startDate.setTimeInMillis(startDateMillis);
        }
        DateUtils.resetTime(startDate);
    }

    private void updateCalendarHeader() {
        tvHeaderDate.setText(HEADER_FORMAT.format(currentMonthCalendar.getTime()));
    }

    private void showWorkoutDetails(Calendar targetDate) {
        TextView tvDetailDate = findViewById(R.id.tv_detail_date);

        if (targetDate == null) {
            tvDetailDate.setText("날짜를 선택해주세요");
            detailAdapter.setWorkouts(new ArrayList<>());
        } else {
            tvDetailDate.setText(DETAIL_FORMAT.format(targetDate.getTime()));

            List<WorkoutEntry> todaysWorkout = getWorkoutForDate(targetDate);
            if (todaysWorkout != null) {
                detailAdapter.setWorkouts(todaysWorkout);
            } else {
                detailAdapter.setWorkouts(new ArrayList<>());
            }
        }
        detailAdapter.notifyDataSetChanged();
    }

    public List<WorkoutEntry> getWorkoutForDate(Calendar targetDate) {
        if (startDate == null) return null;

        long diffDays = DateUtils.getDaysBetween(startDate, targetDate);

        if (diffDays >= 0 && diffDays < 28) {
            return findWorkoutByDayIndex((int) diffDays);
        }
        return null;
    }

    private List<WorkoutEntry> findWorkoutByDayIndex(int dayIndex) {
        List<WorkoutEntry> result = new ArrayList<>();
        int currentDayCount = -1;
        String lastDayName = "";

        for (WorkoutEntry entry : allWorkoutEntries) {
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
}
