package com.gebeya.smartcontract.data.transform;

import com.gebeya.smartcontract.data.dto.CarDTO;
import com.gebeya.smartcontract.data.model.Car;

public class CarTransform {
    CarDTO mCarDTO = new CarDTO();
    Car mCar = new Car();

    public CarTransform() {
        Integer getYearOfManufactured = mCar.getYearOfManufactured();
        getYearOfManufactured = mCarDTO.getYearOfManufactured();
    }
}
