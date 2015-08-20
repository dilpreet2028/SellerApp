package com.medicians.mediciansseller.Services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.medicians.mediciansseller.MainActivity;
import com.medicians.mediciansseller.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dilpreet on 18/8/15.
 */
public class ServerRequest {
    Context context;
    String count;
    public ServerRequest(Context context) {
        super();
        this.context=context;
        start();



    }

    private void start(){

            new AsyncRequest().execute();

    }

    private class AsyncRequest extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... params) {
            String url="http://medicians.herokuapp.com/ordercount/123/delivered";

            RequestQueue queue= Volley.newRequestQueue(context);
            StringRequest request=new StringRequest(url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            JSONObject jsonObject;
                            try{

                               jsonObject =new JSONObject(response);
                                count=jsonObject.getString("count");


                                if(Integer.parseInt(count)>0)
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
        PendingIntent pi=PendingIntent.getActivity(context,0,new Intent(context,MainActivity.class),0);

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
