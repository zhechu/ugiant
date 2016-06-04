package com.ugiant.jfinalext.validator.admin.tpb;

import com.ugiant.jfinalbase.BaseValidator;
import com.jfinal.core.Controller;

/**
 * 角色 验证器
 * @author lingyuwang
 *
 */
public class TpbRoleValidator extends BaseValidator {

	@Override
	protected void validate(Controller c) {
		this.validateString("tpbRole.name", 1, 30, "msg", "角色名称必填，且在1-30个字之间");
		this.validateString("tpbRole.description", 0, 200, "msg", "角色描述不能超过200个字");
	}

	@Override
	protected void handleError(Controller c) {
		c.setAttr("success", false);
		c.renderJson();
	}

}
