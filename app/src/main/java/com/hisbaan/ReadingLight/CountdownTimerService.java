package com.hisbaan.ReadingLight;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

//TODO move everything but the buttons here (from the sleep timer class) to put it in the service. Also learn how to use services.
public class CountdownTimerService extends Service {
    public CountdownTimerService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
