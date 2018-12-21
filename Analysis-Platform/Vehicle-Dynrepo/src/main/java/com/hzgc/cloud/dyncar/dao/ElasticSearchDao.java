package com.hzgc.cloud.dyncar.dao;

import com.hzgc.common.service.search.util.DeviceToIpcs;
import com.hzgc.common.service.facedynrepo.VehicleTable;
import com.hzgc.common.util.es.ElasticSearchHelper;
import com.hzgc.seemmo.bean.carbean.CarData;
import com.hzgc.cloud.dyncar.bean.CaptureOption;
import com.hzgc.cloud.dyncar.bean.VehicleAttribute;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Slf4j
@Repository
public class ElasticSearchDao {
    private TransportClient esClient;


    public ElasticSearchDao(@Value("${es.cluster.name}") String clusterName,
                            @Value("${es.hosts}") String esHost,
                            @Value("${es.cluster.port}") String esPort) {
        this.esClient = ElasticSearchHelper.getEsClient(clusterName, esHost, Integer.parseInt(esPort));
    }

    //根据多个ipcid进行查询总共的
    public SearchResponse getCaptureHistory(CaptureOption option, String sortParam) {
        BoolQueryBuilder queryBuilder = createBoolQueryBuilder(option);
        setArea(queryBuilder, option);
        setPlate(queryBuilder,option);
        SearchRequestBuilder requestBuilder = createSearchRequestBuilder()
                .setQuery(queryBuilder)
                .setFrom(option.getStart())
                .setSize(option.getLimit())
                .addSort(VehicleTable.TIMESTAMP,
                        Objects.equals(sortParam, EsSearchParam.DESC) ? SortOrder.DESC : SortOrder.ASC);
        return requestBuilder.get();
    }

    //过滤商标和车牌
    private void setPlate(BoolQueryBuilder queryBuilder, CaptureOption option) {
        if (null != option.getPlate_licence() && option.getPlate_licence().length() > 0 &&
                null != option.getBrand_name() && option.getBrand_name().length() > 0) {
            queryBuilder.must(QueryBuilders.queryStringQuery(VehicleTable.PLATE_LICENCE + ":*" + option.getPlate_licence() + "*" +
                    " AND " + VehicleTable.BRAND_NAME + ":*" + option.getBrand_name() + "*"));
        }
        if (null != option.getPlate_licence() && option.getPlate_licence().length() > 0) {
            queryBuilder.must(QueryBuilders.queryStringQuery(VehicleTable.PLATE_LICENCE + ":*" + option.getPlate_licence() + "*"));
        }
        if (null != option.getBrand_name() && option.getBrand_name().length() > 0) {
            queryBuilder.must(QueryBuilders.queryStringQuery(VehicleTable.BRAND_NAME + ":*" + option.getBrand_name() + "*"));
        }
    }

    //设置index和type
    private SearchRequestBuilder createSearchRequestBuilder() {
        return esClient.prepareSearch(VehicleTable.INDEX)
                .setTypes(VehicleTable.TYPE);
    }

    //创建boolQuery对象
    private BoolQueryBuilder createBoolQueryBuilder(CaptureOption option) {
        // 最终封装成的boolQueryBuilder 对象。
        BoolQueryBuilder totalBQ = QueryBuilders.boolQuery();
        //筛选车辆属性
        if (option.getAttributes() != null && option.getAttributes().size() > 0
                || null != option.getBrand_name() && option.getBrand_name().length() > 0) {
            setAttribute(totalBQ, option);
        }

        // 开始时间和结束时间存在的时候的处理
        if (option.getStartTime() != null && option.getEndTime() != null &&
                !option.getStartTime().equals("") && !option.getEndTime().equals("")) {
            setStartEndTime(totalBQ, option.getStartTime(), option.getEndTime());
        }
        return totalBQ;
    }

    //时间过滤
    private void setStartEndTime(BoolQueryBuilder totalBQ, String startTime, String endTime) {
        totalBQ.must(QueryBuilders.rangeQuery(VehicleTable.TIMESTAMP).gte(startTime).lte(endTime));
    }

    //过滤省,市,区,区域,设备
    private void setArea(BoolQueryBuilder totalBQ, CaptureOption option) {
        BoolQueryBuilder areaBQ = QueryBuilders.boolQuery();
        if (option.getDevices().size() > 0) {
            List<String> ipcs = DeviceToIpcs.getIpcs(option.getDevices());
            for (String t : ipcs) {
                areaBQ.should(QueryBuilders.matchPhraseQuery(VehicleTable.IPCID, t));
            }
            totalBQ.must(areaBQ);
            return;
        }
        if (option.getCommunity().size() > 0) {
            List<String> ipcs = DeviceToIpcs.getIpcs(option.getCommunity());
            for (String t : ipcs) {
                areaBQ.should(QueryBuilders.matchPhraseQuery(VehicleTable.COMMUNITYID, t));
            }
            totalBQ.must(areaBQ);
            return;
        }
        if (option.getRegion().size() > 0) {
            List<String> ipcs = DeviceToIpcs.getIpcs(option.getCommunity());
            for (String t : ipcs) {
                areaBQ.should(QueryBuilders.matchPhraseQuery(VehicleTable.REGIONID, t));
            }
            totalBQ.must(areaBQ);
            return;
        }
        if (option.getCity().size() > 0) {
            List<String> ipcs = DeviceToIpcs.getIpcs(option.getCommunity());
            for (String t : ipcs) {
                areaBQ.should(QueryBuilders.matchPhraseQuery(VehicleTable.CITYID, t));
            }
            totalBQ.must(areaBQ);
            return;
        }
        if (option.getProvince().size() > 0) {
            List<String> ipcs = DeviceToIpcs.getIpcs(option.getCommunity());
            for (String t : ipcs) {
                areaBQ.should(QueryBuilders.matchPhraseQuery(VehicleTable.PROVINCEID, t));
            }
        }
        totalBQ.must(areaBQ);
    }

    //车辆属性过滤
    private void setAttribute(BoolQueryBuilder totalBQ, CaptureOption option) {

        List <VehicleAttribute> attributes = option.getAttributes();
        if (null != attributes && attributes.size() > 0) {
            for (VehicleAttribute attribute : attributes) {
                String attributeName = attribute.getAttributeName();
                if (CarData.VEHICLE_COLOR.equals(attributeName) || CarData.VEHICLE_TYPE.equals(attributeName)
                        || CarData.PLATE_DESTAIN_CODE.equals(attributeName) || CarData.RACK_CODE.equals(attributeName)) {
                    List <String> attributeValues = attribute.getAttributeCodes();
                    BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
                    if (null != attributeValues && attributeValues.size() > 0) {
                        for (String code : attributeValues) {
                            if (null != code) {
                                boolQueryBuilder.should(QueryBuilders.matchQuery(attributeName, code));
                            }
                        }
                    }
                    totalBQ.must(boolQueryBuilder);
                }
            }
        }
    }
}