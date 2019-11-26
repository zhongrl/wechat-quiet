package com.quiet.live.hall.wx.service;

import com.quiet.live.hall.wx.auth.AuthWechat;

import me.chanjar.weixin.mp.api.WxMpService;

public class WechatService extends AuthWechat{

	public WechatService(WxMpService wxMpService) {
		super(wxMpService);
	}
}
