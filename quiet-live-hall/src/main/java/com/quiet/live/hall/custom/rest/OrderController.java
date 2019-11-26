package com.quiet.live.hall.custom.rest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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
import com.quiet.live.hall.entity.JCouponDetail;
import com.quiet.live.hall.entity.JEvaluate;
import com.quiet.live.hall.entity.JOrder;
import com.quiet.live.hall.entity.JService;
import com.quiet.live.hall.entity.JStore;
import com.quiet.live.hall.entity.JUser;
import com.quiet.live.hall.service.IAuthService;
import com.quiet.live.hall.service.IJWxtempMsgService;
import com.quiet.live.hall.service.JCouponDetailService;
import com.quiet.live.hall.service.JEvaluateService;
import com.quiet.live.hall.service.JOrderService;
import com.quiet.live.hall.service.JServiceService;
import com.quiet.live.hall.service.JStoreService;
import com.quiet.live.hall.service.JUserService;
import com.quiet.live.hall.utils.DateUtils;
import com.quiet.live.hall.utils.string.StringUtil;
import com.quiet.live.hall.utils.web.WebVo;
import com.quiet.live.hall.vo.CutomJOrderVo;
import com.quiet.live.hall.vo.JOrderVo;
import com.quiet.live.hall.vo.WxtempVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api(value="小程序客户端订单接口",tags={"小程序客户端订单接口"})
@RestController
@RequestMapping("/custom")
@Slf4j
@CrossOrigin
public class OrderController {

	
	@Value("${service.hour}")
	private Integer serviceHour;
	
	@Autowired
	JOrderService jOrderService;
	
	@Autowired
	IAuthService iAuthService;
	

	@Autowired
 	RedisComponent redisComponent;
	
	@Autowired
	JUserService jUserService;
	
	@Autowired
	JServiceService jServiceService;
	
	
	@Autowired
	JStoreService jStoreService;
	
	@Autowired
	JEvaluateService jEvaluateService;
	
	@Autowired
	JCouponDetailService jCouponDetailService;
	
	@Autowired
	IJWxtempMsgService iJWxtempMsgService;
	
	@Value("${wx.minapp.yw.appid}")
	private String appid;
	
