package com.gebeya.smartcontract.view.changePassword;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.gebeya.framework.base.BaseActivity;
import com.gebeya.framework.utils.CheckInternetConnection;
import com.gebeya.smartcontract.R;
import com.gebeya.smartcontract.databinding.ActivityChangePasswordBinding;
import com.gebeya.smartcontract.model.data.model.ChangePassword;
import com.gebeya.smartcontract.viewmodel.ChangePasswordViewModel;

import java.util.Objects;

public class ChangePasswordActivity extends BaseActivity {

    ActivityChangePasswordBinding binding;
    ChangePasswordViewModel changePasswordViewModel;

    Animation shake;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create a ChangePasswordViewModel the first time the system calls.
        // Re-created the activity receive the same changePasswordViewModel instance
        // created by the first activity.
        changePasswordViewModel =
              ViewModelProviders.of(this).get(ChangePasswordViewModel.class);


        // Inflate the binding layout ui.
        binding =
              DataBindingUtil.setContentView(this, R.layout.activity_change_password);


        // bind the ChangePassword object to layout.
        binding.setLifecycleOwner(this);
        binding.setChangePasswordViewModel(changePasswordViewModel);

        // Set observable on the ChangePassword model and triggered when the change happen.
        changePasswordViewModel.getChangePassword().observe(this,
              changePassword -> {
                  if (validation(changePassword)) {
                      changeBtnBackground();
                      disableFields();
                  }
              });

        // Set observable on the ChangePasswordResponseDTO and triggered when the change happen.
        ChangePasswordViewModel.getResponseObservable()
              .observe(this, changePasswordResponseDTO -> {
                  changePasswordResponseDTO = ChangePasswordViewModel.getResponseObservable().getValue();
                  if (changePasswordResponseDTO != null) {
                      toast("Password Changed Successfully.");
                      enableFields();
                  }
              });

        // Set observable on the ErrorResponseDTO and triggered when the change happen.
        ChangePasswordViewModel.getErrorResponseObservable()
              .observe(this, errorResponseDTO -> {
                  errorResponseDTO = ChangePasswordViewModel.getErrorResponseObservable().getValue();
                  String message = errorResponseDTO.getMessage();
                  if (message.equals("Password is Incorrect"))
                      setError(message);
              });
    }

    protected void onStart() {
        super.onStart();

        // create instance of shake animation for editText
        shake = AnimationUtils.loadAnimation(ChangePasswordActivity.this, R.anim.shake);

        // hide loading circular progress.
        binding.progressViewChangePassword.setVisibility(View.GONE);


    }

    /**
     * Change the background color of the change password button.
     */
    private void changeBtnBackground() {
        binding.submitChangePasswordButton.setBackground(this.getResources().getDrawable(R.drawable.button_pressed));
    }

    /**
     * change the background color of the button to default.
     */
    public void unpressbtn() {
        binding.submitChangePasswordButton.setBackground(this.getResources().getDrawable(R.drawable.button_blue_background));
    }

    /**
     * Validate the change password fields.
     */
    private boolean validation(ChangePassword changePassword) {
        if (isConnected()) {

            // Check old password field empty or not.
            if (!checkOldPassword(changePassword))
                return false;

            // Check new password field empty or not.
            if (!checkNewPassword(changePassword))
                return false;

            // Check confirm password field empty or not.
            if (!checkConfirmPassword(changePassword))
                return false;

            // Check new password and confirm password are the same or not.
            return checkPasswordMatch(changePassword);

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
    private boolean checkOldPassword(ChangePassword changePassword) {
        if (TextUtils.isEmpty(Objects.requireNonNull(changePassword)
              .getCurrentPassword())) {
            unpressbtn();

            // Set error to current password field.
            binding.txtCurrentPassword.setError(getString(R.string.change_old_password_hint));

            // shake the oldPassword edit text.
            binding.txtCurrentPassword.startAnimation(shake);
            binding.txtCurrentPassword.requestFocus();
            return false;
        }
        return true;
    }

    /**
     * Validate new password edit text.
     */
    private boolean checkNewPassword(ChangePassword changePassword) {

        if (TextUtils.isEmpty(changePassword.getNewPassword())) {
            unpressbtn();

            // Set error to new password field.
            binding.txtNewPassword.setError(getString(R.string.change_password_new_password_hint));

            // shake the newPassword edit text.
            binding.txtNewPassword.startAnimation(shake);
            binding.txtNewPassword.requestFocus();

            return false;
        }
        return true;
    }

    /**
     * Validate the confirm password edit text.
     */
    private boolean checkConfirmPassword(ChangePassword changePassword) {
        if (TextUtils.isEmpty(changePassword.getConfirmPassword())) {
            unpressbtn();

            // Set error to confirm password field.
            binding.txtConfirmPassword.setError(getString(R.string.change_password_confirm_password_hint));

            // shake the newPassword edit text.
            binding.txtConfirmPassword.startAnimation(shake);
            binding.txtConfirmPassword.requestFocus();

            return false;
        }
        return true;
    }

    /**
     * Check new password and confirm password are the same or not.
     *
     * @return true: if the passwords matched. false: if the passwords does't match.
     */
    private boolean checkPasswordMatch(ChangePassword changePassword) {
        if (!changePassword.getNewPassword().equals(changePassword.getConfirmPassword())) {
            unpressbtn();

            binding.txtConfirmPassword.setError(getString(R.string.change_password_match_hint));

            // shake the confirmPassword edit text.
            binding.txtConfirmPassword.startAnimation(shake);
            binding.txtConfirmPassword.requestFocus();
            return false;
        }
        return true;
    }

    /**
     * disable the change password field.
     */
    private void disableFields() {
        // disable current password field.
        binding.txtCurrentPassword.setEnabled(false);

        // disable New password field.
        binding.txtNewPassword.setEnabled(false);

        // disable confirm password field.
        binding.txtConfirmPassword.setEnabled(false);

        // disable submit password button.
        binding.submitChangePasswordButton.setEnabled(false);

        // show loading circular progress.
        binding.progressViewChangePassword.setVisibility(View.VISIBLE);
    }

    /**
     * display error message sent from the database.
     *
     * @param errorMessage: error message sent from api.
     */
    private void setError(String errorMessage) {
        binding.txtCurrentPassword.setError(errorMessage);
        binding.txtCurrentPassword.startAnimation(shake);
        binding.txtCurrentPassword.requestFocus();

        enableFields();

    }

    /**
     * Enable fields which were disabled when the change password forms submit.
     */
    public void enableFields() {

        unpressbtn();
        // enable current password field.
        binding.txtCurrentPassword.setEnabled(true);

        // enable New password field.
        binding.txtNewPassword.setEnabled(true);

        // enable confirm password field.
        binding.txtConfirmPassword.setEnabled(true);

        // enable submit password button.
        binding.submitChangePasswordButton.setEnabled(true);

        // show loading circular progress.
        binding.progressViewChangePassword.setVisibility(View.GONE);
    }

}
