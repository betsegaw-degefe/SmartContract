package com.gebeya.smartcontract.myAsset.api.service;

import com.gebeya.smartcontract.data.dto.MyAssetResponseDTO;
import com.gebeya.smartcontract.data.dto.PublicLedgerResponseDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface MyAssetService {
    @GET("assets/car/byUserId/{id}")
    Call<MyAssetResponseDTO> getMyAsset(
          @Header("Authorization") String token,
          @Header("Content-Type") String type,
          @Path("id") String id
    );
}
