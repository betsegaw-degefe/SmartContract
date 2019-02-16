package com.gebeya.smartcontract.repository.changePassword;

import com.gebeya.framework.utils.Api;
import com.gebeya.framework.utils.ErrorUtils;
import com.gebeya.smartcontract.model.data.dto.ChangePasswordModelDTO;
import com.gebeya.smartcontract.model.data.dto.ChangePasswordResponseDTO;
import com.gebeya.smartcontract.model.data.dto.ErrorResponseDTO;
import com.gebeya.smartcontract.view.changePassword.api.ChangePasswordService;
import com.gebeya.smartcontract.viewmodel.changePassword.ChangePasswordViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.gebeya.framework.utils.Constants.CONTENT_TYPE;

public class ChangePasswordRepository {

    private ChangePasswordService mChangePasswordService;
    private static ChangePasswordRepository INSTANCE;


    /**
     * Create an Instance of this class when the object of this class created.
     *
     * @return Instance of this class.
     */
    public static ChangePasswordRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ChangePasswordRepository();
        }
        return INSTANCE;
    }

    /**
     * create the retrofit service client for this class.
     */
    private ChangePasswordRepository() {
        mChangePasswordService = Api.changePasswordService();
    }

    /**
     * Asynchronous request to the network for change password.
     *
     * @param bearerToken : The user bearer token
     */
    public void getChangePasswordRepository(String bearerToken,
                                            ChangePasswordModelDTO changePasswordModelDto) {

        mChangePasswordService.changePasswordSubmit(bearerToken,
              CONTENT_TYPE, changePasswordModelDto)
              .enqueue(new Callback<ChangePasswordResponseDTO>() {
                  @Override
                  public void onResponse(Call<ChangePasswordResponseDTO> call,
                                         Response<ChangePasswordResponseDTO> response) {
                      if (response.isSuccessful()) {
                          ChangePasswordViewModel.getResponseObservable().setValue(response.body());
                      } else {
                          ErrorResponseDTO errorResponse = ErrorUtils.parseError(response);
                          ChangePasswordViewModel.getErrorResponseObservable().setValue(errorResponse);
                      }
                  }

                  @Override
                  public void onFailure(Call<ChangePasswordResponseDTO> call,
                                        Throwable t) {
                      ChangePasswordViewModel.getResponseObservable().setValue(null);
                  }
              });
    }
}
