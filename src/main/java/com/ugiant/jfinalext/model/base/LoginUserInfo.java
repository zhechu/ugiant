package com.ugiant.jfinalext.model.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 后台用户登录信息
 * @author lingyuwang
 *
 */
public class LoginUserInfo implements Serializable {

	private static final long serialVersionUID = -6539978847412250415L;

	private Integer userId; // 用户 id
	
	private String roleIds; // 角色 ids
	
	private Integer deptId; // 部门 id
	
	private String username; // 用户名
	
	private List<String> permissionList = new ArrayList<String>(0);
	
	public LoginUserInfo() {}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getPermissionList() {
		return permissionList;
	}

	public void setPermissionList(List<String> permissionList) {
		this.permissionList = permissionList;
	}
	
}
