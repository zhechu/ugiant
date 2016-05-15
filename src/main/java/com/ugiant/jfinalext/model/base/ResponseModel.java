package com.ugiant.jfinalext.model.base;

import java.io.Serializable;

/**
 * 返回数据 model
 * @author lingyuwang
 *
 */
public class ResponseModel implements Serializable {
	
	private static final long serialVersionUID = -5109787761925139443L;
	
	private Boolean success;				//调用是否成功
	private String code;					//调用返回错误代码
	private String msg;						//调用返回错误信息
	private Object data;					//调用返回结果
	
	public Boolean getSuccess() {
		return success;
	}
	
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public Object getData() {
		return data;
	}
	
	public void setData(Object data) {
		this.data = data;
	}
	
	public ResponseModel(){
		success = false;
	}
	
	public void msgFailed(String msg){
		this.success = false;
		this.msg = msg;
	}
	
	public void msgSuccess(String msg){
		this.success = true;
		this.msg = msg;
	}
	
	public void msgSuccess(){
		this.success = true;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
}