	/**
	 *  一键呼叫 下单
	 */
	@ApiOperation(value = "一键呼叫 下单 ",httpMethod = "POST",produces="application/json")
	@RequestMapping("/oneKeyUpOrder")
	public WebVo<String> oneKeyUpOrder(@RequestBody JOrder jOrder){
		log.info("下单参数：" + JSON.toJSONString(jOrder));
		if(null == jOrder){
			return WebVo.error("参数不能为空");
		}
		JUser juserOneKey = jUserService.selectById(ContextUtil.getUserId());
//		if(StringUtils.isEmpty(juserOneKey.getMobile())){
//			final WxMaService wxService = WxMaConfiguration.getMaService(appid);
//			WxMaJscode2SessionResult session = null ;
//			try {
//				session = wxService.getUserService().getSessionInfo(jOrder.getCode());
//				log.info(session.getSessionKey());
//				log.info(session.getOpenid());
//			} catch (WxErrorException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				return WebVo.error("微信获取失败， 获取手机号请查看是否域名有所更改1");
//			}
//			if(!StringUtils.isEmpty(jOrder.getEncryptedData()) && !StringUtils.isEmpty(jOrder.getIv())){
//				WxMaPhoneNumberInfo wxMaPhoneNumberInfo = getUserPhoneMinappInfo(session.getSessionKey(),
//						jOrder.getEncryptedData(),  jOrder.getIv(),wxService);
//				if(null == wxMaPhoneNumberInfo){
//					return WebVo.error("微信获取失败， 获取手机号请查看是否域名有所更改2");
//				}
//				juserOneKey.setMobile(wxMaPhoneNumberInfo.getPhoneNumber());
//				jUserService.updateById(juserOneKey);
//			}else{
//				return WebVo.error("微信获取失败， 获取手机号请查看是否域名有所更改3");
//			}
//		}
		JOrder jod = new JOrder();
		jod.setCustOpenid(ContextUtil.getOpenId());
		int checkOrder = jOrderService.selectCount(new EntityWrapper<JOrder>(jod).and(" status != 1").and(" status != 5"));
		if(checkOrder > 0){
			return WebVo.error("您有进行中的订单，请结束订单再呼叫技师！");
		}
		BigDecimal jsbig = new BigDecimal("0");
		JUser juser = null ; 
		if(StringUtils.isEmpty(jOrder.getJsUserId())){
			return WebVo.error("技师ID不能为空");
		}else{
			juser = jUserService.selectById(jOrder.getJsUserId());
			if(null == juser){
				return WebVo.error("技师不存在，请联系店铺！");
			}
			if(!StringUtils.equals(juser.getUserType(), "3")){
				return WebVo.error("技师工种不对， 不能进行呼叫！");
			}
			if(1 != juser.getJsStatus()){
				return WebVo.error("技师休息中 或者暂停接单啦");
			}
			jOrder.setJsHeadImg(juser.getHeadImg());
			jOrder.setJsMobile(juser.getMobile());
			jOrder.setJsUsername(juser.getUsername());
			jOrder.setOrgPrice(juser.getOrgPrice());
			jOrder.setJsLevel(juser.getLevel());
			jsbig = jsbig.add(juser.getPrice());
		}
		
		JService jse = jServiceService.selectById(juser.getServiceId());
		if(null == jse){
			return WebVo.error("服务不存在， 请配置该服务");
		}
		jOrder.setServiceName(jse.getServiceName());
		jOrder.setServiceTime(jse.getServiceTime());
		JStore jse1 = jStoreService.selectById(juser.getStoreId());
		if(null == jse1){
			return WebVo.error("门店不存在， 请配置该服务");
		}
		jOrder.setStoreName(jse1.getName());
		
		BigDecimal couponBig = new BigDecimal("0");
		JCouponDetail jCouponDeta = new JCouponDetail();
		jCouponDeta.setSyOpenid(ContextUtil.getOpenId());
		jCouponDeta.setDelFlag("2");
		List<JCouponDetail> coupons = jCouponDetailService.selectList(new EntityWrapper<JCouponDetail>(jCouponDeta).orderDesc(Collections.singleton("price")));
		if(CollectionUtils.isNotEmpty(coupons)){
			JCouponDetail jCouponDetail = coupons.get(0);
			if(!jCouponDetail.getEndTime().before(new Date())){
				jOrder.setCouponName(jCouponDetail.getTitle());
				jOrder.setCouponPrice(jCouponDetail.getPrice());
				couponBig = couponBig.add(jCouponDetail.getPrice());
				jOrder.setCouponId(jCouponDetail.getId());
			}
		}
		
		jOrder.setStatus("4");
		JOrder jOrd = new JOrder();
		jOrd.setJsUserId(jOrder.getJsUserId());
		int count = jOrderService.selectCount(new EntityWrapper<JOrder>(jOrd).and(" status != 5 ").between("create_time",  DateFormatUtils.format(new Date(), "yyyy-MM-dd 00:00:00"), DateFormatUtils.format(new Date(), "yyyy-MM-dd 23:59:59")));
		if(count > serviceHour){
			return WebVo.error("技师服务时间已经达到上限，技师也需要休息，请谅解！");
		}
		JOrder joid = new JOrder();
		joid.setStoreId(jOrder.getStoreId());
		int count1 = jOrderService.selectCount(new EntityWrapper<JOrder>(joid).between("create_time", DateFormatUtils.format(new Date(), "yyyy-MM-dd 00:00:00"), DateFormatUtils.format(new Date(), "yyyy-MM-dd 23:59:59")));
		log.info("第"+count1+"个人预约排号");
		JOrder jjo = new JOrder();
		jjo.setJsUserId(jOrder.getJsUserId());
		int count2 = jOrderService.selectCount(new EntityWrapper<JOrder>(jjo).and("status = 4 OR status = 3").between("create_time", DateFormatUtils.format(new Date(), "yyyy-MM-dd 00:00:00"), DateFormatUtils.format(new Date(), "yyyy-MM-dd 23:59:59")));
		if(count1 == 0 ){
			jOrder.setOrdernum(1);
		}else{
			jOrder.setOrdernum(count2 + 1);
		}
		jOrder.setLineUpNumber("J" + StringUtil.stringFormNumer_3(Long.parseLong(String.valueOf(count1 == 0 ? 1L:Long.parseLong(String.valueOf(count1 + 1))))));
		log.info("第"+count1+"个人预约排号" + jOrder.getLineUpNumber());
//		String orderNumer = DateUtils.formatDate(new Date(), DateUtils.DATE_FORMAT_DATETIME_TIME_NEW);
//		JOrder jO = new JOrder();
//		int total = jOrderService.selectCount(new EntityWrapper<JOrder>(jO));
//		orderNumer = orderNumer +""+ String.valueOf(total == 0 ? 1:total);
//		jOrder.setOrderNumber("1" + StringUtil.stringFormNumer_18(1, Long.parseLong(orderNumer)));
		jOrder.setCreateTime(new Date());
		jOrder.setId(StringUtil.getUUID());
		JStore jStor = jStoreService.selectById(jOrder.getStoreId());
		jOrder.setJwDu(jStor.getJwDu());
		jOrder.setAddress(jStor.getAddress());
		jOrder.setCustHeadImg(juserOneKey.getHeadImg());
		jOrder.setCustUsername(juserOneKey.getUsername());
		jOrder.setCustOpenid(juserOneKey.getOpenId());
		jOrder.setCustMobile(juserOneKey.getMobile());
		jOrder.setCustUserId(juserOneKey.getId());
		BigDecimal bigs = jsbig.subtract(couponBig);
		// 支付金额
		jOrder.setPrice(bigs.setScale(2, BigDecimal.ROUND_HALF_UP));
		//节约金额
		jOrder.setJyPrice((jOrder.getOrgPrice().subtract(bigs)).setScale(2, BigDecimal.ROUND_HALF_UP).intValue());
		jOrderService.insert(jOrder);
		WxtempVo wxtempVo = new WxtempVo();
		wxtempVo.setOpenId(juserOneKey.getOpenId());
		wxtempVo.setUserName(jOrder.getCustUsername());
		wxtempVo.setKeyword1(jOrder.getStoreName());
		wxtempVo.setKeyword2(jOrder.getLineUpNumber());
		JOrder jojs = new JOrder();
		jojs.setJsUserId(jOrder.getJsUserId());
		JOrder ordreJorder = new JOrder();
		ordreJorder.setJsUserId(jOrder.getJsUserId());
		List<JOrder> jos = jOrderService.selectList(new EntityWrapper<JOrder>(ordreJorder).and("status = 4 OR status = 3").between("create_time", DateFormatUtils.format(new Date(), "yyyy-MM-dd 00:00:00"), DateFormatUtils.format(new Date(), "yyyy-MM-dd 23:59:59")).orderAsc(Collections.singleton("ordernum")));
		if(CollectionUtils.isNotEmpty(jos)){
			int inde = jOrder.getOrdernum() - jos.get(0).getOrdernum();
			if(1 == inde){
				wxtempVo.setTitle("下一个就是你啦，建议提前20分钟到店等候，避免等候时间延长。\r\n如叫号超过5分钟，你的排队将进行延后处理。");
			} else if(0 == inde){
				wxtempVo.setTitle("已经到你啦，技师在店里等你，快来吧～ \r\n如叫号超过5分钟，你的排队将进行延后处理。");
			}else{
				wxtempVo.setTitle("前面排队人数较多，可在附近转转。\r\n当前面等待20分钟时，再到店等候，可减少等候时间哦～");
			}
			
			wxtempVo.setKeyword3(String.valueOf(jOrder.getOrdernum() - jos.get(0).getOrdernum()) + "人");
		}else{
			wxtempVo.setTitle("已经到你啦，技师在店里等你，快来吧～ \r\n如叫号超过5分钟，你的排队将进行延后处理。");
			wxtempVo.setKeyword3("到您了~");
		}
		iJWxtempMsgService.sendPDSUCCESSTXMsg(wxtempVo);
		return WebVo.success(jOrder.getId());
	}
	
	
//	private WxMaPhoneNumberInfo getUserPhoneMinappInfo(String sessionKey,
//	    String encryptedData, String iv,WxMaService wxService){
//		 // 解密
//	    WxMaPhoneNumberInfo phoneNoInfo = wxService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);
//	    return phoneNoInfo;
//	}
	
	
	/**
	 *  排队查询
	 */
	@ApiOperation(value = "订单排队查询 ",httpMethod = "POST",produces="application/json")
	@RequestMapping("/orderQueuing")
	public WebVo<CutomJOrderVo> orderQueuing(@RequestBody JOrder jOrder){
		if(null == jOrder){
			jOrder = new JOrder();
		}
		jOrder.setCustOpenid(ContextUtil.getOpenId());
		jOrder.setStatus("4");
		CutomJOrderVo cjv = new CutomJOrderVo();
		List<JOrder> list = jOrderService.selectList(new EntityWrapper<JOrder>(jOrder).orderDesc(Collections.singleton("create_time ")));
		if(CollectionUtils.isNotEmpty(list)){
			JOrder jOr = new JOrder();
			jOr.setJsUserId(list.get(0).getJsUserId());
			jOr.setStatus("4");
			List<JOrder> jsOrder = jOrderService.selectList(new EntityWrapper<JOrder>(jOr).and("status = 4 OR status = 3").orderDesc(Collections.singleton("create_time ")));
			if(CollectionUtils.isNotEmpty(list)){
				cjv.setPdPresonNum(jsOrder.size());
				cjv.setPdDate(jsOrder.get(0).getCreateTime());
			}
		}
		cjv.setOrders(list);
		return WebVo.success(cjv);
	}
	

	
	@ApiOperation(value= "客户支付完成接口 ",tags = {"客户支付完成接口"},httpMethod = "POST",produces="application/json")
	@RequestMapping("/orderPayEndService")
	public WebVo<String> orderPayEndService(@RequestBody JOrder jOrder){
		log.info("更新订单接口的参数： " + JSON.toJSONString(jOrder));
		if(null == jOrder){
			return WebVo.error("参数不能为空");
		}
		if(StringUtils.isEmpty(jOrder.getId())){
			return WebVo.error("订单ID不能为空");
		}
		if(StringUtils.isEmpty(jOrder.getPayNumber())){
			return WebVo.error("支付流水不能为空");
		}
		JOrder jOrd = jOrderService.selectById(jOrder.getId());
		if(null == jOrd){
			return WebVo.error("订单不存在");
		}
		JOrder update = new JOrder();
		update.setUpdateTime(new Date());
		update.setStatus("1");
		update.setPayNumber(jOrder.getPayNumber());
		update.setPayTime(new Date());
		update.setId(jOrder.getId());
		jOrderService.updateById(update);
		//  TODO  推送支付成功消息给客户
		
		// 默认好评
		JEvaluate jEvaluate = new JEvaluate();
		JUser jUser = jUserService.selectById(jOrd.getJsUserId());
		if(null == jUser){
			return WebVo.error("技师不存在");
		}
		jEvaluate.setUserName(jUser.getUsername());
		JUser wx = new JUser();
		wx.setCustomOpenId(ContextUtil.getOpenId());
		JUser wxUser = jUserService.selectOne(new EntityWrapper<>(wx));
		if(null == wxUser){
			return WebVo.error("客户不存在");
		}
		jEvaluate.setGtDw(5);
		jEvaluate.setJsManyi(5);
		jEvaluate.setServiceYx(5);
		float toatal = (jEvaluate.getServiceYx().floatValue() + jEvaluate.getJsManyi().floatValue() + jEvaluate.getGtDw().floatValue())/3f;
		jEvaluate.setRemark("好评！"); // TODO 随机几条好评规则
		jEvaluate.setAvgNum(new BigDecimal(String.valueOf(toatal)));
		jEvaluate.setWxHeadImg(wxUser.getHeadImg());
		jEvaluate.setWxUserName(wxUser.getUsername());
		jEvaluate.setOpenId(wxUser.getOpenId());
		jEvaluate.setId(StringUtil.getUUID());
		jEvaluate.setCreateTime(new Date());
		jEvaluate.setStatus(1);
		jEvaluateService.insert(jEvaluate);
		return WebVo.success(jEvaluate.getId());
	}
	
