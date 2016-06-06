package com.ugiant.jfinalext.validator.admin.tpb;

import com.ugiant.jfinalbase.BaseValidator;
import com.jfinal.core.Controller;

/**
 * 常量 验证器
 * @author lingyuwang
 *
 */
public class TpbSysConstantValidator extends BaseValidator {

	@Override
	protected void validate(Controller c) {
		this.validateRegex("tpbSysConstant.type", "^[A-Za-z_]+$", true,"msg", "类型由字母和下划线组成");
		this.validateString("tpbSysConstant.label", 1, 40, "msg", "标签名在1-40个字符之间");
		this.validateInteger("tpbSysConstant.value", 0, Integer.MAX_VALUE, "msg", "数据值应为数值类型");
		this.validateString("tpbSysConstant.description", 1, 999, "msg", "描述在999个字之间");
		this.validateInteger("tpbSysConstant.sort", 1, 9999, "msg", "排序错误,只能在1-9999之间");
	}

	@Override
	protected void handleError(Controller c) {
		c.setAttr("success", false);
		c.renderJson();
	}

}
