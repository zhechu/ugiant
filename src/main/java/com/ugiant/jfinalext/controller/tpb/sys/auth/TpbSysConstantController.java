package com.ugiant.jfinalext.controller.tpb.sys.auth;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.ugiant.constant.base.SessionAttriKey;
import com.ugiant.jfinalbase.BaseController;
import com.ugiant.jfinalext.interceptor.UserMenuBtnAllInterceptor;
import com.ugiant.jfinalext.model.base.LoginUserInfo;
import com.ugiant.jfinalext.model.base.ResponseModel;
import com.ugiant.jfinalext.model.tpb.TpbSysConstant;
import com.ugiant.jfinalext.service.tpb.SystemService;
import com.ugiant.jfinalext.validator.admin.tpb.TpbSysConstantValidator;
import com.ugiant.jfinalext.validator.common.IdValidator;

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

	/**
	 * 获取常量列表json字符串,直接返回
	 */
	public void getTreeSysConstantJson(){
		String json = systemService.getSysConstantJson(0);
		this.renderJson(json);
	}

	/**
	 * 添加或更新
	 */
	@Before({TpbSysConstantValidator.class, Tx.class})
	public void save(){
		ResponseModel rm = new ResponseModel();
		LoginUserInfo loginUserInfo = (LoginUserInfo) getSession().getAttribute(SessionAttriKey.LOGIN_USER_INFO);
		Integer currentUserId = loginUserInfo.getUserId(); // 当前用户 id
		TpbSysConstant constant = this.getModel(TpbSysConstant.class);
		Integer id = constant.getInt("id");
		if (id != null) { // 更新
			systemService.updateSysConstant(id, constant.getStr("type"), constant.getStr("label"), 
					constant.getInt("value"), constant.getStr("description"), constant.getInt("sort"), currentUserId);
		} else { // 添加
			systemService.addSysConstant(constant, currentUserId);
		}
		rm.msgSuccess("操作字典成功");
		this.renderJson(rm);
	}

	/**
	 * 删除
	 */
	@Before({IdValidator.class, Tx.class})
	public void remove(){
		ResponseModel rm = new ResponseModel();
		Integer id = this.getParaToInt("id");
		systemService.deleteSysConstant(id);
		rm.msgSuccess("删除成功");
		this.renderJson(rm);
	}
	
}
