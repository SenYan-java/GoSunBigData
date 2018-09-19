package com.hzgc.service.people.dao;

import com.hzgc.service.people.model.PeopleRecognize;

import java.util.List;

public interface PeopleRecognizeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PeopleRecognize record);

    int insertSelective(PeopleRecognize record);

    PeopleRecognize selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PeopleRecognize record);

    int updateByPrimaryKey(PeopleRecognize record);

    List<PeopleRecognize> searchCapture1Month(Long peopleid);
}