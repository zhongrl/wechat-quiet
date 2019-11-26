package com.quiet.live.hall.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

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
@ToString
@ApiModel("j_coupon_detail")
@TableName(value = "j_coupon_detail")
public class JCouponDetail implements Serializable {

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
	@ApiModelProperty(name = "delFlag", value = "1,已使用，2，待使用，3，已过期，空的为待领取，4 待分享")
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
	
	@TableField(exist =false)
	private String code;
	
	@TableField(exist =false)
	private String isWxWp;
	
	@TableField(exist =false)
	private String rawData;
	@TableField(exist =false)
	private String signature;
	@TableField(exist =false)
	private String encryptedData;
	@TableField(exist =false)
	private String iv;
	
	@ApiModelProperty(name = "unionid", value = "开放平台唯一标识")
	@TableField(value = "unionid")
	private String unionid;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getRemake() {
		return remake;
	}

	public void setRemake(String remake) {
		this.remake = remake;
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

	public String getJcUsername() {
		
		if(StringUtils.isNotEmpty(jcUsername)){
		     if(com.quiet.live.hall.utils.string.StringUtil.isBase64(jcUsername)){
		    	 try {
		    		 jcUsername = new String(org.apache.commons.codec.binary.Base64.decodeBase64(jcUsername),"UTF-8");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    	return  jcUsername;
		     }
		}
		return jcUsername;
	}

	public void setJcUsername(String jcUsername) {
		this.jcUsername = jcUsername;
	}
	

	public String getJsUserId() {
		return jsUserId;
	}

	public void setJsUserId(String jsUserId) {
		this.jsUserId = jsUserId;
	}

	public String getSyUsername() {
		
		if(StringUtils.isNotEmpty(syUsername)){
		     if(com.quiet.live.hall.utils.string.StringUtil.isBase64(syUsername)){
		    	 try {
		    		 syUsername = new String(org.apache.commons.codec.binary.Base64.decodeBase64(syUsername),"UTF-8");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    	return  syUsername;
		     }
		}
		return syUsername;
	}

	public void setSyUsername(String syUsername) {
		this.syUsername = syUsername;
	}
	

	public String getSyOpenid() {
		return syOpenid;
	}

	public void setSyOpenid(String syOpenid) {
		this.syOpenid = syOpenid;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public String getGiveOpenid() {
		return giveOpenid;
	}

	public void setGiveOpenid(String giveOpenid) {
		this.giveOpenid = giveOpenid;
	}
	
	public String getGiveUsername() {
		
		if(StringUtils.isNotEmpty(giveUsername)){
		     if(com.quiet.live.hall.utils.string.StringUtil.isBase64(giveUsername)){
		    	 try {
		    		 giveUsername = new String(org.apache.commons.codec.binary.Base64.decodeBase64(giveUsername),"UTF-8");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    	return  giveUsername;
		     }
		}
		return giveUsername;
	}

	public void setGiveUsername(String giveUsername) {
		this.giveUsername = giveUsername;
	}

	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getIsWxWp() {
		return isWxWp;
	}

	public void setIsWxWp(String isWxWp) {
		this.isWxWp = isWxWp;
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

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
	

}
