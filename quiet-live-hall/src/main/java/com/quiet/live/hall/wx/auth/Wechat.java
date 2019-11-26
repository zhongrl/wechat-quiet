package com.quiet.live.hall.wx.auth;

import com.alibaba.fastjson.JSON;
import com.quiet.live.hall.utils.web.WebVo;
import com.quiet.live.hall.wx.ParentWecht;
import com.quiet.live.hall.wx.base.Auth;
import com.quiet.live.hall.wx.base.IntfWechat;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

@Slf4j
public abstract class Wechat extends ParentWecht implements IntfWechat ,Auth{

	
	@Override
	public WxMpUser wxUser(WxMpOAuth2AccessToken wxMpOAuth2AccessToken) {
		try {
			return this.wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken, "zh_CN");
		} catch (WxErrorException e) {
			// TODO Auto-generated catch block
			log.error("系统异常", e);
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public WxMpOAuth2AccessToken auth(String code)  {
		try {
			WxMpOAuth2AccessToken wx = this.wxMpService.oauth2getAccessToken(code);
			log.info("微信授权返回的对象：" + JSON.toJSONString(wx));
			return wx;
		} catch (WxErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("系统异常", e);
		}
		return null;
	}
	
	@Override
	public WxMpOAuth2AccessToken auth3(String code)  {
		try {
			return this.wxMpService.oauth2getAccessToken(code);
		} catch (WxErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("系统异常", e);
		}
		return null;
	}
	
	@Override
	public WebVo<String> menu(String json) {
		try {
			this.delmenu();
			this.createmenu(json);
			return WebVo.success();
		} catch (WxErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("创建微信菜单失败",e);
		}
		return WebVo.error("创建微信菜单失败");
	}
	
	@Override
	public  void delmenu() throws WxErrorException{
		this.wxMpService.getMenuService().menuDelete();
	}
	
	@Override
	public  void createmenu(String json)throws WxErrorException{
		String result = this.wxMpService.getMenuService().menuCreate(json);
		log.info("微信菜单创建返回结果" + result);
	}

}
