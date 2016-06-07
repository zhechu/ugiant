package com.ugiant.jfinalext.validator.admin.tpb;

import com.ugiant.jfinalbase.BaseValidator;
import com.jfinal.core.Controller;

/**
 * 添加菜单验证器
 * @author lingyuwang
 *
 */
public class TpbMenuValidator extends BaseValidator {

	@Override
	protected void validate(Controller c) {
		this.validateString("tpbMenu.name", 1, 40, "msg", "菜单名称在1-40个字符之间");
		this.validateString("tpbMenu.permission", 1, 40, "msg", "权限标识在1-40个字符之间");
		this.validateInteger("tpbMenu.sort_no", 1, Integer.MAX_VALUE, "msg", "请输入正确的排序值");
	}

	@Override
	protected void handleError(Controller c) {
		c.renderJson();
	}

}
