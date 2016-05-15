package com.ugiant.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class CryptUtil {
	/**
	 * (32位)加密
	 * 
	 * @param inStr
	 *            原始字符串
	 * @param method
	 *            加密算法(MD5/SHA1)
	 * @return 加密后的字符串
	 * @throws UnsupportedEncodingException
	 */
	public static String crypt(String inStr, String method) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance(method);
		} catch (Exception e) {
			return "";
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];

		StringBuffer hexValue = new StringBuffer();
		byte[] mdBytes;
		try {
			mdBytes = md.digest(inStr.getBytes("UTF-8"));
			for (int i = 0; i < mdBytes.length; i++) {
				int val = ((int) mdBytes[i]) & 0xff;
				if (val < 16)
					hexValue.append("0");
				hexValue.append(Integer.toHexString(val));
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return hexValue.toString();
	}
	
	/**
	 * AES加密
	 * 
	 * @author wwd
	 * @date 2015-6-1
	 * @param content
	 * @param password
	 * @return
	 */
	public static byte[] AesEncrypt(String content, String password) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128, new SecureRandom(password.getBytes()));
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			byte[] byteContent = content.getBytes("utf-8");
			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(byteContent);
			return result; // 加密
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * AES解密
	 * 
	 * @author wwd
	 * @date 2015-6-1
	 * @param content
	 * @param password
	 * @return
	 */
	public static byte[] AesDecrypt(byte[] content, String password) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128, new SecureRandom(password.getBytes()));
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(content);
			return result; // 加密
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将二进制转换成16进制
	 * 
	 * @author wwd
	 * @date 2015-6-1
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 将16进制转换为二进制
	 * 
	 * @author wwd
	 * @date 2015-6-1
	 * @param hexStr
	 * @return
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
					16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}
	
	/**
	 * base64加密
	 * @author	wwd
	 * @date	2015-6-1
	 * @param str
	 * @return
	 */
	public static String getBase64(String str) {
		byte[] b = null;
		String s = null;
		try {
			b = str.getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (b != null) {
			s = new BASE64Encoder().encode(b);
		}
		return s;
	}

	/**
	 * base64解密
	 * @author	wwd
	 * @date	2015-6-1
	 * @param s
	 * @return
	 */
	public static String getFromBase64(String s) {
		byte[] b = null;
		String result = null;
		if (s != null) {
			BASE64Decoder decoder = new BASE64Decoder();
			try {
				b = decoder.decodeBuffer(s);
				result = new String(b, "utf-8");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static void main(String[] args) throws NoSuchAlgorithmException {
		/*
		String content = CommonUtil.getUUID().substring(0);
		String password = CommonUtil.getUUID();
		// 加密
		System.out.println("加密前：" + content);
		byte[] encryptResult = AesEncrypt(content, password);
//		String encryptResultStr = new String(encryptResult);
		String encryptResultStr = parseByte2HexStr(encryptResult);
		System.out.println(encryptResultStr.length());
		System.out.println("加密后：" + encryptResultStr);
		// 解密
//		byte[] decryptFrom = encryptResultStr.getBytes();
		byte[] decryptFrom = parseHexStr2Byte(encryptResultStr);
		byte[] decryptResult = AesDecrypt(decryptFrom, password);
		System.out.println("解密后：" + new String(decryptResult));
		*/
		System.out.println(getBase64("123456"));
	}

}
