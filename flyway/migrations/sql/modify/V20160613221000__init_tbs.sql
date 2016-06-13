
-- ----------------------------
-- Table structure for tpb_department
-- ----------------------------
DROP TABLE IF EXISTS `tpb_department`;
CREATE TABLE `tpb_department` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(100) NOT NULL COMMENT '部门名称',
  `nickname` varchar(100) NOT NULL COMMENT '部门别名',
  `code` varchar(100) DEFAULT NULL COMMENT '编码',
  `type` smallint(2) DEFAULT '2' COMMENT '类型,1-单位,2-部门',
  `description` varchar(1000) DEFAULT NULL COMMENT '描述',
  `is_parent` smallint(1) NOT NULL COMMENT '是否父部门,1-是，2-否',
  `parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '父部门ID',
  `dt_level` smallint(2) NOT NULL DEFAULT '1' COMMENT '部门层级',
  `sort_no` smallint(4) DEFAULT '1' COMMENT '排序',
  `created` datetime NOT NULL COMMENT '创建时间',
  `updated` datetime DEFAULT NULL COMMENT '更新时间',
  `create_user_id` int(11) NOT NULL COMMENT '创建用户id',
  `last_update_user_id` int(11) DEFAULT NULL COMMENT '最后更新用户',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='后台部门表';

-- ----------------------------
-- Records of tpb_department
-- ----------------------------
INSERT INTO `tpb_department` VALUES ('1', '总公司', '总公司', null, '1', '基础部门，所有的其他部门都是他的子部门', '1', '0', '1', '1', '2016-04-26 20:37:03', '2016-04-26 20:37:03', '1', null);
INSERT INTO `tpb_department` VALUES ('8', '财务部', '总公司_财务部', '1606051821140801', '2', '财务管理', '1', '1', '2', '1', '2016-06-05 18:21:14', null, '1', null);
INSERT INTO `tpb_department` VALUES ('9', '高级会计师', '总公司_财务部_高级会计师', '1606062307001956', '3', '财务部的高级会计师岗位', '2', '8', '3', '1', '2016-06-06 23:07:00', null, '1', null);

-- ----------------------------
-- Table structure for tpb_department_user
-- ----------------------------
DROP TABLE IF EXISTS `tpb_department_user`;
CREATE TABLE `tpb_department_user` (
  `department_id` int(11) NOT NULL COMMENT '部门ID',
  `sys_user_id` int(11) NOT NULL COMMENT '后台用户ID',
  PRIMARY KEY (`department_id`,`sys_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='部门用户表';

-- ----------------------------
-- Records of tpb_department_user
-- ----------------------------
INSERT INTO `tpb_department_user` VALUES ('1', '1');

-- ----------------------------
-- Table structure for tpb_menu
-- ----------------------------
DROP TABLE IF EXISTS `tpb_menu`;
CREATE TABLE `tpb_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(100) NOT NULL COMMENT '菜单名称',
  `link_url` varchar(200) DEFAULT NULL COMMENT '链接URL',
  `permission` varchar(100) DEFAULT NULL COMMENT '权限标识',
  `is_parent` smallint(1) NOT NULL COMMENT '是否父菜单，1-是，2-否',
  `parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '父菜单ID',
  `menu_level` smallint(2) NOT NULL DEFAULT '1' COMMENT '菜单层级',
  `sort_no` int(11) NOT NULL COMMENT '排序编号',
  `type` smallint(2) NOT NULL DEFAULT '1' COMMENT '菜单类型',
  `icon_cls` varchar(64) DEFAULT NULL COMMENT '菜单icon',
  `status` smallint(2) NOT NULL COMMENT '状态,1-正常,2-禁用',
  `created` datetime NOT NULL COMMENT '创建时间',
  `updated` datetime DEFAULT NULL COMMENT '更新时间',
  `create_user_id` int(11) NOT NULL COMMENT '创建用户id',
  `last_update_user_id` int(11) DEFAULT NULL COMMENT '最后更新用户',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='后台菜单表';

