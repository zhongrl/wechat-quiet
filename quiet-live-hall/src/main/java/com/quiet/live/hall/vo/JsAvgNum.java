package com.quiet.live.hall.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class JsAvgNum implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -5208668277741517903L;
	
	@ApiModelProperty(name = "评论数", value = "评论数")
	private int plNum;
	
	@ApiModelProperty(name = "平均分", value = "平均分")
	private BigDecimal avgNum;
}
