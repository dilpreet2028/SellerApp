package com.medicians.mediciansseller.NewOrderServices;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class NewOrderReceiver extends BroadcastReceiver {
    public NewOrderReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("mytag","reciver");
        context.startService(new Intent(context,OrderService.class));

    }
}