-- ----------------------------
-- Records of tpb_menu
-- ----------------------------
INSERT INTO `tpb_menu` VALUES ('1', '首页', '/admin', 'sys:index:view', '2', '0', '1', '1', '1', null, '1', '2016-04-26 20:37:02', '2016-04-26 20:37:02', '1', null);
INSERT INTO `tpb_menu` VALUES ('2', '系统管理', null, 'sys:manage:view', '1', '0', '1', '2', '1', null, '1', '2016-04-26 20:37:02', '2016-04-26 20:37:02', '1', null);
INSERT INTO `tpb_menu` VALUES ('3', '权限管理', null, 'sys:manage:auth:view', '1', '2', '2', '1', '1', null, '1', '2016-04-26 20:37:02', '2016-04-26 20:37:02', '1', null);
INSERT INTO `tpb_menu` VALUES ('4', '菜单管理', '/admin/sys_auth_menu', 'sys:manage:menu:view', '2', '3', '3', '1', '1', null, '1', '2016-04-26 20:37:02', '2016-04-26 20:37:02', '1', null);
INSERT INTO `tpb_menu` VALUES ('5', '菜单按钮管理', '/admin/sys_auth_menu_btn', 'sys:manage:menubtn:view', '2', '3', '3', '1', '1', null, '1', '2016-04-26 20:37:02', '2016-04-26 20:37:02', '1', null);
INSERT INTO `tpb_menu` VALUES ('6', '角色管理', '/admin/sys_auth_role', 'sys:manage:role:view', '2', '3', '3', '1', '1', null, '1', '2016-04-26 20:37:10', '2016-04-26 20:37:10', '1', null);
INSERT INTO `tpb_menu` VALUES ('7', '后台用户管理', '/admin/sys_auth_user', 'sys:manage:sysuser:view', '2', '3', '3', '1', '1', null, '1', '2016-04-26 20:37:10', '2016-04-26 20:37:10', '1', null);
INSERT INTO `tpb_menu` VALUES ('8', '角色权限管理', '/admin/sys_auth_role_menu', 'sys:manage:roleauth:view', '2', '3', '3', '1', '1', null, '1', '2016-04-26 20:37:10', '2016-04-26 20:37:10', '1', null);
INSERT INTO `tpb_menu` VALUES ('9', '部门管理', '/admin/sys_auth_dept', 'sys:manage:dept:view', '2', '3', '3', '1', '1', null, '1', '2016-04-26 20:37:10', '2016-04-26 20:37:10', '1', null);
INSERT INTO `tpb_menu` VALUES ('10', '字典管理', '/admin/sys_constant', 'sys:manage:constant:view', '2', '2', '2', '20', '1', null, '1', '2016-06-06 21:45:27', '2016-06-12 22:42:51', '1', '1');

-- ----------------------------
-- Table structure for tpb_menu_btn
-- ----------------------------
DROP TABLE IF EXISTS `tpb_menu_btn`;
CREATE TABLE `tpb_menu_btn` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `menu_id` int(11) NOT NULL COMMENT '菜单ID',
  `btn_name` varchar(100) NOT NULL COMMENT '按钮名称',
  `btn_code` varchar(100) NOT NULL COMMENT '编码',
  `type` smallint(2) NOT NULL DEFAULT '1' COMMENT '菜单按钮类型,1-全局操作，2-局部操作',
  `btn_url` varchar(100) DEFAULT NULL COMMENT '按钮url',
  `icon_cls` varchar(100) DEFAULT NULL COMMENT '按钮样式',
  `permission` varchar(100) DEFAULT NULL COMMENT '权限标识',
  `sort_no` smallint(4) NOT NULL DEFAULT '1' COMMENT '排序,最大9999',
  `status` smallint(2) NOT NULL COMMENT '按钮状态,1-正常,2-禁用',
  `created` datetime NOT NULL COMMENT '创建时间',
  `updated` datetime DEFAULT NULL COMMENT '更新时间',
  `create_user_id` int(11) NOT NULL COMMENT '创建用户ID',
  `last_update_user_id` int(11) DEFAULT NULL COMMENT '更新用户ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8 COMMENT='菜单按钮表';

