package com.ugiant.jfinalext.interceptor;


import java.lang.reflect.Method;
import java.util.List;

import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.ugiant.constant.base.SessionAttriKey;
import com.ugiant.jfinalbase.BaseInterceptor;
import com.ugiant.jfinalbase.annotation.RequiresAuthentication;
import com.ugiant.jfinalbase.annotation.RequiresPermissions;
import com.ugiant.jfinalext.model.base.LoginUserInfo;
import com.ugiant.jfinalext.service.tpb.SystemService;

/**
 * 权限 拦截器
 * @author lingyuwang
 *
 */
public class AuthInterceptor extends BaseInterceptor {

	private SystemService systemService = SystemService.service; // 系统管理业务 service
	
	public void intercept(Invocation inv) {
		Controller controller = inv.getController();
		Class<?> clazz = controller.getClass();
		Method method = inv.getMethod();
		
		// 类级别登录权限
		RequiresAuthentication requiresAuthentication = clazz.getAnnotation(RequiresAuthentication.class);
		if (requiresAuthentication != null) { // 需拥有登录权限
			LoginUserInfo loginUserInfo = (LoginUserInfo) controller.getSession().getAttribute(SessionAttriKey.LOGIN_USER_INFO);
			if (loginUserInfo == null) { // 缺乏登录权限
				controller.redirect("/");
				return;
			}
		}
		
		// 类级别细粒度权限
		RequiresPermissions requiresPermissions = clazz.getAnnotation(RequiresPermissions.class);
		if (requiresPermissions != null) { // 有权限控制
			String[] permissions = requiresPermissions.value();
			LoginUserInfo loginUserInfo = (LoginUserInfo) controller.getSession().getAttribute(SessionAttriKey.LOGIN_USER_INFO);
			String roleIds = loginUserInfo.getRoleIds(); // 用户角色字符串
			List<String> permissionList = systemService.findPermissionByRoleIds(roleIds);
			int sureSize = 0; // 确定权限个数
			for (String requiresPermission : permissionList) {
				if (permissionList.contains(requiresPermission)) {
					sureSize ++;
				}
			}
			if (sureSize != permissions.length) { // 若缺乏所需权限，则访问不通过
				controller.redirect("/");
				return;
			}
		}
		
		// 方法级别登录权限
		requiresAuthentication = method.getAnnotation(RequiresAuthentication.class);
		if (requiresAuthentication != null) { // 需拥有登录权限
			LoginUserInfo loginUserInfo = (LoginUserInfo) controller.getSession().getAttribute(SessionAttriKey.LOGIN_USER_INFO);
			if (loginUserInfo == null) { // 缺乏登录权限
				controller.redirect("/");
				return;
			}
		}
		
		// 方法级别细粒度权限
		requiresPermissions = method.getAnnotation(RequiresPermissions.class);
		if (requiresPermissions != null) { // 有权限控制
			String[] permissions = requiresPermissions.value();
			LoginUserInfo loginUserInfo = (LoginUserInfo) controller.getSession().getAttribute(SessionAttriKey.LOGIN_USER_INFO);
			String roleIds = loginUserInfo.getRoleIds(); // 用户角色字符串
			List<String> permissionList = systemService.findPermissionByRoleIds(roleIds);
			int sureSize = 0; // 确定权限个数
			for (String requiresPermission : permissions) {
				if (permissionList.contains(requiresPermission)) {
					sureSize ++;
				}
			}
			if (sureSize != permissions.length) { // 若缺乏所需权限，则访问不通过
				controller.redirect("/");
				return;
			}
		}
		
		inv.invoke(); // 正常访问
	}

}
