SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for Order
-- ----------------------------
DROP TABLE IF EXISTS `Admin`;
CREATE TABLE `Admin` (
  `id`              INT(10) NOT NULL AUTO_INCREMENT,
  `name`            VARCHAR(20) NOT NULL COMMENT '用户名称',
  `password`        VARCHAR(10) NOT NULL COMMENT '登陆密码',
  `status`          INT(10) NOT NULL DEFAULT 0,
  `create_time`     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;