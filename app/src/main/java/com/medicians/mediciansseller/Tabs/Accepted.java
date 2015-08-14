package com.medicians.mediciansseller.Tabs;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TableRow;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.medicians.mediciansseller.Content;
import com.medicians.mediciansseller.ContentAdapter;
import com.medicians.mediciansseller.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dilpreet on 10/8/15.
 */
public class Accepted extends Fragment {
    ListView listView;
    ContentAdapter contentAdapter;
    List<Content> list;
    ProgressDialog progressDialog;
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.global,container,false);
        listView=(ListView)view.findViewById(R.id.contentList);
        list=new ArrayList<>();
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading..");
        progressDialog.show();



        contentAdapter=new ContentAdapter(getActivity(),list);
        listView.setAdapter(contentAdapter);


        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    private void getData(){
        String url="http://medicians.herokuapp.com/sellerorder/123/accepted";
        RequestQueue queue= Volley.newRequestQueue(getActivity());
        StringRequest request=new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseJSON(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Tag",error.toString());
                    }
                });
        queue.add(request);
    }

    private void parseJSON(String json){

        JSONArray array;
        try{
            array=new JSONArray(json);

            for(int i=0;i<array.length();i++){
                JSONObject object=array.getJSONObject(i);

                Content item=new Content();
                item.setBrandname(object.getString("brandname"));
                item.setCategory(object.getString("category"));
                item.setQuantity(object.getString("quantity"));
                item.setCompany("new");
                item.setSp(object.getString("sp"));

                list.add(item);
                contentAdapter.notifyDataSetChanged();

            }
            progressDialog.dismiss();
        }
        catch (JSONException e){
            Log.d("Tag",e.toString());
        }


    }
}
