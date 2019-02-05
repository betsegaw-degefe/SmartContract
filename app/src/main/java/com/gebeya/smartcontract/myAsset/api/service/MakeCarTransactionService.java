package com.gebeya.smartcontract.myAsset.api.service;

import com.gebeya.smartcontract.model.data.dto.MakeCarTransactionBodyDTO;
import com.gebeya.smartcontract.model.data.model.MakeCarTransactionModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface MakeCarTransactionService {
    @POST("transactions")
    Call<MakeCarTransactionModel> makeTransaction(
          @Header("Authorization") String token,
          @Header("Content-Type") String type,
          @Body MakeCarTransactionBodyDTO makeCarTransactionBodyDTO
    );
}
