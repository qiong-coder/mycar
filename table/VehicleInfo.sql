SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for VehicleInfo
-- ----------------------------
DROP TABLE IF EXISTS `VehicleInfo`;
CREATE TABLE `VehicleInfo` (
  `id`              INT(10) NOT NULL AUTO_INCREMENT,
  `name`            VARCHAR(50) NOT NULL,
  `brand`           VARCHAR(50) NOT NULL,
  `displacement`    DOUBLE NOT NULL,
  `gearbox`         VARCHAR(50) NOT NULL,
  `boxes`           VARCHAR(50) NOT NULL,
  `manned`          INT(10) NOT NULL,
  `description`     TEXT NOT NULL,
  `day_cost`        INT(10) NOT NULL,
  `base_insurance`  INT(10) NOT NULL,
  `free_insurance`  INT(10) NOT NULL,
  `create_time`     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;