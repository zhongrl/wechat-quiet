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

@Api(value="微信绑定接口",tags={"微信绑定接口"},description = "微信绑定接口")
@RestController
@RequestMapping("/wx")
@Slf4j
@CrossOrigin
public class BindController {

	@Autowired
	IAuthService iAuthService;
	
	@RequestMapping(value="/bind")
	public com.quiet.live.hall.utils.web.WebVo<String> bind(@RequestBody Auth auth){
		log.info("绑定入参"  + JSON.toJSONString(auth));
		if(null == auth){
			return WebVo.error("参数不能为空");
		}
		if(StringUtils.isEmpty(auth.getAccount())){
			return WebVo.error("账号参数不能为空");
		}
		if(StringUtils.isEmpty(auth.getPassword())){
			return WebVo.error("密码参数不能为空");
		}
		if(StringUtils.isEmpty(auth.getOpenId())){
			return WebVo.error("OpenId参数不能为空");
		}
		return iAuthService.bind(auth);
	} 
}
