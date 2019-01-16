package com.gebeya.smartcontract.model.data.remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl) {
            return new Retrofit.Builder()
                  .baseUrl(baseUrl)
                  .addConverterFactory(GsonConverterFactory.create())
                  .build();
    }
}