-- ----------------------------
-- Records of tpb_menu_btn
-- ----------------------------
INSERT INTO `tpb_menu_btn` VALUES ('1', '4', '添加', 'add', '1', null, 'icon-add', 'sys:manage:menu:add', '1', '1', '2016-04-26 20:37:03', null, '1', null);
INSERT INTO `tpb_menu_btn` VALUES ('2', '4', '编辑', 'edit', '1', null, 'icon-edit', 'sys:manage:menu:edit', '2', '1', '2016-04-26 20:37:03', null, '1', null);
INSERT INTO `tpb_menu_btn` VALUES ('3', '4', '禁用', 'forbidden', '1', null, 'icon-remove', 'sys:manage:menu:forbidden', '3', '1', '2016-04-26 20:37:03', null, '1', null);
INSERT INTO `tpb_menu_btn` VALUES ('4', '4', '启用', 'normal', '1', null, 'icon-redo', 'sys:manage:menu:normal', '4', '1', '2016-04-26 20:37:03', null, '1', null);
INSERT INTO `tpb_menu_btn` VALUES ('6', '5', '添加', 'add', '1', null, 'icon-add', 'sys:manage:menubtn:add', '1', '1', '2016-04-26 20:37:03', null, '1', null);
INSERT INTO `tpb_menu_btn` VALUES ('7', '5', '编辑', 'edit', '1', null, 'icon-edit', 'sys:manage:menubtn:edit', '2', '1', '2016-04-26 20:37:03', null, '1', null);
INSERT INTO `tpb_menu_btn` VALUES ('8', '5', '禁用', 'forbidden', '1', null, 'icon-remove', 'sys:manage:menubtn:forbidden', '3', '1', '2016-04-26 20:37:03', null, '1', null);
INSERT INTO `tpb_menu_btn` VALUES ('9', '5', '启用', 'normal', '1', null, 'icon-redo', 'sys:manage:menubtn:normal', '4', '1', '2016-04-26 20:37:03', null, '1', null);
INSERT INTO `tpb_menu_btn` VALUES ('10', '5', '删除', 'delete', '1', null, 'icon-clear', 'sys:manage:menubtn:del', '5', '1', '2016-04-26 20:37:03', null, '1', null);
INSERT INTO `tpb_menu_btn` VALUES ('11', '4', '删除', 'delete', '2', null, 'icon-clear', 'sys:manage:menu:del', '5', '1', '2016-06-04 16:54:45', '2016-06-04 19:07:28', '1', '1');
INSERT INTO `tpb_menu_btn` VALUES ('12', '6', '添加', 'add', '1', null, 'icon-add', 'sys:manage:role:add', '1', '1', '2016-06-04 19:25:26', null, '1', null);
INSERT INTO `tpb_menu_btn` VALUES ('13', '6', '编辑', 'edit', '2', null, 'icon-edit', 'sys:manage:role:edit', '2', '1', '2016-06-04 19:26:16', null, '1', null);
INSERT INTO `tpb_menu_btn` VALUES ('14', '6', '禁用', 'forbidden', '2', null, 'icon-remove', 'sys:manage:role:forbidden', '3', '1', '2016-06-04 19:27:02', null, '1', null);
INSERT INTO `tpb_menu_btn` VALUES ('15', '6', '启用', 'normal', '2', null, 'icon-redo', 'sys:manage:role:normal', '4', '1', '2016-06-04 19:27:40', null, '1', null);
INSERT INTO `tpb_menu_btn` VALUES ('16', '6', '删除', 'delete', '2', null, 'icon-clear', 'sys:manage:role:del', '5', '1', '2016-06-04 19:28:16', null, '1', null);
INSERT INTO `tpb_menu_btn` VALUES ('17', '7', '添加', 'add', '1', null, 'icon-add', 'sys:manage:sysuser:add', '1', '1', '2016-06-04 19:25:26', null, '1', null);
INSERT INTO `tpb_menu_btn` VALUES ('18', '7', '编辑', 'edit', '2', null, 'icon-edit', 'sys:manage:sysuser:edit', '2', '1', '2016-06-04 19:26:16', null, '1', null);
INSERT INTO `tpb_menu_btn` VALUES ('19', '7', '禁用', 'forbidden', '2', null, 'icon-remove', 'sys:manage:sysuser:forbidden', '3', '1', '2016-06-04 19:27:02', null, '1', null);
INSERT INTO `tpb_menu_btn` VALUES ('20', '7', '启用', 'normal', '2', null, 'icon-redo', 'sys:manage:sysuser:normal', '4', '1', '2016-06-04 19:27:40', null, '1', null);
INSERT INTO `tpb_menu_btn` VALUES ('21', '7', '删除', 'delete', '2', null, 'icon-clear', 'sys:manage:sysuser:del', '5', '1', '2016-06-04 19:28:16', null, '1', null);
INSERT INTO `tpb_menu_btn` VALUES ('22', '9', '添加', 'add', '1', null, 'icon-add', 'sys:manage:dept:add', '1', '1', '2016-06-04 19:25:26', null, '1', null);
INSERT INTO `tpb_menu_btn` VALUES ('23', '9', '编辑', 'edit', '2', null, 'icon-edit', 'sys:manage:dept:edit', '2', '1', '2016-06-04 19:26:16', null, '1', null);
INSERT INTO `tpb_menu_btn` VALUES ('26', '9', '删除', 'delete', '2', null, 'icon-clear', 'sys:manage:dept:del', '5', '1', '2016-06-04 19:28:16', null, '1', null);
INSERT INTO `tpb_menu_btn` VALUES ('27', '8', '保存', 'save', '1', null, 'icon-save', 'sys:manage:roleauth:add', '1', '1', '2016-06-05 22:18:14', null, '1', null);
INSERT INTO `tpb_menu_btn` VALUES ('28', '8', '取消', 'cancel', '1', null, 'icon-cancel', 'sys:manage:roleauth:cancel', '2', '1', '2016-06-05 22:19:00', null, '1', null);
INSERT INTO `tpb_menu_btn` VALUES ('29', '10', '添加', 'add', '1', null, 'icon-add', 'sys:manage:constant:add', '1', '1', '2016-06-06 21:59:30', null, '1', null);
INSERT INTO `tpb_menu_btn` VALUES ('30', '10', '编辑', 'edit', '2', null, 'icon-edit', 'sys:manage:constant:edit', '1', '1', '2016-06-06 22:00:03', null, '1', null);
INSERT INTO `tpb_menu_btn` VALUES ('31', '10', '删除', 'delete', '2', null, 'icon-clear', 'sys:manage:constant:del', '3', '1', '2016-06-06 22:00:49', null, '1', null);

