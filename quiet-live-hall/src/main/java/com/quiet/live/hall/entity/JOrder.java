package com.quiet.live.hall.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.ToString;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 *
 * 
 *
 */
@ToString
@ApiModel("j_order")
@TableName(value = "j_order")
public class JOrder implements Serializable {

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
	
	/** 原价 */
	@TableField(value = "jy_price")
	@ApiModelProperty(name = "jyPrice", value = "节约金额")
	private Integer jyPrice;

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
	@ApiModelProperty(name = "status", value = "订单状态，1 已支"
			+ "付，2待支付，3，服务中，4 排队中，5取消")
	@TableField(value = "status")
	private String status;

	/** 支付流水号 */
	@TableField(value = "pay_number")
	@ApiModelProperty(name = "payNumber", value = "支付流水号")
	private String payNumber;

	/** 支付时间 */
	@TableField(value = "pay_time")
	@ApiModelProperty(name = "payTime", value = "支付时间")
	private Date payTime;
	
	@TableField(value = "service_time")
	@ApiModelProperty(name = "serviceTime", value = "服务时长")
	private Integer serviceTime;
	
	@TableField(value = "update_time")
	@ApiModelProperty(name = "updateTime", value = "修改时间")
	private Date updateTime;

	@TableField(value = "cancel_remark")
	@ApiModelProperty(name = "cancelRemark", value = "取消原因")
	private String cancelRemark;
	
	@TableField(exist =false)
	private String code;
	@TableField(exist =false)
	private String rawData;
	@TableField(exist =false)
	private String signature;
	@TableField(exist =false)
	private String encryptedData;
	@TableField(exist =false)
	private String iv;
	

	@ApiModelProperty(name = "ddTimeLong", value = "等待时长")
	@TableField(exist = false)
	private String ddTimeLong;
	@ApiModelProperty(name = "ddRenNum", value = "等待人数")
	@TableField(exist = false)
	private Integer ddRenNum;
	
	@TableField(exist = false)
	private JCouponDetail jCouponDetail;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(name = "startTime", value = "开始日期yyyy-MM-dd HH:mm:ss")
	@TableField(exist = false)
	private Date startTime;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(name = "endTime", value = "结束日期yyyy-MM-dd HH:mm:ss")
	@TableField(exist = false)
	private Date endTime;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(name = "jsServiceTime", value = "结束日期yyyy-MM-dd HH:mm:ss")
	@TableField(value = "jsServiceTime")
	private Date jsServiceTime;

	/** 经纬度 */
	@TableField(value = "jw_du")
	@ApiModelProperty(name = "jwDu", value = "经纬度",required = true)
	private String jwDu;
	
	/** 门店地址 */
	@ApiModelProperty(name = "address", value = "门店地址",required = true)
	@TableField(value = "address")
	private String address;
	
	/** 技师等级 */
	@ApiModelProperty(name = "jsLevel", value = "技师等级",required = true)
	@TableField(value = "jsLevel")
	private String jsLevel;
	
	@TableField(value = "cust_user_Id")
	private String custUserId;

	@TableField(value = "ordernum")
	private Integer ordernum;
	
	@TableField(value = "optSource")
	private String optSource;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getLineUpNumber() {
		return lineUpNumber;
	}

	public void setLineUpNumber(String lineUpNumber) {
		this.lineUpNumber = lineUpNumber;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getCouponPrice() {
		return couponPrice;
	}

	public void setCouponPrice(BigDecimal couponPrice) {
		this.couponPrice = couponPrice;
	}

	public BigDecimal getOrgPrice() {
		return orgPrice;
	}

	public void setOrgPrice(BigDecimal orgPrice) {
		this.orgPrice = orgPrice;
	}

	public Integer getJyPrice() {
		return jyPrice;
	}

	public void setJyPrice(Integer jyPrice) {
		this.jyPrice = jyPrice;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}

	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}
	
