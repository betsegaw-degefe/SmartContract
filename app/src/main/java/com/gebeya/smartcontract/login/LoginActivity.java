package com.gebeya.smartcontract.login;

import android.content.Intent;
import android.os.Bundle;

import com.gebeya.framework.base.BaseActivity;
import com.gebeya.framework.utils.Api;
import com.gebeya.smartcontract.MainActivity;
import com.gebeya.smartcontract.R;
import com.gebeya.smartcontract.data.model.LoginModel;
import com.gebeya.smartcontract.login.api.LoginService;
import com.gebeya.smartcontract.signUp.api.SignUpService;

import butterknife.BindView;
import butterknife.OnClick;
import customfonts.EditText_SFUI_Regular;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.loginPhoneNumber)
    EditText_SFUI_Regular loginPhoneNumber;

    @BindView(R.id.loginPassword)
    EditText_SFUI_Regular loginPassword;

    private String phoneNumber;
    private String password;

    private LoginService mLoginService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bind();


        mLoginService = Api.loginService();

    }

    @OnClick(R.id.logInButton)
    public void submitLogin(){
        phoneNumber = loginPhoneNumber.getText().toString().trim();
        password = loginPassword.getText().toString().trim();
        toast(phoneNumber);

        mLoginService.loginSubmit("application/json",
              phoneNumber,
              password).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                int statusCode = response.code();
                String statusMessage = response.message();
                statusShow(statusCode, statusMessage);
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {

            }
        });
    }

    private void statusShow(int code, String Message) {
        String statusCode = Integer.toString(code);

        if (code == 200) {
            startActivity(new Intent(this, MainActivity.class));
            this.finish();
        } else {
            toast(Message);
        }
    }
}
