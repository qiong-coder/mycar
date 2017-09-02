SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for Store
-- ----------------------------
DROP TABLE IF EXISTS `Store`;
CREATE TABLE `Store` (
  `id`              INT(10) NOT NULL AUTO_INCREMENT,
  `name`            VARCHAR(100) NOT NULL COMMENT '门店名称',
  `address`         VARCHAR(255) NOT NULL COMMENT '门店地址',
  `phone`           VARCHAR(255) NOT NULL COMMENT '门店电话',
  `is_delete`       INT(10) NOT NULL DEFAULT 0,
  `create_time`     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

INSERT `Store` (name, address, phone) VALUES ('义乌','义乌商贸城一期A14门市','4006470600,0470-6230866');
INSERT `Store` (name, address, phone) VALUES ('滿洲里','满洲里西郊国际机场','4006470600,0470-6230866');
INSERT `Store` (name, address, phone) VALUES ('套娃景区', '套娃景区酒店','4006470600,0470-6230866');