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
@ApiModel("j_service")
@TableName(value = "j_service")
public class JServiceVo implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/** 主键 */
	@TableId
	@ApiModelProperty(name = "id", value = "主键")
	private String id;

	/** 服务名称 */
	@TableField(value = "service_name")
	@ApiModelProperty(name = "serviceName", value = "服务名称")
	private String serviceName;

	/** 创建时间 */
	@TableField(value = "create_time")
	@ApiModelProperty(name = "createTime", value = "创建时间")
	private Date createTime;

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
	private int size = 15;
	private int current = 1;
}
