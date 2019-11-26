package com.quiet.live.hall.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class CustomerVo implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -1939072287036728336L;

	private String token;
	
	private boolean isMobile;
	
	private String openId;
}
