/*
Navicat MySQL Data Transfer

Source Server         : neuedu
Source Server Version : 50556
Source Host           : localhost:3306
Source Database       : yklg_bbs

Target Server Type    : MYSQL
Target Server Version : 50556
File Encoding         : 65001

Date: 2018-03-07 15:37:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tab_bbs_category
-- ----------------------------
DROP TABLE IF EXISTS `tab_bbs_category`;
CREATE TABLE `tab_bbs_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `classname` varchar(100) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `classname` (`classname`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tab_bbs_category
-- ----------------------------
INSERT INTO `tab_bbs_category` VALUES ('2', '分享');
INSERT INTO `tab_bbs_category` VALUES ('4', '建议');
INSERT INTO `tab_bbs_category` VALUES ('1', '提问');
INSERT INTO `tab_bbs_category` VALUES ('3', '讨论');