	public String getJsUsername() {
		
		if(StringUtils.isNotEmpty(jsUsername)){
		     if(com.quiet.live.hall.utils.string.StringUtil.isBase64(jsUsername)){
		    	 try {
					jsUsername = new String(org.apache.commons.codec.binary.Base64.decodeBase64(jsUsername),"UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	return  jsUsername;
		     }
		}
		return jsUsername;
	}

	public void setJsUsername(String jsUsername) {
		 this.jsUsername = jsUsername;
	}


	public String getJsUserId() {
		return jsUserId;
	}

	public void setJsUserId(String jsUserId) {
		this.jsUserId = jsUserId;
	}

	public String getJsHeadImg() {
		return jsHeadImg;
	}

	public void setJsHeadImg(String jsHeadImg) {
		this.jsHeadImg = jsHeadImg;
	}

	public String getJsMobile() {
		return jsMobile;
	}

	public void setJsMobile(String jsMobile) {
		this.jsMobile = jsMobile;
	}

	public String getCustMobile() {
		return custMobile;
	}

	public void setCustMobile(String custMobile) {
		this.custMobile = custMobile;
	}

	public String getCustOpenid() {
		return custOpenid;
	}

	public void setCustOpenid(String custOpenid) {
		this.custOpenid = custOpenid;
	}

	public String getCustUsername() {
		
		if(StringUtils.isNotEmpty(custUsername)){
		     if(com.quiet.live.hall.utils.string.StringUtil.isBase64(custUsername)){
		    	 try {
					custUsername = new String(org.apache.commons.codec.binary.Base64.decodeBase64(custUsername),"UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	return  custUsername;
		     }
		}
		return custUsername;
	}

	public void setCustUsername(String custUsername) {
		 this.custUsername = custUsername;
	}
	

	public String getCustHeadImg() {
		return custHeadImg;
	}

	public void setCustHeadImg(String custHeadImg) {
		this.custHeadImg = custHeadImg;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPayNumber() {
		return payNumber;
	}

	public void setPayNumber(String payNumber) {
		this.payNumber = payNumber;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public Integer getServiceTime() {
		return serviceTime;
	}

	public void setServiceTime(Integer serviceTime) {
		this.serviceTime = serviceTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getCancelRemark() {
		return cancelRemark;
	}

	public void setCancelRemark(String cancelRemark) {
		this.cancelRemark = cancelRemark;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRawData() {
		return rawData;
	}

	public void setRawData(String rawData) {
		this.rawData = rawData;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getEncryptedData() {
		return encryptedData;
	}

	public void setEncryptedData(String encryptedData) {
		this.encryptedData = encryptedData;
	}

	public String getIv() {
		return iv;
	}

	public void setIv(String iv) {
		this.iv = iv;
	}

	public String getDdTimeLong() {
		return ddTimeLong;
	}

	public void setDdTimeLong(String ddTimeLong) {
		this.ddTimeLong = ddTimeLong;
	}

	public Integer getDdRenNum() {
		return ddRenNum;
	}

	public void setDdRenNum(Integer ddRenNum) {
		this.ddRenNum = ddRenNum;
	}

	public JCouponDetail getjCouponDetail() {
		return jCouponDetail;
	}

	public void setjCouponDetail(JCouponDetail jCouponDetail) {
		this.jCouponDetail = jCouponDetail;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getJsServiceTime() {
		return jsServiceTime;
	}

	public void setJsServiceTime(Date jsServiceTime) {
		this.jsServiceTime = jsServiceTime;
	}

	public String getJwDu() {
		return jwDu;
	}

	public void setJwDu(String jwDu) {
		this.jwDu = jwDu;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getJsLevel() {
		return jsLevel;
	}

	public void setJsLevel(String jsLevel) {
		this.jsLevel = jsLevel;
	}

	public String getCustUserId() {
		return custUserId;
	}

	public void setCustUserId(String custUserId) {
		this.custUserId = custUserId;
	}

	public Integer getOrdernum() {
		return ordernum;
	}

	public void setOrdernum(Integer ordernum) {
		this.ordernum = ordernum;
	}

	public String getOptSource() {
		return optSource;
	}

	public void setOptSource(String optSource) {
		this.optSource = optSource;
	}
	
}
