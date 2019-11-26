package com.quiet.live.hall.bi.rest;


import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import com.quiet.live.hall.entity.JBanner;
import com.quiet.live.hall.service.JBannerService;
import com.quiet.live.hall.utils.string.StringUtil;
import com.quiet.live.hall.utils.web.WebVo;
import com.quiet.live.hall.vo.JBannerVo;

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
@Api(value="BI广告接口 ",tags={"广告 接口"})
@RestController
@RequestMapping("/bi/jBanner/")
@CrossOrigin
public class JBannerController {

	@Autowired
	JBannerService jBannerService;
	
	@ApiOperation(value = "BI所有Banner 广告 ",httpMethod = "POST",produces="application/json")
	@RequestMapping("/list")
	public WebVo<Page<JBanner>> list(@RequestBody JBannerVo jBannerVo){
		JBanner jBanner = new JBanner();
		BeanUtils.copyProperties(jBannerVo, jBanner);
		Page<JBanner> page = new Page<JBanner>(jBannerVo.getCurrent(),jBannerVo.getSize());
		return WebVo.success(jBannerService.selectPage(page,new EntityWrapper<JBanner>(jBanner)
				.orderDesc(Collections.singleton("create_time "))));
	}
	
	
	
	
	@ApiOperation(value= "BI新增或者修改Banner 广告 ",tags = {"修改带ID， 保存不需要带ID"},httpMethod = "POST",produces="application/json")
	@RequestMapping("/save")
	public WebVo<String> save(@RequestBody JBanner jBanner){
		if(null == jBanner){
			return WebVo.error("参数不能为空");
		}
		if(StringUtils.isEmpty(jBanner.getImgUrl())){
			return WebVo.error("imgUrl图片链接不能为空");
		}
		if(StringUtils.isEmpty(jBanner.getId())){
			jBanner.setId(StringUtil.getUUID());
			jBanner.setCreateTime(new Date());
			jBannerService.insert(jBanner);
		}else{
			JBanner jb = jBannerService.selectById(jBanner.getId());
			if(null != jb){
				jBannerService.updateById(jBanner);
			}else{
				return WebVo.error("该banner不存在");
			}
		}
		return WebVo.success();
	}
	
	@ApiOperation(value = "BI 删除Banner 广告 " , tags = {"批量删除多个以逗号隔开"},httpMethod = "POST",produces="application/json")
	@RequestMapping("/delete")
	public WebVo<String> delete(@RequestBody JBanner jBanner){
		if(null == jBanner){
			return WebVo.error("参数不能为空");
		}
		if(StringUtils.isNoneBlank(jBanner.getId())){
			if(jBanner.getId().contains(",")){
				String[] str = jBanner.getId().split(",");
				List<String> lists =  Lists.newArrayList();
				for (String string : str) {
					JBanner jb = jBannerService.selectById(jBanner.getId());
					if(null != jb){
						lists.add(string);
					}else{
						return WebVo.error("该banner不存在,请刷新页面");
					}
				}
				jBannerService.deleteBatchIds(lists);
			}else{
				JBanner jb = jBannerService.selectById(jBanner.getId());
				if(null != jb){
					jBannerService.deleteById(jb.getId());
				}else{
					return WebVo.error("该banner不存在,请刷新页面");
				}
			}
		}
		
		
		return WebVo.success();
	}
}

