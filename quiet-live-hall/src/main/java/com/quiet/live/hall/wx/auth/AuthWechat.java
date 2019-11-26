package com.quiet.live.hall.wx.auth;

import com.quiet.live.hall.wx.ParentWecht;

import me.chanjar.weixin.mp.api.WxMpService;

public abstract class AuthWechat extends ParentWecht{
	
	  protected WxMpService wxMpService;

	  public AuthWechat(WxMpService wxMpService) {
	    this.wxMpService = wxMpService;
	  }

	
}
