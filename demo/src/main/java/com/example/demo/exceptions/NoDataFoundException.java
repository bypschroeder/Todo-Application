package com.example.demo.exceptions;

public class NoDataFoundException extends RuntimeException{

    public NoDataFoundException(String message) {
        super(message);
    }
}
