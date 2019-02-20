package com.gebeya.smartcontract.view.sendPhoneNumber.api;

import com.gebeya.smartcontract.model.data.dto.SendPhoneNumberResponseDTO;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ResetPasswordService {
    @POST("users/resetPassword")
    @FormUrlEncoded
    Call<SendPhoneNumberResponseDTO> resetPassword(
          @Field("Content-Type") String type,
          @Field("phoneNo") String phoneNo
    );
}
