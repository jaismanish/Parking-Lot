package com.main.exception;

public class InvalidRegistrationNumException extends IllegalArgumentException{
    public InvalidRegistrationNumException(String msg){
        super(msg);
    }
}
