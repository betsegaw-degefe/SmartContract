package com.gebeya.smartcontract.model.data.model;

import com.gebeya.smartcontract.model.data.dto.CarDTO;
import com.gebeya.smartcontract.model.data.dto.FromDTO;
import com.gebeya.smartcontract.model.data.dto.HouseDTO;
import com.gebeya.smartcontract.model.data.dto.ToDTO;
import com.gebeya.smartcontract.model.data.dto.TransactionDTO;

public class Transaction {
    private String id;
    private CarDTO car;
    private ToDTO to;
    private FromDTO from;
    private String createdAt;
    private HouseDTO house;

    public Transaction(TransactionDTO transactionDTO) {
        this.id = transactionDTO.getId();
        //this.car = transactionDTO.getCar();
        this.to = transactionDTO.getTo();
        this.from = transactionDTO.getFrom();
        this.createdAt = transactionDTO.getCreatedAt();
        this.house = transactionDTO.getHouse();
    }

    public String getId() {
        return id;
    }

    public CarDTO getCar() {
        return car;
    }

    public ToDTO getTo() {
        return to;
    }

    public FromDTO getFrom() {
        return from;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public HouseDTO getHouse() {
        return house;
    }

    public void setCar(CarDTO car) {
        this.car = car;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
