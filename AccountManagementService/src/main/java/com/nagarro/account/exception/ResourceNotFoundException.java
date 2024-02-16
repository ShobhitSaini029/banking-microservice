package com.nagarro.account.exception;

public class ResourceNotFoundException extends RuntimeException {

	// Add Extra properties that want to handle. 
    public ResourceNotFoundException(){
        super("Resource not found on server!!!");
    }
    public ResourceNotFoundException(String message){
    	super(message);
    	
    }
}
