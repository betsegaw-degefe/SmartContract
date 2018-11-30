package com.gebeya.smartcontract;

import android.graphics.PorterDuff;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.gebeya.framework.base.BaseActivity;
import com.gebeya.smartcontract.publicLedger.PublicLedgerFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    public static final int PAGE_COUNT = 3;

    @BindView(R.id.mainToolbar)
    Toolbar mToolbar;

    @BindView(R.id.mainViewPager)
    ViewPager mViewPager;

    @BindView(R.id.mainTabs)
    TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bind();

        setSupportActionBar(mToolbar);

        // tab icons
        int[] tabIcons = {
              R.drawable.ic_public_ledger,
              R.drawable.ic_my_assets,
              R.drawable.ic_notification
        };

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new PublicLedgerFragment());
        fragments.add(new PublicLedgerFragment());
        fragments.add(new PublicLedgerFragment());

        MainActivityPagerAdapter mAdapter =
              new MainActivityPagerAdapter(getSupportFragmentManager(), fragments);

        mViewPager.setAdapter(mAdapter);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        int count = mTabLayout.getTabCount();

        mTabLayout.getTabAt(0).setIcon(tabIcons[0]);
        mTabLayout.getTabAt(1).setIcon(tabIcons[1]);
        mTabLayout.getTabAt(2).setIcon(tabIcons[2]);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabIconColor = ContextCompat.getColor(MainActivity.this, R.color.colorAccent);
                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int tabIconColor = ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark);
                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

}
