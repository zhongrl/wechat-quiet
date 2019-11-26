package com.quiet.live.hall.bi.rest;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.quiet.live.hall.config.ContextUtil;
import com.quiet.live.hall.config.RedisComponent;
import com.quiet.live.hall.entity.JEvaluate;
import com.quiet.live.hall.entity.JService;
import com.quiet.live.hall.entity.JStore;
import com.quiet.live.hall.entity.JUser;
import com.quiet.live.hall.service.JEvaluateService;
import com.quiet.live.hall.service.JServiceService;
import com.quiet.live.hall.service.JStoreService;
import com.quiet.live.hall.service.JUserService;
import com.quiet.live.hall.utils.PropUtil;
import com.quiet.live.hall.utils.base.MD5Util;
import com.quiet.live.hall.utils.location.BaiduApiLocation;
import com.quiet.live.hall.utils.string.StringUtil;
import com.quiet.live.hall.utils.web.WebVo;
import com.quiet.live.hall.vo.JUserVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api(value="BI接口",tags={"BI接口"},description = "BI接口")
@RestController
@RequestMapping("/bi")
@Slf4j
@CrossOrigin
public class BiController {

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
	BaiduApiLocation baiduApiLocation;
	
	@ApiOperation(value = "获取所有用户信息",httpMethod = "POST",produces="application/json")
	@RequestMapping("/list")
	public WebVo<Page<JUser>> findUsers(@RequestBody JUserVo jUserVo) throws UnsupportedEncodingException{
		JUser jUser = new JUser();
		if(!StringUtils.isEmpty(jUserVo.getUsername())){
//			jUser.setUsername(jUser.getUsername());
			jUser.setUsername(String.valueOf(org.apache.commons.codec.binary.Base64.encodeBase64String(jUser.getUsername().getBytes("UTF-8"))));
		}
		if(!StringUtils.isEmpty(jUserVo.getMobile())){
			jUser.setId(jUserVo.getMobile());
		}
		if(StringUtils.equals(ContextUtil.getUserType(), "2")){
			jUser.setStoreId(ContextUtil.getStoreId());
		}
		Page<JUser> page = new Page<JUser>(jUserVo.getCurrent(),jUserVo.getSize());
		EntityWrapper<JUser> enti =  new EntityWrapper<JUser>(jUser);
		if(!StringUtils.isEmpty(jUserVo.getUsername())){
			enti.like("username", jUserVo.getUsername());
		}
		return WebVo.success(jUserService.selectPage(page,enti.and(" user_type not in ('3','4') ").orderDesc(Collections.singleton("create_time "))));
	}
	
	@ApiOperation(value = "获取所有技师信息",httpMethod = "POST",produces="application/json")
	@RequestMapping("/findJs")
	public WebVo<Page<JUser>> findJs(@RequestBody JUserVo jUserVo) throws UnsupportedEncodingException{
		JUser jUser = new JUser();
		EntityWrapper<JUser> entitywrapper = new EntityWrapper<JUser>();
		if(!StringUtils.isEmpty(jUserVo.getUsername())){
//			jUser.setUsername(jUser.getUsername());
			jUser.setUsername(String.valueOf(org.apache.commons.codec.binary.Base64.encodeBase64String(jUser.getUsername().getBytes("UTF-8"))));
		}
		if(!StringUtils.isEmpty(jUserVo.getMobile())){
			entitywrapper.like("mobile", jUserVo.getMobile());
		}
		if(StringUtils.equals(ContextUtil.getUserType(), "2")){
			jUser.setStoreId(ContextUtil.getStoreId());
		}else{
			if(!StringUtils.isEmpty(jUserVo.getStoreId())){
				jUser.setStoreId(jUserVo.getStoreId());
			}
		}
		
		jUser.setUserType("3");
		jUser.setDelFlag("1");
		entitywrapper.setEntity(jUser);
		Page<JUser> page = new Page<JUser>(jUserVo.getCurrent(),jUserVo.getSize());
		jUserService.selectPage(page,entitywrapper.orderDesc(Collections.singleton("create_time ")));
		return WebVo.success(page);
	}
	
	@ApiOperation(value = "获取单个技师信息",httpMethod = "POST",produces="application/json")
	@RequestMapping("/js/selectByJs")
	public WebVo<JUser> selectByJs(@RequestBody JUserVo jUserVo){
		if(null == jUserVo){
			return WebVo.error("参数不能为空");
		}
		if(StringUtils.isEmpty(jUserVo.getId())){
			return WebVo.error("id参数不能为空");
		}
		return WebVo.success(jUserService.selectById(jUserVo.getId()));
	}
	
