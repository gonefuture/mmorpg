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

insert  into `t_bag`(`player_Id`,`bag_size`,`bag_name`,`items`,`type`) values (1234,16,'十六格背包','',1),(1234,32,'三十二格背包','',2),(1235,16,'16格背包','{2:{\"count\":1,\"id\":8947531425876280303,\"locationIndex\":2,\"things\":{\"buffer\":1007,\"describe\":\"霜之哀伤饿了！\",\"id\":1007,\"isBind\":1,\"key\":1007,\"kind\":1,\"name\":\"霜之哀伤\",\"part\":\"双手\",\"price\":106,\"roleProperties\":\"\",\"thingRoleProperty\":[]}},3:{\"count\":1,\"id\":8947531434466214896,\"locationIndex\":3,\"things\":{\"id\":1008,\"isBind\":1,\"key\":1008,\"kind\":1,\"name\":\"暴风城披风\",\"part\":\"披风\",\"price\":107,\"roleProperties\":\"[{\\\"id\\\":3,\\\"value\\\":999},{\\\"id\\\":6,\\\"value\\\":666}]\",\"thingRoleProperty\":[{\"id\":3,\"key\":3,\"name\":\"防御\",\"thingPropertyValue\":999,\"type\":\"2\",\"value\":20},{\"id\":6,\"key\":6,\"name\":\"智力\",\"thingPropertyValue\":666,\"type\":\"2\",\"value\":100}]}},4:{\"count\":1,\"id\":8947553716756546540,\"locationIndex\":4,\"things\":{\"buffer\":106,\"id\":1004,\"isBind\":2,\"key\":1004,\"kind\":2,\"name\":\"红药\",\"part\":\"背包\",\"price\":103,\"roleProperties\":\"\",\"thingRoleProperty\":[]}},5:{\"count\":1,\"id\":8947553725346481134,\"locationIndex\":5,\"things\":{\"buffer\":106,\"id\":1006,\"isBind\":1,\"key\":1006,\"kind\":1,\"name\":\"银鳞胸甲\",\"part\":\"上身\",\"price\":105,\"roleProperties\":\"[{\\\"id\\\":3,\\\"value\\\":999},{\\\"id\\\":5,\\\"value\\\":999},{\\\"id\\\":4,\\\"value\\\":999}]\",\"thingRoleProperty\":[{\"describe\":\"五点点力量等一点战力\",\"id\":5,\"key\":5,\"name\":\"力量\",\"thingPropertyValue\":999,\"type\":\"2\",\"value\":100},{\"$ref\":\"$[3].things.thingRoleProperty[0]\"},{\"describe\":\"一点基础攻击力等于一点战力\",\"id\":4,\"key\":4,\"name\":\"攻击力\",\"thingPropertyValue\":999,\"type\":\"2\",\"value\":30},{}]}}}',1),(1313,16,'十六格背包','{3:{\"count\":1,\"id\":8348711215563801577,\"locationIndex\":3,\"things\":{\"buffer\":1,\"describe\":\"屠龙宝刀，点击就送。\",\"id\":1001,\"isBind\":1,\"key\":1001,\"kind\":1,\"name\":\"屠龙宝刀\",\"part\":\"双手\",\"roleProperties\":\"[{\\\"id\\\":5,\\\"value\\\":400},{\\\"id\\\":3,\\\"value\\\":200},{\\\"id\\\":4,\\\"value\\\":666}]\",\"thingRoleProperty\":[{\"describe\":\"一点基础攻击力等于一点战力\",\"id\":4,\"key\":4,\"name\":\"攻击力\",\"thingPropertyValue\":777,\"type\":\"1\",\"value\":30},{\"describe\":\"五点点力量等一点战力\",\"id\":5,\"key\":5,\"name\":\"力量\",\"thingPropertyValue\":400,\"type\":\"1\",\"value\":100},{\"id\":3,\"key\":3,\"name\":\"防御\",\"thingPropertyValue\":666,\"type\":\"1\",\"value\":20}]}},4:{\"count\":1,\"id\":8348711219858768879,\"locationIndex\":4,\"things\":{\"buffer\":1007,\"describe\":\"霜之哀伤饿了！\",\"id\":1007,\"isBind\":1,\"key\":1007,\"kind\":1,\"name\":\"霜之哀伤\",\"part\":\"双手\",\"roleProperties\":\"\",\"thingRoleProperty\":[]}},5:{\"count\":1,\"id\":8300197184838566889,\"locationIndex\":5,\"things\":{\"buffer\":1,\"describe\":\"屠龙宝刀，点击就送。\",\"id\":1001,\"isBind\":1,\"key\":1001,\"kind\":1,\"name\":\"屠龙宝刀\",\"part\":\"双手\",\"roleProperties\":\"[{\\\"id\\\":5,\\\"value\\\":400},{\\\"id\\\":3,\\\"value\\\":200},{\\\"id\\\":4,\\\"value\\\":666}]\",\"thingRoleProperty\":[{\"id\":3,\"key\":3,\"name\":\"基础防御\",\"thingPropertyValue\":200,\"type\":\"1\",\"value\":20},{\"id\":5,\"key\":5,\"name\":\"基础力量\",\"thingPropertyValue\":400,\"type\":\"1\",\"value\":100},{\"id\":4,\"key\":4,\"name\":\"基础攻击力\",\"thingPropertyValue\":666,\"type\":\"1\",\"value\":30}]}},7:{\"count\":1,\"id\":8575889911503127535,\"locationIndex\":7,\"things\":{\"buffer\":1007,\"describe\":\"霜之哀伤饿了！\",\"id\":1007,\"isBind\":1,\"key\":1007,\"kind\":1,\"name\":\"霜之哀伤\",\"part\":\"双手\",\"roleProperties\":\"\",\"thingRoleProperty\":[]}},8:{\"count\":1,\"id\":8575889915798094832,\"locationIndex\":8,\"things\":{\"id\":1008,\"isBind\":1,\"key\":1008,\"kind\":1,\"name\":\"暴风城披风\",\"part\":\"披风\",\"roleProperties\":\"[{\\\"id\\\":3,\\\"value\\\":999},{\\\"id\\\":6,\\\"value\\\":666}]\",\"thingRoleProperty\":[{\"id\":6,\"key\":6,\"name\":\"智力\",\"thingPropertyValue\":666,\"type\":\"1\",\"value\":100},{},{\"id\":3,\"key\":3,\"name\":\"防御\",\"thingPropertyValue\":999,\"type\":\"1\",\"value\":20}]}},9:{\"count\":1,\"id\":8575921616951706604,\"locationIndex\":9,\"things\":{\"buffer\":106,\"id\":1004,\"isBind\":2,\"key\":1004,\"kind\":2,\"name\":\"红药\",\"part\":\"背包\",\"roleProperties\":\"\",\"thingRoleProperty\":[]}}}',1);

