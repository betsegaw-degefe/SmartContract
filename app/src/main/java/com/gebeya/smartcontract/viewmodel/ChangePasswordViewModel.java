package com.gebeya.smartcontract.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.view.View;

import com.gebeya.smartcontract.App;
import com.gebeya.smartcontract.model.data.dto.ChangePasswordModelDto;
import com.gebeya.smartcontract.model.data.dto.ChangePasswordResponseDTO;
import com.gebeya.smartcontract.model.data.dto.ErrorResponseDTO;
import com.gebeya.smartcontract.model.data.dto.PublicLedgerResponseDTO;
import com.gebeya.smartcontract.model.data.model.ChangePassword;
import com.gebeya.smartcontract.model.data.objectBox.UserLoginData;
import com.gebeya.smartcontract.repository.ChangePasswordRepository;

import java.util.List;
import java.util.Objects;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class ChangePasswordViewModel extends AndroidViewModel {

    private final static MutableLiveData<ChangePasswordResponseDTO> mChangePasswordResponseObservable = new MutableLiveData<>();
    private final static MutableLiveData<ErrorResponseDTO> mErrorResponseObservable = new MutableLiveData<>();
    private LiveData<?> responseObservable;

    public MutableLiveData<String> oldPassword = new MutableLiveData<>();
    public MutableLiveData<String> newPassword = new MutableLiveData<>();
    public MutableLiveData<String> confirmPassword = new MutableLiveData<>();

    private MutableLiveData<ChangePassword> mChangePasswordMutableLiveData;
    private ChangePasswordModelDto mChangePasswordModelDto = new ChangePasswordModelDto();

    private BoxStore userBox;
    private Box<UserLoginData> box;


    public ChangePasswordViewModel(@NonNull Application application) {
        super(application);

        userBox = ((App) getApplication().getApplicationContext()).getStore();
        box = userBox.boxFor(UserLoginData.class);
    }

    /**
     * Create a MutableLiveData instance of ChangePassword object.
     *
     * @return a MutableLiveData instance of ChangePassword.
     */
    public MutableLiveData<ChangePassword> getChangePassword() {
        if (mChangePasswordMutableLiveData == null) {
            mChangePasswordMutableLiveData = new MutableLiveData<>();
        }
        return mChangePasswordMutableLiveData;
    }

    /**
     * Create a LiveData instance of ChangePasswordResponseDTO object.
     *
     * @return a LiveData instance of ChangePasswordResponseDTO.
     */
    public static MutableLiveData<ChangePasswordResponseDTO> getResponseObservable() {
        return mChangePasswordResponseObservable;
    }

    /**
     * Create a LiveData instance of ErrorResponseDTO object.
     *
     * @return a LiveData instance of ErrorResponseDTO.
     */
    public static MutableLiveData<ErrorResponseDTO> getErrorResponseObservable() {
        return mErrorResponseObservable;
    }

    /**
     * Triggered when the user clicked the ChangePassword button.
     *
     * @param view: accept the UI ViewGroup.
     */
    public void onClick(View view) {

        ChangePassword changePassword = new ChangePassword(oldPassword.getValue(),
              newPassword.getValue(), confirmPassword.getValue());

        // loads User token from objectBox
        List<UserLoginData> users = box.getAll();
        String token = users.get(0).getToken();
        String bearerToken = "Bearer " + token;

        mChangePasswordMutableLiveData.setValue(changePassword);

        if (oldPassword.getValue() != null && newPassword.getValue() != null
              && confirmPassword.getValue() != null && confirmPassword.getValue().equals(newPassword.getValue())) {

            mChangePasswordModelDto.setOldPassword(oldPassword.getValue().trim());
            mChangePasswordModelDto.setNewPassword(newPassword.getValue().trim());
        }

        // Instantiate change password request.
        ChangePasswordRepository.getInstance()
              .getChangePasswordRepository(bearerToken, mChangePasswordModelDto);
    }
}
