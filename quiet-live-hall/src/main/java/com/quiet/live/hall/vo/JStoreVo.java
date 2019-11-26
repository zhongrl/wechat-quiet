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
@ApiModel("j_store")
@TableName(value = "j_store")
public class JStoreVo implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/** 主键 */
	@TableId
	@ApiModelProperty(name = "id", value = "主键")
	private String id;

	/** 门店名称 */
	@ApiModelProperty(name = "name", value = "门店名称")
	private String name;

	/** 门店地址 */
	@ApiModelProperty(name = "address", value = "门店地址")
	private String address;

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

	/** 开业时间 */
	@TableField(value = "ky_date")
	@ApiModelProperty(name = "kyDate", value = "开业时间")
	private Date kyDate;

	/** 状态，1 有效，0无效 */
	@TableField(value = "del_flag")
	@ApiModelProperty(name = "delFlag", value = "状态，1 有效，0无效")
	private String delFlag;

	/** 经纬度 */
	@TableField(value = "jw_du")
	@ApiModelProperty(name = "jwDu", value = "经纬度")
	private String jwDu;
	private int size = 15;
	private int current = 1;
}
