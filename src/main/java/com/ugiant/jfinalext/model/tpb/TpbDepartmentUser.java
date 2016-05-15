package com.ugiant.jfinalext.model.tpb;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.ugiant.jfinalbase.BaseModel;

/**
 * 部门 用户 model
 * @author lingyuwang
 *
 */
public class TpbDepartmentUser extends BaseModel<TpbDepartmentUser> {

	private static final long serialVersionUID = 4481677639040947370L;
	
	public static final TpbDepartmentUser dao = new TpbDepartmentUser();
	
	/**
	 * 根据用户 id 获取部门信息
	 * @param userId 用户 id
	 * @return
	 */
	public Record findDepartmentByUserId(Integer userId){
		if(userId == null) {
			return null;
		}
		StringBuilder sql = new StringBuilder();
		sql.append("select a.* from tpb_department a left join tpb_department_user b on a.id = b.department_id ");
		sql.append(" where b.sys_user_id = ?");
		return Db.findFirst(sql.toString(), userId);
	}
	
}
