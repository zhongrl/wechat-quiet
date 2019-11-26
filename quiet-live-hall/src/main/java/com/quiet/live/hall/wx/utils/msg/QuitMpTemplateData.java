package com.quiet.live.hall.wx.utils.msg;

import java.io.Serializable;
import java.util.Map;

import lombok.Data;

@Data
public class QuitMpTemplateData implements Serializable{

	  /**
	 * 
	 */
	private static final long serialVersionUID = -5651983701164318517L;
	
	  private String name;
	 
	  private KeywordData keywordData;
	 

	  public QuitMpTemplateData(String name ,KeywordData keywordData){
		  this.name = name ;
		  this.keywordData = keywordData;
	  }
}
