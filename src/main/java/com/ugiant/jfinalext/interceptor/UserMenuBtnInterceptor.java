package com.ugiant.jfinalext.interceptor;


import java.util.List;

import com.jfinal.aop.Invocation;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Record;
import com.ugiant.jfinalbase.BaseInterceptor;
import com.ugiant.jfinalext.model.tpb.TpbMenu;
import com.ugiant.jfinalext.model.tpb.TpbRoleMenuBtn;
import com.ugiant.jfinalext.service.tpb.SystemService;

/**
 * 用户菜单按钮 拦截器
 * @author lingyuwang
 *
 */
public class UserMenuBtnInterceptor extends BaseInterceptor {

	public void intercept(Invocation ai) {
		String uri = ai.getController().getRequest().getRequestURI();
		if(StrKit.notBlank(uri)){
			if(uri.endsWith("/")){
				uri = uri.substring(0, uri.length()-1);
			}
			TpbMenu menu = SystemService.service.findMenuByLinkUrl(uri);
			if(menu != null){
				/*List<Record> btnList = TpbRoleMenuBtn.me.findByParams(SessionUtil.getLoginUserRoleIds(ai.getController().getSession()),menu.getInt("id"), TpbMenuBtnStatus.normal,TpbMenuBtnType.part);
				List<Record> globalBtnList = TpbRoleMenuBtn.me.findByParams(SessionUtil.getLoginUserRoleIds(ai.getController().getSession()),menu.getInt("id"), TpbMenuBtnStatus.normal,TpbMenuBtnType.global);
				ai.getController().setAttr("btnList", btnList);
				ai.getController().setAttr("globalBtnList", globalBtnList);
				ai.getController().setAttr("uri", uri);*/
			}
		}
		ai.invoke();
		
	}

}
