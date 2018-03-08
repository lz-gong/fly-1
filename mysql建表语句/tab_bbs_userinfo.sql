/*
Navicat MySQL Data Transfer

Source Server         : neuedu
Source Server Version : 50556
Source Host           : localhost:3306
Source Database       : yklg_bbs

Target Server Type    : MYSQL
Target Server Version : 50556
File Encoding         : 65001

Date: 2018-03-07 12:43:28
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tab_bbs_userinfo
-- ----------------------------
DROP TABLE IF EXISTS `tab_bbs_userinfo`;
CREATE TABLE `tab_bbs_userinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(60) NOT NULL DEFAULT '',
  `nickname` varchar(100) NOT NULL DEFAULT '',
  `city` varchar(100) NOT NULL DEFAULT '',
  `sex` int(11) NOT NULL DEFAULT '0',
  `head_url` varchar(300) NOT NULL DEFAULT '',
  `password` varchar(64) NOT NULL DEFAULT '',
  `sign_name` varchar(500) NOT NULL DEFAULT '',
  `kiss_num` int(11) NOT NULL DEFAULT '100',
  `jointime` datetime NOT NULL,
  `is_manager` int(1) NOT NULL DEFAULT '0',
  `sign_time` datetime NOT NULL,
  `experience` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;
