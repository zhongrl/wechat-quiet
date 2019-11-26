package com.quiet.live.hall.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.plugins.Page;
import com.quiet.live.hall.entity.JOrder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("订单功能")
public class BiOrderVo implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 3078013927829761731L;


	@ApiModelProperty(name = "pdPresonNum", value = "订单总金额")
	private BigDecimal totalSalar;
	
	@ApiModelProperty(name = "orders", value = "订单列表")
	private Page<JOrder> pages;
	
	@ApiModelProperty(name = "totalOrderNum", value = "订单总条数")
	private int totalOrderNum;
}
