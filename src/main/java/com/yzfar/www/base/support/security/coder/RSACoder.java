package com.yzfar.www.base.support.security.coder;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import com.yzfar.www.base.support.security.SecurityCoder;

/**
 * 
 * @author ShenHuaJie
 * @version 1.0
 * @since 1.0
 */
public abstract class RSACoder extends SecurityCoder {

	/**
	 * ����ǩ�� ��Կ�㷨
	 */
	public static final String KEY_ALGORITHM = "RSA";

	/**
	 * ����ǩ�� ǩ��/��֤�㷨
	 */
	public static final String SIGNATURE_ALGORITHM = "SHA1withRSA";

	/**
	 * ��Կ
	 */
	private static final String PUBLIC_KEY = "RSAPublicKey";

	/**
	 * ˽Կ
	 */
	private static final String PRIVATE_KEY = "RSAPrivateKey";

	/**
	 * RSA��Կ���� Ĭ��1024λ�� ��Կ���ȱ�����64�ı����� ��Χ��512��65536λ֮�䡣
	 */
	private static final int KEY_SIZE = 512;

	/**
	 * ǩ��
	 * 
	 * @param data
	 *            ��ǩ������
	 * @param privateKey
	 *            ˽Կ
	 * @return byte[] ����ǩ��
	 * @throws Exception
	 */
	public static byte[] sign(byte[] data, byte[] privateKey) throws Exception {
		// ת��˽Կ����
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKey);
		// ʵ������Կ����
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		// ȡ˽Կ�׶���
		PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
		// ʵ����Signature
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		// ��ʼ��Signature
		signature.initSign(priKey);
		// ����
		signature.update(data);
		// ǩ��
		return signature.sign();
	}

	/**
	 * У��
	 * 
	 * @param data
	 *            ��У������
	 * @param publicKey
	 *            ��Կ
	 * @param sign
	 *            ����ǩ��
	 * @return boolean У��ɹ�����true ʧ�ܷ���false
	 * @throws Exception
	 */
	public static boolean verify(byte[] data, byte[] publicKey, byte[] sign) throws Exception {
		// ת����Կ����
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);
		// ʵ������Կ����
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		// ���ɹ�Կ
		PublicKey pubKey = keyFactory.generatePublic(keySpec);
		// ʵ����Signature
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		// ��ʼ��Signature
		signature.initVerify(pubKey);
		// ����
		signature.update(data);
		// ��֤
		return signature.verify(sign);
	}

	/**
	 * ˽Կ����
	 * 
	 * @param data
	 *            ����������
	 * @param key
	 *            ˽Կ
	 * @return byte[] ��������
	 * @throws Exception
	 */
	public static byte[] decryptByPrivateKey(byte[] data, byte[] key) throws Exception {
		// ȡ��˽Կ
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(key);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		// ����˽Կ
		PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
		// �����ݽ���
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		return cipher.doFinal(data);
	}

	/**
	 * ��Կ����
	 * 
	 * @param data
	 *            ����������
	 * @param key
	 *            ��Կ
	 * @return byte[] ��������
	 * @throws Exception
	 */
	public static byte[] decryptByPublicKey(byte[] data, byte[] key) throws Exception {
		// ȡ�ù�Կ
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		// ���ɹ�Կ
		PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
		// �����ݽ���
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		return cipher.doFinal(data);
	}

	/**
	 * ��Կ����
	 * 
	 * @param data
	 *            ����������
	 * @param key
	 *            ��Կ
	 * @return byte[] ��������
	 * @throws Exception
	 */
	public static byte[] encryptByPublicKey(byte[] data, byte[] key) throws Exception {
		// ȡ�ù�Կ
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
		// �����ݼ���
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return cipher.doFinal(data);
	}

	/**
	 * ˽Կ����
	 * 
	 * @param data
	 *            ����������
	 * @param key
	 *            ˽Կ
	 * @return byte[] ��������
	 * @throws Exception
	 */
	public static byte[] encryptByPrivateKey(byte[] data, byte[] key) throws Exception {
		// ȡ��˽Կ
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(key);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		// ����˽Կ
		PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
		// �����ݼ���
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		return cipher.doFinal(data);
	}

	/**
	 * ȡ��˽Կ
	 * 
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static byte[] getPrivateKey(Map<String, Object> keyMap) throws Exception {
		Key key = (Key) keyMap.get(PRIVATE_KEY);
		return key.getEncoded();
	}

	/**
	 * ȡ�ù�Կ
	 * 
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static byte[] getPublicKey(Map<String, Object> keyMap) throws Exception {
		Key key = (Key) keyMap.get(PUBLIC_KEY);
		return key.getEncoded();
	}

	/**
	 * ��ʼ����Կ
	 * 
	 * @return Map ��Կ�Զ� Map
	 * @throws Exception
	 */
	public static Map<String, Object> initKey() throws Exception {
		// ʵ������Կ�Զ�������
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		// ��ʼ����Կ�Զ�������
		keyPairGen.initialize(KEY_SIZE);
		// ������Կ�Զ�
		KeyPair keyPair = keyPairGen.generateKeyPair();
		// ��Կ
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		// ˽Կ
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		// ��װ��Կ
		Map<String, Object> keyMap = new HashMap<String, Object>(2);
		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);
		return keyMap;
	}
}
