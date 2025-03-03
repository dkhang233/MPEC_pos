CREATE DATABASE  IF NOT EXISTS `mpos` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `mpos`;
-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: localhost    Database: mpos
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
-- Table structure for table `app_config`
--

DROP TABLE IF EXISTS `app_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `app_config` (
  `key` varchar(50) NOT NULL,
  `value` varchar(500) NOT NULL,
  `user` varchar(255) NOT NULL,
  PRIMARY KEY (`key`),
  KEY `app_config_ibfk_1` (`user`),
  CONSTRAINT `app_config_ibfk_1` FOREIGN KEY (`user`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_config`
--

LOCK TABLES `app_config` WRITE;
/*!40000 ALTER TABLE `app_config` DISABLE KEYS */;
INSERT INTO `app_config` VALUES ('address','123 Nowhere street','admin'),('allow_duplicate_barcodes','0','admin'),('barcode_content','id','admin'),('barcode_first_row','category','admin'),('barcode_font','Arial','admin'),('barcode_font_size','10','admin'),('barcode_formats','[]','admin'),('barcode_generate_if_empty','0','admin'),('barcode_height','50','admin'),('barcode_num_in_row','2','admin'),('barcode_page_cellspacing','20','admin'),('barcode_page_width','100','admin'),('barcode_second_row','item_code','admin'),('barcode_third_row','unit_price','admin'),('barcode_type','Code39','admin'),('barcode_width','250','admin'),('cash_decimals','2','admin'),('cash_rounding_code','1','admin'),('category_dropdown','0','admin'),('company','Open Source Point of Sale','admin'),('company_logo','','admin'),('country_codes','vn','admin'),('currency_code','VND','admin'),('currency_decimals','0','admin'),('currency_symbol','₫','admin'),('customer_reward_enable','1','admin'),('dateformat','m/d/Y','admin'),('date_or_time_format','','admin'),('default_receivings_discount','0','admin'),('default_receivings_discount_type','0','admin'),('default_register_mode','sale','admin'),('default_sales_discount','0','admin'),('default_sales_discount_type','0','admin'),('default_tax_1_name','','admin'),('default_tax_1_rate','','admin'),('default_tax_2_name','','admin'),('default_tax_2_rate','','admin'),('default_tax_category','Standard','admin'),('default_tax_code','','admin'),('default_tax_jurisdiction','','admin'),('default_tax_rate','8','admin'),('derive_sale_quantity','0','admin'),('dinner_table_enable','1','admin'),('email','changeme@example.com','admin'),('email_receipt_check_behaviour','last','admin'),('enforce_privacy','','admin'),('fax','','admin'),('financial_year','1','admin'),('gcaptcha_enable','0','admin'),('gcaptcha_secret_key','','admin'),('gcaptcha_site_key','','admin'),('giftcard_number','series','admin'),('image_allowed_types','jpg|gif|png','admin'),('image_max_height','480','admin'),('image_max_size','128','admin'),('image_max_width','640','admin'),('include_hsn','0','admin'),('invoice_default_comments','This is a default comment','admin'),('invoice_email_message','Dear {CU}, In attachment the receipt for sale {ISEQ}','admin'),('invoice_enable','1','admin'),('invoice_type','invoice','admin'),('language','english','admin'),('language_code','en-US','admin'),('last_used_invoice_number','0','admin'),('last_used_quote_number','0','admin'),('last_used_work_order_number','0','admin'),('lines_per_page','25','admin'),('line_sequence','0','admin'),('login_form','floating_labels','admin'),('mailpath','/usr/sbin/sendmail','admin'),('msg_msg','','admin'),('msg_pwd','','admin'),('msg_src','','admin'),('msg_uid','','admin'),('multi_pack_enabled','0','admin'),('notify_horizontal_position','center','admin'),('notify_vertical_position','bottom','admin'),('number_locale','vn_VN','admin'),('payment_options_order','cashdebitcredit','admin'),('phone','555-555-5555','admin'),('print_bottom_margin','0','admin'),('print_delay_autoreturn','0','admin'),('print_footer','0','admin'),('print_header','0','admin'),('print_left_margin','0','admin'),('print_receipt_check_behaviour','last','admin'),('print_right_margin','0','admin'),('print_silently','1','admin'),('print_top_margin','0','admin'),('protocol','mail','admin'),('quantity_decimals','0','admin'),('quote_default_comments','This is a default quote comment','admin'),('receipt_font_size','12','admin'),('receipt_show_company_name','1','admin'),('receipt_show_description','1','admin'),('receipt_show_serialnumber','1','admin'),('receipt_show_taxes','0','admin'),('receipt_show_tax_ind','0','admin'),('receipt_show_total_discount','1','admin'),('receipt_template','receipt_default','admin'),('receiving_calculate_average_price','0','admin'),('recv_invoice_format','{CO}','admin'),('return_policy','Test','admin'),('sales_invoice_format','{CO}','admin'),('sales_quote_format','Q%y{QSEQ:6}','admin'),('smtp_crypto','ssl','admin'),('smtp_host','','admin'),('smtp_pass','','admin'),('smtp_port','465','admin'),('smtp_timeout','5','admin'),('smtp_user','','admin'),('suggestions_first_column','name','admin'),('suggestions_second_column','','admin'),('suggestions_third_column','','admin'),('tax_decimals','2','admin'),('tax_id','','admin'),('tax_included','0','admin'),('theme','flatly','admin'),('thousands_separator','1','admin'),('timeformat','H:i:s','admin'),('timezone','America/New_York','admin'),('use_destination_based_tax','0','admin'),('website','','admin'),('work_order_enable','0','admin'),('work_order_format','W%y{WSEQ:6}','admin');
/*!40000 ALTER TABLE `app_config` ENABLE KEYS */;
UNLOCK TABLES;


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
  `transfer_amount_cash` decimal(15,2) NOT NULL,
  `note` int NOT NULL,
  `closed_amount_cash` decimal(15,2) NOT NULL,
  `closed_amount_card` decimal(15,2) NOT NULL,
  `closed_amount_check` decimal(15,2) NOT NULL,
  `closed_amount_total` decimal(15,2) NOT NULL,
  `description` varchar(255) NOT NULL,
  `open_user_id` varchar(255) NOT NULL,
  `close_user_id`  varchar(255) NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `closed_amount_due` decimal(15,2) NOT NULL,
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
  `person_id` int NOT NULL,
  `account_number` varchar(255) NOT NULL DEFAULT '',
  `discount` decimal(15,2) NOT NULL DEFAULT '0.00',
  `discount_type` tinyint(1) NOT NULL DEFAULT '0',
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` varchar(255) NOT NULL,
  `consent` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`person_id`),
  UNIQUE KEY `account_number` (`account_number`),
  KEY `person_id` (`person_id`),
  KEY `customers_ibfk_3` (`created_by`),
  CONSTRAINT `customers_ibfk_1` FOREIGN KEY (`person_id`) REFERENCES `people` (`person_id`),
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
-- Table structure for table `dinner_tables`
--

DROP TABLE IF EXISTS `dinner_tables`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dinner_tables` (
  `dinner_table_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '0',
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`dinner_table_id`),
  KEY `status` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dinner_tables`
--

LOCK TABLES `dinner_tables` WRITE;
/*!40000 ALTER TABLE `dinner_tables` DISABLE KEYS */;
/*!40000 ALTER TABLE `dinner_tables` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grants`
--

DROP TABLE IF EXISTS `grants`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `grants` (
  `permission_id` varchar(255) NOT NULL,
  `user` varchar(255) NOT NULL,
  `menu_group` varchar(32) DEFAULT 'home',
  PRIMARY KEY (`permission_id`,`user`),
  KEY `grants_ibfk_2` (`user`),
  CONSTRAINT `grants_ibfk_1` FOREIGN KEY (`permission_id`) REFERENCES `permissions` (`permission_id`) ON DELETE CASCADE,
  CONSTRAINT `grants_ibfk_2` FOREIGN KEY (`user`) REFERENCES `users` (`username`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grants`
--

LOCK TABLES `grants` WRITE;
/*!40000 ALTER TABLE `grants` DISABLE KEYS */;
INSERT INTO `grants` VALUES ('attributes','admin','office'),('cashups','admin','home'),('config','admin','office'),('customers','admin','home'),('expenses','admin','home'),('expenses_categories','admin','office'),('giftcards','admin','home'),('home','admin','office'),('items','admin','home'),('items_stock','admin','home'),('item_kits','admin','home'),('messages','admin','home'),('office','admin','home'),('receivings','admin','home'),('receivings_stock','admin','home'),('reports','admin','home'),('reports_categories','admin','home'),('reports_customers','admin','home'),('reports_discounts','admin','home'),('reports_expenses_categories','admin','home'),('reports_inventory','admin','home'),('reports_items','admin','home'),('reports_payments','admin','home'),('reports_receivings','admin','home'),('reports_sales','admin','home'),('reports_sales_taxes','admin','home'),('reports_suppliers','admin','home'),('reports_taxes','admin','home'),('reports_users','admin','home'),('sales','admin','home'),('sales_change_price','admin','--'),('sales_delete','admin','--'),('sales_stock','admin','home'),('suppliers','admin','home'),('taxes','admin','office'),('users','admin','office');
/*!40000 ALTER TABLE `grants` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item_quantities`
--

DROP TABLE IF EXISTS `item_quantities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item_quantities` (
  `item_id` int NOT NULL,
  `location_id` int NOT NULL,
  `quantity` decimal(15,3) NOT NULL DEFAULT '0.000',
  PRIMARY KEY (`item_id`,`location_id`),
  KEY `item_id` (`item_id`),
  KEY `location_id` (`location_id`),
  CONSTRAINT `item_quantities_ibfk_1` FOREIGN KEY (`item_id`) REFERENCES `items` (`item_id`),
  CONSTRAINT `item_quantities_ibfk_2` FOREIGN KEY (`location_id`) REFERENCES `stock_locations` (`location_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_quantities`
--

LOCK TABLES `item_quantities` WRITE;
/*!40000 ALTER TABLE `item_quantities` DISABLE KEYS */;
/*!40000 ALTER TABLE `item_quantities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `items`
--

DROP TABLE IF EXISTS `items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `items` (
  `name` varchar(255) NOT NULL,
  `category` varchar(255) NOT NULL,
  `supplier_id` int DEFAULT NULL,
  `barcode` varchar(255) DEFAULT NULL,
  `description` varchar(255) NOT NULL,
  `cost_price` decimal(15,2) NOT NULL,
  `unit_price` decimal(15,2) NOT NULL,
  `reorder_level` decimal(15,3) NOT NULL DEFAULT '0.000',
  `receiving_quantity` decimal(15,3) NOT NULL DEFAULT '1.000',
  `item_id` int NOT NULL AUTO_INCREMENT,
  `pic_filename` varchar(255) DEFAULT NULL,
  `stock_type` tinyint(1) NOT NULL DEFAULT '0',
  `item_type` tinyint(1) NOT NULL DEFAULT '0',
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `qty_per_pack` decimal(15,3) NOT NULL DEFAULT '1.000',
  `pack_name` varchar(8) DEFAULT 'Each',
  PRIMARY KEY (`item_id`),
  UNIQUE KEY `items_uq1` (`supplier_id`,`item_id`,`deleted`,`item_type`),
  KEY ` barcode` (`barcode`),
  KEY `supplier_id` (`supplier_id`),
  KEY `deleted` (`deleted`,`item_type`),
  CONSTRAINT `items_ibfk_1` FOREIGN KEY (`supplier_id`) REFERENCES `suppliers` (`person_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `items`
--

LOCK TABLES `items` WRITE;
/*!40000 ALTER TABLE `items` DISABLE KEYS */;
/*!40000 ALTER TABLE `items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `items_taxes`
--

DROP TABLE IF EXISTS `items_taxes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `items_taxes` (
  `item_id` int NOT NULL,
  `name` varchar(255) NOT NULL,
  `percent` decimal(15,3) NOT NULL,
  PRIMARY KEY (`item_id`,`name`,`percent`),
  CONSTRAINT `items_taxes_ibfk_1` FOREIGN KEY (`item_id`) REFERENCES `items` (`item_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `items_taxes`
--

LOCK TABLES `items_taxes` WRITE;
/*!40000 ALTER TABLE `items_taxes` DISABLE KEYS */;
/*!40000 ALTER TABLE `items_taxes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `modules`
--

DROP TABLE IF EXISTS `modules`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `modules` (
  `name_lang_key` varchar(255) NOT NULL,
  `desc_lang_key` varchar(255) NOT NULL,
  `sort` int NOT NULL,
  `module_id` varchar(255) NOT NULL,
  PRIMARY KEY (`module_id`),
  UNIQUE KEY `desc_lang_key` (`desc_lang_key`),
  UNIQUE KEY `name_lang_key` (`name_lang_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `modules`
--

LOCK TABLES `modules` WRITE;
/*!40000 ALTER TABLE `modules` DISABLE KEYS */;
INSERT INTO `modules` VALUES ('module_attributes','module_attributes_desc',107,'attributes'),('module_cashups','module_cashups_desc',110,'cashups'),('module_config','module_config_desc',900,'config'),('module_customers','module_customers_desc',10,'customers'),('module_expenses','module_expenses_desc',108,'expenses'),('module_expenses_categories','module_expenses_categories_desc',109,'expenses_categories'),('module_giftcards','module_giftcards_desc',90,'giftcards'),('module_home','module_home_desc',1,'home'),('module_items','module_items_desc',20,'items'),('module_item_kits','module_item_kits_desc',30,'item_kits'),('module_messages','module_messages_desc',98,'messages'),('module_office','module_office_desc',999,'office'),('module_receivings','module_receivings_desc',60,'receivings'),('module_reports','module_reports_desc',50,'reports'),('module_sales','module_sales_desc',70,'sales'),('module_suppliers','module_suppliers_desc',40,'suppliers'),('module_taxes','module_taxes_desc',105,'taxes'),('module_users','module_users_desc',80,'users');
/*!40000 ALTER TABLE `modules` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `people`
--

DROP TABLE IF EXISTS `people`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `people` (
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `gender` int DEFAULT NULL,
  `phone_number` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `ward` varchar(255) NOT NULL,
  `district` varchar(255) NOT NULL,
  `province` varchar(255) NOT NULL,
  `country` varchar(255) NOT NULL,
  `comments` text NOT NULL,
  `person_id` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`person_id`),
  KEY `email` (`email`),
  KEY `first_name` (`first_name`,`last_name`,`email`,`phone_number`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `people`
--

LOCK TABLES `people` WRITE;
/*!40000 ALTER TABLE `people` DISABLE KEYS */;
INSERT INTO `people` VALUES ('John','Doe',1,'555-555-5555','changeme@example.com','Address 1','Bach khoa','Hai Ba Trung','Ha Noi','Viet Nam','',1);
/*!40000 ALTER TABLE `people` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permissions`
--

DROP TABLE IF EXISTS `permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permissions` (
  `permission_id` varchar(255) NOT NULL,
  `module_id` varchar(255) NOT NULL,
  `location_id` int DEFAULT NULL,
  PRIMARY KEY (`permission_id`),
  KEY `module_id` (`module_id`),
  KEY `permissions_ibfk_2` (`location_id`),
  CONSTRAINT `permissions_ibfk_1` FOREIGN KEY (`module_id`) REFERENCES `modules` (`module_id`) ON DELETE CASCADE,
  CONSTRAINT `permissions_ibfk_2` FOREIGN KEY (`location_id`) REFERENCES `stock_locations` (`location_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permissions`
--

LOCK TABLES `permissions` WRITE;
/*!40000 ALTER TABLE `permissions` DISABLE KEYS */;
INSERT INTO `permissions` VALUES ('attributes','attributes',NULL),('cashups','cashups',NULL),('config','config',NULL),('customers','customers',NULL),('expenses','expenses',NULL),('expenses_categories','expenses_categories',NULL),('giftcards','giftcards',NULL),('home','home',NULL),('items','items',NULL),('items_stock','items',1),('item_kits','item_kits',NULL),('messages','messages',NULL),('office','office',NULL),('receivings','receivings',NULL),('receivings_stock','receivings',1),('reports','reports',NULL),('reports_categories','reports',NULL),('reports_customers','reports',NULL),('reports_discounts','reports',NULL),('reports_expenses_categories','reports',NULL),('reports_inventory','reports',NULL),('reports_items','reports',NULL),('reports_payments','reports',NULL),('reports_receivings','reports',NULL),('reports_sales','reports',NULL),('reports_sales_taxes','reports',NULL),('reports_suppliers','reports',NULL),('reports_taxes','reports',NULL),('reports_users','reports',NULL),('sales','sales',NULL),('sales_change_price','sales',NULL),('sales_delete','sales',NULL),('sales_stock','sales',1),('suppliers','suppliers',NULL),('taxes','taxes',NULL),('users','users',NULL);
/*!40000 ALTER TABLE `permissions` ENABLE KEYS */;
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
  `reference` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`receiving_id`),
  KEY `supplier_id` (`supplier_id`),
  KEY `user` (`user`),
  KEY `reference` (`reference`),
  KEY `receiving_time` (`receiving_time`),
  CONSTRAINT `receivings_ibfk_1` FOREIGN KEY (`user`) REFERENCES `users` (`username`),
  CONSTRAINT `receivings_ibfk_2` FOREIGN KEY (`supplier_id`) REFERENCES `suppliers` (`person_id`)
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
INSERT INTO `roles` VALUES (1,'admin'),(2,'boss user'),(3,'user');
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
  `customer_id` int DEFAULT NULL,
  `user` varchar(255) NOT NULL DEFAULT '',
  `comment` text,
  `invoice_number` varchar(32) DEFAULT NULL,
  `quote_number` varchar(32) DEFAULT NULL,
  `sale_id` int NOT NULL AUTO_INCREMENT,
  `sale_status` tinyint(1) NOT NULL DEFAULT '0',
  `dinner_table_id` int DEFAULT NULL,
  `sale_type` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`sale_id`),
  UNIQUE KEY `invoice_number` (`invoice_number`),
  KEY `customer_id` (`customer_id`),
  KEY `user` (`user`),
  KEY `sale_time` (`sale_time`),
  KEY `dinner_table_id` (`dinner_table_id`),
  CONSTRAINT `sales_ibfk_1` FOREIGN KEY (`user`) REFERENCES `users` (`username`),
  CONSTRAINT `sales_ibfk_2` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`person_id`),
  CONSTRAINT `sales_ibfk_3` FOREIGN KEY (`dinner_table_id`) REFERENCES `dinner_tables` (`dinner_table_id`)
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
  `print_option` tinyint(1) NOT NULL DEFAULT '0',
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
-- Table structure for table `sales_payments`
--

