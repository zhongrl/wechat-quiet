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
@ApiModel("j_banner")
@TableName(value = "j_banner")
public class JBannerVo implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/** 主键 */
	@TableId
	@ApiModelProperty(name = "id", value = "主键")
	private String id;

	/** 链接 */
	@ApiModelProperty(name = "url", value = "链接")
	private String url;

	/** 图片链接 */
	@TableField(value = "img_url")
	@ApiModelProperty(name = "imgUrl", value = "图片链接")
	private String imgUrl;

	/** 创建时间 */
	@TableField(value = "create_time")
	@ApiModelProperty(name = "createTime", value = "创建时间")
	private Date createTime;

	/** 是否有效，1有效，0无效 */
	@TableField(value = "del_flag")
	@ApiModelProperty(name = "delFlag", value = "是否有效，1有效，0无效")
	private String delFlag;

	/** 名字 */
	@ApiModelProperty(name = "name", value = "名字")
	private String name;
	private int size = 15;
	private int current = 1;
}
