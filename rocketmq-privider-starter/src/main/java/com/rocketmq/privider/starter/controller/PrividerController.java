package com.rocketmq.privider.starter.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Mr丶s
 * @date 2020/1/9 11:14
 * @description
 */
@RestController
@Slf4j
public class PrividerController {

    public static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 发送普通消息
     *
     * @param topic
     * @param msg
     * @return
     */
    @RequestMapping("/send/{topic}/{msg}")
    public String send(@PathVariable String topic, @PathVariable String msg) {
        rocketMQTemplate.convertAndSend(topic + ":tag1", msg + ":tag1");
        return "send";
    }

    /**
     * 发送顺序消息
     *
     * @param topic
     * @param msg
     * @return
     */
    @RequestMapping("/sendOrderly/{topic}/{msg}")
    public String sendOrderly(@PathVariable String topic, @PathVariable String msg) {
        for (int i = 0; i < 1; i++) {
            rocketMQTemplate.asyncSendOrderly(topic, msg + i, String.valueOf(1), new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    log.info("传输成功");
                    log.info(JSON.toJSONString(sendResult));
                }

                @Override
                public void onException(Throwable e) {
                    log.error("传输失败", e);
                }
            });
        }
        return "sendOrderly";
    }

    /**
     * 发送事物消息
     *
     * @param topic
     * @param msg
     * @return
     */
    @RequestMapping("/sendTransaction/{topic}/{msg}")
    public String sendTransaction(@PathVariable String topic, @PathVariable String msg) {
        Message message = MessageBuilder.withPayload(msg).build();
        System.out.println(Thread.currentThread().getName());

        TransactionSendResult result = rocketMQTemplate.sendMessageInTransaction("group1", topic, message, "test");

        System.out.println(result.getTransactionId());
        return "sendTransaction";
    }


}
