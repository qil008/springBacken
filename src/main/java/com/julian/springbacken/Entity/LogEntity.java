package com.julian.springbacken.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("log_table")
public class LogEntity {
    @TableId(value = "lid", type = IdType.AUTO)
    private Long lid;

    @TableField("CreationTime")
    private java.time.LocalDateTime creationTime;

    @TableField("source")
    private String source;

    @TableField("level")
    private String level;

    @TableField("content")
    private String content;
}

