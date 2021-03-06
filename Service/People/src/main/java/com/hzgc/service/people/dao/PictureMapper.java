package com.hzgc.service.people.dao;

import com.hzgc.service.people.model.Picture;
import com.hzgc.service.people.model.PictureWithBLOBs;
import org.apache.ibatis.annotations.CacheNamespace;

import java.util.List;

@CacheNamespace
public interface PictureMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PictureWithBLOBs record);

    int insertSelective(PictureWithBLOBs record);

    PictureWithBLOBs selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PictureWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(PictureWithBLOBs record);

    int updateByPrimaryKey(Picture record);

    List<PictureWithBLOBs> selectByPeopleId(String peopleid);

    PictureWithBLOBs selectPictureById(Long id);

    List<PictureWithBLOBs> selectPictureByPeopleId(String peopleid);

    List<Long> selectIdByPeopleId(String peopleid);

    Long getPictureIdByPeopleId(String peopleid);
}