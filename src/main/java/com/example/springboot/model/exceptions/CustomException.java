package com.example.springboot.model.exceptions;

public class CustomException extends IllegalArgumentException {
    public CustomException(String msg)
    {
        super(msg);
    }
}
