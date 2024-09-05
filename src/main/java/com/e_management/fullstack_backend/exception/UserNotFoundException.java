package com.e_management.fullstack_backend.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(Long id){
        super("User Not Found with id number: " + id);
    }
}
