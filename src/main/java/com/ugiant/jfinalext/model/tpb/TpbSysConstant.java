package com.ugiant.jfinalext.model.tpb;

import java.util.Date;
import java.util.List;

import com.jfinal.kit.StrKit;
import com.ugiant.exception.MyException;
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
	
	/**
	 * 根据常量父 id 获取常量
	 * @param parentId 常量父 id
	 * @return
	 */
	public List<TpbSysConstant> findByParentId(Integer parentId) {
		return find("select a.* from tpb_sys_constant a where a.parent_id = ? order by a.type, a.sort", parentId);
	}
	
	/**
	 * 更新常量
	 * @param id 常量 id
	 * @param type 类型
	 * @param label 标签名 
	 * @param value 数据值
	 * @param description 描述
	 * @param sort 排序
	 * @param currentUserId 当前用户 id
	 */
	public void update(Integer id, String type, String label, Integer value, String description,
			Integer sort, Integer currentUserId) {
		TpbSysConstant constant = findById(id);
		if(constant == null){
			throw new MyException("参数错误");
		}
		if (type != null) {
			constant.set("type", type);
		}
		if (label != null) {
			constant.set("label", label);
		}
		if (value != null) {
			constant.set("value", value);
		}
		if (description != null) {
			constant.set("description", description);
		}
		if (sort != null) {
			constant.set("sort", sort);
		}
		constant.set("updated", new Date())
				.set("last_update_user_id", currentUserId);
		if (!constant.update()) {
			throw new MyException("更新失败");
		}
	}
}
