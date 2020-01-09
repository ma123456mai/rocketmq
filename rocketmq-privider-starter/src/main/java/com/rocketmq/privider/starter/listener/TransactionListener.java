package com.rocketmq.privider.starter.listener;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;

/**
 * @author Mr丶s
 * @date 2020/1/9 11:14
 * @description
 */
@Slf4j
@RocketMQTransactionListener(txProducerGroup = "group1")
// TODO 参数：txProducerGroup 需要同 rocketMQTemplate.sendMessageInTransaction(txProducerGroup....)方法中参数一致
public class TransactionListener implements RocketMQLocalTransactionListener {


    /**
     * 事物消息 执行本地
     *
     * @param msg
     * @param arg
     * @return
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        // local transaction process, return bollback, commit or unknown
        log.info("executeLocalTransaction:" + JSON.toJSONString(msg));
        return RocketMQLocalTransactionState.COMMIT;
    }

    /**
     * 事物消息状态：UNKNOWN时  定时查询确认提交本地事物
     *
     * @param msg
     * @return
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        log.info("checkLocalTransaction:" + JSON.toJSONString(msg));
        return RocketMQLocalTransactionState.COMMIT;
    }
}
