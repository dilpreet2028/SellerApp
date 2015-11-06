package com.medicians.mediciansseller.Tabs;


import android.app.ProgressDialog;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.medicians.mediciansseller.Adapter.StocksAdapter;
import com.medicians.mediciansseller.AppController;
import com.medicians.mediciansseller.MainActivity;
import com.medicians.mediciansseller.Models.Stocks;
import com.medicians.mediciansseller.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MyStocksFragment extends Fragment {


    List<Stocks> stocksList;
    ListView listView;
    int index;
    View view;
    StocksAdapter stocksAdapter;
    ProgressDialog dialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       view=inflater.inflate(R.layout.fragment_my_stock, container, false);
        listView=(ListView)view.findViewById(R.id.listing);
        stocksList=new ArrayList<>();

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            index = getArguments().getInt("key");
            dialog=new ProgressDialog(getActivity());
            dialog.setMessage("Loading");
            dialog.show();
           ;
            getData();
        }

      }
    private void getData(){
        String url="http://medicians.herokuapp.com/seller_outofstock";
        StringRequest request=new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseJson(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put("id",MainActivity.id);
                return map;
            }
        };
       AppController.getInstance().addToRequestQueue(request);
    }

    private void parseJson(String array) {
        JSONArray ar;
        stocksList=new ArrayList<>();
        try {
            ar=new JSONArray(array);
            Stocks stocks ;
            JSONObject object;
            String status;
            int quanitity;
            for (int i = 0; i < ar.length(); i++) {
                stocks = new Stocks();
                object = ar.getJSONObject(i);
                status = object.getString("status");
                quanitity = object.getInt("quantity");
                stocks.setMrp(object.getString("mrp"));
                stocks.setSp(object.getString("sp"));
                stocks.setQuantity(quanitity);
                stocks.setName(object.getString("name"));
                stocks.setStatus(status);

                if (index == 0 && status.compareToIgnoreCase("active") == 0 && quanitity > 0)
                {
                    stocksList.add(stocks);
                }
                else if (index == 1 && quanitity == 0)
                    stocksList.add(stocks);
                else if (index == 2 && status.compareToIgnoreCase("inactive") == 0)
                    stocksList.add(stocks);


            }
            stocksAdapter=new StocksAdapter(stocksList,getActivity(),index);
            listView.setAdapter(stocksAdapter);
            dialog.dismiss();
        } catch (JSONException e) {
            Log.d("mytag",e+"");
            dialog.dismiss();
        }
    }
}
