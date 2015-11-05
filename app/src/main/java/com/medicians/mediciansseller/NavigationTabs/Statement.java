package com.medicians.mediciansseller.NavigationTabs;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.medicians.mediciansseller.Adapter.ContentAdapter;
import com.medicians.mediciansseller.Adapter.PaymentAdapter;
import com.medicians.mediciansseller.MainActivity;
import com.medicians.mediciansseller.Models.Content;
import com.medicians.mediciansseller.Models.Payments;
import com.medicians.mediciansseller.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by dilpreet on 13/8/15.
 */
public class Statement extends Fragment {

    ListView listView;
    PaymentAdapter paymentAdapter;
    List<Payments> list;
    ProgressDialog progressDialog;
    Spinner spinner;
    ArrayAdapter<String> adapter;
    int month;
    String[] monthList={"January","Feburary","March","April","May","June","July","August","September","October","November","December"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.global, null);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading..");


        listView = (ListView) view.findViewById(R.id.contentList);
        spinner=(Spinner)view.findViewById(R.id.spinner);
        spinner.setVisibility(View.VISIBLE);
        adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,monthList);

        spinner.setAdapter(adapter);


        Calendar calendar=Calendar.getInstance();
        int previous=calendar.get(Calendar.MONTH);
        month=previous+1;
        spinner.setSelection(previous, false);
        getData();

        list = new ArrayList<>();


        paymentAdapter = new PaymentAdapter(getActivity(),list);
        listView.setAdapter(paymentAdapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                month=position+1;
                getData();
                progressDialog.show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }

    private void getData() {
        String url = "http://medicians.herokuapp.com/transactions/"+ MainActivity.id+"/"+month;
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest request = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseJSON(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Tag", error.toString());
                    }
                });
        queue.add(request);
    }

    private void parseJSON(String json) {

        JSONArray array;
        try {
            array = new JSONArray(json);
            list.clear();
            paymentAdapter.notifyDataSetChanged();
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                Payments payments=new Payments();

                payments.setAmount("Amount Paid : "+object.getString("amount"));
                payments.setDate("Date : " + object.getString("date"));


                list.add(payments);

            }
            paymentAdapter.notifyDataSetChanged();
            progressDialog.dismiss();


        } catch (JSONException e) {
            Log.d("Tag", e.toString());
        }


    }

}