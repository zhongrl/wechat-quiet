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
@ApiModel("j_email_msg")
@TableName(value = "j_email_msg")
public class JEmailMsgVo implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/**  */
	@TableId
	@ApiModelProperty(name = "id", value = "")
	private String id;

	/** 消息类型 */
	@TableField(value = "msg_type")
	@ApiModelProperty(name = "msgType", value = "消息类型")
	private String msgType;

	/** 创建时间 */
	@TableField(value = "create_time")
	@ApiModelProperty(name = "createTime", value = "创建时间")
	private Date createTime;

	/** 发送时间 */
	@TableField(value = "send_time")
	@ApiModelProperty(name = "sendTime", value = "发送时间")
	private Date sendTime;

	/** 发送状态 */
	@TableField(value = "send_status")
	@ApiModelProperty(name = "sendStatus", value = "发送状态")
	private Integer sendStatus;

	/** 标题 */
	@ApiModelProperty(name = "title", value = "标题")
	private String title;

	/**  */
	@ApiModelProperty(name = "value", value = "")
	private String value;

	/** 邮箱 */
	@ApiModelProperty(name = "email", value = "邮箱")
	private String email;

	/** 类型 */
	@ApiModelProperty(name = "type", value = "类型")
	private String type;

	/** 内容 */
	@ApiModelProperty(name = "content", value = "内容")
	private String content;

	/** 用户id */
	@TableField(value = "user_id")
	@ApiModelProperty(name = "userId", value = "用户id")
	private String userId;

	/** 用户名称 */
	@TableField(value = "user_name")
	@ApiModelProperty(name = "userName", value = "用户名称")
	private String userName;

	/** 错误信息 */
	@TableField(value = "error_msg")
	@ApiModelProperty(name = "errorMsg", value = "错误信息")
	private String errorMsg;

	/** 抄送邮件 */
	@TableField(value = "to_email")
	@ApiModelProperty(name = "toEmail", value = "抄送邮件")
	private String toEmail;

	/** 微信ID */
	@TableField(value = "open_id")
	@ApiModelProperty(name = "openId", value = "微信ID")
	private String openId;

	/**  */
	@TableField(value = "file_path")
	@ApiModelProperty(name = "filePath", value = "")
	private String filePath;
	private int size = 15;
	private int current = 1;
}
