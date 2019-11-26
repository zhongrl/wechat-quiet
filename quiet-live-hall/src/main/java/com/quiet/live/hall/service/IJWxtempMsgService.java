package com.quiet.live.hall.service;

import com.baomidou.mybatisplus.service.IService;
import com.quiet.live.hall.entity.JWxtempMsg;
import com.quiet.live.hall.vo.WxtempVo;

/**
 *
 * JWxtempMsg 表数据服务层接口
 *
 */
public interface IJWxtempMsgService extends IService<JWxtempMsg> {

	public void sendJHTXMsg(WxtempVo wxtempVo);
	
	public void sendZFTXMsg(WxtempVo wxtempVo);
	
	public void sendZFSUCCESSTZMsg(WxtempVo wxtempVo);
	
	public void sendDAIZFTXMsg(WxtempVo wxtempVo);
	
	public void sendPDSUCCESSTXMsg(WxtempVo wxtempVo);
	
	public void sendCancelOrderMsg(WxtempVo wxtempVo);
}