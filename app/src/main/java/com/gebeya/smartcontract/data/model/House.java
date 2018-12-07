package com.gebeya.smartcontract.data.model;

import android.support.annotation.Nullable;

import com.gebeya.smartcontract.data.dto.HouseDTO;

import java.util.List;

public class House {

    private List<List<Double>> geoLocation = null;
    private List<List<String>> pictures = null;
    private String id;
    private String owner;
    private Integer areaM2;
    private String createdAt;

    public House(HouseDTO houseDTO) {
        this.geoLocation = houseDTO.getGeoLocation();
        this.pictures = houseDTO.getPictures();
        this.id = houseDTO.getId();
        this.owner = houseDTO.getOwner();
        this.areaM2 = houseDTO.getAreaM2();
        this.createdAt = houseDTO.getCreatedAt();
    }

    public List<List<Double>> getGeoLocation() {
        return geoLocation;
    }

    public List<List<String>> getPictures() {
        return pictures;
    }

    public String getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public Integer getAreaM2() {
        return areaM2;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof House) {
            House other = (House)obj;
            return other.getId().equals(id);
        }
        return false;
    }
}
