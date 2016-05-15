package com.ugiant.exception;

/**
 * 自定义异常
 * @author lingyuwang
 *
 */
public class MyException extends RuntimeException {

	private static final long serialVersionUID = 4353889324891977366L;

	public MyException(String string) {
		super(string);
	}

}
