package com.medicians.mediciansseller.NotificationServices;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by dilpreet on 19/8/15.
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("mytag", "Service going to start");
        Toast.makeText(context,"Service started",Toast.LENGTH_LONG).show();
        context.startService(new Intent(context, NofityService.class));

    }
}
