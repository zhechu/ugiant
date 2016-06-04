package com.ugiant.jfinalext.validator.common;

import com.ugiant.jfinalbase.BaseValidator;
import com.jfinal.core.Controller;

/**
 * ID 验证器
 * @author lingyuwang
 *
 */
public class IdValidator extends BaseValidator {

	@Override
	protected void validate(Controller c) {
		this.validateInteger("id", 1, Integer.MAX_VALUE, "msg", "未选择数据");
	}

	@Override
	protected void handleError(Controller c) {
		c.setAttr("success", false);
		c.renderJson();
	}

}
