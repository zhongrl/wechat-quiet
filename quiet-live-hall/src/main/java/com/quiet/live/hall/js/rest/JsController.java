package com.quiet.live.hall.js.rest;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.quiet.live.hall.config.ContextUtil;
import com.quiet.live.hall.config.RedisComponent;
import com.quiet.live.hall.entity.JEvaluate;
import com.quiet.live.hall.entity.JOrder;
import com.quiet.live.hall.entity.JUser;
import com.quiet.live.hall.service.IAuthService;
import com.quiet.live.hall.service.IJEmailMsgService;
import com.quiet.live.hall.service.IJWxtempMsgService;
import com.quiet.live.hall.service.JEvaluateService;
import com.quiet.live.hall.service.JOrderService;
import com.quiet.live.hall.service.JUserService;
import com.quiet.live.hall.utils.DateUtils;
import com.quiet.live.hall.utils.PropUtil;
import com.quiet.live.hall.utils.base.MD5Util;
import com.quiet.live.hall.utils.string.StringUtil;
import com.quiet.live.hall.utils.web.WebVo;
import com.quiet.live.hall.vo.Auth;
import com.quiet.live.hall.vo.JEvaluateVo;
import com.quiet.live.hall.vo.JOrderVo;
import com.quiet.live.hall.vo.JUserVo;
import com.quiet.live.hall.vo.JsJOrderVo;
import com.quiet.live.hall.vo.WxtempVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
@Api(value="技师端接口",tags={"技师端接口"},description = "技师端接口")
@RestController
@RequestMapping("/js")
@Slf4j
@CrossOrigin
public class JsController {
	
	@Autowired
	JUserService jUserService;
	
	@Autowired
	IAuthService iAuthService;
	
	@Autowired
 	RedisComponent redisComponent;
	
	@Autowired
	public WxMpService wxMpService;
	
	@Autowired
	JEvaluateService jEvaluateService;
	
	@Autowired
	JOrderService jOrderService;
	
	@Autowired
	IJWxtempMsgService iJWxtempMsgService;
	
	@Autowired
	IJEmailMsgService iJEmailMsgService;
	
	@ApiOperation(value = "获取门店技师信息",httpMethod = "POST",produces="application/json")
	@RequestMapping("/findStoreJs")
	public WebVo<Page<JUser>> findStoreJs(@RequestBody JUserVo jUserVo){
		if(null == jUserVo){
			return WebVo.error("参数不能为空");
		}
		
		jUserVo.setStoreId(ContextUtil.getStoreId());
		JUser jUser = new JUser();
		BeanUtils.copyProperties(jUserVo, jUser);
		jUser.setUserType("3");
		jUser.setDelFlag("1");
		jUser.setStoreId(jUserVo.getStoreId());
		Page<JUser> page = new Page<JUser>(jUserVo.getCurrent(),jUserVo.getSize());
		return WebVo.success(jUserService.selectPage(page,new EntityWrapper<JUser>(jUser).orderDesc(Collections.singleton("create_time "))));
	}
	
