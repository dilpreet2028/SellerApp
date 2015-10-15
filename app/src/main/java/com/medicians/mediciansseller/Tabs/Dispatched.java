package com.medicians.mediciansseller.Tabs;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.medicians.mediciansseller.Adapter.ContentAdapter;
import com.medicians.mediciansseller.Models.NewOrderModel;
import com.medicians.mediciansseller.NewOrderDetails;
import com.medicians.mediciansseller.R;
import com.medicians.mediciansseller.PopulateList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dilpreet on 13/8/15.
 */
public class Dispatched extends Fragment {

    ListView listView;
    public  static ContentAdapter contentAdapter;
    public static List<NewOrderModel> list;
     NewOrderModel newOrder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.global,null);


        listView=(ListView)view.findViewById(R.id.contentList);
       list=new ArrayList<>();
        contentAdapter=new ContentAdapter(getActivity(),list,2);
        listView.setAdapter(contentAdapter);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                newOrder = list.get(position);
                Intent intent=new Intent(getActivity(), NewOrderDetails.class);
                intent.putExtra("orderid",newOrder.getOrder_id());
                intent.putExtra("flag",2);
                startActivityForResult(intent,1);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PopulateList populateList=new PopulateList(getActivity(),"http://medicians.herokuapp.com/sellerorderinfo/1/Processed",2);
        populateList.getData();

    }



    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser)
        {



            PopulateList populateList=new PopulateList(getActivity(),"http://medicians.herokuapp.com/sellerorderinfo/1/Processed",2);
            populateList.getData();


        }

    }



}
