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
  `rent_sid`        INT(10) NOT NULL COMMENT '取车门店',
  `return_sid`      INT(10) NOT NULL COMMENT '还车门店',
  `name`            VARCHAR(50) NOT NULL COMMENT '租车人姓名',
  `identity`        VARCHAR(20) NOT NULL COMMENT '租车人身份证ID',
  `phone`           VARCHAR(20) NOT NULL COMMENT '租车人电话',
  `bill`            TEXT DEFAULT NULL COMMENT '支票信息',
  `pre_cost`        TEXT DEFAULT NULL COMMENT '用户租车价格信息',
  `pay_info`        TEXT COMMENT '用户交钱的具体信息',
  `rbegin`          DATETIME DEFAULT NULL COMMENT '实际租车时间',
  `rend`            DATETIME DEFAULT NULL COMMENT '实际还车时间',
  `rrent_sid`       INT(10) DEFAULT NULL COMMENT '实际取车门店',
  `rreturn_sid`     INT(10) DEFAULT NULL COMMENT '实际还车门店',
  `cost_info`       TEXT COMMENT '租车的最终花费的具体信息',
  `status`          INT(10) NOT NULL DEFAULT 0,
  `create_time`     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;