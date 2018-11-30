package com.gebeya.smartcontract;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class MainActivityPagerAdapter extends FragmentPagerAdapter {

    List<Fragment> mFragments;

    public MainActivityPagerAdapter(FragmentManager manager,List<Fragment> fragments) {
        super(manager);
        this.mFragments = fragments;
    }

    @Override
    public Fragment getItem(int i) {
        /*switch (i){
            case 0:
                return MainActivityFragment.newInstance(i);
            case 1:
                return MainActivityFragment.newInstance(i);
            default:
                return MainActivityFragment.newInstance(i);
        }*/

       return mFragments.get(i);
    }

    @Override
    public int getCount() {
        return MainActivity.PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Public Ledger";
            case 1:
                return "My Asset";
            case 2:
                return "About us";
        }
        return null;
    }
}
