package com.quiet.live.hall.custom.rest;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.quiet.live.hall.config.ContextUtil;
import com.quiet.live.hall.config.RedisComponent;
import com.quiet.live.hall.config.WxMaConfiguration;
import com.quiet.live.hall.constants.Constant;
import com.quiet.live.hall.entity.JBanner;
import com.quiet.live.hall.entity.JCoupon;
import com.quiet.live.hall.entity.JCouponDetail;
import com.quiet.live.hall.entity.JEvaluate;
import com.quiet.live.hall.entity.JOrder;
import com.quiet.live.hall.entity.JStore;
import com.quiet.live.hall.entity.JUser;
import com.quiet.live.hall.service.IAuthService;
import com.quiet.live.hall.service.IJWxtempMsgService;
import com.quiet.live.hall.service.JBannerService;
import com.quiet.live.hall.service.JCouponDetailService;
import com.quiet.live.hall.service.JCouponService;
import com.quiet.live.hall.service.JEvaluateService;
import com.quiet.live.hall.service.JOrderService;
import com.quiet.live.hall.service.JStoreService;
import com.quiet.live.hall.service.JUserService;
import com.quiet.live.hall.utils.DateUtils;
import com.quiet.live.hall.utils.PropUtil;
import com.quiet.live.hall.utils.string.StringUtil;
import com.quiet.live.hall.utils.web.WebVo;
import com.quiet.live.hall.vo.Auth;
import com.quiet.live.hall.vo.CustomerVo;
import com.quiet.live.hall.vo.CutomJOrderVo;
import com.quiet.live.hall.vo.JEvaluateVo;
import com.quiet.live.hall.vo.JUserVo;
import com.quiet.live.hall.vo.JsAvgNum;
import com.quiet.live.hall.vo.ShareVo;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

@Api(value = "小程序客户端定接口", tags = { "小程序客户端定接口" })
@RestController
@RequestMapping("/custom")
@Slf4j
@CrossOrigin
public class CustomController {

	@Autowired
	JCouponService jCouponService;

	@Autowired
	JBannerService jBannerService;

	@Autowired
	JCouponDetailService jCouponDetailService;

	@Autowired
	JStoreService jStoreService;

	@Autowired
	IAuthService iAuthService;

	@Autowired
	JUserService jUserService;

	@Autowired
	RedisComponent redisComponent;

	@Autowired
	public WxMpService wxMpService;

	@Autowired
	JEvaluateService jEvaluateService;

	@Autowired
	IJWxtempMsgService iJWxtempMsgService;

	@Autowired
	JOrderService jOrderService;

	@Value("${wx.minapp.yw.appid}")
	private String appid;

	@ApiOperation(value = "小程序所有Banner 广告 ", httpMethod = "POST", produces = "application/json")
	@RequestMapping("/updateUserMobile")
	public WebVo<String> updateUserMobile(@RequestBody JUser jUser) {
		if (null == jUser) {
			return WebVo.error("参数不能为空");
		}
		if (StringUtils.isEmpty(jUser.getMobile())) {
			return WebVo.error("手机号参数不能为空");
		}
		jUser.setId(ContextUtil.getUserId());
		jUserService.updateById(jUser);
		return WebVo.success();
	}

	@ApiOperation(value = "小程序所有Banner 广告 ", httpMethod = "POST", produces = "application/json")
	@RequestMapping("/findXcxBanner")
	public WebVo<Page<JBanner>> findXcxBanner(@RequestBody JBanner jBanner) {
		jBanner = new JBanner();
		Page<JBanner> page = new Page<JBanner>(1, 3);
		jBanner.setDelFlag("1");

		return WebVo.success(jBannerService.selectPage(page,
				new EntityWrapper<JBanner>(jBanner).orderDesc(Collections.singleton("create_time "))));
	}

	@ApiOperation(value = "小程序所有优惠券  ", httpMethod = "POST", produces = "application/json")
	@RequestMapping("/findXcxJCoupon")
	public WebVo<Page<JCoupon>> findXcxJCoupon(@RequestBody JCoupon jCoupon) {
		jCoupon = new JCoupon();
		Page<JCoupon> page = new Page<JCoupon>(1, 3);
		return WebVo
				.success(jCouponService.selectPage(page, new EntityWrapper<JCoupon>(jCoupon).and(" start_time  < NOW()")
						.and(" end_time  > NOW()").orderDesc(Collections.singleton("create_time "))));
	}

