package com.gebeya.smartcontract.view.publicLedger;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.gebeya.framework.base.BaseActivity;
import com.gebeya.app.App;
import com.gebeya.smartcontract.view.login.LoginActivity;
import com.gebeya.smartcontract.model.data.objectBox.UserLoginData;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class SignOut extends BaseActivity {

    BoxStore userBox;
    Box<UserLoginData> box;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userBox = ((App) getApplication()).getStore();
        box = userBox.boxFor(UserLoginData.class);
        box.removeAll();
        startActivity(new Intent(this, LoginActivity.class));
        this.finish();
    }
}
