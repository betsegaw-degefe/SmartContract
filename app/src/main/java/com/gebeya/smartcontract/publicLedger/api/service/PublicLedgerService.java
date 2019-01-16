package com.gebeya.smartcontract.publicLedger.api.service;

import com.gebeya.smartcontract.model.data.dto.PublicLedgerResponseDTO;
import com.gebeya.smartcontract.model.data.model.PublicLedgerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface PublicLedgerService {

    @GET("transactions")
    Call<PublicLedgerResponseDTO> getLedger(
          @Header("Authorization") String token,
          @Header("Content-Type") String type
    );

}
