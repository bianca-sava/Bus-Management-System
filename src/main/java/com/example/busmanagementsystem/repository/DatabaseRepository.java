package com.example.busmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class DatabaseRepository<T> implements CRUD<T> {

    private final JpaRepository<T, String> jpaRepository;

    protected DatabaseRepository(JpaRepository<T, String> jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    protected abstract String getIdFromEntity(T entity);

    @Override
    public boolean create(T entity) {
        if (jpaRepository.existsById(getIdFromEntity(entity))) {
            throw new RuntimeException("Entity already exists");
        }
        jpaRepository.save(entity);
        return true;
    }

    @Override
    public Map<String, T> findAll() {
        return jpaRepository.findAll().stream()
                .collect(Collectors.toMap(this::getIdFromEntity, e -> e));
    }

    @Override
    public T findById(String id) {
        return jpaRepository.findById(id).orElse(null);
    }

    @Override
    public boolean update(String id, T entity) {
        if (jpaRepository.existsById(id)) {
            jpaRepository.save(entity);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        if (jpaRepository.existsById(id)) {
            jpaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}