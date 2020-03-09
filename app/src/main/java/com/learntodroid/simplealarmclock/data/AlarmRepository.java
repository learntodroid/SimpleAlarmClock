package com.learntodroid.simplealarmclock.data;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class AlarmRepository {
    private AlarmDao alarmDao;

//    private AlarmRepository instance;
    private LiveData<List<Alarm>> alarmsLiveData;

    public AlarmRepository(Application application) {
        AlarmDatabase db = AlarmDatabase.getDatabase(application);
        alarmDao = db.alarmDao();
        alarmsLiveData = alarmDao.getAlarms();
    }

    public void insert(Alarm alarm) {
        AlarmDatabase.databaseWriteExecutor.execute(() -> {
            alarmDao.insert(alarm);
        });
    }

//    public AlarmRepository() {
//        alarmsLiveData = new MutableLiveData<>();
//
//        ArrayList<Alarm> alarms = new ArrayList<>();
//        alarms.add(new Alarm(10, 0, true));
//        alarms.add(new Alarm(12, 0, true));
//        alarms.add(new Alarm(17, 0, false));
//        alarmsLiveData.setValue(alarms);
//    }
//
//    public AlarmRepository getInstance() {
//        if (instance == null) {
//            instance = new AlarmRepository();
//        }
//        return instance;
//    }


//    public static AlarmRepository getInstance() {
//        if (instance == null) {
//            instance = new AlarmRepository();
//        }
//        return instance;
//    }

//    public void createAlarm(Alarm alarm) {
//        alarmsLiveData.getValue().add(alarm);
//    }


    public LiveData<List<Alarm>> getAlarmsLiveData() {
        return alarmsLiveData;
    }
}
