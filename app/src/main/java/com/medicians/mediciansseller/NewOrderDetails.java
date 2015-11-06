package com.medicians.mediciansseller;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.medicians.mediciansseller.Adapter.AttemptAdapter;
import com.medicians.mediciansseller.Models.Model;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class NewOrderDetails extends AppCompatActivity {

    List<Model> list;
    String orderid="a";
    ListView listView;
    OrderAdapter adapter;
    LinearLayout row1,row2,row3;
    int flag,time;
    String status,status1;
    RadioGroup radioGroup;
    Button setGlobal,rejectGlobal,acceptButton,delay,reject,compelete,attempt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order_details);
        list=new ArrayList<>();
        listView=(ListView)findViewById(R.id.listDetails);
        row1=(LinearLayout)findViewById(R.id.row1);
        row2=(LinearLayout)findViewById(R.id.row2);
        row3=(LinearLayout)findViewById(R.id.row3);


        acceptButton=(Button)findViewById(R.id.acceptButton);
        delay=(Button)findViewById(R.id.acceptDelayButton);
        reject=(Button)findViewById(R.id.rejectButtonA);

        setGlobal=(Button)findViewById(R.id.setGlobalDetails);
        rejectGlobal=(Button)findViewById(R.id.rejectGlobalDetails);

        compelete=(Button)findViewById(R.id.compeleteOrder);
        attempt=(Button)findViewById(R.id.attemptOrder);

        flag=getIntent().getIntExtra("flag", 0);
        status1=getIntent().getStringExtra("status");
        if(flag==0)
        {
            row1.setVisibility(View.VISIBLE);
        }

        if(flag==1)
        {
            status = "Processed";
            row2.setVisibility(View.VISIBLE);
            setGlobal.setText("Ready to dispatch");
        }


        if(flag==2)
        {status = "Dispatch";
            row2.setVisibility(View.VISIBLE);
            setGlobal.setText("Dispatch");
        }

        if(flag==3)
        { status="Attempt";
            row3.setVisibility(View.VISIBLE);
        }



        orderid=getIntent().getStringExtra("orderid");
        adapter=new OrderAdapter(this);
        listView.setAdapter(adapter);

        getData();
       }

    public void accept(View view){
        postData("http://medicians.herokuapp.com/update_status/" + orderid+"/Accept");
        postData("http://medicians.herokuapp.com/update_status1/" + orderid+"/Accept");
        finish();
    }
    public void delay(View view){
        createDelayDialog();
    }
    public void rejectOne(View view){
        postData("http://medicians.herokuapp.com/update_status/" + orderid + "/Cancel");
        postData("http://medicians.herokuapp.com/update_status1/" + orderid + "/Cancel");
        finish();
    }
    public void setIt(View view){
        postData("http://medicians.herokuapp.com/update_status1/"+orderid + "/" + status);
        finish();
    }
    public void rejectTwo(View view){
        postData("http://medicians.herokuapp.com/update_status/"+orderid+"/Cancel");
        finish();
    }
    public void compelete(View view){
        postData("http://medicians.herokuapp.com/update_status1/" + orderid + "/Complete");
        finish();
    }
    public void attempt(View view){
        createAttemptDialog();
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
                        Log.d("mytag",error+ "");
                    }
                });

        AppController.getInstance().addToRequestQueue(request);

        Log.d("mytag", "outside");

    }

    private void createDelayDialog(){


        Button done;


        final Dialog dialog=new Dialog(this);
        dialog.setTitle("Delay Time");
        dialog.setContentView(R.layout.delay_time);
        dialog.show();



        final EditText editText=(EditText)dialog.findViewById(R.id.delayTime);
        done=(Button)dialog.findViewById(R.id.doneB);



        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String timeStr;
                timeStr = editText.getText().toString();
                time = Integer.parseInt(timeStr);


                postData("http://medicians.herokuapp.com/update_status/" + orderid +"/Accept_delay(+" + time+")");
                postData("http://medicians.herokuapp.com/update_status1/" + orderid + "/Accept");
                dialog.dismiss();
                finish();

            }
        });


    }

    private void createAttemptDialog() {


        final RadioButton reasonOther;
        final EditText reasonText;
        Button submitB;
        final Dialog dialog = new Dialog(NewOrderDetails.this);
        dialog.setContentView(R.layout.attempt_dialog);
        dialog.show();

        reasonOther = (RadioButton) dialog.findViewById(R.id.otherReason);
        radioGroup = (RadioGroup) dialog.findViewById(R.id.radioGroup);
        reasonText = (EditText) dialog.findViewById(R.id.reasonText);
        submitB = (Button) dialog.findViewById(R.id.submitRadio);


        submitB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = radioGroup.getCheckedRadioButtonId();


                String reason = "";
                if (R.id.radioButton == id) {
                    reason = "One";
                    if (check(status1))
                        postData("http://medicians.herokuapp.com/update_status1/" + orderid +"/Attempt_1("+reason+")");
                    else
                        postData("http://medicians.herokuapp.com/update_status1/" + orderid + "/Cancel");
                } else if (R.id.radioButton2 == id) {
                    reason = "Two";
                    if (check(status1))
                        postData("http://medicians.herokuapp.com/update_status1/" + orderid +"/Attempt_1("+reason+")");
                    else
                        postData("http://medicians.herokuapp.com/update_status1/" + orderid + "/Cancel");

                } else if (R.id.otherReason == id) {
                    reason = reasonText.getText().toString();
                    if (check(status1))
                        postData("http://medicians.herokuapp.com/update_status1/" + orderid + "/Attempt_1("+reason+")");
                    else
                        postData("http://medicians.herokuapp.com/update_status1/" + orderid + "/Cancel");

                } else {
                    Toast.makeText(getApplicationContext(), "Please choose a reason for attempt", Toast.LENGTH_LONG).show();
                }

                dialog.dismiss();
                finish();
            }
        });
    }
            private boolean check(String status){
                boolean res=true;
                if(status.compareToIgnoreCase("Attempt_1")==0)
                    return false;
                return res;
            }


    private void getData(){
        String url="http://medicians.herokuapp.com/particular_orderdetails/"+orderid;
        StringRequest request=new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseData(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error+"",Toast.LENGTH_SHORT).show();
                    }
                });
        AppController.getInstance().addToRequestQueue(request);
    }

    private void parseData(String json){
        JSONArray array;
        try{
            array=new JSONArray(json);
            JSONObject object;
            for(int i=0;i<array.length();i++){
                Model item=new Model();
                object=array.getJSONObject(i);
                item.setBrandname(object.getString("brandname"));
                item.setMrp(object.getString("mrp"));
                item.setQuantity(object.getString("quantity"));
                item.setSp(object.getString("sp"));

                list.add(item);
            }

            adapter.notifyDataSetChanged();
        }
        catch (Exception e){

        }

    }



    private class OrderAdapter extends BaseAdapter{
        Context context;
        TextView brandname,quantity,sp,mrp;
        LayoutInflater inflater;
        public OrderAdapter(Context context) {
            super();
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
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(inflater==null)
                inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if(convertView==null)
                convertView=inflater.inflate(R.layout.order_item,null);

            brandname=(TextView)convertView.findViewById(R.id.brandnameDetails);
            quantity=(TextView)convertView.findViewById(R.id.quantityDetails);
            mrp=(TextView)convertView.findViewById(R.id.mrpDetails);
            sp=(TextView)convertView.findViewById(R.id.spDetails);

            Model currentItem=list.get(position);
            brandname.setText(currentItem.getBrandname());
            mrp.setText(currentItem.getMrp());
            sp.setText(currentItem.getSp());
            quantity.setText(currentItem.getMrp());

            return convertView;
        }
    }

}
