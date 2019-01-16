package com.gebeya.smartcontract.model.data.model;

public class LoginRequest {
    private String phoneNo;
    private String password;

    public LoginRequest(String phoneNumber, String password) {
        this.phoneNo = phoneNumber;
        this.password = password;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
