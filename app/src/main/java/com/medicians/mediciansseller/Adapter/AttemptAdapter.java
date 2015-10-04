package com.medicians.mediciansseller.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.medicians.mediciansseller.AppController;
import com.medicians.mediciansseller.Models.NewOrderModel;
import com.medicians.mediciansseller.PopulateList;
import com.medicians.mediciansseller.R;

import java.util.List;

/**
 * Created by dilpreet on 24/8/15.
 */
public class AttemptAdapter extends BaseAdapter {

    List<NewOrderModel> list;
    Context context;
    TextView orderidView,deliveryView;
    LayoutInflater inflater;
    Button attempt,acceptFinal;
    TableRow row;
    String orderId;
    RadioGroup radioGroup;
    public static String status;
    int index;

    public AttemptAdapter(Context context,List<NewOrderModel> list,int index) {
        super();
        this.list = list;
        this.context = context;
        this.index=index;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(inflater==null)
            inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView==null)
            convertView=inflater.inflate(R.layout.new_order_item,null);

        attempt=(Button)convertView.findViewById(R.id.attempt);
        acceptFinal=(Button)convertView.findViewById(R.id.acceptFinal);
        orderidView=(TextView)convertView.findViewById(R.id.orderId);
        deliveryView=(TextView)convertView.findViewById(R.id.deliveryTime);
        row=(TableRow)convertView.findViewById(R.id.rowThree);


        acceptFinal=(Button)convertView.findViewById(R.id.acceptFinal);
        attempt=(Button)convertView.findViewById(R.id.attempt);
        //Log.d()






        if(index==1)
            row.setVisibility(View.VISIBLE);

        orderidView.setText(list.get(position).getOrder_id());
        deliveryView.setText(list.get(position).getDelivery_time());


        acceptFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderId=list.get(position).getOrder_id();
                postData("http://medicians.herokuapp.com/update_status1/" + orderId + "/compeleted");
            }
        });

        attempt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderId=list.get(position).getOrder_id();
                status=list.get(position).getStatus1();
                Log.d("mytag","attempt :"+status);
                createDialog();
            }
        });
        return convertView;
    }

    public static String selectStatus(String old){
        String newStr="";

        if(old.compareToIgnoreCase("attempt_1")==0)
        {   Log.d("mytag","oldL "+old);
            newStr="attempt_2";
        }
        else  if(old.compareToIgnoreCase("attempt")==0)
        {   Log.d("mytag","old: "+old);
            newStr="attempt_1";
        }

        Log.d("mytag","after: "+newStr);
        return newStr;
    }

    private void createDialog(){
        final RadioButton reasonOne,reasonTwo,reasonOther;
        final EditText reasonText;
        Button submitB;
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.attempt_dialog);
        dialog.show();

        //reasonOne=(RadioButton)dialog.findViewById(R.id.radioButton);
        //reasonTwo=(RadioButton)dialog.findViewById(R.id.radioButton2);
        reasonOther=(RadioButton)dialog.findViewById(R.id.otherReason);
        radioGroup=(RadioGroup)dialog.findViewById(R.id.radioGroup);
        reasonText=(EditText)dialog.findViewById(R.id.reasonText);
        submitB=(Button)dialog.findViewById(R.id.submitRadio);




        submitB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id=radioGroup.getCheckedRadioButtonId();

                Log.d("Mytag","Before: "+status);
                String reason="";
                if(R.id.radioButton==id)
                {
                    reason="One";
                    postData("http://medicians.herokuapp.com/update_status1/"+orderId+"/"+selectStatus(status)+"+"+reason);
                }
                else if(R.id.radioButton2==id)
                {
                    reason="Two";
                    postData("http://medicians.herokuapp.com/update_status1/"+orderId+"/"+selectStatus(status)+"+"+reason);
                }
                else if(R.id.otherReason==id){
                    reason=reasonText.getText().toString();
                    postData("http://medicians.herokuapp.com/update_status1/"+orderId+"/"+selectStatus(status)+"+"+reason);
                }

                else{
                    Toast.makeText(context,"Please choose a reason for attempt", Toast.LENGTH_LONG).show();
                }


                dialog.dismiss();


                if(status.compareToIgnoreCase("attempt_2")==0){
                    Toast.makeText(context,"The order has been cancelled due to two attempts", Toast.LENGTH_LONG).show();
                    postData("http://medicians.herokuapp.com/update_status/" + orderId + "/cancel");
                }

            }
        });

        reasonOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reasonText.setVisibility(View.VISIBLE);
            }
        });




    }


    public void postData(String url){




        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Mytag", "Added");
                refreshList();

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("mytag",error+"");
                    }
                });

        AppController.getInstance().addToRequestQueue(request);


    }


    private void refreshList(){


            PopulateList testClass = new PopulateList(context, "http://medicians.herokuapp.com/sellerorderinfo/1/attempt", 3);
            testClass.getData();



    }


}

