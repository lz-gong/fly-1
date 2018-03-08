/*
Navicat MySQL Data Transfer

Source Server         : neuedu
Source Server Version : 50556
Source Host           : localhost:3306
Source Database       : yklg_bbs

Target Server Type    : MYSQL
Target Server Version : 50556
File Encoding         : 65001

Date: 2018-03-07 12:43:11
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tab_bbs_message
-- ----------------------------
DROP TABLE IF EXISTS `tab_bbs_message`;
CREATE TABLE `tab_bbs_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `comment_id` int(11) NOT NULL DEFAULT '0',
  `topic_userid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
