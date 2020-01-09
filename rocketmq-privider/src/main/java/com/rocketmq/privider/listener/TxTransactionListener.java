package com.rocketmq.privider.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.context.annotation.Configuration;

/**
 * @author Mr丶s
 * @date 2020/1/9 11:31
 * @description
 */
@Slf4j
@Configuration
public class TxTransactionListener implements TransactionListener {

    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt msg) {
        log.info("check:" + new String(msg.getBody()));
        return LocalTransactionState.COMMIT_MESSAGE;
    }

    @Override
    public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        log.info("execute:" + new String(msg.getBody()));
        return LocalTransactionState.COMMIT_MESSAGE;
    }
}
