package com.gebeya.smartcontract.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.gebeya.framework.base.BaseActivity;
import com.gebeya.framework.utils.Api;
import com.gebeya.framework.utils.ErrorUtils;
import com.gebeya.smartcontract.App;
import com.gebeya.smartcontract.MainActivity;
import com.gebeya.smartcontract.R;
import com.gebeya.smartcontract.data.dto.ErrorResponseDTO;
import com.gebeya.smartcontract.data.dto.PublicLedgerResponseDTO;
import com.gebeya.smartcontract.data.dto.UserDTO;
import com.gebeya.smartcontract.data.dto.UserResponseDTO;
import com.gebeya.smartcontract.data.model.LoginModel;
import com.gebeya.smartcontract.data.objectBox.UserLoginData;
import com.gebeya.smartcontract.login.api.LoginService;
import com.gebeya.smartcontract.sendPhoneNumber.SendPhoneNumberActivity;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import customfonts.Button_sfuitext_regular;
import customfonts.EditText_SFUI_Regular;
import io.objectbox.Box;
import io.objectbox.BoxStore;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.gebeya.framework.utils.Constants.CONTENT_TYPE;
import static com.gebeya.smartcontract.R.color.light_blue_100;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.loginPhoneNumber)
    EditText_SFUI_Regular loginPhoneNumber;

    @BindView(R.id.loginPassword)
    EditText_SFUI_Regular loginPassword;

    @BindView(R.id.logInButton)
    Button_sfuitext_regular loginButton;

    private LoginService mLoginService;
    private Box<UserLoginData> mUserBox;

    Animation shake;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bind();

        // Prepare a Box object for our UserLoginData class.
        BoxStore boxStore = ((App) getApplicationContext()).getStore();
        mUserBox = boxStore.boxFor(UserLoginData.class);

        mLoginService = Api.loginService();

        // create instance of animation for editText
        shake = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.shake);

    }

    @OnClick(R.id.logInButton)
    public void submitLogin() {
        String phoneNumber = Objects.requireNonNull(loginPhoneNumber.getText()).toString().trim();

        if (TextUtils.isEmpty(phoneNumber)) {
            loginPhoneNumber.setError(getString(R.string.login_phone_number_hint));
            // shake the phone number edit text.
            loginPhoneNumber.startAnimation(shake);
            return;
        }
        String password = Objects.requireNonNull(loginPassword.getText()).toString().trim();
        if (TextUtils.isEmpty(password)) {
            loginPassword.setError(getString(R.string.login_password_hint));

            // shake the password edit text.
            loginPassword.startAnimation(shake);
            return;
        }
        // disable the login button
        loginButton.setEnabled(false);
        toast(phoneNumber);

        mLoginService.loginSubmit(
              CONTENT_TYPE,
              phoneNumber,
              password).enqueue(new Callback<UserResponseDTO>() {

            @Override
            public void onResponse(Call<UserResponseDTO> call,
                                   Response<UserResponseDTO> response) {

                if (response.isSuccessful()) {

                    UserResponseDTO userResponse = response.body();
                    assert userResponse != null;
                    UserDTO user = userResponse.getUser();
                    String token = userResponse.getToken();

                    // Send the user information to the object box to save on the user phone.
                    setUserLogin(user, token);
                    //int statusCode = response.code();
                    //String statusMessage = response.message();
                    openActivity();
                } else {
                    ErrorResponseDTO errorResponse = ErrorUtils.parseError(response);
                    String errorMessage = errorResponse.getMessage();
                    setError(errorMessage);
                }
            }

            @Override
            public void onFailure(Call<UserResponseDTO> call, Throwable t) {
                d("Login Activity error loading from API");
                t.printStackTrace();
            }
        });
    }

    @OnClick(R.id.tvLoginSignUp)
    public void openSendPhoneNumberScreen(){
        startActivity(new Intent(this,SendPhoneNumberActivity.class));
    }

    @OnClick(R.id.tvLoginForgotPassword)
    public void openForgotPasswordScreen(){
        startActivity(new Intent(this,SendPhoneNumberActivity.class));
    }

    private void openActivity() {
        startActivity(new Intent(this, MainActivity.class));
        this.finish();
    }

    private void setError(String message) {

        // Check User name
        if (message.equals("User Does Not Exists")) {
            loginPhoneNumber.setError(message);
            loginButton.setEnabled(true);
            loginPhoneNumber.startAnimation(shake);
        }
        // Check password
        else if (message.equals("Password is Incorrect")) {
            loginPassword.setError(message);
            loginButton.setEnabled(true);
            loginPassword.startAnimation(shake);
        }

    }

    private void setUserLogin(UserDTO user, String token) {

        // Creating a new instance of UserLoginData object.
        UserLoginData userLoginData = new UserLoginData(user.getId(),
              user.getFirstName(),
              user.getLastName(),
              user.getPhoneNo(),
              token);

        // Store UserLoginData in to the object box.
        mUserBox.put(userLoginData);
    }
}
