package com.gebeya.smartcontract.myAsset.api.service;

import com.gebeya.smartcontract.model.data.dto.MakeTransactionBodyDTO;
import com.gebeya.smartcontract.model.data.model.MakeTransactionModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface MakeTransactionService {
    @POST("transactions")
    Call<MakeTransactionModel> makeTransaction(
          @Header("Authorization") String token,
          @Header("Content-Type") String type,
          @Body MakeTransactionBodyDTO makeTransactionBodyDTO
    );
}
