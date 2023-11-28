CREATE DATABASE uberinterndemo;

USE uberinterndemo;

CREATE TABLE `user_table` (
  `uid` BIGINT NOT NULL AUTO_INCREMENT,
  `phone` varchar(255) NOT NULL,
  `role` ENUM('Driver', 'Passenger') NOT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `number_plate` varchar(255),
  `vehicle_type` varchar(255),
  `total_trip_length` double DEFAULT 0,
  `province` varchar(255) DEFAULT 'UNKNOWN',
  `city` varchar(255) DEFAULT 'UNKNOWN',
  PRIMARY KEY (`uid`),
  UNIQUE KEY `unique_phone` (`phone`)
);

SET SQL_SAFE_UPDATES = 0;
DELETE FROM `user_table`;
ALTER TABLE `user_table` AUTO_INCREMENT = 1;
SET SQL_SAFE_UPDATES = 1;

SELECT * FROM `user_table`;

CREATE TABLE `ride_table` (
  `rid` BIGINT NOT NULL AUTO_INCREMENT,
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `passenger_uid` BIGINT NOT NULL,
  `driver_uid` BIGINT,
  `mqtt_channel_name` VARCHAR(255),
  `ride_type` ENUM('economy', 'comfort', 'luxury') NOT NULL,
  `pickUpLong` DECIMAL(9,6),
  `pickUpLat` DECIMAL(9,6),
  `pickUpResolvedAddress` VARCHAR(255) NOT NULL,
  `destLong` DECIMAL(9,6),
  `destLat` DECIMAL(9,6),
  `destResolvedAddress` VARCHAR(255) NOT NULL,
  `status` ENUM('created', 'accepted', 'picking_up', 'picked_up', 'on_trip', 'arrived', 'ended', 'cancelled') NOT NULL,
  `driver_accept_time` DATETIME,
  `passenger_pickup_time` DATETIME,
  `end_point_arrival_time` DATETIME,
  `trip_cancellation_time` DATETIME,
  `total_trip_distance` DOUBLE DEFAULT 0,
  `oid` BIGINT,
  PRIMARY KEY (`rid`),
  FOREIGN KEY (`passenger_uid`) REFERENCES `user_table`(`uid`),
  FOREIGN KEY (`driver_uid`) REFERENCES `user_table`(`uid`),
  INDEX (`passenger_uid`),
  INDEX (`driver_uid`)
);

CREATE TABLE `track_table` (
  `tid` BIGINT NOT NULL AUTO_INCREMENT,
  `rid` BIGINT,
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `lng` DECIMAL(9,6),
  `lat` DECIMAL(9,6),
  `speed` DECIMAL(9,6),
  `asl` DECIMAL(9,6), 
  PRIMARY KEY (`tid`),
  FOREIGN KEY (`rid`) REFERENCES `ride_table`(`rid`)
);

SET SQL_SAFE_UPDATES = 0;
DELETE FROM `ride_table`;
ALTER TABLE `ride_table` AUTO_INCREMENT = 1;
SET SQL_SAFE_UPDATES = 1;
SELECT * FROM `ride_table`;

CREATE TABLE `order_table` (
    `oid` BIGINT NOT NULL AUTO_INCREMENT,
    `rid` BIGINT NOT NULL,                
    `CreationTime` TIMESTAMP DEFAULT CURRENT_TIMESTAMP, 
    `TotalPrice` DECIMAL(10,2),   
    `BasePrice` DECIMAL(10,2),   
    `TripAndFuelFee` DECIMAL(10,2), 
    `TimeFee` DECIMAL(10,2),      
    `SpecialLocationFee` DECIMAL(10,2), -- 特殊地点服务费（机场、高铁站、高速路）
    `DynamicPrice` DECIMAL(10,2), 
    `Status` ENUM('Unpaid', 'Paid', 'Refunding', 'Refunded') NOT NULL, 
    `PaymentPlatform` VARCHAR(255), 
    `PaymentPlatformTransactionID` VARCHAR(255), 
    `PaymentPlatformResult` VARCHAR(255),
    PRIMARY KEY (`oid`),
    FOREIGN KEY (`rid`) REFERENCES `ride_table`(`rid`)
);

CREATE TABLE `log_table` (
    `lid` BIGINT NOT NULL AUTO_INCREMENT,
    `CreationTime` TIMESTAMP DEFAULT CURRENT_TIMESTAMP, 
    `source` VARCHAR(255), 
    `level` VARCHAR(255), 
    `content` TEXT, 
    PRIMARY KEY (`lid`)
);

SELECT * FROM `order_table`;

SELECT CONCAT('DROP TABLE IF EXISTS ', table_name, ';') 
FROM information_schema.tables 
WHERE table_schema = 'uberinterndemo';
DROP TABLE `log`;
DROP TABLE IF EXISTS `ride`;
DROP TABLE IF EXISTS `user`;
DROP DATABASE uberinterndemo;