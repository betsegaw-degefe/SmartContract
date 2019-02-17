package com.gebeya.framework.utils;

import com.gebeya.smartcontract.login.api.LoginService;
import com.gebeya.smartcontract.repository.myAsset.service.MakeCarTransactionService;
import com.gebeya.smartcontract.repository.myAsset.service.MakeHouseTransactionService;
import com.gebeya.smartcontract.repository.myAsset.service.MyAssetCarService;
import com.gebeya.smartcontract.repository.myAsset.service.MyAssetHouseService;
import com.gebeya.smartcontract.repository.myAsset.service.UserService;
import com.gebeya.smartcontract.repository.SearchUser.SearchUserService;
import com.gebeya.smartcontract.sendPhoneNumber.api.ResetPasswordService;
import com.gebeya.smartcontract.view.changePassword.api.ChangePasswordService;
import com.gebeya.smartcontract.view.publicLedger.api.service.HouseTransactionHistoryDetailService;
import com.gebeya.smartcontract.view.publicLedger.api.service.PublicLedgerService;
import com.gebeya.smartcontract.model.data.remote.RetrofitClient;
import com.gebeya.smartcontract.view.publicLedger.api.service.CarTransactionHistoryDetailService;
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

    public static MyAssetCarService getMyAssetCarService() {
        return RetrofitClient.getClient(BASE_URL).create(MyAssetCarService.class);
    }

    public static UserService getUserService() {
        return RetrofitClient.getClient(BASE_URL).create(UserService.class);
    }

    public static MyAssetHouseService getMyAssetHouseService() {
        return RetrofitClient.getClient(BASE_URL).create(MyAssetHouseService.class);
    }

    public static SendPhoneNumberService sendPhoneNumberService() {
        return RetrofitClient.getClient(BASE_URL).create(SendPhoneNumberService.class);
    }

    public static SignUpService signUpService() {
        return RetrofitClient.getClient(BASE_URL).create(SignUpService.class);
    }

    public static LoginService loginService() {
        return RetrofitClient.getClient(BASE_URL).create(LoginService.class);
    }

    public static MakeCarTransactionService makeTransactionService() {
        return RetrofitClient.getClient(BASE_URL).create(MakeCarTransactionService.class);
    }

    public static MakeHouseTransactionService makeHouseTransactionService() {
        return RetrofitClient.getClient(BASE_URL).create(MakeHouseTransactionService.class);
    }

    public static CarTransactionHistoryDetailService carTransactionHistoryDetailService() {
        return RetrofitClient.getClient(BASE_URL).create(CarTransactionHistoryDetailService.class);
    }

    public static HouseTransactionHistoryDetailService houseTransactionHistoryDetailService() {
        return RetrofitClient.getClient(BASE_URL).create(HouseTransactionHistoryDetailService.class);
    }

    public static ChangePasswordService changePasswordService() {
        return RetrofitClient.getClient(BASE_URL).create(ChangePasswordService.class);
    }

    public static ResetPasswordService resetPasswordService() {
        return RetrofitClient.getClient(BASE_URL).create(ResetPasswordService.class);
    }

    public static SearchUserService searchUserService() {
        return RetrofitClient.getClient(BASE_URL).create(SearchUserService.class);
    }

}
