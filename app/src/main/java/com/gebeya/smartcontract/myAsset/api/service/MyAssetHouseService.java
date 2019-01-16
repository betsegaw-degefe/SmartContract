package com.gebeya.smartcontract.myAsset.api.service;

import com.gebeya.smartcontract.model.data.dto.MyAssetHouseResponseDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface MyAssetHouseService {
    @GET("assets/house/byUserId/{id}")
    Call<MyAssetHouseResponseDTO> getMyHouseAsset(
          @Header("Authorization") String token,
          @Header("Content-Type") String type,
          @Path("id") String id
    );
}
