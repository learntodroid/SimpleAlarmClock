package com.learntodroid.simplealarmclock.createalarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.learntodroid.simplealarmclock.AlarmBroadcastReceiver;
import com.learntodroid.simplealarmclock.R;
import com.learntodroid.simplealarmclock.TimePickerUtil;
import com.learntodroid.simplealarmclock.data.Alarm;

import java.util.Calendar;

public class CreateAlarmFragment extends Fragment {
    public static final long RUN_DAILY = 24 * 60 * 60 * 1000;

    private TimePicker timePicker;
    private Button scheduleAlarm, cancelAlarm;
    private LinearLayout recurringOptions;
    private CheckBox recurring, mon, tue, wed, thu, fri, sat, sun;

    private AlarmManager alarmManager;
    private PendingIntent alarmPendingIntent;

    private CreateAlarmViewModel createAlarmViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createAlarmViewModel = ViewModelProviders.of(this).get(CreateAlarmViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_createalarm, container, false);

        timePicker = view.findViewById(R.id.fragment_createalarm_timePicker);

        setupRecurringOptions(view);
        setupButtons(view);

        return view;
    }

    private void setupRecurringOptions(View view) {
        recurringOptions = view.findViewById(R.id.fragment_createalarm_recurring_options);
        recurring = view.findViewById(R.id.fragment_createalarm_recurring);
        mon = view.findViewById(R.id.fragment_createalarm_checkMon);
        tue = view.findViewById(R.id.fragment_createalarm_checkTue);
        wed = view.findViewById(R.id.fragment_createalarm_checkWed);
        thu = view.findViewById(R.id.fragment_createalarm_checkThu);
        fri = view.findViewById(R.id.fragment_createalarm_checkFri);
        sat = view.findViewById(R.id.fragment_createalarm_checkSat);
        sun = view.findViewById(R.id.fragment_createalarm_checkSun);

        recurring.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    recurringOptions.setVisibility(View.VISIBLE);
                } else {
                    recurringOptions.setVisibility(View.GONE);
                }
            }
        });
    }

    private void setupButtons(View view) {
        scheduleAlarm = view.findViewById(R.id.fragment_createalarm_scheduleAlarm);
        cancelAlarm = view.findViewById(R.id.fragment_createalarm_cancelAlarm);

        scheduleAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scheduleAlarm();
            }
        });

//        cancelAlarm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                cancelAlarm();
//            }
//        });
    }

    private void scheduleAlarm() {
        int alarmId = TimePickerUtil.getInstance().getTimePickerHour(timePicker) * TimePickerUtil.getInstance().getTimePickerMinute(timePicker);

        Alarm alarm = new Alarm(
                alarmId,
                TimePickerUtil.getInstance().getTimePickerHour(timePicker),
                TimePickerUtil.getInstance().getTimePickerMinute(timePicker),
                true,
                recurring.isChecked(),
                mon.isChecked(),
                tue.isChecked(),
                wed.isChecked(),
                thu.isChecked(),
                fri.isChecked(),
                sat.isChecked(),
                sun.isChecked()
        );

        createAlarmViewModel.insert(alarm);

        alarm.schedule(getContext());
    }
}
