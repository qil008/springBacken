package com.julian.springbacken.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.julian.springbacken.Entity.TrackEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TrackMapper extends BaseMapper<TrackEntity> {
}