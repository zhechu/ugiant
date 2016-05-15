package com.ugiant.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jfinal.kit.StrKit;

/**
 * 校验工具类
 * @author wwd
 * @date 2014-7-17
 *
 */
public final class ValidatorUtil {
	private static final String EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";		//邮箱
	private static final String ID_CARD_NO = "^\\d{15}|\\d{18}$";										//身份证
	private static final String TELEPHONE = "^((\\d{3,4}-)|\\d{3,4})?\\d{7,8}$";						//座机
	private static final String MOBILEPHONE = "^(13[0-9]|15[0-9]|18[0-9])\\d{8}$";					//手机号
	
	private static final String INTEGER = "^-?0*[1-9][0-9]{0,9}$";										//验证是否是Integer
	private static final String LONG = "^-?0*[1-9][0-9]{0,17}$";
	
	private static final String MONEY = "^[1-9][0-9]{0,9}(.[0-9]{1,2})?$";
	private static final String AREA = "^[1-9][0-9]{0,9}(.[0-9]{1,2})?$";
	
	private static final String PERCENT = "^[0]{1}[.]{1}([0-9]{1,3})$";
	
	/**
	 * 判断字符串是否为空
	 * @param str
	 * @return
	 */
	public static Boolean stringIsEmpty(String str){
		if(str != null && str.trim() != ""){
			return false;
		}
		
		return true;
	}
	
	public static Boolean stringIsEmpty(String... str){
		if(str == null){
			return true;
		}else{
			for(String s : str){
				if(s != null && !s.trim().equals("")){
					continue;
				}else{
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * 判断字符串是否不为空
	 * @param str
	 * @return
	 */
	public static Boolean stringIsNotEmpty(String str){
		if(str != null && str.trim() != ""){
			return true;
		}
		
		return false;
	}
	
	public static Boolean stringIsNotEmpty(String... str){
		if(str == null){
			return false;
		}else{
			for(String s : str){
				if(s != null && !s.trim().equals("")){
					continue;
				}else{
					return false;
				}
			}
		}
		
		return true;
	}
	
	
	/**
	 * 校验数组有效
	 * 
	 * @param arr
	 * @return
	 */
	public static boolean arrayIsValid(Object[] arr) {
		return null != arr && arr.length > 0;
	}
	
	/**
	 * 校验数组无效
	 * 
	 * @param arr
	 * @return
	 */
	public static boolean arrayIsNotValid(Object[] arr) {
		return !arrayIsValid(arr);
	}	
	
	

	/**
	 * 验证是否整数
	 * @param input
	 * @return
	 */
	public static boolean validateInteger(String input){
		if(match(input, INTEGER)){
			Integer base = Integer.MAX_VALUE;
			if(Long.parseLong(input) >base){
				return false;
			}else{
				base = Integer.MIN_VALUE;
				if(base > Long.parseLong(input)){
					return false;
				}else{
					return true;
				}
			}
		}
			
		return false;
	}
	
	public static boolean validateLong(String input){
		if(match(input, LONG)){
			BigDecimal base = new BigDecimal(Long.MAX_VALUE);
			BigDecimal bd = new BigDecimal(input);
			if(bd.compareTo(base) > 0){
				return false;
			}else{
				base = new BigDecimal(Long.MIN_VALUE);
				if(base.compareTo(bd) > 0){
					return false;
				}else{
					return true;
				}
			}
		}
			
		return false;
	}
	
	/**
	 * 验证邮箱
	 * @param input
	 * @return
	 */
	public static boolean validateEmail(String input){
		return match(input, EMAIL);
	}
	
	/**
	 * 验证座机
	 * @param input
	 * @return
	 */
	public static boolean validateTelephone(String input){
		return match(input, TELEPHONE);
	}
	
	/**
	 * 验证手机
	 * @param input
	 * @return
	 */
	public static boolean validateMobilephone(String input){
		return match(input, MOBILEPHONE);
	}
	
	/**
	 * 验证身份证
	 * @param input
	 * @return
	 */
	public static boolean validateIDcardno(String input){
		return match(input, ID_CARD_NO);
	}
	
	public static boolean validateStr(String str, String regex){
		return match(str, regex);
	}
	
	/**
	 *  检验金额
	 * @author wwd
	 * @date   2016-4-18
	 * @param input
	 * @return
	 */
	public static boolean validateMoney(String input){
		return match(input, MONEY);
	}
	
	public static boolean validateStr(String str, int maxLength){
		if(stringIsEmpty(str)){
			return false;
		}else{
			if(str.length() > maxLength){
				return false;
			}else{
				return true;
			}
		}
	}
	
	public static Integer validateBoolStr(String str,String compareStr){
		if(StrKit.notBlank(str, compareStr)){
			if(str.equals(compareStr)){
				return 1;
			}else{
				return 0;
			}
		}else{
			return null;
		}
	}
	
	/**
	 * 判断面积
	 * @author wwd
	 * @date   2016-4-18
	 * @param input
	 * @return
	 */
	public static boolean validateArea(String input){
		return match(input, AREA);
	}
	
	public static boolean validatePercent(String input){
		return match(input,PERCENT);
	}
	
	/**
	 * 正则表达式验证
	 * @param str
	 * @param regex
	 * @return
	 */
	public static boolean match(String str, String regex){
		if(str != null){
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(str);
			return m.matches();
		}else{
			return false;
		}
		
	}
	
	public static boolean validateDate(String time,String format){
		boolean flag = false;
		
		if(StrKit.notBlank(time,format)){
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				sdf.parse(time);
				flag = true;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		return flag;
	}
	
	/**
	 * 单个为空
	 * @param obj
	 * @return
	 */
	public static boolean isNull(Object obj){
		return obj == null ? true : false;
	}
	
	/**
	 * 全为null
	 * @param objs
	 * @return
	 */
	public static boolean isAllNull(Object... objs){
		if(objs != null && objs.length > 0){
			for(Object obj : objs){
				if(obj != null){
					return false;
				}
			}
		}
		
		return true;
	}
	
	/**
	 * 全都不为null
	 * @param objs
	 * @return
	 */
	public static boolean isAllNotNull(Object... objs){
		if(objs != null && objs.length > 0){
			for(Object obj : objs){
				if(obj == null){
					return false;
				}
			}
			return true;
		}
		
		return false;
	}
	
	/**
	 * 不是全为空
	 * @param objs
	 * @return
	 */
	public boolean isNotAllNull(Object... objs){
		return !isAllNull(objs);
	}
	
	public static void main(String[] args){
//		System.out.println(isAllNotNull(1,"111","sdf"));
		System.out.println(validatePercent("0.2212"));
	}
}
