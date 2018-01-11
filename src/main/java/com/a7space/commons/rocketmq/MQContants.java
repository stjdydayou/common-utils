package com.a7space.commons.rocketmq;


/**
 * @author Administrator
 * 公共topic、tag常量
 */
public class MQContants {

	private static String TOPIC_XXX="";
	
	private static String TAG_XXX="";

}

/*
生产者
1.在spring配置文件中配置生产者
	<!--消息队列生产者:定单支付消息生产者-->
	<bean id="mqProducerService_bean名称"
		class="cn.com.besttone.rocketmq.service.MQProducerService"
		init-method="postConstruct" destroy-method="preDestroy">
	      <property name="groupName" value="MQGroup_交易生产者组名" />
	      <property name="namesrvAddr" value="${rocketMQ_NamesrvAddr}" />
	      <property name="instanceName" value="producer_交易生产者实例名" />
	</bean>
2.在要发送消息的地方调用
	mqProducerService_bean名称.sendMsg(mqProducerService_bean名称,"Topic_管道名称", "Tag_消息标记",null,消息内容);



消费者
import org.springframework.stereotype.Service;
import cn.com.besttone.rocketmq.service.MQConsumerService;
@Service
public class MQConsumerSendMessage extends MQConsumerService{    
    public MQConsumerSendMessage() {
        //设定消费者组名、消息接收管道、消息标记
        setConsumerConfig("MQGroup_交易消费者组名", "Topic_管道名称", "Tag_消息标记");
    }    
    @Override
    public boolean consumerMessage(String msgTag, String msgBody) {
        // TODO Auto-generated method stub
        //具体处理逻辑。处理成功返回true，失败返回false，消息队列会稍后再推送该消息。
        .....
        return true;
    }
}

*/
