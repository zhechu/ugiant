package com.ugiant.jfinalext.interceptor;


import java.util.List;

import com.jfinal.aop.Invocation;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Record;
import com.ugiant.constant.base.SessionAttriKey;
import com.ugiant.jfinalbase.BaseInterceptor;
import com.ugiant.jfinalext.model.base.LoginUserInfo;
import com.ugiant.jfinalext.model.tpb.TpbMenu;
import com.ugiant.jfinalext.service.tpb.SystemService;

/**
 * 用户菜单按钮拦截器
 * @author lingyuwang
 *
 */
public class UserMenuBtnAllInterceptor extends BaseInterceptor {

	public void intercept(Invocation ai) {
		String uri = ai.getController().getRequest().getRequestURI();
		if(StrKit.notBlank(uri)){
			if(uri.endsWith("/")){
				uri = uri.substring(0, uri.length()-1);
			}
			TpbMenu menu = SystemService.service.findMenuByLinkUrl(uri);
			if(menu != null){
				LoginUserInfo loginUserInfo = (LoginUserInfo) ai.getController().getSession().getAttribute(SessionAttriKey.LOGIN_USER_INFO);
				List<Record> list = SystemService.service.findNormalRoleMenuBtnByParams(menu.getInt("id"), loginUserInfo.getRoleIds());
				ai.getController().setAttr("btnList", list);
			}
		}
		ai.invoke();
	}

}
