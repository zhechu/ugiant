package com.ugiant.jfinalext.controller.tpb.sys.auth;

import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.ugiant.constant.base.SessionAttriKey;
import com.ugiant.constant.base.Status;
import com.ugiant.constant.tpb.TpbMenuType;
import com.ugiant.exception.MyException;
import com.ugiant.exception.MyMessage;
import com.ugiant.jfinalbase.BaseController;
import com.ugiant.jfinalbase.annotation.RequiresAuthentication;
import com.ugiant.jfinalbase.annotation.RequiresPermissions;
import com.ugiant.jfinalext.interceptor.UserMenuBtnAllInterceptor;
import com.ugiant.jfinalext.model.base.LoginUserInfo;
import com.ugiant.jfinalext.model.base.ResponseModel;
import com.ugiant.jfinalext.model.tpb.TpbMenu;
import com.ugiant.jfinalext.service.tpb.SystemService;
import com.ugiant.jfinalext.validator.admin.tpb.TpbMenuValidator;
import com.ugiant.jfinalext.validator.common.IdValidator;

/**
 * 后台菜单管理 控制器
 * @author lingyuwang
 *
 */
@RequiresAuthentication
public class TpbMenuController extends BaseController {

	private SystemService systemService = SystemService.service; // 系统管理业务 service
	
	/**
	 * 进入菜单管理页
	 */
	@RequiresPermissions({"sys:manage:menu:view"})
	@Before(UserMenuBtnAllInterceptor.class)
	public void index(){
		this.render("tpb_menu_manage.ftl");
	}
	
	/**
	 * 根据角色获取用户菜单
	 */
	public void getRoleMenuList(){
		ResponseModel rm = new ResponseModel();
		try {
			LoginUserInfo loginUserInfo = (LoginUserInfo) getSession().getAttribute(SessionAttriKey.LOGIN_USER_INFO);
			String roleIds = loginUserInfo.getRoleIds();
			if (StrKit.isBlank(roleIds)) {
				throw new MyException("未分配用户角色");
			}
			List<Record> list = systemService.findMenuByParentIdAndRoleIds(0, roleIds);
			rm.msgSuccess("获取用户角色成功");
			rm.setData(list);
		} catch (MyException me) {
			rm.msgFailed(me.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			rm.msgFailed(MyMessage.SYS_EXCEPTION_MSG);
		}
		this.renderJson(rm);
	}
	
	/**
	 * 获取树菜单
	 */
	@RequiresPermissions({"sys:manage:menu:view"})
	public void treegrid_data(){
		this.renderJson(systemService.getRootMenuTreeJson());
	}

	/**
	 * 进入添加菜单页面
	 */
	@RequiresPermissions({"sys:manage:menu:add"})
	public void toAdd(){
		Integer id = this.getParaToInt("id");
		TpbMenu menu = systemService.findMenuById(id);
		if (menu != null) {
			this.setAttr("tpbMenu", menu);
		}
		this.render("tpb_menu_add.ftl");
	}
	
	/**
	 * 获取菜单列表json字符串,直接返回
	 */
	@RequiresPermissions({"sys:manage:menu:view"})
	public void getTreeMenuJson(){
		String json = systemService.getMenuJson(0, null, TpbMenuType.ADMIN);
		this.renderJson(json);
	}
	
	/**
	 * 添加或更新菜单
	 */
	@RequiresPermissions({"sys:manage:menu:add", "sys:manage:menu:edit"})
	@Before({TpbMenuValidator.class, Tx.class})
	public void save(){
		ResponseModel rm = new ResponseModel();
		LoginUserInfo loginUserInfo = (LoginUserInfo) getSession().getAttribute(SessionAttriKey.LOGIN_USER_INFO);
		Integer currentUserId = loginUserInfo.getUserId(); // 当前用户 id
		TpbMenu menu = this.getModel(TpbMenu.class);
		Integer menuId = menu.getInt("id");
		if (menuId != null) { // 更新
			systemService.updateMenu(menuId, menu.getStr("code"), menu.getStr("name"), menu.getStr("link_url"), menu.getInt("sort_no"), menu.getStr("icon_cls"), currentUserId);
		} else { // 添加
			systemService.addMenu(menu, currentUserId);
		}
		rm.msgSuccess("操作菜单成功");
		this.renderJson(rm);
	}

	/**
	 * 禁用
	 */
	@RequiresPermissions({"sys:manage:menu:forbidden"})
	@Before({IdValidator.class, Tx.class})
	public void forbidden(){
		ResponseModel rm = new ResponseModel();
		Integer id = this.getParaToInt("id");
		LoginUserInfo loginUserInfo = (LoginUserInfo) getSession().getAttribute(SessionAttriKey.LOGIN_USER_INFO);
		Integer currentUserId = loginUserInfo.getUserId(); // 当前用户 id
		systemService.updateMenuStatus(id, Status.FORBIDDEN, currentUserId);
		rm.msgSuccess("禁用成功");
		this.renderJson(rm);
	}
	
	/**
	 * 启用
	 */
	@RequiresPermissions({"sys:manage:menu:normal"})
	@Before({IdValidator.class, Tx.class})
	public void normal(){
		ResponseModel rm = new ResponseModel();
		Integer id = this.getParaToInt("id");
		LoginUserInfo loginUserInfo = (LoginUserInfo) getSession().getAttribute(SessionAttriKey.LOGIN_USER_INFO);
		Integer currentUserId = loginUserInfo.getUserId(); // 当前用户 id
		systemService.updateMenuStatus(id, Status.NORMAL, currentUserId);
		rm.msgSuccess("禁用成功");
		this.renderJson(rm);
	}
	
	/**
	 * 删除
	 */
	@RequiresPermissions({"sys:manage:menu:del"})
	@Before({IdValidator.class, Tx.class})
	public void remove(){
		ResponseModel rm = new ResponseModel();
		Integer id = this.getParaToInt("id");
		systemService.deleteMenu(id);
		rm.msgSuccess("删除成功");
		this.renderJson(rm);
	}
	
}
