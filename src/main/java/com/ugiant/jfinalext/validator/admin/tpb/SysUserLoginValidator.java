package com.ugiant.jfinalext.validator.admin.tpb;

import com.ugiant.jfinalbase.BaseValidator;
import com.ugiant.util.CryptUtil;
import com.jfinal.core.Controller;
import com.jfinal.render.CaptchaRender;
import com.jfinal.kit.StrKit;

/**
 * 后台用户登录验证器
 * @author lingyuwang
 *
 */
public class SysUserLoginValidator extends BaseValidator {

	@Override
	protected void validate(Controller c) {
		// 用户名验证
		this.validateRegex("username", "^[A-Za-z0-9]{4,25}$", true, "msg", "用户名用大小写字母和数字组成，4-25位");
		
		// 密码验证
		String cryptPassword = c.getPara("password");
		if (StrKit.notBlank(cryptPassword)) {
			String password = CryptUtil.getFromBase64(cryptPassword);
			this.validateRegexString(password, "^[A-Za-z0-9]{6,18}$", true,"msg", "密码在6-18之间,由字母和数据组成");
		} else {
			this.addError("msg", "密码未填写");
		}
		
		// 验证码验证
		/*String cryptCaptcha = c.getPara("captcha");
		if (StrKit.notBlank(cryptCaptcha)) {
			if (!CaptchaRender.validate(c, CryptUtil.getFromBase64(cryptCaptcha))) {
				this.addError("msg", "验证码不正确");
			}
		} else {
			this.addError("msg", "验证码未填写");
		}*/
	}

	@Override
	protected void handleError(Controller c) {
		c.setAttr("success", false);
		c.renderJson();
	}

}
