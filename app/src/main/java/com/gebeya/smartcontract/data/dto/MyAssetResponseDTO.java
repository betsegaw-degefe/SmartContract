package com.gebeya.smartcontract.data.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyAssetResponseDTO {
    @SerializedName("data")
    @Expose
    public List<CarDTO> data = null;

    public List<CarDTO> getData() {
        return data;
    }

    public void setData(List<CarDTO> data) {
        this.data = data;
    }
}
