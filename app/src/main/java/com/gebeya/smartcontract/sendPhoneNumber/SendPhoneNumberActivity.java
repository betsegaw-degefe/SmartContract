package com.gebeya.smartcontract.sendPhoneNumber;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.gebeya.framework.base.BaseActivity;
import com.gebeya.framework.utils.Api;
import com.gebeya.smartcontract.R;
import com.gebeya.smartcontract.data.model.SendPhoneNumberModel;
import com.gebeya.smartcontract.login.LoginActivity;
import com.gebeya.smartcontract.sendPhoneNumber.api.SendPhoneNumberService;
import com.gebeya.smartcontract.signUp.SignUpActivity;
import com.hbb20.CountryCodePicker;

import butterknife.BindView;
import butterknife.OnClick;
import customfonts.MyTextView_Roboto_Regular;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendPhoneNumberActivity extends BaseActivity {

    @BindView(R.id.ccp)
    CountryCodePicker ccp;

    @BindView(R.id.sendPhoneNumber)
    EditText sendPhoneNumber;

    private SendPhoneNumberService mSendPhoneNumberService;
    private String phoneNumber;
    private String KEY = "PHONE_NUMBER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_phone_number_layout);
        bind();

        // Initialize an instance of the SendPhoneNumberService interface
        mSendPhoneNumberService = Api.sendPhoneNumberService();

        // Attach CarrierNumber editText to Country code picker.
        ccp.registerCarrierNumberEditText(sendPhoneNumber);

        // Enabling auto format in the text field.
        ccp.setNumberAutoFormattingEnabled(true);


    }

    @OnClick(R.id.submitPhoneNumber)
    public void submitPhoneNumber() {


        // Get the full phone number with country code from the text field.
        phoneNumber = ccp.getFullNumber();

        // Get the country code only.
        String countryCode = ccp.getSelectedCountryCode();

        // Trim the the first country code.
        phoneNumber = phoneNumber.replace(countryCode, "");

        mSendPhoneNumberService.sendPhoneNumber("application/json",
              phoneNumber)
              .enqueue(new Callback<SendPhoneNumberModel>() {
                  @Override
                  public void onResponse(Call<SendPhoneNumberModel> call,
                                         Response<SendPhoneNumberModel> response) {
                      int statusCode = response.code();
                      //d(response.toString());
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

    @OnClick(R.id.haveAnAccount)
    public void openLoginPage(MyTextView_Roboto_Regular myTextView_roboto_regular){
        toast("It is working");
        startActivity(new Intent(this,LoginActivity.class));
    }

    private void statusShow(int code, String Message) {
        String statusCode = Integer.toString(code);
        if (code == 200) {

            // Passing the phone number to signUp activity
            Intent intent = new Intent(this, SignUpActivity.class);
            intent.putExtra("PHONE_NUMBER", phoneNumber);
            startActivity(intent);

            this.finish();
        } else {
            toast(Message);
        }
    }
}
