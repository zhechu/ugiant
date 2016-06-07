package com.ugiant.jfinalext.interceptor;


import com.jfinal.aop.Invocation;
import com.ugiant.jfinalbase.BaseInterceptor;

/**
 * 权限 拦截器
 * @author lingyuwang
 *
 */
public class AuthInterceptor extends BaseInterceptor {

	public void intercept(Invocation ai) {
		
		ai.invoke();
	}

}
