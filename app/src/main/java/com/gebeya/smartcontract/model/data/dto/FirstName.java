package com.gebeya.smartcontract.model.data.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FirstName {
    @SerializedName("$regex")
    @Expose
    private String $regex;

    public String get$regex() {
        return $regex;
    }

    public void set$regex(String $regex) {
        this.$regex = $regex;
    }
}
