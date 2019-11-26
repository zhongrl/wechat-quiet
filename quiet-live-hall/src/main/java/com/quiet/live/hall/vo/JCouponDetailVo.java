package com.quiet.live.hall.vo;

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
@ApiModel("j_coupon_detail")
@TableName(value = "j_coupon_detail")
public class JCouponDetailVo implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/** 主键 */
	@TableId
	@ApiModelProperty(name = "id", value = "主键")
	private String id;

	/** 主题 */
	@ApiModelProperty(name = "title", value = "主题")
	private String title;

	/** 创建时间 */
	@TableField(value = "create_time")
	@ApiModelProperty(name = "createTime", value = "创建时间")
	private Date createTime;

	/** 价格 */
	@ApiModelProperty(name = "price", value = "价格")
	private BigDecimal price;

	/** 优惠券描述 */
	@ApiModelProperty(name = "remake", value = "优惠券描述")
	private String remake;

	/** 开始时间 */
	@TableField(value = "start_time")
	@ApiModelProperty(name = "startTime", value = "开始时间")
	private Date startTime;

	/** 结束时间 */
	@TableField(value = "end_time")
	@ApiModelProperty(name = "endTime", value = "结束时间")
	private Date endTime;

	/** 技师名字 */
	@TableField(value = "jc_username")
	@ApiModelProperty(name = "jcUsername", value = "技师名字")
	private String jcUsername;

	/** 技师ID */
	@TableField(value = "js_user_id")
	@ApiModelProperty(name = "jsUserId", value = "技师ID")
	private String jsUserId;

	/** 使用人名字 */
	@TableField(value = "sy_username")
	@ApiModelProperty(name = "syUsername", value = "使用人名字")
	private String syUsername;

	/** 使用人openId */
	@TableField(value = "sy_openid")
	@ApiModelProperty(name = "syOpenid", value = "使用人openId")
	private String syOpenid;

	/** 1,已使用，2，待使用，3，已过期，空的为待领取 */
	@TableField(value = "del_flag")
	@ApiModelProperty(name = "delFlag", value = "1,已使用，2，待使用，3，已过期，空的为待领取")
	private String delFlag;

	/** 赠送人openId */
	@TableField(value = "give_openId")
	@ApiModelProperty(name = "giveOpenid", value = "赠送人openId")
	private String giveOpenid;

	/** 赠送人名字 */
	@TableField(value = "give_username")
	@ApiModelProperty(name = "giveUsername", value = "赠送人名字")
	private String giveUsername;

	/** 优惠券ID */
	@TableField(value = "coupon_id")
	@ApiModelProperty(name = "couponId", value = "优惠券ID")
	private String couponId;
	private int size = 15;
	private int current = 1;
}
