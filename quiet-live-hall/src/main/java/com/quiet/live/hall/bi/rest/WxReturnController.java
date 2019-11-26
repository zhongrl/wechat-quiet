package com.quiet.live.hall.bi.rest;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quiet.live.hall.wx.utils.WechatUtils;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;

@Api(value="BI微信回调接口",tags={"BI微信回调接口"})
@RestController
@RequestMapping("/wx")
@Slf4j
@CrossOrigin
public class WxReturnController {
	
	@Autowired
	WechatUtils wechatUtils;
	
	@Autowired
	WxMpService wxMpService;
	
	/**
	 * @throws Exception  
	* @Title: wxReretrun 
	* @Description: 微信扫码事件回调接口
	* @param    
	* @return void   
	* @throws 
	*/
	@RequestMapping(value="/return")
	public void wxReretrun(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		log.info("微信回调开始");
		boolean isGet = request.getMethod().toLowerCase().equals("get");  
		response.setCharacterEncoding("UTF-8");
		log.info("微信回调开始" + isGet);
		if(isGet){
			
			String echostr = request.getParameter("echostr");
			log.info("微信token ： " + echostr);
			String signature = request.getParameter("signature");
			if(StringUtils.isEmpty(signature)){
				return ;
			}
			String timestamp = request.getParameter("timestamp");
			String nonce = request.getParameter("nonce");
			if(StringUtils.isNotEmpty(echostr) && wxMpService.checkSignature(timestamp, nonce, signature)){// 接口签名
				log.info("数据源为微信后台，将echostr[{}]返回！", echostr);
				response.getOutputStream().print(echostr);
			}
		}else{
			try {
				wechatUtils.acceptMessage(request, response); //处理微信事件
			} catch (IOException e) {
				log.warn("接收事件失败！");
			}
		}
	} 
}
