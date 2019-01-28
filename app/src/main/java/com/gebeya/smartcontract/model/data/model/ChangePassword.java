package com.gebeya.smartcontract.model.data.model;


public class ChangePassword {

    private String currentPassword;
    private String newPassword;
    private String confirmPassword;


    public ChangePassword(String currentPassword, String newPassword, String confirmPassword) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

}
