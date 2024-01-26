package com.depression.relief.depressionissues.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.depression.relief.depressionissues.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ProgressFragment extends Fragment {
    private TextView tvSunday, tvSundayDate;
    private TextView tvMonday, tvMondayDate;
    private TextView tvTuesday, tvTuesdayDate;
    private TextView tvWednesday, tvWednesdayDate;
    private TextView tvThursday, tvThursdayDate;
    private TextView tvFriday, tvFridayDate;
    private TextView tvSaturday, tvSaturdayDate;

    private LinearLayout llSunday, llMonday, llTuesday, llWednesday, llThursday, llFriday, llSaturday;


    public ProgressFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress_fragent, container, false);

        tvSunday = view.findViewById(R.id.tvSunday);
        tvSundayDate = view.findViewById(R.id.tvSundayDate);
        tvMonday = view.findViewById(R.id.tvmonday);
        tvMondayDate = view.findViewById(R.id.tvmondayDate);
        tvTuesday = view.findViewById(R.id.tvTuesday);
        tvTuesdayDate = view.findViewById(R.id.tvTuesdayDate);
        tvWednesday = view.findViewById(R.id.tvwednesday);
        tvWednesdayDate = view.findViewById(R.id.tvwednesdayDate);
        tvThursday = view.findViewById(R.id.tvthursday);
        tvThursdayDate = view.findViewById(R.id.tvthursdayDate);
        tvFriday = view.findViewById(R.id.tvfriday);
        tvFridayDate = view.findViewById(R.id.tvfridayDate);
        tvSaturday = view.findViewById(R.id.tvSaturday);
        tvSaturdayDate = view.findViewById(R.id.tvSaturDateday);

        // Initialize the weekdays LinearLayouts
        llSunday = view.findViewById(R.id.llSunday);
        llMonday = view.findViewById(R.id.llmonday);
        llTuesday = view.findViewById(R.id.llTuesday);
        llWednesday = view.findViewById(R.id.llwednesday);
        llThursday = view.findViewById(R.id.llthursday);
        llFriday = view.findViewById(R.id.llfriday);
        llSaturday = view.findViewById(R.id.llSaturday);


        Calendar calendar = Calendar.getInstance();
        int currentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);


        SimpleDateFormat dayOfWeekFormat = new SimpleDateFormat("E", Locale.getDefault());
        SimpleDateFormat dateFormat = new SimpleDateFormat("d", Locale.getDefault());

        String currentDayOfWeek = dayOfWeekFormat.format(calendar.getTime());
        String currentDayOfMonthString = dateFormat.format(calendar.getTime());

        tvSunday.setText(dayOfWeekFormat.format(calendar.getTime()));
        tvSundayDate.setText(dateFormat.format(calendar.getTime()));
        if (currentDayOfWeek.equals("Sun") && currentDayOfMonthString.equals(String.valueOf(currentDayOfMonth))) {
            llSunday.setBackgroundResource(R.drawable.ic_selected_date);
        }

        calendar.add(Calendar.DAY_OF_WEEK, -1); // Move to next day
        tvMonday.setText(dayOfWeekFormat.format(calendar.getTime()));
        tvMondayDate.setText(dateFormat.format(calendar.getTime()));
        if (currentDayOfWeek.equals("Mon") && currentDayOfMonthString.equals(String.valueOf(currentDayOfMonth))) {
            llSunday.setBackgroundResource(R.drawable.ic_selected_date);
        }

        calendar.add(Calendar.DAY_OF_WEEK, -1); // Move to next day
        tvTuesday.setText(dayOfWeekFormat.format(calendar.getTime()));
        tvTuesdayDate.setText(dateFormat.format(calendar.getTime()));
        if (currentDayOfWeek.equals("Tue") && currentDayOfMonthString.equals(String.valueOf(currentDayOfMonth))) {
            llSunday.setBackgroundResource(R.drawable.ic_selected_date);
        }

        calendar.add(Calendar.DAY_OF_WEEK, -1); // Move to next day
        tvWednesday.setText(dayOfWeekFormat.format(calendar.getTime()));
        tvWednesdayDate.setText(dateFormat.format(calendar.getTime()));
        if (currentDayOfWeek.equals("Wed") && currentDayOfMonthString.equals(String.valueOf(currentDayOfMonth))) {
            llSunday.setBackgroundResource(R.drawable.ic_selected_date);
        }

        calendar.add(Calendar.DAY_OF_WEEK, -1); // Move to next day
        tvThursday.setText(dayOfWeekFormat.format(calendar.getTime()));
        tvThursdayDate.setText(dateFormat.format(calendar.getTime()));
        if (currentDayOfWeek.equals("Thu") && currentDayOfMonthString.equals(String.valueOf(currentDayOfMonth))) {
            llSunday.setBackgroundResource(R.drawable.ic_selected_date);
        }

        calendar.add(Calendar.DAY_OF_WEEK, -1); // Move to next day
        tvFriday.setText(dayOfWeekFormat.format(calendar.getTime()));
        tvFridayDate.setText(dateFormat.format(calendar.getTime()));
        if (currentDayOfWeek.equals("Fri") && currentDayOfMonthString.equals(String.valueOf(currentDayOfMonth))) {
            llSunday.setBackgroundResource(R.drawable.ic_selected_date);
        }

        calendar.add(Calendar.DAY_OF_WEEK, -1); // Move to next day
        tvSaturday.setText(dayOfWeekFormat.format(calendar.getTime()));
        tvSaturdayDate.setText(dateFormat.format(calendar.getTime()));
        if (currentDayOfWeek.equals("Sat") && currentDayOfMonthString.equals(String.valueOf(currentDayOfMonth))) {
            llSunday.setBackgroundResource(R.drawable.ic_selected_date);
        }

        return view;
    }
}