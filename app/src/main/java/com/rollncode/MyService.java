package com.rollncode;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.widget.Toast;

public class MyService extends Service {

    final static String KEY_INT_FROM_SERVICE = "KEY_INT_FROM_SERVICE";
    final static String ACTION_UPDATE_CNT = "UPDATE_CNT";
    final static String ACTION_MSG_TO_SERVICE = "MSG_TO_SERVICE";

    SharedPreferences sharedPreferences = new SharedPreferences();
    MyServiceThread myServiceThread;
    int cnt;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Toast.makeText(getApplicationContext(), "onCreate", Toast.LENGTH_LONG).show();
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(getApplicationContext(), "onStartCommand", Toast.LENGTH_LONG).show();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_MSG_TO_SERVICE);

        myServiceThread = new MyServiceThread();
        myServiceThread.start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Toast.makeText(getApplicationContext(), "onDestroy", Toast.LENGTH_LONG).show();
        myServiceThread.setRunning(false);
        super.onDestroy();
    }


    private class MyServiceThread extends Thread {

        private boolean running;

        public void setRunning(boolean running) {
            this.running = running;
        }

        @Override
        public void run() {
            cnt = sharedPreferences.getCounter();
            running = true;

            while (running) {
                try {
                    Thread.sleep(5000);

                    Intent intent = new Intent();
                    intent.setAction(ACTION_UPDATE_CNT);
                    intent.putExtra(KEY_INT_FROM_SERVICE, cnt);
                    sharedPreferences.setCounter(cnt);
                    sendBroadcast(intent);

                    cnt++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}