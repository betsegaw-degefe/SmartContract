package com.gebeya.smartcontract.myAsset.api.service;

import com.gebeya.smartcontract.model.data.dto.UserLoginResponseDTO;
import com.gebeya.smartcontract.model.data.model.LoginRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserService {
    @POST("users/login")
    Call<UserLoginResponseDTO> getUser(
          @Header("Content-Type") String type,
          @Body LoginRequest loginRequest
    );
}
