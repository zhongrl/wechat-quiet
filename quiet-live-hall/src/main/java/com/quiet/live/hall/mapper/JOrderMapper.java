package com.quiet.live.hall.mapper;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.quiet.live.hall.entity.JOrder;
import com.quiet.live.hall.vo.BiOrderVo;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author william zhong123
 * @since 2019-08-07
 */
public interface JOrderMapper extends BaseMapper<JOrder> {

	public BiOrderVo selectOrderTotal(@Param("dto")JOrder jOrder);
}
