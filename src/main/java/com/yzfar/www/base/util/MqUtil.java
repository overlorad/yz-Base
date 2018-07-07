package com.yzfar.www.base.util;

/**
 * @version:1.0.0
 * @Description: （描述）
 * @Author: chengpeng
 * @Date: 10:55 2018/5/28
 */
public interface MqUtil {
    /**
     * 队列:发送text文本 json格式
     */
    void sendQueue(String topicName, final Object message, boolean synch);

    /**
     * 队列:发送byte数组
     */
    void sendByteQueue(String topicName, final Object message, boolean synch);

    /**
     * 广播:发送text文本 json格式
     */
    void sendTopic(String topicName, final Object message, boolean synch);

    /**
     * 广播:发送byte数组
     */
    void sendByteTopic(String topicName, final Object message, boolean synch);
}
