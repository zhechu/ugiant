package com.ugiant.jfinalext.controller.tpb;

import com.ugiant.jfinalbase.BaseController;

/**
 * 系统管理 控制器
 * @author lingyuwang
 *
 */
public class AdminController extends BaseController {
	
	/**
	 * 进入后台首页
	 */
	public void index() {
		this.render("index.ftl");
	}

	/**
	 * 登出
	 */
	public void logout(){
		this.getSession().invalidate();
		this.redirect("/");
	}
	
}
