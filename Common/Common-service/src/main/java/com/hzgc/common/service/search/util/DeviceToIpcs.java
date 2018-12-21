package com.hzgc.common.service.search.util;


import com.hzgc.common.service.search.bean.Device;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeviceToIpcs {

    //设备转ipcs
    public static List<String> getIpcs(List<Device> deviceList){
        ArrayList <String> list = new ArrayList <>();
        for (Device device:deviceList){
            list.add(device.getDeviceCode());
        }
        return list;
    }

    //ipcid和设备名称映射关系
    public static Map<String, Device> getIpcMapping(List<Device> deviceList){
        HashMap <String, Device> ipcMapping = new HashMap <>();
        for (Device device:deviceList){
            ipcMapping.put(device.getDeviceCode(),device);
        }
        return ipcMapping;
    }
}
