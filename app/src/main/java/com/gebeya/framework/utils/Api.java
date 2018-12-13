package com.gebeya.framework.utils;

import com.gebeya.smartcontract.myAsset.api.service.MyAssetService;
import com.gebeya.smartcontract.myAsset.api.service.UserService;
import com.gebeya.smartcontract.publicLedger.api.service.PublicLedgerService;
import com.gebeya.smartcontract.data.remote.RetrofitClient;

public final class Api {
    /**
     * Represents operations to be performed on the remote REST API.
     */
    /**
     * Base endpoint for the entire API.
     * This endpoint should return the status of the API itself.
     */
    public static final String BASE_URL = "https://smart-contract-app.herokuapp.com/";


    public static PublicLedgerService getPublicLedgerService() {
        return RetrofitClient.getClient(BASE_URL).create(PublicLedgerService.class);
    }

    public static MyAssetService getMyAssetService(){
        return RetrofitClient.getClient(BASE_URL).create(MyAssetService.class);
    }

    public static UserService getUserService(){
        return RetrofitClient.getClient(BASE_URL).create(UserService.class);
    }

}
