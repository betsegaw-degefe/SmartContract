package com.gebeya.smartcontract.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Transaction {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("carId")
    @Expose
    private Car car;
    @SerializedName("to")
    @Expose
    private To to;
    @SerializedName("from")
    @Expose
    private From from;
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("houseId")
    @Expose
    private House house;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Car getCar() {
        return car;
    }

    public void setCarId(Car car) {
        this.car = car;
    }

    public To getTo() {
        return to;
    }

    public void setTo(To to) {
        this.to = to;
    }

    public From getFrom() {
        return from;
    }

    public void setFrom(From from) {
        this.from = from;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public House getHouseId() {
        return house;
    }

    public void setHouseId(House house) {
        this.house = house;
    }
}
