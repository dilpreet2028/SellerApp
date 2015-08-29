package com.medicians.mediciansseller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.medicians.mediciansseller.Models.NewOrderModel;
import com.medicians.mediciansseller.Tabs.NewOrder;

public class NewOrderDetails extends AppCompatActivity {
    NewOrderModel order;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order_details);
        //order= NewOrder.newOrder;
        Toast.makeText(this,order.getOrder_id()+" "+order.getCommission(),Toast.LENGTH_LONG).show();
    }


}
