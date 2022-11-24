package com.sampledomain.bank.exception;

/**
 * The Exception will throw when null object returns by a method.
 */
public class ResourceNotFoundException extends Exception{
    public ResourceNotFoundException() {
    }

    /**
     * @param message The message to display the exception description and cause.
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
