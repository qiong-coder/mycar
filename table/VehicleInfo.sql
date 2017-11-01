SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for VehicleInfo
-- ----------------------------
DROP TABLE IF EXISTS `VehicleInfo`;
CREATE TABLE `VehicleInfo` (
  `id`              INT(10) NOT NULL AUTO_INCREMENT,
  `name`            VARCHAR(50) NOT NULL,
  `displacement`    VARCHAR(50) NOT NULL,
  `gearbox`         VARCHAR(50) NOT NULL,
  `boxes`           VARCHAR(50) NOT NULL,
  `manned`          VARCHAR(50) NOT NULL,
  `spare`           INT(10) NOT NULL DEFAULT 0 COMMENT '预留备用车数量',
  `description`     TEXT NOT NULL,
  `picture`         VARCHAR(100) NOT NULL,
  `is_delete`       INT(4) NOT NULL DEFAULT 0,
  `create_time`     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

-- INSERT `VehicleInfo`(name,displacement,gearbox,boxes,manned,description,picture) VALUES("宝马-X6","2.0T","自动挡","3厢","5","豪车","x6.jpg");