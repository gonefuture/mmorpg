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

/*Table structure for table `t_buffer` */

DROP TABLE IF EXISTS `t_buffer`;

CREATE TABLE `t_buffer` (
  `id` int(11) NOT NULL,
  `name` varchar(32) NOT NULL,
  `type` int(11) NOT NULL DEFAULT '1' COMMENT '类型，静态、动态、状态buffer',
  `cover` int(11) NOT NULL DEFAULT '1' COMMENT '是否可覆盖',
  `hp` int(11) NOT NULL DEFAULT '0',
  `mp` int(11) NOT NULL DEFAULT '0',
  `state` int(11) NOT NULL DEFAULT '0' COMMENT '状态',
  `duration` bigint(20) NOT NULL COMMENT '持续时间（单位毫秒）',
  `interval_time` int(11) NOT NULL COMMENT '生效间隔时间',
  `times` int(11) NOT NULL DEFAULT '1' COMMENT '生效间隔',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_buffer` */

insert  into `t_buffer`(`id`,`name`,`type`,`cover`,`hp`,`mp`,`state`,`duration`,`interval_time`,`times`) values (101,'狂暴',1,1,0,0,101,120000,0,1),(102,'英勇',1,1,0,0,102,120000,0,1),(103,'蝰蛇守护',2,1,0,-5,0,20000,0,1),(104,'复苏之风',2,1,20,0,0,60000,1000,10),(105,'魔法值恢复',2,1,0,5,0,3000,2000,5);

/*Table structure for table `t_game_object` */

DROP TABLE IF EXISTS `t_game_object`;

CREATE TABLE `t_game_object` (
  `id` bigint(20) NOT NULL,
  `name` varchar(32) NOT NULL,
  `hp` bigint(20) NOT NULL,
  `mp` bigint(20) NOT NULL,
  `talk` varchar(256) NOT NULL COMMENT '谈话',
  `skills` varchar(100) NOT NULL COMMENT '技能',
  `state` int(11) NOT NULL COMMENT '状态',
  `role_type` int(11) NOT NULL,
  `refresh_time` bigint(20) NOT NULL COMMENT '死亡后的刷新时间，单位毫秒',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_game_object` */

insert  into `t_game_object`(`id`,`name`,`hp`,`mp`,`talk`,`skills`,`state`,`role_type`,`refresh_time`) values (1,'软泥怪1',5,5,'哇哇','1',1,2,20000),(2,'软泥怪2',5,5,'哇哇哇','1',1,2,20000),(3,'新手村村长',20,32,'欢迎来到新手村！','1',1,1,30000),(4,'黑衣人',100,5,'十步杀一人，千里不留行。','1',1,1,30000),(5,'伊利丹',20000,10000,'你们这是自寻死路！！','1，2，3，4，5，5',1,2,30000),(6,'疯狂的地精',500,45,'时间就是金钱！','2',1,2,10000),(7,'鱼人',500,40,'噢哇','1',1,2,10000);

/*Table structure for table `t_player` */

DROP TABLE IF EXISTS `t_player`;

CREATE TABLE `t_player` (
  `id` bigint(20) NOT NULL,
  `name` varchar(32) NOT NULL,
  `hp` bigint(20) NOT NULL,
  `mp` bigint(20) NOT NULL,
  `position` varchar(32) NOT NULL,
  `state` int(11) NOT NULL,
  `scene` int(11) NOT NULL,
  `player_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_player` */

insert  into `t_player`(`id`,`name`,`hp`,`mp`,`position`,`state`,`scene`,`player_id`) values (1212,'阿尔萨斯',1000,1100,'0',0,2,13533805040),(1235,'只会装死的猎人 233',242,2247,'0',0,3,1),(1313,'蓝色巨龙卡雷苟斯',4545,1454,'0',0,5,2),(4556,'瞬发炉石的萨满祭司',11212,1212,'0',0,2,13533805040),(14154,'被猎人捉了当宠物的德鲁伊大咕咕',2424,44,'0',0,5,1),(1493484114469936429,'死在冲锋路上的狂暴战',9999,9999,'0',0,1,13533805040);

/*Table structure for table `t_scene` */

DROP TABLE IF EXISTS `t_scene`;

CREATE TABLE `t_scene` (
  `id` int(11) NOT NULL,
  `name` varchar(64) NOT NULL,
  `state` int(11) NOT NULL COMMENT '场景的状态',
  `neighbors` varchar(100) NOT NULL,
  `game_object_ids` varchar(200) NOT NULL COMMENT '场景内游戏对象',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_scene` */

insert  into `t_scene`(`id`,`name`,`state`,`neighbors`,`game_object_ids`) values (1,'新手村',1,'2','1,5'),(2,'泰达希尔',1,'1,3,4','1,2'),(3,'桃花源',1,'2,4,5','2,3,4'),(4,'提瑞斯法森林',1,'3,5','1,2,3,4'),(5,'城堡',1,'4,6','5,1,2'),(6,'紫禁之巅',1,'1,7,6','1,2,3,,4,5'),(7,'伽蓝之洞',1,'1,2,3,4,5,6','1');

/*Table structure for table `t_skill` */

DROP TABLE IF EXISTS `t_skill`;

CREATE TABLE `t_skill` (
  `id` int(11) NOT NULL,
  `name` varchar(32) NOT NULL,
  `skills_type` int(11) NOT NULL,
  `cd` bigint(11) NOT NULL,
  `mp_consumption` bigint(20) NOT NULL,
  `hp_lose` bigint(20) NOT NULL,
  `level` int(11) NOT NULL,
  `state` int(11) NOT NULL,
  `activeTime` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_skill` */

insert  into `t_skill`(`id`,`name`,`skills_type`,`cd`,`mp_consumption`,`hp_lose`,`level`,`state`,`activeTime`) values (1001,'剑刃风暴',1,30,50,120,10,0,0),(1002,'冲锋',1,40,30,10,5,0,0),(1003,'暴风雪',1,30,80,60,10,0,0),(1004,'奥术飞弹',1,10,30,50,5,0,0);

/*Table structure for table `t_state` */

DROP TABLE IF EXISTS `t_state`;

CREATE TABLE `t_state` (
  `id` int(11) NOT NULL,
  `name` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_state` */

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
