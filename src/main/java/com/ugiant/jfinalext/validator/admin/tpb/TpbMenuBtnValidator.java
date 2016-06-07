package com.ugiant.jfinalext.validator.admin.tpb;

import com.ugiant.jfinalbase.BaseValidator;
import com.jfinal.core.Controller;

/**
 * 菜单按钮 验证器
 * @author lingyuwang
 *
 */
public class TpbMenuBtnValidator extends BaseValidator {

	@Override
	protected void validate(Controller c) {
		this.validateInteger("tpbMenuBtn.menu_id", 1, Integer.MAX_VALUE, "msg", "菜单信息错误");
		this.validateInteger("tpbMenuBtn.type", 1, 2, "msg", "按钮类型错误");
		this.validateString("tpbMenuBtn.btn_name", 1, 10, "msg", "菜单名称在1-10个字符之间");
		this.validateRegex("tpbMenuBtn.btn_code", "^[a-zA-Z0-9_]{1,50}", true, "msg", "按钮编码错误");
		this.validateString("tpbMenuBtn.permission", 1, 40, "msg", "权限标识在1-40个字符之间");
		this.validateInteger("tpbMenuBtn.sort_no", 1, 9999, "msg", "排序在1-9999之间，小的排在前面");
		this.validateRegex("tpbMenuBtn.icon_cls", "^[a-zA-Z0-9_-]{0,50}", true, "msg", "按钮样式错误");
	}

	@Override
	protected void handleError(Controller c) {
		c.setAttr("success", false);
		c.renderJson();
	}

}
