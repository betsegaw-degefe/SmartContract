package com.gebeya.smartcontract.model.data.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransactionDetailHouseResponseDTO {
    @SerializedName("_id")
    @Expose
    private String id;

    @SerializedName("houseId")
    @Expose
    private HouseDTO house;

    @SerializedName("from")
    @Expose
    private FromDTO from;

    @SerializedName("to")
    @Expose
    private ToDTO to;

    @SerializedName("created_at")
    @Expose
    private String createdAt;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public HouseDTO getHouse() {
        return house;
    }

    public void setHouseId(HouseDTO house) {
        this.house = house;
    }

    public FromDTO getFrom() {
        return from;
    }

    public void setFrom(FromDTO from) {
        this.from = from;
    }

    public ToDTO getTo() {
        return to;
    }

    public void setTo(ToDTO to) {
        this.to = to;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}
