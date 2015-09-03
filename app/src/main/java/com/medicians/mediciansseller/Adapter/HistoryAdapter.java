package com.medicians.mediciansseller.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.medicians.mediciansseller.Models.History;
import com.medicians.mediciansseller.R;

import java.util.List;

/**
 * Created by dilpreet on 3/9/15.
 */
public class HistoryAdapter extends BaseAdapter {
    List<History> list;
    LayoutInflater inflater;
    Context context;
    TextView orderId,orderDate,orderTime,status;
    public HistoryAdapter(Context context,List<History> list) {
        super();
        this.context=context;
        this.list=list;
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
            convertView=inflater.inflate(R.layout.order_history_item,null);

        orderId=(TextView)convertView.findViewById(R.id.orderIdH);
        orderDate=(TextView)convertView.findViewById(R.id.orderDateH);
        orderTime=(TextView)convertView.findViewById(R.id.orderTimeH);
        status=(TextView)convertView.findViewById(R.id.statusH);

        History current=list.get(position);

        orderId.setText(current.getOrder());
        orderTime.setText(current.getTime());
        orderDate.setText(current.getDate());
        status.setText(current.getStatus());
        return convertView;
    }
}
