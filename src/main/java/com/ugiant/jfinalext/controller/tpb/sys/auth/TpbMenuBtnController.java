package com.ugiant.jfinalext.controller.tpb.sys.auth;

import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.ugiant.constant.base.Flag;
import com.ugiant.constant.base.SessionAttriKey;
import com.ugiant.constant.base.Status;
import com.ugiant.exception.MyException;
import com.ugiant.jfinalbase.BaseController;
import com.ugiant.jfinalbase.annotation.RequiresAuthentication;
import com.ugiant.jfinalbase.annotation.RequiresPermissions;
import com.ugiant.jfinalext.interceptor.UserMenuBtnAllInterceptor;
import com.ugiant.jfinalext.model.base.LoginUserInfo;
import com.ugiant.jfinalext.model.base.ResponseModel;
import com.ugiant.jfinalext.model.tpb.TpbMenu;
import com.ugiant.jfinalext.model.tpb.TpbMenuBtn;
import com.ugiant.jfinalext.service.tpb.SystemService;
import com.ugiant.jfinalext.validator.admin.tpb.TpbMenuBtnValidator;
import com.ugiant.jfinalext.validator.common.IdValidator;

/**
 * 菜单按钮管理 控制器
 * @author lingyuwang
 *
 */
@RequiresAuthentication
public class TpbMenuBtnController extends BaseController {

	private SystemService systemService = SystemService.service; // 系统管理业务 service
	
	/**
	 * 进入菜单按钮管理页
	 */
	@RequiresPermissions({"sys:manage:menubtn:view"})
	@Before(UserMenuBtnAllInterceptor.class)
	public void index(){
		this.render("tpb_menu_btn_manage.ftl");
	}
	
	/**
	 * 获取菜单按钮数据
	 */
	@RequiresPermissions({"sys:manage:menubtn:view"})
	public void data(){
		Integer menuId = this.getParaToInt("menuId"); // 菜单 id
		List<Record> data = systemService.findMenuBtnByMenuId(menuId);
		this.setAttr("rows", data);
		this.renderJson();
	}
	
	/**
	 * 进入添加菜单按钮页面
	 */
	@RequiresPermissions({"sys:manage:menubtn:add"})
	public void toAdd(){
		Integer id = this.getParaToInt("id");
		TpbMenuBtn menuBtn = systemService.findMenuBtnById(id);
		if (menuBtn != null) {
			this.setAttr("tpbMenuBtn", menuBtn);
		}
		this.render("tpb_menu_btn_add.ftl");
	}
	
	/**
	 * 添加或更新
	 */
	@RequiresPermissions({"sys:manage:menubtn:add", "sys:manage:menubtn:edit"})
	@Before({TpbMenuBtnValidator.class, Tx.class})
	public void save(){
		ResponseModel rm = new ResponseModel();
		LoginUserInfo loginUserInfo = (LoginUserInfo) getSession().getAttribute(SessionAttriKey.LOGIN_USER_INFO);
		Integer currentUserId = loginUserInfo.getUserId(); // 当前用户 id
		TpbMenuBtn menuBtn = this.getModel(TpbMenuBtn.class);
		Integer menuId = menuBtn.getInt("menu_id");
		TpbMenu menu = systemService.findMenuById(menuId);
		if (menu!=null && menu.getInt("is_parent")==Flag.YES) { // 父菜单节点，没有绑定按钮
			throw new MyException("父菜单不能添加按钮");
		}
		
		Integer menuBtnId = menuBtn.getInt("id");
		if (menuBtnId != null) { // 更新
			systemService.updateMenuBtn(menuBtnId, menuBtn.getStr("btn_name"), menuBtn.getStr("code"), menuBtn.getInt("sort_no"), menuBtn.getInt("type"), menuBtn.getStr("icon_cls"), currentUserId);
		} else { // 添加
			systemService.addMenuBtn(menuBtn, currentUserId);
		}
		rm.msgSuccess("操作菜单按钮成功");
		this.renderJson(rm);
	}

	/**
	 * 禁用
	 */
	@RequiresPermissions({"sys:manage:menubtn:forbidden"})
	@Before({IdValidator.class, Tx.class})
	public void forbidden(){
		ResponseModel rm = new ResponseModel();
		Integer id = this.getParaToInt("id");
		LoginUserInfo loginUserInfo = (LoginUserInfo) getSession().getAttribute(SessionAttriKey.LOGIN_USER_INFO);
		Integer currentUserId = loginUserInfo.getUserId(); // 当前用户 id
		systemService.updateMenuBtnStatus(id, Status.FORBIDDEN, currentUserId);
		rm.msgSuccess("禁用成功");
		this.renderJson(rm);
	}
	
	/**
	 * 启用
	 */
	@RequiresPermissions({"sys:manage:menubtn:normal"})
	@Before({IdValidator.class, Tx.class})
	public void normal(){
		ResponseModel rm = new ResponseModel();
		Integer id = this.getParaToInt("id");
		LoginUserInfo loginUserInfo = (LoginUserInfo) getSession().getAttribute(SessionAttriKey.LOGIN_USER_INFO);
		Integer currentUserId = loginUserInfo.getUserId(); // 当前用户 id
		systemService.updateMenuBtnStatus(id, Status.NORMAL, currentUserId);
		rm.msgSuccess("启用成功");
		this.renderJson(rm);
	}

	/**
	 * 删除
	 */
	@RequiresPermissions({"sys:manage:menubtn:del"})
	@Before({IdValidator.class, Tx.class})
	public void remove(){
		ResponseModel rm = new ResponseModel();
		Integer id = this.getParaToInt("id");
		systemService.deleteMenuBtn(id);
		rm.msgSuccess("删除成功");
		this.renderJson(rm);
	}
	
}
