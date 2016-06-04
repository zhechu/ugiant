package com.ugiant.jfinalext.model.tpb;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.ugiant.constant.base.Status;
import com.ugiant.exception.MyException;
import com.ugiant.jfinalbase.BaseModel;
import com.ugiant.util.SqlUtil;

/**
 * 角色 用户 model
 * @author lingyuwang
 *
 */
public class TpbRoleUser extends BaseModel<TpbRoleUser> {
	
	private static final long serialVersionUID = -7178272279889579566L;
	
	public static final TpbRoleUser dao = new TpbRoleUser();
	
	/**
	 * 根据用户 id 获取角色列表
	 * @param userId 用户 id
	 * @return
	 */
	public List<Record> findRoleByUserId(Integer userId) {
		if (userId == null) {
			return new ArrayList<Record>();
		}
		StringBuilder sql = new StringBuilder();
		sql.append("select a.* from tpb_role a left join tpb_role_user b on a.id = b.role_id ");
		sql.append(" where b.sys_user_id = ?");
		sql.append(SqlUtil.statusWhere("a", Status.NORMAL)); // 状态过滤
		return Db.find(sql.toString(), userId);
	}
	
	/**
	 * 根据角色 id 删除角色而用户关系
	 * @param roleId 角色 id
	 */
	public void deleteByRoleId(Integer roleId) {
		Db.update("delete from tpb_role_user where role_id = ?", roleId);
	}
	
	/**
	 * 根据用户 id 删除角色用户关系
	 * @param userId 后台用户 id
	 */
	public void deleteByUserId(Integer userId) {
		Db.update("delete from tpb_role_user where sys_user_id = ?", userId);
	}
	
	/**
	 * 绑定角色用户关系
	 * @param roleIds 角色 ids
	 * @param userId 用户 id
	 */
	public void add(Integer[] roleIds, Integer userId) {
		TpbRoleUser roleUser = null;
		for (Integer roleId : roleIds) {
			roleUser = new TpbRoleUser();
			roleUser.set("sys_user_id", userId)
					.set("role_id", roleId);
			if (!roleUser.save()) {
				throw new MyException("绑定用户角色关系失败");
			}
		}
	}
	
}
