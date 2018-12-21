package com.hzgc.cloud.dyncar.util;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EsUtils {

    public static void esTOes (String srcClusterName,String srcIndexName,String srcIp,int srcPort,String tagClusterName,String tagIndexName,String tagTypeName,String tagIp,int tagPort) throws Exception{
        Settings srcSettings = Settings.builder()
                .put("cluster.name",srcClusterName)
                .build();
        PreBuiltTransportClient srcClient = new PreBuiltTransportClient(srcSettings);
        srcClient.addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress(srcIp,srcPort)));

        Settings tagSettings = Settings.builder()
                .put("cluster.name", tagClusterName)
                .build();
        PreBuiltTransportClient tagClient = new PreBuiltTransportClient(tagSettings);
        tagClient.addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress(tagIp,tagPort)));

        SearchResponse scrollResp = srcClient.prepareSearch(srcIndexName)
                .setScroll(new TimeValue(1000))
                .setSize(1000)
                .execute().actionGet();

        BulkRequestBuilder bulk = tagClient.prepareBulk();
        ExecutorService executor = Executors.newFixedThreadPool(5);
        while(true) {
            bulk = tagClient.prepareBulk();
            final BulkRequestBuilder bulk_new = bulk;
            System.out.println("查询条数=" + scrollResp.getHits().getHits().length);
            for (SearchHit hit : scrollResp.getHits().getHits()) {
                hit.getSource().get("");
                IndexRequest req = tagClient.prepareIndex().setIndex(tagIndexName)
                        .setType(tagTypeName).setSource(hit.getSourceAsMap()).request();
                bulk_new.add(req);
            }
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    bulk_new.execute();
                }
            });
            Thread.sleep(100);
            scrollResp = srcClient.prepareSearchScroll(scrollResp.getScrollId())
                    .setScroll(new TimeValue(1000)).execute().actionGet();
            if (scrollResp.getHits().getHits().length == 0) {
                break;
            }
        }
        //该方法在加入线程队列的线程执行完之前不会执行
        executor.shutdown();
        System.out.println("执行结束");
        tagClient.close();
        srcClient.close();
    }
}
