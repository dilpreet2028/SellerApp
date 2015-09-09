package com.medicians.mediciansseller.NavigationTabs;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.medicians.mediciansseller.AppController;
import com.medicians.mediciansseller.R;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by dilpreet on 7/9/15.
 */
public class Home extends Fragment {

    int length,available;
    ProgressBar progressBar;
    TextView textView,newOrdersText;
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.home_layout,null);
        available=0;
        progressBar=(ProgressBar)view.findViewById(R.id.progressBarHome);
        textView=(TextView)view.findViewById(R.id.out_of_stock);
        newOrdersText=(TextView)view.findViewById(R.id.new_order);

        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading..");
        progressDialog.show();

        progressBar.setIndeterminate(false);
        progressBar.setMax(100);
        getQuantityData();
        getNewData();
        return view;
    }

    private void getNewData(){
        String url="http://medicians.herokuapp.com/sellerorder/1/new";
        StringRequest request=new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int newOrders=0;
                        JSONArray array;

                        try{
                            array=new JSONArray(response);
                            newOrders=array.length();
                            newOrdersText.setText("You have "+(newOrders+1)+" new Orders");

                        }catch (Exception e){

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        AppController.getInstance().addToRequestQueue(request);
    }

    private void getQuantityData(){
        String url="http://medicians.herokuapp.com/seller_quantity/"+"demo";
        StringRequest request=new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONArray array;
                        int t;
                        try{
                            array=new JSONArray(response);
                            length=array.length();

                            JSONObject object;

                            for(int i=0;i<array.length();i++){
                                object=array.getJSONObject(i);
                                t=Integer.parseInt(object.getString("quantity"));
                                if(t>0)
                                    available++;

                            }


                            progressBar.setProgress((int)(available*100)/length);
                            textView.setText("Out of Stock :"+(length-available)+" ");
                            progressDialog.dismiss();
                        }catch (Exception e){

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        AppController.getInstance().addToRequestQueue(request);
    }


}
