package com.gebeya.smartcontract.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MakeTransactionModel {

    @SerializedName("carId")
    @Expose
    private String carId;
    @SerializedName("to")
    @Expose
    private String to;
    @SerializedName("from")
    @Expose
    private String from;


    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
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
