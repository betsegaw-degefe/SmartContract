package com.gebeya.smartcontract.myAsset.api.service;

import com.gebeya.smartcontract.model.data.model.MakeTransactionModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MakeTransactionService {
    @POST("transactions")
    @FormUrlEncoded
    Call<MakeTransactionModel> makeTransaction(
          @Field("Content-Type") String type,
          @Field("carId") String carId,
          @Field("to") String to,
          @Field("from") String from
    );
}
