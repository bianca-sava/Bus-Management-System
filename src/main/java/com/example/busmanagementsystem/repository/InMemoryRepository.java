package com.example.busmanagementsystem.repository;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public abstract class InMemoryRepository<T> implements CRUD<T>{

    protected Map<String, T> database = new ConcurrentHashMap<>();

    protected abstract String getIdFromEntity(T entity);

    @Override
    public boolean create(T entity) {
        if(entity == null)
            return false;
        else if(database.containsKey(getIdFromEntity(entity))){
            throw  new RuntimeException("Entity already exists");
        }
        return (boolean) database.put(getIdFromEntity(entity), entity);
    }

    @Override
    public Map<String,T> findAll() {
        return database;
    }

    @Override
    public T findById(String id) {
        if(database.containsKey(id)){
            return database.get(id);
        }
        return null;
    }

    @Override
    public boolean update(String id, T entity) {
        if(database.containsKey(id)){
            database.put(id, entity);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        if(database.containsKey(id)){
            database.remove(id);
            return true;
        }
        return false;
    }
}
