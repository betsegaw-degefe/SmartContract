package com.gebeya.smartcontract.data.model;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PublicLedgerResponse {

    @SerializedName("data")
    @Expose
    private List<Transaction> data = null;

    public List<Transaction> getData() {
        return data;
    }

    public void setData(List<Transaction> data) {
        this.data = data;
    }
}
