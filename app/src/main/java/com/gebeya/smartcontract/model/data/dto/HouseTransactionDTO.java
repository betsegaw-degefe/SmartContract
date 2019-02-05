package com.gebeya.smartcontract.model.data.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HouseTransactionDTO {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("houseId")
    @Expose
    private HouseDTO houseId;
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

    public HouseDTO getHouse() {
        return houseId;
    }

    public void setHouse(HouseDTO houseId) {
        this.houseId = houseId;
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
