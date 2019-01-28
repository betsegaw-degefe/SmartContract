package com.gebeya.smartcontract.view.changePassword.api;

import com.gebeya.smartcontract.model.data.dto.ChangePasswordModelDTO;
import com.gebeya.smartcontract.model.data.dto.ChangePasswordResponseDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ChangePasswordService {
    @POST("/users/changePassword")
    Call<ChangePasswordResponseDTO> changePasswordSubmit(
          @Header("Authorization") String token,
          @Header("Content-Type") String type,
          @Body ChangePasswordModelDTO ChangePasswordModelDto
    );
}
