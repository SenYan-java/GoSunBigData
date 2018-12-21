package com.hzgc.cloud.dynperson.controller;

import com.hzgc.common.service.error.RestErrorCode;
import com.hzgc.common.service.response.ResponseResult;
import com.hzgc.common.service.rest.BigDataPath;
import com.hzgc.common.service.search.bean.Device;
import com.hzgc.common.service.search.util.DeviceToIpcs;
import com.hzgc.common.util.basic.UuidUtil;
import com.hzgc.common.util.json.JacksonUtil;
import com.hzgc.cloud.dynperson.bean.CaptureOption;
import com.hzgc.cloud.dynperson.bean.CaptureResult;
import com.hzgc.cloud.dynperson.bean.SingleResults;
import com.hzgc.cloud.dynperson.service.DynpersonHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@Api(tags = "行人服务")
@Slf4j
@SuppressWarnings("unused")
public class DynpersonController {

    @Autowired
    @SuppressWarnings("unused")
    private DynpersonHistoryService dynpersonHistoryService;


    @ApiOperation(value = "行人抓拍历史查询", response = ResponseResult.class)
    @RequestMapping(value = BigDataPath.CAPTURE_HISTORY, method = RequestMethod.POST)
    public ResponseResult<CaptureResult> getCaptureHistory(
            @RequestBody @ApiParam(value = "行人抓拍查询入参") CaptureOption captureOption){
        if (null == captureOption){
            log.info("CaptrueOption is null,please check");
            return ResponseResult.error(RestErrorCode.ILLEGAL_ARGUMENT);
        }
        if ((null == captureOption.getDevices() || captureOption.getDevices().size() <= 0) &&
                (null == captureOption.getCommunity() || captureOption.getCommunity().size() <= 0) &&
                (null == captureOption.getRegion() || captureOption.getRegion().size() <= 0) &&
                (null == captureOption.getCity() || captureOption.getCity().size() <= 0) &&
                (null == captureOption.getProvince() || captureOption.getProvince().size() <= 0)) {
            log.info("Query vehilce history, but devices or communityid or regionid or cityid or provinceid is null");
            return ResponseResult.error(RestErrorCode.ILLEGAL_ARGUMENT,"查询条件有误");
        }
        log.info("Start capture history, search option is:" + JacksonUtil.toJson(captureOption));
        SingleResults searchResultList = dynpersonHistoryService.getCaptureHistory(captureOption);
        CaptureResult captureResult = new CaptureResult();
        captureResult.setSingleResults(searchResultList);
        captureResult.setSearchId(UuidUtil.getUuid());

        return ResponseResult.init(captureResult,searchResultList.getTotal());
    }


}
