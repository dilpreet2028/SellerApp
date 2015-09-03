package com.medicians.mediciansseller.NewOrderServices;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;

import com.medicians.mediciansseller.PopulateList;
import com.medicians.mediciansseller.Tabs.NewOrder;

public class OrderService extends Service {
    public OrderService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //PopulateList populateList=new PopulateList(getApplicationContext(),"http://medicians.herokuapp.com/sellerorder/1/new",10);
        //populateList.getData();
        //new UpdateMain().execute();
        NewOrder.changeList();
        stopSelf();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class UpdateMain extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            PopulateList populateList=new PopulateList(getApplicationContext(),"http://medicians.herokuapp.com/sellerorder/1/new",10);
            populateList.getData();
        }
    }

}
