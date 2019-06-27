package com.rollncode;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends Activity {


    TextView tvTimeService, tvCounter;
    MyMainReceiver myMainReceiver;
    SharedPreferences sharedPreferences;
    Intent myIntent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvCounter = findViewById(R.id.tvCouner);
        tvTimeService = findViewById(R.id.tvTimeService);
    }

    public void onClickStart(View view) {
        startService();
    }

    public void onClickStop(View view) {
        stopService();
    }


    private void startService() {
        myIntent = new Intent(MainActivity.this, MyService.class);
        sharedPreferences = new SharedPreferences();
        startService(myIntent);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String dateString = sdf.format(date);
        sharedPreferences.setTimeOfTheLastLaunchService(dateString);
        sharedPreferences.setCounter(0);
    }

    private void stopService() {
        if (myIntent != null) {
            stopService(myIntent);
        }
        myIntent = null;
    }

    @Override
    protected void onStart() {
        myMainReceiver = new MyMainReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MyService.ACTION_UPDATE_CNT);
        registerReceiver(myMainReceiver, intentFilter);

        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(myMainReceiver);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService();
    }

    private class MyMainReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(MyService.ACTION_UPDATE_CNT)) {
                int int_from_service = intent.getIntExtra(MyService.KEY_INT_FROM_SERVICE, 0);
                tvCounter.setText(String.valueOf(int_from_service));
                tvTimeService.setText(sharedPreferences.getTimeOfTheLastLaunchService());
            }
        }
    }
}