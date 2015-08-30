package com.medicians.mediciansseller.NotificationServices;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class NofityService extends Service {
    public NofityService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("MyTag","Service STarted");
        ServerRequest serverRequest=new ServerRequest(this);
        stopSelf();
        return START_STICKY;

    }
}
