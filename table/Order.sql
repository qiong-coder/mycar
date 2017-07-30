SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for Order
-- ----------------------------
DROP TABLE IF EXISTS `Order`;
CREATE TABLE `Order` (
  `id`              INT(10) NOT NULL AUTO_INCREMENT,
  `oid`             VARCHAR(20) NOT NULL COMMENT '订单ID',
  `viid`            INT(10) NOT NULL COMMENT '下单时的车系ID',
  `vid`             INT(10) DEFAULT NULL COMMENT '租车时关联的车ID',
  `begin`           DATETIME DEFAULT NULL,
  `end`             DATETIME DEFAULT NULL,
  `rent_address`    VARCHAR(50) NOT NULL COMMENT '取车地点',
  `return_address`  VARCHAR(50) NOT NULL COMMENT '还车地点',
  `name`            VARCHAR(50) NOT NULL COMMENT '租车人姓名',
  `identity`        VARCHAR(20) NOT NULL COMMENT '租车人身份证ID',
  `phone`           VARCHAR(20) NOT NULL COMMENT '租车人电话',
  `bill`            TEXT DEFAULT NULL COMMENT '支票信息',
  `day_cost`        INT(10) NOT NULL COMMENT '日均花费',
  `base_insurance`  INT(10) NOT NULL COMMENT '基础保险',
  `free_insurance`  INT(10) NOT NULL COMMENT '免陪保险',
  `pay_info`        TEXT COMMENT '用户交钱的具体信息',
  `rbegin`          DATETIME DEFAULT NULL COMMENT '实际租车时间',
  `rend`            DATETIME DEFAULT NULL COMMENT '实际还车时间',
  `distance`        DOUBLE NOT NULL DEFAULT 0.0 COMMENT '租车行驶里程',
  `cost_info`       TEXT COMMENT '租车的最终花费的具体信息',
  `status`          INT(10) NOT NULL DEFAULT 0,
  `create_time`     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;