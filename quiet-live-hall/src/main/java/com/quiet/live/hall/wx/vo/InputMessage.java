package com.quiet.live.hall.wx.vo;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import lombok.Data;

@XStreamAlias("xml")
@Data
public class InputMessage implements Serializable{

	/** 
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = 1104064261740264221L;
	
	 	@XStreamAlias("ToUserName")  
	    private String ToUserName;  
	    @XStreamAlias("FromUserName")  
	    private String FromUserName;  
	    @XStreamAlias("CreateTime")  
	    private Long CreateTime;  
	    @XStreamAlias("MsgType")  
	    private String MsgType = "text";  
	    
	    @XStreamAlias("MsgId")  
	    private Long MsgId;  
	    // 文本消息  
	    @XStreamAlias("Content")  
	    private String Content;  
	    // 图片消息  
	    @XStreamAlias("PicUrl")  
	    private String PicUrl;  
	    // 位置消息  
	    @XStreamAlias("LocationX")  
	    private String LocationX;  
	    @XStreamAlias("LocationY")  
	    private String LocationY;  
	    @XStreamAlias("Scale")  
	    private Long Scale;  
	    @XStreamAlias("Label")  
	    private String Label;  
	    // 链接消息  
	    @XStreamAlias("Title")  
	    private String Title;  
	    @XStreamAlias("Description")  
	    private String Description;  
	    @XStreamAlias("Url")  
	    private String URL;  
	    // 语音信息  
	    @XStreamAlias("MediaId")  
	    private String MediaId;  
	    @XStreamAlias("Format")  
	    private String Format;  
	    @XStreamAlias("Recognition")  
	    private String Recognition;  
	    // 事件  
	    @XStreamAlias("Event")  
	    private String Event;  
	    @XStreamAlias("EventKey")  
	    private String EventKey;  
	    @XStreamAlias("Ticket")  
	    private String Ticket;
	    
	    @XStreamAlias("MusicUrl")  
	    private String MusicUrl;
	    @XStreamAlias("HQMusicUrl")  
	    private String HQMusicUrl;
	    @XStreamAlias("ThumbMediaId")  
	    private String ThumbMediaId;
	    
	    @XStreamAlias("KfAccount")
	    private String KfAccount;
	    
	    @XStreamAlias("ArticleCount") 
	    private int ArticleCount;
	    
	    @XStreamAlias("MenuId") 
	    private String MenuId;
	    
		
	    

}
