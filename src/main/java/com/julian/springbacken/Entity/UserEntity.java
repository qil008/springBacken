package com.julian.springbacken.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user_table")
public class UserEntity {

    @TableId(value = "uid", type = IdType.AUTO)
    private Long uid;

    @TableField(value = "phone")
    private String phone;

    @TableField(value = "role")
    private String role;

    @TableField(value = "password")
    private String password;

    @TableField(value = "username")
    private String username;

    @TableField(value = "number_plate")
    private String numberPlate;

    @TableField(value = "vehicle_type")
    private String vehicleType;

    @TableField(value = "total_trip_length")
    private Double totalTripLength;

    @TableField(value = "province")
    private String province;

    @TableField(value = "city")
    private String city;
}

