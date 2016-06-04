package com.ugiant.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Record;

public class CommonUtil {
	/**
	 * 获取UUID 去除"-"
	 * @author	wwd
	 * @date	2015-6-1
	 * @return
	 */
	public static String getUUID(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	/**
	 * 数据join方法
	 * @param arr
	 * @param split
	 * @return
	 */
	public static final <T> String ArrayJoin(T[] arr, String split){
		return ArrayJoin(Arrays.asList(arr), split);
	}
	
	/**
	 * 数据join方法
	 * @param list
	 * @param split
	 * @return
	 */
	public static final <T> String ArrayJoin(List<T> list, String split) {
		if(list == null){
			return null;
		}
		if(list.size() == 0){
			return "";
		}
		if(split == null){
			split = "";
		}
		StringBuilder sb = new StringBuilder();
		for(T t : list){
			sb.append(t.toString()).append(split);
		}
		
		return sb.substring(0,sb.length()-split.length()).toString();
	}

	/**
	 * integer 转 string
	 * @param num			数值
	 * @param digit			位数
	 * @return
	 */
	public static String integerToString(Integer num, Integer digit){
		String str = "";
		if(num != null){
			str = num.toString();
			if(num.toString().length() < digit){
				for(int i = digit; i > num.toString().length(); i--){
					str = "0" + str;
				}
			}
		}
		return str;
	}
	
	/**
	 * string转成integer数组
	 * @param ids			有规律的字符串
	 * @param split			分隔符
	 * @return
	 */
	public static Integer[] stringToIntegerArray(String ids, String split){
		if(StrKit.isBlank(ids)){
			return new Integer[0];
		}
		String[] idStrArr = ids.split(split);
		Integer[] idArr = new Integer[idStrArr.length];
		int i = 0;
		for(String id : idStrArr){
			idArr[i] = Integer.parseInt(id);
			i++;
		}
		
		return idArr;
	}
	
	/**
	 * 获取记录集的idValues created on 2016-03-16,updateb on xxxx-xx-xx xx:xx hwj
	 * @author hwj
	 * @param records
	 * @return
	 */
	public static Object[] getRecordSetIdValues(List<Record> records){
 		List<Integer> idValues = new ArrayList<Integer>();
		for(Record record : records){
			Integer temp = record.getInt("id");
			System.out.println("temp"+temp+"  /  "+record.getInt("camera_group_id"));
			idValues.add(temp);
		}
			
		Object[] ids = idValues.toArray(new Object[idValues.size()]);
		System.out.println("size:"+idValues.size());
	return  ids;
	}
	
	public static List<String> initTestOrg(){
		List<String> list = new ArrayList<String>();
		list.add("单位1");
		list.add("单位2");
		list.add("单位3");
		list.add("单位4");
		list.add("单位5");
		return list;
	}
	
	public static void main(String[] args) {
		Integer[] array = new Integer[]{1,2,3};
		System.out.println(ArrayJoin(array, ","));
	}
}
