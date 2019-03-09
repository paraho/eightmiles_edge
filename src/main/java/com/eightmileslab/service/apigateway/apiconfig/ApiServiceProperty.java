package com.eightmileslab.service.apigateway.apiconfig;

import lombok.Data;

/**
 * API 서비스 엔드포인트 정보
 * @author      snjeong
 */

@Data
public class ApiServiceProperty {

    private String baseurl;
    private String get;
    private String post;
    private String put;
    private String del;

}

