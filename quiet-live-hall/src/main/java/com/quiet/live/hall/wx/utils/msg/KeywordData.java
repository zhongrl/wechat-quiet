package com.quiet.live.hall.wx.utils.msg;

import java.io.Serializable;

import lombok.Data;

@Data
public class KeywordData implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 512935140717092452L;

	 private String value;
	 private String color;
	 

	  public KeywordData(String value, String color) {
	    this.value = value;
	    this.color = color;
	  }
}
