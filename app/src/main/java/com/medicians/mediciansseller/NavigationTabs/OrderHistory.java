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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.medicians.mediciansseller.Adapter.HistoryAdapter;
import com.medicians.mediciansseller.AppController;
import com.medicians.mediciansseller.MainActivity;
import com.medicians.mediciansseller.Models.History;
import com.medicians.mediciansseller.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dilpreet on 3/9/15.
 */
public class OrderHistory extends Fragment {
    View view;
    List<History> list;
    HistoryAdapter adapter;
    ListView listView;
    ProgressDialog progressDialog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.global,null);
        list=new ArrayList<>();
        listView=(ListView)view.findViewById(R.id.contentList);
        getData();
        adapter=new HistoryAdapter(getActivity(),list);
        listView.setAdapter(adapter);
        return view;
    }

    public void getData(){

        String url="http://medicians.herokuapp.com/allsellerorders/"+ MainActivity.id;

        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading..");
        progressDialog.show();



        StringRequest request=new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //Toast.makeText(context,response,Toast.LENGTH_LONG).show();
                        parseJSON(response);
                        Log.d("mytag",response);


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

    private void parseJSON(String json){
        JSONArray array;
        try{
            array=new JSONArray(json);
            JSONObject object;
            for(int i=0;i<array.length();i++){

                object=array.getJSONObject(i);
                History item=new History();
                item.setDate(object.getString("orderdate"));
                item.setOrder(object.getString("order_id"));
                item.setStatus(object.getString("status1"));
                item.setTime(object.getString("ordertime"));
                list.add(item);
                Log.d("mytag","added");
            }
            adapter.notifyDataSetChanged();
            progressDialog.dismiss();
        }catch (Exception e){

        }
    }
}