	@ApiOperation(value= "客户取消订单 ",tags = {"客户取消订单"},httpMethod = "POST",produces="application/json")
	@RequestMapping("/orderCancel")
	public WebVo<String> orderCancel(@RequestBody JOrder jOrder){
		log.info("更新订单接口的参数： " + JSON.toJSONString(jOrder));
		if(null == jOrder){
			return WebVo.error("参数不能为空");
		}
		if(StringUtils.isEmpty(jOrder.getCancelRemark())){
			return WebVo.error("取消原因不能为空");
		}
		JOrder joder = new JOrder();
		joder.setStatus("4");
		joder.setCustOpenid(ContextUtil.getOpenId());
		JOrder jOrd = jOrderService.selectOne(new EntityWrapper<JOrder>(joder));
		if(null == jOrd){
			return WebVo.error("您没有排队中的订单，不能进行取消操作！");
		}
		JOrder update = new JOrder();
		update.setUpdateTime(new Date());
		update.setStatus("5");
		update.setCancelRemark(jOrder.getCancelRemark());
		update.setId(jOrd.getId());
		jOrderService.updateById(update);
		//   推送排队消息给客户
		WxtempVo wxtempVo = new WxtempVo();
		wxtempVo.setOpenId(jOrd.getCustOpenid());
		wxtempVo.setUserName(jOrd.getCustUsername());
		wxtempVo.setTitle("您的订单取消成功，感谢你选择静享！");
		wxtempVo.setRemark("祝您生活愉快");
		wxtempVo.setKeyword1(jOrd.getServiceName());
		wxtempVo.setKeyword2(DateUtils.formatDate(jOrd.getCreateTime()));
		iJWxtempMsgService.sendCancelOrderMsg(wxtempVo);
		JOrder ordreJorder = new JOrder();
		ordreJorder.setJsUserId(jOrd.getJsUserId());
		List<JOrder> jos = jOrderService.selectList(new EntityWrapper<JOrder>(ordreJorder).and("status = 4 OR status = 3").between("create_time", DateFormatUtils.format(new Date(), "yyyy-MM-dd 00:00:00"), DateFormatUtils.format(new Date(), "yyyy-MM-dd 23:59:59")).orderAsc(Collections.singleton("create_time")));
		if(CollectionUtils.isNotEmpty(jos)){
			Integer total = null;
			for(JOrder updates : jos){
				if(total != null){
					total = total + 1;
					updates.setOrdernum(total);
				}else{
					total = updates.getOrdernum();
				}
				jOrderService.updateById(updates);
			}
		}
		return WebVo.success();
	}
	
