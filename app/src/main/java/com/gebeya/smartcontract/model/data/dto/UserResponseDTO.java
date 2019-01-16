package com.gebeya.smartcontract.model.data.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserResponseDTO {
    @SerializedName("user")
    @Expose
    private UserDTO user;
    @SerializedName("token")
    @Expose
    private String token;

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
