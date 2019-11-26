package com.quiet.live.hall.custom.rest;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.quiet.live.hall.config.ContextUtil;
import com.quiet.live.hall.config.RedisComponent;
import com.quiet.live.hall.entity.JEvaluate;
import com.quiet.live.hall.entity.JOrder;
import com.quiet.live.hall.entity.JUser;
import com.quiet.live.hall.service.IJWxtempMsgService;
import com.quiet.live.hall.service.JCouponDetailService;
import com.quiet.live.hall.service.JEvaluateService;
import com.quiet.live.hall.service.JOrderService;
import com.quiet.live.hall.utils.DateUtils;
import com.quiet.live.hall.utils.IPUtil;
import com.quiet.live.hall.utils.PriceUtil;
import com.quiet.live.hall.utils.string.StringUtil;
import com.quiet.live.hall.utils.web.WebVo;
import com.quiet.live.hall.vo.WxtempVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api(value = "微信支付接口", tags = { "微信支付接口" })
@RestController
@RequestMapping("/order")
@Slf4j
@CrossOrigin
public class PayController {

	@Autowired
	WxPayService wxPayService;
	
	@Autowired
	JOrderService jOrderService;
	
	@Autowired
	IJWxtempMsgService iJWxtempMsgService;
	
	@Autowired
	JEvaluateService jEvaluateService;
	
	@Autowired
	JCouponDetailService jCouponDetailService;
	
	@Autowired
	RedisComponent redisComponent;
	
	@ApiOperation(value= "查询待支付订单接口 ",tags = {"客户支付完成接口"},httpMethod = "POST",produces="application/json")
	@RequestMapping("/selectNotPayJOrder")
	public WebVo<JOrder> selectNotPayJOrder(@RequestBody JOrder jOrder) throws UnsupportedEncodingException{
		JOrder joder = new JOrder();
		JOrder jod = null;
		joder.setStatus("2");
		joder.setCustOpenid(ContextUtil.getOpenId());
		jod = jOrderService.selectOne(new EntityWrapper<JOrder>(joder));
		if(null == jod){
			return WebVo.error("您没有待支付的订单，不能进行支付！如果有，请技师完成服务再进行支付");
		}
		if(com.quiet.live.hall.utils.string.StringUtil.isBase64(jod.getCustUsername())){
			jod.setCustUsername(new String(org.apache.commons.codec.binary.Base64.decodeBase64(jod.getCustUsername()),"UTF-8"));
	     }
		return WebVo.success(jod);
	}
	

	@ApiOperation(value = "统一下单，并组装所需支付参数",tags = {"客户支付完成接口"},httpMethod = "POST",produces="application/json")
	@RequestMapping("/createOrderParam")
	public WebVo<WxPayMpOrderResult> createOrder(@RequestBody JOrder jOrder,HttpServletRequest request) {
		log.info("支付参数： " + JSON.toJSONString(jOrder));
		try {
//			if(null == jOrder || StringUtil.isEmpty(jOrder.getId())){
//				return WebVo.error("订单ID不能为空");
//			}
//			JOrder jod = jOrderService.selectById(jOrder.getId());
//			if(null == jod){
//				return WebVo.error("订单订单不存在");
//			}
			JOrder joder = new JOrder();
			joder.setStatus("2");
			joder.setCustOpenid(ContextUtil.getOpenId());
			JOrder jod = jOrderService.selectOne(new EntityWrapper<JOrder>(joder));
			if(null == jod){
				return WebVo.error("您没有待支付的订单，不能进行支付！如果有，请技师完成服务再进行支付");
			}
//			if(!StringUtil.equals(jod.getStatus(), "2")){
//				if(Integer.parseInt(jod.getStatus()) < 2){
//					return WebVo.error("订单已完成");
//				}
//				if(Integer.parseInt(jod.getStatus()) > 2){
//					return WebVo.error("技师没有完成订单");
//				}
//				return WebVo.error("订单状态不对,请联系门店经理");
//			}
			WxPayUnifiedOrderRequest orderRequest = new WxPayUnifiedOrderRequest();   //商户订单类
	        orderRequest.setBody(jod.getServiceName());
	        orderRequest.setOpenid(jod.getCustOpenid());
	        //元转成分
	        orderRequest.setTotalFee(PriceUtil.changeY2F(jod.getPrice()));   //注意：传入的金额参数单位为分
	        //outTradeNo  订单号
	        JOrder jO = new JOrder();
	        String orderNumer = DateUtils.formatDate(new Date(), DateUtils.DATE_FORMAT_DATETIME_TIME_NEW);
	        int total = jOrderService.selectCount(new EntityWrapper<JOrder>(jO));
	        orderNumer = orderNumer +""+ String.valueOf(total == 0 ? 1:total);
	        jod.setOrderNumber("1" + StringUtil.stringFormNumer_18(1, Long.parseLong(orderNumer)));
	        orderRequest.setOutTradeNo(jod.getOrderNumber());
	        JOrder jodupdate = new JOrder();
	 	    jodupdate.setId(jod.getId());
	 	    jodupdate.setOrderNumber(jod.getOrderNumber());
	 	    jOrderService.updateById(jodupdate);
	        //tradeType 支付方式
	        orderRequest.setTradeType("JSAPI");
	        //用户IP地址
	        log.info("IP地址： " + IPUtil.getIP(request));
	        orderRequest.setSpbillCreateIp(IPUtil.getIP(request)); 
			WxPayMpOrderResult wxPayMpOrderResult = (WxPayMpOrderResult)this.wxPayService.createOrder(orderRequest);
			log.info("支付参数： " + JSON.toJSONString(wxPayMpOrderResult));
			return WebVo.success(wxPayMpOrderResult);
		} catch (WxPayException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("创建订单失败: ",e);
			return WebVo.error("创建订单失败");
		}
	}
	
