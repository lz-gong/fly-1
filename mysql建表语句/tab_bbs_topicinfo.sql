/*
Navicat MySQL Data Transfer

Source Server         : neuedu
Source Server Version : 50556
Source Host           : localhost:3306
Source Database       : yklg_bbs

Target Server Type    : MYSQL
Target Server Version : 50556
File Encoding         : 65001

Date: 2018-03-07 12:43:16
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tab_bbs_topicinfo
-- ----------------------------
DROP TABLE IF EXISTS `tab_bbs_topicinfo`;
CREATE TABLE `tab_bbs_topicinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(200) NOT NULL DEFAULT '',
  `content` longtext NOT NULL,
  `createtime` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `category_id` int(11) NOT NULL DEFAULT '1',
  `view_count` int(11) NOT NULL DEFAULT '0',
  `userid` int(11) NOT NULL DEFAULT '0',
  `is_good` int(11) NOT NULL DEFAULT '0',
  `is_end` int(11) NOT NULL DEFAULT '0',
  `reward_kiss` int(11) NOT NULL DEFAULT '0',
  `is_top` int(1) NOT NULL DEFAULT '0',
  `is_accepted` int(1) NOT NULL DEFAULT '0',
  `answer_num` int(5) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8;
