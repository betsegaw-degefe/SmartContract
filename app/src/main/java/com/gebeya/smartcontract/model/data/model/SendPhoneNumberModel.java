package com.gebeya.smartcontract.model.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendPhoneNumberModel {

    @SerializedName("phoneNo")
    @Expose
    private String phoneNo;

    public SendPhoneNumberModel(String phoneNumber) {
        this.phoneNo = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNo;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNo = phoneNumber;
    }
}
