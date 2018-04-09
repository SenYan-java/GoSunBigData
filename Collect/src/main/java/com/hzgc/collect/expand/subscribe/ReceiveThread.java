package com.hzgc.collect.expand.subscribe;

import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * 人脸抓拍订阅功能及人脸抓拍演示功能定时刷新及去除过期数据任务
 */
public class ReceiveThread extends ReceiveIpcIds implements Serializable {
    private static Logger LOG = Logger.getLogger(ReceiveThread.class);
    private FtpSubscriptionClient ftpSubscriptionClient;
    private ReceiveIpcIds object = ReceiveIpcIds.getInstance();

    public ReceiveThread() {
        ftpSubscriptionClient = new FtpSubscriptionClient(ZookeeperParam.SESSION_TIMEOUT, ZookeeperParam.zookeeperAddress, ZookeeperParam.PATH_SUBSCRIBE, ZookeeperParam.WATCHER);
        ftpSubscriptionClient.createConnection(ZookeeperParam.zookeeperAddress, ZookeeperParam.SESSION_TIMEOUT);
    }

    private boolean isInDate(String time) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MONTH, -6);
        long endTime = now.getTimeInMillis();
        long startTime = Long.parseLong(time);
        return startTime <= endTime;
    }

    public void start() {
        Thread thread = new Thread() {
            public void run() {
                while (true) {
                    map_ZKData = ftpSubscriptionClient.getData();
                    object.setIpcIdList(map_ZKData);
                    for (String userId : map_ZKData.keySet()) {
                        Map<String, List<String>> map = map_ZKData.get(userId);
                        for (String time : map.keySet()) {
                            if (isInDate(time)) {
                                ftpSubscriptionClient.delete(ZookeeperParam.PATH_SUBSCRIBE + "/" + userId);
                            }
                        }
                    }
                    LOG.info("Ftp Subscription, ipcIdList:" + object.getIpcIdList());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        LOG.error("ReceiveThread thread error!");
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
        LOG.info("The face snapshot subscription function starts successfully!");
    }
}
