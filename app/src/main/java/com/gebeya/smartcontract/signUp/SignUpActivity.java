package com.gebeya.smartcontract.signUp;

import android.content.Intent;
import android.os.Bundle;

import com.gebeya.framework.base.BaseActivity;
import com.gebeya.framework.utils.Api;
import com.gebeya.smartcontract.R;
import com.gebeya.smartcontract.data.model.SignUpModel;
import com.gebeya.smartcontract.login.LoginActivity;
import com.gebeya.smartcontract.signUp.api.SignUpService;

import butterknife.BindView;
import butterknife.OnClick;
import customfonts.EditText_Roboto_Regular;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends BaseActivity {

    // first name from signUp screen
    @BindView(R.id.firstName)
    EditText_Roboto_Regular mFirstName;

    // Last name from signUp screen
    @BindView(R.id.lastName)
    EditText_Roboto_Regular mLastName;

    // Password from signUp screen
    @BindView(R.id.password)
    EditText_Roboto_Regular mPassword;

    // Verification code
    @BindView(R.id.verificationCode)
    EditText_Roboto_Regular mVerificationCode;

    private SignUpService mSignUpService;
    private String firstName;
    private String lastName;
    private String password;
    private String phoneNo;
    private String verificationCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        bind();
        Intent intent = getIntent();

        Bundle extras = intent.getExtras();

        if (extras!= null) {

            phoneNo = intent.getExtras().getString("PHONE_NUMBER");
            toast(phoneNo);
        }

        // Initialize an instance of the SendPhoneNumberService interface
        mSignUpService = Api.signUpService();

    }

    @OnClick(R.id.signUpButton)
    public void submitSignUp() {
        firstName = mFirstName.getText().toString().trim();
        lastName = mLastName.getText().toString().trim();
        password = mPassword.getText().toString().trim();
        verificationCode = mVerificationCode.getText().toString().trim();
        toast(phoneNo);
        mSignUpService.SignUp("application/json",
              firstName,
              lastName,
              phoneNo,
              password,
              verificationCode).enqueue(new Callback<SignUpModel>() {
            @Override
            public void onResponse(Call<SignUpModel> call, Response<SignUpModel> response) {
                int statusCode = response.code();
                String statusMessage = response.message();
                statusShow(statusCode, statusMessage);
            }

            @Override
            public void onFailure(Call<SignUpModel> call, Throwable t) {

            }
        });
    }

    private void statusShow(int code, String Message) {
        String statusCode = Integer.toString(code);

        if (code == 201) {
            startActivity(new Intent(this, LoginActivity.class));
            this.finish();
        } else {
            toast(Message);
        }
    }
}
