package com.ugiant.jfinalext.model.tpb;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.ugiant.constant.tpb.Status;
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
	
}
