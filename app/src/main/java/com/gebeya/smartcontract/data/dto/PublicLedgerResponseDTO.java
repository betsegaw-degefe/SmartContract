package com.gebeya.smartcontract.data.dto;


import java.util.List;

import com.gebeya.smartcontract.data.model.PublicLedgerResponse;
import com.gebeya.smartcontract.data.model.Transaction;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PublicLedgerResponseDTO{

    @SerializedName("data")
    @Expose
    public List<TransactionDTO> data = null;

    public List<TransactionDTO> getData() {
        return data;
    }

    public void setData(List<TransactionDTO> data) {
        this.data = data;
    }
}
