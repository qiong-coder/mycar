SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for Order
-- ----------------------------
DROP TABLE IF EXISTS `Order`;
CREATE TABLE `Order` (
  `id`              INT(10) NOT NULL AUTO_INCREMENT,
  `vid`             INT(10) NOT NULL,
  `begin`           DATETIME DEFAULT NULL,
  `end`             DATETIME DEFAULT NULL,
  `rent_address`    VARCHAR(50) NOT NULL,
  `return_address`  VARCHAR(50) NOT NULL,
  `name`            VARCHAR(50) NOT NULL,
  `identity`        VARCHAR(20) NOT NULL,
  `phone`           VARCHAR(20) NOT NULL,
  `bill`            TEXT DEFAULT NULL,
  `day_cost`        DOUBLE NOT NULL,
  `base_insurance`  DOUBLE NOT NULL,
  `free_insurance`  DOUBLE NOT NULL,
  `paid`            DOUBLE NOT NULL,
  `paid_info`       TEXT NOT NULL,
  `rbegin`          DATETIME DEFAULT NULL,
  `rend`            DATETIME DEFAULT NULL,
  `distance`        DOUBLE NOT NULL DEFAULT 0.0,
  `cost`            DOUBLE NOT NULL DEFAULT 0.0,
  `cost_info`       TEXT NOT NULL,
  `status`          INT(10) NOT NULL DEFAULT 0,
  `operator`        VARCHAR(20) NOT NULL,
  `create_time`     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;