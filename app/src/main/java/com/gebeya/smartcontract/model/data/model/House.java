package com.gebeya.smartcontract.model.data.model;

import android.support.annotation.Nullable;

import com.gebeya.smartcontract.model.data.dto.HouseDTO;

import java.util.List;

public class House {

    private List<List<Double>> geoLocation = null;
    private List<List<String>> pictures = null;
    private String id;
    private String owner;
    private Integer areaM2;
    private String createdAt;


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
