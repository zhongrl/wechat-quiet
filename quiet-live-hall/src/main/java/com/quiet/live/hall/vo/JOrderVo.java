package com.quiet.live.hall.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 *
 * 
 *
 */
@Data
@ToString
@ApiModel("j_order")
@TableName(value = "j_order")
public class JOrderVo implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/** 主键 */
	@TableId
	@ApiModelProperty(name = "id", value = "主键")
	private String id;

	/** 创建时间 */
	@TableField(value = "create_time")
	@ApiModelProperty(name = "createTime", value = "创建时间")
	private Date createTime;

	/** 订单号 */
	@TableField(value = "order_number")
	@ApiModelProperty(name = "orderNumber", value = "订单号")
	private String orderNumber;

	/** 排队号 */
	@TableField(value = "line_up_number")
	@ApiModelProperty(name = "lineUpNumber", value = "排队号")
	private String lineUpNumber;

	/** 订单支付金额 */
	@ApiModelProperty(name = "price", value = "订单支付金额")
	private BigDecimal price;

	/** 优惠金额 */
	@TableField(value = "coupon_price")
	@ApiModelProperty(name = "couponPrice", value = "优惠金额")
	private BigDecimal couponPrice;

	/** 原价 */
	@TableField(value = "org_price")
	@ApiModelProperty(name = "orgPrice", value = "原价")
	private BigDecimal orgPrice;

	/** 服务ID */
	@TableField(value = "service_id")
	@ApiModelProperty(name = "serviceId", value = "服务ID")
	private String serviceId;

	/** 服务名称 */
	@TableField(value = "service_name")
	@ApiModelProperty(name = "serviceName", value = "服务名称")
	private String serviceName;

	/** 门店ID */
	@TableField(value = "store_id")
	@ApiModelProperty(name = "storeId", value = "门店ID")
	private String storeId;

	/** 门店名称 */
	@TableField(value = "store_name")
	@ApiModelProperty(name = "storeName", value = "门店名称")
	private String storeName;

	/** 优惠券ID */
	@TableField(value = "coupon_id")
	@ApiModelProperty(name = "couponId", value = "优惠券ID")
	private String couponId;

	/** 优惠券名称 */
	@TableField(value = "coupon_name")
	@ApiModelProperty(name = "couponName", value = "优惠券名称")
	private String couponName;

	/** 技师名称 */
	@TableField(value = "js_username")
	@ApiModelProperty(name = "jsUsername", value = "技师名称")
	private String jsUsername;

	/** 技师ID */
	@TableField(value = "js_user_id")
	@ApiModelProperty(name = "jsUserId", value = "技师ID")
	private String jsUserId;

	/** 技师头像 */
	@TableField(value = "js_head_img")
	@ApiModelProperty(name = "jsHeadImg", value = "技师头像")
	private String jsHeadImg;

	/** 技师手机号 */
	@TableField(value = "js_mobile")
	@ApiModelProperty(name = "jsMobile", value = "技师手机号")
	private String jsMobile;

	/** 客户手机号 */
	@TableField(value = "cust_mobile")
	@ApiModelProperty(name = "custMobile", value = "客户手机号")
	private String custMobile;

	/** 客户微信ID */
	@TableField(value = "cust_openid")
	@ApiModelProperty(name = "custOpenid", value = "客户微信ID")
	private String custOpenid;

	/** 客户名称 */
	@TableField(value = "cust_username")
	@ApiModelProperty(name = "custUsername", value = "客户名称")
	private String custUsername;

	/** 客户头像 */
	@TableField(value = "cust_head_img")
	@ApiModelProperty(name = "custHeadImg", value = "客户头像")
	private String custHeadImg;

	/** 订单状态，1 已支付，2待支付，3，服务中，4 排队中 */
	@ApiModelProperty(name = "status", value = "订单状态，1 已支付，2待支付，3，服务中，4 排队中")
	private String status;

	/** 支付流水号 */
	@TableField(value = "pay_number")
	@ApiModelProperty(name = "payNumber", value = "支付流水号")
	private String payNumber;

	/** 支付时间 */
	@TableField(value = "pay_time")
	@ApiModelProperty(name = "payTime", value = "支付时间")
	private Date payTime;
	@TableField(exist = false)
	private int size = 15;
	@TableField(exist = false)
	private int current = 1;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(name = "startTime", value = "开始日期yyyy-MM-dd HH:mm:ss")
	private Date startTime;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(name = "endTime", value = "结束日期yyyy-MM-dd HH:mm:ss")
	private Date endTime;
	
	private String optSource;
}
