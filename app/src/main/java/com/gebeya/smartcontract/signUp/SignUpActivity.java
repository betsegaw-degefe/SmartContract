package com.gebeya.smartcontract.signUp;

import android.os.Bundle;

import com.gebeya.framework.base.BaseActivity;
import com.gebeya.smartcontract.R;

public class SignUpActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        bind();
    }
}
