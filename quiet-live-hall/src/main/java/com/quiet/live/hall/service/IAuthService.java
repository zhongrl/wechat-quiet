package com.quiet.live.hall.service;

import java.util.Map;

import com.quiet.live.hall.utils.web.WebVo;
import com.quiet.live.hall.vo.Auth;
import com.quiet.live.hall.vo.WxtempVo;

import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;

public interface IAuthService {

	public  WebVo<String> auth2(Auth auth);
	
	public  WxMpOAuth2AccessToken auth3(Auth auth);
	
	public WebVo<String> bind(Auth auth);
	
	public WebVo<String> menu(String json);
	
	public void sendWxMsg(Map<String,String> map);
	
	public void sendWxMsg(WxtempVo wxtempVo);
}
