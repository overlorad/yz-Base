package com.yzfar.www.base.util;

import java.util.Collection;

/**
 * 发送
 */
public interface SendUtil {
	/**
	 * 发送邮箱
	 * 
	 * @param subject
	 *            主题
	 * @param content
	 *            邮件内容
	 * @param toList
	 *            目的邮件地址
	 */
	void sendEmail(String subject, String content, String... toList);

	/**
	 * 测试邮箱发送
	 */
	boolean testEmail();

	/**
	 * 发送邮箱
	 * 
	 * @param subject
	 *            主题
	 * @param content
	 *            邮件内容
	 * @param toList
	 *            目的邮件地址
	 */
	void sendEmail(String subject, String content, Collection<String> toList);

	/**
	 * 发送微信
	 * 
	 * @param content
	 *            内容
	 * @param toList
	 *            接受微信号
	 */
	void sendWeChat(String content, String... toList);

	/**
	 * 测试微信发送
	 */
	boolean testWeChat();

	/**
	 * 发送微信
	 * 
	 * @param content
	 *            内容
	 * @param toList
	 *            接受微信号
	 * @return
	 */
	void sendWeChat(String content, Collection<String> toList);

	void setMailHostName(String mailHostName);

	void setMailPassWord(String mailPassWord);

	void setMailUserName(String mailUserName);

	void setWeChatAgentid(String weChatAgentid);

	void setWeChatCorpid(String weChatCorpid);

	void setWeChatCorpsecret(String weChatCorpsecret);

	void setWeChatCreateSessionUrl(String weChatCreateSessionUrl);

	void setWeChatAccessTokenUrl(String weChatAccessTokenUrl);

}
