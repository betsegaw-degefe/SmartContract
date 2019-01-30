package com.gebeya.smartcontract.model.data.dto;

import com.gebeya.smartcontract.model.data.model.To;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransactionDetailCarResponseDTO {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("carId")
    @Expose
    private CarDTO carId;
    @SerializedName("to")
    @Expose
    private To to;
    @SerializedName("from")
    @Expose
    private Object from;
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CarDTO getCarId() {
        return carId;
    }

    public void setCarId(CarDTO carId) {
        this.carId = carId;
    }

    public To getTo() {
        return to;
    }

    public void setTo(To to) {
        this.to = to;
    }

    public Object getFrom() {
        return from;
    }

    public void setFrom(Object from) {
        this.from = from;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
