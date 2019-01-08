package com.paige.service.apigateway.apiconfig;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ApiServiceInfo implements Serializable {

    private String baseurl;
    List<CommonProperty> get;
    List<CommonProperty> post;
    List<CommonProperty> put;
    List<CommonProperty> del;

}

