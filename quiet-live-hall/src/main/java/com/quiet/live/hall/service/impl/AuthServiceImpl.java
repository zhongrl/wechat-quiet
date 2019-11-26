package com.quiet.live.hall.service.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.quiet.live.hall.config.RedisComponent;
import com.quiet.live.hall.constants.Constant;
import com.quiet.live.hall.constants.SysConstant;
import com.quiet.live.hall.entity.JEmailMsg;
import com.quiet.live.hall.entity.JUser;
import com.quiet.live.hall.entity.JWxtempMsg;
import com.quiet.live.hall.service.IAuthService;
import com.quiet.live.hall.service.IJEmailMsgService;
import com.quiet.live.hall.service.IJWxtempMsgService;
import com.quiet.live.hall.service.JUserService;
import com.quiet.live.hall.utils.PropUtil;
import com.quiet.live.hall.utils.base.MD5Util;
import com.quiet.live.hall.utils.string.StringUtil;
import com.quiet.live.hall.utils.web.WebVo;
import com.quiet.live.hall.vo.Auth;
import com.quiet.live.hall.vo.WxtempVo;
import com.quiet.live.hall.wx.auth.Wechat;
import com.quiet.live.hall.wx.utils.msg.SendTemplateMsg;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage.MiniProgram;

@Service
@Slf4j
public class AuthServiceImpl extends Wechat implements IAuthService {

	@Autowired
	JUserService jUserService;

	@Autowired
	private WxMpService wxMpService;

	@Autowired
	RedisComponent redisComponent;

	@Autowired
	IJEmailMsgService iJEmailMsgService;

	@Autowired
	IJWxtempMsgService iJWxtempMsgService;
	
	@Autowired
	SendTemplateMsg sendTemplateMsg;
	
	@Value("${wx.minapp.yw.appid}")
	private String appid;

	@Override
	public WebVo<String> auth2(Auth auth) {
		WxMpOAuth2AccessToken wx = this.auth(auth.getCode());
		if(null == wx){
			return WebVo.error("获取openId失败");
		}
		log.info(JSON.toJSONString(wx));
		if(StringUtils.isNotEmpty(wx.getUnionId())){
			redisComponent.set("accessTokenunId-"+wx.getOpenId(),wx.getUnionId());
		}
		redisComponent.set("accessToken-"+wx.getOpenId(), wx.getAccessToken());
		JUser parm = new JUser();
		parm.setOpenId(wx.getOpenId());
		JUser user = jUserService.selectOne(new EntityWrapper<JUser>(parm));
		if (null != user) {
			return WebVo.success(user.getOpenId());
		}
		return WebVo.success((null != wx ? wx.getOpenId() : null));
	}

	@Override
	public WebVo<String> bind(Auth auth) {
		JUser parm = new JUser();
		parm.setAccount(auth.getAccount());
		JUser user = jUserService.selectOne(new EntityWrapper<JUser>(parm));
		log.info(JSON.toJSONString(user));
		if (null == user) {
			return WebVo.result(SysConstant.ErrorCode.LOGIN_ACCOUNT_PASSWORD_ERROR, "账号或密码错误");
		}
		if (StringUtils.isNotEmpty(user.getOpenId())) {
			return WebVo.error("已经绑定微信！");
		}

		if (!Constant.BASE_STATUS.DISABLE.equals(user.getDelFlag())) {
			return WebVo.result(SysConstant.ErrorCode.LOGIN_ACCOUNT_DISABLE, "账号已禁用");
		}
		if (StringUtils.isEmpty(user.getPassword())) {
			return WebVo.result(SysConstant.ErrorCode.LOGIN_ACCOUNT_PASSWORD_ERROR, "账号或密码错误");
		}
		if (!user.getPassword().equals(MD5Util.md5(auth.getPassword()))) {
			return WebVo.result(SysConstant.ErrorCode.LOGIN_ACCOUNT_PASSWORD_ERROR, "账号或密码错误");
		}
		if (StringUtils.isNotEmpty(user.getOpenId())) {
			return WebVo.error("该账户已经绑定！");
		}
		user.setOpenId(auth.getOpenId());
		jUserService.updateById(user);
		return WebVo.success();
	}

	@Override
	public WebVo<String> menu(String json) {
		return super.menu(json);
	}

