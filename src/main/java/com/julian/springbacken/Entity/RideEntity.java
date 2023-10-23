package com.julian.springbacken.Entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
@Data
@TableName("ride_table")
public class RideEntity {
    @TableId(value = "rid", type = IdType.AUTO)
    private Long rid;

    @TableField(value = "create_time")
    private java.time.LocalDateTime createTime;

    @TableField(value = "passenger_uid")
    private Long passengerUid;

    @TableField(value = "driver_uid")
    private Long driverUid;

    @TableField(value = "mqtt_channel_name")
    private String mqttChannelName;

    @TableField(value = "ride_type")
    private String rideType;

    @TableField(value = "pickUpLong")
    private Double pickUpLong;

    @TableField(value = "pickUpLat")
    private Double pickUpLat;

    @TableField(value = "pickUpResolvedAddress")
    private String pickUpResolvedAddress;

    @TableField(value = "destLong")
    private Double destLong;

    @TableField(value = "destLat")
    private Double destLat;

    @TableField(value = "destResolvedAddress")
    private String destResolvedAddress;

    @TableField(value = "status")
    private String status;

    @TableField(value = "driver_accept_time")
    private java.time.LocalDateTime driverAcceptTime;

    @TableField(value = "passenger_pickup_time")
    private java.time.LocalDateTime passengerPickupTime;

    @TableField(value = "end_point_arrival_time")
    private java.time.LocalDateTime endPointArrivalTime;

    @TableField(value = "trip_cancellation_time")
    private java.time.LocalDateTime tripCancellationTime;

    @TableField(value = "total_trip_distance")
    private Double totalTripDistance;

    @TableField(value = "oid")
    private Long oid;
}
