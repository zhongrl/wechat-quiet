package com.quiet.live.hall.vo;

import java.io.Serializable;

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
@ApiModel("j_tmp_msg")
@TableName(value = "j_tmp_msg")
public class JTmpMsg implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/**  */
	@TableId
	@ApiModelProperty(name = "id", value = "")
	private String id;

	/**  */
	@TableField(value = "temp_type")
	@ApiModelProperty(name = "tempType", value = "")
	private String tempType;

	/**  */
	@ApiModelProperty(name = "titile", value = "")
	private String titile;

}
