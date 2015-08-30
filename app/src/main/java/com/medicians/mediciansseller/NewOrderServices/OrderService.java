package com.medicians.mediciansseller.NewOrderServices;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.medicians.mediciansseller.PopulateList;

public class OrderService extends Service {
    public OrderService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //PopulateList populateList=new PopulateList(getApplicationContext(),"http://medicians.herokuapp.com/sellerorder/1/new",10);
        //populateList.getData();
        stopSelf();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
