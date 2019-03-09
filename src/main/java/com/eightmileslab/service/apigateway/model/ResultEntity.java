package com.eightmileslab.service.apigateway.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResultEntity {

    @JsonCreator
    public ResultEntity(@JsonProperty("error_code") Integer error_code
            , @JsonProperty("error_msg") String error_msg
            , @JsonProperty("result") Object result) {
        this.error_code = error_code;
        this.error_msg = error_msg;
        this.result = result;

    }

    Integer error_code;
    String error_msg;
    Object result;
}