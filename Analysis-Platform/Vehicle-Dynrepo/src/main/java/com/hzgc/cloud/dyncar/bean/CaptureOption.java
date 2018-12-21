package com.hzgc.cloud.dyncar.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hzgc.common.service.search.bean.Device;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 抓拍查询入参
 */
@Data
public class CaptureOption implements Serializable {
    // 开始日期,格式：xxxx-xx-xx xx:xx:xx
    private String startTime;
    // 截止日期,格式：xxxx-xx-xx xx:xx:xx
    private String endTime;
    //搜索的设备IPC列表
    private List<Device> devices;
    // 属性
    private List<VehicleAttribute> attributes;
    // 车牌
    private String plate_licence;
    // 车标
    private String brand_name;
    //社区
    private List<Device> community;
    //区域
    private List<Device> region;
    //市区
    private List<Device> city;
    //省份
    private List<Device> province;
    // 排序参数
    private List<Integer> sort;
    // 分页查询开始行
    private int start;
    // 查询条数
    private int limit;
}
