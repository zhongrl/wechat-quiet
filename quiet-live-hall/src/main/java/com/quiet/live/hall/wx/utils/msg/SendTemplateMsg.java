package com.quiet.live.hall.wx.utils.msg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.WxType;
import me.chanjar.weixin.common.error.WxError;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;

@Component
@Slf4j
public class SendTemplateMsg {

	private static final JsonParser JSON_PARSER = new JsonParser();

	@Autowired
	WxMpService wxMpService;

	public String sendTemplateMsg(TemplateMessageVo templateMessageVo) throws WxErrorException {
		String url = "https://api.weixin.qq.com/cgi-bin/message/template/send";
		String responseContent = this.wxMpService.post(url, templateMessageVo.toJson());
		log.info(responseContent);
		final JsonObject jsonObject = JSON_PARSER.parse(responseContent).getAsJsonObject();
		if (jsonObject.get("errcode").getAsInt() == 0) {
			return jsonObject.get("msgid").getAsString();
		}
		throw new WxErrorException(WxError.fromJson(responseContent, WxType.MP));
	}

}
