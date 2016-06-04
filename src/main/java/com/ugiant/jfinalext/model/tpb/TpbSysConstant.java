package com.ugiant.jfinalext.model.tpb;

import java.util.List;

import com.jfinal.kit.StrKit;
import com.ugiant.jfinalbase.BaseModel;

/**
 * 常量 model
 * @author lingyuwang
 *
 */
public class TpbSysConstant extends BaseModel<TpbSysConstant> {
	
	private static final long serialVersionUID = -2096624295353370241L;
	
	public static final TpbSysConstant dao = new TpbSysConstant();
	
	/**
	 * 根据类型获取常量列表
	 * @param type 常量类型
	 * @return
	 */
	public List<TpbSysConstant> findByType(String type) {
		if (StrKit.isBlank(type)) {
			return null;
		}
		StringBuilder sql = new StringBuilder();
		sql.append("select a.* from tpb_sys_constant a where a.type = ? ");
		return dao.find(sql.toString(), type);
	}
	
	/**
	 * 根据类型和值获取常量
	 * @param type 常量类型
	 * @param value 值
	 * @return
	 */
	public TpbSysConstant findByTypeAndValue(String type, Integer value) {
		if (StrKit.isBlank(type) || value==null) {
			return null;
		}
		StringBuilder sql = new StringBuilder();
		sql.append("select a.* from tpb_sys_constant a where a.type = ? and a.value = ?");
		return dao.findFirst(sql.toString(), type, value);
	}
	
}
