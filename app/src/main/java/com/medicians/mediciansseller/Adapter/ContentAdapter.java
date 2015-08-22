package com.medicians.mediciansseller.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.medicians.mediciansseller.AppController;
import com.medicians.mediciansseller.Models.NewOrderModel;
import com.medicians.mediciansseller.R;
import com.medicians.mediciansseller.PopulateList;

import java.util.List;

/**
 * Created by dilpreet on 12/8/15.
 */
public class ContentAdapter extends BaseAdapter{

    List<NewOrderModel> list;
    Context context;
    LayoutInflater inflater;
    TextView orderidView,deliveryView;
    Button setButton,rejectG;
    TableRow row;
    int time,index;
    String status;

    public ContentAdapter(Context context,List<NewOrderModel> list,int index) {
        super();
        this.list = list;
        this.context = context;
        this.index = index;

        if (index == 1)
            status = "dispatch";
        if (index == 2)
            status = "attempt";
    }

    /*
        index==1 for Process tab
        index==2 for Dispatch tab


     */
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final String orderId;
        if(inflater==null)
            inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView==null)
            convertView=inflater.inflate(R.layout.new_order_item,null);

        orderidView=(TextView)convertView.findViewById(R.id.orderId);
        deliveryView=(TextView)convertView.findViewById(R.id.deliveryTime);
        setButton=(Button)convertView.findViewById(R.id.setButton);
        rejectG=(Button)convertView.findViewById(R.id.rejectG);

        row=(TableRow)convertView.findViewById(R.id.rowTwo);

        row.setVisibility(View.VISIBLE);


        if(index==1)
            setButton.setText("Dispatch");

        if(index==2)
            setButton.setText("Attempt");


        orderId=list.get(position).getOrder_id();

        orderidView.setText(list.get(position).getOrder_id());
        deliveryView.setText(list.get(position).getDelivery_time());


        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postData("http://medicians.herokuapp.com/update_status1/"+orderId+"/"+status);
                Log.d("tag","status 1 :"+status);
            }
        });



        rejectG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                postData("http://medicians.herokuapp.com/update_status/"+orderId+"/cancel");
            }
        });

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void postData(String url){




        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Mytag", "Added");
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        AppController.getInstance().addToRequestQueue(request);

        refreshList();
    }




    private void refreshList(){

        if(index==1){
            PopulateList testClass=new PopulateList(context,"http://medicians.herokuapp.com/sellerorderinfo/1/process",1);
            testClass.getData();
        }
        if(index==2){
            PopulateList testClass=new PopulateList(context,"http://medicians.herokuapp.com/sellerorderinfo/1/dispatch",2);
            testClass.getData();
        }
    }


}
