package com.gebeya.smartcontract.publicLedger.api.service;

import com.gebeya.smartcontract.publicLedger.api.model.PublicLedgerRespond;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PublicLedgerClient {

    @GET("/transactions")
    Call<List<PublicLedgerRespond>> publicLedger();

}
