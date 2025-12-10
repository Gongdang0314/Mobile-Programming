package com.example.team_project;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.function.Function;

public class CalendarAdapter extends BaseAdapter {
    private final Context context;
    private List<Integer> days = new ArrayList<>();

    private Calendar currentMonthCalendar;
    private Calendar selectedDate;

    private final Function<Calendar, List<WorkoutEntry>> getWorkoutForDate;

    public CalendarAdapter(
            Context context,
            Function<Calendar, List<WorkoutEntry>> getWorkoutForDate,
            Calendar currentMonthCalendar) {
        this.context = context;
        this.getWorkoutForDate = getWorkoutForDate;
        this.currentMonthCalendar = currentMonthCalendar;
        calculateDays();
    }

    public void setCurrentMonthCalendar(Calendar cal) {
        this.currentMonthCalendar = cal;
    }

    public void setSelectedDate(Calendar cal) {
        this.selectedDate = cal;
    }

    @Override public int getCount() { return days.size(); }
    @Override public Object getItem(int i) { return days.get(i); }
    @Override public long getItemId(int i) { return i; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CalendarDayItemView view;
        if (convertView == null) {
            view = new CalendarDayItemView(context);
        } else {
            view = (CalendarDayItemView) convertView;
        }

        int day = days.get(position);
        String routineText = "";
        String type = "NONE";
        boolean isToday = false;
        boolean isSelected = false;

        if (day != 0) {
            Calendar currentCellCal = getCalendarForDay(day);

            isToday = DateUtils.isSameDay(currentCellCal, Calendar.getInstance());

            if (selectedDate != null) {
                isSelected = DateUtils.isSameDay(currentCellCal, selectedDate);
            }

            List<WorkoutEntry> entries = getWorkoutForDate.apply(currentCellCal);
            if (entries != null && !entries.isEmpty()) {
                String exName = entries.get(0).getExerciseName();
                if ("휴식".equals(exName)) {
                    routineText = "휴식";
                    type = "REST";
                } else {
                    routineText = "운동";
                    type = "WORKOUT";
                }
            }
        }
        view.setDay(day, routineText, type, isToday, isSelected);
        return view;
    }

    private Calendar getCalendarForDay(int day) {
        Calendar cal = (Calendar) currentMonthCalendar.clone();
        cal.set(Calendar.DATE, day);
        DateUtils.resetTime(cal);
        return cal;
    }

    @Override
    public void notifyDataSetChanged() {
        calculateDays();
        super.notifyDataSetChanged();
    }

    private void calculateDays() {
        days.clear();
        Calendar tempCal = (Calendar) currentMonthCalendar.clone();

        tempCal.set(Calendar.DATE, 1);

        int startDayOfWeek = tempCal.get(Calendar.DAY_OF_WEEK);

        int lastDayOfMonth = tempCal.getActualMaximum(Calendar.DAY_OF_MONTH);

        for (int i = 1; i < startDayOfWeek; i++) {
            days.add(0);
        }

        for (int i = 1; i <= lastDayOfMonth; i++) {
            days.add(i);
        }
    }
}
