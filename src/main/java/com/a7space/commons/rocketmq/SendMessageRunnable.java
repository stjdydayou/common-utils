package com.a7space.commons.rocketmq;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

/**
 * Created on 2016年8月26日
 * <p>Title:       开放平台_[通用]_[消息队列]/p>
 * <p>Description: [异步调用消息队列生产者发送消息]</p>
 * <p>Copyright:   Copyright (c) 2014</p>
 * @version        1.0
*/
public class SendMessageRunnable  implements Runnable{
    
	private Logger log = LoggerFactory.getLogger(SendMessageRunnable.class);
    
    private MQProducerService mqProducerService;
    private String topic;
    private String tag;
    private Long key;
    private Object msgBody;
    private Integer sleepSecond=0;
    
    /**
     *  Created on 2016年11月30日 
     * <p>Discription:[消息队列生产者--发送消息子线程]</p>
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     * @param mqProducerService 配置在spring中的消息队列生产者beanid
     * @param topic 消息传输的管道
     * @param tag 消息标签
     * @param msgBody 消息内容，如果是Object，会被JSONObject.toJSONString转成字符串再发送。
     * @param key 不为null时，发送有序消息
     */
    public SendMessageRunnable(MQProducerService mqProducerService,String topic, String tag, Object msgBody, Long key) {
        // TODO Auto-generated constructor stub
        this.mqProducerService=mqProducerService;
        this.topic=topic;
        this.tag=tag;
        this.key=key;
        this.msgBody=msgBody;
    }
    
    /**
     *  Created on 2016年11月30日 
     * <p>Discription:[消息队列生产者--发送消息子线程]</p>
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     * @param mqProducerService 配置在spring中的消息队列生产者beanid
     * @param topic 消息传输的管道
     * @param tag 消息标签
     * @param msgBody 消息内容，如果是Object，会被JSONObject.toJSONString转成字符串再发送。
     * @param key 不为null时，发送有序消息
     * @param sleepSecond 发送消息前暂时时间，单位为秒
     */
    public SendMessageRunnable(MQProducerService mqProducerService,String topic, String tag, Object msgBody,Long key,Integer sleepSecond) {
        // TODO Auto-generated constructor stub
        this.mqProducerService=mqProducerService;
        this.topic=topic;
        this.tag=tag;
        this.key=key;
        this.msgBody=msgBody;
        this.sleepSecond=sleepSecond;
    }
    
    @Override
    public void run() {
        // TODO Auto-generated method stub
            try {
                String msgStr ="";
                if(this.msgBody instanceof String){
                    msgStr=(String) this.msgBody;
                }else{
                    msgStr = JSONObject.toJSONString(this.msgBody);
                }
                
                if(StringUtils.isBlank(msgStr)){
                    log.info("MQProducerService producer skip body");
                    return;
                }
                String result=this.mqProducerService.sendMessage(this.topic, this.tag, msgStr, this.key, this.sleepSecond);
                
                log.info("MQProducerService producer send body:"+msgStr+" return:"+result);
            } catch (Exception e) {
                log.error("MQProducerService producer send error:",e);
            }
    }
    
}