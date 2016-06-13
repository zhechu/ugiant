package com.ugiant.jfinalext.controller.tpb.sys.auth;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.ugiant.constant.base.SessionAttriKey;
import com.ugiant.jfinalbase.BaseController;
import com.ugiant.jfinalbase.annotation.RequiresAuthentication;
import com.ugiant.jfinalbase.annotation.RequiresPermissions;
import com.ugiant.jfinalext.interceptor.UserMenuBtnAllInterceptor;
import com.ugiant.jfinalext.model.base.LoginUserInfo;
import com.ugiant.jfinalext.model.base.ResponseModel;
import com.ugiant.jfinalext.model.tpb.TpbDepartment;
import com.ugiant.jfinalext.service.tpb.SystemService;
import com.ugiant.jfinalext.validator.admin.tpb.TpbDepartmentValidator;
import com.ugiant.jfinalext.validator.common.IdValidator;

/**
 * 部门 控制器
 * @author lingyuwang
 *
 */
@RequiresAuthentication
public class TpbDepartmentController extends BaseController {

	private SystemService systemService = SystemService.service; // 系统管理业务 service
	
	/**
	 * 进入部门管理页
	 */
	@RequiresPermissions({"sys:manage:dept:view"})
	@Before(UserMenuBtnAllInterceptor.class)
	public void index(){
		this.render("tpb_dept_manage.ftl");
	}

	/**
	 * 获取树部门
	 */
	@RequiresPermissions({"sys:manage:dept:view"})
	public void treegrid_data(){
		this.renderJson(systemService.getRootDeptTreeJson());
	}

	/**
	 * 进入添加部门页
	 */
	@RequiresPermissions({"sys:manage:dept:add"})
	public void toAdd(){
		Integer id = this.getParaToInt("id");
		TpbDepartment dept = systemService.findDepartmentById(id);
		if (dept != null) {
			this.setAttr("tpbDepartment", dept);
		}
		this.render("tpb_dept_add.ftl");
	}

	/**
	 * 获取部门列表json字符串,直接返回
	 */
	@RequiresPermissions({"sys:manage:dept:view"})
	public void getTreeDeptJson(){
		String json = systemService.getDeptJson(0);
		this.renderJson(json);
	}

	/**
	 * 添加或更新
	 */
	@RequiresPermissions({"sys:manage:dept:add", "sys:manage:dept:edit"})
	@Before({TpbDepartmentValidator.class, Tx.class})
	public void save(){
		ResponseModel rm = new ResponseModel();
		LoginUserInfo loginUserInfo = (LoginUserInfo) getSession().getAttribute(SessionAttriKey.LOGIN_USER_INFO);
		Integer currentUserId = loginUserInfo.getUserId(); // 当前用户 id
		TpbDepartment dept = this.getModel(TpbDepartment.class);
		Integer id = dept.getInt("id");
		if (id != null) { // 更新
			systemService.updateDepartment(id, dept.getStr("name"), dept.getStr("nickname"), 
					dept.getStr("description"), dept.getInt("type"), dept.getInt("sort_no"), currentUserId);
		} else { // 添加
			systemService.addDepartment(dept, currentUserId);
		}
		rm.msgSuccess("操作部门成功");
		this.renderJson(rm);
	}

	/**
	 * 删除
	 */
	@RequiresPermissions({"sys:manage:dept:del"})
	@Before({IdValidator.class, Tx.class})
	public void remove(){
		ResponseModel rm = new ResponseModel();
		Integer id = this.getParaToInt("id");
		systemService.deleteDepartment(id);
		rm.msgSuccess("删除成功");
		this.renderJson(rm);
	}
	
}
