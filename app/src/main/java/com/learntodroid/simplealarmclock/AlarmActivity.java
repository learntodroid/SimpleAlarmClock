package com.learntodroid.simplealarmclock;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.util.TimeUnit;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class AlarmActivity extends AppCompatActivity {
    public static final long RUN_DAILY = 24 * 60 * 60 * 1000;

    private AlarmManager alarmManager;
    private PendingIntent alarmPendingIntent;
    private TimePicker timePicker;
    private DatePicker datePicker;
    private TextView timeDifference;
    private Button scheduleAlarm, cancelAlarm;
    private CheckBox recurring, mon, tue, wed, thu, fri, sat, sun;
    private LinearLayout recurringOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        timePicker = findViewById(R.id.activity_alarm_timePicker);
//        datePicker = findViewById(R.id.activity_alarm_datePicker);
        timeDifference = findViewById(R.id.activity_alarm_timeDifference);

//        datePicker.setMinDate(System.currentTimeMillis());

        recurringOptions = findViewById(R.id.activity_alarm_recurring_options);
        recurring = findViewById(R.id.activity_alarm_recurring);
        mon = findViewById(R.id.activity_alarm_checkMon);
        tue = findViewById(R.id.activity_alarm_checkTue);
        wed = findViewById(R.id.activity_alarm_checkWed);
        thu = findViewById(R.id.activity_alarm_checkThu);
        fri = findViewById(R.id.activity_alarm_checkFri);
        sat = findViewById(R.id.activity_alarm_checkSat);
        sun = findViewById(R.id.activity_alarm_checkSun);

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

        scheduleAlarm = findViewById(R.id.activity_alarm_scheduleAlarm);
        scheduleAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAlarm();
            }
        });

        cancelAlarm = findViewById(R.id.activity_alarm_cancelAlarm);
        cancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAlarm();
            }
        });

//        updateTimeDifference();
    }

    // bug alarm starts immediately if alarm is set for a time during the day that has already passed
    public void startAlarm() {
        alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(getApplicationContext(), AlarmBroadcastReceiver.class);
        intent.putExtra("isRecurring", recurring.isChecked());
        intent.putExtra("Mon", mon.isChecked());
        intent.putExtra("Tue", tue.isChecked());
        intent.putExtra("Wed", wed.isChecked());
        intent.putExtra("Thu", thu.isChecked());
        intent.putExtra("Fri", fri.isChecked());
        intent.putExtra("Sat", sat.isChecked());
        intent.putExtra("Sun", sun.isChecked());

        alarmPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);

        int alarmHour = TimePickerUtil.getInstance().getTimePickerHour(timePicker);
        int alarmMinute = TimePickerUtil.getInstance().getTimePickerMinute(timePicker);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, alarmHour);
        calendar.set(Calendar.MINUTE, alarmMinute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        String timeText = "Alarm set for: ";
        timeText += alarmHour + " HR ";
        timeText += alarmMinute + " MIN" ;
        Toast.makeText(getApplicationContext(), timeText, Toast.LENGTH_SHORT).show();

        if (!recurring.isChecked()) {
            alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    alarmPendingIntent
            );
        } else {
            alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    RUN_DAILY,
                    alarmPendingIntent
            );
        }

        Log.i("startAlarm", timeText);
    }

    public void updateTimeDifference() {
        int alarmHour = TimePickerUtil.getInstance().getTimePickerHour(timePicker);
        int alarmMinute = TimePickerUtil.getInstance().getTimePickerMinute(timePicker);

        long sysTime = System.currentTimeMillis();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(sysTime);
        calendar.set(Calendar.HOUR_OF_DAY, alarmHour);
        calendar.set(Calendar.MINUTE, alarmMinute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        long diff_sec = (calendar.getTimeInMillis() - System.currentTimeMillis()) / 1000;




//        long diff_mil = sys_time - System.currentTimeMillis();
//        long diff_sec = diff_mil / 1000;
//        int diff_hour = (int) diff_sec / (60 * 60);
//        int diff_min = (int) diff_sec / (60 * 60);
//        int diff_second = (int) diff_sec / (60 * 60);

        timeDifference.setText("Next Alarm is " + diff_sec + " seconds away");
    }

    public void cancelAlarm() {
        // todo implement recyclerview of alarms
        // implement cancelation of an alarm by using a toggle button as trigger
    }
}
