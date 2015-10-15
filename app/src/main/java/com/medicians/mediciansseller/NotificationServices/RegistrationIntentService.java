package com.medicians.mediciansseller.NotificationServices;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.medicians.mediciansseller.R;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dilpreet on 15/10/15.
 */
public class RegistrationIntentService extends IntentService {
    public RegistrationIntentService() {
        super("RegistrationIntentService");
    }

    GoogleCloudMessaging gcm;
    SharedPreferences preferences;

    SharedPreferences.Editor editor;

    @Override
    protected void onHandleIntent(Intent intent) {


        preferences=getSharedPreferences("Data", MODE_PRIVATE);

        editor=preferences.edit();
        gcm=GoogleCloudMessaging.getInstance(this);
        String senderId=getResources().getString(R.string.gcm_id);
        try{
            String regId=gcm.register(senderId);
            editor.putString("regId",regId);
            editor.commit();
            Log.d("mytag", "chal ja yr");

            Toast.makeText(RegistrationIntentService.this, "Added", Toast.LENGTH_SHORT).show();
            registerOnServer(regId);
        }catch (IOException e){

        }
    }


    private void registerOnServer(final String regId){

        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        StringRequest request=new StringRequest(Request.Method.POST, "http://192.168.0.105:5000/gcmregister"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("name",preferences.getString("sellerid",""));
                params.put("reg",regId);
                return params;
            }
        };

        queue.add(request);

    }

}