	@ApiOperation(value= "BI新增或者修改技师或者管理员 ",tags = {"修改带ID， 保存不需要带ID"},httpMethod = "POST",produces="application/json")
	@RequestMapping("/jUser/save")
	public WebVo<String> save(@RequestBody JUser jUser) throws UnsupportedEncodingException{
		log.info(JSON.toJSONString(jUser));
		if(null == jUser){
			return WebVo.error("参数不能为空");
		}
		if(StringUtils.isEmpty(jUser.getMobile()) || jUser.getMobile().length() != 11){
			return WebVo.error("手机号不能为空或者格式不正确");
		}else{
			if(StringUtils.equals(jUser.getUserType(), "3") &&StringUtils.isEmpty(jUser.getId())){
				JUser jUs = new JUser();
				jUs.setMobile(jUser.getMobile());
				jUs.setUserType("3");
				int total = jUserService.selectCount(new EntityWrapper<>(jUs));
				if(total > 0){
					return WebVo.error("账户已经存在!");
				}
			}
		}
		if(StringUtils.isEmpty(jUser.getUsername())){
			return WebVo.error("名字不能为空!");
		}else{
//			jUser.setUsername(jUser.getUsername());
			jUser.setUsername(String.valueOf(org.apache.commons.codec.binary.Base64.encodeBase64String(jUser.getUsername().getBytes("UTF-8"))));
		}
		if(StringUtils.isEmpty(jUser.getAccount())){
			return WebVo.error("账户不能为空!");
		}else{
			if(StringUtils.isEmpty(jUser.getId())){
				JUser jUs = new JUser();
				jUs.setAccount(jUser.getAccount());
				int total = jUserService.selectCount(new EntityWrapper<>(jUs));
				if(total > 0){
					return WebVo.error("账户已经存在!");
				}
			}
			
		}
		if(!StringUtils.equals(jUser.getUserType(), "4")){
			
			jUser.setAccount(jUser.getMobile());
			
			
		}
		if(StringUtils.equals(jUser.getUserType(), "3")){
			if(StringUtils.isEmpty(jUser.getHeadImg())){
				return WebVo.error("头像不能为空");
			}
			if(null == jUser.getPrice() || jUser.getPrice().compareTo(BigDecimal.ZERO) == 0  || jUser.getPrice().doubleValue() < 0){
				return WebVo.error("价格不能为空，并且不能小于或者等0");
			}
			if(null == jUser.getOrgPrice() || jUser.getOrgPrice().compareTo(BigDecimal.ZERO) == 0  || jUser.getOrgPrice().doubleValue() < 0 || jUser.getOrgPrice().doubleValue() < jUser.getPrice().doubleValue()){
				return WebVo.error("原价价格不能为空，并且不能小于或者等0,原价必须大于或者等于折扣价格");
			}
			if(StringUtils.isEmpty(jUser.getLevel())){
				return WebVo.error("级别不能为空");
			}
			if(StringUtils.isEmpty(jUser.getServiceId())){
				return WebVo.error("服务不能为空");
			}else{
				JService jse = jServiceService.selectById(jUser.getServiceId());
				if(null == jse){
					return WebVo.error("服务不存在， 请配置该服务");
				}
				jUser.setService(jse.getServiceName());
				jUser.setServiceTime(jse.getServiceTime());
			}
			if(null == jUser.getInJobDate()){
				return WebVo.error("入职时间不能为空");
			}
			if(StringUtils.isEmpty(jUser.getStoreId())){
				return WebVo.error("门店Id不能为空");
			}else{
				JStore jse1 = jStoreService.selectById(jUser.getStoreId());
				if(null == jse1){
					return WebVo.error("服务不存在， 请配置该服务");
				}
				jUser.setStoreName(jse1.getName());
			}
			jUser.setJsStatus(3);
		}else{
			if(StringUtils.isEmpty(jUser.getAccount())){
				return WebVo.error("账号不能为空!");
			}
		}
		if(StringUtils.isEmpty(jUser.getPassword())){
			return WebVo.error("密码不能为空");
		}else{
			jUser.setPassword(MD5Util.md5(jUser.getPassword()));
		}
		if(!StringUtils.isEmpty(jUser.getLevel())){
			jUser.setLevel(jUser.getLevel() + "技师");
		}
		jUser.setOper(ContextUtil.getUserName());
		jUser.setOperId(ContextUtil.getUserId());
		if(StringUtils.isEmpty(jUser.getId())){
			jUser.setId(StringUtil.getUUID());
			jUser.setCreateTime(new Date());
			jUser.setDelFlag("1");
			
			jUserService.insert(jUser);
		}else{
			JUser jb = jUserService.selectById(jUser.getId());
			if(null != jb){
				jUser.setUpdateTime(new Date());
				jUserService.updateById(jUser);
			}else{
				return WebVo.error("该用户不存在");
			}
		}
		return WebVo.success();
	}
	
	
	@ApiOperation(value= "BI新增或者修改技师或者管理员 ",tags = {"修改带ID， 保存不需要带ID"},httpMethod = "POST",produces="application/json")
	@RequestMapping("/jUser/restPwd")
	public WebVo<String> restPwd(@RequestBody JUser jUser){
		if(null == jUser){
			return WebVo.error("参数不能为空");
		}
		if(StringUtils.isEmpty(jUser.getId())){
			return WebVo.error("ID不能为空");
		}
		if(StringUtils.isEmpty(jUser.getPassword())){
			return WebVo.error("密码不能为空");
		}else{
			jUser.setPassword(MD5Util.md5(jUser.getPassword()));
		}
		jUser.setOper(ContextUtil.getUserName());
		jUser.setOperId(ContextUtil.getUserId());
		JUser jb = jUserService.selectById(jUser.getId());
		if(null != jb){
			jUser.setUpdateTime(new Date());
			jUserService.updateById(jUser);
		}else{
			return WebVo.error("该用户不存在");
		}
		return WebVo.success();
	}
	
