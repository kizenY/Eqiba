/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80015
 Source Host           : localhost:3306
 Source Schema         : eqiba

 Target Server Type    : MySQL
 Target Server Version : 80015
 File Encoding         : 65001

 Date: 19/02/2019 07:29:47
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for activity
-- ----------------------------
DROP TABLE IF EXISTS `activity`;
CREATE TABLE `activity`  (
  `id` int(11) NOT NULL COMMENT '活动编号',
  `title` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '活动标题',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '活动内容',
  `interest_id` smallint(6) NULL DEFAULT NULL COMMENT '所属兴趣的编号',
  `create_time` datetime(0) NOT NULL COMMENT '活动创建时间',
  `start_time` datetime(0) NOT NULL COMMENT '活动开始时间',
  `end_time` datetime(0) NOT NULL COMMENT '活动结束时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `interest_id`(`interest_id`) USING BTREE,
  CONSTRAINT `activity_ibfk_1` FOREIGN KEY (`interest_id`) REFERENCES `interest` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for activity_experience
-- ----------------------------
DROP TABLE IF EXISTS `activity_experience`;
CREATE TABLE `activity_experience`  (
  `id` int(11) NOT NULL COMMENT '活动经历编号',
  `activity_id` int(11) NULL DEFAULT NULL COMMENT '活动编号',
  `title` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '活动标题',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '活动内容',
  `epilogue` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '活动结语',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `activity_id`(`activity_id`) USING BTREE,
  CONSTRAINT `activity_experience_ibfk_1` FOREIGN KEY (`activity_id`) REFERENCES `activity` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for activity_member
-- ----------------------------
DROP TABLE IF EXISTS `activity_member`;
CREATE TABLE `activity_member`  (
  `activity_id` int(11) NULL DEFAULT NULL COMMENT '活动编号',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '成员用户编号',
  `role_id` tinyint(4) NULL DEFAULT NULL COMMENT '角色编号',
  INDEX `activity_id`(`activity_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `role_id`(`role_id`) USING BTREE,
  CONSTRAINT `activity_member_ibfk_1` FOREIGN KEY (`activity_id`) REFERENCES `activity` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `activity_member_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `activity_member_ibfk_3` FOREIGN KEY (`role_id`) REFERENCES `activity_role` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for activity_personal_experience
-- ----------------------------
DROP TABLE IF EXISTS `activity_personal_experience`;
CREATE TABLE `activity_personal_experience`  (
  `id` int(11) NOT NULL COMMENT '个人经历编号',
  `activity_experience_id` int(11) NULL DEFAULT NULL COMMENT '活动经历编号',
  `epilogue` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '个人结语',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `activity_experience_id`(`activity_experience_id`) USING BTREE,
  CONSTRAINT `activity_personal_experience_ibfk_1` FOREIGN KEY (`activity_experience_id`) REFERENCES `activity_experience` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for activity_role
-- ----------------------------
DROP TABLE IF EXISTS `activity_role`;
CREATE TABLE `activity_role`  (
  `id` tinyint(4) NOT NULL COMMENT '角色编号',
  `name` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名',
  `authority_add_member` tinyint(1) NOT NULL COMMENT '添加成员权限',
  `authority_delete_member` tinyint(1) NOT NULL COMMENT '删除成员权限',
  `authority_upload_picture` tinyint(1) NOT NULL COMMENT '上传活动照片权限',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for chatting_member
-- ----------------------------
DROP TABLE IF EXISTS `chatting_member`;
CREATE TABLE `chatting_member`  (
  `session_id` int(11) NULL DEFAULT NULL COMMENT '会话编号',
  `member_id` int(11) NULL DEFAULT NULL COMMENT '成员编号',
  `role_id` tinyint(1) NULL DEFAULT NULL COMMENT '角色编号',
  INDEX `session_id`(`session_id`) USING BTREE,
  INDEX `member_id`(`member_id`) USING BTREE,
  INDEX `role_id`(`role_id`) USING BTREE,
  CONSTRAINT `chatting_member_ibfk_1` FOREIGN KEY (`session_id`) REFERENCES `chatting_session` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `chatting_member_ibfk_2` FOREIGN KEY (`member_id`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `chatting_member_ibfk_3` FOREIGN KEY (`role_id`) REFERENCES `chatting_mitilateral_role` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for chatting_message
-- ----------------------------
DROP TABLE IF EXISTS `chatting_message`;
CREATE TABLE `chatting_message`  (
  `id` bigint(20) NOT NULL COMMENT '消息编号',
  `session_id` int(11) NULL DEFAULT NULL COMMENT '会话编号',
  `sender` tinyint(1) NOT NULL COMMENT '发送方是用户1或用户2',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '消息内容',
  `time` datetime(0) NOT NULL COMMENT '发送时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for chatting_mitilateral_role
-- ----------------------------
DROP TABLE IF EXISTS `chatting_mitilateral_role`;
CREATE TABLE `chatting_mitilateral_role`  (
  `id` tinyint(4) NOT NULL COMMENT '会话角色编号',
  `name` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '会话角色名',
  `authority_speak` tinyint(1) NOT NULL COMMENT '发言权',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for chatting_session
-- ----------------------------
DROP TABLE IF EXISTS `chatting_session`;
CREATE TABLE `chatting_session`  (
  `id` int(11) NOT NULL COMMENT '会话编号',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最新更新的时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for examine_activity
-- ----------------------------
DROP TABLE IF EXISTS `examine_activity`;
CREATE TABLE `examine_activity`  (
  `id` int(11) NOT NULL COMMENT '举报信息编号',
  `activity_id` int(11) NULL DEFAULT NULL COMMENT '被举报活动的编号',
  `type_id` tinyint(4) NULL DEFAULT NULL COMMENT '举报类型的编号',
  `supplement` varchar(0) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '举报原因的补充说明',
  `result` tinyint(4) NOT NULL DEFAULT 0 COMMENT '审核结果：0标识未审核，1表示举报属实，2表示举报不实',
  `reason_result` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '审核结果的理由',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for examine_interest
-- ----------------------------
DROP TABLE IF EXISTS `examine_interest`;
CREATE TABLE `examine_interest`  (
  `id` int(11) NOT NULL COMMENT '申请编号',
  `applicant_id` int(11) NULL DEFAULT NULL COMMENT '申请者用户编号',
  `interest_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '兴趣名称',
  `catelogue_id` tinyint(4) NULL DEFAULT NULL COMMENT '兴趣所属类别的编号',
  `interest_discribe` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '兴趣描述',
  `reason_application` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '申请理由',
  `result` tinyint(1) NOT NULL COMMENT '申请结果：0表示未审阅，1表示已通过，2表示审核不通过',
  `reason_result` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '审核结果的理由',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `applicant_id`(`applicant_id`) USING BTREE,
  INDEX `catelogue_id`(`catelogue_id`) USING BTREE,
  CONSTRAINT `examine_interest_ibfk_1` FOREIGN KEY (`applicant_id`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `examine_interest_ibfk_2` FOREIGN KEY (`catelogue_id`) REFERENCES `interest_category` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for examine_news
-- ----------------------------
DROP TABLE IF EXISTS `examine_news`;
CREATE TABLE `examine_news`  (
  `id` int(11) NOT NULL COMMENT '申请编号',
  `journal_id` int(11) NULL DEFAULT NULL COMMENT '日志编号',
  `interest_id` smallint(6) NULL DEFAULT NULL COMMENT '所属兴趣的编号',
  `reason_application` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '申请理由',
  `result` tinyint(1) NOT NULL COMMENT '审核结果：0表示未审核，1表示通过，2表示未通过。',
  `reason_result` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '审核结果的理由',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for friend_relation
-- ----------------------------
DROP TABLE IF EXISTS `friend_relation`;
CREATE TABLE `friend_relation`  (
  `id` int(11) NOT NULL COMMENT '好友关系编号',
  `user1_id` int(11) NULL DEFAULT NULL COMMENT '好友此方编号',
  `user2_id` int(11) NULL DEFAULT NULL COMMENT '好友彼方编号',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user1_id`(`user1_id`) USING BTREE,
  INDEX `user2_id`(`user2_id`) USING BTREE,
  CONSTRAINT `friend_relation_ibfk_1` FOREIGN KEY (`user1_id`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `friend_relation_ibfk_2` FOREIGN KEY (`user2_id`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for friend_request
-- ----------------------------
DROP TABLE IF EXISTS `friend_request`;
CREATE TABLE `friend_request`  (
  `id` int(11) NOT NULL COMMENT '好友申请编号',
  `requester_id` int(11) NULL DEFAULT NULL COMMENT '提出好友申请方用户的编号',
  `reciver_id` int(11) NULL DEFAULT NULL COMMENT '接收申请方用户的编号',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `requester_id`(`requester_id`) USING BTREE,
  INDEX `reciver_id`(`reciver_id`) USING BTREE,
  CONSTRAINT `friend_request_ibfk_1` FOREIGN KEY (`requester_id`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `friend_request_ibfk_2` FOREIGN KEY (`reciver_id`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for interest
-- ----------------------------
DROP TABLE IF EXISTS `interest`;
CREATE TABLE `interest`  (
  `id` smallint(6) NOT NULL COMMENT '兴趣编号',
  `title` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '兴趣名',
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '兴趣描述',
  `category_id` tinyint(4) NULL DEFAULT NULL COMMENT '所属类别的编号',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `category_id`(`category_id`) USING BTREE,
  CONSTRAINT `interest_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `interest_category` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for interest_category
-- ----------------------------
DROP TABLE IF EXISTS `interest_category`;
CREATE TABLE `interest_category`  (
  `id` tinyint(11) NOT NULL COMMENT '类别编号',
  `title` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类别名',
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类别描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for interest_user_record
-- ----------------------------
DROP TABLE IF EXISTS `interest_user_record`;
CREATE TABLE `interest_user_record`  (
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户编号',
  `interest_id` smallint(6) NULL DEFAULT NULL COMMENT '兴趣编号',
  `type` tinyint(1) NOT NULL COMMENT '显示兴趣或是隐式兴趣',
  `clicks` smallint(6) NULL DEFAULT NULL COMMENT '点击兴趣相关的次数',
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `interest_id`(`interest_id`) USING BTREE,
  CONSTRAINT `interest_user_record_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `interest_user_record_ibfk_2` FOREIGN KEY (`interest_id`) REFERENCES `interest` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for news
-- ----------------------------
DROP TABLE IF EXISTS `news`;
CREATE TABLE `news`  (
  `id` int(11) NOT NULL COMMENT '时闻编号',
  `author` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '作者名',
  `title` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标题',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容',
  `level_id` tinyint(4) NULL DEFAULT NULL COMMENT '等级编号',
  `lauch_time` datetime(0) NOT NULL COMMENT '发布时间',
  `interest_id` smallint(6) NULL DEFAULT NULL COMMENT '所属兴趣的编号',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `interest_id`(`interest_id`) USING BTREE,
  INDEX `level_id`(`level_id`) USING BTREE,
  CONSTRAINT `news_ibfk_1` FOREIGN KEY (`interest_id`) REFERENCES `interest` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `news_ibfk_2` FOREIGN KEY (`level_id`) REFERENCES `news_level` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for news_journal
-- ----------------------------
DROP TABLE IF EXISTS `news_journal`;
CREATE TABLE `news_journal`  (
  `id` int(11) NOT NULL COMMENT '日志编号',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户编号',
  `title` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标题',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最新更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  CONSTRAINT `news_journal_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for news_level
-- ----------------------------
DROP TABLE IF EXISTS `news_level`;
CREATE TABLE `news_level`  (
  `id` tinyint(1) NOT NULL COMMENT '时闻等级编号',
  `level` tinyint(1) NOT NULL COMMENT '时闻等级',
  `discribe` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '等级描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `username` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `phone_number` varchar(13) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `email` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `password` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `birthday` date NULL DEFAULT NULL COMMENT '生日',
  `sex` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `create_time` date NOT NULL COMMENT '用户创建日期',
  `login_time` datetime(0) NULL DEFAULT NULL COMMENT '用户最近一次登录时间',
  `logout_time` datetime(0) NULL DEFAULT NULL COMMENT '用户最近一次登出时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_certificate
-- ----------------------------
DROP TABLE IF EXISTS `user_certificate`;
CREATE TABLE `user_certificate`  (
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户编号',
  `number` char(18) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '身份证号',
  `name` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '姓名',
  INDEX `user_id`(`user_id`) USING BTREE,
  CONSTRAINT `user_certificate_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
