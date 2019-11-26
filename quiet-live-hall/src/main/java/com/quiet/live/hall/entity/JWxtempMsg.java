package com.quiet.live.hall.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

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
@ApiModel("j_wxtemp_msg")
@TableName(value = "j_wxtemp_msg")
public class JWxtempMsg implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/**  */
	@TableId
	@ApiModelProperty(name = "id", value = "")
	private String id;

	/**  */
	@TableField(value = "msg_name")
	@ApiModelProperty(name = "msg_name", value = "")
	private String msgName;
	/**  */
	@TableField(value = "msg_type")
	@ApiModelProperty(name = "msgType", value = "")
	private String msgType;
	
	@TableField(value = "title")
	private String  title;
	
	@TableField(value = "remark")
	private String remark;
	
	@TableField(value = "url")
	private String url;



}
