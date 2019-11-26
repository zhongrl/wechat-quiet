package com.quiet.live.hall.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.quiet.live.hall.entity.JEmailMsg;
import com.quiet.live.hall.entity.JOrder;
import com.quiet.live.hall.mapper.JEmailMsgMapper;
import com.quiet.live.hall.service.IJEmailMsgService;
import com.quiet.live.hall.service.IJWxtempMsgService;
import com.quiet.live.hall.service.JOrderService;
import com.quiet.live.hall.utils.DateUtils;
import com.quiet.live.hall.utils.string.StringUtil;
import com.quiet.live.hall.vo.WxtempVo;

/**
 *
 * JEmailMsg 表数据服务层接口实现类
 *
 */
@Service
public class JEmailMsgServiceImpl extends ServiceImpl<JEmailMsgMapper, JEmailMsg> implements IJEmailMsgService {

	@Autowired
	IJWxtempMsgService iJWxtempMsgService;
	
	@Autowired
	JOrderService jOrderService;
	
	
	@Override
	public void sendMsg() {
		//每30分钟发送一次
		JOrder jOrde = new JOrder();
		List<JOrder> list = jOrderService.selectList(new EntityWrapper<JOrder>(jOrde).between("create_time", DateFormatUtils.format(new Date(), "yyyy-MM-dd 00:00:00"), DateFormatUtils.format(new Date(), "yyyy-MM-dd 23:59:59")).and("status = 4 OR status = 3").orderAsc(Collections.singleton("ordernum")));
		if(!CollectionUtils.isEmpty(list)){
			list.stream().forEach(jorder->{
				if(StringUtils.equals("4", jorder.getStatus())){
					WxtempVo wxtempVo = new WxtempVo();
					wxtempVo.setOpenId(jorder.getCustOpenid());
					wxtempVo.setKeyword1(jorder.getLineUpNumber());
					wxtempVo.setUserName(jorder.getCustUsername());
					wxtempVo.setKeyword2(DateUtils.formatDate(jorder.getCreateTime()));
					JOrder js = new JOrder();
					js.setJsUserId(jorder.getJsUserId());
					js.setStatus("3");
					JOrder jouser = jOrderService.selectOne(new EntityWrapper<JOrder>(js));
					JOrder ordreJorder = new JOrder();
					ordreJorder.setJsUserId(jorder.getJsUserId());
					List<JOrder> jos = jOrderService.selectList(new EntityWrapper<JOrder>(ordreJorder).between("create_time", DateFormatUtils.format(new Date(), "yyyy-MM-dd 00:00:00"), DateFormatUtils.format(new Date(), "yyyy-MM-dd 23:59:59")).and("status = 4 OR status = 3").orderAsc(Collections.singleton("ordernum")));
					if(CollectionUtils.isNotEmpty(jos)){
						
						// 有人在上钟
						int renshu  = jorder.getOrdernum() - jos.get(0).getOrdernum();
						int rs = renshu;
						if(null != jouser){
							renshu = renshu- 1;
							if(renshu < 0 ){
								renshu = 0;
							}
							
							int fen = (int) ((jouser.getServiceTime()+ 15) - DateUtils.getMinuteCompan(DateUtils.convertDateToLDT(
									jouser.getUpdateTime()),DateUtils.convertDateToLDT(new Date())));
							if(jorder.getServiceTime() > 60){
								int xiaoshi = renshu * 15;
								fen = xiaoshi + fen ;
								int xs = 0;
								if(fen > 60){
									xs =  (fen/60);
									if(xs > 1){
										fen = fen - (xs * 60);
										renshu = renshu + xs ; 
									}else if(xs == 1){
										renshu = renshu + xs ;
									}
								}
							}
							if(fen > 0 ){
								wxtempVo.setKeyword3("预计" + renshu+ "小时" + fen+ "分钟");
							}else{
								wxtempVo.setKeyword3("预计" + (renshu == 0 ? "0分钟": renshu +"小时"));
							}
						}else{
							wxtempVo.setKeyword3("预计" + (rs == 0 ? "0分钟": rs +"小时"));
						}
						
						wxtempVo.setKeyword4(String.valueOf(rs) + "人");
						if(rs > 1 && null != jouser && StringUtil.equals(jouser.getStatus(), "4")){
							int fen = (int) ((jos.get(0).getServiceTime() + 15) - DateUtils.getMinuteCompan(DateUtils.convertDateToLDT(
									jouser.getUpdateTime()),DateUtils.convertDateToLDT(new Date())));
							wxtempVo.setKeyword3("预计0小时" + fen+ "分钟");
							wxtempVo.setTitle("前面排队人数较多，可在附近转转。\r\n当前面等待20分钟时，再到店等候，可减少等候时间哦～");
							iJWxtempMsgService.sendJHTXMsg(wxtempVo);
						}else{
							if(rs == 0){
								wxtempVo.setTitle("已经到你啦，技师在店里等你，快来吧～\r\n如叫号超过5分钟，你的排队将进行延后处理。");
								// 技师没有人在上钟
								wxtempVo.setKeyword3("到您了亲~");
								wxtempVo.setKeyword4("0人");
							}else if(rs == 1){
								wxtempVo.setTitle("下一个就是你啦，建议提前20分钟到店等候，避免等候时间延长。\r\n如叫号超过5分钟，你的排队将进行延后处理。");
							}else{
								wxtempVo.setTitle("前面排队人数较多，可在附近转转。\r\n当前面等待20分钟时，再到店等候，可减少等候时间哦～");
								iJWxtempMsgService.sendJHTXMsg(wxtempVo);
							}
							iJWxtempMsgService.sendJHTXMsg(wxtempVo);
						}
						
					}else{
						wxtempVo.setTitle("已经到你啦，技师在店里等你，快来吧～\r\n如叫号超过5分钟，你的排队将进行延后处理。");
						// 技师没有人在上钟
						wxtempVo.setKeyword3("到您了亲~");
						wxtempVo.setKeyword4("0人");
						iJWxtempMsgService.sendJHTXMsg(wxtempVo);
					}
					
					
					
				}
			});
		}
	}
	
	

