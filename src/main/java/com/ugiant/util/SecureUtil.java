package com.ugiant.util;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import com.jfinal.kit.StrKit;


public final class SecureUtil {

	public static void main(String[] args) {
		String pdSalt = SecureUtil.passwdSalt();
		String passwd = SecureUtil.passwd("123456", pdSalt);
		System.out.println(pdSalt);
		System.out.println("--------------------------------------");
		System.out.println(passwd);
	}
	
	/**
	 * 生成密码
	 * 
	 * @param pd
	 * @param pdSalt
	 * @return
	 */
	public static String passwd(String pd, String pdSalt){
		StringBuilder ret = new StringBuilder();
		if(StrKit.notBlank(pd) && StrKit.notBlank(pdSalt)){
			MessageDigest md5 = null;
			try {
			    md5 = MessageDigest.getInstance("MD5");
			} catch (Exception e) {
			    
			}
			char[] charArray = new StringBuffer().append(pd).append(pdSalt).toString().trim().toCharArray();
			byte[] byteArray = new byte[charArray.length];
			for (int i = 0; i < charArray.length; i++){
				byteArray[i] = (byte) charArray[i];
			}
			byte[] md5Bytes = md5.digest(byteArray);

			for (int i = 0; i < md5Bytes.length; i++) {
			    int val = ((int) md5Bytes[i]) & 0xff;
			    if (val < 16){
			    	ret.append("0");
			    }
			    ret.append(Integer.toHexString(val));
			}
		}
        return ret.toString();
	}
	
	/**
	 * 生成密码校验位
	 * 
	 * @return
	 */
	public static String passwdSalt(){
		return passwdSalt(Calendar.getInstance().getTime());
	}
	
	/**
	 * 生成密码校验位
	 * 
	 * @param date
	 * @return
	 */
	public static String passwdSalt(Date date){
		return new StringBuffer()
			.append(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(null != date ? date : Calendar.getInstance().getTime()))
			.append(",").append(new Random().nextInt(Integer.MAX_VALUE)).toString();
	}
	
	
}
