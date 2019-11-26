package com.quiet.live.hall.service.impl;

import com.quiet.live.hall.entity.JOrder;
import com.quiet.live.hall.mapper.JOrderMapper;
import com.quiet.live.hall.service.JOrderService;
import com.quiet.live.hall.vo.BiOrderVo;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author william zhong123
 * @since 2019-08-07
 */
@Service
public class JOrderServiceImpl extends ServiceImpl<JOrderMapper, JOrder> implements JOrderService {

	@Override
	public BiOrderVo selectOrderTotal(JOrder jOrder) {
		return this.baseMapper.selectOrderTotal(jOrder);
	}

}
