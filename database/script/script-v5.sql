-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: mpos
-- ------------------------------------------------------
-- Server version	8.0.29

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cash_up`
--

DROP TABLE IF EXISTS `cash_up`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cash_up` (
  `cashup_id` int NOT NULL AUTO_INCREMENT,
  `open_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `close_date` timestamp NULL DEFAULT NULL,
  `open_amount_cash` decimal(15,2) NOT NULL,
  `closed_amount_cash` decimal(15,2) NOT NULL,
  `description` varchar(255) NOT NULL,
  `open_user_id` varchar(255) NOT NULL,
  `close_user_id` varchar(255) NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`cashup_id`),
  KEY `open_user_id` (`open_user_id`),
  KEY `close_user_id` (`close_user_id`),
  CONSTRAINT `cash_up_ibfk_1` FOREIGN KEY (`open_user_id`) REFERENCES `users` (`username`),
  CONSTRAINT `cash_up_ibfk_2` FOREIGN KEY (`close_user_id`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cash_up`
--

LOCK TABLES `cash_up` WRITE;
/*!40000 ALTER TABLE `cash_up` DISABLE KEYS */;
/*!40000 ALTER TABLE `cash_up` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customers` (
  `discount` decimal(15,2) NOT NULL DEFAULT '0.00',
  `discount_type` tinyint(1) NOT NULL DEFAULT '0',
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`name`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  KEY `customers_ibfk_3` (`created_by`),
  CONSTRAINT `customers_ibfk_3` FOREIGN KEY (`created_by`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inventories`
--

DROP TABLE IF EXISTS `inventories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inventories` (
  `id` int NOT NULL AUTO_INCREMENT,
  `item` int NOT NULL,
  `timestamp` datetime NOT NULL,
  `comment` text,
  `changed_quantity` int DEFAULT '0',
  `after_quantity` int DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `item` (`item`),
  CONSTRAINT `inventories_ibfk_1` FOREIGN KEY (`item`) REFERENCES `items` (`item_id`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventories`
--

LOCK TABLES `inventories` WRITE;
/*!40000 ALTER TABLE `inventories` DISABLE KEYS */;
INSERT INTO `inventories` VALUES (1,1,'2021-02-01 00:00:00','',5,15),(2,1,'2025-04-13 17:44:36','Updated quantity for item ID: 1',10,50),(3,1,'2025-04-13 17:44:48','Updated quantity for item ID: 1',5,55),(4,1,'2025-04-13 17:45:05','Updated quantity for item ID: 1',10,65),(5,1,'2025-04-13 17:45:19','Updated quantity for item ID: 1',10,75),(6,1,'2025-04-13 18:08:33','Updated quantity for item ID: 1',0,75),(7,1,'2025-04-13 18:14:30','Updated quantity for item ID: 1',5,80),(8,1,'2025-04-13 18:14:59','Updated quantity for item ID: 1',10,90),(9,1,'2025-04-13 18:15:43','Updated quantity for item ID: 1',10,100),(10,1,'2025-04-13 18:20:47','Updated quantity for item ID: 1',10,110),(11,1,'2025-04-13 18:23:48','Updated quantity for item ID: 1',5,115),(12,1,'2025-04-13 18:32:09','Updated quantity for item ID: 1',5,120),(13,1,'2025-04-13 18:32:11','Updated quantity for item ID: 1',5,125),(14,1,'2025-04-13 18:32:11','Updated quantity for item ID: 1',5,130),(15,1,'2025-04-13 18:32:11','Updated quantity for item ID: 1',5,135),(16,1,'2025-04-13 18:35:50','Updated quantity for item ID: 1',5,140),(17,1,'2025-04-13 18:35:51','Updated quantity for item ID: 1',5,145),(18,1,'2025-04-13 18:35:51','Updated quantity for item ID: 1',5,150),(19,1,'2025-04-13 18:35:52','Updated quantity for item ID: 1',5,155),(20,1,'2025-04-13 18:35:54','Updated quantity for item ID: 1',0,155),(21,2,'2025-04-13 18:36:05','Updated quantity for item ID: 2',10,10),(22,2,'2025-04-13 18:36:05','Updated quantity for item ID: 2',10,20),(23,2,'2025-04-13 18:36:06','Updated quantity for item ID: 2',10,30),(24,2,'2025-04-13 18:36:06','Updated quantity for item ID: 2',10,40),(25,2,'2025-04-13 18:36:06','Updated quantity for item ID: 2',10,50),(26,22,'2025-04-13 23:17:24','Updated quantity for item ID: 22',10,10),(27,1,'2025-04-14 09:20:56','Update inventory',5,160),(28,1,'2025-04-14 09:33:16','Receiving item',5,165),(29,1,'2025-04-14 09:35:10','Selling item',-5,160),(30,22,'2025-04-14 13:28:11','Selling item',-5,5),(31,22,'2025-04-14 13:28:27','Receiving item',5,10),(32,22,'2025-04-14 13:28:49','Selling item',-5,5),(33,22,'2025-04-14 13:28:49','Selling item',-15,-10),(34,22,'2025-04-14 13:28:50','Selling item',-5,-15),(35,22,'2025-04-14 13:28:50','Selling item',-15,-30),(36,22,'2025-04-14 13:36:26','Selling item',30,0),(37,22,'2025-04-14 13:36:52','Selling item',30,30),(38,22,'2025-04-14 13:36:53','Selling item',30,60),(39,22,'2025-04-14 13:37:04','Selling item',30,90),(40,1,'2025-04-14 13:37:38','Selling item',-10,150),(41,1,'2025-04-14 13:37:40','Selling item',-10,140),(42,1,'2025-04-14 13:37:40','Selling item',-10,130),(43,1,'2025-04-14 13:37:40','Selling item',-10,120),(44,1,'2025-04-14 13:37:52','Selling item',-10,110),(45,1,'2025-04-14 13:39:46','Selling item',-10,100),(46,22,'2025-04-14 13:43:21','Selling item',-1,89),(47,23,'2025-04-14 13:44:11','Update inventory',20,20),(48,24,'2025-04-14 13:48:53','Update inventory',10,10),(49,30,'2025-04-14 13:49:00','Update inventory',25,25),(50,3,'2025-04-14 13:49:07','Update inventory',5,5),(51,4,'2025-04-14 13:49:13','Update inventory',10,10),(52,5,'2025-04-14 13:49:22','Update inventory',5,5),(53,10,'2025-04-14 13:49:28','Update inventory',1,1),(54,1,'2025-04-14 14:09:38','Receiving item',20,120),(55,2,'2025-04-14 14:09:38','Receiving item',100,150),(56,1,'2025-04-14 14:10:52','Receiving item',5,125),(57,22,'2025-05-12 13:49:28','Receiving item',5,94),(58,22,'2025-05-12 13:49:28','Receiving item',89,183),(59,22,'2025-05-12 13:50:10','Receiving item',7,190),(60,22,'2025-05-12 13:50:39','Receiving item',10,200),(61,22,'2025-05-12 13:51:16','Selling item',-10,190),(62,22,'2025-05-12 13:51:45','Selling item',-10,180);
/*!40000 ALTER TABLE `inventories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `items`
--

DROP TABLE IF EXISTS `items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `items` (
  `item_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `category` varchar(255) NOT NULL,
  `supplier_id` int DEFAULT '0',
  `barcode` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `cost_price` decimal(15,2) NOT NULL,
  `selling_price` decimal(15,2) NOT NULL,
  `reorder_level` decimal(15,3) DEFAULT '0.000',
  `pic_filename` varchar(255) DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `owned_by` varchar(255) DEFAULT NULL,
  `quantity` int DEFAULT '0',
  PRIMARY KEY (`item_id`),
  UNIQUE KEY `uq_item_name_owned_by` (`name`,`owned_by`),
  KEY ` barcode` (`barcode`),
  KEY `items_ibfk_1` (`supplier_id`),
  KEY `items_ibfk_2` (`owned_by`),
  CONSTRAINT `items_ibfk_1` FOREIGN KEY (`supplier_id`) REFERENCES `suppliers` (`id`),
  CONSTRAINT `items_ibfk_2` FOREIGN KEY (`owned_by`) REFERENCES `users` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `items`
--

LOCK TABLES `items` WRITE;
/*!40000 ALTER TABLE `items` DISABLE KEYS */;
INSERT INTO `items` VALUES (1,'Điện thoại Samsung Galaxy A52','Điện thoại',0,'1234567','Điện thoại thông minh 128GB',5000000.00,6500000.00,10.000,'samsung_a52.jpg',0,'khang',125),(2,'Cà phê hòa tan Nescafé','Thực phẩm',0,'223213','Hộp 20 gói cà phê hòa tan',80000.00,120000.00,50.000,'nescafe_instant.jpg',0,'khang',150),(3,'Máy lọc nước RO Karofi','Gia dụng',3,'3','Máy lọc nước 10 lít',3500000.00,4500000.00,5.000,'karofi_ro.jpg',0,'khang',5),(4,'Ghế văn phòng Hòa Phát','Nội thất',4,'3','Ghế xoay văn phòng cao cấp',1200000.00,1800000.00,15.000,'hoaphat_chair.jpg',1,'khang',10),(5,'Pin sạc dự phòng Xiaomi 10000mAh','Phụ kiện',5,'4','Pin sạc nhanh USB-C',250000.00,350000.00,20.000,'xiaomi_powerbank.jpg',0,'khang',5),(10,'Laptop Dell Inspiron 15','Điện tử',0,'21331','Laptop 15 inch, Intel Core i5, 8GB RAM',15000000.00,18000000.00,10000.000,'dell_inspiron.jpg',0,'khang',1),(22,'Bánh gạo OneOne','Bánh kẹo',0,'','',20000.00,25000.00,0.000,'',0,'khang',180),(23,'Kẹo dẻo','Bánh kẹo',0,'','',10000.00,15000.00,0.000,'',0,'khang',20),(24,'Cocacola','Đồ uống',0,'','',8000.00,10000.00,0.000,'',0,'khang',10),(30,'Trà táo','Đồ uống',0,'','',10000.00,12000.00,0.000,'',0,'khang',25);
/*!40000 ALTER TABLE `items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `receivings`
--

DROP TABLE IF EXISTS `receivings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `receivings` (
  `receiving_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `supplier_id` int DEFAULT NULL,
  `user` varchar(255) NOT NULL DEFAULT '',
  `comment` text,
  `receiving_id` int NOT NULL AUTO_INCREMENT,
  `payment_type` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`receiving_id`),
  KEY `supplier_id` (`supplier_id`),
  KEY `user` (`user`),
  KEY `receiving_time` (`receiving_time`),
  CONSTRAINT `receivings_ibfk_1` FOREIGN KEY (`user`) REFERENCES `users` (`username`),
  CONSTRAINT `receivings_ibfk_2` FOREIGN KEY (`supplier_id`) REFERENCES `suppliers` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `receivings`
--

LOCK TABLES `receivings` WRITE;
/*!40000 ALTER TABLE `receivings` DISABLE KEYS */;
/*!40000 ALTER TABLE `receivings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `receivings_items`
--

DROP TABLE IF EXISTS `receivings_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `receivings_items` (
  `receiving_id` int NOT NULL DEFAULT '0',
  `item_id` int NOT NULL DEFAULT '0',
  `description` varchar(30) DEFAULT NULL,
  `serialnumber` varchar(30) DEFAULT NULL,
  `line` int NOT NULL,
  `quantity_purchased` decimal(15,3) NOT NULL DEFAULT '0.000',
  `item_cost_price` decimal(15,2) NOT NULL,
  `item_unit_price` decimal(15,2) NOT NULL,
  `discount` decimal(15,2) NOT NULL DEFAULT '0.00',
  `discount_type` tinyint(1) NOT NULL DEFAULT '0',
  `item_location` int NOT NULL,
  `receiving_quantity` decimal(15,3) NOT NULL DEFAULT '1.000',
  PRIMARY KEY (`receiving_id`,`item_id`,`line`),
  KEY `item_id` (`item_id`),
  KEY `receivings_items_ibfk_3` (`item_location`),
  CONSTRAINT `receivings_items_ibfk_1` FOREIGN KEY (`item_id`) REFERENCES `items` (`item_id`),
  CONSTRAINT `receivings_items_ibfk_2` FOREIGN KEY (`receiving_id`) REFERENCES `receivings` (`receiving_id`),
  CONSTRAINT `receivings_items_ibfk_3` FOREIGN KEY (`item_location`) REFERENCES `stock_locations` (`location_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `receivings_items`
--

LOCK TABLES `receivings_items` WRITE;
/*!40000 ALTER TABLE `receivings_items` DISABLE KEYS */;
/*!40000 ALTER TABLE `receivings_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `role_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'admin'),(2,'user');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sales`
--

DROP TABLE IF EXISTS `sales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sales` (
  `sale_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `customer_id` varchar(255) DEFAULT NULL,
  `user` varchar(255) NOT NULL DEFAULT '',
  `comment` text,
  `sale_id` int NOT NULL AUTO_INCREMENT,
  `sale_status` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`sale_id`),
  KEY `customer_id` (`customer_id`),
  KEY `user` (`user`),
  KEY `sale_time` (`sale_time`),
  CONSTRAINT `sales_ibfk_1` FOREIGN KEY (`user`) REFERENCES `users` (`username`),
  CONSTRAINT `sales_ibfk_2` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sales`
--

LOCK TABLES `sales` WRITE;
/*!40000 ALTER TABLE `sales` DISABLE KEYS */;
/*!40000 ALTER TABLE `sales` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sales_items`
--

DROP TABLE IF EXISTS `sales_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sales_items` (
  `sale_id` int NOT NULL DEFAULT '0',
  `item_id` int NOT NULL DEFAULT '0',
  `description` varchar(255) DEFAULT NULL,
  `serialnumber` varchar(30) DEFAULT NULL,
  `line` int NOT NULL DEFAULT '0',
  `quantity_purchased` decimal(15,3) NOT NULL DEFAULT '0.000',
  `item_cost_price` decimal(15,2) NOT NULL,
  `item_unit_price` decimal(15,2) NOT NULL,
  `discount` decimal(15,2) NOT NULL DEFAULT '0.00',
  `discount_type` tinyint(1) NOT NULL DEFAULT '0',
  `item_location` int NOT NULL,
  PRIMARY KEY (`sale_id`,`item_id`,`line`),
  KEY `sale_id` (`sale_id`),
  KEY `item_id` (`item_id`),
  KEY `item_location` (`item_location`),
  CONSTRAINT `sales_items_ibfk_1` FOREIGN KEY (`item_id`) REFERENCES `items` (`item_id`),
  CONSTRAINT `sales_items_ibfk_2` FOREIGN KEY (`sale_id`) REFERENCES `sales` (`sale_id`),
  CONSTRAINT `sales_items_ibfk_3` FOREIGN KEY (`item_location`) REFERENCES `stock_locations` (`location_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sales_items`
--

LOCK TABLES `sales_items` WRITE;
/*!40000 ALTER TABLE `sales_items` DISABLE KEYS */;
/*!40000 ALTER TABLE `sales_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stock_locations`
--

DROP TABLE IF EXISTS `stock_locations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stock_locations` (
  `location_id` int NOT NULL AUTO_INCREMENT,
  `location_name` varchar(255) DEFAULT NULL,
  `address` varchar(255) NOT NULL,
  `ward` varchar(255) NOT NULL,
  `district` varchar(255) NOT NULL,
  `province` varchar(255) NOT NULL,
  `country` varchar(255) NOT NULL,
  `owned_by` varchar(255) NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`location_id`),
  KEY `stock_locations_ibfk_1` (`owned_by`),
  CONSTRAINT `stock_locations_ibfk_1` FOREIGN KEY (`owned_by`) REFERENCES `users` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stock_locations`
--

LOCK TABLES `stock_locations` WRITE;
/*!40000 ALTER TABLE `stock_locations` DISABLE KEYS */;
/*!40000 ALTER TABLE `stock_locations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `suppliers`
--

DROP TABLE IF EXISTS `suppliers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `suppliers` (
  `id` int NOT NULL,
  `company_name` varchar(255) NOT NULL,
  `phone_number` varchar(255) NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `owned_by` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `account_number` (`phone_number`),
  KEY `person_id` (`id`),
  KEY `company_name` (`company_name`,`deleted`),
  KEY `suppliers_ibfk_2` (`owned_by`),
  CONSTRAINT `suppliers_ibfk_2` FOREIGN KEY (`owned_by`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `suppliers`
--

LOCK TABLES `suppliers` WRITE;
/*!40000 ALTER TABLE `suppliers` DISABLE KEYS */;
INSERT INTO `suppliers` VALUES (0,'','',0,NULL,''),(2,'Công ty TNHH Minh Anh','1234567890',0,'khang',NULL),(3,'Công ty CP Thương Mại Sài Gòn','0987654321',0,'khang',NULL),(4,'Công ty TNHH Phát Triển Đông Á','1122334455',0,'khang',NULL),(5,'Công ty Hợp Phát','2233445566',1,'khang',NULL),(6,'Công ty TNHH Công Nghệ Xanh','3344556677',0,'khang',NULL);
/*!40000 ALTER TABLE `suppliers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` int NOT NULL,
  `managed_by` varchar(255) NOT NULL DEFAULT '',
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `email` varchar(255) NOT NULL,
  PRIMARY KEY (`username`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `users_ibfk_2` (`role`),
  KEY `users_ibfk_3` (`managed_by`),
  CONSTRAINT `users_ibfk_2` FOREIGN KEY (`role`) REFERENCES `roles` (`role_id`),
  CONSTRAINT `users_ibfk_3` FOREIGN KEY (`managed_by`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('admin','$2y$10$vJBSMlD02EC7ENSrKfVQXuvq9tNRHMtcOA8MSK2NYS748HHWm.gcG',1,'admin',0,'admin@ex.com'),('khang','1',2,'admin',0,'khang@ex.com');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-28 15:36:35
