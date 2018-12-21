package com.hzgc.cloud.dynrepo.service;

import com.hzgc.common.collect.util.CollectUrlUtil;
import com.hzgc.common.service.api.bean.CameraQueryDTO;
import com.hzgc.common.service.api.bean.UrlInfo;
import com.hzgc.common.service.api.service.InnerService;
import com.hzgc.common.service.api.service.PlatformService;
import com.hzgc.common.service.facedynrepo.FaceTable;
import com.hzgc.common.service.response.ResponseResult;
import com.hzgc.cloud.dynrepo.bean.CaptureOption;
import com.hzgc.cloud.dynrepo.bean.CapturedPicture;
import com.hzgc.cloud.dynrepo.bean.SingleCaptureResult;
import com.hzgc.cloud.dynrepo.dao.ElasticSearchDao;
import com.hzgc.cloud.dynrepo.dao.EsSearchParam;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class CaptureHistoryService {
    @Autowired
    @SuppressWarnings("unused")
    private ElasticSearchDao elasticSearchDao;
    @Autowired
    @SuppressWarnings("unused")
    private CaptureServiceHelper captureServiceHelper;
    @Autowired
    private PlatformService platformService;
    @Autowired
    private InnerService innerService;

    public ResponseResult<List<SingleCaptureResult>> getCaptureHistory(CaptureOption option) {
        String sortParam = EsSearchParam.DESC;
        log.info("The current query don't needs to be grouped by ipcid");
        return getCaptureHistory(option, sortParam);
    }

    private ResponseResult<List<SingleCaptureResult>> getCaptureHistory(CaptureOption option, String sortParam) {
        List<SingleCaptureResult> results = new ArrayList<>();
        SingleCaptureResult singleResult = new SingleCaptureResult();
        List<CapturedPicture> captureList = new ArrayList<>();
        SearchResponse searchResponse = elasticSearchDao.getCaptureHistory(option, sortParam);
        SearchHits searchHits = searchResponse.getHits();
        SearchHit[] hits = searchHits.getHits();
        CapturedPicture capturePicture;
        if (hits.length > 0) {
            for (SearchHit hit : hits) {
                capturePicture = new CapturedPicture();
                String sabsolutepath = (String) hit.getSource().get(FaceTable.SABSOLUTEPATH);
                String babsolutepath = (String) hit.getSource().get(FaceTable.BABSOLUTEPATH);
                String ipc = (String) hit.getSource().get(FaceTable.IPCID);
                String timestamp = (String) hit.getSource().get(FaceTable.TIMESTAMP);
                String hostname = (String) hit.getSource().get(FaceTable.HOSTNAME);
                UrlInfo urlInfo = innerService.hostName2Ip(hostname);
                //属性参数封装
                int age = (int) hit.getSource().get(FaceTable.AGE);
                int gender = (int) hit.getSource().get(FaceTable.GENDER);
                int mask = (int) hit.getSource().get(FaceTable.MASK);
                int huzi = (int) hit.getSource().get(FaceTable.HUZI);
                int eyeglasses = (int) hit.getSource().get(FaceTable.EYEGLASSES);
                Object o = hit.getSource().get(FaceTable.SHARPNESS);
                int sharpness = 0;
                if (null != o) {
                    sharpness = (int) hit.getSource().get(FaceTable.SHARPNESS);
                }
                capturePicture.setAge(age);
                capturePicture.setGender(gender);
                capturePicture.setMask(mask);
                capturePicture.setHuzi(huzi);
                capturePicture.setEyeglasses(eyeglasses);
                capturePicture.setSharpness(sharpness);
                capturePicture.setSabsolutepath(CollectUrlUtil.toHttpPath(urlInfo.getIp(), urlInfo.getPort(), sabsolutepath));
                capturePicture.setBabsolutepath(CollectUrlUtil.toHttpPath(urlInfo.getIp(), urlInfo.getPort(), babsolutepath));
                CameraQueryDTO cameraQueryDTO = getLocation(ipc);
                capturePicture.setLocation(cameraQueryDTO.getRegion() + cameraQueryDTO.getCommunity());
                capturePicture.setTimeStamp(timestamp);
                capturePicture.setDeviceId(ipc);
                capturePicture.setDeviceName(cameraQueryDTO.getCameraName());
                captureList.add(capturePicture);
            }
        }
        singleResult.setTotal((int) searchHits.getTotalHits());
        singleResult.setPictures(captureList);
        results.add(singleResult);

        return ResponseResult.init(results,(int) searchHits.getTotalHits());
    }

    private CameraQueryDTO getLocation(String ipc) {
        //查询相机位置
        ArrayList<String> list = new ArrayList<>();
        list.add(ipc);
        Map<String, CameraQueryDTO> cameraInfoByBatchIpc = platformService.getCameraInfoByBatchIpc(list);
        return cameraInfoByBatchIpc.get(ipc);
    }
}