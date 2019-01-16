package com.gebeya.smartcontract.model.data.transform;

import com.gebeya.smartcontract.model.data.dto.CarDTO;
import com.gebeya.smartcontract.model.data.model.Car;

public class CarTransform {
    CarDTO mCarDTO = new CarDTO();
    Car mCar = new Car();

    public CarTransform() {
        Integer getYearOfManufactured = mCar.getYearOfManufactured();
        getYearOfManufactured = mCarDTO.getYearOfManufactured();
    }
}
