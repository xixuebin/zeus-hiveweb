-- MySQL dump 10.13  Distrib 5.5.32, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: zeustest
-- ------------------------------------------------------`
-- Server version	5.5.32-0ubuntu0.12.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `HIVE_TABLE`
--

DROP TABLE IF EXISTS `HIVE_TABLE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `HIVE_TABLE` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `create_user_id` int(11) NOT NULL,
  `table_name` varchar(100) NOT NULL,
  `comment` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `db_name` varchar(100) DEFAULT 'default',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 COMMENT='user do not query table';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `QUERIES`
--

DROP TABLE IF EXISTS `QUERIES`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QUERIES` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `callback` varchar(400) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `cpu_time` int(11) DEFAULT NULL,
  `created` datetime NOT NULL,
  `error_code` int(11) DEFAULT NULL,
  `error_msg` mediumtext CHARACTER SET utf8 COLLATE utf8_bin,
  `job_id` varchar(400) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `task_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `query_sql` mediumtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `result_location` varchar(400) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `status` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `total_time` int(11) DEFAULT NULL,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `user_id` int(11) NOT NULL default 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=349 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TABLE_FIELD`
--

DROP TABLE IF EXISTS `TABLE_FIELD`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TABLE_FIELD` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `table_id` int(11) unsigned NOT NULL,
  `field` varchar(100) NOT NULL,
  `field_type` varchar(100) NOT NULL,
  `field_comment` varchar(200) NOT NULL,
  `is_partition` char(1) DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `table_id` (`table_id`),
  CONSTRAINT `TABLE_FIELD_ibfk_1` FOREIGN KEY (`table_id`) REFERENCES `HIVE_TABLE` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8 COMMENT='user do not query table';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `USER`
--

DROP TABLE IF EXISTS `USER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USER` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) NOT NULL,
  `pwd` varchar(50) NOT NULL,
  `level` int(1) NOT NULL DEFAULT '1',
  `is_delete` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='user info table';
/*!40101 SET character_set_client = @saved_cs_client */;

insert into  USER(user_name,pwd,level) values ("admin","admin123456");

--
-- Table structure for table `USER_TABLE`
--

DROP TABLE IF EXISTS `USER_TABLE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USER_TABLE` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `table_id` int(11) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `table_id` (`table_id`),
  CONSTRAINT `USER_TABLE_ibfk_1` FOREIGN KEY (`table_id`) REFERENCES `HIVE_TABLE` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=169 DEFAULT CHARSET=utf8 COMMENT='user do not query table';
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-01-07 17:10:14
