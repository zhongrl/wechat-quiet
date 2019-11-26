package com.quiet.live.hall.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
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
@ApiModel("j_email_msg")
@TableName(value = "j_email_msg")
public class JEmailMsg implements Serializable {

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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Integer getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(Integer sendStatus) {
		this.sendStatus = sendStatus;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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
	
	

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getToEmail() {
		return toEmail;
	}

	public void setToEmail(String toEmail) {
		this.toEmail = toEmail;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	

}
