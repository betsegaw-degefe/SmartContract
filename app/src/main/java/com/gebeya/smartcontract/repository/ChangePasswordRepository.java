package com.gebeya.smartcontract.repository;

import com.gebeya.framework.utils.Api;
import com.gebeya.framework.utils.ErrorUtils;
import com.gebeya.smartcontract.model.data.dto.ChangePasswordModelDto;
import com.gebeya.smartcontract.model.data.dto.ChangePasswordResponseDTO;
import com.gebeya.smartcontract.model.data.dto.ErrorResponseDTO;
import com.gebeya.smartcontract.view.changePassword.api.ChangePasswordService;
import com.gebeya.smartcontract.viewmodel.ChangePasswordViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.gebeya.framework.utils.Constants.CONTENT_TYPE;

public class ChangePasswordRepository {
    private ChangePasswordService mChangePasswordService;

    private static ChangePasswordRepository INSTANCE;


    /**
     * called when the instance of the ChangePasswordRepository class needed.
     *
     * @return Instance of this class.
     */
    public static ChangePasswordRepository getInstance() {
        //return SingletonHelper.INSTANCE;
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
     * @param bearerToken : The user bearer token
     * @return change password object responded from the api.
     */
    public void getChangePasswordRepository(String bearerToken, ChangePasswordModelDto changePasswordModelDto) {

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
