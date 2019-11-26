package com.quiet.live.hall.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.quiet.live.hall.entity.JOrder;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CutomJOrderVo implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -703122367450908720L;

	@ApiModelProperty(name = "pdPresonNum", value = "技师排队数量")
	private Integer pdPresonNum = 0;
	
	@ApiModelProperty(name = "orders", value = "技师排队中列表")
	private List<JOrder> orders;
	
	@ApiModelProperty(name = "pdDate", value = "技师最新的下单时间")
	private Date pdDate;
	
	@ApiModelProperty(name = "pdTime", value = "只有刷新列表才能有排队时长")
	private String pdTime;
}
