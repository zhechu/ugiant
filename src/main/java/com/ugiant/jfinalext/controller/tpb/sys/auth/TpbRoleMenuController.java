package com.ugiant.jfinalext.controller.tpb.sys.auth;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.ugiant.jfinalbase.BaseController;
import com.ugiant.jfinalbase.annotation.RequiresAuthentication;
import com.ugiant.jfinalbase.annotation.RequiresPermissions;
import com.ugiant.jfinalext.interceptor.UserMenuBtnAllInterceptor;
import com.ugiant.jfinalext.model.base.ResponseModel;
import com.ugiant.jfinalext.service.tpb.SystemService;
import com.ugiant.jfinalext.validator.admin.tpb.TpbRoleMenuValidator;
import com.ugiant.util.CommonUtil;

/**
 * 角色菜单 控制器
 * @author lingyuwang
 *
 */
@RequiresAuthentication
public class TpbRoleMenuController extends BaseController {

	private SystemService systemService = SystemService.service; // 系统管理业务 service
	
	/**
	 * 进入角色权限管理页
	 */
	@RequiresPermissions({"sys:manage:roleauth:view"})
	@Before(UserMenuBtnAllInterceptor.class)
	public void index() {
		this.render("tpb_role_menu_manage.ftl");
	}
	
	/**
	 * 菜单和菜单按钮树
	 */
	@RequiresPermissions({"sys:manage:roleauth:view"})
	public void treegrid_data(){
		Integer roleId = this.getParaToInt("role_id");
		this.renderJson(systemService.getTreeAuthJson(roleId, 0));
	}
	
	/**
	 * 保存权限
	 */
	@RequiresPermissions({"sys:manage:roleauth:add"})
	@Before({TpbRoleMenuValidator.class, Tx.class})
	public void save(){
		ResponseModel rm = new ResponseModel();
		Integer roleId = this.getParaToInt("role_id");
		String menuIds = this.getPara("menuIds");
		String btnIds = this.getPara("btnIds");
		Integer[] menuIdArr = CommonUtil.stringToIntegerArray(menuIds, ",");
		Integer[] btnIdArr = CommonUtil.stringToIntegerArray(btnIds, ",");
		
		// 更新角色菜单关系
		systemService.updateRoleMenu(roleId, menuIdArr);
		
		// 更新角色菜单按钮关系
		systemService.updateRoleMenuBtn(roleId, btnIdArr);
		
		rm.msgSuccess("操作成功");
		this.renderJson(rm);
	}
}
