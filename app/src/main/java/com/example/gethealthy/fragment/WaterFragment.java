package com.example.gethealthy.fragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gethealthy.R;
import com.example.gethealthy.notification.MyNotification;

import javax.annotation.Nullable;

public class WaterFragment extends Fragment implements View.OnClickListener {

    private Button setWaterButton, deleteWaterButton;
    private EditText editWater;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_water, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        setWaterButton = (Button) view.findViewById(R.id.setWaterButton);
        setWaterButton.setOnClickListener(this);

        deleteWaterButton = (Button) view.findViewById(R.id.deleteWaterButton);
        deleteWaterButton.setOnClickListener(this);

        editWater = (EditText) view.findViewById(R.id.editWater);

        setAlarmWater();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setWaterButton:
                goAlarm();
                break;
            case R.id.deleteWaterButton:
                deleteAlarmWater();
                break;
        }
            
    }

    private void deleteAlarmWater() {
        if (alarmManager != null){
            alarmManager.cancel(pendingIntent);
        }
    }

    private void setAlarmWater() {
        String water = editWater.getText().toString().trim();

        if (water.isEmpty()) {
            editWater.setError("Minutes are required!");
            editWater.requestFocus();
            return;
        }
        Intent intent = new Intent(getActivity(), MyNotification.class);
        pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, intent, 0);
        alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
    }
    private void goAlarm(){
        String water = editWater.getText().toString().trim();
        int time = Integer.parseInt(water);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE){
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+(time*1000),time*1000, pendingIntent);
        }else {
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, System.currentTimeMillis()+(time*1000), time*1000, pendingIntent);
        }
    }
}