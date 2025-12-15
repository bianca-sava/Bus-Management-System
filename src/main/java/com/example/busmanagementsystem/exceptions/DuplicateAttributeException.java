package com.example.busmanagementsystem.exceptions;

public class DuplicateAttributeException extends RuntimeException {

    private final String attributeName;

    public DuplicateAttributeException(String attributeName, String message) {
        super(message);
        this.attributeName = attributeName;
    }

    public String getAttributeName() {
        return attributeName;
    }
}
