package com.learntodroid.simplealarmclock;

import android.os.Build;
import android.widget.TimePicker;

public final class TimePickerUtil {
    private static TimePickerUtil instance;

    private TimePickerUtil() {

    }

    public static  TimePickerUtil getInstance() {
        if (instance == null) {
            instance = new TimePickerUtil();
        }
        return instance;
    }

    public int getTimePickerHour(TimePicker tp) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return tp.getHour();
        } else {
            return tp.getCurrentHour();
        }
    }

    public int getTimePickerMinute(TimePicker tp) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return tp.getMinute();
        } else {
            return tp.getCurrentMinute();
        }
    }
}
