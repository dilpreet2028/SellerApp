package com.medicians.mediciansseller.Services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.medicians.mediciansseller.MainActivity;
import com.medicians.mediciansseller.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by dilpreet on 18/8/15.
 */
public class ServerRequest {
    Context context;
    int count;
    public static int status;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    ArrayList<String> list;

    public ServerRequest(Context context) {
        super();
        this.context=context;
        list=new ArrayList<>();

         //preferences=context.getSharedPreferences("array",Context.MODE_PRIVATE);
        //editor=preferences.edit();

        start();
        status=0;

    }

    private void start(){

        if(MainActivity.mainScreenOn==0)
            new AsyncRequest().execute();



    }

    private class AsyncRequest extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... params) {
            count=0;
            String url="http://medicians.herokuapp.com/ordercount/1/new";
            final ArrayList<String> templist=new ArrayList<>();
            RequestQueue queue= Volley.newRequestQueue(context);
            StringRequest request=new StringRequest(url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            JSONArray jsonArray;
                            try{
                                JSONObject object;
                               jsonArray =new JSONArray(response);
                                for (int i=0;i<jsonArray.length();i++){
                                        object=jsonArray.getJSONObject(i);
                                        templist.add(object.getString("order_id"));
                                    count++;

                                }


                                if(count>0)
                                    notification();



                            }
                            catch (Exception e){

                            }


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });

            queue.add(request);
            return null;
        }


    }


    private void notification(){
        PendingIntent pi=PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0);
        status=1;
        NotificationManager nm=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder notify=new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.notification_template_icon_bg)
                .setContentTitle("Hello!!")
                .setContentInfo("You have " + count + " new orders")
                .setContentIntent(pi)
                .setDefaults(Notification.DEFAULT_ALL);
        nm.notify(11,notify.build());



    }



}
