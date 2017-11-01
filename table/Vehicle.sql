SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for Vehicle
-- ----------------------------
DROP TABLE IF EXISTS `Vehicle`;
CREATE TABLE `Vehicle` (
  `id`              INT(10) NOT NULL AUTO_INCREMENT,
  `viid`            INT(10) NOT NULL COMMENT '车系ID',
  `number`          VARCHAR(20) NOT NULL UNIQUE COMMENT '车牌',
  `description`     TEXT NOT NULL,
  `status`          INT(10) NOT NULL DEFAULT 0 COMMENT '车辆的状态',
  `begin`           DATETIME DEFAULT NULL COMMENT '租车或者修车的开始时间',
  `end`             DATETIME DEFAULT NULL COMMENT '租车或者修车的结束时间',
  `create_time`     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `is_delete`       INT(4) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

-- INSERT `Vehicle` (viid,number,description,status) VALUES(1,"京A10001","车辆描述",0);