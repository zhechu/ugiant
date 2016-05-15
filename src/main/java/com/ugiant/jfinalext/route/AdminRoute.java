package com.ugiant.jfinalext.route;

import com.ugiant.jfinalbase.BaseRoute;
import com.ugiant.jfinalext.controller.tpb.AdminController;
import com.ugiant.jfinalext.controller.tpb.PublicController;
import com.ugiant.jfinalext.controller.tpb.sys.auth.TpbMenuController;
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
		
		this.add("/", PublicController.class, PUBLIC_PATH); // 公开路由
		
		this.add("/admin", AdminController.class, ADMIN_PATH); // 系统管理路由
		
		this.add("/admin/sys_auth_user", TpbSysUserController.class, ADMIN_SYS_AUTH); // 后台用户管理路由
		
		this.add("/admin/sys_auth_menu", TpbMenuController.class, ADMIN_SYS_AUTH); // 后台菜单管理路由
		
	}
}
