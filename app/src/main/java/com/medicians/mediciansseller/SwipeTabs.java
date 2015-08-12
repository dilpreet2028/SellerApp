package com.medicians.mediciansseller;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

        slidingTabLayout.setDistributeEvenly(true);
        viewPager.setAdapter(viewPagerAdapter);
        slidingTabLayout.setViewPager(viewPager);
        return view;
    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter{
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position==0) {
                flag=0;
                return new TabOne();
            }
            if (position==1) {
                flag=1;
                return new TabOne();
            }
            if (position==2) {
                flag=2;
                return new TabOne();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position==0)
                return "Accepted";
            if (position==1)
                return "Dispatched";
            if (position==2)
                return "Delivered";
            return super.getPageTitle(position);
        }
    }


}
