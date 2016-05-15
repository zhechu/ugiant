package com.ugiant.jfinalext.controller.tpb.sys.auth;

import com.jfinal.aop.Before;
import com.ugiant.constant.tpb.SessionAttriKey;
import com.ugiant.exception.MyException;
import com.ugiant.exception.MyMessage;
import com.ugiant.jfinalbase.BaseController;
import com.ugiant.jfinalext.model.base.LoginUserInfo;
import com.ugiant.jfinalext.model.base.ResponseModel;
import com.ugiant.jfinalext.service.tpb.SystemService;
import com.ugiant.jfinalext.validator.admin.tpb.ResetPwdValidator;

/**
 * 后台用户 控制器
 * @author lingyuwang
 *
 */
public class TpbSysUserController extends BaseController {

	//@Before(UserMenuBtnInterceptor.class)
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
			LoginUserInfo loginUserInfo = (LoginUserInfo) this.getSession().getAttribute(SessionAttriKey.LOGINUSERINFO);
			if (loginUserInfo == null) {
				throw new MyException("用户未登陆");
			}
			boolean flag = SystemService.service.resetPwd(loginUserInfo.getUsername(), old_password, new_password, loginUserInfo.getUserId());
			if (!flag) {
				throw new MyException("修改密码失败");
			}
			rm.msgSuccess("修改密码成功");
		} catch (MyException me) {
			rm.msgFailed(me.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			rm.msgFailed(MyMessage.sysExceptionMsg);
		}
		this.renderJson(rm);
	}
	
}
