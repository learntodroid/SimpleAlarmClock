package com.learntodroid.simplealarmclock;

import java.util.ArrayList;

public class AlarmsRepository {
    private ArrayList<Alarm> alarms;

    public AlarmsRepository() {
        alarms = new ArrayList<Alarm>();
    }

    public ArrayList<Alarm> getAlarms() {
        return alarms;
    }
}
