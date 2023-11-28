package com.julian.springbacken.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.julian.springbacken.Entity.LogEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogMapper extends BaseMapper<LogEntity> {
}