	@Override
	public void sendJsMsg(String jsUserId) {
		//每30分钟发送一次
		JOrder jOrde = new JOrder();
		jOrde.setJsUserId(jsUserId);
		List<JOrder> list = jOrderService.selectList(new EntityWrapper<JOrder>(jOrde).between("create_time", DateFormatUtils.format(new Date(), "yyyy-MM-dd 00:00:00"), DateFormatUtils.format(new Date(), "yyyy-MM-dd 23:59:59")).and("status = 4").orderAsc(Collections.singleton("ordernum")));
		if(!CollectionUtils.isEmpty(list)){
			JOrder jorders = list.get(0);
			final Map<String,Object> map = new HashMap<String,Object>();
			list.stream().forEach(jorder -> {
				if(StringUtils.equals("4", jorder.getStatus())){
					
					WxtempVo wxtempVo = new WxtempVo();
					wxtempVo.setUserName(jorder.getCustUsername());
					wxtempVo.setOpenId(jorder.getCustOpenid());
					wxtempVo.setKeyword1(jorder.getLineUpNumber());
					wxtempVo.setKeyword2(DateUtils.formatDate(jorder.getCreateTime()));
					if(!map.containsKey(jorders.getId())){
						wxtempVo.setTitle("已经到你啦，技师在店里等你，快来吧～\r\n如叫号超过5分钟，你的排队将进行延后处理。");
						wxtempVo.setKeyword3("到您了亲~");
						wxtempVo.setKeyword4("0人");
						map.put(jorders.getId(), jorders);
						iJWxtempMsgService.sendJHTXMsg(wxtempVo);
					}else{
						JOrder js = new JOrder();
						js.setJsUserId(jorder.getJsUserId());
						js.setStatus("3");
						JOrder jouser = jOrderService.selectOne(new EntityWrapper<JOrder>(js));
							
							// 有人在上钟
							int renshu  = jorder.getOrdernum() - jorders.getOrdernum();
							int rs = renshu;
							renshu = renshu- 1;
							if(renshu < 0 ){
								renshu = 0;
							}
							if(null != jouser){
								int fen = (int) ((jouser.getServiceTime()+ 15) - DateUtils.getMinuteCompan(DateUtils.convertDateToLDT(
										jouser.getUpdateTime()),DateUtils.convertDateToLDT(new Date())));
								if(jorder.getServiceTime() > 60){
									int xiaoshi = renshu * 15;
									fen = xiaoshi + fen ;
									int xs = 0;
									if(fen > 60){
										xs =  (fen/60);
										if(xs > 1){
											fen = fen - (xs * 60);
											renshu = renshu + xs ; 
										}else if(xs == 1){
											renshu = renshu + xs ;
										}
									}
								}
								if(fen > 0 ){
									wxtempVo.setKeyword3("预计" + renshu+ "小时" + fen+ "分钟");
								}else{
									wxtempVo.setKeyword3("预计" + (renshu == 0 ? "0分钟": renshu +"小时"));
								}
							}else{
								wxtempVo.setKeyword3("预计" + (rs == 0 ? "0分钟": rs +"小时"));
							}
							
							wxtempVo.setKeyword4(String.valueOf(rs) + "人");
							if(rs > 1 && null != jouser && StringUtil.equals(jouser.getStatus(), "4")){
								int fen = (int) ((jorders.getServiceTime() + 15) - DateUtils.getMinuteCompan(DateUtils.convertDateToLDT(
										jouser.getUpdateTime()),DateUtils.convertDateToLDT(new Date())));
								wxtempVo.setKeyword3("预计0小时" + fen+ "分钟");
								wxtempVo.setTitle("前面排队人数较多，可在附近转转。\r\n当前面等待20分钟时，再到店等候，可减少等候时间哦～");
								iJWxtempMsgService.sendJHTXMsg(wxtempVo);
							}else{
								if(rs == 0){
									wxtempVo.setTitle("已经到你啦，技师在店里等你，快来吧～\r\n如叫号超过5分钟，你的排队将进行延后处理。");
									// 技师没有人在上钟
									wxtempVo.setKeyword3("到您了亲~");
									wxtempVo.setKeyword4("0人");
								}else if(rs == 1){
									wxtempVo.setTitle("下一个就是你啦，建议提前20分钟到店等候，避免等候时间延长。\r\n如叫号超过5分钟，你的排队将进行延后处理。");
								}
								iJWxtempMsgService.sendJHTXMsg(wxtempVo);
							}
						}
				}
			});
		}
	}

	@Override
	public void sendDaizfMsg() {
		//每30分钟发送一次
				JOrder jOrde = new JOrder();
				jOrde.setStatus("2");
				List<JOrder> list = jOrderService.selectList(new EntityWrapper<JOrder>(jOrde));
				if(!CollectionUtils.isEmpty(list)){
					list.stream().forEach(jorder->{
						WxtempVo wxtempVo = new WxtempVo();
						wxtempVo.setOpenId(jorder.getCustOpenid());
						wxtempVo.setTitle("尊敬"+jorder.getCustUsername()+"您好！点击“详情”,确认订单支付吧~");
						wxtempVo.setKeyword1(jorder.getStoreName());
						wxtempVo.setKeyword2(DateUtils.formatDate(jorder.getUpdateTime()));
						wxtempVo.setKeyword3(jorder.getPrice().toString());
						wxtempVo.setUserName(jorder.getCustUsername());
						iJWxtempMsgService.sendDAIZFTXMsg(wxtempVo);
					});
				}
		
	}


}