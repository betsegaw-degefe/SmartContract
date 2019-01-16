package com.gebeya.smartcontract.changePassword;

import android.os.Bundle;

import com.gebeya.framework.base.BaseActivity;
import com.gebeya.smartcontract.R;

import butterknife.BindView;
import customfonts.Button_sfuitext_regular;
import customfonts.EditText_SFUI_Regular;

public class ChangePasswordActivity extends BaseActivity {

    @BindView(R.id.currentPassword)
    EditText_SFUI_Regular oldPassword;

    @BindView(R.id.newPassword)
    EditText_SFUI_Regular newPassword;

    @BindView(R.id.submitChangePasswordButton)
    Button_sfuitext_regular changePasswordBtn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        bind();
    }
}
