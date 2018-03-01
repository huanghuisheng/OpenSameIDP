/*
Navicat MySQL Data Transfer

Source Server         : database17
Source Server Version : 50553
Source Host           : 192.168.1.17:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50553
File Encoding         : 65001

Date: 2018-03-01 20:07:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ds_association_permission
-- ----------------------------
DROP TABLE IF EXISTS `ds_association_permission`;
CREATE TABLE `ds_association_permission` (
  `id` decimal(19,0) NOT NULL,
  `association_id` decimal(19,0) NOT NULL,
  `url` varchar(200) NOT NULL,
  `status` varchar(1) NOT NULL,
  `permission_type` varchar(1) DEFAULT NULL,
  `update_time` datetime NOT NULL,
  `create_time` datetime NOT NULL,
  `operator_id` decimal(19,0) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ds_association_permission
-- ----------------------------
INSERT INTO `ds_association_permission` VALUES ('1075873762309051222', '1075873762399809051', 'http://localhost:8090/hoffice/smsdoctor/referredreservations.do?method=enter&tagrandom=0.5669605692950603', 'A', '1', '2018-01-31 09:18:59', '2018-01-31 09:19:01', '0');

-- ----------------------------
-- Table structure for ds_employee_info
-- ----------------------------
DROP TABLE IF EXISTS `ds_employee_info`;
CREATE TABLE `ds_employee_info` (
  `id` decimal(19,0) NOT NULL,
  `name` varchar(50) NOT NULL,
  `sex` decimal(19,0) NOT NULL,
  `birthday` datetime NOT NULL,
  `nationality` decimal(19,0) DEFAULT NULL,
  `native_place` varchar(60) DEFAULT NULL,
  `detail_address` varchar(100) DEFAULT NULL,
  `contact_phone` varchar(20) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `idno` varchar(18) DEFAULT NULL,
  `pre_workunit` varchar(100) DEFAULT NULL,
  `start_work_years` int(2) DEFAULT NULL,
  `hire_date` datetime DEFAULT NULL,
  `hire_type` decimal(19,0) DEFAULT NULL,
  `pre_work_training` decimal(19,0) DEFAULT NULL,
  `hire_status` decimal(19,0) NOT NULL,
  `dismiss_date` datetime DEFAULT NULL,
  `position` decimal(19,0) DEFAULT NULL,
  `education` decimal(19,0) DEFAULT NULL,
  `graduate_time` datetime DEFAULT NULL,
  `graduated_school` varchar(100) DEFAULT NULL,
  `graduated_speciality` varchar(100) DEFAULT NULL,
  `sec_education` decimal(19,0) DEFAULT NULL,
  `get_title_date` datetime DEFAULT NULL,
  `practising_certificate` decimal(19,0) DEFAULT NULL,
  `certificate_no` varchar(100) DEFAULT NULL,
  `get_certificate_date` datetime DEFAULT NULL,
  `certificate_issue_org` varchar(100) DEFAULT NULL,
  `certificate_expiry_date` datetime DEFAULT NULL,
  `local_register` decimal(19,0) DEFAULT NULL,
  `register_code` varchar(100) DEFAULT NULL,
  `status` varchar(1) NOT NULL,
  `update_time` datetime NOT NULL,
  `operator_id` decimal(19,0) NOT NULL,
  `create_date` datetime NOT NULL,
  `create_userid` decimal(19,0) NOT NULL,
  `checksum` varchar(200) NOT NULL,
  `get_title_addr` varchar(100) DEFAULT NULL,
  `snd_graduated_school` varchar(100) DEFAULT NULL,
  `snd_graduate_time` datetime DEFAULT NULL,
  `continue_education` varchar(4000) DEFAULT NULL,
  `start_work_time` datetime DEFAULT NULL,
  `employee_type` int(2) DEFAULT NULL,
  `audit_status` int(1) DEFAULT NULL,
  `audit_userid` decimal(19,0) DEFAULT NULL,
  `audit_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ds_employee_info
-- ----------------------------
INSERT INTO `ds_employee_info` VALUES ('1075873762399809050', '吴珊', '302001', '2018-01-26 02:10:22', '303001', null, null, null, null, null, null, null, null, null, null, '1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 'A', '2018-01-26 02:10:53', '1', '2018-01-26 02:10:59', '1', '0', null, null, null, null, null, null, null, null, null);

-- ----------------------------
-- Table structure for ds_service_provider
-- ----------------------------
DROP TABLE IF EXISTS `ds_service_provider`;
CREATE TABLE `ds_service_provider` (
  `id` decimal(19,0) NOT NULL,
  `sevice_name` varchar(30) DEFAULT NULL COMMENT '服务系统名称',
  `register_time` datetime DEFAULT NULL,
  `service_url` varchar(200) DEFAULT NULL COMMENT '默认服务地址，若上传策略为1时，不能为空',
  `status` varchar(2) DEFAULT NULL,
  `upload_strategy` int(11) DEFAULT NULL COMMENT '上传用户策略，1：只上传用户基本信息，\r\n	2：上传用户的相关系统映射和权限。',
  `check_strategy` int(11) DEFAULT NULL COMMENT '验证断言用户策略：1：非系统用户，创建新的用户，分配默认权限。\r\n	2：非系统用户，拦截，不提供服务。',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='服务提供者';

-- ----------------------------
-- Records of ds_service_provider
-- ----------------------------
INSERT INTO `ds_service_provider` VALUES ('123456789', 'b系统', null, null, null, null, null, null);

-- ----------------------------
-- Table structure for ds_user
-- ----------------------------
DROP TABLE IF EXISTS `ds_user`;
CREATE TABLE `ds_user` (
  `id` decimal(19,0) NOT NULL,
  `login_name` varchar(50) DEFAULT NULL,
  `eff_date` datetime DEFAULT NULL,
  `exp_date` datetime DEFAULT NULL,
  `last_name` varchar(100) DEFAULT NULL,
  `first_name` varchar(100) DEFAULT NULL,
  `region` decimal(19,0) DEFAULT NULL,
  `sex` decimal(19,0) DEFAULT NULL,
  `account_status` decimal(19,0) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `lst_logon_date` datetime DEFAULT NULL,
  `logon_trial_date` datetime DEFAULT NULL,
  `lst_logon_result` decimal(19,0) DEFAULT NULL,
  `logon_fail_cnt` int(4) DEFAULT NULL,
  `pwd_change_date` datetime DEFAULT NULL,
  `password` varchar(64) DEFAULT NULL,
  `pwd_question` varchar(100) DEFAULT NULL,
  `pwd_answer` varchar(100) DEFAULT NULL,
  `org_id` decimal(19,0) DEFAULT NULL,
  `status` varchar(1) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `operator_id` decimal(19,0) DEFAULT NULL,
  `account_type` decimal(19,0) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ds_user
-- ----------------------------
INSERT INTO `ds_user` VALUES ('1075873762399809050', '吴珊', '2018-01-31 08:13:05', '2018-01-31 08:13:08', null, null, null, null, '1', null, null, null, null, null, null, null, 'd5925c1cebe79bcb450cfeb12176fca5991aea551fbfae0c', null, null, '0', 'A', '2018-01-31 08:13:36', '0', null);
INSERT INTO `ds_user` VALUES ('1075877044193456065', '小红', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `ds_user` VALUES ('1075877044193456642', '小黄', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `ds_user` VALUES ('1075877044193456643', '小李', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `ds_user` VALUES ('1075877044193456644', '小几', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);

-- ----------------------------
-- Table structure for ds_user_association
-- ----------------------------
DROP TABLE IF EXISTS `ds_user_association`;
CREATE TABLE `ds_user_association` (
  `id` decimal(19,0) DEFAULT NULL,
  `ds_user_id` decimal(19,0) DEFAULT NULL COMMENT '关联ds_user表的id',
  `service_provider_id` decimal(19,0) DEFAULT NULL COMMENT '系统标识，1是医院平台',
  `service_provider_name` varchar(50) DEFAULT NULL COMMENT '系统名称',
  `name` varchar(50) DEFAULT NULL COMMENT '用户名',
  `sp_user_id` varchar(64) DEFAULT NULL COMMENT '当前系统用户标识id --(当前系统的ds_user_id)',
  `status` varchar(2) DEFAULT NULL COMMENT '记录状态（A）有效，（I）删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `operator_id` decimal(19,0) DEFAULT NULL COMMENT '操作者'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='多个系统用户关联';

-- ----------------------------
-- Records of ds_user_association
-- ----------------------------
INSERT INTO `ds_user_association` VALUES ('1075873762399809051', '1075873762399809050', '1', '医院平台', '小红', 'd5925c1cebe79bcb450cfeb12176fca5991aea551fbfae0c', 'A', '2018-01-26 02:14:49', '2018-01-26 02:14:51', '151143123131');
INSERT INTO `ds_user_association` VALUES ('1075873762399809052', '1075873762399809050', '2', 'c平台', '小李', 'd5925c1cebe79bcb450cfeb12176fca5991aea551fbfae0c', 'A', '2018-01-26 02:16:08', '2018-01-26 02:16:11', '156161116151');
