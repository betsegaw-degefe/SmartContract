package com.gebeya.smartcontract.myAsset.api.service;

import com.gebeya.smartcontract.model.data.dto.MakeHouseTransactionBodyDTO;
import com.gebeya.smartcontract.model.data.model.MakeHouseTransactionModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface MakeHouseTransactionService {
    @POST("transactions")
    Call<MakeHouseTransactionModel> makeTransaction(
          @Header("Authorization") String token,
          @Header("Content-Type") String type,
          @Body MakeHouseTransactionBodyDTO makeHouseTransactionBodyDTO
    );
}
