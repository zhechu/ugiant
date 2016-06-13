package com.ugiant.jfinalext.controller.tpb.sys.auth;

import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.ugiant.constant.base.SessionAttriKey;
import com.ugiant.constant.base.Status;
import com.ugiant.exception.MyException;
import com.ugiant.jfinalbase.BaseController;
import com.ugiant.jfinalbase.annotation.RequiresAuthentication;
import com.ugiant.jfinalbase.annotation.RequiresPermissions;
import com.ugiant.jfinalext.interceptor.UserMenuBtnAllInterceptor;
import com.ugiant.jfinalext.model.base.LoginUserInfo;
import com.ugiant.jfinalext.model.base.ResponseModel;
import com.ugiant.jfinalext.model.tpb.TpbSysUser;
import com.ugiant.jfinalext.service.tpb.SystemService;
import com.ugiant.jfinalext.validator.admin.tpb.ResetPwdValidator;
import com.ugiant.jfinalext.validator.admin.tpb.TpbSysUserValidator;
import com.ugiant.jfinalext.validator.common.IdValidator;

/**
 * 后台用户 控制器
 * @author lingyuwang
 *
 */
@RequiresAuthentication
public class TpbSysUserController extends BaseController {

	private SystemService systemService = SystemService.service; // 系统管理业务 service
	
	/**
	 * 进入后台用户管理页
	 */
	@RequiresPermissions({"sys:manage:sysuser:view"})
	@Before(UserMenuBtnAllInterceptor.class)
	public void index(){
		this.render("tpb_sys_user_manage.ftl");
	}
	
	/**
	 * 进入修改密码页
	 */
	public void to_reset_password(){
		this.render("to_reset_password.ftl");
	}

	/**
	 * 修改密码
	 */
	@Before({ResetPwdValidator.class, Tx.class})
	public void reset_password(){
		ResponseModel rm = new ResponseModel();
		String old_password = this.getPara("old_password");
		String new_password = this.getPara("new_password");
		LoginUserInfo loginUserInfo = (LoginUserInfo) this.getSession().getAttribute(SessionAttriKey.LOGIN_USER_INFO);
		if (loginUserInfo == null) {
			throw new MyException("用户未登陆");
		}
		boolean flag = systemService.resetPwd(loginUserInfo.getUsername(), old_password, new_password, loginUserInfo.getUserId());
		if (!flag) {
			throw new MyException("修改密码失败");
		}
		rm.msgSuccess("修改密码成功");
		this.renderJson(rm);
	}

	/**
	 * 获取后台用户数据
	 */
	@RequiresPermissions({"sys:manage:sysuser:view"})
	public void data(){
		List<Record> data = systemService.findTpbSysUser();
		this.setAttr("rows", data);
		this.renderJson();
	}

	/**
	 * 进入添加用户页面
	 */
	@RequiresPermissions({"sys:manage:sysuser:add"})
	public void toAdd(){
		Integer id = this.getParaToInt("id");
		Record sysUser = systemService.findSysUserDetailById(id);
		if (sysUser != null) {
			this.setAttr("tpbSysUser", sysUser);
		}
		this.render("tpb_sys_user_add.ftl");
	}

	/**
	 * 添加或更新
	 */
	@RequiresPermissions({"sys:manage:sysuser:add", "sys:manage:sysuser:edit"})
	@Before({TpbSysUserValidator.class, Tx.class})
	public void save(){
		ResponseModel rm = new ResponseModel();
		LoginUserInfo loginUserInfo = (LoginUserInfo) getSession().getAttribute(SessionAttriKey.LOGIN_USER_INFO);
		Integer currentUserId = loginUserInfo.getUserId(); // 当前用户 id
		TpbSysUser sysUser = this.getModel(TpbSysUser.class);
		Integer[] roleIds = this.getParaValuesToInt("roleIds");
		String password = this.getPara("password");
		Integer id = sysUser.getInt("id");
		if (id != null) { // 更新
			systemService.updateSysUser(id, sysUser.getStr("username"), sysUser.getStr("nickname"), password, roleIds, currentUserId);
		} else { // 添加
			systemService.addSysUser(sysUser, password, roleIds, currentUserId);
		}
		rm.msgSuccess("操作后台用户成功");
		this.renderJson(rm);
	}

	/**
	 * 禁用
	 */
	@RequiresPermissions({"sys:manage:sysuser:forbidden"})
	@Before({IdValidator.class, Tx.class})
	public void forbidden(){
		ResponseModel rm = new ResponseModel();
		Integer id = this.getParaToInt("id");
		LoginUserInfo loginUserInfo = (LoginUserInfo) getSession().getAttribute(SessionAttriKey.LOGIN_USER_INFO);
		Integer currentUserId = loginUserInfo.getUserId(); // 当前用户 id
		systemService.updateSysUserStatus(id, Status.FORBIDDEN, currentUserId);
		rm.msgSuccess("禁用成功");
		this.renderJson(rm);
	}
	
	/**
	 * 启用
	 */
	@RequiresPermissions({"sys:manage:sysuser:normal"})
	@Before({IdValidator.class, Tx.class})
	public void normal(){
		ResponseModel rm = new ResponseModel();
		Integer id = this.getParaToInt("id");
		LoginUserInfo loginUserInfo = (LoginUserInfo) getSession().getAttribute(SessionAttriKey.LOGIN_USER_INFO);
		Integer currentUserId = loginUserInfo.getUserId(); // 当前用户 id
		systemService.updateSysUserStatus(id, Status.NORMAL, currentUserId);
		rm.msgSuccess("启用成功");
		this.renderJson(rm);
	}

	/**
	 * 删除
	 */
	@RequiresPermissions({"sys:manage:sysuser:del"})
	@Before({IdValidator.class, Tx.class})
	public void remove(){
		ResponseModel rm = new ResponseModel();
		Integer id = this.getParaToInt("id");
		systemService.deleteSysUser(id);
		rm.msgSuccess("删除成功");
		this.renderJson(rm);
	}
	
}
