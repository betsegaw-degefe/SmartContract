package com.gebeya.smartcontract.view.publicLedger.api.service;

import com.gebeya.smartcontract.model.data.dto.TransactionDetailCarResponseDTO;
import com.gebeya.smartcontract.model.data.dto.TransactionDetailHouseResponseDTO;
import com.gebeya.smartcontract.model.data.model.Transaction;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface TransactionDetailService {

    @POST("transactions/getAssetTransactionHistory")
    @FormUrlEncoded
    Call<ArrayList<TransactionDetailCarResponseDTO>> getTransactionHistory(
          @Field("Authorization") String token,
          @Field("Content-Type") String type,
          @Field("carId") String carId
    );
}