	@ApiOperation(value = "获取技师状态信息",httpMethod = "POST",produces="application/json")
	@RequestMapping("/findJsStatus")
	public WebVo<JUser> findJsStatus(@RequestBody JUserVo jUserVo){
		log.info(JSON.toJSONString(ContextUtil.getUserId()));
		JUser JUserVo = jUserService.selectById(ContextUtil.getUserId());
		return WebVo.success(JUserVo);
	}
	
	
	@ApiOperation(value = "小程序技师端登陆 ",httpMethod = "POST",produces="application/json")
	@RequestMapping(value="/jslogin")
	public WebVo<String> jslogin(@RequestBody JUserVo jUserVo){
		log.info("微信入参"  + JSON.toJSONString(jUserVo));
		if(null == jUserVo){
			return WebVo.error("参数不能为空");
		}
		
//		if(!StringUtil.isEmpty(jUserVo.getOpenId())){
//			JUser jUser1 = new JUser();
//			jUser1.setOpenId(jUserVo.getOpenId());
//			jUser1.setDelFlag("1");
//			jUser1.setUserType("3");
//			JUser sysUser1 = jUserService.selectOne(new EntityWrapper<JUser>(jUser1));
//			if(null != sysUser1){
//				// 自动登录
//				String token = StringUtil.getUUID();
//				redisComponent.setEx(token, sysUser1, PropUtil.getInt("token.timeout", 17280000));
//				sysUser1.setLoginTime(new Date());
//				jUserService.updateById(sysUser1);
//				return WebVo.success(token);
//			}
//			if(StringUtil.isEmpty(jUserVo.getAccount())){
//				return WebVo.result(Constant.CommonCode.NOT_LOGIN, "账户不能为空");
//			}
//			if(StringUtil.isEmpty(jUserVo.getPassword())){
//				return WebVo.result(Constant.CommonCode.NOT_LOGIN, "密码不能为空");
//			}
//			JUser jUser = new JUser();
//			jUser.setAccount(jUserVo.getAccount());
//			jUser.setDelFlag("1");
//			jUser.setUserType("3");
//			JUser sysUser = jUserService.selectOne(new EntityWrapper<JUser>(jUser));
//			if(null == sysUser){
//				return WebVo.error("用户不存在");
//			}
//			if(!sysUser.getPassword().equals(MD5Util.md5(jUserVo.getPassword()))){
//				return WebVo.error("密码错误");
//			}
//			if(StringUtils.isEmpty(sysUser.getOpenId())){
//				sysUser.setOpenId(jUserVo.getOpenId());
//				sysUser.setLoginTime(new Date());
//
//				try {
////					String unionid = WechatUtils.getUnionidLogin(redisComponent.get("accessToken-"+jUserVo.getOpenId()), jUserVo.getOpenId());
//					// 获取 开发者平台id
//					String unionid = WechatUtils.getUnionidLogin(wxMpService.getAccessToken(), jUserVo.getOpenId());
//					sysUser.setUnionid(unionid);
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//
//			}
//			String token = StringUtil.getUUID();
//			redisComponent.setEx(token, sysUser, PropUtil.getInt("token.timeout", 17280000));
//			sysUser.setLoginTime(new Date());
//			
//			jUserService.updateById(sysUser);
//			return WebVo.success(token);
//		}else{
			if(StringUtil.isEmpty(jUserVo.getAccount())){
				return WebVo.error("账户不能为空");
			}
			if(StringUtil.isEmpty(jUserVo.getPassword())){
				return WebVo.error("密码不能为空");
			}
			
			JUser jUser = new JUser();
			jUser.setAccount(jUserVo.getAccount());
			jUser.setDelFlag("1");
			jUser.setUserType("3");
			JUser sysUser = jUserService.selectOne(new EntityWrapper<JUser>(jUser));
			if(null == sysUser){
				return WebVo.error("用户不存在");
			}
			if(!sysUser.getPassword().equals(MD5Util.md5(jUserVo.getPassword()))){
				return WebVo.error("密码错误");
			}
			if(StringUtils.isEmpty(sysUser.getOpenId())){
				if(StringUtil.isEmpty(jUserVo.getCode())){
					return WebVo.error("code密码不能为空");
				}
				WxMpOAuth2AccessToken wx= null ;
				try {
					 wx = this.wxMpService.oauth2getAccessToken(jUserVo.getCode());
				} catch (WxErrorException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return WebVo.error("微信获取用户信息失败");
				}
				sysUser.setOpenId(wx.getOpenId());
				sysUser.setLoginTime(new Date());
				sysUser.setUnionid(wx.getUnionId());
				jUserService.updateById(sysUser);
				
			}
			String token = "js" + sysUser.getOpenId();
			redisComponent.setEx(token, sysUser, PropUtil.getInt("token.timeout", 2592000));
			if(!StringUtils.isEmpty(sysUser.getOpenId())){
				redisComponent.setEx(sysUser.getOpenId(), token, PropUtil.getInt("token.timeout", 2592000));
			}
			sysUser.setLoginTime(new Date());
			jUserService.updateById(sysUser);
			return WebVo.success(token);
//		}
		
	} 
	
