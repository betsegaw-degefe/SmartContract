package com.gebeya.smartcontract;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.gebeya.framework.base.BaseActivity;
import com.gebeya.smartcontract.model.data.objectBox.UserLoginData;
import com.gebeya.smartcontract.login.LoginActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.Objects;

import butterknife.BindView;
import io.objectbox.Box;
import io.objectbox.BoxStore;

public class MainActivity extends BaseActivity {

    public static final int PAGE_COUNT = 3;

    @BindView(R.id.mainToolbar)
    Toolbar mToolbar;

    @BindView(R.id.appbar)
    AppBarLayout mAppBarLayout;

    @BindView(R.id.mainViewPager)
    ViewPager mViewPager;

    @BindView(R.id.mainTabs)
    TabLayout mTabLayout;

    String titles[];
    BoxStore userBox;
    Box<UserLoginData> box;

    private int mLastDy;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bind();

        setSupportActionBar(mToolbar);

        titles = getResources().getStringArray(R.array.tab_titles);

        // Getting the FireBase token
        FirebaseInstanceId.getInstance().getInstanceId()
              .addOnSuccessListener(this, new OnSuccessListener<InstanceIdResult>() {
                  @Override
                  public void onSuccess(InstanceIdResult instanceIdResult) {
                      String newToken = instanceIdResult.getToken();
                      d(newToken);
                  }
              });

        // Display icon in the toolbar
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        //getSupportActionBar().setDisplayUseLogoEnabled(true);


        // Retrieve the Box for the UserLogin
        userBox = ((App) getApplication()).getStore();
        box = userBox.boxFor(UserLoginData.class);


        // tab icons
        int[] tabIcons = {
              R.drawable.ic_public_ledger_tab_icon,
              R.drawable.ic_my_asset_tab_icon,
              R.drawable.ic_user_profile
        };

        MainActivityPagerAdapter mAdapter =
              new MainActivityPagerAdapter(getSupportFragmentManager(), mTabLayout.getTabCount());

        mViewPager.setAdapter(mAdapter);


        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        Objects.requireNonNull(mTabLayout.getTabAt(0)).setIcon(tabIcons[0]);
        Objects.requireNonNull(mTabLayout.getTabAt(1)).setIcon(tabIcons[1]);
        Objects.requireNonNull(mTabLayout.getTabAt(2)).setIcon(tabIcons[2]);

        // Set the tab color to ruby by default.
        int tabIconColor = ContextCompat.getColor(MainActivity.this, R.color.colorAccent);
        Objects.requireNonNull(Objects.requireNonNull(mTabLayout.getTabAt(0)).getIcon()).setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabIconColor = ContextCompat.getColor(MainActivity.this, R.color.colorAccent);
                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                mViewPager.setCurrentItem(tab.getPosition());
                int tabAt = tab.getPosition();
                mToolbar.setTitle(titles[tabAt]);
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

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

   /* @Override
    public void onPageSelected(int i) {
        d("Current page:  " + i);
        String title = titles[i];


        //titleLabel.setText(title);
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // if Sign out selected
        if (id == R.id.signOutToolBar) {
            toast("Sign out menu");
            // Toast.makeText(getApplicationContext(), "Setting Menu", Toast.LENGTH_SHORT).show();
            box.removeAll();
            startActivity(new Intent(this, LoginActivity.class));
            this.finish();
            return true;
        }
        // If search option selected
        else if (id == R.id.searchToolBar) {
            toast("Search tool bar selected");
            //Toast.makeText(getApplicationContext(), "Search Menu", Toast.LENGTH_SHORT).show();
            return true;
        }
        // If notification option selected.
        else if (id == R.id.notificationToolbar) {
            toast("Notification tool bar selected");
            //Toast.makeText(getApplicationContext(), "User Menu", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onScrollStateChanged(final RecyclerView recyclerView, final int newState) {
        switch (newState) {
            case RecyclerView.SCROLL_STATE_IDLE:
                mAppBarLayout.setExpanded(mLastDy <= 0, true);
                mLastDy = 0;
                break;
        }
    }

    public void onScrolled(final RecyclerView recyclerView, final int dx, final int dy) {
        mLastDy = dy == 0 ? mLastDy : dy;
    }
}
