package com.quiet.live.hall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.quiet.live.hall.constants.Constant;
import com.quiet.live.hall.entity.JWxtempMsg;
import com.quiet.live.hall.mapper.JWxtempMsgMapper;
import com.quiet.live.hall.service.IAuthService;
import com.quiet.live.hall.service.IJWxtempMsgService;
import com.quiet.live.hall.vo.WxtempVo;

/**
 *
 * JWxtempMsg 表数据服务层接口实现类
 *
 */
@Service
public class JWxtempMsgServiceImpl extends ServiceImpl<JWxtempMsgMapper, JWxtempMsg> implements IJWxtempMsgService {
	
	@Autowired
	IAuthService iAuthService;

	@Override
	public void sendJHTXMsg(WxtempVo wxtempVo) {
		wxtempVo.setMsgType(Constant.WX_TEMP_MSG.JH_TX);
		iAuthService.sendWxMsg(wxtempVo);
		
	}

	@Override
	public void sendZFTXMsg(WxtempVo wxtempVo) {
		wxtempVo.setMsgType(Constant.WX_TEMP_MSG.ZF_TX);
		iAuthService.sendWxMsg(wxtempVo);
		
	}

	@Override
	public void sendZFSUCCESSTZMsg(WxtempVo wxtempVo) {
		wxtempVo.setMsgType(Constant.WX_TEMP_MSG.ZF_SUCCESS_TZ);
		iAuthService.sendWxMsg(wxtempVo);
		
	}

	@Override
	public void sendDAIZFTXMsg(WxtempVo wxtempVo) {
		wxtempVo.setMsgType(Constant.WX_TEMP_MSG.DAI_ZF_TX);
		iAuthService.sendWxMsg(wxtempVo);
		
	}

	@Override
	public void sendPDSUCCESSTXMsg(WxtempVo wxtempVo) {
		wxtempVo.setMsgType(Constant.WX_TEMP_MSG.PD_SUCCESS_TX);
		iAuthService.sendWxMsg(wxtempVo);
		
	}

	@Override
	public void sendCancelOrderMsg(WxtempVo wxtempVo) {
		wxtempVo.setMsgType(Constant.WX_TEMP_MSG.CANCLE_ORDER);
		iAuthService.sendWxMsg(wxtempVo);
	}

	


}