	@ApiOperation(value = "获取微信openId",tags = {"code 必填"},httpMethod = "POST",produces="application/json")
	@RequestMapping(value="/logOut")
	public WebVo<String> logOut(@RequestBody Auth auth){
		log.info("微信入参"  + JSON.toJSONString(auth));
		redisComponent.del(ContextUtil.getToken());
		return WebVo.success();
	} 
	
	@ApiOperation(value = "获取微信openId",tags = {"code 必填"},httpMethod = "POST",produces="application/json")
	@RequestMapping(value="/code")
	public WebVo<String> auth(@RequestBody Auth auth){
		log.info("微信入参"  + JSON.toJSONString(auth));
		if(null == auth){
			return WebVo.error("参数不能为空");
		}
		if(StringUtils.isEmpty(auth.getCode())){
			return WebVo.error("微信code参数不能为空");
		}
		return iAuthService.auth2(auth);
	} 
	
	/**
	 *   BI查询所有评论
	 */
	@ApiOperation(value = "单个技师所有所有评论 ",httpMethod = "POST",produces="application/json")
	@RequestMapping("/jEvaluate/list")
	public WebVo<Page<JEvaluate>> list(@RequestBody JEvaluateVo jEvaluateVo){
		JEvaluate jEvaluate = new JEvaluate();
		jEvaluate.setUserId(ContextUtil.getUserId());
		jEvaluate.setStatus(1);
		Page<JEvaluate> page = new Page<JEvaluate>(jEvaluateVo.getCurrent(),jEvaluateVo.getSize());
		return WebVo.success(jEvaluateService.selectPage(page,new EntityWrapper<JEvaluate>(jEvaluate)
				.orderDesc(Collections.singleton("create_time "))));
	}
	
	/**
	 *   BI查询所有评论
	 */
	@ApiOperation(value = "单个技师所有所有评论 ",httpMethod = "POST",produces="application/json")
	@RequestMapping("/jEvaluate/selectById")
	public WebVo<JEvaluate> selectById(@RequestBody JEvaluate jEvaluate){
		
		if(StringUtils.isEmpty(jEvaluate.getOrderId())){
			return WebVo.error("订单id参数不能为空");
		}
		return WebVo.success(jEvaluateService.selectOne(new EntityWrapper<JEvaluate>(jEvaluate)));
		
	}
	
	/**
	 *  排队查询
	 */
	@ApiOperation(value = "技师订单排队查询 ",httpMethod = "POST",produces="application/json")
	@RequestMapping("/orderQueuing")
	public WebVo<JsJOrderVo> orderQueuing(@RequestBody JOrder jOrder){
		JOrder jOrde = new JOrder();
		jOrde.setJsUserId(ContextUtil.getUserId());
		JsJOrderVo cjv = new JsJOrderVo();
		List<JOrder> list = null ; 
		if(StringUtils.equals("1", jOrder.getStatus())){
			list = jOrderService.selectList(new EntityWrapper<JOrder>(jOrde).and("status = 1 OR status = 2 OR status = 5 ").orderDesc(Collections.singleton("create_time ")));
		}else{
			list = jOrderService.selectList(new EntityWrapper<JOrder>(jOrde).and("status = 4 OR status = 3").orderAsc(Collections.singleton("create_time ")).orderAsc(Collections.singleton("status")));
		}
		if(CollectionUtils.isNotEmpty(list)){
			cjv.setPdDate(list.get(0).getCreateTime());
			cjv.setPdPresonNum(list.size());
		}
		cjv.setOrders(list);
		return WebVo.success(cjv);
	}
	
