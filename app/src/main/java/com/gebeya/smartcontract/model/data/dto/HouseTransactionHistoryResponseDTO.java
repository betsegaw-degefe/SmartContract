package com.gebeya.smartcontract.model.data.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HouseTransactionHistoryResponseDTO {
    @SerializedName("data")
    @Expose
    public List<HouseTransactionDTO> data = null;

    public List<HouseTransactionDTO> getData() {
        return data;
    }

    public void setData(List<HouseTransactionDTO> data) {
        this.data = data;
    }
}
