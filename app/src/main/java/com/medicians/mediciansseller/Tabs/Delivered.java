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
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.medicians.mediciansseller.Adapter.AttemptAdapter;
import com.medicians.mediciansseller.Models.Content;
import com.medicians.mediciansseller.Adapter.ContentAdapter;
import com.medicians.mediciansseller.Models.NewOrderModel;
import com.medicians.mediciansseller.NewOrderDetails;
import com.medicians.mediciansseller.PopulateList;
import com.medicians.mediciansseller.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dilpreet on 13/8/15.
 */
public class Delivered extends Fragment {

    ListView listView;
    public  static AttemptAdapter attemptAdapter;
    public static List<NewOrderModel> list;
    NewOrderModel newOrder;
    Button setButton,rejectG;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.global,null);

        setButton=(Button)view.findViewById(R.id.setButton);
        listView=(ListView)view.findViewById(R.id.contentList);
        list=new ArrayList<>();
        attemptAdapter=new AttemptAdapter(getActivity(),list,2);
        listView.setAdapter(attemptAdapter);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                newOrder = list.get(position);
                Intent intent=new Intent(getActivity(), NewOrderDetails.class);
                intent.putExtra("orderid",newOrder.getOrder_id());
                intent.putExtra("flag",5);
                startActivityForResult(intent,1);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PopulateList populateList=new PopulateList(getActivity(),"http://medicians.herokuapp.com/sellerorderinfo/1/Compelete",4);
        populateList.getData();

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser)
        {

            PopulateList populateList=new PopulateList(getActivity(),"http://medicians.herokuapp.com/sellerorderinfo/1/Compelete",4);
            populateList.getData();


        }

    }
}
