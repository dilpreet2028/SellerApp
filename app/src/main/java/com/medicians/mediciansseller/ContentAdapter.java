package com.medicians.mediciansseller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dilpreet on 12/8/15.
 */
public class ContentAdapter extends BaseAdapter{

    List<Content> contentList;
    Context context;
    LayoutInflater inflater;
    TextView company,brandname,category,quantity,selling;

    public ContentAdapter(Context context,List<Content> contentList) {
        super();
        this.contentList=contentList;
        this.context=context;
    }

    @Override
    public int getCount() {
        return contentList.size();
    }

    @Override
    public Object getItem(int position) {
        return contentList.get(position);
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
            convertView=inflater.inflate(R.layout.list_item,null);

        company=(TextView)convertView.findViewById(R.id.company);
        brandname=(TextView)convertView.findViewById(R.id.brandname);
        category=(TextView)convertView.findViewById(R.id.category);
        quantity=(TextView)convertView.findViewById(R.id.quantity);
        selling=(TextView)convertView.findViewById(R.id.selling);

        Content currentItem=contentList.get(position);
        company.setText(currentItem.getCompany());
        brandname.setText(currentItem.getBrandname());
        category.setText(currentItem.getCategory());
        quantity.setText(currentItem.getQuantity()+"");
        selling.setText(currentItem.getSp()+"");

        return convertView;
    }
}
