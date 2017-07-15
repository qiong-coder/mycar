SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for Vehicle
-- ----------------------------
DROP TABLE IF EXISTS `Vehicle`;
CREATE TABLE `Vehicle` (
  `id`              INT(10) NOT NULL AUTO_INCREMENT,
  `vinfo`           INT(10) NOT NULL,
  `number`          VARCHAR(20) NOT NULL,
  `status`          INT(10) NOT NULL DEFAULT 0,
  `begin`           TIMESTAMP DEFAULT NULL,
  `end`             TIMESTAMP DEFAULT NULL,
  `create_time`     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;