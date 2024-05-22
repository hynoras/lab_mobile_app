// src/main/java/com/example/alarmapp/MainActivity.java
package com.example.alarmapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AlarmAdapter.OnAlarmDeleteListener {

    private RecyclerView alarmRecyclerView;
    private FloatingActionButton addAlarmFab;
    private AlarmManager alarmManager;
    private List<Alarm> alarmList;
    private AlarmAdapter alarmAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alarmRecyclerView = findViewById(R.id.alarmRecyclerView);
        addAlarmFab = findViewById(R.id.addAlarmFab);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmList = new ArrayList<>();
        alarmAdapter = new AlarmAdapter(alarmList, this);

        alarmRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        alarmRecyclerView.setAdapter(alarmAdapter);

        addAlarmFab.setOnClickListener(v -> showTimePickerDialog());
    }

    private void showTimePickerDialog() {
        View view = getLayoutInflater().inflate(R.layout.dialog_time_picker, null);
        TimePicker timePicker = view.findViewById(R.id.timePicker);

        new AlertDialog.Builder(this)
                .setView(view)
                .setPositiveButton("Set", (dialog, which) -> {
                    Calendar calendar = Calendar.getInstance();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                        calendar.set(Calendar.MINUTE, timePicker.getMinute());
                    } else {
                        calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                        calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());
                    }
                    calendar.set(Calendar.SECOND, 0);
                    setAlarm(calendar);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void setAlarm(Calendar calendar) {
        Alarm alarm = new Alarm(calendar);
        alarmList.add(alarm);
        alarmAdapter.notifyItemInserted(alarmList.size() - 1);

        Intent intent = new Intent(this, AlarmReceiver.class);
        int flags = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S ? PendingIntent.FLAG_IMMUTABLE : 0;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, alarmList.size(), intent, flags);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    @Override
    public void onAlarmDelete(Alarm alarm) {
        int index = alarmList.indexOf(alarm);
        alarmList.remove(index);
        alarmAdapter.notifyItemRemoved(index);

        Intent intent = new Intent(this, AlarmReceiver.class);
        int flags = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S ? PendingIntent.FLAG_IMMUTABLE : 0;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, index, intent, flags);
        alarmManager.cancel(pendingIntent);
    }
}
