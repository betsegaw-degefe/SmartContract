package com.gebeya.smartcontract.sendPhoneNumber;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.gebeya.framework.base.BaseActivity;
import com.gebeya.framework.utils.Api;
import com.gebeya.framework.utils.ErrorUtils;
import com.gebeya.smartcontract.R;
import com.gebeya.smartcontract.data.dto.ErrorResponseDTO;
import com.gebeya.smartcontract.data.model.SendPhoneNumberModel;
import com.gebeya.smartcontract.login.LoginActivity;
import com.gebeya.smartcontract.sendPhoneNumber.api.SendPhoneNumberService;
import com.gebeya.smartcontract.signUp.SignUpActivity;
import com.hbb20.CountryCodePicker;

import butterknife.BindView;
import butterknife.OnClick;
import customfonts.Button_sfuitext_regular;
import customfonts.MyTextView_Roboto_Regular;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendPhoneNumberActivity extends BaseActivity {

    @BindView(R.id.ccp)
    CountryCodePicker ccp;

    @BindView(R.id.sendPhoneNumber)
    EditText sendPhoneNumber;

    @BindView(R.id.llEnterPhoneNumber)
    LinearLayout llEnterPhoneNumber;

    @BindView(R.id.submitPhoneNumber)
    Button_sfuitext_regular submitPhoneNumberButton;

    private SendPhoneNumberService mSendPhoneNumberService;
    private String phoneNumber;
    private String KEY = "PHONE_NUMBER";

    Animation shake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //setTheme(R.style.base_theme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_phone_number_layout);
        bind();

        // Initialize an instance of the SendPhoneNumberService interface
        mSendPhoneNumberService = Api.sendPhoneNumberService();

        // Attach CarrierNumber editText to Country code picker.
        ccp.registerCarrierNumberEditText(sendPhoneNumber);

        // Enabling auto format in the text field.
        ccp.setNumberAutoFormattingEnabled(true);

        // create instance of animation for editText
        shake = AnimationUtils.loadAnimation(SendPhoneNumberActivity.this, R.anim.shake);
    }

    @OnClick(R.id.submitPhoneNumber)
    public void submitPhoneNumber() {
        submitPhoneNumberButton.setEnabled(false);

        String phoneNumberCheck = sendPhoneNumber.getText().toString().trim();

        //toast("phone number length: " + countWords(phoneNumberCheck));
        // Get the full phone number with country code from the text field.
        phoneNumber = ccp.getFullNumber();
        if (TextUtils.isEmpty(phoneNumberCheck)) {
            sendPhoneNumber.setError(getString(R.string.login_phone_number_hint));
            submitPhoneNumberButton.setEnabled(true);

            // shake the phone number edit text.
            llEnterPhoneNumber.startAnimation(shake);
            return;
        } else if (!compareFirstWord(phoneNumberCheck)) {
            sendPhoneNumber.setError(getString(R.string.send_phone_number_start));
            submitPhoneNumberButton.setEnabled(true);

            // shake the phone number edit text.
            llEnterPhoneNumber.startAnimation(shake);
            return;
        } else if (countWords(phoneNumberCheck) != 9) {
            sendPhoneNumber.setError(getString(R.string.send_phone_number_length));
            submitPhoneNumberButton.setEnabled(true);

            // shake the phone number edit text.
            llEnterPhoneNumber.startAnimation(shake);
            return;
        }

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
                      if (response.isSuccessful()) {
                          openSignUp();
                      } else {
                          ErrorResponseDTO errorResponse = ErrorUtils.parseError(response);
                          String errorMessage = errorResponse.getMessage();
                          setError(errorMessage);
                      }
                  }

                  @Override
                  public void onFailure(Call<SendPhoneNumberModel> call,
                                        Throwable t) {
                      d("SendPhoneNumberActivity error loading from API");
                      t.printStackTrace();
                  }
              });
    }

    /**
     * when have an account label clicked this triggered to start Login activity.
     *
     * @param myTextView_roboto_regular
     */

    @OnClick(R.id.haveAnAccount)
    public void openLoginPage(MyTextView_Roboto_Regular myTextView_roboto_regular) {
       // toast("It is working");
        startActivity(new Intent(this, LoginActivity.class));
    }

    /**
     * This function open sign up page if the user login return with success.
     */
    private void openSignUp() {
        // Passing the phone number to signUp activity
        Intent intent = new Intent(this, SignUpActivity.class);
        intent.putExtra("PHONE_NUMBER", phoneNumber);
        startActivity(intent);
        this.finish();
    }

    /**
     * This function set custom error message returned from api.
     *
     * @param message: api custom message.
     */
    private void setError(String message) {
        sendPhoneNumber.setError(message);
        submitPhoneNumberButton.setEnabled(true);
        llEnterPhoneNumber.startAnimation(shake);
    }

    /**
     * This function count word and return number count.
     *
     * @param str: word to count
     * @return Number of words
     */
    private static int countWords(String str) {

        int wordCount = 0;

        //if string is null, word count is 0
        if (str == null)
            return wordCount;

        //remove leading and trailing white spaces first
        str = str.trim();

        //if string contained all the spaces, word count is 0
        if (str.equals(""))
            return wordCount;

        // Create an char array of given String
        char[] ch = str.toCharArray();

        for (int i = 0; i < ch.length; i++) {

            // When the character is not space
            if (ch[i] != ' ') {
                wordCount++;
            }
        }
        return wordCount;
    }

    /**
     * This function check the first letter is 9 or not.
     *
     * @param str: Word to check the first letter
     * @return return true if the first char is 9 or false.
     */
    private boolean compareFirstWord(String str) {

        // Create a char array of given String
        char[] ch = str.toCharArray();
        return ch[0] == '9';
    }
}
