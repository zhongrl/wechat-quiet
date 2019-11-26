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
import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.google.common.collect.Lists;
import com.quiet.live.hall.config.ContextUtil;
import com.quiet.live.hall.entity.JService;
import com.quiet.live.hall.entity.JUser;
import com.quiet.live.hall.service.JServiceService;
import com.quiet.live.hall.service.JUserService;
import com.quiet.live.hall.utils.string.StringUtil;
import com.quiet.live.hall.utils.web.WebVo;
import com.quiet.live.hall.vo.JServiceVo;

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
@Api(value="BI技师服务种类",tags={"BI技师服务种类"})
@RestController
@RequestMapping("/bi/jService")
@CrossOrigin
public class JServiceController {

	@Autowired
	JServiceService jServiceService;
	
	@Autowired
	JUserService jUserService;
	
	@ApiOperation(value = "BI技师服务列表 ",httpMethod = "POST",produces="application/json")
	@RequestMapping("/list")
	public WebVo<Page<JService>> list(@RequestBody JServiceVo JServiceVo){
		JService jService = new JService();
		if(!StringUtils.isEmpty(JServiceVo.getServiceName())){
			jService.setServiceName(JServiceVo.getServiceName());
		}
		if(!StringUtils.isEmpty(JServiceVo.getId())){
			jService.setId(JServiceVo.getId());
		}
		Page<JService> page = new Page<JService>(JServiceVo.getCurrent(),JServiceVo.getSize());
		return WebVo.success(jServiceService.selectPage(page,new EntityWrapper<JService>(jService)
				.orderDesc(Collections.singleton("create_time "))));
	}
	
	
	@ApiOperation(value = "BI所有技师服务 ",httpMethod = "POST",produces="application/json")
	@RequestMapping("/findXcxService")
	public WebVo<List<JService>> findXcxService(@RequestBody JService jService){
		jService = new JService();
		return WebVo.success(jServiceService.selectList(new EntityWrapper<JService>(jService)
				.orderDesc(Collections.singleton("create_time "))));
	}
	
	@ApiOperation(value= "BI新增或者修改技师服务 ",tags = {"修改带ID， 保存不需要带ID"},httpMethod = "POST",produces="application/json")
	@RequestMapping("/save")
	public WebVo<String> save(@RequestBody JService jService){
		if(null == jService){
			return WebVo.error("参数不能为空");
		}
		if(StringUtils.isEmpty(jService.getServiceName())){
			return WebVo.error("服务名称链接不能为空");
		}
		if(null ==jService.getServiceTime()){
			jService.setServiceTime(45);
		}
		if(StringUtils.isEmpty(jService.getId())){
			jService.setId(StringUtil.getUUID());
			jService.setCreateTime(new Date());
			jService.setOper(ContextUtil.getUserName());
			jService.setOperId(ContextUtil.getUserId());
			jServiceService.insert(jService);
		}else{
			JService jb = jServiceService.selectById(jService.getId());
			if(null != jb){
				if(null != jService.getServiceTime() && jb.getServiceTime() != jService.getServiceTime()){
					JUser jUser = new JUser();
					jUser.setServiceId(jb.getId());
					List<JUser> list  = jUserService.selectList(new EntityWrapper<>(jUser));
					if(CollectionUtils.isNotEmpty(list)){
						list.stream().forEach(p->{
							JUser ju = new JUser();
							ju.setServiceTime(jService.getServiceTime());
							ju.setId(p.getId());
							jUserService.updateById(ju);
						});
					}
				}
				jServiceService.updateById(jService);
			}else{
				return WebVo.error("该服务配置不存在");
			}
		}
		return WebVo.success();
	}
	
	@ApiOperation(value = "BI 删除师服务 " , tags = {"批量删除多个以逗号隔开"},httpMethod = "POST",produces="application/json")
	@RequestMapping("/delete")
	public WebVo<String> delete(@RequestBody JService jService){
		if(null == jService){
			return WebVo.error("参数不能为空");
		}
		if(StringUtils.isNoneBlank(jService.getId())){
			if(jService.getId().contains(",")){
				String[] str = jService.getId().split(",");
				List<String> lists =  Lists.newArrayList();
				for (String string : str) {
					JService jb = jServiceService.selectById(jService.getId());
					if(null != jb){
						lists.add(string);
					}else{
						return WebVo.error("该服务不存在,请刷新页面");
					}
					
				}
				jServiceService.deleteBatchIds(lists);
			}else{
				JService jb = jServiceService.selectById(jService.getId());
				if(null != jb){
					jServiceService.deleteById(jb.getId());
				}else{
					return WebVo.error("该服务不存在,请刷新页面");
				}
			}
		}
		
		
		return WebVo.success();
	}
}

