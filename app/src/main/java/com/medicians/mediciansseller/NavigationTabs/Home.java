package com.medicians.mediciansseller.NavigationTabs;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.medicians.mediciansseller.AppController;
import com.medicians.mediciansseller.MainActivity;
import com.medicians.mediciansseller.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dilpreet on 7/9/15.
 */
public class Home extends Fragment {

    int length,available=0,outofstock=0,inactive=0;
    ProgressBar progressBar;
    TextView textView,newOrdersText,statusText,totalText;
    ProgressDialog progressDialog;
    CardView cardView,cardList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.home_layout,null);
        available=0;
        progressBar=(ProgressBar)view.findViewById(R.id.progressBarHome);
        textView=(TextView)view.findViewById(R.id.out_of_stock);
        newOrdersText=(TextView)view.findViewById(R.id.new_order);
        cardView=(CardView)view.findViewById(R.id.card2);
        cardList=(CardView)view.findViewById(R.id.card1);
        totalText=(TextView)view.findViewById(R.id.total_stock);
        statusText=(TextView)view.findViewById(R.id.item_status);
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading..");
        progressDialog.show();
        progressBar.setVisibility(View.GONE);
      //  progressBar.setIndeterminate(false);
    //    progressBar.setMax(100);
        getQuantityData();
        getNewData();

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.displayFragment(1);
            }
        });

        cardList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.displayFragment(5);
            }
        });
        return view;
    }

    private void getNewData(){
        String url="http://medicians.herokuapp.com/sellerorder/"+MainActivity.id+"/New";
        StringRequest request=new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int newOrders=0;
                        JSONArray array;

                        try{
                            array=new JSONArray(response);
                            newOrders=array.length();
                            newOrdersText.setText("You have "+newOrders+" new Orders");

                        }catch (Exception e){

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        AppController.getInstance().addToRequestQueue(request);
    }

    private void getQuantityData(){
        String url="http://medicians.herokuapp.com/seller_outofstock";
        StringRequest request=new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONArray array;
                        int t;
                        String status;
                        try{
                            array=new JSONArray(response);
                            length=array.length();

                            JSONObject object;

                            for(int i=0;i<array.length();i++){
                                object= array.getJSONObject(i);
                                t=Integer.parseInt(object.getString("quantity"));
                                Log.d("mytag",t+"");
                                if(t>0)
                                    available++;
                                status=object.getString("status");
                                if(status.compareToIgnoreCase("Inactive")==0)
                                    inactive++;

                            }


                         //   progressBar.setProgress((int)(available*100)/length);
                            textView.setText("Out of Stock :"+(length-available)+" ");
                            statusText.setText("Inactive: "+inactive);
                            totalText.setText("Total Stocks "+length);
                            progressDialog.dismiss();
                        }catch (Exception e){

                            Toast.makeText(getActivity(),"Error  in:"+e,Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),"Error :"+error,Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put("id",MainActivity.id);
                return map;
            }
        };

        AppController.getInstance().addToRequestQueue(request);
    }


}
