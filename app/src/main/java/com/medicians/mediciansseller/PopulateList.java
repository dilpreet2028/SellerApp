package com.medicians.mediciansseller;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.medicians.mediciansseller.Adapter.ContentAdapter;
import com.medicians.mediciansseller.Models.NewOrderModel;
import com.medicians.mediciansseller.Tabs.*;
import com.medicians.mediciansseller.Tabs.Process;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dilpreet on 22/8/15.
 */
public class PopulateList {


    ProgressDialog progressDialog;
    Context context;
    String url;
    int index;

    public PopulateList(Context context, String url,int index) {
        super();

        this.context=context;
        this.url=url;
        this.index=index;

    }

    public void getData(){


        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("Loading..");
        progressDialog.show();



        StringRequest request=new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //Toast.makeText(context,response,Toast.LENGTH_LONG).show();
                            parseJSON(response);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Tag", error.toString());
                        progressDialog.dismiss();

                    }
                });
        AppController.getInstance().addToRequestQueue(request);


    }

    public void parseJSON(String json){




        JSONArray array;
        try{
            array=new JSONArray(json);
            Dispatched.list.clear();
            Process.list.clear();

            for(int i=0;i<array.length();i++){

                JSONObject object=array.getJSONObject(i);

                if(object.getString("status").compareToIgnoreCase("Cancel")!=0)
                {
                    NewOrderModel item = new NewOrderModel();
                    item.setCommission(object.getString("commission"));
                    item.setDelivery_time(object.getString("delivery_time"));
                    item.setOrder_id(object.getString("order_id"));
                    item.setOrderdate(object.getString("orderdate"));
                    item.setOrdertime(object.getString("ordertime"));
                    item.setSeller_id(object.getString("seller_id"));
                    item.setStatus(object.getString("status"));
                    //item.setStatus1(object.getString("status1"));
                    item.setUser_id(object.getString("user_id"));

                    if(index==1){
                        Process.list.add(item);
                        Process.contentAdapter.notifyDataSetChanged();
                        Log.d("tag","in one");
                    }

                    if(index==2) {
                        Dispatched.list.add(item);
                        Dispatched.contentAdapter.notifyDataSetChanged();
                        Log.d("tag", "in two");
                    }
                }


            }
           // Toast.makeText(context, "One: "+Dispatched.list.toString(), Toast.LENGTH_LONG).show();
           // Dispatched.contentAdapter.notifyDataSetChanged();
            if(progressDialog.isShowing())
            {
                Log.d("Tag", "ccclose it");
                progressDialog.dismiss();
                //progressDialog.cancel();
            }

        }
        catch (JSONException e){
            Log.d("Tag",e.toString());
            progressDialog.dismiss();
        }



    }

}
