package com.ugiant.jfinalext.validator.admin.tpb;

import com.ugiant.jfinalbase.BaseValidator;
import com.jfinal.core.Controller;

/**
 * 部门 验证器
 * @author lingyuwang
 *
 */
public class TpbDepartmentValidator extends BaseValidator {

	@Override
	protected void validate(Controller c) {
		this.validateString("tpbDepartment.name", 1, 40, "msg", "部门名称在1-40个字符之间");
		this.validateString("tpbDepartment.nickname", 1, 40, "msg", "部门别名在1-40个字符之间");
		this.validateInteger("tpbDepartment.type", 0, Integer.MAX_VALUE, "msg", "部门类型未选择");
		this.validateString("tpbDepartment.description", 1, 40, "msg", "部门描述在250个字之间");
		this.validateInteger("tpbDepartment.sort_no", 1, 9999, "msg", "排序错误,只能在1-9999之间");
	}

	@Override
	protected void handleError(Controller c) {
		c.setAttr("success", false);
		c.renderJson();
	}

}
