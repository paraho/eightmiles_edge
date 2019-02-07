package com.paige.service.apigateway.exceptions;

import org.springframework.http.HttpStatus;

public class SessionException extends RuntimeException {

    private final SessionException.Reason reason;

    public SessionException(SessionException.Reason reason, String message) {
        this(reason, message, null);
    }

    public SessionException(SessionException.Reason reason, String message,
                                              Throwable cause) {
        super(message, cause);
        this.reason = reason;
    }

    /**
     * Return the status code that should be returned to the client.
     * @return the HTTP status code
     */
    public HttpStatus getStatusCode() {
        return getReason().getStatus();
    }

    /**
     * Return the reason why the authorization exception was thrown.
     * @return the reason
     */
    public SessionException.Reason getReason() {
        return this.reason;
    }

    /**
     * Reasons why the exception can be thrown.
     */
    public enum Reason {

        INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR),

        ACCESS_DENIED(HttpStatus.FORBIDDEN),

        INVALID_AUDIENCE(HttpStatus.UNAUTHORIZED),

        INVALID_ISSUER(HttpStatus.UNAUTHORIZED),

        INVALID_KEY_ID(HttpStatus.UNAUTHORIZED),

        INVALID_SIGNATURE(HttpStatus.UNAUTHORIZED),

        INVALID_TOKEN(HttpStatus.UNAUTHORIZED),

        MISSING_AUTHORIZATION(HttpStatus.UNAUTHORIZED),

        TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED),

        UNSUPPORTED_TOKEN_SIGNING_ALGORITHM(HttpStatus.UNAUTHORIZED),

        SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE);

        private final HttpStatus status;

        Reason(HttpStatus status) {
            this.status = status;
        }

        public HttpStatus getStatus() {
            return this.status;
        }

    }
}