	@ApiOperation(value = "小程序查询用户优惠券  ", httpMethod = "POST", produces = "application/json")
	@RequestMapping("/findUserJCouponDetail")
	public WebVo<List<JCouponDetail>> findUserJCouponDetail(@RequestBody JCouponDetail jCouponDetail) {
		if (null == jCouponDetail) {
			return WebVo.error("参数不能为空");
		}
		jCouponDetail.setSyOpenid(ContextUtil.getOpenId());
		List<JCouponDetail> list = jCouponDetailService
				.selectList(new EntityWrapper<JCouponDetail>(jCouponDetail).and(" start_time  < NOW()")
						.and(" end_time  > NOW()").orderDesc(Collections.singleton("create_time ")));
		return WebVo.success(list);
	}

	@ApiOperation(value = "小程序查询用户优惠券  ", httpMethod = "POST", produces = "application/json")
	@RequestMapping("/selectUserDetail")
	public WebVo<JUser> selectUserDetail(@RequestBody JUser JUser) {

		JUser juser = jUserService.selectById(ContextUtil.getUserId());
		JCoupon jCoupon = new JCoupon();
		// 拉新永远只配置一条
		jCoupon.setCouponType("LAXIN");
		// 拉新活动
		JCoupon jc = jCouponService.selectOne(new EntityWrapper<>(jCoupon));
		juser.setCouponId(jc.getId());
		return WebVo.success(juser);
	}

