package com.scaler.blogapi.commons.Exception;

public class DuplicateEmailException extends Exception{
    public DuplicateEmailException(String email){
        super("Email "+ email +" already in use");
    }
}
