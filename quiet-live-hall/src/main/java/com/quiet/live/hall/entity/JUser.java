package com.quiet.live.hall.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.quiet.live.hall.utils.string.StringUtil;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

/**
 *
 * 
 *
 */
@ToString
@ApiModel("j_user")
@TableName(value = "j_user")
public class JUser implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/** 主键 */
	@TableId
	@ApiModelProperty(name = "id", value = "主键,修改必填",required = true)
	private String id;

	/** 用户名称 */
	@ApiModelProperty(name = "username", value = "用户名称",required = true)
	private String username;

	/** 密码 */
	@ApiModelProperty(name = "password", value = "密码",required = true)
	private String password;

	/** 用户类型，1超级管理员，2门店、运营、3、技师，4 客户 */
	@TableField(value = "user_type")
	@ApiModelProperty(name = "userType", value = "用户类型，1超级管理员，2门店、运营、3、技师，4 客户",required = true)
	private String userType;

	/** 技师等级 */
	@ApiModelProperty(name = "level", value = "技师等级",required = true)
	private String level;

	/** 手机号 */
	@ApiModelProperty(name = "mobile", value = "手机号",required = true)
	private String mobile;

	/** 服务ID */
	@TableField(value = "service_id")
	@ApiModelProperty(name = "serviceId", value = "服务ID",required = true)
	private String serviceId;

	/** 服务名称 */
	@ApiModelProperty(name = "service", value = "服务名称")
	private String service;

	/** 创建时间 */
	@TableField(value = "create_time")
	@ApiModelProperty(name = "createTime", value = "创建时间")
	private Date createTime;

	/** 入职时间 */
	@TableField(value = "in_job_date")
	@ApiModelProperty(name = "inJobDate", value = "入职时间,技师必填 yyyy-MM-dd",required = true)
	@JsonFormat(pattern=" yyyy-MM-dd")
	@JSONField(format=" yyyy-MM-dd")
	@DateTimeFormat(pattern = " yyyy-MM-dd")
	private Date inJobDate;

	/** 账户 */
	@ApiModelProperty(name = "account", value = "账户 技师默认手机号就是账号",required = true)
	private String account;

	/** 修改时间 */
	@TableField(value = "update_time")
	@ApiModelProperty(name = "updateTime", value = "修改时间")
	private Date updateTime;

	/** 操作人 */
	@ApiModelProperty(name = "oper", value = "操作人")
	private String oper;

	/** 操作人ID */
	@TableField(value = "oper_id")
	@ApiModelProperty(name = "operId", value = "操作人ID")
	private String operId;

	/** 是否有效 1 有效，0无效 */
	@TableField(value = "del_flag")
	@ApiModelProperty(name = "delFlag", value = "是否有效 1 有效，0无效",required = true)
	private String delFlag;

	/** 门店ID */
	@TableField(value = "store_id")
	@ApiModelProperty(name = "storeId", value = "门店ID",required = true)
	private String storeId;

	/** 门店名称 */
	@TableField(value = "store_name")
	@ApiModelProperty(name = "storeName", value = "门店名称")
	private String storeName;

	/** 技师头像 */
	@TableField(value = "head_img")
	@ApiModelProperty(name = "headImg", value = "技师头像",required = true)
	private String headImg;

	/**  */
	@TableField(value = "org_price")
	@ApiModelProperty(name = "orgPrice", value = "")
	private BigDecimal orgPrice;

	/** 价格 */
	@ApiModelProperty(name = "price", value = "价格",required = true)
	private BigDecimal price;

	/** 微信openId */
	@TableField(value = "open_id")
	@ApiModelProperty(name = "openId", value = "微信openId")
	private String openId;

	/**  */
	@TableField(value = "login_time")
	@ApiModelProperty(name = "loginTime", value = "")
	private Date loginTime;

	@TableField(value = "js_status")
	@ApiModelProperty(name = "技师状态", value = "1 可接单，2 停止接单，3 休息中")
	private Integer jsStatus;
	
	@TableField(value = "service_time")
	@ApiModelProperty(name = "serviceTime", value = "服务时长")
	private Integer serviceTime;
	
	@ApiModelProperty(name = "gender", value = "性别")
	@TableField(value = "gender")
	private String gender;
	
	@ApiModelProperty(name = "jstotlfen", value = "技师总分平均分")
	@TableField(value = "jstotlfen")
	private Integer jstotlfen;
	
	@ApiModelProperty(name = "pinglunnum", value = "评论总数")
	@TableField(value = "pinglunnum")
	private Integer pinglunnum;
	
	@ApiModelProperty(name = "custom_open_id", value = "小程序openId")
	@TableField(value = "custom_open_id")
	private String customOpenId;
	
	@ApiModelProperty(name = "unionid", value = "开放平台唯一标识")
	@TableField(value = "unionid")
	private String unionid;
	
	@ApiModelProperty(name = "isQuNum", value = "是否一排队 1 排队 0 未排队")
	@TableField(exist = false)
	private String isQuNum;
	
	
	@ApiModelProperty(name = "ddTimeLong", value = "等待时长")
	@TableField(exist = false)
	private String ddTimeLong;
	@ApiModelProperty(name = "ddRenNum", value = "等待人数")
	@TableField(exist = false)
	private Integer ddRenNum;
	
	@ApiModelProperty(name = "isYuYue", value = "是否预约 ， 1 已约，0 未预约")
	@TableField(exist = false)
	private String isYuYue;
	@ApiModelProperty(name = "couponId", value = "卡券Id")
	@TableField(exist = false)
	private String couponId;
	
	@ApiModelProperty(name = "支付状态", value = "1  支付")
	@TableField(exist = false)
	private String isPay;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		
		if(StringUtils.isNotEmpty(username)){
		     if(com.quiet.live.hall.utils.string.StringUtil.isBase64(username)){
		    	 try {
					username = new String(org.apache.commons.codec.binary.Base64.decodeBase64(username),"UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	return  username;
		     }
		}
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getInJobDate() {
		return inJobDate;
	}

	public void setInJobDate(Date inJobDate) {
		this.inJobDate = inJobDate;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	public String getOperId() {
		return operId;
	}

	public void setOperId(String operId) {
		this.operId = operId;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
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

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public BigDecimal getOrgPrice() {
		return orgPrice;
	}

	public void setOrgPrice(BigDecimal orgPrice) {
		this.orgPrice = orgPrice;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Integer getJsStatus() {
		return jsStatus;
	}

	public void setJsStatus(Integer jsStatus) {
		this.jsStatus = jsStatus;
	}

	public Integer getServiceTime() {
		return serviceTime;
	}

	public void setServiceTime(Integer serviceTime) {
		this.serviceTime = serviceTime;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Integer getJstotlfen() {
		return jstotlfen;
	}

	public void setJstotlfen(Integer jstotlfen) {
		this.jstotlfen = jstotlfen;
	}

	public Integer getPinglunnum() {
		return pinglunnum;
	}

	public void setPinglunnum(Integer pinglunnum) {
		this.pinglunnum = pinglunnum;
	}

	public String getCustomOpenId() {
		return customOpenId;
	}

	public void setCustomOpenId(String customOpenId) {
		this.customOpenId = customOpenId;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public String getIsQuNum() {
		return isQuNum;
	}

	public void setIsQuNum(String isQuNum) {
		this.isQuNum = isQuNum;
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

	public String getIsYuYue() {
		return isYuYue;
	}

	public void setIsYuYue(String isYuYue) {
		this.isYuYue = isYuYue;
	}

	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}

	public String getIsPay() {
		return isPay;
	}

	public void setIsPay(String isPay) {
		this.isPay = isPay;
	}
	
	
	
	
}
