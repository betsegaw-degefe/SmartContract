package com.gebeya.smartcontract.signUp.api;

import com.gebeya.smartcontract.model.data.model.SignUpModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface SignUpService {
    @POST("/users/create")
    @FormUrlEncoded()
    Call<SignUpModel> SignUp(
          @Field("Content-Type") String type,
          @Field("firstName") String firstName,
          @Field("lastName") String lastName,
          @Field("phoneNo") String phoneNo,
          @Field("password") String password,
          @Field("verificationCode") String verificationCode
    );
}
