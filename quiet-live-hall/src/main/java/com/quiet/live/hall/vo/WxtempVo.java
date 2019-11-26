package com.quiet.live.hall.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class WxtempVo implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 3126077304760759245L;

	private String title;
	private String keyword1;
	private String keyword2;
	private String keyword3;
	private String keyword4;
	private String keyword5;
	private String url;
	private String remark;
	private String msgType;
	private String openId;
	private String msgId;
	private String userName;
	private String userId;
}
