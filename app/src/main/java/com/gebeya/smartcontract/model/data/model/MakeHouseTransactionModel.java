package com.gebeya.smartcontract.model.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MakeHouseTransactionModel {
    @SerializedName("houseId")
    @Expose
    private String houseId;
    @SerializedName("to")
    @Expose
    private String to;
    @SerializedName("from")
    @Expose
    private String from;


    public String getHouseIdId() {
        return houseId;
    }

    public void setHouseId(String carId) {
        this.houseId = carId;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
