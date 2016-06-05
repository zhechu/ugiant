package com.ugiant.jfinalext.model.tpb;

import java.util.Date;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.ugiant.exception.MyException;
import com.ugiant.jfinalbase.BaseModel;

/**
 * 角色 model
 * @author lingyuwang
 *
 */
public class TpbRole extends BaseModel<TpbRole> {
	
	private static final long serialVersionUID = -2096624295353370241L;
	
	public static final TpbRole dao = new TpbRole();
	
	/**
	 * 获取角色列表
	 * @return
	 */
	public List<Record> find() {
		return Db.find("select a.* from tpb_role a order by a.created");
	}
	
	/**
	 * 根据参数查询
	 * @param status
	 * @return
	 */
	public List<Record> findByParams(Integer status) {
		StringBuilder sql = new StringBuilder();
		sql.append("select a.* from tpb_role a");
		sql.append(" where 1 = 1");
		if (status != null) {
			sql.append(" and a.status = ").append(status);
		}
		sql.append(" order by a.created");
		return Db.find(sql.toString());
	}
	
	/**
	 * 更新角色
	 * @param id 角色 id
	 * @param status 角色状态
	 * @param name 角色名称
	 * @param description 角色描述
	 * @param currentUserId 当前用户 id
	 */
	public void update(Integer id, Integer status, String name, String description, Integer currentUserId) {
		TpbRole role = findById(id);
		if(role == null){
			throw new MyException("参数错误");
		}
		if (status != null) {
			role.set("status", status);
		}
		if (name != null) {
			role.set("name", name);
		}
		if (description != null) {
			role.set("description", description);
		}
		role.set("updated", new Date())
			.set("last_update_user_id", currentUserId);
		if (!role.update()) {
			throw new MyException("更新失败");
		}
	}
	
	/**
	 * 更新状态
	 * @param id 角色 id
	 * @param status 角色状态
	 * @param currentUserId
	 */
	public void update(Integer id, Integer status, Integer currentUserId) {
		update(id, status, null, null, currentUserId);
	}
	
	/**
	 * 编辑
	 * @param id 角色 id
	 * @param name 角色名称
	 * @param description 角色描述
	 * @param currentUserId 当前用户 id
	 */
	public void update(Integer id, String name, String description, Integer currentUserId) {
		update(id, null, name, description, currentUserId);
	}
}
