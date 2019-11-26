package com.quiet.live.hall.wx.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.quiet.live.hall.entity.JEmailMsg;
import com.quiet.live.hall.service.IAuthService;
import com.quiet.live.hall.service.IJEmailMsgService;
import com.quiet.live.hall.utils.json.JSONUtils;
import com.quiet.live.hall.vo.WxtempVo;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;

@Slf4j
@Component
public class ScheduledService {

	@Autowired
	public WxMpService wxMpService;
	
	@Autowired
	IAuthService iAuthService;
	
	@Autowired
	IJEmailMsgService iJEmailMsgService;

//	@Scheduled(cron = "0 0 0/1 * * *")
//	public void refshToken() {
//		log.info("刷新token");
//		try {
//			wxMpService.getAccessToken(true);
//			log.info("刷新token成功");
//		} catch (WxErrorException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			log.error("刷新token失败", e);
//		}
//
//	}

	@Scheduled(cron = "0 0/30 * * * *")
	public void trimSendMsg() {
		log.info("推送消息");
		JEmailMsg jes = new JEmailMsg();
		jes.setSendStatus(0);
		List<JEmailMsg> list = iJEmailMsgService.selectList(new EntityWrapper<JEmailMsg>(jes));
		if(!CollectionUtils.isEmpty(list)){
			list.stream().forEach(wx ->{
				try {
					WxtempVo  wxtempVo= JSONUtils.jsonToBean(wx.getContent(), WxtempVo.class);
					wxtempVo.setMsgId(wx.getId());
					iAuthService.sendWxMsg(wxtempVo);
					wx.setSendStatus(1);
					wx.setSendTime(new Date());
					iJEmailMsgService.updateById(wx);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			
		}
		
		log.info("推送成功");
	}
}
