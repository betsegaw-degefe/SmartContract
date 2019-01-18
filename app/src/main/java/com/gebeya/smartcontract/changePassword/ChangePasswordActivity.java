package com.gebeya.smartcontract.changePassword;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.gebeya.framework.base.BaseActivity;
import com.gebeya.framework.utils.CheckInternetConnection;
import com.gebeya.smartcontract.R;
import com.github.rahatarmanahmed.cpv.CircularProgressView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import customfonts.Button_sfuitext_regular;
import customfonts.EditText_SFUI_Regular;

public class ChangePasswordActivity extends BaseActivity {

    @BindView(R.id.currentPassword)
    EditText_SFUI_Regular mOldPassword;

    @BindView(R.id.newPassword)
    EditText_SFUI_Regular mNewPassword;

    @BindView(R.id.confirmPassword)
    EditText_SFUI_Regular mConfirmPassword;

    @BindView(R.id.submitChangePasswordButton)
    Button_sfuitext_regular changePasswordBtn;

    @BindView(R.id.progressViewChangePassword)
    CircularProgressView mProgressView;


    Animation shake;
    String newPassword;
    String confirmPassword;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        bind();
    }

    protected void onStart() {
        super.onStart();

        // create instance of shake animation for editText
        shake = AnimationUtils.loadAnimation(ChangePasswordActivity.this, R.anim.shake);

        // hide loading circular progress.
        mProgressView.setVisibility(View.GONE);
    }

    /**
     * On changePassword submit button clicked.
     */
    @OnClick(R.id.submitChangePasswordButton)
    public void submitChangePassword() {
        // Change the background color of the change password button.
        changeBtnBackground();

        // Validate the change password fields.
        if (!validation())
            return;

        // Disable the change password field.
        disableFields();
    }

    /**
     * Change the background color of the change password button.
     */
    private void changeBtnBackground() {
        changePasswordBtn.setBackground(this.getResources().getDrawable(R.drawable.button_pressed));
    }

    /**
     * change the background color of the button to default.
     */
    public void unpressbtn() {
        changePasswordBtn.setBackground(this.getResources().getDrawable(R.drawable.button_blue_background));
    }

    /**
     * Validate the change password fields.
     */
    private boolean validation() {
        if (isConnected()) {

            // Check old password field empty or not.
            if (!checkOldPassword())
                return false;

            // Check new password field empty or not.
            if (!checkNewPassword())
                return false;

            // Check confirm password field empty or not.
            if (!checkConfirmPassword())
                return false;

            // Check new password and confirm password are the same or not.
            return checkPasswordMatch();
        } else {
            toast("No connection available.");
            unpressbtn();
            return false;
        }
    }

    /**
     * Check whether connection is available or not.
     */
    private boolean isConnected() {
        return new CheckInternetConnection().CheckInternetConnection(getApplicationContext());
    }

    /**
     * Validate old password edit text.
     */
    private boolean checkOldPassword() {
        String oldPassword = Objects.requireNonNull(mOldPassword.getText()).toString().trim();
        if (TextUtils.isEmpty(oldPassword)) {
            unpressbtn();
            mOldPassword.setError(getString(R.string.change_old_password_hint));

            // shake the oldPassword edit text.
            mOldPassword.startAnimation(shake);
            return false;
        }
        return true;
    }

    /**
     * Validate new password edit text.
     */
    private boolean checkNewPassword() {
        newPassword = Objects.requireNonNull(mNewPassword.getText()).toString().trim();

        if (TextUtils.isEmpty(newPassword)) {
            unpressbtn();
            mNewPassword.setError(getString(R.string.change_password_new_password_hint));

            // shake the newPassword edit text.
            mNewPassword.startAnimation(shake);

            return false;
        }
        return true;
    }

    /**
     * Validate the confirm password edit text.
     */
    private boolean checkConfirmPassword() {
        confirmPassword = Objects.requireNonNull(mConfirmPassword.getText()).toString().trim();
        if (TextUtils.isEmpty(confirmPassword)) {
            unpressbtn();
            mConfirmPassword.setError(getString(R.string.change_password_confirm_password_hint));

            // shake the newPassword edit text.
            mConfirmPassword.startAnimation(shake);
            return false;
        }
        return true;
    }

    /**
     * Check new password and confirm password are the same or not.
     *
     * @return true: if the passwords matched. false: if the passwords does't match.
     */
    private boolean checkPasswordMatch() {
        if (!confirmPassword.equals(newPassword)) {
            unpressbtn();
            mConfirmPassword.setError(getString(R.string.change_password_match_hint));

            // shake the confirmPassword edit text.
            mConfirmPassword.startAnimation(shake);
            return false;
        }
        return true;
    }

    /**
     * disable the change password field.
     */
    private void disableFields() {
        // disable current password field.
        mOldPassword.setEnabled(false);

        // disable New password field.
        mNewPassword.setEnabled(false);

        // disable confirm password field.
        mConfirmPassword.setEnabled(false);

        // disable submit password button.
        changePasswordBtn.setEnabled(false);

        // show loading circular progress.
        mProgressView.setVisibility(View.VISIBLE);
    }
}
