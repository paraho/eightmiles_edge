package com.eightmileslab.service.apigateway.exceptions;

public class GetRemoteServiceException extends Exception {
    public GetRemoteServiceException(final String message, final Throwable throwable) {
        super(message, throwable);
    }

    public GetRemoteServiceException(final String message) {
        super(message);
    }
}
