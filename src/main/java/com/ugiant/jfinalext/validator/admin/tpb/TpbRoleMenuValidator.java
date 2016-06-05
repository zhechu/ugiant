package com.ugiant.jfinalext.validator.admin.tpb;

import com.ugiant.jfinalbase.BaseValidator;
import com.ugiant.util.ValidatorUtil;
import com.jfinal.core.Controller;

/**
 * 权限 验证器
 * @author lingyuwang
 *
 */
public class TpbRoleMenuValidator extends BaseValidator {

	@Override
	protected void validate(Controller c) {
		Integer roleId = c.getParaToInt("role_id");
		if (roleId == null) {
			this.addError("msg", "未选择角色");
		}

		String menuIds = c.getPara("menuIds");
		String btnIds = c.getPara("btnIds");
		String regex = "^([0-9]{1,9}[,])*([0-9]{1,9})*";
		if (!ValidatorUtil.validateStr(menuIds, regex)) {
			this.addError("msg", "菜单参数错误");
			return;
		}
		if (!ValidatorUtil.validateStr(btnIds, regex)) {
			this.addError("msg", "按钮参数错误");
			return;
		}
	}

	@Override
	protected void handleError(Controller c) {
		c.renderJson();
	}

}