	@Override
	public WxMpOAuth2AccessToken auth3(Auth auth) {
		try {
			return this.wxMpService.oauth2getAccessToken(auth.getCode());
		} catch (WxErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("系统异常", e);
		}
		return null;
	}

	@Override
	public void sendWxMsg(Map<String, String> map) {

		WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder().toUser(map.get("openId"))
				.templateId(redisComponent.get(map.get("title"))).url(map.get("url")).build();
		List<WxMpTemplateData> data = Arrays.asList(new WxMpTemplateData("first", map.get("title")),
				new WxMpTemplateData("keyword1", map.get("keyword1")),
				new WxMpTemplateData("keyword2", map.get("keyword2")),
				new WxMpTemplateData("keyword3", map.get("keyword3")),
				new WxMpTemplateData("keyword4", map.get("keyword4")),
				new WxMpTemplateData("keyword5", "￥" + map.get("keyword5")),
				new WxMpTemplateData("remark", map.get("remark")));
		templateMessage.setData(data);
		try {
			wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
		} catch (WxErrorException e) {
			JEmailMsg jEmailMsg = new JEmailMsg();
			jEmailMsg.setOpenId(map.get("openId"));
			jEmailMsg.setContent(JSON.toJSONString(templateMessage));
			jEmailMsg.setCreateTime(new Date());
			jEmailMsg.setId(StringUtil.getUUID());
			jEmailMsg.setSendStatus(0);
			iJEmailMsgService.insert(jEmailMsg);
			log.error("【微信模版消息】发送失败, {}", e);
		}
	}
	
