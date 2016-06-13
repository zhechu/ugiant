package com.ugiant.jfinalext.controller.tpb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.render.CaptchaRender;
import com.ugiant.constant.base.SessionAttriKey;
import com.ugiant.exception.MyException;
import com.ugiant.exception.MyMessage;
import com.ugiant.jfinalbase.BaseController;
import com.ugiant.jfinalext.model.base.LoginUserInfo;
import com.ugiant.jfinalext.model.base.ResponseModel;
import com.ugiant.jfinalext.model.tpb.TpbSysConstant;
import com.ugiant.jfinalext.model.tpb.TpbSysUser;
import com.ugiant.jfinalext.service.tpb.SystemService;
import com.ugiant.jfinalext.validator.admin.tpb.SysUserLoginValidator;

/**
 * 后台公开路由（无需登录） 控制器
 * @author lingyuwang
 *
 */
public class PublicController extends BaseController {

	private SystemService systemService = SystemService.service; // 系统管理业务 service
	
	/**
	 * 进入登录页
	 */
	public void index() {
		this.render("login.ftl");
	}
	
	/**
	 * 进入登录页
	 */
	public void login() {
		this.render("login.ftl");
	}

	/**
	 * 生成验证码
	 */
	public void captchaImg() {
		CaptchaRender captchaRender = new CaptchaRender();  
        this.render(captchaRender);  
	}
	
	/**
	 * 登录
	 */
	@Before(SysUserLoginValidator.class)
	public void doLogin(){
		ResponseModel rm = new ResponseModel();
		try {
			String username = this.getPara("username");
			String cryptPassword = this.getPara("password");
			// 登录验证
			TpbSysUser sysUser = systemService.login(username, cryptPassword);
			if (sysUser == null) {
				throw new MyException("登录失败");
			}
			rm.msgSuccess("登录成功");
			
			Integer userId = sysUser.getInt("id");
			Record dept = systemService.findDepartmentByUserId(userId);
			String roleIds = systemService.findRoleIdsByUserId(userId);
			
			LoginUserInfo loginUserInfo = new LoginUserInfo();
			loginUserInfo.setUserId(sysUser.getInt("id"));
			loginUserInfo.setUsername(sysUser.getStr("username"));
			if (dept != null) {
				loginUserInfo.setDeptId(dept.getInt("id"));
			}
			loginUserInfo.setRoleIds(roleIds);
			List<String> permissionList = systemService.findPermissionByRoleIds(roleIds);
			loginUserInfo.setPermissionList(permissionList);
			
			// 将登录用户信息存入 session
			this.getSession().setAttribute(SessionAttriKey.LOGIN_USER_INFO, loginUserInfo);
		} catch (MyException me) {
			rm.msgFailed(me.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			rm.msgFailed(MyMessage.SYS_EXCEPTION_MSG);
		}
		this.renderJson(rm);
	}
	
	/**
	 * 获取常量
	 */
	public void constant() {
		ResponseModel rm = new ResponseModel();
		try {
			String type = this.getPara("type");
			Integer value = this.getParaToInt("value");
			if (StrKit.notBlank(type) && value==null) { // 只根据类型
				List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
				Map<String, Object> data = null;
				List<TpbSysConstant> constantList = systemService.findConstantByType(type);
				if (constantList != null) {
					TpbSysConstant constant = null;
					int len = constantList.size();
					for (int i = 0; i < len; i++) {
						data = new HashMap<String, Object>();
						constant = constantList.get(i);
						data.put("label", constant.getStr("label"));
						data.put("value", constant.getInt("value"));
						dataList.add(data);
					}
				}
				rm.setData(dataList);
			} else { // 根据类型和值获取 label
				TpbSysConstant constant = systemService.findByTypeAndValue(type, value);
				rm.setData(constant);
			}
			rm.msgSuccess("获取常量成功");
		} catch (MyException me) {
			rm.msgFailed(me.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			rm.msgFailed(MyMessage.SYS_EXCEPTION_MSG);
		}
		this.renderJson(rm);
	}
	
}
