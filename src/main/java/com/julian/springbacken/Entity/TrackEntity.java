package com.julian.springbacken.Entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
@Data
@TableName("track_table")
public class TrackEntity {
    @TableId(value = "tid", type = IdType.AUTO)
    private Long tid;

    @TableField(value = "rid")
    private Long rid;

    @TableField(value = "create_time")
    private java.time.LocalDateTime createTime;

    @TableField(value = "lng")
    private Double lng;

    @TableField(value = "lat")
    private Double lat;

    @TableField(value = "speed")
    private Double speed;

    @TableField(value = "asl")
    private Double asl;
}
