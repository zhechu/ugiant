package com.ugiant.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class SysCodeUtil {
	
//	public static String initPicName(){
//		return "PIC" + generateSysCode("yyyyMMddHHmmss") + initRandomCode(3);
//	}
	
	public static String initRealtyCode(){
		return generateSysCode("yyMMddHHmmss") + initRandomCode(4);
	}
	
	/**
	 * 生成审核码
	 * @author wwd
	 * @date   2016-4-18
	 * @return
	 */
	public static String initAuditCode(){
		return generateSysCode("yyMMddHHmmss") + initRandomCode(8);
	}
	
	/**
	 * 生成部门编码
	 * @return
	 */
	public static String deptCode(){
		return generateSysCode("yyMMddHHmmss") + initRandomCode(4);
	}
	
	/**
	 * 构建用户验证码
	 * @author	wwd
	 * @date	2015-6-2
	 * @return
	 */
	public static String initSmsCode(){
		return initRandomCode(6);
	}
	
	/**
	 * 生成订单code
	 * @author	wwd
	 * @date	2015-12-27
	 * @version 
	 * @return
	 */
	public static String initOrderCode(){
		return generateSysCode("yyMMddHHmmss") + initRandomCode(4);
	}
	
	
	public static String initRoleCode(){
		return "ROLE" + generateSysCode("yyyyMMddHHmmss") + initRandomCode(3);
	}
	
	
	public static String initPicName(){
		return "PIC" + generateSysCode("yyyyMMddHHmmss") + initRandomCode(3);
	}
	
	/**
	 * 生成系统编码
	 * @param format
	 * @return
	 */
	public static String generateSysCode(String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		return sdf.format(calendar.getTime());
	}
	
	/**
	 * 生成code
	 * @param val
	 * @param digit
	 * @return
	 */
	public static String initCode(Integer val, Integer digit){
		if(digit == null){
			return val == null ? null : val.toString();
		}
		if(val != null){
			String code = val.toString();
			if(digit < 9 && digit > 0){
				int lenght = val.toString().length();
				for(int i = digit; i > lenght; i--){
					code = "0" + code;
				}
			}
			
			return code;
		}else{
			return "";
		}
	
	}
	
	/**
	 * 生成随机数
	 * @param digit 位数(1-9)
	 * @return
	 */
	private static String initRandomCode(Integer digit){
		String code = "";
		if(digit < 9 && digit > 0){
			Integer base = Integer.MAX_VALUE;
			Long l = (long) (Math.pow(10, digit) - 1);
			if(l < Integer.MAX_VALUE){
				base = (int) Math.pow(10, digit) - 1;
			}
			
			Random rm = new Random();
			Integer num = rm.nextInt(base);
			code = num.toString();
			int lenght = num.toString().length();
			for(int i = digit; i > lenght; i--){
				code = "0" + code;
			}
		}
		
		return code;
	}
	
	public static void main(String[] args){
		//System.out.println(SysCodeUtil.initSmsCode());
		//System.out.println(generateSysCode("HHmmss"));
//		System.out.println(initCode(112,6));
		System.out.println(initOrderCode());
	}
}