	/**
	 *  排队查询
	 */
	@ApiOperation(value = "技师订单排队查询 ",httpMethod = "POST",produces="application/json")
	@RequestMapping("/orderOkSuccess")
	public WebVo<JsJOrderVo> orderOkSuccess(@RequestBody JOrder jOrder){
		JOrder jOrde = new JOrder();
		jOrde.setJsUserId(ContextUtil.getUserId());
		JsJOrderVo cjv = new JsJOrderVo();
		List<JOrder> list = jOrderService.selectList(new EntityWrapper<JOrder>(jOrde).and("status = 1 OR status = 2 OR status = 5 ").orderDesc(Collections.singleton("create_time ")));
		cjv.setOrders(list);
		return WebVo.success(cjv);
	}
	

	/**
	 *  排队查询
	 */
	@ApiOperation(value = "技师订单排队查询 ",httpMethod = "POST",produces="application/json")
	@RequestMapping("/orderSuccess")
	public WebVo<List<JOrder>> orderSuccess(@RequestBody JOrder jOrder){
		JOrder jOrde = new JOrder();
		jOrde.setJsUserId(ContextUtil.getUserId());
		List<JOrder> list = jOrderService.selectList(new EntityWrapper<JOrder>(jOrde).and(" ( status =1 OR status = 2 OR status = 5 ) ").orderDesc(Collections.singleton("create_time ")));
		return WebVo.success(list);
	}
	
	/**
	 *  服务中接口更新
	 */
	@ApiOperation(value= "技师确定服务接口 ",tags = {"技师确定服务接口"},httpMethod = "POST",produces="application/json")
	@RequestMapping("/updateOrderService")
	public WebVo<String> updateOrderService(@RequestBody JOrder jOrder){
		log.info("更新订单接口的参数： " + JSON.toJSONString(jOrder));
		if(null == jOrder){
			return WebVo.error("参数不能为空");
		}
		if(StringUtils.isEmpty(jOrder.getId())){
			return WebVo.error("订单ID不能为空");
		}
		JOrder jOrd = jOrderService.selectById(jOrder.getId());
		if(null == jOrd){
			return WebVo.error("订单不存在");
		}
		JOrder update = new JOrder();
		update.setUpdateTime(new Date());
		update.setStatus("3");
		update.setId(jOrder.getId());
		update.setJsServiceTime(new Date());
		jOrderService.updateById(update);
		//  推送排队消息给客户
//		iJEmailMsgService.sendMsg();
		return WebVo.success();
	}
	
	/**
	 *  服务中接口更新
	 */
	@ApiOperation(value= "技师催促服务接口 ",tags = {"技师催促服务接口"},httpMethod = "POST",produces="application/json")
	@RequestMapping("/cuiCuService")
	public WebVo<String> cuiCuService(@RequestBody JOrder jOrder){
		log.info("更新订单接口的参数： " + JSON.toJSONString(jOrder));
		if(null == jOrder){
			return WebVo.error("参数不能为空");
		}
		if(StringUtils.isEmpty(jOrder.getId())){
			return WebVo.error("订单ID不能为空");
		}
		JOrder jodupda = jOrderService.selectById(jOrder.getId());
		if(null == jodupda){
			return WebVo.error("订单不存在");
		}
	//   推送待支付消息给客户
		WxtempVo wxtempVo = new WxtempVo();
		wxtempVo.setOpenId(jodupda.getCustOpenid());
		wxtempVo.setTitle("尊敬"+jodupda.getCustUsername()+"您好！点击“详情”,确认订单支付吧~");
		wxtempVo.setKeyword1(jodupda.getStoreName());
		wxtempVo.setKeyword2(DateUtils.formatDate(jodupda.getUpdateTime()));
		wxtempVo.setKeyword3(jodupda.getPrice().toString());
		wxtempVo.setUserName(jodupda.getCustUsername());
		wxtempVo.setUserId(jodupda.getCustUserId());
		iJWxtempMsgService.sendDAIZFTXMsg(wxtempVo);
		return WebVo.success();
	}
	
