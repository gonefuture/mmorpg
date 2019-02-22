/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.22-log : Database - game
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`game` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `game`;

/*Table structure for table `t_auction_item` */

DROP TABLE IF EXISTS `t_auction_item`;

CREATE TABLE `t_auction_item` (
  `auction_id` int(11) NOT NULL AUTO_INCREMENT,
  `thing_info_id` int(11) NOT NULL,
  `number` int(11) NOT NULL,
  `auction_price` int(11) NOT NULL,
  `auction_mode` int(11) NOT NULL,
  `publish_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `bidders` text NOT NULL,
  `publisher_id` bigint(20) NOT NULL,
  PRIMARY KEY (`auction_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_auction_item` */

/*Table structure for table `t_bag` */

DROP TABLE IF EXISTS `t_bag`;

CREATE TABLE `t_bag` (
  `player_Id` bigint(20) NOT NULL,
  `bag_size` int(11) NOT NULL,
  `bag_name` varchar(32) NOT NULL,
  `items` text NOT NULL,
  `type` int(11) NOT NULL,
  PRIMARY KEY (`player_Id`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_bag` */

insert  into `t_bag`(`player_Id`,`bag_size`,`bag_name`,`items`,`type`) values (1,16,'16格背包','{1:{\"count\":2,\"id\":198773812844695552,\"locationIndex\":1,\"thingInfo\":{\"buffer\":106,\"describe\":\"加hp\",\"id\":1004,\"key\":1004,\"kind\":3,\"level\":1,\"name\":\"红药\",\"part\":\"背包\",\"price\":103,\"roleProperties\":\"\",\"thingRoleProperty\":{}}},2:{\"count\":1,\"id\":198773813025050624,\"locationIndex\":2,\"thingInfo\":{\"buffer\":106,\"describe\":\"经典护甲\",\"id\":1006,\"key\":1006,\"kind\":1,\"level\":34,\"name\":\"银鳞胸甲\",\"part\":\"胸部\",\"price\":105,\"roleProperties\":\"[{\\\"id\\\":3,\\\"value\\\":200},{\\\"id\\\":5,\\\"value\\\":200},{\\\"id\\\":4,\\\"value\\\":200}]\",\"thingRoleProperty\":{3:{\"id\":3,\"key\":3,\"name\":\"防御\",\"thingPropertyValue\":200,\"type\":\"2\",\"value\":20},4:{\"describe\":\"一点基础攻击力等于一点战力\",\"id\":4,\"key\":4,\"name\":\"攻击力\",\"thingPropertyValue\":200,\"type\":\"2\",\"value\":30},5:{\"describe\":\"五点力量等一点战力\",\"id\":5,\"key\":5,\"name\":\"力量\",\"thingPropertyValue\":200,\"type\":\"2\",\"value\":100}}}},3:{\"count\":1,\"id\":203455933018411008,\"locationIndex\":3,\"thingInfo\":{\"buffer\":106,\"describe\":\"经典护甲\",\"id\":1006,\"key\":1006,\"kind\":1,\"level\":34,\"name\":\"银鳞胸甲\",\"part\":\"胸部\",\"price\":105,\"roleProperties\":\"[{\\\"id\\\":3,\\\"value\\\":200},{\\\"id\\\":5,\\\"value\\\":200},{\\\"id\\\":4,\\\"value\\\":200}]\",\"thingRoleProperty\":{3:{\"id\":3,\"key\":3,\"name\":\"防御\",\"thingPropertyValue\":200,\"type\":\"2\",\"value\":20},4:{\"describe\":\"一点基础攻击力等于一点战力\",\"id\":4,\"key\":4,\"name\":\"攻击力\",\"thingPropertyValue\":200,\"type\":\"2\",\"value\":30},5:{\"describe\":\"五点力量等一点战力\",\"id\":5,\"key\":5,\"name\":\"力量\",\"thingPropertyValue\":200,\"type\":\"2\",\"value\":100}}}}}',1),(2,16,'16格背包','{}',1),(3,16,'16格背包','{}',1),(4,16,'16格背包','{}',1),(5,16,'16格背包','',1),(102,16,'16格背包','',1);

/*Table structure for table `t_guild` */

DROP TABLE IF EXISTS `t_guild`;

CREATE TABLE `t_guild` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `level` int(11) NOT NULL,
  `member` text NOT NULL,
  `warehouse` text NOT NULL,
  `warehouse_size` int(11) NOT NULL,
  `join_request` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_guild` */

/*Table structure for table `t_mail` */

DROP TABLE IF EXISTS `t_mail`;

CREATE TABLE `t_mail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `subject` varchar(32) NOT NULL,
  `content` varchar(64) NOT NULL,
  `attachment` text,
  `sender` bigint(20) NOT NULL,
  `receiver` bigint(20) NOT NULL,
  `has_read` tinyint(1) NOT NULL,
  `money` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_mail` */

/*Table structure for table `t_player` */

DROP TABLE IF EXISTS `t_player`;

CREATE TABLE `t_player` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `exp` int(11) NOT NULL DEFAULT '0' COMMENT '经验值',
  `state` int(11) NOT NULL DEFAULT '0',
  `scene` int(11) NOT NULL DEFAULT '1',
  `user_id` bigint(20) NOT NULL,
  `equipments` text,
  `money` int(11) NOT NULL DEFAULT '0',
  `guild_id` int(11) NOT NULL DEFAULT '0',
  `role_class` int(11) NOT NULL COMMENT '游戏职业',
  `guild_class` int(11) NOT NULL DEFAULT '0' COMMENT '公会职位，0，1，2，3',
  `friends` text COMMENT '朋友列表',
  PRIMARY KEY (`id`),
  UNIQUE KEY `t_player_name_uindex` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8;

/*Data for the table `t_player` */

insert  into `t_player`(`id`,`name`,`exp`,`state`,`scene`,`user_id`,`equipments`,`money`,`guild_id`,`role_class`,`guild_class`,`friends`) values (1,'只会装死的猎人',11856,1,5,1,'{}',997711,0,3,0,'{2:{\"lastOnlineTime\":1546575981694,\"name\":\"蓝色巨龙卡雷苟斯\",\"online\":true,\"playerId\":2,\"roleClass\":\"法师\"},3:{\"lastOnlineTime\":1546576228975,\"name\":\"洛丹伦王子阿尔萨斯\",\"online\":true,\"playerId\":3,\"roleClass\":\"死亡骑士\"}}'),(2,'卡雷苟斯',10453,1,6,2,'{}',19943,0,2,3,''),(3,'死在冲锋路上的狂暴战',0,0,6,3,'{}',0,0,0,0,''),(4,'怀特迈恩',0,1,12,4,'{}',0,0,1,0,''),(5,'被猎人捉了当宠物的德鲁伊大咕咕',1661,1,12,1,'{}',1661,0,1,0,''),(6,'洛丹伦王子阿尔萨斯',0,1,10,3,'{}',0,0,4,0,''),(7,'瞬发炉石的萨满祭司',0,0,2,2,'',0,0,1,0,''),(100,'拍卖行',0,0,1,0,'',0,0,0,0,''),(101,'GM',0,0,1,0,'',0,0,0,0,''),(102,'瓦里安*乌瑞恩',250,0,1,7,'',250,0,0,0,NULL);

/*Table structure for table `t_quest_progress` */

DROP TABLE IF EXISTS `t_quest_progress`;

CREATE TABLE `t_quest_progress` (
  `player_id` bigint(20) NOT NULL,
  `quest_id` int(11) NOT NULL,
  `quest_state` int(11) NOT NULL,
  `begin_time` datetime NOT NULL,
  `end_time` datetime DEFAULT NULL,
  `progress` text,
  PRIMARY KEY (`player_id`,`quest_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_quest_progress` */

insert  into `t_quest_progress`(`player_id`,`quest_id`,`quest_state`,`begin_time`,`end_time`,`progress`) values (1,1,1,'2019-02-13 10:47:33',NULL,'{\"2\":{\"goal\":10,\"now\":0}}'),(1,2,5,'2019-01-31 12:16:10',NULL,'{\"first\":{\"goal\":10,\"now\":118}}'),(1,3,2,'2019-01-29 19:51:17',NULL,NULL),(1,4,1,'2019-02-12 11:40:20',NULL,'{\"first\":{\"goal\":100,\"now\":0}}'),(1,9,1,'2019-01-29 17:43:35',NULL,'{\"first\":{\"goal\":1000000,\"now\":997711}}'),(2,1,2,'2019-01-31 12:15:46',NULL,NULL);

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL,
  `phone` varchar(11) DEFAULT NULL,
  `last_ip` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `t_user_id_uindex` (`id`),
  UNIQUE KEY `t_user_name_uindex` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Data for the table `t_user` */

insert  into `t_user`(`id`,`name`,`password`,`phone`,`last_ip`) values (1,'奈非天','123','13533805040',NULL),(2,'贪婪的冒险者','123','13533805040',NULL),(3,'玩家一','123','13533805040',NULL),(4,'血色十字军','123','13533805040',NULL),(5,'阿昆达','123','1353334',NULL),(7,'联盟勇士','123','12345',NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