	@ApiOperation(value = "小程序查询用户分享赠送优惠券  ", httpMethod = "POST", produces = "application/json")
	@RequestMapping("/shareUserJCouponDetail")
	public WebVo<String> shareUserJCouponDetail(@RequestBody JCouponDetail jCouponDetail) throws Exception {
		log.info("参数：" + JSON.toJSONString(jCouponDetail));
		if (null == jCouponDetail) {
			return WebVo.error("参数不能为空");
		}
		if (StringUtils.isEmpty(jCouponDetail.getCode())) {
			return WebVo.error("code不能为空");
		}
		if (StringUtils.isEmpty(jCouponDetail.getUnionid())) {
			return WebVo.error("分享人unionid参数不能为空");
		}
		if (StringUtils.isEmpty(jCouponDetail.getCouponId())) {
			return WebVo.error("优惠券openID参数不能为空");
		}
		
		
		String openId = null;
		String unionId = null;
		if(StringUtils.isNotEmpty(jCouponDetail.getIsWxWp())){
			if(StringUtils.equals(jCouponDetail.getIsWxWp(), "wxmp")){
				WxMpOAuth2AccessToken wx= null ;
				try {
					 wx = this.wxMpService.oauth2getAccessToken(jCouponDetail.getCode());
				} catch (WxErrorException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return WebVo.error("微信获取用户信息失败");
				}
				openId = wx.getOpenId();
				unionId = 	wx.getUnionId();
				
			}else if(StringUtils.equals(jCouponDetail.getIsWxWp(), "minapp")){
				final WxMaService wxService = WxMaConfiguration.getMaService(appid);
				WxMaJscode2SessionResult session = null;
				try {
					session = wxService.getUserService().getSessionInfo(jCouponDetail.getCode());
				} catch (WxErrorException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				log.info(session.getSessionKey());
				log.info(session.getOpenid());
				openId = session.getOpenid();
				unionId = 	session.getUnionid();
				
			}else{
				log.info("参数不为空， 但是类型不正确 只支持isWxWp = wxmp  minapp");
				return WebVo.error("未知来源！");
			}
			if(StringUtils.equals(unionId, jCouponDetail.getUnionid())){
				 return WebVo.error("自己分享出去的本人不能获取优惠券！");
			}
			int count = jCouponDetailService.selectCount(new EntityWrapper<>(new JCouponDetail()).and("unionId = " + unionId));
			if(count > 0){
				return WebVo.error("您已经领过优惠券了！");
			}
		}else{
			log.info("参数为空，只支持isWxWp = wxmp  minapp");
			return WebVo.error("未知来源！");
		}
		jCouponDetail.setSyOpenid(openId);
		JUser giveUser = new JUser();
		giveUser.setUnionid(jCouponDetail.getUnionid());
		giveUser.setUserType("4");
		JUser wxgiveUser = jUserService.selectOne(new EntityWrapper<>(giveUser));
		if (null == wxgiveUser) {
			log.error(JSON.toJSONString(jCouponDetail) + "------当前分享人不存在系统");
			return WebVo.error("客户不存在");
		}
		jCouponDetail.setGiveUsername(wxgiveUser.getUsername());
		JUser syUser = new JUser();
		if(StringUtils.equals(jCouponDetail.getIsWxWp(), "wxmp")){
			syUser.setOpenId(openId);
		}else{
			syUser.setCustomOpenId(openId);
		}
		syUser.setUserType("4");
		JUser wxSyUser = jUserService.selectOne(new EntityWrapper<>(syUser));
		if (null == wxSyUser) {
			JUser juser = new JUser();
			
			
			juser.setId(StringUtil.getUUID());
			juser.setCreateTime(new Date());
			if(StringUtils.equals(jCouponDetail.getIsWxWp(), "wxmp")){
				WxMpOAuth2AccessToken wx = new WxMpOAuth2AccessToken();
				wx.setOpenId(jCouponDetail.getSyOpenid());
				WxMpUser wxMpUser = null;
				try {
					wxMpUser = wxMpService.oauth2getUserInfo(wx, null);
				} catch (Exception e) {
					e.printStackTrace();
					log.error("微信获取失败");
				}
				juser.setOpenId(openId);
				if (null != wxMpUser) {
//					juser.setUsername(wxMpUser.getNickname());
					juser.setUsername(String.valueOf(org.apache.commons.codec.binary.Base64.encodeBase64String(wxMpUser.getNickname().getBytes("UTF-8"))));
					juser.setHeadImg(wxMpUser.getHeadImgUrl());
					juser.setDelFlag("1");
					juser.setUserType("4");
//					jCouponDetail.setSyUsername(wxMpUser.getNickname());
					jCouponDetail.setSyUsername(String.valueOf(org.apache.commons.codec.binary.Base64.encodeBase64String(wxMpUser.getNickname().getBytes("UTF-8"))));
				}
			}else{
				juser.setCustomOpenId(openId);
			}
			juser.setUnionid(unionId);
			jUserService.insert(juser);
		} else {
			log.error(JSON.toJSONString(jCouponDetail) + "------已经是老用户了，不能进行派送优惠券");
			return WebVo.error("已经是老用户了，不能进行派送优惠券");
		}
		JCoupon jCoupon = jCouponService.selectById(jCouponDetail.getCouponId());
		if (null == jCoupon) {
			return WebVo.error("优惠券不存在");
		}
		// jCouponDetail.setPrice(jCoupon.getPrice());
		jCouponDetail.setTitle(jCoupon.getTitle());
		jCouponDetail.setRemake(jCoupon.getRemake());
		jCouponDetail.setSyOpenid(openId);
		jCouponDetail.setUnionid(unionId);
		jCouponDetail.setPrice(new BigDecimal("20"));
		jCouponDetail.setStartTime(new Date());
		jCouponDetail.setEndTime(jCoupon.getEndTime());
		jCouponDetail.setCreateTime(new Date());
		jCouponDetail.setDelFlag("2");
		jCouponDetail.setId(StringUtil.getUUID());
		jCouponDetail.setUnionid(unionId);;
		jCouponDetailService.insert(jCouponDetail);
		log.error(JSON.toJSONString(jCouponDetail) + "------派送优惠券成功");
		return WebVo.success();
	}

	@ApiOperation(value = "小程序查询门店列表 ", httpMethod = "POST", produces = "application/json")
	@RequestMapping("/findXcxJStore")
	public WebVo<List<JStore>> findXcxJStore(@RequestBody JStore jStore) {
		jStore = new JStore();
		jStore.setDelFlag("1");
		return WebVo.success(jStoreService
				.selectList(new EntityWrapper<JStore>(jStore).orderDesc(Collections.singleton("create_time "))));
	}

	@ApiOperation(value = "查询门店详情 ", httpMethod = "POST", produces = "application/json")
	@RequestMapping("/findXcxJStoreSelectById")
	public WebVo<JStore> findXcxJStoreSelectById(@RequestBody JStore jStore) {
		return WebVo.success(jStoreService.selectById(jStore.getId()));
	}

	@ApiOperation(value = "小程序客户端端登陆 ", httpMethod = "POST", produces = "application/json")
	@RequestMapping(value = "/login")
	public WebVo<CustomerVo> login(@RequestBody JUserVo jUserVo) throws Exception {
		log.info("微信入参" + JSON.toJSONString(jUserVo));
		if (null == jUserVo) {
			return WebVo.error("参数不能为空");
		}
		if (StringUtils.isEmpty(jUserVo.getCode())) {
			return WebVo.error("code不能为空");
		}
		CustomerVo customerVo = new CustomerVo();
		String token = null;
		if (StringUtils.isNoneEmpty(jUserVo.getIsPay()) && StringUtils.equals(jUserVo.getIsPay(), "1")) {
			Auth auth = new Auth();
			auth.setCode(jUserVo.getCode());
			WebVo<String> web = iAuthService.auth2(auth);
			if (Constant.CommonCode.SUCCESS.equals(web.getData()) && StringUtils.isNotEmpty(web.getData())) {
				JUser parm11 = new JUser();
				String unionid = redisComponent.get("accessTokenunId-" + web.getData());
				if (StringUtils.isNotEmpty(unionid)) {
					parm11.setUnionid(unionid);
				} else {
					parm11.setOpenId(web.getData());
				}
				parm11.setUserType("4");
				JUser user = jUserService.selectOne(new EntityWrapper<JUser>(parm11));
				if (null == user) {
					JUser parm112 = new JUser();
					// 绑定开发者账号
					parm112.setOpenId(web.getData());
					user = jUserService.selectOne(new EntityWrapper<JUser>(parm112));
					if (null == user) {
						return WebVo.error("没有绑定关系， 请联系管理员！");
					}
				}
				user.setIsPay("1");
				token = user.getOpenId();
				redisComponent.setEx(token, user, PropUtil.getInt("token.timeout", 172800));
				customerVo.setToken(token);
				customerVo.setOpenId(user.getOpenId());
				return WebVo.success(customerVo);
			} else {
				log.info(JSON.toJSONString(web));
				return WebVo.error("授权失败");
			}
		}
		try {

			final WxMaService wxService = WxMaConfiguration.getMaService(appid);
			WxMaJscode2SessionResult session = wxService.getUserService().getSessionInfo(jUserVo.getCode());
			log.info(session.getSessionKey());
			log.info(session.getOpenid());
			// 用户信息校验
			// if
			// (!wxService.getUserService().checkUserInfo(session.getSessionKey(),
			// jUserVo.getRawData(), jUserVo.getSignature())) {
			// return WebVo.error("用户传入参数不正确，微信小程序授权失败");
			// }
			if (StringUtils.isNotEmpty(jUserVo.getCode())) {
				JUser parm = new JUser();
				// 绑定开发者账号
				log.info("获取unionId = " + session.getUnionid());
				parm.setUnionid(session.getUnionid());
				parm.setUserType("4");
				JUser user = jUserService.selectOne(new EntityWrapper<JUser>(parm));
				parm.setCustomOpenId(session.getOpenid());
				if (null != user) {
					if (StringUtil.isEmpty(user.getUsername())) {
						if (StringUtils.isEmpty(jUserVo.getEncryptedData())) {
							return WebVo.error("encryptedData 不能为空");
						}
						if (StringUtils.isEmpty(jUserVo.getIv())) {
							return WebVo.error("iv 不能为空");
						}
						WxMaUserInfo wxMaUserInfo = getUserMinappInfo(session.getSessionKey(),
								jUserVo.getEncryptedData(), jUserVo.getIv(), wxService);
						if (null == wxMaUserInfo) {
							return WebVo.error("微信获取失败， wxMaUserInfo请查看是否域名有所更改");
						}
						// 保存用户是TODO
						parm.setId(user.getId());
//						parm.setUsername(wxMaUserInfo.getNickName());
						parm.setUsername(String.valueOf(org.apache.commons.codec.binary.Base64.encodeBase64String(wxMaUserInfo.getNickName().getBytes("UTF-8"))));
						parm.setHeadImg(wxMaUserInfo.getAvatarUrl());
						parm.setUpdateTime(new Date());
						parm.setLoginTime(new Date());
						parm.setGender(wxMaUserInfo.getGender());
						parm.setDelFlag("1");
						jUserService.updateById(parm);

					}
					token = user.getOpenId();
					redisComponent.setEx(token, user, PropUtil.getInt("token.timeout", 172800));
					if (StringUtils.isEmpty(user.getMobile())) {
						customerVo.setMobile(true);
					} else {
						customerVo.setMobile(false);
					}
					customerVo.setToken(token);
					customerVo.setOpenId(user.getOpenId());
					return WebVo.success(customerVo);
				} else {
					if (StringUtils.isEmpty(jUserVo.getEncryptedData())) {
						return WebVo.error("encryptedData 不能为空");
					}
					if (StringUtils.isEmpty(jUserVo.getIv())) {
						return WebVo.error("iv 不能为空");
					}
					log.info(session.getSessionKey() + "," + jUserVo.getEncryptedData() + "," + jUserVo.getIv());
					WxMaUserInfo wxMaUserInfo = getUserMinappInfo(session.getSessionKey(), jUserVo.getEncryptedData(),
							jUserVo.getIv(), wxService);
					if (null == wxMaUserInfo) {
						return WebVo.error("微信获取失败， wxMpUser请查看是否域名有所更改");
					}
					if (StringUtils.isNotEmpty(wxMaUserInfo.getUnionId())) {
						JUser parm11 = new JUser();
						parm11.setUnionid(wxMaUserInfo.getUnionId());
						parm11.setUserType("4");
						JUser users = jUserService.selectOne(new EntityWrapper<JUser>(parm11));
						if (null != users) {
							// 保存用户是TODO
							parm.setId(users.getId());
//							parm.setUsername(wxMaUserInfo.getNickName());
							parm.setUsername(String.valueOf(org.apache.commons.codec.binary.Base64.encodeBase64String(wxMaUserInfo.getNickName().getBytes("UTF-8"))));
							parm.setCustomOpenId(wxMaUserInfo.getOpenId());
							parm.setHeadImg(wxMaUserInfo.getAvatarUrl());
							parm.setCreateTime(new Date());
							parm.setLoginTime(new Date());
							parm.setGender(wxMaUserInfo.getGender());
							parm.setDelFlag("1");
							jUserService.updateById(parm);
							token = parm.getOpenId();
							redisComponent.setEx(token, parm, PropUtil.getInt("token.timeout", 172800));
							if (StringUtils.isEmpty(parm.getMobile())) {
								customerVo.setMobile(true);
							} else {
								customerVo.setMobile(false);
							}
							customerVo.setToken(token);
							customerVo.setOpenId(wxMaUserInfo.getOpenId());
							return WebVo.success(customerVo);
						}
					}
					// 保存用户是TODO
					parm.setId(StringUtil.getUUID());
//					parm.setUsername(wxMaUserInfo.getNickName());
					parm.setUsername(String.valueOf(org.apache.commons.codec.binary.Base64.encodeBase64String(wxMaUserInfo.getNickName().getBytes("UTF-8"))));
					parm.setCustomOpenId(wxMaUserInfo.getOpenId());
					parm.setHeadImg(wxMaUserInfo.getAvatarUrl());
					parm.setCreateTime(new Date());
					parm.setLoginTime(new Date());
					parm.setGender(wxMaUserInfo.getGender());
					parm.setDelFlag("1");
					jUserService.insert(parm);
					token = parm.getOpenId();
					redisComponent.setEx(token, parm, PropUtil.getInt("token.timeout", 172800));
					if (StringUtils.isEmpty(parm.getMobile())) {
						customerVo.setMobile(true);
					} else {
						customerVo.setMobile(false);
					}
					customerVo.setToken(token);
					customerVo.setOpenId(wxMaUserInfo.getOpenId());
					return WebVo.success(customerVo);

				}
			}
		} catch (WxErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return WebVo.error("小程序登录获取失败");
		}
		return WebVo.error("微信获取失败， 请查看是否域名有所更改");
	}

	private WxMaUserInfo getUserMinappInfo(String sessionKey, String encryptedData, String iv, WxMaService wxService) {
		// 解密用户信息
		WxMaUserInfo userInfo = wxService.getUserService().getUserInfo(sessionKey, encryptedData, iv);
		log.info("小程序授权用户信息 " + JSON.toJSONString(userInfo));
		return userInfo;
	}
	// private WxMaPhoneNumberInfo getUserPhoneMinappInfo(String sessionKey,
	// String encryptedData, String iv,WxMaService wxService){
	// // 解密
	// WxMaPhoneNumberInfo phoneNoInfo =
	// wxService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);
	// return phoneNoInfo;
	// }

	@ApiOperation(value = "获取微信openId", tags = { "code 必填" }, httpMethod = "POST", produces = "application/json")
	@RequestMapping(value = "/code")
	public WebVo<String> auth(@RequestBody Auth auth) {
		log.info("微信入参" + JSON.toJSONString(auth));
		if (null == auth) {
			return WebVo.error("参数不能为空");
		}
		if (StringUtils.isEmpty(auth.getCode())) {
			return WebVo.error("微信code参数不能为空");
		}
		log.info("获取code成功");
		return iAuthService.auth2(auth);
	}

	@ApiOperation(value = "获取门店技师列表", httpMethod = "POST", produces = "application/json")
	@RequestMapping("/findJs")
	public WebVo<Page<JUser>> findJs(@RequestBody JUserVo jUserVo) {
		if (null == jUserVo) {
			return WebVo.error("参数不能为空");
		}
		if (StringUtils.isEmpty(jUserVo.getStoreId())) {
			return WebVo.error("门店id参数不能为空");
		}
		List<JUser> newlist = new ArrayList<>();
		JUser jUser = new JUser();
		jUser.setUserType("3");
		jUser.setStoreId(jUserVo.getStoreId());
		jUser.setDelFlag("1");
		Page<JUser> page = new Page<JUser>(jUserVo.getCurrent(), jUserVo.getSize());
		jUserService.selectPage(page, new EntityWrapper<JUser>(jUser).orderAsc(Collections.singleton("js_status ")));
		List<JUser> list = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(page.getRecords())) {
			List<JUser> users = page.getRecords();
			for (JUser juser : users) {
				CutomJOrderVo cjd = new CutomJOrderVo();
				JOrder js = new JOrder();
				js.setJsUserId(juser.getId());
				js.setStatus("3");
				JOrder jsOrder = jOrderService.selectOne(new EntityWrapper<JOrder>(js));
				int total = 0;
				if (null != jsOrder) {
					js.setStatus("4");
					total = jOrderService.selectCount(new EntityWrapper<JOrder>(js).between("create_time", DateFormatUtils.format(new Date(), "yyyy-MM-dd 00:00:00"), DateFormatUtils.format(new Date(), "yyyy-MM-dd 23:59:59")).orderAsc(Collections.singleton("ordernum")));
					if (total > 0) {
						int fen = (int) ((jsOrder.getServiceTime()+ 15)
								- DateUtils.getMinuteCompan(DateUtils.convertDateToLDT(jsOrder.getUpdateTime()),
										DateUtils.convertDateToLDT(new Date()))) ;
						if (fen > 0) {
							cjd.setPdTime((total) + "小时" + (fen) + "分钟");
						} else {
							cjd.setPdTime((total) + "小时" + (fen) + "分钟");
						}
						
					}else{
						int fen = (int) ((jsOrder.getServiceTime()+ 15)
								- DateUtils.getMinuteCompan(DateUtils.convertDateToLDT(jsOrder.getUpdateTime()),
										DateUtils.convertDateToLDT(new Date()))) ;
						cjd.setPdTime((fen) + "分钟");
					}
					total = total + 1;
				} else {
					js.setStatus("4");
					List<JOrder> totals = jOrderService
							.selectList(new EntityWrapper<JOrder>(js).between("create_time", DateFormatUtils.format(new Date(), "yyyy-MM-dd 00:00:00"), DateFormatUtils.format(new Date(), "yyyy-MM-dd 23:59:59")).orderAsc(Collections.singleton("ordernum")));
					
					if (CollectionUtils.isNotEmpty(totals)) {
						total = totals.size();
							if(null != totals.get(0).getUpdateTime()){
								int fen = (int) ((totals.get(0).getServiceTime()+ 15) - DateUtils.getMinuteCompan(
										DateUtils.convertDateToLDT(totals.get(0).getUpdateTime()),
										DateUtils.convertDateToLDT(new Date())));
								cjd.setPdTime((totals.size()) + "小时" + fen + "分钟");
							}else{
								cjd.setPdTime((totals.size()) + "小时");
							}
					} else {
						cjd.setPdTime(0 + "小时");
					}
					
				}
				juser.setDdTimeLong(cjd.getPdTime());
				juser.setDdRenNum(total);
				JOrder jOrder = new JOrder();
				jOrder.setJsUserId(juser.getId());
				JOrder jc = jOrderService
						.selectOne(new EntityWrapper<JOrder>(jOrder).and(" (status = 4 or status = 3 or status = 2) "));
				if (null == jc) {
					juser.setIsQuNum("0");
				} else {
					juser.setIsQuNum("1");
				}
				list.add(juser);
			}
			page.getRecords().clear();
			newlist.addAll(list);
			page.setRecords(newlist);
		}
		return WebVo.success(page);
	}

