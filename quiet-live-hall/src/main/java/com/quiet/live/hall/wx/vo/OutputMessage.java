package com.quiet.live.hall.wx.vo;

import com.quiet.live.hall.wx.base.XStreamCDATA;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import lombok.Data;

/** 
 *  
 * @author morning 
 * @date 2015年2月16日 下午2:29:32 
 */  
@XStreamAlias("xml")  
@Data
public class OutputMessage {  
  
    @XStreamAlias("ToUserName")  
    @XStreamCDATA  
    private String ToUserName;  
  
    @XStreamAlias("FromUserName")  
    @XStreamCDATA  
    private String FromUserName;  
  
    @XStreamAlias("CreateTime")  
    private Long CreateTime;  
  
    @XStreamAlias("MsgType")  
    @XStreamCDATA  
    private String MsgType = "text";  
  
    
    @XStreamAlias("ArticleCount")
    private int ArticleCount;
    
    @XStreamAlias("KfAccount")
    private String KfAccount;
    

	
    
}