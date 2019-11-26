package com.quiet.live.hall.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.quiet.live.hall.entity.JCoupon;
import com.quiet.live.hall.entity.JCouponDetail;
import com.quiet.live.hall.entity.JUser;
import com.quiet.live.hall.mapper.JCouponDetailMapper;
import com.quiet.live.hall.service.JCouponDetailService;
import com.quiet.live.hall.service.JCouponService;
import com.quiet.live.hall.service.JUserService;
import com.quiet.live.hall.utils.string.StringUtil;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author william zhong123
 * @since 2019-08-07
 */
@Service
public class JCouponDetailServiceImpl extends ServiceImpl<JCouponDetailMapper, JCouponDetail> implements JCouponDetailService {

	@Autowired
	JUserService jUserService;
	
	@Autowired
	JCouponService jCouponService;
	
	@Override
	public void checkCoupon() {
		JCouponDetail jCouponDetail = new JCouponDetail();
		jCouponDetail.setDelFlag("2");
		List<JCouponDetail> list = this.selectList(new EntityWrapper<JCouponDetail>(jCouponDetail));
		if(!CollectionUtils.isEmpty(list)){
			list.stream().forEach(jc ->{
				if(jc.getEndTime().before(new Date())){
					jCouponDetail.setDelFlag("3");
					this.updateById(jCouponDetail);
				}
			});
		}
		
	}

	@Override
	public void paiFaCoupon() {
		JUser jUser = new JUser();
		jUser.setUserType("4");
		List<JUser> list = jUserService.selectList(new EntityWrapper<JUser>(jUser));
		if(!CollectionUtils.isEmpty(list)){
			for(JUser juser : list){
				JCouponDetail jCouponDetail = new JCouponDetail();
				jCouponDetail.setSyOpenid(juser.getOpenId());
				jCouponDetail.setDelFlag("4");
				int count = this.selectCount(new EntityWrapper<JCouponDetail>(jCouponDetail));
				if(count == 0){
					JCoupon jCoupon= new JCoupon();
					jCoupon.setCouponType("LAXIN");
					jCoupon.setDelFlag("1");
					JCoupon jCoup =jCouponService.selectOne(new EntityWrapper<JCoupon>(jCoupon));
					if(null == jCoup){
						continue;
					}
					jCouponDetail.setTitle(jCoup.getTitle());
					jCouponDetail.setPrice(jCoup.getPrice());
					jCouponDetail.setStartTime(new Date());
					jCouponDetail.setEndTime(jCoup.getEndTime());
					jCouponDetail.setCreateTime(new Date());
					jCouponDetail.setDelFlag("2");
					jCouponDetail.setId(StringUtil.getUUID());
					jCouponDetail.setSyOpenid(juser.getOpenId());
					this.insert(jCouponDetail);
				}
			}
		}
		
	}
	
	

}
