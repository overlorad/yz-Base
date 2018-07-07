package com.yzfar.www.base.support.mq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.yzfar.www.base.util.MqUtil;
import com.yzfar.www.base.util.ObjectUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * @version:1.0.0
 * @Description: （mq实现）
 * @Author: chengpeng
 * @Date: 11:18 2018/5/28
 */
public class MqUtilImpl implements MqUtil {
	protected Logger logger = LogManager.getLogger();
	private JmsTemplate jmsQueueTemplate;
	private JmsTemplate jmsTopicTemplate;

	public void setJmsQueueTemplate(JmsTemplate jmsQueueTemplate) {
		this.jmsQueueTemplate = jmsQueueTemplate;
	}

	public void setJmsTopicTemplate(JmsTemplate jmsTopicTemplate) {
		this.jmsTopicTemplate = jmsTopicTemplate;
	}

	private class ThreadRunable implements Runnable {
		String topicName;
		Object message;
		boolean topic;
		boolean isByte;

		ThreadRunable(String topicName, final Object message, boolean topic, boolean isByte) {
			this.topicName = topicName;
			this.message = message;
			this.topic = topic;
			this.isByte = isByte;
		}

		public void run() {
			generateSend(topicName, message, topic, isByte);
		}
	}

	private MessageCreator getMessageCreator(final Object message, boolean isByte) {
		if (isByte) {
			MessageCreator msg = new MessageCreator() {
				public Message createMessage(Session session) throws JMSException {
					BytesMessage message = session.createBytesMessage();
					message.writeBytes(ObjectUtil.objectToByte(message));
					return message;
				}
			};
			return msg;
		} else {
			MessageCreator msg = new MessageCreator() {
				public Message createMessage(Session session) throws JMSException {
					return session.createTextMessage(
							JSON.toJSONString(message, SerializerFeature.DisableCircularReferenceDetect));
				}
			};
			return msg;
		}
	}

	private void generateSend(String topicName, Object message, boolean topic, boolean isByte) {
		MessageCreator messageCreator = getMessageCreator(message, isByte);
		try {
			if (topic) {
				jmsTopicTemplate.send(topicName, messageCreator);
			} else {
				jmsQueueTemplate.send(topicName, messageCreator);
			}
			logger.info("MQ发送-->[{}]", topicName);
		} catch (Exception e) {
			logger.error("MQ发送--->[{}]失败：{}", topicName, e);
		}
	}

	@Override
	public void sendQueue(String topicName, Object message, boolean synch) {
		if (!synch) {
			new Thread(new ThreadRunable(topicName, message, false, false)).start();
		} else {
			generateSend(topicName, message, false, false);
		}
	}

	@Override
	public void sendTopic(String topicName, Object message, boolean synch) {
		if (!synch) {
			new Thread(new ThreadRunable(topicName, message, true, false)).start();
		} else {
			generateSend(topicName, message, true, false);
		}

	}

	@Override
	public void sendByteQueue(String topicName, Object message, boolean synch) {
		if (!synch) {
			new Thread(new ThreadRunable(topicName, message, false, true)).start();
		} else {
			generateSend(topicName, message, false, true);
		}
	}

	@Override
	public void sendByteTopic(String topicName, Object message, boolean synch) {
		if (!synch) {
			new Thread(new ThreadRunable(topicName, message, true, true)).start();
		} else {
			generateSend(topicName, message, true, true);
		}
	}

}