-- ----------------------------
-- Table structure for tpb_role
-- ----------------------------
DROP TABLE IF EXISTS `tpb_role`;
CREATE TABLE `tpb_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(100) NOT NULL COMMENT '角色名称',
  `code` varchar(100) NOT NULL COMMENT '编码（唯一）',
  `description` varchar(1000) DEFAULT NULL COMMENT '描述',
  `status` smallint(2) NOT NULL COMMENT '状态,1-正常,2-禁用',
  `created` datetime NOT NULL COMMENT '创建时间',
  `updated` datetime DEFAULT NULL COMMENT '更新时间',
  `create_user_id` int(11) NOT NULL COMMENT '创建用户ID',
  `last_update_user_id` int(11) DEFAULT NULL COMMENT '更新用户ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='后台角色表';

-- ----------------------------
-- Records of tpb_role
-- ----------------------------
INSERT INTO `tpb_role` VALUES ('1', '超级管理员', 'ROLE20150626172344262', '超级管理员，拥有所有的权限', '1', '2016-04-26 20:37:03', '2016-05-03 17:35:17', '1', null);
INSERT INTO `tpb_role` VALUES ('2', '管理员', 'ROLE20150626172344263', '管理员', '1', '2016-06-04 21:44:05', null, '1', null);
INSERT INTO `tpb_role` VALUES ('3', '普通用户', 'ROLE20160613215039443', '普通操作', '1', '2016-06-13 21:50:39', null, '1', null);

-- ----------------------------
-- Table structure for tpb_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `tpb_role_menu`;
CREATE TABLE `tpb_role_menu` (
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `menu_id` int(11) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色菜单表';

-- ----------------------------
-- Records of tpb_role_menu
-- ----------------------------
INSERT INTO `tpb_role_menu` VALUES ('1', '1');
INSERT INTO `tpb_role_menu` VALUES ('1', '2');
INSERT INTO `tpb_role_menu` VALUES ('1', '3');
INSERT INTO `tpb_role_menu` VALUES ('1', '4');
INSERT INTO `tpb_role_menu` VALUES ('1', '5');
INSERT INTO `tpb_role_menu` VALUES ('1', '6');
INSERT INTO `tpb_role_menu` VALUES ('1', '7');
INSERT INTO `tpb_role_menu` VALUES ('1', '8');
INSERT INTO `tpb_role_menu` VALUES ('1', '9');
INSERT INTO `tpb_role_menu` VALUES ('1', '10');
INSERT INTO `tpb_role_menu` VALUES ('2', '1');
INSERT INTO `tpb_role_menu` VALUES ('2', '2');
INSERT INTO `tpb_role_menu` VALUES ('2', '3');
INSERT INTO `tpb_role_menu` VALUES ('2', '4');
INSERT INTO `tpb_role_menu` VALUES ('2', '5');
INSERT INTO `tpb_role_menu` VALUES ('2', '6');
INSERT INTO `tpb_role_menu` VALUES ('2', '7');
INSERT INTO `tpb_role_menu` VALUES ('2', '8');
INSERT INTO `tpb_role_menu` VALUES ('2', '9');
INSERT INTO `tpb_role_menu` VALUES ('2', '10');
INSERT INTO `tpb_role_menu` VALUES ('3', '1');

-- ----------------------------
-- Table structure for tpb_role_menu_btn
-- ----------------------------
DROP TABLE IF EXISTS `tpb_role_menu_btn`;
CREATE TABLE `tpb_role_menu_btn` (
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `menu_id` int(11) NOT NULL COMMENT '菜单ID',
  `btn_id` int(11) NOT NULL COMMENT '菜单按钮ID',
  PRIMARY KEY (`role_id`,`menu_id`,`btn_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色菜单按钮表';

