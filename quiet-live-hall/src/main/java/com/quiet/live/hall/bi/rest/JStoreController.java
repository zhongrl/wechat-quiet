package com.quiet.live.hall.bi.rest;


import java.util.ArrayList;
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
import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.google.common.collect.Lists;
import com.quiet.live.hall.entity.JStore;
import com.quiet.live.hall.entity.JUser;
import com.quiet.live.hall.service.JStoreService;
import com.quiet.live.hall.service.JUserService;
import com.quiet.live.hall.utils.location.BaiduApiLocation;
import com.quiet.live.hall.utils.string.StringUtil;
import com.quiet.live.hall.utils.web.WebVo;
import com.quiet.live.hall.vo.JStoreVo;

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
@Api(value="BI门店种类",tags={"BI门店接口"})
@RestController
@RequestMapping("/bi/jStore")
@CrossOrigin
public class JStoreController {

	@Autowired
	JStoreService jStoreService;
	
	@Autowired
	BaiduApiLocation baiduApiLocation;
	
	@Autowired
	JUserService jUserService;
	
	@ApiOperation(value = "BI门店列表 ",httpMethod = "POST",produces="application/json")
	@RequestMapping("/list")
	public WebVo<Page<JStore>> list(@RequestBody JStoreVo jStoreVo){
		JStore jStore = new JStore();
		BeanUtils.copyProperties(jStoreVo, jStore);
		Page<JStore> page = new Page<JStore>(jStoreVo.getCurrent(),jStoreVo.getSize());
		jStoreService.selectPage(page,new EntityWrapper<JStore>(jStore)
				.orderDesc(Collections.singleton("create_time ")));
		List<JStore> list = page.getRecords();
		List<JStore> lists = new ArrayList<JStore>();
		if(!CollectionUtils.isEmpty(list)){
			for(JStore jsre : list){
				JUser j = new JUser();
				j.setStoreId(jsre.getId());
				j.setDelFlag("1");
				int count = jUserService.selectCount(new EntityWrapper<JUser>(j));
				jsre.setJsNum(count);
				lists.add(jsre);
			}
			page.getRecords().clear();
			page.setRecords(lists);
		}
		return WebVo.success(page);
	}
	

	@ApiOperation(value = "BI下拉门店列表 ",httpMethod = "POST",produces="application/json")
	@RequestMapping("/findSores")
	public WebVo<List<JStore>> findSores(@RequestBody JStoreVo jStoreVo){
		JStore jStore = new JStore();
		return WebVo.success(jStoreService.selectList(new EntityWrapper<JStore>(jStore)
				.orderDesc(Collections.singleton("create_time "))));
	}
	
	@ApiOperation(value = "获取单个技师信息",httpMethod = "POST",produces="application/json")
	@RequestMapping("/selectByStoreId")
	public WebVo<JStore> selectByJs(@RequestBody JStoreVo jStoreVo){
		if(null == jStoreVo){
			return WebVo.error("参数不能为空");
		}
		if(StringUtils.isEmpty(jStoreVo.getId())){
			return WebVo.error("id参数不能为空");
		}
		return WebVo.success(jStoreService.selectById(jStoreVo.getId()));
	}
	
	@ApiOperation(value= "BI新增或者修改门店 ",tags = {"修改带ID， 保存不需要带ID"},httpMethod = "POST",produces="application/json")
	@RequestMapping("/save")
	public WebVo<String> save(@RequestBody JStore jStore){
		if(null == jStore){
			return WebVo.error("参数不能为空");
		}
		if(StringUtils.isEmpty(jStore.getName())){
			return WebVo.error("门店名称不能为空");
		}
		if(StringUtils.isEmpty(jStore.getAddress())){
			return WebVo.error("门店地址不能为空");
		}
		jStore.setImgurl("http://jingxiang2019.com/img/quiet/a303890a-6a2e-4eef-9afd-ea448508ec55.png");
		jStore.setRemarke("营业时间：11:00~22:00");
		if(StringUtils.isEmpty(jStore.getId())){
			jStore.setId(StringUtil.getUUID());
			jStore.setCreateTime(new Date());
//			String result = baiduApiLocation.getAddPostion(jStore.getAddress());
//			if(null != result){
//				jStore.setJwDu(result);
//			}else{
//				return WebVo.error("地址获取不到!");
//			}
//			
			
			jStore.setStoretitle("现代推拿");
			jStoreService.insert(jStore);
		}else{
			JStore jb = jStoreService.selectById(jStore.getId());
			if(null != jb){
				if(null != jStore.getName() &&  jb.getName() !=  jStore.getName()){
					JUser jUser = new JUser();
					jUser.setStoreId(jStore.getId());
					List<JUser> list  = jUserService.selectList(new EntityWrapper<>(jUser));
					if(CollectionUtils.isNotEmpty(list)){
						list.stream().forEach(p->{
							JUser ju = new JUser();
							ju.setStoreName(jStore.getName());
							ju.setId(p.getId());
							jUserService.updateById(ju);
						});
					}
				}
				jStoreService.updateById(jStore);
			}else{
				return WebVo.error("该服务配置不存在");
			}
		}
		return WebVo.success();
	}
	
	@ApiOperation(value = "BI 删除门店 " , tags = {"批量删除多个以逗号隔开"},httpMethod = "POST",produces="application/json")
	@RequestMapping("/delete")
	public WebVo<String> delete(@RequestBody JStore jStore){
		if(null == jStore){
			return WebVo.error("参数不能为空");
		}
		if(StringUtils.isNoneBlank(jStore.getId())){
			if(jStore.getId().contains(",")){
				String[] str = jStore.getId().split(",");
				List<String> lists =  Lists.newArrayList();
				for (String string : str) {
					JStore jb = jStoreService.selectById(jStore.getId());
					if(null != jb){
						lists.add(string);
					}else{
						return WebVo.error("该门店不存在,请刷新页面");
					}
				}
				jStoreService.deleteBatchIds(lists);
			}else{
				JStore jb = jStoreService.selectById(jStore.getId());
				if(null != jb){
					jStoreService.deleteById(jb.getId());
				}else{
					return WebVo.error("该门店不存在,请刷新页面");
				}
			}
		}
		
		
		return WebVo.success();
	}
}

