package com.quiet.live.hall.bi.rest;


import java.util.Collections;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.quiet.live.hall.entity.JCouponDetail;
import com.quiet.live.hall.service.JCouponDetailService;
import com.quiet.live.hall.service.JCouponService;
import com.quiet.live.hall.utils.web.WebVo;
import com.quiet.live.hall.vo.JCouponDetailVo;

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
@Api(value="BI用户优惠券 ",tags={"用户优惠券接口"})
@RestController
@RequestMapping("/bi/jCouponDetail")
@CrossOrigin
public class JCouponDetailController {

	@Autowired
	JCouponDetailService jCouponDetailService;
	
	@Autowired
	JCouponService jCouponService;
	
	
	@ApiOperation(value = "BI所有优惠券 ",httpMethod = "POST",produces="application/json")
	@RequestMapping("/list")
	public WebVo<Page<JCouponDetail>> list(@RequestBody JCouponDetailVo jCouponDetailVo){
		JCouponDetail jCouponDetail = new JCouponDetail();
		if(!StringUtils.isEmpty(jCouponDetailVo.getDelFlag())){
			jCouponDetail.setDelFlag(jCouponDetailVo.getDelFlag());
		}
		if(!StringUtils.isEmpty(jCouponDetailVo.getCouponId())){
			jCouponDetail.setId(jCouponDetailVo.getCouponId());
		}
		if(null != jCouponDetailVo.getStartTime() && null != jCouponDetailVo.getEndTime()){
			jCouponDetail.setStartTime(jCouponDetailVo.getStartTime());
			jCouponDetail.setEndTime(jCouponDetailVo.getEndTime());
		}
		Page<JCouponDetail> page = new Page<JCouponDetail>(jCouponDetailVo.getCurrent(),jCouponDetailVo.getSize());
		return WebVo.success(jCouponDetailService.selectPage(page,new EntityWrapper<JCouponDetail>(jCouponDetail)
				.orderDesc(Collections.singleton("create_time "))));
	}
	
}

