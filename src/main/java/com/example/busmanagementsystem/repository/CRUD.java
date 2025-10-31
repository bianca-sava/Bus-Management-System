package com.example.busmanagementsystem.repository;


import java.util.Map;

public interface CRUD<T> {

    public boolean create(T entity);
    public Map<String, T> findAll();
    public T findById(String id);
    public boolean update(String id,  T entity);
    public boolean delete(String id);
}
