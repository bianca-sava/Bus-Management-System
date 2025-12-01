package com.example.busmanagementsystem.service;

import jakarta.validation.ValidationException;

public interface Validate<T> {
    void validate(T obj) throws RuntimeException;
}
