/*
 Navicat MySQL Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : localhost:3306
 Source Schema         : shop

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 28/12/2019 21:45:47
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for item
-- ----------------------------
DROP TABLE IF EXISTS `item`;
CREATE TABLE `item`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `price` double(10, 2) NOT NULL DEFAULT 0.00,
  `description` varchar(500) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `sales` int(11) NOT NULL DEFAULT 0,
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of item
-- ----------------------------
INSERT INTO `item` VALUES (1, 'iphone', 4998.00, ' X 全面屏 吃鸡手机 二手手机 银白色 64G全网通', 9, 'https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=352443151,2763258060&fm=26&gp=0.jpg');
INSERT INTO `item` VALUES (2, 'ipad', 6799.00, ' 10.5英寸 2019款平板电脑 深空灰色 64GB WIFI', 19, 'https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=4051965604,472515385&fm=26&gp=0.jpg');
INSERT INTO `item` VALUES (3, '华为', 4199.00, '【30天价保】 绮境森林 全网通8G+128G【白条12期免息再减200】', 8, 'https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2401797372,3305829156&fm=26&gp=0.jpg');
INSERT INTO `item` VALUES (12, '小米9', 2399.00, '小米MIX3 骁龙845AIE AI 双摄 磁动力滑盖全面屏 三星 AMOLED屏幕 黑色 8GB+128GB 游戏智能拍照手机', 1, 'https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2065841539,3024623810&fm=11&gp=0.jpg');
INSERT INTO `item` VALUES (13, '奥利奥', 14.00, '奥利奥（Oreo）早餐休闲蛋糕糕点零食大礼包 夹心饼干巧克力味349g', 1, 'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2070828115,1686638511&fm=26&gp=0.jpg');

-- ----------------------------
-- Table structure for item_stock
-- ----------------------------
DROP TABLE IF EXISTS `item_stock`;
CREATE TABLE `item_stock`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stock` int(11) NOT NULL DEFAULT 0,
  `item_id` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of item_stock
-- ----------------------------
INSERT INTO `item_stock` VALUES (1, 991, 1);
INSERT INTO `item_stock` VALUES (2, 1, 2);
INSERT INTO `item_stock` VALUES (3, 14, 3);
INSERT INTO `item_stock` VALUES (12, 69, 12);
INSERT INTO `item_stock` VALUES (13, 998, 13);

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `user_id` int(11) NOT NULL DEFAULT 0,
  `item_id` int(11) NOT NULL DEFAULT 0,
  `item_price` double(10, 2) NOT NULL DEFAULT 0.00,
  `amount` int(11) NOT NULL DEFAULT 0,
  `order_price` double(10, 2) NOT NULL DEFAULT 0.00,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_info
-- ----------------------------
INSERT INTO `order_info` VALUES ('2019122400000000', 12, 1, 0.00, 1, 0.00);
INSERT INTO `order_info` VALUES ('2019122400000100', 12, 1, 10000.00, 1, 10000.00);
INSERT INTO `order_info` VALUES ('2019122400000200', 12, 1, 10000.00, 1, 10000.00);
INSERT INTO `order_info` VALUES ('2019122400000300', 12, 1, 10000.00, 1, 10000.00);
INSERT INTO `order_info` VALUES ('2019122400000400', 14, 1, 10000.00, 1, 10000.00);
INSERT INTO `order_info` VALUES ('2019122400000500', 14, 2, 6000.00, 1, 6000.00);
INSERT INTO `order_info` VALUES ('2019122400000600', 14, 2, 6000.00, 1, 6000.00);
INSERT INTO `order_info` VALUES ('2019122400000700', 14, 2, 6000.00, 1, 6000.00);
INSERT INTO `order_info` VALUES ('2019122400000800', 14, 2, 6000.00, 1, 6000.00);
INSERT INTO `order_info` VALUES ('2019122400000900', 14, 2, 6000.00, 1, 6000.00);
INSERT INTO `order_info` VALUES ('2019122400001000', 14, 2, 6000.00, 1, 6000.00);
INSERT INTO `order_info` VALUES ('2019122400001100', 14, 2, 6000.00, 1, 6000.00);
INSERT INTO `order_info` VALUES ('2019122400001200', 14, 2, 6000.00, 1, 6000.00);
INSERT INTO `order_info` VALUES ('2019122400001300', 14, 1, 10000.00, 1, 10000.00);
INSERT INTO `order_info` VALUES ('2019122400001400', 14, 1, 10000.00, 1, 10000.00);
INSERT INTO `order_info` VALUES ('2019122400001500', 14, 1, 10000.00, 1, 10000.00);
INSERT INTO `order_info` VALUES ('2019122500001600', 12, 14, 4.20, 1, 4.20);
INSERT INTO `order_info` VALUES ('2019122500001700', 12, 1, 4998.00, 1, 4998.00);
INSERT INTO `order_info` VALUES ('2019122500001800', 12, 13, 14.00, 1, 14.00);
INSERT INTO `order_info` VALUES ('2019122600001900', 19, 2, 6799.00, 1, 6799.00);
INSERT INTO `order_info` VALUES ('2019122600002000', 19, 3, 4199.00, 1, 4199.00);
INSERT INTO `order_info` VALUES ('2019122700002100', 23, 3, 4199.00, 1, 4199.00);
INSERT INTO `order_info` VALUES ('2019122700002200', 19, 3, 4199.00, 1, 4199.00);
INSERT INTO `order_info` VALUES ('2019122700002300', 19, 1, 4998.00, 1, 4998.00);
INSERT INTO `order_info` VALUES ('2019122800002400', 12, 12, 2399.00, 1, 2399.00);

-- ----------------------------
-- Table structure for sequence_info
-- ----------------------------
DROP TABLE IF EXISTS `sequence_info`;
CREATE TABLE `sequence_info`  (
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `current_value` int(11) NOT NULL DEFAULT 0,
  `step` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sequence_info
-- ----------------------------
INSERT INTO `sequence_info` VALUES ('order_info', 25, 1);

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `gender` int(11) NOT NULL DEFAULT 0,
  `age` int(11) NOT NULL DEFAULT -1,
  `telphone` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '-1',
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `telphone_unique_index`(`telphone`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES ('1', 1, 1, '1', 12);
INSERT INTO `user_info` VALUES ('11', 1, 21, '11', 13);
INSERT INTO `user_info` VALUES ('ys', 0, 19, '18816210781', 14);
INSERT INTO `user_info` VALUES ('zcj', 1, 20, '18816210000', 15);
INSERT INTO `user_info` VALUES ('ys2', 1, 21, '123123', 17);
INSERT INTO `user_info` VALUES ('123', 1, 19, '112233', 18);
INSERT INTO `user_info` VALUES ('test123', 1, 19, '188188', 19);
INSERT INTO `user_info` VALUES ('ys', 1, 19, '110', 23);

-- ----------------------------
-- Table structure for user_password
-- ----------------------------
DROP TABLE IF EXISTS `user_password`;
CREATE TABLE `user_password`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `password` varchar(128) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `user_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_password
-- ----------------------------
INSERT INTO `user_password` VALUES (10, '1', '12');
INSERT INTO `user_password` VALUES (11, '11', '13');
INSERT INTO `user_password` VALUES (12, '18816210781', '14');
INSERT INTO `user_password` VALUES (13, '123456', '15');
INSERT INTO `user_password` VALUES (14, '123123', '17');
INSERT INTO `user_password` VALUES (15, '123', '18');
INSERT INTO `user_password` VALUES (16, '188188', '19');
INSERT INTO `user_password` VALUES (18, 'ys', '23');

SET FOREIGN_KEY_CHECKS = 1;
