package com.julian.springbacken.Entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
@Data
@TableName("order_table")
public class OrderEntity {
    @TableId(value = "oid", type = IdType.AUTO)
    private Long oid;

    @TableField(value = "rid")
    private Long rid;

    @TableField(value = "CreationTime")
    private java.time.LocalDateTime CreationTime;

    @TableField(value = "TotalPrice")
    private java.math.BigDecimal TotalPrice;

    @TableField(value = "BasePrice")
    private java.math.BigDecimal BasePrice;

    @TableField(value = "TripAndFuelFee")
    private java.math.BigDecimal TripAndFuelFee;

    @TableField(value = "TimeFee")
    private java.math.BigDecimal TimeFee;

    @TableField(value = "SpecialLocationFee")
    private java.math.BigDecimal SpecialLocationFee;

    @TableField(value = "DynamicPrice")
    private java.math.BigDecimal DynamicPrice;

    @TableField(value = "Status")
    private String Status;

    @TableField(value = "PaymentPlatform")
    private String PaymentPlatform;

    @TableField(value = "PaymentPlatformTransactionID")
    private String PaymentPlatformTransactionID;

    @TableField(value = "PaymentPlatformResult")
    private String PaymentPlatformResult;
}
