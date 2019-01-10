package com.gebeya.smartcontract.splashScreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.gebeya.framework.base.BaseActivity;
import com.gebeya.smartcontract.App;
import com.gebeya.smartcontract.MainActivity;
import com.gebeya.smartcontract.data.objectBox.UserLoginData;
import com.gebeya.smartcontract.login.LoginActivity;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class SplashScreenActivity extends AppCompatActivity {

    BoxStore userBox;
    Box<UserLoginData> box;
    boolean boxStoreCheck;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Login activity intent.
        Intent loginIntent = new Intent(this, LoginActivity.class);

        // Main Activity intent
        Intent mainIntent = new Intent(this,MainActivity.class);

        // Retrieve the Box for the UserLogin
        userBox = ((App) getApplication()).getStore();
        box = userBox.boxFor(UserLoginData.class);

        boxStoreCheck = box.isEmpty();
        if (box.isEmpty()) {
            startActivity(loginIntent);
            finish();
        } else{
            startActivity(mainIntent);
            finish();
        }
    }
}
