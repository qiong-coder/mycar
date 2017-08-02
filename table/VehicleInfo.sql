SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for VehicleInfo
-- ----------------------------
DROP TABLE IF EXISTS `VehicleInfo`;
CREATE TABLE `VehicleInfo` (
  `id`              INT(10) NOT NULL AUTO_INCREMENT,
  `name`            VARCHAR(50) NOT NULL,
  `brand`           VARCHAR(50) NOT NULL,
  `displacement`    VARCHAR(50) NOT NULL,
  `gearbox`         VARCHAR(50) NOT NULL,
  `boxes`           VARCHAR(50) NOT NULL,
  `manned`          VARCHAR(50) NOT NULL,
  `description`     TEXT NOT NULL,
  `picture`         VARCHAR(100) NOT NULL,
  `create_time`     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

INSERT `VehicleInfo`(name,brand,displacement,gearbox,boxes,manned,description,picture)
VALUES("X6","宝马","2.0T","自动挡","3厢","5","豪车","x6.jpg")