	@ApiOperation(value = "获取门店技师详情", httpMethod = "POST", produces = "application/json")
	@RequestMapping("/findJsSelectById")
	public WebVo<JUser> findJsSelectById(@RequestBody JUser jUser) {
		if (null == jUser) {
			return WebVo.error("参数不能为空");
		}
		if (StringUtils.isEmpty(jUser.getId())) {
			return WebVo.error("门店id参数不能为空");
		}
		return WebVo.success(jUserService.selectById(jUser.getId()));
	}

	/**
	 * 添加评论
	 */
	@ApiOperation(value = "小程序添加评论 ", tags = { "添加评论" }, httpMethod = "POST", produces = "application/json")
	@RequestMapping("/jEvaluate/save")
	public WebVo<String> save(@RequestBody JEvaluate jEvaluate) {
		if (null == jEvaluate) {
			return WebVo.error("参数不能为空");
		}
		if (StringUtils.isEmpty(jEvaluate.getOrderId())) {
			return WebVo.error("订单ID 不能为空不能为空");
		}
		JOrder jOrde = jOrderService.selectById(jEvaluate.getOrderId());
		if (null == jOrde) {
			return WebVo.error("订单不存在");
		}
		JUser jUser = jUserService.selectById(jOrde.getJsUserId());
		if (null == jUser) {
			return WebVo.error("技师不存在");
		}
		jEvaluate.setUserName(jUser.getUsername());
		JUser wx = new JUser();
		wx.setOpenId(ContextUtil.getOpenId());
		JUser wxUser = jUserService.selectOne(new EntityWrapper<>(wx));
		if (null == wxUser) {
			return WebVo.error("客户不存在");
		}
		JEvaluate je = new JEvaluate();
		je.setOrderId(jEvaluate.getOrderId());
		JEvaluate jel = jEvaluateService.selectOne(new EntityWrapper<>(je));
		if (null == jel) {
			float toatal = (jEvaluate.getServiceYx().floatValue() + jEvaluate.getJsManyi().floatValue()
					+ jEvaluate.getGtDw().floatValue()) / 3f;
			jEvaluate.setAvgNum(new BigDecimal(String.valueOf(toatal)));
			jEvaluate.setWxHeadImg(jOrde.getCustHeadImg());
			jEvaluate.setWxUserName(jOrde.getCustUsername());
			jEvaluate.setOpenId(jOrde.getCustOpenid());
			jEvaluate.setId(StringUtil.getUUID());
			jEvaluate.setCreateTime(new Date());
			jEvaluate.setStatus(1);
			jEvaluate.setRemark(jEvaluate.getRemark());
			jEvaluateService.insert(jEvaluate);
		} else {
			float toatal = (jEvaluate.getServiceYx().floatValue() + jEvaluate.getJsManyi().floatValue()
					+ jEvaluate.getGtDw().floatValue()) / 3f;
			jEvaluate.setAvgNum(new BigDecimal(String.valueOf(toatal)));
			jEvaluate.setWxHeadImg(jOrde.getCustHeadImg());
			jEvaluate.setWxUserName(jOrde.getCustUsername());
			jEvaluate.setOpenId(jOrde.getCustOpenid());
			jEvaluate.setId(jel.getId());
			jEvaluate.setCreateTime(new Date());
			jEvaluate.setStatus(1);
			jEvaluate.setRemark(jEvaluate.getRemark());
			jEvaluateService.updateById(jEvaluate);
		}

		return WebVo.success();
	}