	@Override
	public void sendWxMsg(WxtempVo wxtempVo) {
		log.info("推送消息参数： " + JSON.toJSONString(wxtempVo));
		if (null == wxtempVo || StringUtils.isEmpty(wxtempVo.getMsgType())) {
			log.error("参数消息模板不存在。。。。。。。。。。。。。。。。。。。。");
			return;
		}
		if (StringUtils.isEmpty(wxtempVo.getOpenId())) {
			log.error("接收消息用户不能为空。。。。。。。。。。。。。。。。。。。。");
			return;
		}

		JWxtempMsg jWxtempMsg = new JWxtempMsg();
		jWxtempMsg.setMsgType(wxtempVo.getMsgType());
		JWxtempMsg sysMsg = iJWxtempMsgService.selectOne(new EntityWrapper<JWxtempMsg>(jWxtempMsg));
		log.info("模板消息模板： " + JSON.toJSONString(sysMsg));
		if (null == sysMsg) {
			log.error("消息模板不存在。。。。。。。。。。。。。。。。。。。。");
			return;
		}
		WxMpTemplateMessage templateMessage =  new WxMpTemplateMessage();
//		WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder().toUser(wxtempVo.getOpenId())
//				.templateId(sysMsg.getId())
//				.url(StringUtils.isNotEmpty(wxtempVo.getUrl())?wxtempVo.getUrl():sysMsg.getUrl()).build();
		templateMessage.setToUser(wxtempVo.getOpenId());
		templateMessage.setTemplateId(sysMsg.getId());
		if(StringUtils.isNotEmpty(sysMsg.getUrl())){
			if(sysMsg.getUrl().contains("token")){
				JUser ju = jUserService.selectById(wxtempVo.getUserId());
				String token = "pay_" + ju.getOpenId();
				redisComponent.setEx(token, ju, PropUtil.getInt("token.timeout", 2592000));
				templateMessage.setUrl(MessageFormat.format(sysMsg.getUrl(), token));
			}else{
				MiniProgram miniProgram = new MiniProgram();
				miniProgram.setAppid(appid);
				miniProgram.setPagePath(sysMsg.getUrl());
				// TODO 线上要改成false
				miniProgram.setUsePath(true);
				templateMessage.setMiniProgram(miniProgram);
			}
		}
		
		List<WxMpTemplateData> data = new ArrayList<>();
		 
		if(wxtempVo.getMsgType().equals(Constant.WX_TEMP_MSG.ZF_SUCCESS_TZ)
				|| wxtempVo.getMsgType().equals(Constant.WX_TEMP_MSG.DAI_ZF_TX)
				||wxtempVo.getMsgType().equals(Constant.WX_TEMP_MSG.ZF_TX)){
			log.info("支付的tital");
			data.add(new WxMpTemplateData(Constant.WX_TEMP_FLEAD.FIRST, MessageFormat.format(sysMsg.getTitle(), wxtempVo.getUserName()),"#000000"));
		}else{
			log.info("排队叫号的tital : " + sysMsg.getTitle());
			if(wxtempVo.getMsgType().equals(Constant.WX_TEMP_MSG.JH_TX) || wxtempVo.getMsgType().equals(Constant.WX_TEMP_MSG.PD_SUCCESS_TX)){
				if(StringUtils.isNotEmpty(wxtempVo.getTitle())){
					data.add(new WxMpTemplateData(Constant.WX_TEMP_FLEAD.FIRST, wxtempVo.getTitle(),"#000000"));
				}else{
					data.add(new WxMpTemplateData(Constant.WX_TEMP_FLEAD.FIRST, sysMsg.getTitle(),"#000000"));
				}
			}else{
				data.add(new WxMpTemplateData(Constant.WX_TEMP_FLEAD.FIRST, sysMsg.getTitle(),"#000000"));
			}
			
		}
		
		if (!StringUtil.isEmpty(wxtempVo.getKeyword1())) {
			if(wxtempVo.getMsgType().equals(Constant.WX_TEMP_MSG.ZF_SUCCESS_TZ)){
				data.add(new WxMpTemplateData(Constant.WX_TEMP_FLEAD.KEYWORD1, wxtempVo.getKeyword1(),"#000000"));
			}else{
				data.add(new WxMpTemplateData(Constant.WX_TEMP_FLEAD.KEYWORD1, wxtempVo.getKeyword1(),"#000000"));
			}
			
		}
		if (!StringUtil.isEmpty(wxtempVo.getKeyword2())) {
			data.add(new WxMpTemplateData(Constant.WX_TEMP_FLEAD.KEYWORD2, wxtempVo.getKeyword2(),"#000000"));
		}
		if (!StringUtil.isEmpty(wxtempVo.getKeyword3())) {
			data.add(new WxMpTemplateData(Constant.WX_TEMP_FLEAD.KEYWORD3, wxtempVo.getKeyword3(),"#000000"));
		}
		if (!StringUtil.isEmpty(wxtempVo.getKeyword4())) {
			data.add(new WxMpTemplateData(Constant.WX_TEMP_FLEAD.KEYWORD4, wxtempVo.getKeyword4(),"#000000"));
		}
		if (!StringUtil.isEmpty(wxtempVo.getKeyword5())) {
			data.add(new WxMpTemplateData(Constant.WX_TEMP_FLEAD.KEYWORD5, wxtempVo.getKeyword5(),"#000000"));
		}
		if(StringUtils.isNotEmpty(wxtempVo.getRemark())){
			data.add(new WxMpTemplateData(Constant.WX_TEMP_FLEAD.REMARK, wxtempVo.getRemark(),"#000000"));
		}else{
			data.add(new WxMpTemplateData(Constant.WX_TEMP_FLEAD.REMARK, sysMsg.getRemark(),"#000000"));
		}
		
		templateMessage.setData(data);
		try {
			log.info("发送前的消息： " + JSON.toJSONString(templateMessage));
			// 微信的
			wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
			JEmailMsg jEmailMsg = new JEmailMsg();
			jEmailMsg.setOpenId(wxtempVo.getOpenId());
			jEmailMsg.setContent(JSON.toJSONString(wxtempVo));
			jEmailMsg.setCreateTime(new Date());
			jEmailMsg.setId(StringUtil.getUUID());
			jEmailMsg.setSendStatus(1);
			jEmailMsg.setMsgType(wxtempVo.getMsgType());
			iJEmailMsgService.insert(jEmailMsg);
		} catch (Exception e) {
			if (StringUtils.isEmpty(wxtempVo.getMsgId())) {
				JEmailMsg jEmailMsg = new JEmailMsg();
				jEmailMsg.setOpenId(wxtempVo.getOpenId());
				jEmailMsg.setContent(JSON.toJSONString(wxtempVo));
				jEmailMsg.setCreateTime(new Date());
				jEmailMsg.setId(StringUtil.getUUID());
				jEmailMsg.setSendStatus(0);
				jEmailMsg.setMsgType(wxtempVo.getMsgType());
				iJEmailMsgService.insert(jEmailMsg);
			}
			log.error("【微信模版消息】发送失败, {}", e);
		}

	}

}