	/** 
	 *  客户端查询订单列表
	 */
	@ApiOperation(value = "客户端查询订单列表",httpMethod = "POST",produces="application/json")
	@RequestMapping("/list")
	public WebVo<Page<JOrder>> list(@RequestBody JOrderVo jOrderVo){
		JOrder jOrder1 = new JOrder();
		BeanUtils.copyProperties(jOrderVo, jOrder1);
		jOrder1.setCustOpenid(ContextUtil.getOpenId());
		Page<JOrder> page = new Page<JOrder>(jOrderVo.getCurrent(),jOrderVo.getSize());
		jOrderService.selectPage(page,new EntityWrapper<JOrder>(jOrder1)
				.orderDesc(Collections.singleton("create_time ")));
		List<JOrder> list = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(page.getRecords())){
			List<JOrder> jOrders = page.getRecords();
			for(JOrder jOrder : jOrders){
				JOrder js = new JOrder();
				js.setJsUserId(jOrder.getJsUserId());
				js.setStatus("3");
				JOrder jouser = jOrderService.selectOne(new EntityWrapper<JOrder>(js));
				
				JOrder ordreJorder = new JOrder();
				ordreJorder.setJsUserId(jOrder.getJsUserId());
				List<JOrder> jos = jOrderService.selectList(new EntityWrapper<JOrder>(ordreJorder).between("create_time", DateFormatUtils.format(new Date(), "yyyy-MM-dd 00:00:00"), DateFormatUtils.format(new Date(), "yyyy-MM-dd 23:59:59")).and("status = 4 OR status = 3").orderAsc(Collections.singleton("ordernum")));
				if(CollectionUtils.isNotEmpty(jos)){
					// 有人在上钟
					log.info("jOrder=" + JSON.toJSONString(jOrder) + ",JOS = " + JSON.toJSONString(jos.get(0)));
					int renshu  =  jOrder.getOrdernum() - jos.get(0).getOrdernum();
					int rs = renshu;
					if(null != jouser){
						renshu = renshu- 1;
						if(renshu < 0 ){
							renshu = 0;
						}
						
						int fen = (int) ((jos.get(0).getServiceTime() + 15) - DateUtils.getMinuteCompan(DateUtils.convertDateToLDT(
								jouser.getUpdateTime()),DateUtils.convertDateToLDT(new Date())));
						if(jOrder.getServiceTime() > 60){
							int xs = 0;
							if(fen > 60){
								xs =  (fen/60);
								if(xs > 1){
									fen = fen - (xs * 60);
									renshu = renshu + xs ; 
								}else if(xs == 1){
									renshu = renshu + xs ;
								}
							}
						}
						
						if(fen > 0 ){
							jOrder.setDdTimeLong(renshu + "小时" + fen );
						}else{
							if(0 == renshu || renshu < 0){
								jOrder.setDdTimeLong("0");
							}else{
								jOrder.setDdTimeLong(renshu + "小时" +   "0" );
							}
						}
						
					}else{
						if(0 == renshu || renshu < 0){
							jOrder.setDdTimeLong("0");
						}else{
							jOrder.setDdTimeLong(renshu + "小时" +   "0" );
						}
						
					}
					jOrder.setDdRenNum(rs);
					
				}else{
					// 技师没有人在上钟
					jOrder.setDdTimeLong("0");
					jOrder.setDdRenNum(0);
				}
				
				list.add(jOrder);
			}
			page.getRecords().clear();
			page.setRecords(list);
		}
		return WebVo.success(page);
	}
	
	
	@ApiOperation(value = "客户端查询未支付订单列表",httpMethod = "POST",produces="application/json")
	@RequestMapping("/selectOrderNotPayCount")
	public WebVo<String> selectOrderNotPayCount(@RequestBody JOrderVo jOrderVo){
		JOrder jOrder = new JOrder();
		jOrder.setStatus("2");
		jOrder.setCustOpenid(ContextUtil.getOpenId());
		if(jOrderService.selectCount(new EntityWrapper<JOrder>(jOrder)) > 0){
			return WebVo.error("您有未支付订单未完成！"); 
		}
		return WebVo.success();
	}
	
	@ApiOperation(value = "查询排队时间",httpMethod = "POST",produces="application/json")
	@RequestMapping("/selectOrderpdTime")
	public WebVo<CutomJOrderVo> selectOrderpdTime(@RequestBody JOrder jOrder){
		jOrder = new JOrder();
		jOrder.setStatus("4");
		jOrder.setCustOpenid(ContextUtil.getOpenId());
		JOrder jc = jOrderService.selectOne(new EntityWrapper<JOrder>(jOrder));
		if(null == jc){
			return WebVo.error("订单不存在！"); 
		}
		CutomJOrderVo cjd = new CutomJOrderVo();
		JOrder js = new JOrder();
		js.setJsUserId(jc.getJsUserId());
		js.setStatus("3");
		JOrder jsOrder = jOrderService.selectOne(new EntityWrapper<JOrder>(js));
		if(null != jsOrder){
			js.setStatus("4");
			int total = jOrderService.selectCount(new EntityWrapper<JOrder>(js));
			if(total > 0){
				int fen = (int) (jsOrder.getServiceTime() - DateUtils.getMinuteCompan(DateUtils.convertDateToLDT(
						jsOrder.getUpdateTime()),DateUtils.convertDateToLDT(new Date())));
				if(fen > 0 ){
					cjd.setPdTime((total - 1) + "小时" + fen + "分");
				}else{
					cjd.setPdTime((total - 1) + "小时");
				}
			}
		}else{
			List<JOrder> total = jOrderService.selectList(new EntityWrapper<JOrder>(js).orderDesc(Collections.singleton("create_time ")));
			if(CollectionUtils.isNotEmpty(total)){
				if(!ContextUtil.getOpenId().equals(total.get(0).getCustOpenid())){
					int fen = (int) (total.get(0).getServiceTime() - DateUtils.getMinuteCompan(DateUtils.convertDateToLDT(
							total.get(0).getUpdateTime()),DateUtils.convertDateToLDT(new Date())));
					cjd.setPdTime((total.size() - 1) + "小时" + fen + "分钟");
				}
			}else{
				cjd.setPdTime(0 + "小时");
			}
			
		}
		return WebVo.success(cjd);
	}
	
	
	
}
