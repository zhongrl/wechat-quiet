package com.quiet.live.hall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.quiet.live.hall.entity.JEvaluate;
import com.quiet.live.hall.entity.JUser;
import com.quiet.live.hall.mapper.JUserMapper;
import com.quiet.live.hall.service.JEvaluateService;
import com.quiet.live.hall.service.JUserService;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author william zhong123
 * @since 2019-08-07
 */
@Service
@Slf4j
public class JUserServiceImpl extends ServiceImpl<JUserMapper, JUser> implements JUserService {

	@Autowired
	JEvaluateService jEvaluateService;
	
	@Override
	public void jsTotal() {
		JUser jUser = new JUser();
		jUser.setUserType("3");
		EntityWrapper<JUser> enti = new EntityWrapper<JUser>(jUser);
		List<JUser> list = this.selectList(enti);
		if(CollectionUtils.isNotEmpty(list)){
			int index = 1;
			for(JUser ju:list){
				JUser juser = new JUser();
				juser.setId(ju.getId());
				JEvaluate je = new JEvaluate();
				je.setUserId(ju.getId());
				je.setStatus(1);
				EntityWrapper<JEvaluate> Jealte  = new EntityWrapper<JEvaluate>(je);
				List<JEvaluate> jlist = jEvaluateService.selectList(Jealte);
				if(null != ju.getPinglunnum() && ju.getPinglunnum() == jlist.size()){
					continue;
				}
				if(CollectionUtils.isNotEmpty(jlist)){
					int tota = 0 ;
					for(JEvaluate jelte : jlist){
						int avg = 0;
						if(null !=  jelte.getJsManyi()){
							avg = avg + jelte.getJsManyi();
						}
						if(null !=  jelte.getGtDw()){
							avg = avg + jelte.getGtDw();						
						}
						if(null !=  jelte.getServiceYx()){
							avg = avg + jelte.getServiceYx();
						}
						if(avg > 0 ){
							avg = avg /3;
						}
						tota = tota + avg;
					}
					if(tota > 0){
						tota = tota / jlist.size();
						juser.setJstotlfen(tota);
					}
				}else{
					juser.setJstotlfen(0);
				}
				juser.setPinglunnum(jlist.size());
				this.updateById(juser);
				log.info("更新成功平均分 : 第" + index + "个 ： " + JSON.toJSONString(juser));
				index ++ ;
			}
		}
		
	}

}
