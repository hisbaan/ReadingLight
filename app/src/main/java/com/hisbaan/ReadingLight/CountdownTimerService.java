package com.hisbaan.ReadingLight;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;

//TODO move everything but the buttons here (from the sleep timer class) to put it in the service. Also learn how to use services.
public class CountdownTimerService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public CountdownTimerService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
