package com.julian.springbacken.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.julian.springbacken.Entity.RideEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RideMapper extends BaseMapper<RideEntity> {
}

