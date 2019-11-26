package com.quiet.live.hall.custom.rest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.quiet.live.hall.config.ContextUtil;
import com.quiet.live.hall.config.WxMaConfiguration;
import com.quiet.live.hall.entity.JUser;
import com.quiet.live.hall.service.JUserService;
import com.quiet.live.hall.utils.WxJsonUtils;
import com.quiet.live.hall.utils.web.WebVo;
import com.quiet.live.hall.vo.CustomerVo;
import com.quiet.live.hall.vo.MinAppVo;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;

/**
 * @author zhongrl
 *
 */
@Api(value="小程序客户端定接口",tags={"小程序客户端定接口"})
@RestController
@RequestMapping("/minapp")
@Slf4j
@CrossOrigin
public class MinAppController {

	
	@Value("${wx.minapp.yw.appid}")
	private String appid;
	
	@Autowired
	JUserService jUserService;
	/**
     * 登陆接口
     * @author zhongrl 
     */
    @RequestMapping("/login")
    public String login(@PathVariable String appid, String code) {
        if (StringUtils.isBlank(code)) {
            return "empty jscode";
        }

        final WxMaService wxService = WxMaConfiguration.getMaService(appid);
        try {
            WxMaJscode2SessionResult session = wxService.getUserService().getSessionInfo(code);
            log.info(session.getSessionKey());
            log.info(session.getOpenid());
            //TODO 可以增加自己的逻辑，关联业务相关数据
            return WxJsonUtils.toJson(session);
        } catch (WxErrorException e) {
            log.error(e.getMessage(), e);
            return e.toString();
        }
    }

    /**
     * <pre>
     * 获取用户信息接口
     * @author zhongrl
     * </pre>
     */
    @RequestMapping("/info")
    public String info(@PathVariable String appid, String sessionKey,
                       String signature, String rawData, String encryptedData, String iv) {
        final WxMaService wxService = WxMaConfiguration.getMaService(appid);

        // 用户信息校验
        if (!wxService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            return "user check failed";
        }

        // 解密用户信息
        WxMaUserInfo userInfo = wxService.getUserService().getUserInfo(sessionKey, encryptedData, iv);

        return WxJsonUtils.toJson(userInfo);
    }

    /**
     * <pre>
     * 获取用户绑定手机号信息
     * ao
     * </pre>
     */
    @RequestMapping("/phone")
    public WebVo<CustomerVo> phone(@RequestBody MinAppVo minAppVo) {
    	
        final WxMaService wxService = WxMaConfiguration.getMaService(appid);

        // 用户信息校验
//        if (!wxService.getUserService().checkUserInfo(minAppVo.getSessionKey(), minAppVo.getRawData(), minAppVo.getSignature())) {
//            return WebVo.error("user check failed");
//        }
		WxMaJscode2SessionResult session = null ;
		try {
			session = wxService.getUserService().getSessionInfo(minAppVo.getCode());
			log.info(session.getSessionKey());
			log.info(session.getOpenid());
		} catch (WxErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return WebVo.error("微信获取失败， 获取手机号请查看是否域名有所更改");
		}
		CustomerVo customerVo = new CustomerVo();
        // 解密
        WxMaPhoneNumberInfo phoneNoInfo = wxService.getUserService().getPhoneNoInfo(session.getSessionKey(), minAppVo.getEncryptedData(), minAppVo.getIv());
        log.info(JSON.toJSONString(phoneNoInfo));
        JUser jUser = new JUser();
        jUser.setMobile(phoneNoInfo.getPhoneNumber());
        jUser.setId(ContextUtil.getUserId());
		jUserService.updateById(jUser);
		customerVo.setMobile(false);
		customerVo.setToken(ContextUtil.getToken());
        return WebVo.success(customerVo);
    }
}