-- ----------------------------
-- Records of tpb_role_menu_btn
-- ----------------------------
INSERT INTO `tpb_role_menu_btn` VALUES ('1', '4', '1');
INSERT INTO `tpb_role_menu_btn` VALUES ('1', '4', '2');
INSERT INTO `tpb_role_menu_btn` VALUES ('1', '4', '3');
INSERT INTO `tpb_role_menu_btn` VALUES ('1', '4', '4');
INSERT INTO `tpb_role_menu_btn` VALUES ('1', '4', '5');
INSERT INTO `tpb_role_menu_btn` VALUES ('1', '4', '11');
INSERT INTO `tpb_role_menu_btn` VALUES ('1', '5', '6');
INSERT INTO `tpb_role_menu_btn` VALUES ('1', '5', '7');
INSERT INTO `tpb_role_menu_btn` VALUES ('1', '5', '8');
INSERT INTO `tpb_role_menu_btn` VALUES ('1', '5', '9');
INSERT INTO `tpb_role_menu_btn` VALUES ('1', '5', '10');
INSERT INTO `tpb_role_menu_btn` VALUES ('1', '5', '32');
INSERT INTO `tpb_role_menu_btn` VALUES ('1', '6', '12');
INSERT INTO `tpb_role_menu_btn` VALUES ('1', '6', '13');
INSERT INTO `tpb_role_menu_btn` VALUES ('1', '6', '14');
INSERT INTO `tpb_role_menu_btn` VALUES ('1', '6', '15');
INSERT INTO `tpb_role_menu_btn` VALUES ('1', '6', '16');
INSERT INTO `tpb_role_menu_btn` VALUES ('1', '7', '17');
INSERT INTO `tpb_role_menu_btn` VALUES ('1', '7', '18');
INSERT INTO `tpb_role_menu_btn` VALUES ('1', '7', '19');
INSERT INTO `tpb_role_menu_btn` VALUES ('1', '7', '20');
INSERT INTO `tpb_role_menu_btn` VALUES ('1', '7', '21');
INSERT INTO `tpb_role_menu_btn` VALUES ('1', '8', '27');
INSERT INTO `tpb_role_menu_btn` VALUES ('1', '8', '28');
INSERT INTO `tpb_role_menu_btn` VALUES ('1', '9', '22');
INSERT INTO `tpb_role_menu_btn` VALUES ('1', '9', '23');
INSERT INTO `tpb_role_menu_btn` VALUES ('1', '9', '26');
INSERT INTO `tpb_role_menu_btn` VALUES ('1', '10', '29');
INSERT INTO `tpb_role_menu_btn` VALUES ('1', '10', '30');
INSERT INTO `tpb_role_menu_btn` VALUES ('1', '10', '31');
INSERT INTO `tpb_role_menu_btn` VALUES ('2', '4', '1');
INSERT INTO `tpb_role_menu_btn` VALUES ('2', '4', '2');
INSERT INTO `tpb_role_menu_btn` VALUES ('2', '4', '3');
INSERT INTO `tpb_role_menu_btn` VALUES ('2', '4', '4');
INSERT INTO `tpb_role_menu_btn` VALUES ('2', '4', '11');
INSERT INTO `tpb_role_menu_btn` VALUES ('2', '5', '6');
INSERT INTO `tpb_role_menu_btn` VALUES ('2', '5', '8');
INSERT INTO `tpb_role_menu_btn` VALUES ('2', '5', '9');
INSERT INTO `tpb_role_menu_btn` VALUES ('2', '5', '10');
INSERT INTO `tpb_role_menu_btn` VALUES ('2', '6', '12');
INSERT INTO `tpb_role_menu_btn` VALUES ('2', '6', '13');
INSERT INTO `tpb_role_menu_btn` VALUES ('2', '6', '14');
INSERT INTO `tpb_role_menu_btn` VALUES ('2', '6', '15');
INSERT INTO `tpb_role_menu_btn` VALUES ('2', '6', '16');
INSERT INTO `tpb_role_menu_btn` VALUES ('2', '7', '17');
INSERT INTO `tpb_role_menu_btn` VALUES ('2', '7', '18');
INSERT INTO `tpb_role_menu_btn` VALUES ('2', '7', '19');
INSERT INTO `tpb_role_menu_btn` VALUES ('2', '7', '20');
INSERT INTO `tpb_role_menu_btn` VALUES ('2', '7', '21');
INSERT INTO `tpb_role_menu_btn` VALUES ('2', '8', '27');
INSERT INTO `tpb_role_menu_btn` VALUES ('2', '8', '28');
INSERT INTO `tpb_role_menu_btn` VALUES ('2', '9', '22');
INSERT INTO `tpb_role_menu_btn` VALUES ('2', '9', '23');
INSERT INTO `tpb_role_menu_btn` VALUES ('2', '9', '26');
INSERT INTO `tpb_role_menu_btn` VALUES ('2', '10', '29');
INSERT INTO `tpb_role_menu_btn` VALUES ('2', '10', '30');
INSERT INTO `tpb_role_menu_btn` VALUES ('2', '10', '31');

