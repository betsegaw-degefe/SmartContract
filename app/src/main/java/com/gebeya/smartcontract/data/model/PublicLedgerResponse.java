package com.gebeya.smartcontract.data.model;

import com.gebeya.smartcontract.data.dto.PublicLedgerResponseDTO;
import com.gebeya.smartcontract.data.dto.TransactionDTO;

import java.util.List;

public class PublicLedgerResponse  {

    private List<Transaction> mdata = null;


    public List<Transaction> getData() {
        return mdata;
    }

    public void setData(List<Transaction> data) {
        this.mdata = data;
    }
}
