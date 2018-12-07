package com.gebeya.smartcontract.publicLedger.api.service;

import com.gebeya.smartcontract.data.dto.PublicLedgerResponseDTO;
import com.gebeya.smartcontract.data.model.PublicLedgerResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PublicLedgerService {

    @GET("transactions")
    Call<PublicLedgerResponseDTO> getLedger();

}
