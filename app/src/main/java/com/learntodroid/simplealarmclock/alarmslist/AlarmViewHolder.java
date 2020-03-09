package com.learntodroid.simplealarmclock.alarmslist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.learntodroid.simplealarmclock.Alarm;
import com.learntodroid.simplealarmclock.R;

public class AlarmViewHolder extends RecyclerView.ViewHolder {
    private TextView alarmTime;

    public AlarmViewHolder(@NonNull View itemView) {
        super(itemView);

        alarmTime = itemView.findViewById(R.id.item_alarm_time);
    }

    public void bind(Alarm alarm) {
        String alarmText = String.format("%dd:%dd", alarm.getHour(), alarm.getMinute());

        alarmTime.setText(alarmText);
    }

    public static class AlarmsListFragment extends Fragment {
        private AlarmRecyclerViewAdapter alarmRecyclerViewAdapter;
        private AlarmsListViewModel alarmsListViewModel;
        private RecyclerView alarmsRecyclerView;

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            alarmRecyclerViewAdapter = new AlarmRecyclerViewAdapter();

        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_listalarms, container, false);

            alarmsRecyclerView = view.findViewById(R.id.fragment_listalarms_recylerView);
            alarmsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            alarmsRecyclerView.setAdapter(alarmRecyclerViewAdapter);

            return view;
        }
    }
}
