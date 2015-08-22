package com.medicians.mediciansseller.NavigationTabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.medicians.mediciansseller.R;
import com.medicians.mediciansseller.Tabs.*;
import com.medicians.mediciansseller.Tabs.Process;
import com.medicians.mediciansseller.slidingtab.SlidingTabLayout;

/**
 * Created by dilpreet on 10/8/15.
 */
public class SwipeTabs extends Fragment {
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    SlidingTabLayout slidingTabLayout;
    public static int flag;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.swipe_tabs,container,false);
        viewPager=(ViewPager)view.findViewById(R.id.viewPager);
        slidingTabLayout=(SlidingTabLayout)view.findViewById(R.id.tabs);
        viewPagerAdapter=new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        viewPager.setOffscreenPageLimit(0);

        slidingTabLayout.setDistributeEvenly(false);
        viewPager.setAdapter(viewPagerAdapter);
        slidingTabLayout.setViewPager(viewPager);
        return view;
    }


    private class ViewPagerAdapter extends FragmentPagerAdapter {
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position==0) {
                flag=0;
                return new NewOrder();
            }
            if (position==1) {
                flag=1;
                return new Process();
            }
            if (position==2) {
                flag=2;
                return new Dispatched();
            }

            if (position==3) {
                flag=2;
                return new Attempt();
            }
            if (position==4) {
                flag=2;
                return new Delivered();
            }
            if (position==5) {
                flag=2;
                return new Delivered();
            }

            return null;
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position==0)
                return "New Order";
            if (position==1)
                return "Process";
            if (position==2)
                return "Dispatch";
            if (position==3)
                return "Attempt";
            if (position==4)
                return "Compeleted";
            if (position==5)
                return "Cancelled";
            return super.getPageTitle(position);
        }
    }


}
