package com.hzgc.common.service.search.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class Device implements Serializable {

    private String deviceCode;
    private String deviceName;
}
