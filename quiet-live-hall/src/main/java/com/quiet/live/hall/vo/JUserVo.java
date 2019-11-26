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
@ApiModel("j_user")
@TableName(value = "j_user")
public class JUserVo implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/** 主键 */
	@TableId
	@ApiModelProperty(name = "id", value = "主键")
	private String id;

	/** 用户名称 */
	@ApiModelProperty(name = "username", value = "用户名称")
	private String username;

	/** 密码 */
	@ApiModelProperty(name = "password", value = "密码")
	private String password;

	/** 用户类型，1超级管理员，2门店、运营、3、技师，4 客户 */
	@TableField(value = "user_type")
	@ApiModelProperty(name = "userType", value = "用户类型，1超级管理员，2门店、运营、3、技师，4 客户")
	private String userType;

	/** 技师等级 */
	@ApiModelProperty(name = "level", value = "技师等级")
	private String level;

	/** 手机号 */
	@ApiModelProperty(name = "mobile", value = "手机号")
	private String mobile;

	/** 服务ID */
	@TableField(value = "service_id")
	@ApiModelProperty(name = "serviceId", value = "服务ID")
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
	@ApiModelProperty(name = "inJobDate", value = "入职时间")
	private Date inJobDate;

	/** 账户 */
	@ApiModelProperty(name = "account", value = "账户")
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
	@ApiModelProperty(name = "delFlag", value = "是否有效 1 有效，0无效")
	private String delFlag;

	/** 门店ID */
	@TableField(value = "store_id")
	@ApiModelProperty(name = "storeId", value = "门店ID")
	private String storeId;

	/** 门店名称 */
	@TableField(value = "store_name")
	@ApiModelProperty(name = "storeName", value = "门店名称")
	private String storeName;

	/** 技师头像 */
	@TableField(value = "head_img")
	@ApiModelProperty(name = "headImg", value = "技师头像")
	private String headImg;

	/**  */
	@TableField(value = "org_price")
	@ApiModelProperty(name = "orgPrice", value = "")
	private BigDecimal orgPrice;

	/** 价格 */
	@ApiModelProperty(name = "price", value = "价格")
	private BigDecimal price;

	/** 微信openId */
	@TableField(value = "open_id")
	@ApiModelProperty(name = "openId", value = "微信openId")
	private String openId;

	/**  */
	@TableField(value = "login_time")
	@ApiModelProperty(name = "loginTime", value = "")
	private Date loginTime;
	private int size = 15;
	private int current = 1;
	
	private  String code;
	
	@TableField(value = "js_status")
	@ApiModelProperty(name = "技师状态", value = "1 可接单，2 停止接单，3 休息中")
	private Integer jsStatus;
	
	private String rawData;
	private String signature;
	
	private String encryptedData;
	private String iv;
	
	@ApiModelProperty(name = "支付状态", value = "1  支付")
	private String isPay;
	
}