/*Table structure for table `t_mail` */

DROP TABLE IF EXISTS `t_mail`;

CREATE TABLE `t_mail` (
  `id` int(11) NOT NULL,
  `subject` varchar(32) NOT NULL,
  `content` varchar(64) NOT NULL,
  `attachment` varchar(512) DEFAULT NULL,
  `sender` bigint(20) NOT NULL,
  `receiver` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_mail` */

insert  into `t_mail`(`id`,`subject`,`content`,`attachment`,`sender`,`receiver`) values (2107226061,'主题','内容',NULL,1313,1235),(2107268428,'主4545题','内45容','{\"count\":2,\"id\":8681397505411253232,\"locationIndex\":2,\"things\":{\"id\":1008,\"isBind\":1,\"key\":1008,\"kind\":1,\"name\":\"暴风城披风\",\"part\":\"披风\",\"price\":107,\"roleProperties\":\"[{\\\"id\\\":3,\\\"value\\\":999},{\\\"id\\\":6,\\\"value\\\":666}]\",\"thingRoleProperty\":[{\"id\":3,\"key\":3,\"name\":\"防御\",\"thingPropertyValue\":999,\"type\":\"2\",\"value\":20},{\"id\":6,\"key\":6,\"name\":\"智力\",\"thingPropertyValue\":666,\"type\":\"2\",\"value\":100}]}}',1313,1235),(2109858830,'五装备','内容',NULL,1235,1313);

/*Table structure for table `t_player` */

DROP TABLE IF EXISTS `t_player`;

CREATE TABLE `t_player` (
  `id` bigint(20) NOT NULL,
  `name` varchar(32) NOT NULL,
  `hp` bigint(20) NOT NULL,
  `exp` int(11) NOT NULL COMMENT '经验值',
  `mp` bigint(20) NOT NULL,
  `position` varchar(32) NOT NULL,
  `state` int(11) NOT NULL,
  `scene` int(11) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `equipments` text NOT NULL,
  `money` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_player` */

insert  into `t_player`(`id`,`name`,`hp`,`exp`,`mp`,`position`,`state`,`scene`,`user_id`,`equipments`,`money`) values (1,'只会装死的猎人 233',0,0,600,'0',-1,6,1,'{}',0),(2,'被猎人捉了当宠物的德鲁伊大咕咕',2424,0,44,'0',0,5,1,'',0),(4,'瞬发炉石的萨满祭司',11212,0,1212,'0',0,2,13533805040,'',0),(123,'死在冲锋路上的狂暴战',9999,0,9999,'0',0,1,13533805040,'',0),(1212,'阿尔萨斯',1000,0,1100,'0',0,2,13533805040,'',0),(1313,'蓝色巨龙卡雷苟斯',600,0,600,'0',0,6,2,'{}',9893);

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

insert  into `t_user`(`id`,`name`,`password`,`phone`,`last_ip`) values (1,'奈非天','123','13533805040',NULL),(2,'贪婪的冒险者','123','13533805040',NULL),(13533805040,'玩家一','123','13533805040',NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
