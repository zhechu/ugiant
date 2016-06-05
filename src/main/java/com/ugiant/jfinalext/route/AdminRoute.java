package com.ugiant.jfinalext.route;

import com.ugiant.jfinalbase.BaseRoute;
import com.ugiant.jfinalext.controller.tpb.AdminController;
import com.ugiant.jfinalext.controller.tpb.PublicController;
import com.ugiant.jfinalext.controller.tpb.sys.auth.TpbDepartmentController;
import com.ugiant.jfinalext.controller.tpb.sys.auth.TpbMenuBtnController;
import com.ugiant.jfinalext.controller.tpb.sys.auth.TpbMenuController;
import com.ugiant.jfinalext.controller.tpb.sys.auth.TpbRoleController;
import com.ugiant.jfinalext.controller.tpb.sys.auth.TpbRoleMenuController;
import com.ugiant.jfinalext.controller.tpb.sys.auth.TpbSysConstantController;
import com.ugiant.jfinalext.controller.tpb.sys.auth.TpbSysUserController;

/**
 * 系统后台路由管理
 * @author lingyuwang
 *
 */
public class AdminRoute extends BaseRoute {
	
	private static final String PUBLIC_PATH = "/pages/admin/public";
	
	private static final String ADMIN_PATH = "/pages/admin";
	
	private static final String ADMIN_SYS_AUTH = "/pages/admin/sys/auth";
	
	@Override
	public void config() {
		
		this.add("/", PublicController.class, PUBLIC_PATH); // 公开
		
		this.add("/admin", AdminController.class, ADMIN_PATH); // 系统管理
		
		this.add("/admin/sys_auth_user", TpbSysUserController.class, ADMIN_SYS_AUTH); // 后台用户管理
		
		this.add("/admin/sys_auth_menu", TpbMenuController.class, ADMIN_SYS_AUTH); // 后台菜单管理
		
		this.add("/admin/sys_auth_menu_btn", TpbMenuBtnController.class, ADMIN_SYS_AUTH); // 后台菜单按钮管理
		
		this.add("/admin/sys_auth_role_menu", TpbRoleMenuController.class,ADMIN_SYS_AUTH); // 角色权限管理
		
		this.add("/admin/sys_auth_role", TpbRoleController.class, ADMIN_SYS_AUTH); // 角色管理
		
		this.add("/admin/sys_auth_dept", TpbDepartmentController.class, ADMIN_SYS_AUTH); // 部门管理
		
		this.add("/admin/sys_constant", TpbSysConstantController.class, ADMIN_SYS_AUTH); // 字典管理
		
	}
}
