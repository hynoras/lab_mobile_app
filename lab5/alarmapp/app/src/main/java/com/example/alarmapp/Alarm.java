package com.example.alarmapp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Alarm {
    private Calendar calendar;

    public Alarm(Calendar calendar) {
        this.calendar = calendar;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public String getTimeString() {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        return sdf.format(calendar.getTime());
    }
}
