package com.medicians.mediciansseller.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class NewOrderService extends Service {
    public NewOrderService() {
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
