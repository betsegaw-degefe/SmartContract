package com.gebeya.smartcontract.data.model.transaction;

import android.support.annotation.Nullable;

public class TransactionEntity {
    private String id;
    private String assetId;
    private String from;
    private String to;
    private String createdAt;

    public TransactionEntity(String id, String assetId, String from, String to, String createdAt) {
        this.id = id;
        this.assetId = assetId;
        this.from = from;
        this.to = to;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Nullable
    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj instanceof TransactionEntity){
            TransactionEntity other = (TransactionEntity) obj;
            return other.getId().equals(id);
        }

        return false;
    }
}
