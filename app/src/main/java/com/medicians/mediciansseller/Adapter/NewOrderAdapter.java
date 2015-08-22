package com.medicians.mediciansseller.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.medicians.mediciansseller.AppController;
import com.medicians.mediciansseller.Models.NewOrderModel;
import com.medicians.mediciansseller.R;
import com.medicians.mediciansseller.Tabs.NewOrder;

import java.util.List;


/**
 * Created by dilpreet on 20/8/15.
 */
public class NewOrderAdapter extends BaseAdapter {
    List<NewOrderModel> list;
    Context context;
    LayoutInflater inflater;
    TextView orderidView,deliveryView;
    Button accept,acceptWithDelay,reject;
    String currentOrderId;
    TableRow row;
    int time;

    public NewOrderAdapter(Context context,List<NewOrderModel> list) {
        super();
        this.list=list;
        this.context=context;


    }

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
        accept=(Button)convertView.findViewById(R.id.acceptButton);
        acceptWithDelay=(Button)convertView.findViewById(R.id.acceptDelayButton);
        reject=(Button)convertView.findViewById(R.id.rejectButton);
        row=(TableRow)convertView.findViewById(R.id.rowOne);

        row.setVisibility(View.VISIBLE);

        orderId=list.get(position).getOrder_id();

        orderidView.setText(list.get(position).getOrder_id());
        deliveryView.setText(list.get(position).getDelivery_time());


        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentOrderId=orderId;
                postData("http://medicians.herokuapp.com/update_status1/"+currentOrderId+"/process");
            }
        });

        acceptWithDelay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentOrderId=orderId;
                createDialog();

            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentOrderId=orderId;
                postData("http://medicians.herokuapp.com/update_status/"+currentOrderId+"/cancel");
            }
        });


        //Global button for setting the process,dispatch,attempt


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
                        Log.d("Mytag","Added");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        AppController.getInstance().addToRequestQueue(request);



    }

    private void createDialog(){


        Button done;


        final Dialog dialog=new Dialog(context);
        dialog.setTitle("Delay Time");
        dialog.setContentView(R.layout.delay_time);
        dialog.show();



        final EditText  editText=(EditText)dialog.findViewById(R.id.delayTime);
        done=(Button)dialog.findViewById(R.id.doneB);



        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String timeStr;
                timeStr=editText.getText().toString();
                time=Integer.parseInt(timeStr);

                dialog.cancel();
                postData("http://medicians.herokuapp.com/update_status/" + currentOrderId + "/delay+" + time);
                postData("http://medicians.herokuapp.com/update_status1/"+currentOrderId+"/process");

            }
        });


    }

}
