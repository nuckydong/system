/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50173
Source Host           : localhost:3306
Source Database       : goods_management

Target Server Type    : MYSQL
Target Server Version : 50173
File Encoding         : 65001

Date: 2018-10-24 18:06:57
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `mobile_phone` varchar(255) DEFAULT NULL,
  `telephone` varchar(255) DEFAULT NULL,
  `wechat` varchar(255) DEFAULT NULL,
  `qq` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `create_user` int(11) DEFAULT NULL,
  `update_user` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of customer
-- ----------------------------
INSERT INTO `customer` VALUES ('3', 'TEST', null, 'TEST', null, null, null, '2018-10-24 17:52:54', '2018-10-24 17:52:54', '0', '0');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `account` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `user_type` int(1) NOT NULL,
  `wechat` varchar(255) DEFAULT NULL,
  `phone` varchar(255) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `create_user` int(11) DEFAULT NULL,
  `update_user` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'TEST', 'TEST', 'TEST', '1', 'TEST', 'TEST', '2018-10-24 16:56:56', '2018-10-24 16:56:56', '0', '0');
INSERT INTO `user` VALUES ('2', null, 'TEST2', 'TEST', '1', 'TEST', 'TEST', '2018-10-24 17:52:54', '2018-10-24 17:52:54', '0', '0');
