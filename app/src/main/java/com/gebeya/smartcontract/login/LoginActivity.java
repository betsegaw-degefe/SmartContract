package com.gebeya.smartcontract.login;

import android.content.Intent;
import android.os.Bundle;

import com.gebeya.framework.base.BaseActivity;
import com.gebeya.framework.utils.Api;
import com.gebeya.smartcontract.App;
import com.gebeya.smartcontract.MainActivity;
import com.gebeya.smartcontract.R;
import com.gebeya.smartcontract.data.dto.PublicLedgerResponseDTO;
import com.gebeya.smartcontract.data.dto.UserDTO;
import com.gebeya.smartcontract.data.dto.UserResponseDTO;
import com.gebeya.smartcontract.data.model.LoginModel;
import com.gebeya.smartcontract.data.objectBox.UserLoginData;
import com.gebeya.smartcontract.login.api.LoginService;

import butterknife.BindView;
import butterknife.OnClick;
import customfonts.EditText_SFUI_Regular;
import io.objectbox.Box;
import io.objectbox.BoxStore;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.gebeya.framework.utils.Constants.CONTENT_TYPE;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.loginPhoneNumber)
    EditText_SFUI_Regular loginPhoneNumber;

    @BindView(R.id.loginPassword)
    EditText_SFUI_Regular loginPassword;

    private String phoneNumber;
    private String password;

    private LoginService mLoginService;
    private Box<UserLoginData> mUserBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bind();

        // Prepare a Box object for our UserLoginData class.
        BoxStore boxStore = ((App) getApplicationContext()).getStore();
        mUserBox = boxStore.boxFor(UserLoginData.class);

        mLoginService = Api.loginService();

    }

    @OnClick(R.id.logInButton)
    public void submitLogin() {
        phoneNumber = loginPhoneNumber.getText().toString().trim();
        password = loginPassword.getText().toString().trim();
        toast(phoneNumber);

        mLoginService.loginSubmit(
              CONTENT_TYPE,
              phoneNumber,
              password).enqueue(new Callback<UserResponseDTO>() {

            @Override
            public void onResponse(Call<UserResponseDTO> call,
                                   Response<UserResponseDTO> response) {

                UserResponseDTO userResponse = response.body();
                assert userResponse != null;
                UserDTO user = userResponse.getUser();
                String token = userResponse.getToken();

                // Send the user information to the object box to save on the user phone.
                setUserLogin(user,token);


                int statusCode = response.code();
                String statusMessage = response.message();
                statusShow(statusCode, statusMessage);
            }

            @Override
            public void onFailure(Call<UserResponseDTO> call, Throwable t) {

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
