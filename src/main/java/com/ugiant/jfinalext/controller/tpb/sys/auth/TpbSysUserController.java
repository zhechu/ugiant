package com.ugiant.jfinalext.controller.tpb.sys.auth;

import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Record;
import com.ugiant.constant.base.SessionAttriKey;
import com.ugiant.constant.base.Status;
import com.ugiant.exception.MyException;
import com.ugiant.exception.MyMessage;
import com.ugiant.jfinalbase.BaseController;
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
public class TpbSysUserController extends BaseController {

	private SystemService systemService = SystemService.service; // 系统管理业务 service
	
	/**
	 * 进入后台用户管理页
	 */
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
	@Before(ResetPwdValidator.class)
	public void reset_password(){
		ResponseModel rm = new ResponseModel();
		try {
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
		} catch (MyException me) {
			rm.msgFailed(me.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			rm.msgFailed(MyMessage.SYS_EXCEPTION_MSG);
		}
		this.renderJson(rm);
	}

	/**
	 * 获取后台用户数据
	 */
	public void data(){
		List<Record> data = systemService.findTpbSysUser();
		this.setAttr("rows", data);
		this.renderJson();
	}

	/**
	 * 进入添加用户页面
	 */
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
	@Before(TpbSysUserValidator.class)
	public void save(){
		ResponseModel rm = new ResponseModel();
		try {
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
		} catch (MyException me) {
			rm.msgFailed(me.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			rm.msgFailed(MyMessage.SYS_EXCEPTION_MSG);
		}
		this.renderJson(rm);
	}

	/**
	 * 禁用
	 */
	@Before(IdValidator.class)
	public void forbidden(){
		ResponseModel rm = new ResponseModel();
		try {
			Integer id = this.getParaToInt("id");
			LoginUserInfo loginUserInfo = (LoginUserInfo) getSession().getAttribute(SessionAttriKey.LOGIN_USER_INFO);
			Integer currentUserId = loginUserInfo.getUserId(); // 当前用户 id
			systemService.updateSysUserStatus(id, Status.FORBIDDEN, currentUserId);
			rm.msgSuccess("禁用成功");
		} catch (MyException me) {
			rm.msgFailed(me.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			rm.msgFailed(MyMessage.SYS_EXCEPTION_MSG);
		}
		this.renderJson(rm);
	}
	
	/**
	 * 启用
	 */
	@Before(IdValidator.class)
	public void normal(){
		ResponseModel rm = new ResponseModel();
		try {
			Integer id = this.getParaToInt("id");
			LoginUserInfo loginUserInfo = (LoginUserInfo) getSession().getAttribute(SessionAttriKey.LOGIN_USER_INFO);
			Integer currentUserId = loginUserInfo.getUserId(); // 当前用户 id
			systemService.updateSysUserStatus(id, Status.NORMAL, currentUserId);
			rm.msgSuccess("启用成功");
		} catch (MyException me) {
			rm.msgFailed(me.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			rm.msgFailed(MyMessage.SYS_EXCEPTION_MSG);
		}
		this.renderJson(rm);
	}

	/**
	 * 删除
	 */
	@Before(IdValidator.class)
	public void remove(){
		ResponseModel rm = new ResponseModel();
		try {
			Integer id = this.getParaToInt("id");
			systemService.deleteSysUser(id);
			rm.msgSuccess("删除成功");
		} catch (MyException me) {
			rm.msgFailed(me.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			rm.msgFailed(MyMessage.SYS_EXCEPTION_MSG);
		}
		this.renderJson(rm);
	}
	
}
