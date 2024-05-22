// src/main/java/com/example/alarmapp/AlarmAdapter.java
package com.example.alarmapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder> {

    private List<Alarm> alarmList;
    private OnAlarmDeleteListener onAlarmDeleteListener;

    public AlarmAdapter(List<Alarm> alarmList, OnAlarmDeleteListener onAlarmDeleteListener) {
        this.alarmList = alarmList;
        this.onAlarmDeleteListener = onAlarmDeleteListener;
    }

    @NonNull
    @Override
    public AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alarm_item, parent, false);
        return new AlarmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmViewHolder holder, int position) {
        Alarm alarm = alarmList.get(position);
        holder.alarmTimeTextView.setText(alarm.getTimeString());
        holder.deleteAlarmButton.setOnClickListener(v -> onAlarmDeleteListener.onAlarmDelete(alarm));
    }

    @Override
    public int getItemCount() {
        return alarmList.size();
    }

    public static class AlarmViewHolder extends RecyclerView.ViewHolder {
        TextView alarmTimeTextView;
        Button deleteAlarmButton;

        public AlarmViewHolder(@NonNull View itemView) {
            super(itemView);
            alarmTimeTextView = itemView.findViewById(R.id.alarmTimeTextView);
            deleteAlarmButton = itemView.findViewById(R.id.deleteAlarmButton);
        }
    }

    public interface OnAlarmDeleteListener {
        void onAlarmDelete(Alarm alarm);
    }
}
