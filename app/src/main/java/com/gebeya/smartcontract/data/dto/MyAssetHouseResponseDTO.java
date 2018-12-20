package com.gebeya.smartcontract.data.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyAssetHouseResponseDTO {

    @SerializedName("data")
    @Expose
    private List<HouseDTO> data = null;


    public List<HouseDTO> getHouseData() {
        return data;
    }

    public void setHouseData(List<HouseDTO> data) {
        this.data = data;
    }

}
