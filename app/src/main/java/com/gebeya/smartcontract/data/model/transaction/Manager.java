package com.gebeya.smartcontract.data.model.transaction;

import java.util.ArrayList;
import java.util.List;

public abstract class Manager<T extends TransactionEntity> {

    private List<T> entities;

    public Manager() {
        entities = new ArrayList<>();
        load();
    }

    protected abstract void load();

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

    private synchronized void update(T entity) {
        if (entities.equals(entity)) {
            entities.remove(entity);
            entities.add(entity);
        }
    }

    private void remove(T entity) {
        entities.remove(entity);
    }

    public int count() {
        return entities.size();
    }

    public boolean empty() {
        return entities.isEmpty();
    }
}
