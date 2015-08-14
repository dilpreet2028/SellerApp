package com.medicians.mediciansseller.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.medicians.mediciansseller.Models.Due;
import com.medicians.mediciansseller.R;

import java.util.List;

/**
 * Created by dilpreet on 14/8/15.
 */
public class DueAdapter extends BaseAdapter {

    List<Due> dueList;
    Context context;
    LayoutInflater inflater;
    TextView month,amount,paid,unpaid;
    public DueAdapter(List<Due> dueList,Context context) {
        super();
        this.dueList=dueList;
        this.context=context;
    }

    @Override
    public int getCount() {
        return dueList.size();
    }

    @Override
    public Object getItem(int position) {
        return dueList.get(position);
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
            convertView=inflater.inflate(R.layout.due_payments_item,null);

        month=(TextView)convertView.findViewById(R.id.month);
        amount=(TextView)convertView.findViewById(R.id.amount);
        paid=(TextView)convertView.findViewById(R.id.paid);
        unpaid=(TextView)convertView.findViewById(R.id.unpaid);

        Due currentItem=dueList.get(position);

        month.setText(currentItem.getDate());
        amount.setText(currentItem.getAmount());
        paid.setText(currentItem.getPaid());
        unpaid.setText(currentItem.getUnpaid());

        return convertView;
    }
}
