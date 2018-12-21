package com.hzgc.cloud.dynrepo.controller;

import com.hzgc.common.service.error.RestErrorCode;
import com.hzgc.common.service.faceattribute.service.AttributeService;
import com.hzgc.common.service.response.ResponseResult;
import com.hzgc.common.service.rest.BigDataPath;
import com.hzgc.common.util.basic.UuidUtil;
import com.hzgc.common.util.json.JacksonUtil;
import com.hzgc.cloud.dynrepo.bean.*;
import com.hzgc.cloud.dynrepo.service.CaptureHistoryService;
import com.hzgc.cloud.dynrepo.service.CaptureSearchService;
import com.hzgc.cloud.dynrepo.service.CaptureServiceHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "动态库服务")
@Slf4j
@SuppressWarnings("unused")
public class CaptureSearchController {

    @Autowired
    @SuppressWarnings("unused")
    private AttributeService attributeService;
    @Autowired
    @SuppressWarnings("unused")
    private CaptureHistoryService captureHistoryService;
    @Autowired
    @SuppressWarnings("unused")
    private CaptureSearchService captureSearchService;
    @Autowired
    @SuppressWarnings("unused")
    private CaptureServiceHelper captureServiceHelper;

    /**
     * 以图搜图
     *
     * @param searchOption 以图搜图入参
     * @return SearchResult
     */
    @ApiOperation(value = "以图搜图", response = SearchResult.class)
    @RequestMapping(value = BigDataPath.DYNREPO_SEARCH, method = RequestMethod.POST)
    @SuppressWarnings("unused")
    public ResponseResult<SearchResult> searchPicture(
            @RequestBody @ApiParam(value = "以图搜图查询参数") SearchOption searchOption) throws SQLException {
        SearchResult searchResult;
        if (searchOption == null) {
            log.error("Start search picture, but search option is null");
            return ResponseResult.error(RestErrorCode.ILLEGAL_ARGUMENT);
        }

        if (searchOption.getImages() == null) {
            log.error("Start search picture, but images is null");
            return ResponseResult.error(RestErrorCode.ILLEGAL_ARGUMENT);
        }

        if (searchOption.getSimilarity() < 0.0) {
            log.error("Start search picture, but threshold is error");
            return ResponseResult.error(RestErrorCode.ILLEGAL_ARGUMENT);
        }
        if ((null == searchOption.getDevices() || searchOption.getDevices().size() <= 0) &&
                (null == searchOption.getCommunity() || searchOption.getCommunity().size() <= 0) &&
                (null == searchOption.getRegion() || searchOption.getRegion().size() <= 0) &&
                (null == searchOption.getCity() || searchOption.getCity().size() <= 0) &&
                (null == searchOption.getProvince() || searchOption.getProvince().size() <= 0)) {
            log.info("Query vehilce history, but devices or communityid or regionid or cityid or provinceid is null");
            return ResponseResult.error(RestErrorCode.ILLEGAL_ARGUMENT,"查询条件有误");
        }
        log.info("Start search picture, search option is:" + JacksonUtil.toJson(searchOption));
        searchResult = captureSearchService.searchPicture2(searchOption, UuidUtil.getUuid());
        return ResponseResult.init(searchResult);
    }

    /**
     * 历史搜索记录查询
     *
     * @param searchResultOption 以图搜图入参
     * @return SearchResult
     */
    @ApiOperation(value = "获取历史搜图结果", response = SearchResult.class)
    @RequestMapping(value = BigDataPath.DYNREPO_SEARCHRESULT, method = RequestMethod.POST)
    @SuppressWarnings("unused")
    public ResponseResult<SearchResult> getSearchResult(
            @RequestBody @ApiParam(value = "历史搜图结果查询参数") SearchResultOption searchResultOption) {
        SearchResult searchResult;
        if (searchResultOption != null) {
            searchResult = captureSearchService.getSearchResult(searchResultOption);
        } else {
            searchResult = null;
        }
        return ResponseResult.init(searchResult);
    }

    /**
     * 抓拍历史记录查询
     *
     * @param captureOption 以图搜图入参
     * @return List<SearchResult>
     */
    @ApiOperation(value = "抓拍历史查询", response = SearchResult.class, responseContainer = "List")
    @ApiImplicitParam(name = "searchOption", value = "抓拍历史查询参数", paramType = "body")
    @RequestMapping(value = BigDataPath.DYNREPO_HISTORY, method = RequestMethod.POST)
    @SuppressWarnings("unused")
    public ResponseResult<List<SingleCaptureResult>> getCaptureHistory(
            @RequestBody @ApiParam(value = "以图搜图入参") CaptureOption captureOption) {
        if (captureOption == null) {
            log.error("Start query capture history, capture option is null");
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
        log.info("Start query capture history, search option is:" + JacksonUtil.toJson(captureOption));
        return captureHistoryService.getCaptureHistory(captureOption);
    }

    /**
     * 查询设备最后一次抓拍时间
     *
     * @param deviceId 设备ID
     * @return 最后抓拍时间
     */
    @ApiOperation(value = "查询设备最后一次抓拍时间", response = String.class)
    @ApiImplicitParam(name = "deviceId", value = "设备ID", paramType = "query")
    @RequestMapping(value = BigDataPath.DYNREPO_CAPTURE_LASTTIME, method = RequestMethod.GET)
    public ResponseResult<String> getLastCaptureTime(@RequestParam("deviceId") String deviceId) {
        if (StringUtils.isBlank(deviceId)) {
            log.error("Start query last capture time, deviceId option is null");
        }
        log.info("Start query last capture time, deviceId option is:" + deviceId);
        String time = captureSearchService.getLastCaptureTime(deviceId);
        return ResponseResult.init(time);
    }
}
