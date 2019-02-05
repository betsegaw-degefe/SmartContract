package com.gebeya.smartcontract.model.data.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CarTransactionDTO {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("carId")
    @Expose
    private CarDTO carId;
    @SerializedName("to")
    @Expose
    private ToDTO to;
    @SerializedName("from")
    @Expose
    private FromDTO from;
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CarDTO getCar() {
        return carId;
    }

    public void setCar(CarDTO carId) {
        this.carId = carId;
    }

    public ToDTO getTo() {
        return to;
    }

    public void setTo(ToDTO to) {
        this.to = to;
    }

    public FromDTO getFrom() {
        return from;
    }

    public void setFrom(FromDTO from) {
        this.from = from;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
