package com.ugiant.jfinalext.controller.tpb.sys.auth;

import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Record;
import com.ugiant.constant.tpb.SessionAttriKey;
import com.ugiant.exception.MyException;
import com.ugiant.exception.MyMessage;
import com.ugiant.jfinalbase.BaseController;
import com.ugiant.jfinalext.interceptor.UserMenuBtnAllInterceptor;
import com.ugiant.jfinalext.model.base.LoginUserInfo;
import com.ugiant.jfinalext.model.base.ResponseModel;
import com.ugiant.jfinalext.service.tpb.SystemService;

/**
 * 后台菜单管理 控制器
 * @author lingyuwang
 *
 */
public class TpbMenuController extends BaseController {

	/**
	 * 进入菜单管理页
	 */
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
			LoginUserInfo loginUserInfo = (LoginUserInfo) getSession().getAttribute(SessionAttriKey.LOGINUSERINFO);
			if (loginUserInfo == null) {
				throw new MyException("用户未登陆");
			}
			String roleIds = loginUserInfo.getRoleIds();
			if (StrKit.isBlank(roleIds)) {
				throw new MyException("用户未分配角色");
			}
			List<Record> list = SystemService.service.findMenuByParentIdAndRoleIds(0, roleIds);
			rm.msgSuccess("获取用户角色成功");
			rm.setData(list);
		} catch (MyException me) {
			rm.msgFailed(me.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			rm.msgFailed(MyMessage.sysExceptionMsg);
		}
		this.renderJson(rm);
	}
	
	/**
	 * 获取树菜单
	 */
	public void treegrid_data(){
		this.renderJson(SystemService.service.getRootMenuTreeJson());
	}
	
}
