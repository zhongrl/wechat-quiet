package com.quiet.live.hall.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

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
@ApiModel("j_evaluate")
@TableName(value = "j_evaluate")
public class JEvaluate implements Serializable {

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

	/** 技术ID */
	@TableField(value = "user_id")
	@ApiModelProperty(name = "技师Id", value = "技师ID",required = true)
	private String userId;

	/** 技师名字 */
	@TableField(value = "user_name")
	@ApiModelProperty(name = "userName", value = "技师名字")
	private String userName;

	/** 状态 1，显示 ，2 隐藏，3 删除 */
	@ApiModelProperty(name = "status", value = "状态 1，显示 ，2 隐藏，3 删除")
	private Integer status;

	/** 客户微信头像 */
	@TableField(value = "wx_head_img")
	@ApiModelProperty(name = "wxHeadImg", value = "客户微信头像")
	private String wxHeadImg;

	/** 客户微信名称 */
	@TableField(value = "wx_user_name")
	@ApiModelProperty(name = "wxUserName", value = "客户微信名称")
	private String wxUserName;

	/** 技师满意度 */
	@TableField(value = "js_manyi")
	@ApiModelProperty(name = "jsManyi", value = "技师满意度",required = true)
	private Integer jsManyi;

	/** 服务满意度 */
	@TableField(value = "service_yx")
	@ApiModelProperty(name = "serviceYx", value = "服务满意度",required = true)
	private Integer serviceYx;

	/** 沟通满意度 */
	@TableField(value = "gt_dw")
	@ApiModelProperty(name = "gtDw", value = "沟通满意度",required = true)
	private Integer gtDw;

	/** 评论 */
	@ApiModelProperty(name = "remark", value = "评论")
	private String remark;

	/** 图片1 */
	@ApiModelProperty(name = "img1", value = "图片1")
	private String img1;

	/** 图片2 */
	@ApiModelProperty(name = "img2", value = "图片2")
	private String img2;

	/** 图片3 */
	@ApiModelProperty(name = "img3", value = "图片3")
	private String img3;
	
	@ApiModelProperty(name = "微信openId", value = "openId")
	private String openId;
	
	@ApiModelProperty(name = "技师服务星", value = "技师服务星")
	@TableField(value = "avg_num")
	private BigDecimal avgNum;

	@ApiModelProperty(name = "订单id", value = "订单id")
	@TableField(value = "order_id")
	private String orderId;

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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		
		if(StringUtils.isNotEmpty(userName)){
		     if(com.quiet.live.hall.utils.string.StringUtil.isBase64(userName)){
		    	 try {
					userName = new String(org.apache.commons.codec.binary.Base64.decodeBase64(userName),"UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	return  userName;
		     }
		}
		return userName;
	}

	public void setUserName(String userName) {
		 this.userName = userName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getWxHeadImg() {
		return wxHeadImg;
	}

	public void setWxHeadImg(String wxHeadImg) {
		this.wxHeadImg = wxHeadImg;
	}
	
	public String getWxUserName() {
		
		if(StringUtils.isNotEmpty(wxUserName)){
		     if(com.quiet.live.hall.utils.string.StringUtil.isBase64(wxUserName)){
		    	 try {
					wxUserName = new String(org.apache.commons.codec.binary.Base64.decodeBase64(wxUserName),"UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	return  wxUserName;
		     }
		}
		return wxUserName;
	}

	public void setWxUserName(String wxUserName) {
		 this.wxUserName = wxUserName;
	}


	public Integer getJsManyi() {
		return jsManyi;
	}

	public void setJsManyi(Integer jsManyi) {
		this.jsManyi = jsManyi;
	}

	public Integer getServiceYx() {
		return serviceYx;
	}

	public void setServiceYx(Integer serviceYx) {
		this.serviceYx = serviceYx;
	}

	public Integer getGtDw() {
		return gtDw;
	}

	public void setGtDw(Integer gtDw) {
		this.gtDw = gtDw;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getImg1() {
		return img1;
	}

	public void setImg1(String img1) {
		this.img1 = img1;
	}

	public String getImg2() {
		return img2;
	}

	public void setImg2(String img2) {
		this.img2 = img2;
	}

	public String getImg3() {
		return img3;
	}

	public void setImg3(String img3) {
		this.img3 = img3;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public BigDecimal getAvgNum() {
		return avgNum;
	}

	public void setAvgNum(BigDecimal avgNum) {
		this.avgNum = avgNum;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	
}