	 @ApiOperation(value = "支付回调")
	 @RequestMapping("/notify/order")
	  public  String parseOrderNotifyResult(@RequestBody String xmlData) throws WxPayException {
	    final WxPayOrderNotifyResult notifyResult = this.wxPayService.parseOrderNotifyResult(xmlData);
	    log.info("微信回调返回的参数 ： " + JSON.toJSONString(notifyResult));
	    if(StringUtil.equals(notifyResult.getReturnCode(), "SUCCESS")){
	    	 JOrder jo = new JOrder();
	 	    jo.setOrderNumber(notifyResult.getOutTradeNo());
	 	    JOrder jodupda = jOrderService.selectOne(new EntityWrapper<>(jo));
	 	    if(null == jodupda){
	 	    	return WxPayNotifyResponse.fail("订单不存在");
	 	    }
	 	    if(StringUtil.equals("1", jodupda.getStatus())){
	 	    	return WxPayNotifyResponse.success("已经回调成功");
	 	    }
	 	    JOrder jodupdate = new JOrder();
	 	    jodupdate.setUpdateTime(new Date());
	 	    jodupdate.setStatus("1");
	 	    jodupdate.setPayTime(new Date());
	 	    jodupdate.setPayNumber(notifyResult.getOutTradeNo());
	 	    jodupdate.setId(jodupda.getId());
	 	    jodupdate.setOptSource("线上支付");
	 	    jOrderService.updateById(jodupdate);

			// 默认好评
			JEvaluate jEvaluate = new JEvaluate();
			jEvaluate.setUserName(jodupda.getJsUsername());
			JUser wx = new JUser();
			wx.setCustomOpenId(jodupda.getCustOpenid());
			jEvaluate.setGtDw(5);
			jEvaluate.setJsManyi(5);
			jEvaluate.setServiceYx(5);
			float toatal = (jEvaluate.getServiceYx().floatValue() + jEvaluate.getJsManyi().floatValue() + jEvaluate.getGtDw().floatValue())/3f;
			jEvaluate.setRemark("好评！"); // TODO 随机几条好评规则
			jEvaluate.setAvgNum(new BigDecimal(String.valueOf(toatal)));
			jEvaluate.setWxHeadImg(jodupda.getCustHeadImg());
			jEvaluate.setWxUserName(jodupda.getCustUsername());
			jEvaluate.setOpenId(ContextUtil.getOpenId());
			jEvaluate.setId(com.quiet.live.hall.utils.string.StringUtil.getUUID());
			jEvaluate.setCreateTime(new Date());
			jEvaluate.setStatus(1);
			jEvaluate.setOrderId(jodupdate.getId());
			jEvaluate.setUserId(jodupda.getJsUserId());
			jEvaluateService.insert(jEvaluate);
	 	    
	 	//   推送排队消息给客户
			WxtempVo wxtempVo = new WxtempVo();
			wxtempVo.setOpenId(jodupda.getCustOpenid());
			wxtempVo.setUserName(jodupda.getCustUsername());
			wxtempVo.setKeyword1("¥ " + jodupda.getPrice().toString());
			wxtempVo.setKeyword2(jodupda.getStoreName());
			wxtempVo.setKeyword3(DateUtils.formatDate(jodupda.getCreateTime()));
			iJWxtempMsgService.sendZFSUCCESSTZMsg(wxtempVo);
	 	    // TODO 根据自己业务场景需要构造返回对象
	 	    return WxPayNotifyResponse.success("成功");
	    }
	    return WxPayNotifyResponse.fail("失败");
	  }
	
}
