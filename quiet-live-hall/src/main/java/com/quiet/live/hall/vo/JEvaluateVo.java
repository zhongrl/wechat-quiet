package com.quiet.live.hall.vo;

import java.io.Serializable;
import java.util.Date;

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
@ApiModel("j_evaluate")
@TableName(value = "j_evaluate")
public class JEvaluateVo implements Serializable {

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
	@ApiModelProperty(name = "userId", value = "技术ID")
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
	@ApiModelProperty(name = "jsManyi", value = "技师满意度")
	private Integer jsManyi;

	/** 服务满意度 */
	@TableField(value = "service_yx")
	@ApiModelProperty(name = "serviceYx", value = "服务满意度")
	private Integer serviceYx;

	/** 沟通满意度 */
	@TableField(value = "gt_dw")
	@ApiModelProperty(name = "gtDw", value = "沟通满意度")
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
	private int size = 5;
	private int current = 1;
}
