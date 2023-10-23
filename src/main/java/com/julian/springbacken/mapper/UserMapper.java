package com.julian.springbacken.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.julian.springbacken.Entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {
}

