package com.gebeya.smartcontract.view.sendPhoneNumber;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.gebeya.framework.base.BaseActivity;
import com.gebeya.framework.utils.Api;
import com.gebeya.framework.utils.ErrorUtils;
import com.gebeya.smartcontract.R;
import com.gebeya.smartcontract.view.login.LoginActivity;
import com.gebeya.smartcontract.model.data.dto.ErrorResponseDTO;
import com.gebeya.smartcontract.model.data.dto.SendPhoneNumberResponseDTO;
import com.gebeya.smartcontract.model.data.model.SendPhoneNumberModel;
import com.gebeya.smartcontract.view.sendPhoneNumber.api.ResetPasswordService;
import com.gebeya.smartcontract.view.sendPhoneNumber.api.SendPhoneNumberService;
import com.gebeya.smartcontract.view.signUp.SignUpActivity;
import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.hbb20.CountryCodePicker;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import customfonts.Button_sfuitext_regular;
import customfonts.MyTextView_Roboto_Regular;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.gebeya.framework.utils.Constants.CONTENT_TYPE;

public class SendPhoneNumberActivity extends BaseActivity {

    @BindView(R.id.ccp)
    CountryCodePicker ccp;

    @BindView(R.id.sendPhoneNumber)
    EditText sendPhoneNumber;

    @BindView(R.id.llEnterPhoneNumber)
    LinearLayout llEnterPhoneNumber;

    @BindView(R.id.submitPhoneNumber)
    Button_sfuitext_regular submitPhoneNumberButton;

    @BindView(R.id.sendPhoneNumberTitle)
    MyTextView_Roboto_Regular mTitle;

    @BindView(R.id.progressViewSendPhoneNumber)
    CircularProgressView prgressView;

    private SendPhoneNumberService mSendPhoneNumberService;
    private ResetPasswordService mResetPasswordService;
    private String phoneNumber;
    private String KEY = "PHONE_NUMBER";
    private String title;

    Animation shake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_phone_number_layout);
        bind();

        // Extracting the title which is passed from login activity.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            title = intent.getExtras().getString("TITLE");
        }

        // Set the title of the page
        setScreenTitle();

        // Initialize an instance of the SendPhoneNumberService interface
        mSendPhoneNumberService = Api.sendPhoneNumberService();
        mResetPasswordService = Api.resetPasswordService();

        // Attach CarrierNumber editText to Country code picker.
        ccp.registerCarrierNumberEditText(sendPhoneNumber);

        // Enabling auto format in the text field.
        ccp.setNumberAutoFormattingEnabled(true);

        // create instance of animation for editText
        shake = AnimationUtils.loadAnimation(SendPhoneNumberActivity.this, R.anim.shake);

        // Hide the progress view.
        prgressView.setVisibility(View.GONE);
    }

    /**
     * Set the title of the page.
     */
    private void setScreenTitle() {
        mTitle.setText(title);
    }

    /**
     * Triggered when the submit button pressed.
     */
    @OnClick(R.id.submitPhoneNumber)
    public void submitPhoneNumber() {
        submitPhoneNumberButton.setEnabled(false);

        String phoneNumberCheck = sendPhoneNumber.getText().toString().trim();

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

        // Change the background color of the pressed button.
        submitPhoneNumberButton.setBackground(this.getResources().getDrawable(R.drawable.button_pressed));

        // Making visible the progress view.
        prgressView.setVisibility(View.VISIBLE);

        // Get the country code only.
        String countryCode = ccp.getSelectedCountryCode();

        // Trim the the first country code.
        phoneNumber = phoneNumber.replace(countryCode, "");


        if (title.equals("Sign Up")) {
            mSendPhoneNumberService.sendPhoneNumber(CONTENT_TYPE,
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
                              unpressbtn();
                          }
                      }

                      @Override
                      public void onFailure(Call<SendPhoneNumberModel> call,
                                            Throwable t) {
                          d("SendPhoneNumberActivity error loading from API");
                          t.printStackTrace();
                      }
                  });
        } else if (title.equals("Forget Password")) {
            mResetPasswordService.resetPassword(CONTENT_TYPE, phoneNumber)
                  .enqueue(new Callback<SendPhoneNumberResponseDTO>() {
                      @Override
                      public void onResponse(Call<SendPhoneNumberResponseDTO> call,
                                             Response<SendPhoneNumberResponseDTO> response) {
                          if (response.isSuccessful()) {
                              SendPhoneNumberResponseDTO sendPhoneNumberResponseDTO =
                                    response.body();

                              String message = Objects.requireNonNull(sendPhoneNumberResponseDTO).getMessage();

                              if (message.equals("Password reset successful")) {
                                  openResetPassword(message);
                              }
                          } else {
                              ErrorResponseDTO errorResponse = ErrorUtils.parseError(response);
                              String errorMessage = errorResponse.getMessage();
                              setError(errorMessage);
                              unpressbtn();
                          }
                      }

                      @Override
                      public void onFailure(Call<SendPhoneNumberResponseDTO> call, Throwable t) {
                          d("SendPhoneNumberActivity error loading from API");
                          t.printStackTrace();
                      }
                  });
        }


    }

    /**
     * when have an account label clicked this triggered to start Login activity.
     *
     * @param myTextView_roboto_regular
     */

    @OnClick(R.id.haveAnAccount)
    public void openLoginPage(MyTextView_Roboto_Regular myTextView_roboto_regular) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    /**
     * open reset password activity.
     */
    public void openResetPassword(String message) {
        toast("Password sent to your phone number.");
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

    /**
     * change the background color of the button to default.
     */
    public void unpressbtn() {
        submitPhoneNumberButton.setBackground(this.getResources().getDrawable(R.drawable.button_blue_background));
    }
}
