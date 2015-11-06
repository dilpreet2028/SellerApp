package com.medicians.mediciansseller.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.medicians.mediciansseller.Models.Stocks;
import com.medicians.mediciansseller.R;

import java.util.List;

/**
 * Created by dilpreet on 5/11/15.
 */
public class StocksAdapter extends BaseAdapter {
    List<Stocks> list;
    LayoutInflater inflater;
    TextView nameText,mrpText,spText,qtyText,statusText;
    Context context;
    LinearLayout layout;
    int index;
    public StocksAdapter(List<Stocks> list,Context context,int index) {
        super();
        this.list=list;
        this.context=context;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater==null)
            inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;
        if(convertView==null)
        {
            convertView=inflater.inflate(R.layout.stock_item,null);
        }
        nameText=(TextView)convertView.findViewById(R.id.nameText);
        mrpText=(TextView)convertView.findViewById(R.id.mrpText);
        spText=(TextView)convertView.findViewById(R.id.spText);
        qtyText=(TextView)convertView.findViewById(R.id.quantityText);
        statusText=(TextView)convertView.findViewById(R.id.statusText);
        layout=(LinearLayout)convertView.findViewById(R.id.rowList);

        Stocks currentStock=list.get(position);
        String status=currentStock.getStatus();
        int quanitity=currentStock.getQuantity();
        nameText.setText(currentStock.getName());
        mrpText.setText("MRP: "+currentStock.getMrp());
        spText.setText("SP: "+currentStock.getSp());
        qtyText.setText("Quantity :"+quanitity+"");
        statusText.setText(status);
        if (index == 0 && status.compareToIgnoreCase("active") == 0 && quanitity > 0)
            layout.setVisibility(View.GONE);
        else if (index == 1 && quanitity == 0)
            statusText.setVisibility(View.GONE);
        else if (index == 2 && status.compareToIgnoreCase("inactive") == 0)
            qtyText.setVisibility(View.GONE);

        return convertView;
    }
}
