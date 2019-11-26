package com.quiet.live.hall.service;

import com.quiet.live.hall.entity.JCouponDetail;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author william zhong123
 * @since 2019-08-07
 */
public interface JCouponDetailService extends IService<JCouponDetail> {

	public void checkCoupon();
	
	public void paiFaCoupon();
}