	/**
	 * 添加评论
	 */
	@ApiOperation(value = "小程序算出技师评分以及评论数 ", tags = { "添加评论" }, httpMethod = "POST", produces = "application/json")
	@RequestMapping("/jEvaluate/statiats")
	public WebVo<JsAvgNum> statiats(@RequestBody JEvaluate jEvaluate) {
		if (null == jEvaluate) {
			return WebVo.error("参数不能为空");
		}
		if (StringUtils.isEmpty(jEvaluate.getUserId())) {
			return WebVo.error("技师ID 不能为空不能为空");
		}
		jEvaluate.setStatus(1);
		List<JEvaluate> list = jEvaluateService.selectList(new EntityWrapper<JEvaluate>(jEvaluate));
		JsAvgNum JsAvgNum = new JsAvgNum();
		if (CollectionUtils.isNotEmpty(list)) {
			BigDecimal bitg = new BigDecimal("0");
			for (JEvaluate jEvaluate2 : list) {
				bitg = bitg.add(jEvaluate2.getAvgNum());
			}
			JsAvgNum.setPlNum(list.size());
			JsAvgNum.setAvgNum(
					bitg.divide(new BigDecimal(String.valueOf(list.size())),1, BigDecimal.ROUND_HALF_UP).setScale(1, BigDecimal.ROUND_HALF_UP));
		} else {
			JsAvgNum.setPlNum(100);
			JsAvgNum.setAvgNum(new BigDecimal("5"));
		}

		return WebVo.success(JsAvgNum);
	}

