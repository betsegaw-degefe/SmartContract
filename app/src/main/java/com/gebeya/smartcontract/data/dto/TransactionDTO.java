package com.gebeya.smartcontract.data.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransactionDTO {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("carId")
    @Expose
    private CarDTO car;
    @SerializedName("to")
    @Expose
    private ToDTO to;
    @SerializedName("from")
    @Expose
    private FromDTO from;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("houseId")
    @Expose
    private HouseDTO house;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CarDTO getCar() {
        return car;
    }

    public void setCar(CarDTO car) {
        this.car = car;
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

    public HouseDTO getHouse() {
        return house;
    }

    public void setHouse(HouseDTO house) {
        this.house = house;
    }
}
