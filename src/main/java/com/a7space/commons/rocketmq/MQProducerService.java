package com.a7space.commons.rocketmq;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.MessageQueueSelector;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageQueue;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.math.BigInteger;
import java.util.List;


/**
 * Created on 2016年8月16日
 * <p>Title:       [消息队列]_[生产者]/p>
 * <p>Description: [描述该类概要功能介绍]</p>
 * <p>Copyright:   Copyright (c) 2014</p>
 * @version        1.0
*/
public class MQProducerService {
    
    /*<bean id="mqProducerService" class="com.a7space.commons.rocketmq.MQProducerService" init-method="postConstruct" destroy-method="preDestroy">
        <property name="groupName" value="groupName" />
        <property name="namesrvAddr" value="192.168.36.236:9876" />
        <property name="instanceName" value="item_server_producer" />
        <property name="threadPoolTaskExecutor" ref="threadPoolTaskExecutor"/>
    </bean>*/
    
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;
	
    private DefaultMQProducer producer;
    
    private String groupName;
    
    private String namesrvAddr;
    
    private String instanceName;
    
    private int MaxMessageSize=1024*128;
    
    private Logger log = LoggerFactory.getLogger(MQProducerService.class);
    
    private void postConstruct() {    
        try {
            if(StringUtils.isBlank(namesrvAddr)){
                log.info("spring中MQProducerService的namesrvAddr不能为空，比如：namesvr ip:9876");
                return;
            }
            if(StringUtils.isBlank(instanceName)){
                log.info("spring中MQProducerService的instanceName不能为空，比如：item_server_producer");
                return;
            }
            producer = new DefaultMQProducer(groupName);
            producer.setNamesrvAddr(namesrvAddr);
            producer.setInstanceName(instanceName);
            producer.setMaxMessageSize(MaxMessageSize);
            producer.setVipChannelEnabled(false);
            producer.start();
            
            log.info("MQProducerService postConstruct: producer.start");
        } catch (MQClientException e) {
            // TODO Auto-generated catch block
            log.error("MQProducerService start error:",e);
        }
        
    } 
    
    private void preDestroy()  {
        try{
            if(producer!=null){
                producer.shutdown();
                log.info("MQProducerService preDestroy: producer.shutdown");
            }
        }catch (Exception e) {
            log.error("MQProducerService producer shutdown error:",e);
        }
    }
    
    /**
     *  Created on 2016年9月1日 
     * <p>Discription:[在子纯种中异步发送消息]</p>
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     * @param topic 消息线路
     * @param tag   消息标记
     * @param body  消息内容
     * @return
     */
    public void sendMessageAsync(String topic, String tag,Object body){
    	sendMessageAsync(topic,tag,body,null,null);
    }
    
    /**
     *  Created on 2016年9月1日 
     * <p>Discription:[在子纯种中异步发送消息]</p>
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     * @param topic 消息线路
     * @param tag   消息标记
     * @param body  消息内容
     * @param key key相同的消息，是有序的，由同一消费者处理，比如ID相同的订单，由同一消费者处理。null时，随机处理。
     * @return
     */
    public void sendMessageAsync(String topic, String tag,Object body,Long key){
    	sendMessageAsync(topic,tag,body,key,null);
    }
    /**
     *  Created on 2016年9月1日 
     * <p>Discription:[在子纯种中异步发送消息]</p>
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     * @param topic 消息线路
     * @param tag   消息标记
     * @param body  消息内容
     * @param key key相同的消息，是有序的，由同一消费者处理，比如ID相同的订单，由同一消费者处理。null时，随机处理。
     * @param sleepSecond 发送消息前，等待多少时间
     * @return
     */
    public void sendMessageAsync(String topic, String tag,Object body,Long key, Integer sleepSecond){
        try {
        	if(this.threadPoolTaskExecutor==null){
            	log.info("async=true时需要指定threadPoolTaskExecutor，请检查spring中bean装载的参数。");
            	return;
            }
        	Runnable runnable=new SendMessageRunnable(this,topic,tag,body,key,sleepSecond);
            this.threadPoolTaskExecutor.execute(runnable);
        }
        catch (Exception e) {
            log.error("MQProducerService send body="+body+" error:",e);
        }
    }
    
