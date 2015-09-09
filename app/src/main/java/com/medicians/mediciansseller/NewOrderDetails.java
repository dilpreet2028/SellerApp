package com.medicians.mediciansseller;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.medicians.mediciansseller.Models.NewOrderModel;
import com.medicians.mediciansseller.Tabs.NewOrder;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order_details);
        list=new ArrayList<>();
        listView=(ListView)findViewById(R.id.listDetails);

        //orderid=getIntent().getStringExtra("orderid");
        adapter=new OrderAdapter(this);
        listView.setAdapter(adapter);

        getData();
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

    private class Model{
        String brandname;
        String quantity;
        String mrp;

        public String getSp() {
            return sp;
        }

        public void setSp(String sp) {
            this.sp = sp;
        }

        public String getMrp() {
            return mrp;
        }

        public void setMrp(String mrp) {
            this.mrp = mrp;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getBrandname() {
            return brandname;
        }

        public void setBrandname(String brandname) {
            this.brandname = brandname;
        }

        String sp;


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
