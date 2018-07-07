package com.yzfar.www.base.support.security.coder;

import java.security.MessageDigest;

import com.yzfar.www.base.support.security.SecurityCoder;

/**
 * SHA�������
 * 
 * @author ShenHuaJie
 * @version 1.0
 * @since 1.0
 */
public abstract class SHACoder extends SecurityCoder {

	/**
	 * SHA-1����
	 * 
	 * @param data
	 *            ����������
	 * @return byte[] ��ϢժҪ
	 * @throws Exception
	 */
	public static byte[] encodeSHA(byte[] data) throws Exception {
		// ��ʼ��MessageDigest
		MessageDigest md = MessageDigest.getInstance("SHA");
		// ִ����ϢժҪ
		return md.digest(data);
	}

	/**
	 * SHA-1����
	 * 
	 * @param data
	 *            ����������
	 * @return byte[] ��ϢժҪ
	 * @throws Exception
	 */
	public static byte[] encodeSHA1(byte[] data) throws Exception {
		// ��ʼ��MessageDigest
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		// ִ����ϢժҪ
		return md.digest(data);
	}

	/**
	 * SHA-256����
	 * 
	 * @param data
	 *            ����������
	 * @return byte[] ��ϢժҪ
	 * @throws Exception
	 */
	public static byte[] encodeSHA256(byte[] data) throws Exception {
		// ��ʼ��MessageDigest
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		// ִ����ϢժҪ
		return md.digest(data);
	}

	/**
	 * SHA-384����
	 * 
	 * @param data
	 *            ����������
	 * @return byte[] ��ϢժҪ
	 * @throws Exception
	 */
	public static byte[] encodeSHA384(byte[] data) throws Exception {
		// ��ʼ��MessageDigest
		MessageDigest md = MessageDigest.getInstance("SHA-384");
		// ִ����ϢժҪ
		return md.digest(data);
	}

	/**
	 * SHA-512����
	 * 
	 * @param data
	 *            ����������
	 * @return byte[] ��ϢժҪ
	 * @throws Exception
	 */
	public static byte[] encodeSHA512(byte[] data) throws Exception {
		// ��ʼ��MessageDigest
		MessageDigest md = MessageDigest.getInstance("SHA-512");
		// ִ����ϢժҪ
		return md.digest(data);
	}

	/**
	 * SHA-224����
	 * 
	 * @param data
	 *            ����������
	 * @return byte[] ��ϢժҪ
	 * @throws Exception
	 */
	public static byte[] encodeSHA224(byte[] data) throws Exception {
		// ��ʼ��MessageDigest
		MessageDigest md = MessageDigest.getInstance("SHA-224");
		// ִ����ϢժҪ
		return md.digest(data);
	}
}
