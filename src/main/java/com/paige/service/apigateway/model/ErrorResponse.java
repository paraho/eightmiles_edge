package com.paige.service.apigateway.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ErrorResponse {

    private final Integer error_code;
    private final String error_msg;

    @JsonCreator
    public ErrorResponse(@JsonProperty("error_code") final Integer errorCode,
                        @JsonProperty("error_msg") final String errorMsg) {
        this.error_code = errorCode;
        this.error_msg = errorMsg;
    }

    @JsonCreator
    public ErrorResponse(@JsonProperty("error") final String error) {
        this.error_msg = error;
        this.error_code = HttpStatus.INTERNAL_SERVER_ERROR.value();
    }

    public String getError() {
        return error_msg;
    }
}
