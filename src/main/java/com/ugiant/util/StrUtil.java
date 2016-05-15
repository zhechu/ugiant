package com.ugiant.util;

/**
 * 字符串工具类
 * @author wwd
 * @date  2015-7-6
 */
public final class StrUtil {
	/**
	 * 判断字符串为空
	 * @author wwd
	 * @date  2015-7-6
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		if(str != null && str.trim() != ""){
			return false;
		}
		return true;
	}
	
	/**
	 * 判断字符串不为空
	 * @author wwd
	 * @date  2015-7-6
	 * @param str
	 * @return
	 */
	public static boolean notEmpty(String str){
		return !isEmpty(str);
	}
	
	/**
	 * 判断字符串为空（多个）
	 * @author wwd
	 * @date  2015-7-6
	 * @param strings
	 * @return
	 */
	public static boolean isEmpty(String ...strings){
		if(strings == null){
			return true;
		}else{
			for(String str : strings){
				if(notEmpty(str)){
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * 判断字符串不为空（多个）
	 * @author wwd
	 * @date  2015-7-6
	 * @param strings
	 * @return
	 */
	public static boolean notEmpty(String ...strings){
		if(strings == null){
			return false;
		}else{
			for(String str : strings){
				if(isEmpty(str)){
					return false;
				}
			}
		}
		return true;
	}
	
	
	public static String trim(String str){
		if(isEmpty(str)){
			return "";
		}
		return str;
	}
	
	public static void main(String[] args){
		boolean flag = notEmpty("s","s");
		System.out.println(flag);
	}
}
