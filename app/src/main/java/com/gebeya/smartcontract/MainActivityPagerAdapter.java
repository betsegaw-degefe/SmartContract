package com.gebeya.smartcontract;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.gebeya.smartcontract.myAsset.MyAssetFragment;
import com.gebeya.smartcontract.profile.ProfileFragment;
import com.gebeya.smartcontract.publicLedger.PublicLedgerFragment;

import java.util.List;

public class MainActivityPagerAdapter extends FragmentPagerAdapter {

    private int numOfTabs;


    public MainActivityPagerAdapter(FragmentManager manager,int numOfTabs) {
        super(manager);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int i) {

        switch (i) {
            case 0:
                return new PublicLedgerFragment();
            case 1:
                return new MyAssetFragment();
            case 2:
                return new ProfileFragment();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return MainActivity.PAGE_COUNT;
    }

    /*@Override
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
    }*/
}
