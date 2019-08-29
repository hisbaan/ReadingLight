package com.hisbaan.ReadingLight;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;

//TODO move everything but the buttons here (from the sleep timer class) to put it in the service. Also learn how to use services.
public class CountdownTimerService extends Service {
    public CountdownTimerService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