    /**
     *  Created on 2016年9月1日 
     * <p>Discription:[同步方式发送消息，不推荐]</p>
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     * @param topic 消息线路
     * @param tag   消息标记
     * @param body  消息内容
     * @return
     */
    public String sendMessage(String topic, String tag, Object body) {
        return sendMessage(topic,tag,body,null,null);
    }
    /**
     *  Created on 2016年9月1日 
     * <p>Discription:[同步方式发送消息，不推荐]</p>
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     * @param topic 消息线路
     * @param tag   消息标记
     * @param body  消息内容
     * @param key key相同的消息，是有序的，由同一消费者处理，比如ID相同的订单，由同一消费者处理。null时，随机处理。
     * @return
     */
    public String sendMessage(String topic, String tag,Object body,Long key){
    	return sendMessage(topic,tag,body,key,null);
    }
    /**
     *  Created on 2016年9月1日 
     * <p>Discription:[同步方式发送消息，不推荐]</p>
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     * @param topic 消息线路
     * @param tag   消息标记
     * @param body  消息内容
     * @param key key相同的消息，是有序的，由同一消费者处理，比如ID相同的订单，由同一消费者处理。null时，随机处理。
     * @param sleepSecond 发送消息前，等待多少时间
     * @return
     */
    public String sendMessage(String topic, String tag,Object body,Long key, Integer sleepSecond){
     // TODO Auto-generated method stub
        String result="";
        try {
            if(StringUtils.isBlank(topic)){
                return "topic不能为空";
            }
            if(StringUtils.isBlank(tag)){
                return "tag不能为空";
            }
            if(body==null){
                return "body不能为null";
            }
            if(producer==null){
                log.info("DefaultMQProducer 未初始化，请检查spring中bean装载的参数。");
                return "DefaultMQProducer 未初始化，请检查spring中bean装载的参数。";
            }
            String msgBody="";
            if(body instanceof String){
            	msgBody=(String)body;
            }else{
            	msgBody=JSONObject.toJSONString(body);
            }
            if(sleepSecond!=null && sleepSecond.intValue()>0){
                Thread.sleep(1000*sleepSecond.intValue());
            }

            Message msg = new Message(topic, tag, msgBody.getBytes("UTF-8"));
            SendResult sendResult=new SendResult();
            if(key==null){
                sendResult = producer.send(msg);
            }else{
                //相同的key，被同一消费者处理。比如商品订单
                sendResult = producer.send(msg, new MessageQueueSelector() {
                    public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                        Long id = (Long) arg;
                        Long mqSize=Long.parseLong(String.valueOf(mqs.size()));
                        BigInteger bid=BigInteger.valueOf(id);
                        BigInteger bmqSize=BigInteger.valueOf(mqSize);
                        
                        int index =bid.mod(bmqSize).intValue();
                        return mqs.get(index);
                    }
                }, key);
            }
            result=sendResult.toString();
            
            log.debug("MQProducerService producer send:"+result);
        }
        catch (Exception e) {
            log.error("MQProducerService send body="+body+" error:",e);
        }
        return result;
    }
    
    public ThreadPoolTaskExecutor getThreadPoolTaskExecutor() {
		return threadPoolTaskExecutor;
	}
    public void setThreadPoolTaskExecutor(ThreadPoolTaskExecutor threadPoolTaskExecutor) {
		this.threadPoolTaskExecutor = threadPoolTaskExecutor;
	}
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getNamesrvAddr() {
        return namesrvAddr;
    }

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public int getMaxMessageSize() {
        return MaxMessageSize;
    }

    public void setMaxMessageSize(int maxMessageSize) {
        MaxMessageSize = maxMessageSize;
    }
    
}