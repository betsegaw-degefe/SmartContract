package com.gebeya.smartcontract.publicLedger.api.model;

public class dataAPIModel {
    private String createdAt;
    private FromModel from;
    private ToModel to;

    public String getCreatedAt() {
        return createdAt;
    }

    public com.gebeya.smartcontract.publicLedger.api.model.FromModel getFrom() {
        return from;
    }

    public com.gebeya.smartcontract.publicLedger.api.model.ToModel getTo() {
        return to;
    }
}
