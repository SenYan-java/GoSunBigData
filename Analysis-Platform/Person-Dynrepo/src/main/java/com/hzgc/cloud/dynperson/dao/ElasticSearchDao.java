package com.hzgc.cloud.dynperson.dao;

import com.hzgc.common.service.facedynrepo.PersonTable;
import com.hzgc.common.service.personattribute.bean.PersonAttribute;
import com.hzgc.common.service.personattribute.bean.PersonAttributeValue;
import com.hzgc.common.service.search.util.DeviceToIpcs;
import com.hzgc.common.util.es.ElasticSearchHelper;
import com.hzgc.cloud.dynperson.bean.CaptureOption;
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

@Repository
@Slf4j
public class ElasticSearchDao {
    private TransportClient esClient;

    public ElasticSearchDao(@Value("${es.cluster.name}") String clusterName,
                            @Value("${es.hosts}") String esHost,
                            @Value("${es.cluster.port}") String esPort) {
        this.esClient = ElasticSearchHelper.getEsClient(clusterName, esHost, Integer.parseInt(esPort));
    }

    public SearchResponse getCaptureHistory(CaptureOption captureOption, String sortParam) {
        BoolQueryBuilder queryBuilder = createBoolQueryBuilder(captureOption);
        setArea(queryBuilder, captureOption);
        SearchRequestBuilder requestBuilder = createSearchRequestBuilder()
                .setQuery(queryBuilder)
                .setFrom(captureOption.getStart())
                .setSize(captureOption.getLimit())
                .addSort(PersonTable.TIMESTAMP,
                        Objects.equals(sortParam, EsSearchParam.DESC) ? SortOrder.DESC : SortOrder.ASC);
        return requestBuilder.get();
    }

    private SearchRequestBuilder createSearchRequestBuilder() {
        return esClient.prepareSearch(PersonTable.DYNAMIC_INDEX)
                .setTypes(PersonTable.PERSON_INDEX_TYPE);
    }

    private BoolQueryBuilder createBoolQueryBuilder(CaptureOption option) {
        // 最终封装成的boolQueryBuilder 对象。
        BoolQueryBuilder totalBQ = QueryBuilders.boolQuery();
        // 筛选行人属性
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (option.getAttributes() != null && option.getAttributes().size() > 0) {
            setAttribute(totalBQ, option.getAttributes());
        }
        // 开始时间和结束时间存在的时候的处理
        if (option.getStartTime() != null && option.getEndTime() != null &&
                !option.getStartTime().equals("") && !option.getEndTime().equals("")) {
            setStartEndTime(totalBQ, option.getStartTime(), option.getEndTime());
        }
        return totalBQ;
    }

    private void setStartEndTime(BoolQueryBuilder totalBQ, String startTime, String endTime) {
        totalBQ.must(QueryBuilders.rangeQuery(PersonTable.TIMESTAMP).gte(startTime).lte(endTime));
    }

    private void setArea(BoolQueryBuilder totalBQ, CaptureOption option) {
        BoolQueryBuilder areaBQ = QueryBuilders.boolQuery();
        if (option.getDevices().size() > 0) {
            List<String> ipcs = DeviceToIpcs.getIpcs(option.getDevices());
            for (String t : ipcs) {
                areaBQ.should(QueryBuilders.matchPhraseQuery(PersonTable.IPCID, t));
            }
            totalBQ.must(areaBQ);
            return;
        }
        if (option.getCommunity().size() > 0) {
            List<String> ipcs = DeviceToIpcs.getIpcs(option.getCommunity());
            for (String t : ipcs) {
                areaBQ.should(QueryBuilders.matchPhraseQuery(PersonTable.COMMUNITYID, t));
            }
            totalBQ.must(areaBQ);
            return;
        }
        if (option.getRegion().size() > 0) {
            List<String> ipcs = DeviceToIpcs.getIpcs(option.getCommunity());
            for (String t : ipcs) {
                areaBQ.should(QueryBuilders.matchPhraseQuery(PersonTable.REGIONID, t));
            }
            totalBQ.must(areaBQ);
            return;
        }
        if (option.getCity().size() > 0) {
            List<String> ipcs = DeviceToIpcs.getIpcs(option.getCommunity());
            for (String t : ipcs) {
                areaBQ.should(QueryBuilders.matchPhraseQuery(PersonTable.CITYID, t));
            }
            totalBQ.must(areaBQ);
            return;
        }
        if (option.getProvince().size() > 0) {
            List<String> ipcs = DeviceToIpcs.getIpcs(option.getCommunity());
            for (String t : ipcs) {
                areaBQ.should(QueryBuilders.matchPhraseQuery(PersonTable.PROVINCEID, t));
            }
        }
        totalBQ.must(areaBQ);
    }

    private void setAttribute(BoolQueryBuilder totalBQ, List <PersonAttribute> attributes) {
        // 筛选行人属性
        for (PersonAttribute attribute : attributes) {
            String identify = attribute.getIdentify().toLowerCase();
            List <PersonAttributeValue> attributeValues = attribute.getValues();
            BoolQueryBuilder attributeBuilder = QueryBuilders.boolQuery();
            for (PersonAttributeValue attributeValue : attributeValues) {
                String desc = attributeValue.getDesc();
                if (PersonTable.KNAPSACK.equals(desc) || PersonTable.SHOULDERBAG.equals(desc) || PersonTable.UMBRELLA.equals(desc)) {
                    continue;
                }
                String attr = attributeValue.getCode();
                if (null != attr) {
                    attributeBuilder.should(QueryBuilders.matchQuery(identify, attr));
                }
            }
            totalBQ.must(attributeBuilder);
        }
    }
}
