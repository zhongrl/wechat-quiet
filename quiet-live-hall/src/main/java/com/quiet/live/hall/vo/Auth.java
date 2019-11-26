package com.quiet.live.hall.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class Auth implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4698447811179349187L;
	
	//@ApiField(name="微信code",remark="微信code",show= true)
	private String code;
	
	//@ApiField(name="账户名",remark="账户名",show= true)
	private String account;
	
	//@ApiField(name="密码",remark="密码",show= true)
	private String password;
	
	//@ApiField(name="openId",remark="openId",show= true)
	private String openId;
	
	//@ApiField(name="菜单",remark="菜单",show= true)
	private String menu;
	
	private String token;
}
