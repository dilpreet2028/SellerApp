package com.medicians.mediciansseller.Tabs;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.medicians.mediciansseller.Adapter.ContentAdapter;
import com.medicians.mediciansseller.Models.NewOrderModel;
import com.medicians.mediciansseller.NewOrderDetails;
import com.medicians.mediciansseller.R;
import com.medicians.mediciansseller.PopulateList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dilpreet on 10/8/15.
 */
public class Attempt extends Fragment {
    ListView listView;
    ContentAdapter contentAdapter;
    List<NewOrderModel> list;
    public static NewOrderModel newOrder;
    ProgressDialog progressDialog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.global,null);


        listView=(ListView)view.findViewById(R.id.contentList);



        contentAdapter=new ContentAdapter(getActivity(),list,1);
        listView.setAdapter(contentAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                newOrder = list.get(position);
                startActivity(new Intent(getActivity(), NewOrderDetails.class));
            }
        });
        return view;
    }




    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser)
        {


            PopulateList testClass=new PopulateList(getActivity(),"",3);
            testClass.getData();


          //  getData();
            Log.d("Tag","Here");
        }
        else{
           list=new ArrayList<>();
        }
    }


    private void getData(){


        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading..");
        progressDialog.show();

        String url="http://medicians.herokuapp.com/sellerorderinfo/1/attempt";
        RequestQueue queue= Volley.newRequestQueue(getActivity());
        StringRequest request=new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseJSON(response);
                        Log.d("tag", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Tag", error.toString());
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Error: " + error, Toast.LENGTH_LONG).show();
                    }
                });
        queue.add(request);
    }

    private void parseJSON(String json){

        JSONArray array;
        try{
            array=new JSONArray(json);
          //  list.clear();
            for(int i=0;i<array.length();i++){
                JSONObject object=array.getJSONObject(i);

                NewOrderModel item=new NewOrderModel();
                item.setCommission(object.getString("commission"));
                item.setDelivery_time(object.getString("delivery_time"));
                item.setOrder_id(object.getString("order_id"));
                item.setOrderdate(object.getString("orderdate"));
                item.setOrdertime(object.getString("ordertime"));
                item.setSeller_id(object.getString("seller_id"));
                // item.setStatus(object.getString("status"));
                // item.setStatus1(object.getString("status1"));
                item.setUser_id(object.getString("user_id"));
               // Toast.makeText(getActivity(),"One",Toast.LENGTH_LONG).show();
               // list.add(item);
                //contentAdapter.notifyDataSetChanged();

            }

            if(progressDialog.isShowing())
            {
                Log.d("Tag", "ccclose it");
                progressDialog.dismiss();
                progressDialog.cancel();
            }

        }
        catch (JSONException e){
            Log.d("Tag",e.toString());
            progressDialog.dismiss();
        }


    }

}
