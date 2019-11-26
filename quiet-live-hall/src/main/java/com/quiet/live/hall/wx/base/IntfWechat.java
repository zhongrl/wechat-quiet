package com.quiet.live.hall.wx.base;

import com.quiet.live.hall.utils.web.WebVo;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

public interface IntfWechat {
	
	
	public WxMpUser  wxUser(WxMpOAuth2AccessToken wxMpOAuth2AccessToken);
	
	public  WebVo<String> menu(String json);
	
	public  void delmenu()throws WxErrorException;
	
	public  void createmenu(String json)throws WxErrorException;
}
