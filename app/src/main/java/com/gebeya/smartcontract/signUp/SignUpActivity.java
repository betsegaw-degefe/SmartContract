package com.gebeya.smartcontract.signUp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.gebeya.framework.base.BaseActivity;
import com.gebeya.framework.utils.Api;
import com.gebeya.smartcontract.R;
import com.gebeya.smartcontract.model.data.model.SignUpModel;
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

    @BindView(R.id.confirmPassword)
    EditText_Roboto_Regular mConfirmPassword;

    Animation shake;

    private SignUpService mSignUpService;
    private String firstName;
    private String lastName;
    private String password;
    private String confirmPasswrd;
    private String phoneNo;
    private String verificationCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        bind();

        // Extracting the phone number which is passed from sendPhoneNumberActivity.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            phoneNo = intent.getExtras().getString("PHONE_NUMBER");
        }

        // Initialize an instance of the SendPhoneNumberService interface
        mSignUpService = Api.signUpService();

        // create instance of animation for editText
        shake = AnimationUtils.loadAnimation(SignUpActivity.this, R.anim.shake);

    }

    @OnClick(R.id.signUpButton)
    public void submitSignUp() {
        firstName = mFirstName.getText().toString().trim();
        if (TextUtils.isEmpty(firstName)) {
            mFirstName.setError(getString(R.string.sign_up_first_name_hint));
            // shake the phone number edit text.
            mFirstName.startAnimation(shake);
            return;
        }
        lastName = mLastName.getText().toString().trim();
        if (TextUtils.isEmpty(lastName)) {
            mLastName.setError(getString(R.string.sign_up_last_name_hint));
            // shake the phone number edit text.
            mLastName.startAnimation(shake);
            return;
        }
        password = mPassword.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            mPassword.setError(getString(R.string.sign_up_password_hint));
            // shake the phone number edit text.
            mPassword.startAnimation(shake);
            return;
        }
        verificationCode = mVerificationCode.getText().toString().trim();
        if (TextUtils.isEmpty(verificationCode)) {
            mVerificationCode.setError(getString(R.string.sign_up_verification_code_hint));
            // shake the phone number edit text.
            mVerificationCode.startAnimation(shake);
            return;
        }
        confirmPasswrd = mConfirmPassword.getText().toString().trim();

        // Check whether the password match or not
        if (TextUtils.isEmpty(confirmPasswrd)) {
            mConfirmPassword.setError(getString(R.string.sign_up_password_confirm_hint));
            // shake the phone number edit text.
            mConfirmPassword.startAnimation(shake);
            return;
        }

        if (!password.equals(confirmPasswrd)) {
            mConfirmPassword.setError(getString(R.string.sign_up_password_match_hint));
            // shake the phone number edit text.
            mConfirmPassword.startAnimation(shake);
            return;
        }

        //toast(phoneNo);
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
