package com.medicians.mediciansseller;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.medicians.mediciansseller.Adapter.StocksAdapter;
import com.medicians.mediciansseller.Models.Stocks;
import com.medicians.mediciansseller.Tabs.MyStocksFragment;
import com.medicians.mediciansseller.slidingtab.SlidingTabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Listing extends Fragment{

    View view;
    ViewPager viewPager;
    MyAdapter adapter;
    SlidingTabLayout slidingTabLayout;
    List<Stocks> stocksList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_stocks,null);

        slidingTabLayout=(SlidingTabLayout)view.findViewById(R.id.tabs);
        viewPager=(ViewPager)view.findViewById(R.id.viewPager);
        adapter=new MyAdapter(getActivity().getSupportFragmentManager());
        slidingTabLayout.setDistributeEvenly(true);
        viewPager.setAdapter(adapter);
        slidingTabLayout.setViewPager(viewPager);
        stocksList=new ArrayList<>();
        return view;
    }


    private class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment= new MyStocksFragment();
            Bundle bundle=new Bundle();
            if(position==0)
                bundle.putInt("key",0);
            if(position==1)
                bundle.putInt("key",1);
            if(position==2)
                bundle.putInt("key",2);
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String title="";
            if(position==0)
                title="In Stock";
            if(position==1)
                title="Out of Stock";
            if(position==2)
                title="InActive";
            return title;
        }
    }
}
