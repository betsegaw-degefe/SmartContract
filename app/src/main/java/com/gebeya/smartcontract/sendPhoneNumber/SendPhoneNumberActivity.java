package com.gebeya.smartcontract.sendPhoneNumber;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.gebeya.framework.base.BaseActivity;
import com.gebeya.framework.utils.Api;
import com.gebeya.smartcontract.R;
import com.gebeya.smartcontract.data.model.SendPhoneNumberModel;
import com.gebeya.smartcontract.sendPhoneNumber.api.SendPhoneNumberService;
import com.gebeya.smartcontract.signUp.SignUpActivity;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendPhoneNumberActivity extends BaseActivity {

    @BindView(R.id.etPhoneNumber)
    EditText mPhoneNumber;

    private SendPhoneNumberService mSendPhoneNumberService;
    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_phone_number_layout);
        bind();

        // Initialize an instance of the SendPhoneNumberService interface
        mSendPhoneNumberService = Api.sendPhoneNumberService();
    }

    @OnClick(R.id.submitPhoneNumber)
    public void submitPhoneNumber() {

        // Get the phone number from the text field.
        phoneNumber = mPhoneNumber.getText().toString().trim();

        mSendPhoneNumberService.sendPhoneNumber("application/json",
              phoneNumber)
              .enqueue(new Callback<SendPhoneNumberModel>() {
                  @Override
                  public void onResponse(Call<SendPhoneNumberModel> call,
                                         Response<SendPhoneNumberModel> response) {
                      int statusCode = response.code();
                      String statusMessage = response.message();
                      statusShow(statusCode, statusMessage);

                  }

                  @Override
                  public void onFailure(Call<SendPhoneNumberModel> call,
                                        Throwable t) {
                      d("SendPhoneNumberActivity error loading from API");
                      t.printStackTrace();
                  }
              });
    }

    public void statusShow(int code, String Message) {
        String statusCode = Integer.toString(code);
        if (code == 200) {
            //toast("phone number success!");
            toast(Message);
            startActivity(new Intent(this, SignUpActivity.class));
            this.finish();
        } else {
            toast(Message);
        }
    }
}
