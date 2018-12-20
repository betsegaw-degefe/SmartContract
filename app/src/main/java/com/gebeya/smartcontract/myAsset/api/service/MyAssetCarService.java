package com.gebeya.smartcontract.myAsset.api.service;

import com.gebeya.smartcontract.data.dto.MyAssetCarResponseDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface MyAssetCarService {
    @GET("assets/car/byUserId/{id}")
    Call<MyAssetCarResponseDTO> getMyCarAsset(
          @Header("Authorization") String token,
          @Header("Content-Type") String type,
          @Path("id") String id
    );
}
