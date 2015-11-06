package com.medicians.mediciansseller.NavigationTabs;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.medicians.mediciansseller.Adapter.ContentAdapter;
import com.medicians.mediciansseller.Adapter.DueAdapter;
import com.medicians.mediciansseller.MainActivity;
import com.medicians.mediciansseller.Models.Content;
import com.medicians.mediciansseller.Models.Due;
import com.medicians.mediciansseller.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dilpreet on 14/8/15.
 */
public class DuePayment extends Fragment {

    ListView listView;
    DueAdapter dueAdapter;
    List<Due> list;
    ProgressDialog progressDialog;
    TableLayout tableLayout;
    TextView totalAll,unpaidAll;
    int totalInt=0,unpaidInt=0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.global, null);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading..");
        progressDialog.show();

        tableLayout=(TableLayout)view.findViewById(R.id.dueView);
        tableLayout.setVisibility(View.VISIBLE);

        listView = (ListView) view.findViewById(R.id.contentList);
        list = new ArrayList<>();

        getData();
        dueAdapter = new DueAdapter(list,getActivity());
        listView.setAdapter(dueAdapter);


        totalAll=(TextView)view.findViewById(R.id.totalAll);
        unpaidAll=(TextView)view.findViewById(R.id.unpaidAll);




        return view;
    }

    private void getData() {
        String url = "http://medicians.herokuapp.com/masterpayment";
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest request = new StringRequest(Request.Method.POST,url,
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
                        Toast.makeText(getActivity(),"Error",Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put("id", MainActivity.id);
                return map;
            }
        };
        queue.add(request);
    }

    private void parseJSON(String json) {

        JSONArray array;
        try {
            array = new JSONArray(json);

            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                float totalcom=object.getInt("totalcom");
                Due addItem=new Due();
                float paid=object.getInt("paid");
                float unpaid=totalcom-paid;
                totalInt+=totalcom;
                unpaidInt+=unpaid;

                addItem.setAmount("Total :"+totalcom+"");

                addItem.setDate("Date :" + object.getString("month"));
                addItem.setPaid("Amount Paid:" + paid + "");
                addItem.setUnpaid("Unpaid: " +unpaid);
                list.add(addItem);

            }
            dueAdapter.notifyDataSetChanged();
            progressDialog.dismiss();

            totalAll.setText("Amount to be Paid :" + totalInt);
            unpaidAll.setText("Amount UnPaid :"+unpaidInt+"");

        } catch (JSONException e) {
            Log.d("Tag", e.toString());
        }


    }
}