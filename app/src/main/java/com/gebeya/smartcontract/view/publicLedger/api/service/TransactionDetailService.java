package com.gebeya.smartcontract.view.publicLedger.api.service;

import com.gebeya.smartcontract.model.data.dto.TransactionDetailResponseDTO;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface TransactionDetailService {

    @POST("transactions/getAssetTransactionHistory")
    @FormUrlEncoded
    Call<TransactionDetailResponseDTO> getTransactionHistory(
          @Field("Authorization") String token,
          @Field("Content-Type") String type,
          @Field("carId") String carId
    );
}
