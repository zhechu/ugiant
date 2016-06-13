package com.ugiant.jfinalext.controller.tpb.sys.auth;

import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.ugiant.constant.base.SessionAttriKey;
import com.ugiant.constant.base.Status;
import com.ugiant.exception.MyException;
import com.ugiant.exception.MyMessage;
import com.ugiant.jfinalbase.BaseController;
import com.ugiant.jfinalbase.annotation.RequiresAuthentication;
import com.ugiant.jfinalbase.annotation.RequiresPermissions;
import com.ugiant.jfinalext.interceptor.UserMenuBtnAllInterceptor;
import com.ugiant.jfinalext.model.base.LoginUserInfo;
import com.ugiant.jfinalext.model.base.ResponseModel;
import com.ugiant.jfinalext.model.tpb.TpbRole;
import com.ugiant.jfinalext.service.tpb.SystemService;
import com.ugiant.jfinalext.validator.admin.tpb.TpbRoleValidator;
import com.ugiant.jfinalext.validator.common.IdValidator;

/**
 * 角色 控制器
 * @author lingyuwang
 *
 */
@RequiresAuthentication
public class TpbRoleController extends BaseController {

	private SystemService systemService = SystemService.service; // 系统管理业务 service
	
	/**
	 * 进入角色管理页
	 */
	@RequiresPermissions({"sys:manage:role:view"})
	@Before(UserMenuBtnAllInterceptor.class)
	public void index(){
		this.render("tpb_role_manage.ftl");
	}
	
	/**
	 * 获取角色数据
	 */
	@RequiresPermissions({"sys:manage:role:view"})
	public void data(){
		List<Record> data = systemService.findRole();
		this.setAttr("rows", data);
		this.renderJson();
	}

	/**
	 * 获取可用的角色
	 */
	@RequiresPermissions({"sys:manage:role:view"})
	public void datalist(){
		this.renderJson(systemService.findRoleByStatus(Status.NORMAL));
	}
	
	/**
	 * 获取角色列表
	 */
	@RequiresPermissions({"sys:manage:role:view"})
	public void list() {
		ResponseModel rm = new ResponseModel();
		try {
			List<Record> roleList = systemService.findRole();
			rm.setData(roleList);
			rm.msgSuccess("获取角色成功");
		} catch (MyException me) {
			rm.msgFailed(me.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			rm.msgFailed(MyMessage.SYS_EXCEPTION_MSG);
		}
		this.renderJson(rm);
	}
	
	/**
	 * 进入添加角色页面
	 */
	@RequiresPermissions({"sys:manage:role:add"})
	public void toAdd(){
		Integer id = this.getParaToInt("id");
		TpbRole role = systemService.findRoleById(id);
		if (role != null) {
			this.setAttr("tpbRole", role);
		}
		this.render("tpb_role_add.ftl");
	}

	/**
	 * 添加或更新
	 */
	@RequiresPermissions({"sys:manage:role:add", "sys:manage:role:edit"})
	@Before({TpbRoleValidator.class, Tx.class})
	public void save(){
		ResponseModel rm = new ResponseModel();
		LoginUserInfo loginUserInfo = (LoginUserInfo) getSession().getAttribute(SessionAttriKey.LOGIN_USER_INFO);
		Integer currentUserId = loginUserInfo.getUserId(); // 当前用户 id
		TpbRole role = this.getModel(TpbRole.class);
		Integer id = role.getInt("id");
		if (id != null) { // 更新
			systemService.updateRole(id, role.getStr("name"), role.getStr("description"), currentUserId);
		} else { // 添加
			systemService.addRole(role, currentUserId);
		}
		rm.msgSuccess("操作角色成功");
		this.renderJson(rm);
	}

	/**
	 * 禁用
	 */
	@RequiresPermissions({"sys:manage:role:forbidden"})
	@Before({IdValidator.class, Tx.class})
	public void forbidden(){
		ResponseModel rm = new ResponseModel();
		Integer id = this.getParaToInt("id");
		LoginUserInfo loginUserInfo = (LoginUserInfo) getSession().getAttribute(SessionAttriKey.LOGIN_USER_INFO);
		Integer currentUserId = loginUserInfo.getUserId(); // 当前用户 id
		systemService.updateRoleStatus(id, Status.FORBIDDEN, currentUserId);
		rm.msgSuccess("禁用成功");
		this.renderJson(rm);
	}
	
	/**
	 * 启用
	 */
	@RequiresPermissions({"sys:manage:role:normal"})
	@Before({IdValidator.class, Tx.class})
	public void normal(){
		ResponseModel rm = new ResponseModel();
		Integer id = this.getParaToInt("id");
		LoginUserInfo loginUserInfo = (LoginUserInfo) getSession().getAttribute(SessionAttriKey.LOGIN_USER_INFO);
		Integer currentUserId = loginUserInfo.getUserId(); // 当前用户 id
		systemService.updateRoleStatus(id, Status.NORMAL, currentUserId);
		rm.msgSuccess("启用成功");
		this.renderJson(rm);
	}

	/**
	 * 删除
	 */
	@RequiresPermissions({"sys:manage:role:del"})
	@Before({IdValidator.class, Tx.class})
	public void remove(){
		ResponseModel rm = new ResponseModel();
		Integer id = this.getParaToInt("id");
		systemService.deleteRole(id);
		rm.msgSuccess("删除成功");
		this.renderJson(rm);
	}
	
}
