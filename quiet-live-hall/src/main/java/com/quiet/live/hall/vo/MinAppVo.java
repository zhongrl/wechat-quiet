package com.quiet.live.hall.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class MinAppVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5720223734071171607L;


	private String sessionKey,  signature,
     rawData,  encryptedData,  iv,code;
	
	
}
