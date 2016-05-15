DROP TABLE IF EXISTS `tbs_report`;
CREATE TABLE `tbs_report` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `build_address` varchar(256) NOT NULL COMMENT '违章建筑地址',
  `district_id` varchar(200) NOT NULL COMMENT '区域ID',
  `time` datetime NOT NULL COMMENT '举报时间',
  `title` varchar(255) NOT NULL COMMENT '主题',
  `content` varchar(900) NOT NULL COMMENT '内容',
  `attach_address` varchar(255) DEFAULT NULL COMMENT '附件地址，用|隔开',
  `is_public` smallint(2) NOT NULL DEFAULT '1' COMMENT '1-公开,2-不公开',
  `audit_user_id` int(11) DEFAULT NULL COMMENT '审核人ID',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `audit_status` smallint(2) NOT NULL DEFAULT '1' COMMENT '审核状态，1-待处理,2-审核，3-已处理，4-无效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='举报表';