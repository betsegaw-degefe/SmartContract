package com.gebeya.framework.utils;

import com.gebeya.framework.base.BaseActivity;
import com.gebeya.smartcontract.login.api.LoginService;
import com.gebeya.smartcontract.myAsset.api.service.MakeTransactionService;
import com.gebeya.smartcontract.myAsset.api.service.MyAssetCarService;
import com.gebeya.smartcontract.myAsset.api.service.MyAssetHouseService;
import com.gebeya.smartcontract.myAsset.api.service.UserService;
import com.gebeya.smartcontract.publicLedger.api.service.PublicLedgerService;
import com.gebeya.smartcontract.data.remote.RetrofitClient;
import com.gebeya.smartcontract.sendPhoneNumber.api.SendPhoneNumberService;
import com.gebeya.smartcontract.signUp.api.SignUpService;

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

    public static MyAssetCarService getMyAssetCarService(){
        return RetrofitClient.getClient(BASE_URL).create(MyAssetCarService.class);
    }

    public static UserService getUserService(){
        return RetrofitClient.getClient(BASE_URL).create(UserService.class);
    }

    public static MyAssetHouseService getMyAssetHouseService(){
        return RetrofitClient.getClient(BASE_URL).create(MyAssetHouseService.class);
    }

    public static SendPhoneNumberService sendPhoneNumberService(){
        return RetrofitClient.getClient(BASE_URL).create(SendPhoneNumberService.class);
    }

    public static SignUpService signUpService(){
        return RetrofitClient.getClient(BASE_URL).create(SignUpService.class);
    }

    public static LoginService loginService(){
        return RetrofitClient.getClient(BASE_URL).create(LoginService.class);
    }

    public static MakeTransactionService makeTransactionService(){
        return RetrofitClient.getClient(BASE_URL).create(MakeTransactionService.class);
    }

}
