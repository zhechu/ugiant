package com.ugiant.jfinalext.model.tpb;

import com.ugiant.constant.tpb.Status;
import com.ugiant.jfinalbase.BaseModel;
import com.ugiant.util.SqlUtil;

/**
 * 后台用户 model
 * @author lingyuwang
 *
 */
public class TpbSysUser extends BaseModel<TpbSysUser> {
	
	private static final long serialVersionUID = 7295166537716627407L;
	
	public static final TpbSysUser dao = new TpbSysUser();
	
	/**
	 * 根据用户名获取用户信息
	 * @param username 用户名
	 * @return
	 */
	public TpbSysUser findByUsername(String username, Integer status) {
		if (username == null) {
			return null;
		}
		StringBuilder sql = new StringBuilder();
		sql.append("select a.* from tpb_sys_user a where a.username = ? ");
		if (status != null) {
			sql.append(SqlUtil.statusWhere("a", Status.NORMAL)); // 状态过滤
		}
		return dao.findFirst(sql.toString(), username);
	}
	
}
