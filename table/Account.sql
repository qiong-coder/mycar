SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for Account
-- ----------------------------
DROP TABLE IF EXISTS `Account`;
CREATE TABLE `Account` (
  `id`              INT(10) NOT NULL AUTO_INCREMENT,
  `username`        VARCHAR(20) UNIQUE NOT NULL COMMENT '用户名称',
  `password`        VARCHAR(20) NOT NULL COMMENT '登陆密码',
  `name`            VARCHAR(20) NOT NULL COMMENT '用户姓名',
  `phone`           VARCHAR(20) NOT NULL COMMENT '联系电话',
  `store`           INT(10) NOT NULL COMMENT '门店编号',
  `roles`           INT(10) NOT NULL COMMENT '角色信息',
  `status`          INT(10) NOT NULL DEFAULT 0,
  `create_time`     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

INSERT Account (username,password,name,phone,roles,store) VALUES("admin","administrator","超级管理员","",0, 0);