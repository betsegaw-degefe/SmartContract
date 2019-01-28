package com.gebeya.smartcontract.login.api;

import com.gebeya.smartcontract.model.data.dto.UserLoginResponseDTO;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginService {
    @POST("/users/login")
    @FormUrlEncoded()
    Call<UserLoginResponseDTO> loginSubmit(
          @Field("Content-Type") String type,
          @Field("phoneNo") String phoneNo,
          @Field("password") String password
    );
}
