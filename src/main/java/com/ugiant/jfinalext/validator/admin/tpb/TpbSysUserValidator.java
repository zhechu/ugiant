package com.ugiant.jfinalext.validator.admin.tpb;
import com.ugiant.jfinalbase.BaseValidator;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;

/**
 * 后台用户 验证器
 * @author lingyuwang
 *
 */
public class TpbSysUserValidator extends BaseValidator {

	@Override
	protected void validate(Controller c) {
		this.validateRegex("tpbSysUser.username", "^[A-Za-z0-9]{4,25}$", true, "msg", "用户名用大小写字母和数字组成，4-25位");
		this.validateString("tpbSysUser.nickname", 0, 25, "msg", "用户昵称最长不超过25位");
		
		Integer id = c.getParaToInt("tpbSysUser.id");
		if (id == null) { // 添加
			this.validateRegex("password", "^[A-Za-z0-9]{6,18}$", true,"msg", "密码在6-18之间,由字母和数据组成");
			this.validateEqualField("password", "sure_password", "msg", "两次输入的新密码不一致");
		} else { // 编辑
			String password = c.getPara("password");
			if (StrKit.notBlank(password)) { // 密码不为空时，才验证
				this.validateRegex("password", "^[A-Za-z0-9]{6,18}$", true,"msg", "密码在6-18之间,由字母和数据组成");
				this.validateEqualField("password", "sure_password", "msg", "两次输入的新密码不一致");
			}
		}
	}

	@Override
	protected void handleError(Controller c) {
		c.setAttr("success", false);
		c.renderJson();
	}

}
