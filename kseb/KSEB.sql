/*
SQLyog Ultimate v11.11 (64 bit)
MySQL - 5.7.9 : Database - kseb
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`kseb` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `kseb`;

/*Table structure for table `bill` */

DROP TABLE IF EXISTS `bill`;

CREATE TABLE `bill` (
  `bill_id` int(50) NOT NULL AUTO_INCREMENT,
  `cons_id` int(50) DEFAULT NULL,
  `usage` varchar(200) DEFAULT NULL,
  `amount` varchar(100) DEFAULT NULL,
  `reading_id` int(50) DEFAULT NULL,
  `bill_date` date DEFAULT NULL,
  `pay_status` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`bill_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `bill` */

insert  into `bill`(`bill_id`,`cons_id`,`usage`,`amount`,`reading_id`,`bill_date`,`pay_status`) values (4,11,'50','160.0',7,'2020-03-11','Payed');

/*Table structure for table `complaints` */

DROP TABLE IF EXISTS `complaints`;

CREATE TABLE `complaints` (
  `comp_id` int(50) NOT NULL AUTO_INCREMENT,
  `cons_id` int(50) DEFAULT NULL,
  `complaints` varchar(100) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`comp_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

/*Data for the table `complaints` */

insert  into `complaints`(`comp_id`,`cons_id`,`complaints`,`date`,`status`) values (5,11,'fgxc','2020-03-11','NA'),(6,11,'bshs','2020-03-11','NA'),(7,11,'bzb','2020-03-11','NA');

/*Table structure for table `consumers` */

DROP TABLE IF EXISTS `consumers`;

CREATE TABLE `consumers` (
  `cons_id` int(50) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `f_name` varchar(100) DEFAULT NULL,
  `l_name` varchar(100) DEFAULT NULL,
  `h_name` varchar(100) DEFAULT NULL,
  `place` varchar(100) DEFAULT NULL,
  `pincode` varchar(50) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `connectiontype` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`cons_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

/*Data for the table `consumers` */

insert  into `consumers`(`cons_id`,`login_id`,`f_name`,`l_name`,`h_name`,`place`,`pincode`,`phone`,`email`,`connectiontype`) values (9,NULL,'vipinnnnn','kaimal','sreenilay','malakkara','689532','9744423271','vipinmkaimal@gmail.com',NULL),(11,19,'kkk','kli','kli','kli','987778','9877899876','guyv@fd.gd','Home');

/*Table structure for table `employee` */

DROP TABLE IF EXISTS `employee`;

CREATE TABLE `employee` (
  `emp_id` int(50) NOT NULL AUTO_INCREMENT,
  `login_id` int(50) DEFAULT NULL,
  `f_name` varchar(100) DEFAULT NULL,
  `l_name` varchar(100) DEFAULT NULL,
  `h_name` varchar(100) DEFAULT NULL,
  `place` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `qualification` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`emp_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `employee` */

insert  into `employee`(`emp_id`,`login_id`,`f_name`,`l_name`,`h_name`,`place`,`email`,`phone`,`qualification`) values (4,11,'vipin','kaimal','sreenilay','malakkara','vipinmkaimal@gmail.com','9744423271','mca'),(5,14,'vipin','kaimal','sreenilay','malakkara','vipinmkaimal@gmail.com','9744423271','mca');

/*Table structure for table `login` */

DROP TABLE IF EXISTS `login`;

CREATE TABLE `login` (
  `login_id` int(50) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `login_type` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`login_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;

/*Data for the table `login` */

insert  into `login`(`login_id`,`username`,`password`,`login_type`) values (1,'vipin','vipin','admin'),(2,'asd','asd','employee'),(3,'asd','asd','employee'),(4,'asd','asd','employee'),(5,'vipin','vipin','employee'),(6,'vipin','vipin','employee'),(7,'vipin','vipin','employee'),(8,'aaa','aaaa','consumers'),(9,'qwerty','asd','consumers'),(10,'vipin','vipin','employee'),(11,'vipin','vipin','employee'),(12,'vipin','vipin','consumers'),(13,'aaa','ppp','employee'),(14,'vipin','vipin','employee'),(15,'kkj','kkj','consumers'),(16,'kkj','kkj','consumers'),(17,'kli','kli','consumers'),(18,'kil','kil','consumers'),(19,'ilo','ilo','consumers');

/*Table structure for table `meter_reading` */

DROP TABLE IF EXISTS `meter_reading`;

CREATE TABLE `meter_reading` (
  `reading_id` int(50) NOT NULL AUTO_INCREMENT,
  `cons_id` int(50) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `time` int(50) DEFAULT NULL,
  `cur_reading` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`reading_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

/*Data for the table `meter_reading` */

insert  into `meter_reading`(`reading_id`,`cons_id`,`date`,`time`,`cur_reading`) values (7,11,'2020-03-11',63356,'50');

/*Table structure for table `notification` */

DROP TABLE IF EXISTS `notification`;

CREATE TABLE `notification` (
  `noti_id` int(50) NOT NULL AUTO_INCREMENT,
  `notificationtype` varchar(100) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`noti_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `notification` */

insert  into `notification`(`noti_id`,`notificationtype`,`description`,`date`) values (1,'Home','djfghieged','2020-03-10'),(3,'Industrial','rdtfghjk','2020-03-10');

/*Table structure for table `payment` */

DROP TABLE IF EXISTS `payment`;

CREATE TABLE `payment` (
  `pay_id` int(50) NOT NULL AUTO_INCREMENT,
  `bill_id` varchar(100) DEFAULT NULL,
  `cons_id` int(50) DEFAULT NULL,
  `amount` int(100) DEFAULT NULL,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`pay_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `payment` */

insert  into `payment`(`pay_id`,`bill_id`,`cons_id`,`amount`,`date`) values (2,'4',11,160,'2020-03-11');

/*Table structure for table `prediction` */

DROP TABLE IF EXISTS `prediction`;

CREATE TABLE `prediction` (
  `pred_id` int(50) NOT NULL AUTO_INCREMENT,
  `area_id` int(50) DEFAULT NULL,
  `no_of_consumers` int(100) DEFAULT NULL,
  `avg_temp` varchar(100) DEFAULT NULL,
  `avg_rainfall` varchar(100) DEFAULT NULL,
  `no_of_holidays` varchar(100) DEFAULT NULL,
  `humidity` varchar(100) DEFAULT NULL,
  `predicted_usage` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`pred_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `prediction` */

/*Table structure for table `tariff` */

DROP TABLE IF EXISTS `tariff`;

CREATE TABLE `tariff` (
  `tariff_if` int(11) NOT NULL AUTO_INCREMENT,
  `connection_type` varchar(100) DEFAULT NULL,
  `minimum_usage` varchar(100) DEFAULT NULL,
  `maximum_usage` varchar(100) DEFAULT NULL,
  `amount_per_unit` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`tariff_if`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `tariff` */

insert  into `tariff`(`tariff_if`,`connection_type`,`minimum_usage`,`maximum_usage`,`amount_per_unit`) values (1,'hj','30','60','1000');

/*Table structure for table `wallet` */

DROP TABLE IF EXISTS `wallet`;

CREATE TABLE `wallet` (
  `wallet_id` int(11) NOT NULL AUTO_INCREMENT,
  `cons_id` int(11) DEFAULT NULL,
  `transaction_amount` decimal(10,0) DEFAULT NULL,
  `transaction_type` varchar(50) DEFAULT NULL,
  `date` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`wallet_id`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

/*Data for the table `wallet` */

insert  into `wallet`(`wallet_id`,`cons_id`,`transaction_amount`,`transaction_type`,`date`) values (1,11,1000,'credit','2020-03-11'),(2,11,1000,'credit','2020-03-11'),(3,11,100,'credit','2020-03-11'),(4,11,100,'credit','2020-03-11'),(6,11,160,'debit','2020-03-11');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
