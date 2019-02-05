package com.gebeya.smartcontract.model.data.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CarTransactionHistoryResponseDTO {
    @SerializedName("data")
    @Expose
    public List<CarTransactionDTO> data = null;

    public List<CarTransactionDTO> getData() {
        return data;
    }

    public void setData(List<CarTransactionDTO> data) {
        this.data = data;
    }
}
