package com.ugiant.jfinalext.model.tpb;

import java.util.Date;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.ugiant.constant.base.Status;
import com.ugiant.exception.MyException;
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
	 * @param status 状态
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
	
	/**
	 * 根据用户名获取用户信息
	 * @param username 用户名
	 * @return
	 */
	public TpbSysUser findByUsername(String username) {
		return findByUsername(username, null);
	}

	/**
	 * 获取后台用户列表
	 * @return
	 */
	public List<Record> find() {
		StringBuilder sql = new StringBuilder();
		sql.append("select a.*");
		sql.append(", (select group_concat(r.name separator '，') from tpb_role_user ru left join tpb_role r on ru.role_id = r.id  where ru.sys_user_id = a.id group by ru.sys_user_id) role_names");
		sql.append(" from tpb_sys_user a");
		return Db.find(sql.toString());
	}
	
	/**
	 * 获取后台用户详细信息
	 * @param id
	 * @return
	 */
	public Record findSysUserById(Integer id) {
		StringBuilder sql = new StringBuilder();
		sql.append("select a.*");
		sql.append(", (select group_concat(r.id separator ',') from tpb_role_user ru left join tpb_role r on ru.role_id = r.id  where ru.sys_user_id = a.id) role_ids");
		sql.append(" from tpb_sys_user a");
		sql.append(" where a.id = ?");
		return Db.findFirst(sql.toString(), id);
	}
	
	/**
	 * 更新
	 * @param id 后台用户 id
	 * @param status 状态
	 * @param username 登陆用户名
	 * @param nickname 昵称
	 * @param password_salt 加密盐值
	 * @param password 加密后的密码
	 * @param currentUserId 当前用户 id
	 */
	public void update(Integer id, Integer status, String username, String nickname, String password, String password_salt, Integer currentUserId) {
		TpbSysUser sysUser = findById(id);
		if(sysUser == null){
			throw new MyException("参数错误");
		}
		if (status != null) {
			sysUser.set("status", status);
		}
		if (username != null) {
			sysUser.set("username", username);
		}
		if (nickname != null) {
			sysUser.set("nickname", nickname);
		}
		if (password != null) {
			sysUser.set("password", password);
		}
		if (password_salt != null) {
			sysUser.set("password_salt", password_salt);
		}
		sysUser.set("updated", new Date())
			   .set("last_update_user_id", currentUserId);
		if (!sysUser.update()) {
			throw new MyException("更新失败");
		}
	}
	
	/**
	 * 更新
	 * @param id 后台用户 id
	 * @param username 登陆用户名
	 * @param nickname 昵称
	 * @param password_salt 加密盐值
	 * @param password 加密后的密码
	 * @param currentUserId 当前用户 id
	 */
	public void update(Integer id, String username, String nickname, String password, String password_salt, Integer currentUserId) {
		update(id, null, username, nickname, password, password_salt, currentUserId);
	}
	
	/**
	 * 更新
	 * @param id 后台用户 id
	 * @param status 状态
	 * @param currentUserId 当前用户 id
	 */
	public void update(Integer id, Integer status, Integer currentUserId) {
		update(id, status, null, null, null, null, currentUserId);
	}
	
}
