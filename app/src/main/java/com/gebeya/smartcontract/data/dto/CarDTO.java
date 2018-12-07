package com.gebeya.smartcontract.data.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CarDTO {

    @SerializedName("year_of_manufactured")
    @Expose
    private Integer yearOfManufactured;
    @SerializedName("pictures")
    @Expose
    private List<List<String>> pictures = null;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("owner")
    @Expose
    private String owner;
    @SerializedName("brand")
    @Expose
    private String brand;
    @SerializedName("Model")
    @Expose
    private String model;
    @SerializedName("created_at")
    @Expose
    private String createdAt;


    public Integer getYearOfManufactured() {
        return yearOfManufactured;
    }

    public void setYearOfManufactured(Integer yearOfManufactured) {
        this.yearOfManufactured = yearOfManufactured;
    }

    public List<List<String>> getPictures() {
        return pictures;
    }

    public void setPictures(List<List<String>> pictures) {
        this.pictures = pictures;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setUpdatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
