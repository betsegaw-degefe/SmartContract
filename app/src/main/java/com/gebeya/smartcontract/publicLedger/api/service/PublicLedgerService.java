package com.gebeya.smartcontract.publicLedger.api.service;

import com.gebeya.smartcontract.data.model.PublicLedgerResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PublicLedgerService {

    @GET("transactions")
    Call<PublicLedgerResponse> getLedger();

}
