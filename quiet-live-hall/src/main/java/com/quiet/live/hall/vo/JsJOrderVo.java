package com.quiet.live.hall.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.quiet.live.hall.entity.JOrder;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class JsJOrderVo implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -703122367450908720L;

	@ApiModelProperty(name = "pdPresonNum", value = "排队数量")
	private Integer pdPresonNum = 0;
	
	@ApiModelProperty(name = "orders", value = "排队中列表")
	private List<JOrder> orders;
	
	@ApiModelProperty(name = "pdDate", value = "下单时间")
	private Date pdDate;
}
