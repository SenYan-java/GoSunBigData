package com.hzgc.service.community.dao;

import com.hzgc.service.community.model.PeopleRecognize;
import com.hzgc.service.community.param.CaptureDetailsDTO;
import com.hzgc.service.community.param.ImportantPeopleRecognize;
import com.hzgc.service.community.param.ImportantRecognizeSearchParam;
import org.apache.ibatis.annotations.CacheNamespace;

import java.util.List;

@CacheNamespace
public interface PeopleRecognizeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PeopleRecognize record);

    int insertSelective(PeopleRecognize record);

    PeopleRecognize selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PeopleRecognize record);

    int updateByPrimaryKey(PeopleRecognize record);

    List<PeopleRecognize> searchCapture1Month(String peopleid);

    PeopleRecognize searchCommunityOutPeopleLastCapture(String peopleid);

    List<PeopleRecognize> searchCommunityNewPeopleCaptureData(CaptureDetailsDTO dto);

    String getSurlByPeopleId(String peopleid);

    List<ImportantPeopleRecognize> importantPeopleRecognize(ImportantRecognizeSearchParam search);
}