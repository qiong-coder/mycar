SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for Store
-- ----------------------------
DROP TABLE IF EXISTS `Store`;
CREATE TABLE `Store` (
  `id`              INT(10) NOT NULL AUTO_INCREMENT,
  `address`         VARCHAR(50) NOT NULL COMMENT '门店地址',
  `phone`           VARCHAR(20) NOT NULL COMMENT '门店电话',
  `create_time`     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;