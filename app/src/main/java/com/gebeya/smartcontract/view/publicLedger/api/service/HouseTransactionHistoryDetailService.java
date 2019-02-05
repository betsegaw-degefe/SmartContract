package com.gebeya.smartcontract.view.publicLedger.api.service;

import com.gebeya.smartcontract.model.data.dto.HouseHistoryTransactionBodyDTO;
import com.gebeya.smartcontract.model.data.dto.HouseTransactionHistoryResponseDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface HouseTransactionHistoryDetailService {
    @POST("transactions/getAssetTransactionHistory")
    Call<HouseTransactionHistoryResponseDTO> getHouseTransactionHistory(
          @Header("Authorization") String token,
          @Header("Content-Type") String type,
          @Body HouseHistoryTransactionBodyDTO houseHistoryTransactionBodyDTO
    );
}
