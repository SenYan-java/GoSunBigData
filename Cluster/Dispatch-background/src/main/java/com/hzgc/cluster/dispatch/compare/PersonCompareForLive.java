package com.hzgc.cluster.dispatch.compare;


import com.hzgc.cluster.dispatch.cache.CaptureCache;
import com.hzgc.cluster.dispatch.cache.TableCache;
import com.hzgc.cluster.dispatch.dao.DispatchRecognizeMapper;
import com.hzgc.cluster.dispatch.model.DispatchAlive;
import com.hzgc.cluster.dispatch.model.DispatchRecognize;
import com.hzgc.cluster.dispatch.producer.AlarmMessage;
import com.hzgc.cluster.dispatch.producer.Producer;
import com.hzgc.common.collect.bean.PersonObject;
import com.hzgc.common.collect.util.CollectUrlUtil;
import com.hzgc.common.service.api.bean.CameraQueryDTO;
import com.hzgc.common.service.api.service.PlatformService;
import com.hzgc.common.util.basic.UuidUtil;
import com.hzgc.common.util.json.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class PersonCompareForLive implements Runnable{
    private boolean action;
    @Autowired
    private CaptureCache captureCache;
    @Autowired
    private TableCache tableCache;
    @Autowired
    private DispatchRecognizeMapper dispatureRecognizeMapper;
    @Autowired
    private PlatformService platformService;
    @Autowired
    private Producer producer;
    @Value("${kafka.topic.dispatch-show}")
    private String topic;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public PersonCompareForLive(){
        action = true;
    }

    @Override
    public void run() {
        while (action) {
            long start = System.currentTimeMillis();
            List<PersonObject> personObjects = captureCache.getPersonObjectForLive();
            if(personObjects.size() == 0){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }

            List<String> ipcIds = new ArrayList<>();
            for(PersonObject personObject : personObjects){
                ipcIds.add(personObject.getIpcId());
            }

            Map<String, CameraQueryDTO> map = platformService.getCameraInfoByBatchIpc(ipcIds);

            for(PersonObject personObject : personObjects){
                DispatchAlive dispachAliveRule = tableCache.getDispachAlive(personObject.getIpcId());
                if(dispachAliveRule == null){
                    continue;
                }

                String captachTime = personObject.getTimeStamp();
                try {
                    long time = sdf.parse(captachTime).getTime();
                    if(dispachAliveRule.getStartTime().getTime() < time && dispachAliveRule.getEndTime().getTime() > time){
                        DispatchRecognize dispatureRecognize = new DispatchRecognize();
                        dispatureRecognize.setDispatchId("111111");
                        dispatureRecognize.setId(UuidUtil.getUuid().substring(0, 32));
                        dispatureRecognize.setDeviceId(personObject.getIpcId());
                        dispatureRecognize.setType(4);
                        String surl = CollectUrlUtil.toHttpPath(personObject.getHostname(), "2573", personObject.getsAbsolutePath());
                        String burl = CollectUrlUtil.toHttpPath(personObject.getHostname(), "2573", personObject.getbAbsolutePath());
                        dispatureRecognize.setSurl(surl);
                        dispatureRecognize.setBurl(burl);
                        dispatureRecognize.setRecordTime(sdf.parse(personObject.getTimeStamp()));
                        dispatureRecognize.setCreateTime(new Date());
                        try {
                            dispatureRecognizeMapper.insertSelective(dispatureRecognize);
                        }catch (Exception e){
                            e.printStackTrace();
                            log.error(e.getMessage());
                        }

                        AlarmMessage alarmMessage = new AlarmMessage();
                        String ip = personObject.getIp();
                        alarmMessage.setBCaptureImage(CollectUrlUtil.toHttpPath(ip, "2573", personObject.getbAbsolutePath()));
                        alarmMessage.setCaptureImage(CollectUrlUtil.toHttpPath(ip, "2573", personObject.getsAbsolutePath()));
                        alarmMessage.setDeviceId(personObject.getIpcId());
                        alarmMessage.setDeviceName(map.get(personObject.getIpcId()).getCameraName());
                        alarmMessage.setType(4);
                        alarmMessage.setTime(personObject.getTimeStamp());
                        producer.send(topic, JacksonUtil.toJson(alarmMessage));
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            log.info("The size of person compared for live is " + personObjects.size() + " , the time is " + (System.currentTimeMillis() - start));
        }
    }
}
