package com.medicians.mediciansseller;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by dilpreet on 22/8/15.
 */
public class AppController extends Application {

    public static final String TAG = AppController.class
            .getSimpleName();
    public static String basic="http://medicians.herokuapp.com/sellerorder/"+ MainActivity.id+"/";
    public static String info="http://medicians.herokuapp.com/sellerorderinfo/"+ MainActivity.id+"/";
    private RequestQueue requestQueue;
    private  static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    private RequestQueue getRequestQueue(){
        if(requestQueue==null)
            requestQueue= Volley.newRequestQueue(getApplicationContext());

        return requestQueue;
    }

    public <T> void addToRequestQueue(StringRequest request){
        getRequestQueue().add(request);
    }
}
