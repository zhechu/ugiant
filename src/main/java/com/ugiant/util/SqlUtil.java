package com.ugiant.util;

import com.jfinal.kit.StrKit;
import com.ugiant.exception.MyException;

/**
 * 逻辑删除 工具类
 * @author lingyuwang
 *
 */
public class SqlUtil {

	/**
	 * where sql 拼接
	 * @param tableAlias 表别名
	 * @param field 字段名
	 * @param value 值
	 * @return
	 */
	private static String where(String tableAlias, String field, Integer value) {
		StringBuilder sb = new StringBuilder();
		sb.append(" and ").append(tableAlias).append(".").append(field).append(" = ").append(value);
		return sb.toString();
	}
	
	/**
	 * 状态过滤 sql
	 * @param tableAlias 表别名
	 * @param status 状态
	 * @return
	 */
	public static String statusWhere(String tableAlias, Integer status) {
		return where(tableAlias, "status", status);
	}
	
	/**
	 * 换算常量 label
	 * @param type 类型
	 * @param value 数据值
	 * @param aliasFieldName
	 * @return
	 */
	public static String fieldLabel(String type, String value, String aliasFieldName){
		StringBuilder sql = new StringBuilder();
		if (StrKit.isBlank(type)) {
			throw new MyException("参数错误");
		}
		if (StrKit.isBlank(value)) {
			throw new MyException("参数错误");
		}
		if (StrKit.isBlank(aliasFieldName)) {
			throw new MyException("参数错误");
		}
		sql.append("(")
			.append(" select label from tpb_sys_constant tsc")
			.append(" where tsc.type = '").append(type).append("'")
			.append(" and tsc.value = ").append(value)
			.append(")").append(" ").append(aliasFieldName);
		return sql.toString();
	} 
	
}
