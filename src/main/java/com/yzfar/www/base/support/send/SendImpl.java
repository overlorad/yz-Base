package com.yzfar.www.base.support.send;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.DefaultHttpParams;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.mail.util.MailSSLSocketFactory;
import com.yzfar.www.base.util.ListUtil;
import com.yzfar.www.base.util.SendUtil;

/**
 * Copyright © 2018 yzfar Info. Tech Ltd. All rights reserved.
 * 
 * @author: cp
 * @date: 2018年6月7日 下午5:55:21
 */
public class SendImpl implements SendUtil {
	protected Logger log = LogManager.getLogger();
	private String mailHostName;
	private String mailUserName;
	private String mailPassWord;
	private String weChatAccessTokenUrl;
	private String weChatCorpid;
	private String weChatCorpsecret;
	private String weChatCreateSessionUrl;
	private String weChatAgentid;
	private final String testSubject = "测试";
	private final String testcontent = "测试内容";

	@Override
	public void setWeChatCreateSessionUrl(String weChatCreateSessionUrl) {
		this.weChatCreateSessionUrl = weChatCreateSessionUrl;
	}

	@Override
	public void setWeChatAgentid(String weChatAgentid) {
		this.weChatAgentid = weChatAgentid;
	}

	@Override
	public void setWeChatCorpsecret(String weChatCorpsecret) {
		this.weChatCorpsecret = weChatCorpsecret;
	}

	@Override
	public void setWeChatAccessTokenUrl(String weChatAccessTokenUrl) {
		this.weChatAccessTokenUrl = weChatAccessTokenUrl;
	}

	@Override
	public void setWeChatCorpid(String weChatCorpid) {
		this.weChatCorpid = weChatCorpid;
	}

	@Override
	public void setMailHostName(String mailHostName) {
		this.mailHostName = mailHostName;
	}

	@Override
	public void setMailUserName(String mailUserName) {
		this.mailUserName = mailUserName;
	}

	@Override
	public void setMailPassWord(String mailPassWord) {
		this.mailPassWord = mailPassWord;
	}

	private boolean sendEmailSynch(String subject, String content, Collection<String> toList) {
		try {
			Properties props = new Properties();
			props.setProperty("mail.debug", "false");
			props.setProperty("mail.smtp.auth", "true");
			props.setProperty("mail.host", mailHostName);
			props.setProperty("mail.transport.protocol", "smtp");
			Transport transport;
			MailSSLSocketFactory sf = new MailSSLSocketFactory();
			sf.setTrustAllHosts(true);
			props.put("mail.smtp.ssl.enable", "true");
			props.put("mail.smtp.ssl.socketFactory", sf);
			Session session = Session.getInstance(props);
			Message msg = new MimeMessage(session);
			msg.setSubject(subject);
			StringBuilder builder = new StringBuilder();
			builder.append(content);
			msg.setContent(builder.toString(), "text/html; charset=utf-8");
			msg.setFrom(new InternetAddress(mailUserName));
			transport = session.getTransport();
			transport.connect(mailHostName, mailUserName, mailPassWord);
			InternetAddress[] address = new InternetAddress[toList.size()];
			int i = 0;
			for (String addres : toList) {
				address[i] = new InternetAddress(addres);
				i++;
			}
			transport.sendMessage(msg, address);
			transport.close();
			log.info("发送邮件到{}成功", toList);
			return true;
		} catch (Exception e) {
			log.error("发送邮件到{}失败:{}", toList, e.getMessage());
			return false;
		}

	}

	@Override
	public void sendEmail(String subject, String content, Collection<String> toList) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				sendEmailSynch(subject, content, toList);
			}
		}).start();
	}

	@Override
	public void sendEmail(String subject, String content, String... toList) {
		sendEmail(subject, content, Arrays.asList(toList));
	}

	@Override
	public void sendWeChat(String content, String... toList) {
		sendWeChat(content, Arrays.asList(toList));
	}

	private String getAccessToken() {
		HttpClient client = new HttpClient();
		GetMethod get = new GetMethod(weChatAccessTokenUrl);
		get.releaseConnection();
		get.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
		NameValuePair[] param = { new NameValuePair("corpid", weChatCorpid),
				new NameValuePair("corpsecret", weChatCorpsecret) };
		DefaultHttpParams.getDefaultParams().setParameter("http.protocol.cookie-policy",
				CookiePolicy.BROWSER_COMPATIBILITY);
		get.setQueryString(param);
		try {
			client.executeMethod(get);
			String result = new String(get.getResponseBodyAsString().getBytes("utf-8"));
			JSONObject parse = (JSONObject) JSON.parse(result);
			return parse.getString("access_token");
		} catch (IOException e) {
			log.error("WeChat get Token fail");
			return null;
		}
	}

	private String getTouser(Collection<String> toList) {
		StringBuffer sb = new StringBuffer();
		List<String> list = new ArrayList<>(toList);
		for (int i = 0, size = list.size(); i < size; i++) {
			if (i == 0 || i == (size - 1)) {
				sb.append(list.get(i));
			} else {
				sb.append(list.get(i)).append("|");
			}
		}
		return sb.toString();
	}

	private boolean sendWeChatSynch(String content, Collection<String> toList) {
		String accessToken = getAccessToken();
		if (accessToken == null) {
			return false;
		}
		PostMethod post = null;
		try {
			HttpClient client = new HttpClient();
			String url = weChatCreateSessionUrl + accessToken;
			post = new PostMethod(url);
			post.releaseConnection();
			post.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
			DefaultHttpParams.getDefaultParams().setParameter("http.protocol.cookie-policy",
					CookiePolicy.BROWSER_COMPATIBILITY);
			Part[] parts = { new StringPart("touser", getTouser(toList)), new StringPart("msgtype", "text"),
					new StringPart("agentid", weChatAgentid),
					new StringPart("text", "{\"content\":" + "\"" + content + "\"}") };
			post.setRequestEntity(new MultipartRequestEntity(parts, post.getParams()));
			client.executeMethod(post);
			log.info("微信发送成功{}", toList);
			return true;
		} catch (IOException e) {
			log.error("WeChat send fail:{}", e.getMessage());
			return false;
		} finally {
			post.releaseConnection();
		}
	}

	@Override
	public void sendWeChat(String content, Collection<String> toList) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				sendWeChatSynch(content, toList);
			}
		}).start();
	}

	@Override
	public boolean testEmail() {
		return sendEmailSynch(testSubject, testcontent, ListUtil.newArrayList(mailUserName));
	}

	@Override
	public boolean testWeChat() {
		return sendWeChatSynch(testcontent, ListUtil.newArrayList(mailUserName));
	}

}
