package com.hzgc.common.util.basic;

import java.io.Serializable;
import java.util.UUID;

public class UuidUtil implements Serializable {

    public static String getUuid() {
        return UUID.randomUUID()
                .toString()
                .replaceAll("-", "")
                .toUpperCase();
    }


}
