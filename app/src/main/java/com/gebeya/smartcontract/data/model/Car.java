package com.gebeya.smartcontract.data.model;

import android.support.annotation.Nullable;

import com.gebeya.smartcontract.data.dto.CarDTO;

import java.util.List;

public class Car {
    private String id;
    private Integer yearOfManufactured;
    private List<List<String>> pictures = null;
    private String owner;
    private String brand;
    private String model;
    private String createdAt;

    /*public Car(CarDTO carDTO) {

        this.id = carDTO.getId();
        this.yearOfManufactured = carDTO.getYearOfManufactured();
        this.pictures = carDTO.getPictures();
        this.owner = carDTO.getOwner();
        this.brand = carDTO.getBrand();
        this.model = carDTO.getModel();
        this.createdAt = carDTO.getCreatedAt();
    }*/

    public Integer getYearOfManufactured() {
        return yearOfManufactured;
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

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Car) {
            Car other = (Car)obj;
            return other.getId().equals(id);
        }
        return false;
    }

}
