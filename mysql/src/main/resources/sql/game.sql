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

/*Table structure for table `t_game_object` */

DROP TABLE IF EXISTS `t_game_object`;

CREATE TABLE `t_game_object` (
  `id` bigint(20) NOT NULL,
  `name` varchar(32) NOT NULL,
  `hp` bigint(20) NOT NULL,
  `mp` bigint(20) DEFAULT NULL,
  `talk` varchar(256) DEFAULT NULL COMMENT '谈话',
  `skills` varchar(100) DEFAULT NULL COMMENT '技能',
  `state` int(11) NOT NULL COMMENT '状态',
  `role_type` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_game_object` */

insert  into `t_game_object`(`id`,`name`,`hp`,`mp`,`talk`,`skills`,`state`,`role_type`) values (1,'软泥怪1',1,NULL,'哇哇',NULL,1,2),(2,'软泥怪2',1,NULL,'哇哇哇',NULL,1,2),(3,'新手村村长',1,NULL,'欢迎来到新手村！',NULL,1,1),(4,'黑衣人',100,NULL,'十步杀一人，千里不留行。',NULL,1,1),(5,'伊利丹',20000,10000,'你们这是自寻死路！！','1，2，3，4，5，5',1,2);

/*Table structure for table `t_player` */

DROP TABLE IF EXISTS `t_player`;

CREATE TABLE `t_player` (
  `id` bigint(20) NOT NULL,
  `name` varchar(32) NOT NULL,
  `hp` bigint(20) DEFAULT NULL,
  `mp` bigint(20) DEFAULT NULL,
  `position` varchar(32) DEFAULT NULL,
  `state` int(11) NOT NULL,
  `scene` int(11) DEFAULT NULL,
  `player_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_player` */

insert  into `t_player`(`id`,`name`,`hp`,`mp`,`position`,`state`,`scene`,`player_id`) values (1212,'阿尔萨斯',NULL,NULL,NULL,0,2,13533805040),(1235,'只会装死的猎人 233',242,2247,NULL,0,2,1),(1313,'蓝色巨龙卡雷苟斯',4545,1454,NULL,0,1,2),(4556,'瞬发炉石的萨满祭司',11212,1212,NULL,0,2,13533805040),(14154,'被猎人捉了当宠物的德鲁伊大咕咕',2424,44,NULL,0,5,1),(1493484114469936429,'死在冲锋路上的狂暴战',9999,9999,NULL,0,1,13533805040);

/*Table structure for table `t_scene` */

DROP TABLE IF EXISTS `t_scene`;

CREATE TABLE `t_scene` (
  `id` int(11) NOT NULL,
  `name` varchar(64) NOT NULL,
  `state` int(11) NOT NULL COMMENT '场景的状态',
  `neighbors` varchar(100) NOT NULL,
  `game_object_ids` varchar(200) NOT NULL COMMENT '场景内游戏对象',
  `players` varchar(200) NOT NULL COMMENT '游戏内在线的玩家，不需要持久化',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_scene` */

insert  into `t_scene`(`id`,`name`,`state`,`neighbors`,`game_object_ids`,`players`) values (1,'新手村',1,'2','1,5',''),(2,'泰达希尔',1,'3,4','1,2',''),(3,'桃花源',1,'4,5','2,3,4',''),(4,'提瑞斯法森林',1,'5','1,2,3,4',''),(5,'城堡',1,'6','5,1,2',''),(6,'紫禁之巅',1,'1,7','1,2,3,,4,5',''),(7,'伽蓝之洞',1,'1，2，3，4，5，6','1','');

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `id` bigint(20) NOT NULL,
  `name` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL,
  `phone` varchar(11) DEFAULT NULL,
  `last_ip` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_user` */

insert  into `t_user`(`id`,`name`,`password`,`phone`,`last_ip`) values (1,'奈非天','123456','13533805040',NULL),(2,'贪婪的冒险者','123456','13533805040',NULL),(13533805040,'玩家一','123456','13533805040',NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
