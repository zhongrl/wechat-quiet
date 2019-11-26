package com.quiet.live.hall.bi.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.quiet.live.hall.service.IAuthService;
import com.quiet.live.hall.utils.web.WebVo;
import com.quiet.live.hall.vo.Auth;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;

@Api(value="BI微信菜单接口",tags={"BI用户授权"},description = "BI微信菜单接口")
@RestController
@RequestMapping("/wx")
@Slf4j
@CrossOrigin
public class MenuController {


	@Autowired
	IAuthService iAuthService;
	
	@Autowired
	WxMpService wxMpService;
	
	@RequestMapping(value="/menu")
	public WebVo<String> auth(@RequestBody Auth auth){
		log.info("微信入参"  + JSON.toJSONString(auth));
		if(auth == null || StringUtils.isEmpty(auth.getMenu())){
			return WebVo.error("参数不能为空");
		}
		return iAuthService.menu(auth.getMenu());
	} 
	
	@RequestMapping(value="/getUserInfo")
	public WebVo<String> getUserInfo(@RequestBody Auth auth){
		WxMpOAuth2AccessToken wx = new WxMpOAuth2AccessToken();
		wx.setOpenId(auth.getOpenId());
		try {
			wx.setAccessToken(auth.getToken());
			return  WebVo.success(JSON.toJSONString(wxMpService.oauth2getUserInfo(wx, "zh_CN")));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("微信获取失败",e);
		}
		return WebVo.error("获取微信失败");
	} 
	
	@RequestMapping(value="/getToken")
	public WebVo<String> getToken(@RequestBody Auth auth){
		try {
			return WebVo.success(wxMpService.getAccessToken(true));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("微信获取失败",e);
		}
		return WebVo.error("获取微信失败");
	} 
	
}
