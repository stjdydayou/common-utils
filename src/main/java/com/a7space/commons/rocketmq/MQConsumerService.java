package com.a7space.commons.rocketmq;


import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;

public abstract class MQConsumerService {
	private Logger logger = LoggerFactory.getLogger(MQConsumerService.class);
    
    private DefaultMQPushConsumer consumer;
    
    @Value("${rocketmq.namesrv.addr}")
    private String namesrvAddr;
    private String consumerGroupName;
    private String consumerInstanceName;
    private String consumerTopicName;
    private String consumerTagName;
    
    public String getNamesrvAddr() {
        return namesrvAddr;
    }

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }

    public String getConsumerGroupName() {
        return consumerGroupName;
    }

    public void setConsumerGroupName(String consumerGroupName) {
        this.consumerGroupName = consumerGroupName;
    }

    public String getConsumerInstanceName() {
        return consumerInstanceName;
    }
    public void setConsumerInstanceName(String consumerInstanceName) {
        this.consumerInstanceName = consumerInstanceName;
    }

    public String getConsumerTopicName() {
        return consumerTopicName;
    }

    /**
     *  Created on 2016年11月10日 
     * <p>Discription:[消息队列--用来传输消息体的管道名称，比如交易管道----Topic_Trade]</p>
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     * @param consumerTopicName
     */
    public void setConsumerTopicName(String consumerTopicName) {
        this.consumerTopicName = consumerTopicName;
    }

    public String getConsumerTagName() {
        return consumerTagName;
    }

    /**
     *  Created on 2016年11月10日 
     * <p>Discription:[消息队列--消息体标签，比如发送短信----Tag_SendPhoneMessage,多个可能用||连接]</p>
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     * @param consumerTagName
     */
    public void setConsumerTagName(String consumerTagName) {
        this.consumerTagName = consumerTagName;
    }
    
    

    @PostConstruct
    private void postConstruct() {
        // TODO Auto-generated method stub
        try{
            if(StringUtils.isBlank(consumerGroupName)){
                logger.error("consumerGroupName不能为空！");
                return;
            }
            if(StringUtils.isBlank(namesrvAddr)){
                logger.error("rocketMQ_NamesrvAddr不能为空，请在配置文件configuration.properties中补充rocketMQ_NamesrvAddr=消息队列服务器地址");
                return;
            }
            if(StringUtils.isBlank(consumerTopicName)){
                logger.error("consumerTopicName不能为空！");
                return;
            }
            if(StringUtils.isBlank(consumerTagName)){
                logger.error("consumerTagName不能为空！");
                return;
            }
            if(consumer==null){
                consumer = new DefaultMQPushConsumer(consumerGroupName);
                consumer.setNamesrvAddr(namesrvAddr);
                if(StringUtils.isNotEmpty(consumerInstanceName)){
                    consumer.setInstanceName(consumerInstanceName);
                }
                consumer.setVipChannelEnabled(false);
                consumer.subscribe(consumerTopicName, consumerTagName);
                consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
                
                consumer.registerMessageListener(new MessageListenerConcurrently() {
                    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,ConsumeConcurrentlyContext context) {
                        try{
                            boolean exeFlag=true;
                            for (MessageExt msg : msgs) {
                                String msgTag=msg.getTags();
                                String msgBody=new String(msg.getBody(),"UTF-8");
                                logger.debug("consumer get message tag="+msgTag+" body=" + msgBody);
                                exeFlag=consumerMessage(msgTag,msgBody);
                            }
                            if(exeFlag){
                                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                            }else{
                                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                            }
                        }catch(Exception e){
                            logger.error("consumer message error:{}",e);
                            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                        }
                    }
                });
                consumer.start();
            }
            
        }catch(Exception e){
            logger.error("rocketmq error:{}",e);
        }
    }

    @PreDestroy
    private void preDestroy() {
        // TODO Auto-generated method stub
        try{
            if(consumer!=null){
                consumer.shutdown();
                logger.info("MQConsumerService preDestroy: consumer.shutdown");
            }
        }catch (Exception e) {
            logger.error("MQConsumerService consumer shutdown error:",e);
        }
    }
    
    /**
     *  Created on 2016年11月10日 
     * <p>Discription:[配置消息消费者相关配置]</p>
     * @param groupName 消费者所属组
     * @param topicName 用来传输消息体的管道名称，比如交易管道----Topic_Trade
     * @param tagName   消息体标签，比如发送短信----Tag_SendPhoneMessage,多个可能用||连接
     */
    public void setConsumerConfig(String groupName,String topicName,String tagName){
        setConsumerGroupName(groupName);
        setConsumerTopicName(topicName);
        setConsumerTagName(tagName);
    }

    /**
     *  Created on 2016年11月10日 
     * <p>Discription:[消息消费者处理辑]</p>
     * @param msgTag 消息体标签
     * @param msgBody 消息体内容
     * @return true--表示该条消息处理成功，false--表示该条消息处理失败，服务器会稍后再推送这条消息。
     */
    public abstract boolean consumerMessage(String msgTag,String msgBody);
}
