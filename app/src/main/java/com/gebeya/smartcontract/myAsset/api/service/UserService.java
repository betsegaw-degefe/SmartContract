package com.gebeya.smartcontract.myAsset.api.service;

import com.gebeya.smartcontract.data.dto.UserResponseDTO;
import com.gebeya.smartcontract.data.model.LoginRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserService {
    @POST("users/login")
    Call<UserResponseDTO> getUser(
          @Header("Authorization") String token,
          @Header("Content-Type") String type,
          @Body LoginRequest loginRequest
    );
}
