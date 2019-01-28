package com.gebeya.smartcontract.model.data.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchUserBodyDTO {
    @SerializedName("firstName")
    @Expose
    private FirstName firstName;

    public FirstName getFirstName() {
        return firstName;
    }

    public void setFirstName(FirstName firstName) {
        this.firstName = firstName;
    }
}
