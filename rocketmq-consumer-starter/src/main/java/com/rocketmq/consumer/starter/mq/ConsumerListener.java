package com.rocketmq.consumer.starter.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

/**
 * @author Mr丶s
 * @date 2020/1/9 11:09
 * @description 普通消息或顺序消息消费
 */
@Slf4j
@Service
@RocketMQMessageListener(
        topic = "test-topic-1",                     //topic 名称
        consumerGroup = "group2",                   //分组名
        consumeMode = ConsumeMode.CONCURRENTLY,     //发送模式，异步接收或有序接收
        messageModel = MessageModel.BROADCASTING,   //并发模式，广播或集群
        selectorExpression = "tag1",                //选择需要接受的消息 tag
        selectorType = SelectorType.TAG)            //控制如何选择消息
public class ConsumerListener implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        log.info("received message: {}", message);
    }
}
