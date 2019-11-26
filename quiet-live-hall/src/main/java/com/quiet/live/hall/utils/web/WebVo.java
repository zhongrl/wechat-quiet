package com.quiet.live.hall.utils.web;

import java.io.Serializable;

import com.quiet.live.hall.constants.Constant;

import ch.qos.logback.core.util.ContextUtil;


/**
 * web基础vo
 * 
 * @author ky.zhongrl
 *
 */
public class WebVo<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	private String code;
	private String msg;
	private T data;
	
	
	public WebVo() {
		super();
	}

	public WebVo(String code,String msg){
		this.code = code;
		this.msg = msg;
	}
	
	public WebVo(String code,String msg,T data){
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
	


	public static <T> WebVo<T> success(){
		return new WebVo<T>(Constant.CommonCode.SUCCESS,Constant.CommonCode.SUCCESS_MSG);
	}
	
	public static <T> WebVo<T> success(T data){
		return new WebVo<T>(Constant.CommonCode.SUCCESS,Constant.CommonCode.SUCCESS_MSG,data);
	}
	
	public static <T> WebVo<T> error(String msg){
		return new WebVo<T>(Constant.CommonCode.SYS_ERROR,msg);
	}
	
	public static <T> WebVo<T> result(String code,String msg){
		return new WebVo<T>(code,msg);
	}
	
	public static <T> WebVo<T> result(String code,String msg,T obj){
		return new WebVo<T>(code,msg,obj);
	}
	
	public String getCode() {
		return code;
	}
	public WebVo<T> setCode(String code) {
		this.code = code;
		return this;
	}
	public String getMsg() {
		return msg;
	}
	public WebVo<T> setMsg(String msg) {
		this.msg = msg;
		return this;
	}
	public T getData() {
		return data;
	}
	public WebVo<T> setData(T data) {
		this.data = data;
		return this;
	}
}
