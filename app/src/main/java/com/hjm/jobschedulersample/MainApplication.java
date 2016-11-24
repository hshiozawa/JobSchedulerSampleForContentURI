package com.hjm.jobschedulersample;

import android.app.Application;
import android.os.Build;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // use job scheduler
            CameraJobService.startJob(this);
        } else {
            // use broadcast receiver
            CameraBroadcastReceiver.register(this);
        }
    }

}
