package com.ugiant.jfinalbase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jfinal.validate.Validator;

/**
 * 验证器 基类
 * @author lingyuwang
 *
 */
public abstract class BaseValidator extends Validator {

	/**
	 * 验证字符串
	 * @param value 值
	 * @param regExpression 正则表达式
	 * @param isCaseSensitive 是否大小写敏感
	 * @param errorKey 错误信息键
	 * @param errorMessage 错误信息值
	 */
	protected void validateRegexString(String value, String regExpression, boolean isCaseSensitive, String errorKey, String errorMessage) {
		if (value == null) {
        	addError(errorKey, errorMessage);
        	return ;
        }
        Pattern pattern = isCaseSensitive ? Pattern.compile(regExpression) : Pattern.compile(regExpression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(value);
        if (!matcher.matches())
        	addError(errorKey, errorMessage);
	}
	
}
