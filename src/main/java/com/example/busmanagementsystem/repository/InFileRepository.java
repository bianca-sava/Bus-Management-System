package com.example.busmanagementsystem.repository;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.locks.ReentrantLock;


@Repository
public abstract class InFileRepository<T> implements CRUD<T>{

    protected Map<String, T> database = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper;
    private final Path filePath;
    private final JavaType mapType;
    private final ReentrantLock fileLock = new ReentrantLock();

    protected InFileRepository(String filePath, ObjectMapper objectMapper, Class<T> entityClass) {
        this.objectMapper = objectMapper;
        this.filePath = Paths.get(filePath);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        this.mapType = objectMapper.getTypeFactory().constructMapType(ConcurrentHashMap.class, String.class, entityClass);

        loadData();
    }

    private void loadData() {
        try{
            if(Files.exists(filePath)){
                byte[] jsonData = Files.readAllBytes(filePath);
                if(jsonData.length > 0){
                    this.database = objectMapper.readValue(jsonData, mapType);
                }
                else{
                    this.database = new ConcurrentHashMap<>();
                }
            }
            else{
                this.database = new ConcurrentHashMap<>();
            }
        }
        catch (IOException e){
            this.database = new ConcurrentHashMap<>();
            throw new RuntimeException("Failed to load repository data from " + filePath.toAbsolutePath(), e);
        }
    }

    private void saveData(){
        fileLock.lock();
        try{
            byte[] jsonData = objectMapper.writeValueAsBytes(database);
            Files.write(
                    filePath,
                    jsonData,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
        }
        catch (IOException e){
            throw new RuntimeException("Failed to save repository data to " + filePath.toAbsolutePath(), e);
        } finally {
            fileLock.unlock();
        }
    }


    protected abstract String getIdFromEntity(T entity);

    @Override
    public boolean create(T entity) {
        if(entity == null)
            return false;
        else if(database.containsKey(getIdFromEntity(entity))){
            throw  new RuntimeException("Entity already exists");
        }
        database.put(getIdFromEntity(entity), entity);
        saveData();
        return true;
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
            saveData();
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        if(database.containsKey(id)){
            database.remove(id);
            saveData();
            return true;
        }
        return false;
    }
}