	/**
	 *  服务中接口更新
	 */
	@ApiOperation(value= "技师完成服务接口 ",tags = {"技师完成服务接口"},httpMethod = "POST",produces="application/json")
	@RequestMapping("/updateOrderEndService")
	public WebVo<String> updateOrderEndService(@RequestBody JOrder jOrder){
		log.info("更新订单接口的参数： " + JSON.toJSONString(jOrder));
		if(null == jOrder){
			return WebVo.error("参数不能为空");
		}
		if(StringUtils.isEmpty(jOrder.getId())){
			return WebVo.error("订单ID不能为空");
		}
		JOrder jOrd = jOrderService.selectById(jOrder.getId());
		if(null == jOrd){
			return WebVo.error("订单不存在");
		}
		JOrder update = new JOrder();
		update.setUpdateTime(new Date());
		update.setStatus("2");
		update.setId(jOrder.getId());
		jOrderService.updateById(update);
		//   推送待支付消息给客户
		WxtempVo wxtempVo = new WxtempVo();
		wxtempVo.setOpenId(jOrd.getCustOpenid());
		wxtempVo.setTitle("尊敬"+jOrd.getCustUsername()+"您好！点击“详情”,确认订单支付吧~");
		wxtempVo.setKeyword1(jOrd.getServiceName());
		wxtempVo.setKeyword2(jOrd.getJsUsername());
		wxtempVo.setKeyword3(DateUtils.formatDate(jOrd.getUpdateTime()));
		wxtempVo.setKeyword4(jOrd.getPrice().toString());
		wxtempVo.setUserName(jOrd.getCustUsername());
		wxtempVo.setUserId(jOrd.getCustUserId());
		iJWxtempMsgService.sendZFTXMsg(wxtempVo);
		iJEmailMsgService.sendJsMsg(jOrd.getJsUserId());
		return WebVo.success();
	}
	
	@ApiOperation(value= "技师取消订单 ",tags = {"技师取消订单 "},httpMethod = "POST",produces="application/json")
	@RequestMapping("/orderCancel")
	public WebVo<String> orderCancel(@RequestBody JOrder jOrder){
		log.info("更新订单接口的参数： " + JSON.toJSONString(jOrder));
		if(null == jOrder){
			return WebVo.error("参数不能为空");
		}
		if(StringUtils.isEmpty(jOrder.getId())){
			return WebVo.error("订单ID不能为空");
		}
		jOrder.setCancelRemark("技师取消");
		JOrder jOrd = jOrderService.selectById(jOrder.getId());
		if(null == jOrd){
			return WebVo.error("订单不存在");
		}
		JOrder update = new JOrder();
		update.setUpdateTime(new Date());
		update.setStatus("5");
		update.setId(jOrder.getId());
		jOrderService.updateById(update);
		//  推送排队消息给客户
		WxtempVo wxtempVo = new WxtempVo();
		wxtempVo.setOpenId(jOrd.getCustOpenid());
		wxtempVo.setUserName(jOrder.getCustUsername());
		wxtempVo.setKeyword1(jOrd.getServiceName());
		wxtempVo.setKeyword2(DateUtils.formatDate(jOrd.getCreateTime()));
		iJWxtempMsgService.sendCancelOrderMsg(wxtempVo);
		
		return WebVo.success();
	}
	
	
	/** 
	 *  客户端查询订单列表
	 */
	@ApiOperation(value = "客户端查询订单列表",httpMethod = "POST",produces="application/json")
	@RequestMapping("/list")
	public WebVo<Page<JOrder>> list(@RequestBody JOrderVo jOrderVo){
		JOrder jOrder = new JOrder();
		BeanUtils.copyProperties(jOrderVo, jOrder);
		EntityWrapper<JOrder> ew = new EntityWrapper<JOrder>(jOrder);
		if(null != jOrderVo){
			if(null != jOrderVo.getStartTime() && null != jOrderVo.getEndTime()){
				if(!jOrderVo.getStartTime().before(jOrderVo.getEndTime())){
					return WebVo.error("查询时间开始时间不能大于结束时间");
				}else{
					ew.between("create_time", jOrderVo.getStartTime(), jOrderVo.getEndTime());
				}
				
			}
			BeanUtils.copyProperties(jOrderVo, jOrder);
			jOrder.setStatus("1");
		}
		Page<JOrder> page = new Page<JOrder>(jOrderVo.getCurrent(),jOrderVo.getSize());
		return WebVo.success(jOrderService.selectPage(page,ew
				.orderDesc(Collections.singleton("create_time "))));
	}
	

