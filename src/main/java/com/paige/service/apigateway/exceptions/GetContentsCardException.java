package com.paige.service.apigateway.exceptions;

public class GetContentsCardException extends Exception {

    public GetContentsCardException(final String message, final Throwable throwable) {
        super(message, throwable);
    }

    public GetContentsCardException(final String message) {
        super(message);
    }
}