DROP TABLE IF EXISTS `sales_payments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sales_payments` (
  `payment_id` int NOT NULL AUTO_INCREMENT,
  `sale_id` int NOT NULL,
  `payment_type` varchar(40) NOT NULL,
  `payment_amount` decimal(15,2) NOT NULL,
  `cash_refund` decimal(15,2) NOT NULL DEFAULT '0.00',
  `cash_adjustment` tinyint NOT NULL DEFAULT '0',
  `user_id` int DEFAULT NULL,
  `payment_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `reference_code` varchar(40) NOT NULL DEFAULT '',
  PRIMARY KEY (`payment_id`),
  KEY `payment_sale` (`sale_id`,`payment_type`),
  KEY `user_id` (`user_id`),
  KEY `payment_time` (`payment_time`),
  CONSTRAINT `sales_payments_ibfk_1` FOREIGN KEY (`sale_id`) REFERENCES `sales` (`sale_id`),
  CONSTRAINT `sales_payments_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`person_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sales_payments`
--

LOCK TABLES `sales_payments` WRITE;
/*!40000 ALTER TABLE `sales_payments` DISABLE KEYS */;
/*!40000 ALTER TABLE `sales_payments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sessions`
--

DROP TABLE IF EXISTS `sessions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sessions` (
  `id` varchar(40) NOT NULL,
  `ip_address` varchar(45) NOT NULL,
  `user` varchar(255) NOT NULL,
  `timestamp` int unsigned NOT NULL DEFAULT '0',
  `data` mediumblob NOT NULL,
  KEY `ci_sessions_timestamp` (`timestamp`),
  KEY `id` (`id`),
  KEY `ip_address` (`ip_address`),
  KEY `sessions_ibfk_1` (`user`),
  CONSTRAINT `sessions_ibfk_1` FOREIGN KEY (`user`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sessions`
--

LOCK TABLES `sessions` WRITE;
/*!40000 ALTER TABLE `sessions` DISABLE KEYS */;
/*!40000 ALTER TABLE `sessions` ENABLE KEYS */;
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
  `person_id` int NOT NULL,
  `company_name` varchar(255) NOT NULL,
  `agency_name` varchar(255) NOT NULL,
  `account_number` varchar(255) DEFAULT NULL,
  `tax_id` varchar(32) NOT NULL DEFAULT '',
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `category` tinyint(1) NOT NULL,
  PRIMARY KEY (`person_id`),
  UNIQUE KEY `account_number` (`account_number`),
  KEY `person_id` (`person_id`),
  KEY `category` (`category`),
  KEY `company_name` (`company_name`,`deleted`),
  CONSTRAINT `suppliers_ibfk_1` FOREIGN KEY (`person_id`) REFERENCES `people` (`person_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `suppliers`
--

LOCK TABLES `suppliers` WRITE;
/*!40000 ALTER TABLE `suppliers` DISABLE KEYS */;
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
  `person_id` int NOT NULL,
  `role` int NOT NULL,
  `managed_by` varchar(255) NOT NULL DEFAULT '',
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `hash_version` tinyint(1) NOT NULL DEFAULT '2',
  `language` varchar(48) DEFAULT NULL,
  `language_code` varchar(8) DEFAULT NULL,
  PRIMARY KEY (`username`),
  KEY `users_ibfk_1` (`person_id`),
  KEY `users_ibfk_2` (`role`),
  KEY `users_ibfk_3` (`managed_by`),
  CONSTRAINT `users_ibfk_1` FOREIGN KEY (`person_id`) REFERENCES `people` (`person_id`),
  CONSTRAINT `users_ibfk_2` FOREIGN KEY (`role`) REFERENCES `roles` (`role_id`),
  CONSTRAINT `users_ibfk_3` FOREIGN KEY (`managed_by`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('admin','$2y$10$vJBSMlD02EC7ENSrKfVQXuvq9tNRHMtcOA8MSK2NYS748HHWm.gcG',1,1,'admin',0,2,NULL,NULL);
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

-- Dump completed on 2025-02-27 13:57:28
