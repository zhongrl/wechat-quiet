package com.quiet.live.hall.bi.rest;


import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import com.quiet.live.hall.entity.JCoupon;
import com.quiet.live.hall.service.JCouponService;
import com.quiet.live.hall.utils.string.StringUtil;
import com.quiet.live.hall.utils.web.WebVo;
import com.quiet.live.hall.vo.JCouponVo;

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
@Api(value="BI优惠券 ",tags={"优惠券接口"})
@RestController
@RequestMapping("/bi/jCoupon")
@CrossOrigin
public class JCouponController {
	@Autowired
	JCouponService jCouponService;
	
	@ApiOperation(value = "BI所有优惠券 ",httpMethod = "POST",produces="application/json")
	@RequestMapping("/list")
	public WebVo<Page<JCoupon>> list(@RequestBody JCouponVo JCouponVo){
		JCoupon JCoupon = new JCoupon();
		if(!StringUtils.isEmpty(JCouponVo.getDelFlag())){
			JCoupon.setDelFlag(JCouponVo.getDelFlag());
		}
		if(!StringUtils.isEmpty(JCouponVo.getCouponId())){
			JCoupon.setId(JCouponVo.getCouponId());
		}
		if(null != JCouponVo.getStartTime() && null != JCouponVo.getEndTime()){
			JCoupon.setStartTime(JCouponVo.getStartTime());
			JCoupon.setEndTime(JCouponVo.getEndTime());
		}
		Page<JCoupon> page = new Page<JCoupon>(JCouponVo.getCurrent(),JCouponVo.getSize());
		return WebVo.success(jCouponService.selectPage(page,new EntityWrapper<JCoupon>(JCoupon)
				.orderDesc(Collections.singleton("create_time "))));
	}
	
	@ApiOperation(value = "BI所有优惠券 ",httpMethod = "POST",produces="application/json")
	@RequestMapping("/selectById")
	public WebVo<JCoupon> selectById(@RequestBody JCoupon JCoupon){
		if(StringUtils.isEmpty(JCoupon.getId())){
			return WebVo.error("id 不能为空!");
		}
		return WebVo.success(jCouponService.selectById(JCoupon.getId()));
		
	}
	
	
	@ApiOperation(value= "BI新增或者修改优惠券 ",tags = {"修改带ID， 保存不需要带ID"},httpMethod = "POST",produces="application/json")
	@RequestMapping("/save")
	public WebVo<String> save(@RequestBody JCoupon jCoupon){
		if(null == jCoupon){
			return WebVo.error("参数不能为空");
		}
		if(StringUtils.isEmpty(jCoupon.getTitle())){
			return WebVo.error("主题title参数不能为空");
		}
		if(StringUtils.isEmpty(jCoupon.getRemake())){
			return WebVo.error("描述remake参数不能为空");
		}
		if(StringUtils.isEmpty(jCoupon.getCouponType())){
			return WebVo.error("优惠券类型参数不能为空");
		}
		if(null == jCoupon.getPrice() || 0 == jCoupon.getPrice().doubleValue()){
			return WebVo.error("价格price参数不能为空并且不能等0");
		}
		if(null == jCoupon.getStartTime()){
			return WebVo.error("开始时间不能为空");
		}
		if(null == jCoupon.getEndTime()){
			return WebVo.error("结束时间不能为空");
		}
		if(jCoupon.getStartTime().after(jCoupon.getEndTime())){
			return WebVo.error("开始时间不能大于结束时间");
		}
		if(StringUtils.isEmpty(jCoupon.getId())){
			jCoupon.setId(StringUtil.getUUID());
			jCoupon.setCreateTime(new Date());
			jCouponService.insert(jCoupon);
		}else{
			JCoupon jb = jCouponService.selectById(jCoupon.getId());
			if(null != jb){
				jCouponService.updateById(jCoupon);
			}else{
				return WebVo.error("该优惠券不存在");
			}
		}
		return WebVo.success();
	}
	
	@ApiOperation(value = "BI 删除优惠券  " , tags = {"批量删除多个以逗号隔开"},httpMethod = "POST",produces="application/json")
	@RequestMapping("/delete")
	public WebVo<String> delete(@RequestBody JCoupon jCoupon){
		if(null == jCoupon){
			return WebVo.error("参数不能为空");
		}
		if(StringUtils.isNoneBlank(jCoupon.getId())){
			if(jCoupon.getId().contains(",")){
				String[] str = jCoupon.getId().split(",");
				List<String> lists =  Lists.newArrayList();
				for (String string : str) {
					JCoupon jb = jCouponService.selectById(jCoupon.getId());
					if(null != jb){
						lists.add(string);
					}else{
						return WebVo.error("该优惠券不存在,请刷新页面");
					}
				}
				jCouponService.deleteBatchIds(lists);
			}else{
				JCoupon jb = jCouponService.selectById(jCoupon.getId());
				if(null != jb){
					jCouponService.deleteById(jb.getId());
				}else{
					return WebVo.error("该优惠券不存在,请刷新页面");
				}
			}
		}
		return WebVo.success();
	}
	
}

