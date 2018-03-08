/*
Navicat MySQL Data Transfer

Source Server         : neuedu
Source Server Version : 50556
Source Host           : localhost:3306
Source Database       : yklg_bbs

Target Server Type    : MYSQL
Target Server Version : 50556
File Encoding         : 65001

Date: 2018-03-07 12:43:06
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tab_bbs_comment
-- ----------------------------
DROP TABLE IF EXISTS `tab_bbs_comment`;
CREATE TABLE `tab_bbs_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `topic_or_comment_id` int(11) NOT NULL DEFAULT '0',
  `is_topic` int(11) NOT NULL DEFAULT '0',
  `comment_content` varchar(2000) NOT NULL DEFAULT '',
  `userid` int(11) NOT NULL DEFAULT '0',
  `comment_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `support_num` int(11) NOT NULL DEFAULT '0',
  `is_accepted` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=216 DEFAULT CHARSET=utf8;
