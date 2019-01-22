package com.gebeya.smartcontract.model.data.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.security.acl.Owner;
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
    private UserDTO mUserDTO;
    @SerializedName("brand")
    @Expose
    private String brand;
    @SerializedName("Model")
    @Expose
    private String model;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

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

    public UserDTO getUserDTO() {
        return mUserDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        mUserDTO = userDTO;
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

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