	/** 
	 *  客户端查询订单列表
	 */
	@ApiOperation(value = "客户端查询订单列表",httpMethod = "POST",produces="application/json")
	@RequestMapping("/selectOrderById")
	public WebVo<JOrder> selectOrderById(@RequestBody JOrder jOrder){
		if(null == jOrder){
			return WebVo.error("参数不能为空");
		}
		if(StringUtils.isEmpty(jOrder.getId())){
			return WebVo.error("订单ID不能为空");
		}
		JOrder jo = jOrderService.selectById(jOrder.getId());
		if(null == jo){
			return WebVo.error("订单不存在");
		}
		return WebVo.success(jo);
	}
	
	/**
	 *  切换技师工作状态
	 */
	@ApiOperation(value= "切换技师工作状态 ",tags = {"切换技师工作状态"},httpMethod = "POST",produces="application/json")
	@RequestMapping("/updateJsUserStatus")
	public WebVo<String> updateJsUserStatus(@RequestBody JUser jUser){
		log.info("更新User的参数： " + JSON.toJSONString(jUser));
		JUser  jOrd = jUserService.selectById(ContextUtil.getUserId());
		if(null == jOrd){
			return WebVo.error("技师不存在");
		}
		JUser update = new JUser();
		update.setUpdateTime(new Date());
		update.setId(jOrd.getId());
		update.setJsStatus(jUser.getJsStatus());
		jUserService.updateById(update);
		return WebVo.success();
	}
	
	/**
	 *  服务中接口更新
	 */
	@ApiOperation(value= "线下支付接口 ",tags = {"技师完成服务接口"},httpMethod = "POST",produces="application/json")
	@RequestMapping("/upPayOrderService")
	public WebVo<String> upPayOrderService(@RequestBody JOrder jOrder){
		log.info("更新订单接口的参数： " + JSON.toJSONString(jOrder));
		if(null == jOrder){
			return WebVo.error("参数不能为空");
		}
		if(StringUtils.isEmpty(jOrder.getId())){
			return WebVo.error("订单ID不能为空");
		}
		JOrder jOrd = jOrderService.selectById(jOrder.getId());
		if(null == jOrd){
			return WebVo.error("订单不存在");
		}
		JOrder jodupdate = new JOrder();
 	    jodupdate.setUpdateTime(new Date());
 	    jodupdate.setStatus("1");
 	    jodupdate.setPayTime(new Date());
 	    jodupdate.setId(jOrd.getId());
 	    jodupdate.setOptSource("第三方支付");
 	    jOrderService.updateById(jodupdate);
 	    
 	//   推送排队消息给客户
		WxtempVo wxtempVo = new WxtempVo();
		wxtempVo.setOpenId(jOrd.getCustOpenid());
		wxtempVo.setUserName(jOrd.getCustUsername());
		wxtempVo.setKeyword1("¥ " + jOrd.getPrice().toString());
		wxtempVo.setKeyword2(jOrd.getStoreName());
		wxtempVo.setKeyword3(DateUtils.formatDate(jOrd.getCreateTime()));
		iJWxtempMsgService.sendZFSUCCESSTZMsg(wxtempVo);
		return WebVo.success();
	}
	
}
