package com.gebeya.smartcontract.model.transaction;

import java.util.List;

public abstract class Manager<T extends TransactionEntity> {

    private List<T> entities;

    private void add(T entity) {
        entities.add(entity);
    }

    private T get(String id) {
        for (T entity : entities) {
            if (entity.getId().equals(id)) {
                return entity;
            }
        }
        return null;
    }
}
