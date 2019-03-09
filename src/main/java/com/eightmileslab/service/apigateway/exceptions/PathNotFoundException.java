package com.eightmileslab.service.apigateway.exceptions;

public class PathNotFoundException extends Exception{

    public PathNotFoundException(final String message) {
        super(message);
    }
}
