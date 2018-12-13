package com.gebeya.smartcontract.myAsset.api.service;

import com.gebeya.smartcontract.data.dto.PublicLedgerResponseDTO;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MyAssetService {
    @GET("assets/car/RW8JDKF28000")
    Call<PublicLedgerResponseDTO> getLedger();

}
