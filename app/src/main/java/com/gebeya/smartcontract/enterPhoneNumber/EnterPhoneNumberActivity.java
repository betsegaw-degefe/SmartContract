package com.gebeya.smartcontract.enterPhoneNumber;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.gebeya.framework.base.BaseActivity;
import com.gebeya.smartcontract.MainActivity;
import com.gebeya.smartcontract.R;

import butterknife.BindView;

public class EnterPhoneNumberActivity extends BaseActivity {

    @BindView(R.id.enterPhoneNumberToolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_phone_number);

        setSupportActionBar(mToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.enterphonenumber, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // submit users number to back end
        int id = item.getItemId();

        if (id == R.id.phone_number_submit) {
            toast("Action clicked");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
