package com.quiet.live.hall.entity;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.ToString;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 *
 * 
 *
 */
@Data
@ToString
@ApiModel("j_coupon")
@TableName(value = "j_coupon")
public class JCoupon implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/** 主键 */
	@TableId
	@ApiModelProperty(name = "id", value = "主键，修改必填",required = true)
	private String id;

	/** 主题 */
	@ApiModelProperty(name = "title", value = "主题",required = true)
	private String title;

	/** 创建时间 */
	@TableField(value = "create_time")
	@ApiModelProperty(name = "createTime", value = "创建时间")
	private Date createTime;

	/** 优惠券金额 */
	@ApiModelProperty(name = "price", value = "优惠券金额",required = true)
	private BigDecimal price;

	/** 优惠券描述 */
	@ApiModelProperty(name = "remake", value = "优惠券描述",required = true)
	private String remake;

	/** 开始时间 */
	@TableField(value = "start_time")
	@ApiModelProperty(name = "startTime", value = "开始时间",required = true)
	private Date startTime;

	/** 结束时间 */
	@TableField(value = "end_time")
	@ApiModelProperty(name = "endTime", value = "结束时间",required = true)
	private Date endTime;

	/**  */
	@TableField(value = "del_flag")
	@ApiModelProperty(name = "delFlag", value = "0失效，1有效",required = true)
	private String delFlag;

	/** 优惠券类型 */
	@TableField(value = "coupon_type")
	@ApiModelProperty(name = "couponType", value = "优惠券类型",required = true)
	private String couponType;

}
