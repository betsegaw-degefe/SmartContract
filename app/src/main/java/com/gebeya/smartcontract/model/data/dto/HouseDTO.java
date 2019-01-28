package com.gebeya.smartcontract.model.data.dto;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HouseDTO {
    @SerializedName("geo_location")
    @Expose
    private List<List<Double>> geoLocation = null;
    @SerializedName("pictures")
    @Expose
    private List<List<String>> pictures = null;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("owner")
    @Expose
    private UserDTO mUserDTO;
    @SerializedName("area_m2")
    @Expose
    private Integer areaM2;
    @SerializedName("created_at")
    @Expose
    private String createdAt;




    public List<List<Double>> getGeoLocation() {
        return geoLocation;
    }

    public void setGeoLocation(List<List<Double>> geoLocation) {
        this.geoLocation = geoLocation;
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

    public Integer getAreaM2() {
        return areaM2;
    }

    public void setAreaM2(Integer areaM2) {
        this.areaM2 = areaM2;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }



}
