package com.ugiant.jfinalext.controller.tpb.sys.auth;

import com.jfinal.aop.Before;
import com.ugiant.jfinalbase.BaseController;
import com.ugiant.jfinalext.interceptor.UserMenuBtnAllInterceptor;
import com.ugiant.jfinalext.model.tpb.TpbSysConstant;
import com.ugiant.jfinalext.service.tpb.SystemService;

/**
 * 字典 控制器
 * @author lingyuwang
 *
 */
public class TpbSysConstantController extends BaseController {

	private SystemService systemService = SystemService.service; // 系统管理业务 service
	
	/**
	 * 进入字典管理页
	 */
	@Before(UserMenuBtnAllInterceptor.class)
	public void index(){
		this.render("tpb_sys_constant_manage.ftl");
	}

	/**
	 * 获取树常量
	 */
	public void treegrid_data(){
		this.renderJson(systemService.getRootSysConstantTreeJson());
	}

	/**
	 * 进入添加常量页
	 */
	public void toAdd(){
		Integer id = this.getParaToInt("id");
		TpbSysConstant constant = systemService.findSysConstantById(id);
		if (constant != null) {
			this.setAttr("tpbSysConstant", constant);
		}
		this.render("tpb_sys_constant_add.ftl");
	}

}
