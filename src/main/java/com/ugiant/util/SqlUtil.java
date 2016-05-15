package com.ugiant.util;

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
	
}
