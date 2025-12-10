package com.example.team_project;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.ColorInt;

public class CalendarDayItemView extends LinearLayout {

    private TextView tvDate;
    private TextView tvRoutineDay;

    public CalendarDayItemView(Context context) {
        super(context);
        init(context);
    }

    public CalendarDayItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.item_calendar_day, this, true);
        tvDate = findViewById(R.id.tv_date);
        tvRoutineDay = findViewById(R.id.tv_routine_day);
        this.setOrientation(VERTICAL);
        this.setGravity(Gravity.CENTER);
        int padding = dpToPx(context, 4);
        this.setPadding(padding, padding, padding, padding);
    }

    public void setDay(int day, String routineText, String type, boolean isToday, boolean isSelected) {
        if (day == 0) {
            tvDate.setText("");
            tvRoutineDay.setText("");
            this.setBackgroundColor(Color.TRANSPARENT);
            this.setEnabled(false);
            return;
        }

        this.setEnabled(true);
        tvDate.setText(String.valueOf(day));
        tvRoutineDay.setText(routineText);

        @ColorInt int bgColor = 0xFFF8E1;
        @ColorInt int routineColor = Color.GRAY;
        int dateTextStyle = Typeface.NORMAL;

        if (type.equals("WORKOUT")) {
            bgColor = 0xFFE8F5E9;
            routineColor = 0xFF4CAF50;
        } else if (type.equals("REST")) {
            bgColor = 0xFFF5F5F5;
            routineColor = 0xFF9E9E9E;
        } else {
            tvRoutineDay.setText("");
        }

        if (isSelected) {
            bgColor = 0xFFFFE0B2;
            routineColor = Color.BLACK;
        }

        if (isToday) {
            tvDate.setTextColor(Color.BLUE);
            dateTextStyle = Typeface.BOLD;
        } else {
            tvDate.setTextColor(Color.BLACK);
        }

        this.setBackgroundColor(bgColor);
        tvRoutineDay.setTextColor(routineColor);
        tvDate.setTypeface(null, dateTextStyle);
    }

    private int dpToPx(Context context, int dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }
}
