package com.quiet.live.hall.service;

import com.baomidou.mybatisplus.service.IService;
import com.quiet.live.hall.entity.JEmailMsg;

/**
 *
 * JEmailMsg 表数据服务层接口
 *
 */
public interface IJEmailMsgService extends IService<JEmailMsg> {

	public void sendMsg();
	
	public void sendDaizfMsg();
	
	public void sendJsMsg(String jsUserId);

}