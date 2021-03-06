package com.hzgc.service.community.dao;

import com.hzgc.service.community.model.FusionImsi;
import org.apache.ibatis.annotations.CacheNamespace;

import java.util.List;

@CacheNamespace
public interface FusionImsiMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FusionImsi record);

    int insertSelective(FusionImsi record);

    FusionImsi selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FusionImsi record);

    int updateByPrimaryKey(FusionImsi record);

    List<FusionImsi> searchCapture1Month(String peopleid);
}