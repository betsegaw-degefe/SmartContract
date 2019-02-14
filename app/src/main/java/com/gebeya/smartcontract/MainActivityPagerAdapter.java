package com.gebeya.smartcontract;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.gebeya.smartcontract.myAsset.MyAssetFragment;
import com.gebeya.smartcontract.profile.ProfileFragment;
import com.gebeya.smartcontract.view.aboutUs.AboutUsFragment;
import com.gebeya.smartcontract.view.publicLedger.PublicLedgerFragment;

public class MainActivityPagerAdapter extends FragmentPagerAdapter {

    private int numOfTabs;


    MainActivityPagerAdapter(FragmentManager manager, int numOfTabs) {
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
            case 3:
                return new AboutUsFragment();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return numOfTabs;
    }

}
