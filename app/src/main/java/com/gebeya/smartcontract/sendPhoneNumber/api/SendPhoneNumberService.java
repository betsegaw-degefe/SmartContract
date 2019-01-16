package com.gebeya.smartcontract.sendPhoneNumber.api;

import com.gebeya.smartcontract.model.data.model.SendPhoneNumberModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface SendPhoneNumberService {
    @POST("users/sendVerificationCode")
    @FormUrlEncoded
    Call<SendPhoneNumberModel> sendPhoneNumber(
          @Field("Content-Type") String type,
          @Field("phoneNo") String phoneNo
    );
}