	@ApiOperation(value = "BI 删除用户" , tags = {"批量删除多个以逗号隔开"},httpMethod = "POST",produces="application/json")
	@RequestMapping("/delete")
	public WebVo<String> delete(@RequestBody JUser jUser){
		if(null == jUser){
			return WebVo.error("参数不能为空");
		}
		if(!StringUtils.isEmpty(jUser.getId())){
			if(jUser.getId().contains(",")){
				String[] str = jUser.getId().split(",");
				for (String string : str) {
					JUser jb = jUserService.selectById(string);
					if(null != jb){
						jb.setOper(ContextUtil.getUserName());
						jb.setOperId(ContextUtil.getUserId());
						jb.setDelFlag("0");
						jUser.setUpdateTime(new Date());
						jUserService.updateById(jb);
					}else{
						return WebVo.error("该banner不存在,请刷新页面");
					}
				}
			}else{
				JUser jb = jUserService.selectById(jUser.getId());
				if(null != jb){
//					jb.setOper(ContextUtil.getUserName());
//					jb.setOperId(ContextUtil.getUserId());
//					jb.setDelFlag("0");
//					jUser.setUpdateTime(new Date());
					
					jUserService.deleteById(jb.getId());
				}else{
					return WebVo.error("该用户不存在,请刷新页面");
				}
			}
		}
		return WebVo.success();
	}
	
	@ApiOperation(value = "BI系统登陆 ",httpMethod = "POST",produces="application/json")
	@RequestMapping(value="/syslogin")
	public WebVo<String> login(@RequestBody JUser jUser){
		log.info("微信入参"  + JSON.toJSONString(jUser));
		if(null == jUser){
			return WebVo.error("参数不能为空");
		}
		if(StringUtil.isEmpty(jUser.getAccount())){
			return WebVo.error("用户名不能为空");
		}
		if(StringUtil.isEmpty(jUser.getPassword())){
			return WebVo.error("密码不能为空");
		}
		JUser jUs = new JUser();
		jUs.setDelFlag("1");
		jUs.setAccount(jUser.getAccount());
		JUser sysUser = jUserService.selectOne(new EntityWrapper<JUser>(jUs).and("user_type = 1").or("user_type = 2"));
		if(null == sysUser){
			return WebVo.error("用户不存在");
		}
		if(!sysUser.getPassword().equals(MD5Util.md5(jUser.getPassword()))){
			return WebVo.error("密码错误");
		}
		String token = "bi-"+sysUser.getId();
		redisComponent.setEx(token, sysUser, PropUtil.getInt("token.timeout", 7200));
		sysUser.setLoginTime(new Date());
		jUserService.updateById(sysUser);
		return WebVo.success(token);
	} 
	
	/**
	 *  修改评论
	 */
	@ApiOperation(value= "修改评论 ",tags = {"添加评论"},httpMethod = "POST",produces="application/json")
	@RequestMapping("/jEvaluate/updateeval")
	public WebVo<String> updateeval(@RequestBody JEvaluate jEvaluate){
		if(null == jEvaluate){
			return WebVo.error("参数不能为空");
		}
		if(StringUtils.isEmpty(jEvaluate.getId())){
			return WebVo.error("ID 不能为空不能为空");
		}
		if(null == jEvaluate.getStatus()){
			return WebVo.error("状态不能为空不能为空");
		}
		if(jEvaluate.getId().contains(",")){
			String[] str = jEvaluate.getId().split(",");
			for (String string : str) {
				JEvaluate jb = jEvaluateService.selectById(string);
				if(null != jb){
					if(3 == jEvaluate.getStatus()){
						jEvaluateService.deleteById(jEvaluate.getId());
					}else{
						jb.setStatus(jEvaluate.getStatus());
						jEvaluateService.updateById(jb);
					}
				}else{
					return WebVo.error("该评论不存在,请刷新页面");
				}
			}
			}else{
				if(3 == jEvaluate.getStatus()){
					jEvaluateService.deleteById(jEvaluate.getId());
					return WebVo.success();
				}
				jEvaluateService.updateById(jEvaluate);
		}
		return WebVo.success();
	}
	
	@RequestMapping("/getLoactionBaiduApi")
	public WebVo<String> getLoactionBaiduApi(@RequestBody String address){
		
		return WebVo.success(baiduApiLocation.getCoordinate(address));
	}
}
