package com.quiet.live.hall.bi.rest;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.google.common.collect.Lists;
import com.quiet.live.hall.entity.JEvaluate;
import com.quiet.live.hall.service.JEvaluateService;
import com.quiet.live.hall.service.JUserService;
import com.quiet.live.hall.utils.web.WebVo;
import com.quiet.live.hall.vo.JEvaluateVo;

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
@Api(value="BI评论接口 ",tags={"评论接口"},description="评论接口")
@RestController
@RequestMapping("/bi/jEvaluate")
@CrossOrigin
@Slf4j
public class JEvaluateController {

	@Autowired
	JUserService jUserService;
	
	
	
	@Autowired
	JEvaluateService jEvaluateService;
	/**
	 *   BI查询所有评论
	 */
	@ApiOperation(value = "BI所有所有评论 ",httpMethod = "POST",produces="application/json")
	@RequestMapping("/list")
	public WebVo<Page<JEvaluate>> list(@RequestBody JEvaluateVo jEvaluateVo){
		
		JEvaluate jEvaluate = new JEvaluate();
		if(StringUtils.isNotEmpty(jEvaluateVo.getUserId())){
			jEvaluate.setUserId(jEvaluateVo.getUserId());
		}
		log.info("前端参数" + JSON.toJSONString(jEvaluateVo));
		log.info("copy之后参数" + JSON.toJSONString(jEvaluate));
		Page<JEvaluate> page = new Page<JEvaluate>(jEvaluateVo.getCurrent(),jEvaluateVo.getSize());
		jEvaluateService.selectPage(page,new EntityWrapper<JEvaluate>(jEvaluate)
				.orderDesc(Collections.singleton("create_time ")));
		List<JEvaluate> list = page.getRecords();
		List<JEvaluate> newList = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(list)){
			for(JEvaluate jEvalua : list ){
				if(StringUtils.isNotEmpty(jEvalua.getRemark()) && jEvalua.getRemark().length() > 20){
					jEvalua.setRemark(jEvalua.getRemark().substring(0,20));
				}
				newList.add(jEvalua);
			}
		}
		page.getRecords().clear();
		page.setRecords(newList);
		return WebVo.success(page);
	}
	@ApiOperation(value= "BI新增或者修改门店 ",tags = {"修改带ID， 保存不需要带ID"},httpMethod = "POST",produces="application/json")
	@RequestMapping("/update")
	public WebVo<String> update(@RequestBody JEvaluate jEvaluate){
		if(null == jEvaluate){
			return WebVo.error("参数不能为空");
		}
		if(StringUtils.isEmpty(jEvaluate.getId())){
			return WebVo.error("id不能为空");
		}
		if(null == jEvaluate.getStatus()){
			return WebVo.error("评论状态不能为空");
		}
		JEvaluate je = jEvaluateService.selectById(jEvaluate.getId());
		if(null == je){
			return WebVo.error("评论不存在");
		}
		jEvaluateService.updateById(jEvaluate);
		return WebVo.success();
	}

	/** 
	 *  删除评论
	 */
	@ApiOperation(value = "BI 删除评论 " , tags = {"批量删除多个以逗号隔开"},httpMethod = "POST",produces="application/json")
	@RequestMapping("/delete")
	public WebVo<String> delete(@RequestBody JEvaluate jEvaluate){
		if(null == jEvaluate){
			return WebVo.error("参数不能为空");
		}
		if(StringUtils.isNoneBlank(jEvaluate.getId())){
			if(jEvaluate.getId().contains(",")){
				String[] str = jEvaluate.getId().split(",");
				List<String> lists =  Lists.newArrayList();
				for (String string : str) {
					JEvaluate jb = jEvaluateService.selectById(jEvaluate.getId());
					if(null != jb){
						lists.add(string);
					}else{
						return WebVo.error("该评论不存在,请刷新页面");
					}
				}
				jEvaluateService.deleteBatchIds(lists);
			}else{
				JEvaluate jb = jEvaluateService.selectById(jEvaluate.getId());
				if(null != jb){
					jEvaluateService.deleteById(jb.getId());
				}else{
					return WebVo.error("该评论不存在,请刷新页面");
				}
			}
		}
		
		
		return WebVo.success();
	}
}

