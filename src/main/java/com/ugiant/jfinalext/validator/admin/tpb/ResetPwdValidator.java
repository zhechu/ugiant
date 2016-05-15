package com.ugiant.jfinalext.validator.admin.tpb;

import com.ugiant.jfinalbase.BaseValidator;
import com.jfinal.core.Controller;

public class ResetPwdValidator extends BaseValidator {

	@Override
	protected void validate(Controller c) {
		this.validateRegex("old_password", "^[A-Za-z0-9]{6,18}$", true,"msg", "密码在6-18之间,由字母和数据组成");
		this.validateRegex("new_password", "^[A-Za-z0-9]{6,18}$", true,"msg", "密码在6-18之间,由字母和数据组成");
		this.validateEqualField("new_password", "sure_password", "msg", "两次输入的新密码不一致");
	}

	@Override
	protected void handleError(Controller c) {
		c.setAttr("success", false);
		c.renderJson();
	}

}
