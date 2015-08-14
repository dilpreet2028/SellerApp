package com.medicians.mediciansseller.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.medicians.mediciansseller.Models.Payments;
import com.medicians.mediciansseller.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by dilpreet on 14/8/15.
 */
public class PaymentAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
    List<Payments> paymentsList;
    TextView amount,date;
    public PaymentAdapter(Context context,List<Payments> paymentsList) {
        super();
        this.context=context;
        this.paymentsList=paymentsList;
    }

    @Override
    public int getCount() {
        return paymentsList.size();
    }

    @Override
    public Object getItem(int position) {
        return paymentsList.get(position);
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
            convertView=inflater.inflate(R.layout.payment_item,null);

        date=(TextView)convertView.findViewById(R.id.datePaid);
        amount=(TextView)convertView.findViewById(R.id.amountPaid);

        Payments current=paymentsList.get(position);


        date.setText(current.getDate());
        amount.setText(current.getAmount());



        return convertView;
    }
}
