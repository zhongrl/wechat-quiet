package com.quiet.live.hall.bi.rest;


import java.math.BigDecimal;
import java.util.Collections;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.quiet.live.hall.config.RedisComponent;
import com.quiet.live.hall.entity.JOrder;
import com.quiet.live.hall.service.JCouponDetailService;
import com.quiet.live.hall.service.JEvaluateService;
import com.quiet.live.hall.service.JOrderService;
import com.quiet.live.hall.service.JServiceService;
import com.quiet.live.hall.service.JStoreService;
import com.quiet.live.hall.service.JUserService;
import com.quiet.live.hall.utils.web.WebVo;
import com.quiet.live.hall.vo.BiOrderVo;
import com.quiet.live.hall.vo.JOrderVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

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
@RequestMapping("/bi/jOrder")
@CrossOrigin
public class JOrderController {

	
	
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
	
	/** 
	 *  BI 查询订单 加统金额功能
	 */
	@ApiOperation(value = "BI查询订单 加统金额功能",httpMethod = "POST",produces="application/json")
	@RequestMapping("/list")
	public WebVo<Page<JOrder>> list(@RequestBody JOrderVo jOrderVo){
		JOrder jOrder = new JOrder();
		
		EntityWrapper<JOrder> ew = new EntityWrapper<JOrder>(jOrder);
		if(!StringUtils.isEmpty(jOrderVo.getOptSource())){
			ew.like("optSource", jOrderVo.getOptSource());
		}
		if(null != jOrderVo){
			if(null != jOrderVo.getStartTime() && null != jOrderVo.getEndTime()){
				if(!jOrderVo.getStartTime().before(jOrderVo.getEndTime())){
					return WebVo.error("查询时间开始时间不能大于结束时间");
				}else{
					ew.between("create_time", jOrderVo.getStartTime(), jOrderVo.getEndTime());
				}
			}
			BeanUtils.copyProperties(jOrderVo, jOrder);
		}
		if(!StringUtils.isEmpty(jOrderVo.getStatus())){
			jOrder.setStatus(jOrderVo.getStatus());
		}
		ew.setEntity(jOrder);
		Page<JOrder> page = new Page<JOrder>(jOrderVo.getCurrent(),jOrderVo.getSize());
		jOrderService.selectPage(page,ew
				.orderDesc(Collections.singleton("create_time ")));
		return WebVo.success(page);
	}
	
	

	/** 
	 *  BI 查询订单 加统金额功能
	 */
	@ApiOperation(value = "BI查询订单 加统金额功能",httpMethod = "POST",produces="application/json")
	@RequestMapping("/orderYingYee")
	public WebVo<BiOrderVo> orderYingYee(@RequestBody JOrderVo jOrderVo){
		JOrder jOrder = new JOrder();
		EntityWrapper<JOrder> ew = new EntityWrapper<JOrder>(jOrder);
		if(!StringUtils.isEmpty(jOrderVo.getOptSource())){
			ew.like("optSource", jOrderVo.getOptSource());
		}
		if(null != jOrderVo){
			if(null != jOrderVo.getStartTime() && null != jOrderVo.getEndTime()){
				if(!jOrderVo.getStartTime().before(jOrderVo.getEndTime())){
					return WebVo.error("查询时间开始时间不能大于结束时间");
				}else{
					ew.between("create_time", jOrderVo.getStartTime(), jOrderVo.getEndTime());
				}
				
			}
			BeanUtils.copyProperties(jOrderVo, jOrder);
		}
		
		BiOrderVo biOrderVo = jOrderService.selectOrderTotal(jOrder);
		if(null == biOrderVo){
			biOrderVo = new BiOrderVo();
			biOrderVo.setTotalOrderNum(0);
			biOrderVo.setTotalSalar(new BigDecimal("0"));
		}
		return WebVo.success(biOrderVo);
	}
	
}

