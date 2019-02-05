package com.gebeya.smartcontract.view.publicLedger.api.service;

import com.gebeya.smartcontract.model.data.dto.CarHistoryTransactionBodyDTO;
import com.gebeya.smartcontract.model.data.dto.CarTransactionHistoryResponseDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface CarTransactionHistoryDetailService {
    @POST("transactions/getAssetTransactionHistory")
    Call<CarTransactionHistoryResponseDTO> getCarTransactionHistory(
          @Header("Authorization") String token,
          @Header("Content-Type") String type,
          @Body CarHistoryTransactionBodyDTO carHistoryTransactionBodyDTO
    );
}
