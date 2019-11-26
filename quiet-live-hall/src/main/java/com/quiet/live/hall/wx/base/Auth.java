package com.quiet.live.hall.wx.base;

import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

public interface Auth {

	public abstract WxMpOAuth2AccessToken auth(String code);
	
	public WxMpUser wxUser(WxMpOAuth2AccessToken wxMpOAuth2AccessToken);

	WxMpOAuth2AccessToken auth3(String code); 
	
}
