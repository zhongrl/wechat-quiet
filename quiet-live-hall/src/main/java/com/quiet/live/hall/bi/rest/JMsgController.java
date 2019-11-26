package com.quiet.live.hall.bi.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.quiet.live.hall.config.RedisComponent;
import com.quiet.live.hall.entity.JOrder;
import com.quiet.live.hall.service.IJWxtempMsgService;
import com.quiet.live.hall.service.JCouponDetailService;
import com.quiet.live.hall.service.JEvaluateService;
import com.quiet.live.hall.service.JOrderService;
import com.quiet.live.hall.service.JServiceService;
import com.quiet.live.hall.service.JStoreService;
import com.quiet.live.hall.service.JUserService;
import com.quiet.live.hall.utils.DateUtils;
import com.quiet.live.hall.utils.web.WebVo;
import com.quiet.live.hall.vo.WxtempVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author william zhong123
 * @since 2019-08-07
 */
@Api(value="BI订单接口 ",tags={"BI评论接口"},description="订单接口")
@RestController
@RequestMapping("/bi/msg")
@Slf4j
@CrossOrigin
public class JMsgController {

	
	
	@Value("${service.hour}")
	private Integer serviceHour;
	
	@Autowired
	JOrderService jOrderService;
	

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
		WxtempVo wxtempVo = new WxtempVo();
		wxtempVo.setOpenId(jodupda.getCustOpenid());
		wxtempVo.setTitle("尊敬"+jodupda.getCustUsername()+"您好！点击“详情”,确认订单支付吧~");
		wxtempVo.setKeyword1(jodupda.getStoreName());
		wxtempVo.setKeyword2(DateUtils.formatDate(jodupda.getUpdateTime()));
		wxtempVo.setKeyword3(jodupda.getPrice().toString());
		wxtempVo.setUserName(jodupda.getCustUsername());
		wxtempVo.setUserId(jodupda.getCustUserId());;
		iJWxtempMsgService.sendDAIZFTXMsg(wxtempVo);
		return WebVo.success();
	}
	
}