-- ----------------------------
-- Table structure for tpb_role_user
-- ----------------------------
DROP TABLE IF EXISTS `tpb_role_user`;
CREATE TABLE `tpb_role_user` (
  `sys_user_id` int(10) NOT NULL COMMENT '用户ID',
  `role_id` int(10) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`sys_user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色表';

-- ----------------------------
-- Records of tpb_role_user
-- ----------------------------
INSERT INTO `tpb_role_user` VALUES ('1', '1');
INSERT INTO `tpb_role_user` VALUES ('1', '2');
INSERT INTO `tpb_role_user` VALUES ('3', '2');
INSERT INTO `tpb_role_user` VALUES ('4', '3');

-- ----------------------------
-- Table structure for tpb_sys_constant
-- ----------------------------
DROP TABLE IF EXISTS `tpb_sys_constant`;
CREATE TABLE `tpb_sys_constant` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `value` smallint(2) NOT NULL COMMENT '数据值',
  `label` varchar(100) NOT NULL COMMENT '标签名',
  `type` varchar(100) NOT NULL COMMENT '类型',
  `description` varchar(100) NOT NULL COMMENT '描述',
  `sort` smallint(4) NOT NULL COMMENT '排序（升序）',
  `parent_id` smallint(2) DEFAULT '0' COMMENT '父级编号',
  `is_parent` smallint(1) DEFAULT '2' COMMENT '是否父常量，1-是，2-否',
  `created` datetime NOT NULL COMMENT '创建时间',
  `updated` datetime DEFAULT NULL COMMENT '更新时间',
  `create_user_id` int(11) NOT NULL COMMENT '创建用户ID',
  `last_update_user_id` int(11) DEFAULT NULL COMMENT '更新用户ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='常量表（字典表）';

-- ----------------------------
-- Records of tpb_sys_constant
-- ----------------------------
INSERT INTO `tpb_sys_constant` VALUES ('1', '1', '是', 'flag', '是与否', '1', '0', '2', '2016-05-15 14:57:40', null, '1', null);
INSERT INTO `tpb_sys_constant` VALUES ('2', '2', '否', 'flag', '是与否', '2', '0', '2', '2016-05-15 14:58:15', null, '1', null);
INSERT INTO `tpb_sys_constant` VALUES ('3', '1', '正常', 'status', '状态', '1', '0', '2', '2016-06-04 11:06:11', null, '1', null);
INSERT INTO `tpb_sys_constant` VALUES ('4', '2', '禁用', 'status', '状态', '2', '0', '2', '2016-06-04 11:06:44', null, '1', null);
INSERT INTO `tpb_sys_constant` VALUES ('5', '1', '后台菜单', 'menu_type', '菜单类型', '1', '0', '2', '2016-06-04 11:45:01', null, '1', null);
INSERT INTO `tpb_sys_constant` VALUES ('6', '2', '前台菜单', 'menu_type', '菜单类型', '2', '0', '2', '2016-06-04 11:45:36', null, '1', null);
INSERT INTO `tpb_sys_constant` VALUES ('7', '1', '全局操作', 'menu_btn_type', '菜单按钮类型', '1', '0', '2', '2016-06-04 12:04:23', null, '1', null);
INSERT INTO `tpb_sys_constant` VALUES ('8', '2', '局部操作', 'menu_btn_type', '菜单按钮类型', '2', '0', '2', '2016-06-04 12:05:55', null, '1', null);
INSERT INTO `tpb_sys_constant` VALUES ('9', '1', '单位', 'dept_type', '部门类型', '1', '0', '2', '2016-06-05 11:27:41', null, '1', null);
INSERT INTO `tpb_sys_constant` VALUES ('10', '2', '部门', 'dept_type', '部门类型', '2', '0', '2', '2016-06-05 11:28:09', null, '1', null);
INSERT INTO `tpb_sys_constant` VALUES ('14', '3', '岗位', 'dept_type', '部门岗位', '3', '0', '2', '2016-06-06 23:05:31', null, '1', null);

-- ----------------------------
-- Table structure for tpb_sys_user
-- ----------------------------
DROP TABLE IF EXISTS `tpb_sys_user`;
CREATE TABLE `tpb_sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(100) NOT NULL COMMENT '登陆用户名',
  `nickname` varchar(100) DEFAULT NULL COMMENT '昵称',
  `password` varchar(100) NOT NULL COMMENT '加密后的密码',
  `password_salt` varchar(100) NOT NULL COMMENT '加密盐值',
  `status` smallint(2) NOT NULL COMMENT '状态,1-正常,2-禁用',
  `created` datetime NOT NULL COMMENT '创建时间',
  `updated` datetime DEFAULT NULL COMMENT '更新时间',
  `create_user_id` int(11) NOT NULL COMMENT '创建用户ID',
  `last_update_user_id` int(11) DEFAULT NULL COMMENT '更新用户ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='后台用户表';

-- ----------------------------
-- Records of tpb_sys_user
-- ----------------------------
INSERT INTO `tpb_sys_user` VALUES ('1', 'admin', 'admin', 'f634ec33d3361077fbaaba79fb15e310', '20150626090439843,1246810676', '1', '2016-04-26 20:37:02', '2016-04-26 20:37:02', '1', null);
INSERT INTO `tpb_sys_user` VALUES ('3', 'jetty', 'jetty', '41d756e84a46d99fdef53195b56643bb', '20160605230332596,1992430315', '1', '2016-06-05 01:52:43', '2016-06-05 23:03:49', '1', '1');
INSERT INTO `tpb_sys_user` VALUES ('4', 'normal', '普通人', '0a2d98029bc24d9c1a591573102adc59', '20160613215226321,1955427093', '1', '2016-06-13 21:52:26', null, '1', null);