	/**
	 * BI查询所有评论
	 * @throws UnsupportedEncodingException 
	 */
	@ApiOperation(value = "BI所有所有评论 ", httpMethod = "POST", produces = "application/json")
	@RequestMapping("/jEvaluate/list")
	public WebVo<Page<JEvaluate>> jEvaluatelist(@RequestBody JEvaluateVo jEvaluateVo) throws UnsupportedEncodingException {
		if (null == jEvaluateVo) {
			return WebVo.error("参数不能为空");
		}
		if (StringUtils.isEmpty(jEvaluateVo.getUserId())) {
			return WebVo.error("userId技师ID 不能为空不能为空");
		}
		JEvaluate je = new JEvaluate();
		BeanUtils.copyProperties(jEvaluateVo, je);
		je.setStatus(1);
		Page<JEvaluate> page = new Page<JEvaluate>(jEvaluateVo.getCurrent(), jEvaluateVo.getSize());
		jEvaluateService.selectPage(page,
				new EntityWrapper<JEvaluate>(je).orderDesc(Collections.singleton("create_time ")));
		List<JEvaluate> list = page.getRecords();
		List<JEvaluate> newList = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(list)){
			for(JEvaluate jEvalua : list ){
				if(StringUtils.isNotEmpty(jEvalua.getWxUserName())){
					 if(com.quiet.live.hall.utils.string.StringUtil.isBase64(jEvalua.getWxUserName())){
						 jEvalua.setWxUserName(new String(org.apache.commons.codec.binary.Base64.decodeBase64(jEvalua.getWxUserName()),"UTF-8"));
				     }
				}
				newList.add(jEvalua);
			}
		}
		page.getRecords().clear();
		page.setRecords(newList);
		log.info(JSON.toJSONString(page));
		return WebVo.success(page);
	}

	@ApiOperation(value = "小程序修改门店坐标 ", tags = {
			"修改带ID， 保存不需要带ID" }, httpMethod = "POST", produces = "application/json")
	@RequestMapping("/updateJwDu")
	public WebVo<String> updateJwDu(@RequestBody List<JStore> jStores) {
		if (null == jStores) {
			return WebVo.error("参数不能为空");
		}
		log.info("门店:" + JSON.toJSONString(jStores));
		if (CollectionUtils.isNotEmpty(jStores)) {
			for (JStore jStore : jStores) {
				if (StringUtils.isEmpty(jStore.getId())) {
					return WebVo.error("门店ID不能为空");
				}
				if (StringUtils.isEmpty(jStore.getJwDu())) {
					return WebVo.error("经纬不能为空");
				}
				JStore jb = jStoreService.selectById(jStore.getId());
				if (null != jb) {
					jStore.setId(jStore.getId());
					jStoreService.updateById(jStore);

				} else {
					log.info("不存在的门店:" + JSON.toJSONString(jStore));
				}
			}
			return WebVo.success();
		}

		return WebVo.error("门店不存在");
	}

	@ApiOperation(value = "小程序修改门店坐标 ", tags = {
			"修改带ID， 保存不需要带ID" }, httpMethod = "POST", produces = "application/json")
	@RequestMapping("/shareConfig")
	public WebVo<WxJsapiSignature> shareConfig(@RequestBody ShareVo shareVo) {
		if (null == shareVo) {
			return WebVo.error("参数不能为空");
		}
		if (StringUtil.isEmpty(shareVo.getUrl())) {
			return WebVo.error("当前分享页面的url不能为空!");
		}
		try { // 直接调用wxMpServer 接口
			WxJsapiSignature wxJsapiSignature = wxMpService.createJsapiSignature(shareVo.getUrl());
			return WebVo.success(wxJsapiSignature);
		} catch (WxErrorException e) {
			log.error("分享错误信息", e);
			return WebVo.error("分享创建配置错误");
		}

	}

	@ApiOperation(value = "小程序修改门店坐标 ", tags = {
			"修改带ID， 保存不需要带ID" }, httpMethod = "POST", produces = "application/json")
	@RequestMapping("/shareShortLink")
	public WebVo<String> shareShortLink(@RequestBody ShareVo shareVo) {
		if (null == shareVo) {
			return WebVo.error("参数不能为空");
		}
		if (StringUtil.isEmpty(shareVo.getUrl())) {
			return WebVo.error("当前长连接面的url不能为空!");
		}
		try { // 直接调用wxMpServer 接口
			String shortUrl = wxMpService.shortUrl(shareVo.getUrl());
			return WebVo.success(shortUrl);
		} catch (WxErrorException e) {
			log.error("分享错误信息", e);
			return WebVo.error("长连接创建配置错误");
		}
	}
}
