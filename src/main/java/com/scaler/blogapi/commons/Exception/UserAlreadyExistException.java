package com.scaler.blogapi.commons.Exception;

public class UserAlreadyExistException extends Exception{
    public UserAlreadyExistException(String userName){
        super("Username "+userName+" already exists");
    }
}
