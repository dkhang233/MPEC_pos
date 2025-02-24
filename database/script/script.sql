CREATE DATABASE  IF NOT EXISTS `mpec_pos` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `mpec_pos`;
-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: localhost    Database: mpec_pos
-- ------------------------------------
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

--
-- Bảng này lưu trữ cài đặt của người dùng
--
DROP TABLE IF EXISTS `app_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `app_config` (
  `key` varchar(50) NOT NULL,
  `value` varchar(500) NOT NULL,
  `user` varchar(255) NOT NULL,
  PRIMARY KEY (`key`),
  FOREIGN KEY (`user`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_config`
--

LOCK TABLES `app_config` WRITE;
/*!40000 ALTER TABLE `app_config` DISABLE KEYS */;
INSERT INTO `app_config` 
VALUES ('address','123 Nowhere street'), -- Địa chỉ cửa hàng 

-- Setting liên quan đến barcode --
('allow_duplicate_barcodes','0'), -- Cho phép trùng lặp barcode (nhiều sản phẩm có cùng barcode) 
('barcode_content','id'), -- Nội dung barcode (liên quan đến chức năng tạo barcode của ứng dụng): nếu là 'id' thì generate barcode theo id, nếu là 'number' thì generate dựa trên barcode đã có của item đó
('barcode_first_row','category'), -- Dòng đầu tiên của barcode
('barcode_font','Arial'), -- Font chữ barcode
('barcode_font_size','10'), -- Size font chữ barcode
('barcode_formats','[]'), -- Định dạng barcode
('barcode_generate_if_empty','0'), -- Tạo barcode nếu trống
('barcode_height','50'), -- Chiều cao barcode
('barcode_num_in_row','2'), -- Số lượng barcode trên 1 hàng
('barcode_page_cellspacing','20'), -- Khoảng cách giữa các barcode (tính theo px)
('barcode_page_width','100'), -- Chiều rộng trang barcode (tính theo %)
('barcode_second_row','item_code'), -- Dòng thứ 2 của barcode
('barcode_third_row','unit_price'), -- Dòng thứ 3 của barcode
('barcode_type','Code39'), -- Loại barcode
('barcode_width','250'), -- Chiều rộng barcode

-- Setting liên quan đến của hàng --
('cash_decimals','2'), -- Số lượng số thập phân của tiền mặt
('cash_rounding_code','1'), -- Làm tròn tiền mặt: 1 -> Half up(round half up), 2 -> Half down(round half down), 3 -> Half even(round half even), 4 -> Half odd(round half odd), 5 -> Round up, 6 -> Round down, 7 -> Half five
('category_dropdown','0'), -- Tùy chỉnh cách hiển thị category cho các mặt hàng 
('company','Open Source Point of Sale'), -- Tên cửa hàng
('email','changeme@example.com'), -- Email cửa hàng
('fax',''), -- Fax của cửa hàng
('website',''), -- Website của cửa hàng 
('phone','555-555-5555'), -- Sdt của cửa hàng 
('return_policy','Test'), -- Chính sách trả hàng

-- Setting chung --
('gcaptcha_enable','0'), -- Có bật chức năng captcha hay không
('gcaptcha_secret_key',''), -- Key bí mật để dùng captcha
('gcaptcha_site_key',''), -- Nơi cung cấp captcha
('image_allowed_types','jpg|gif|png'), -- Định dạng ảnh cho phép upload: 
('image_max_height','480'),
('image_max_size','128'),
('image_max_width','640'),
('include_hsn','0'), -- Có hỗ trợ mã HSN(Harmonized System of Nomenclature) không
('giftcard_number','series'), -- Cách tạo giftcard_number: series -> 1,2,3,... , random -> EBEDJ-32,...
('enforce_privacy',''), -- Có bật chế độ ghi đè dữ liệu của khách hàng khi cần xóa dữ liệu đó hay không (thường thì dữ liệu sẽ không bị xóa mà có thể sẽ có thêm cột deleted để ẩn đi, nếu cần đảm bảo tính bảo mật cho thông tin của khách hàng thì có thể thay bằng những dự liệu giả)
('default_receivings_discount','0'), -- Giảm giá mặc định khi nhập hàng
('default_receivings_discount_type','0'), -- Loại giảm giá mặc định khi nhập hàng: 0 -> theo %, 1 -> theo số tiền
('default_register_mode','sale'), -- Chế độ mặc định khi sử dụng chức năng Sales, có các chế đô: sale receipt (khách hàng đã thanh toán khi mua hàng), quote(báo giá cho khách hàng), invoice(khách hàng chưa thanh toán khi mua hàng), return (khách hàng trả hàng). Hiện tại chưa thấy ứng dụng có chỗ chỉnh cái này
('default_sales_discount','0'), -- Giảm giá mặc định khi bán hàng
('default_sales_discount_type','0'), -- Loại giảm giá mặc định khi bán hàng: 0 -> theo %, 1 -> theo số tiền
('derive_sale_quantity','0'), -- Số lượng bán
('theme','flatly'), -- Theme --
('lines_per_page','25'),
('line_sequence','0'),
('login_form','floating_labels'),
('multi_pack_enabled','0'),
('notify_horizontal_position','center'),
('notify_vertical_position','bottom'),
('suggestions_first_column','name'),
('suggestions_second_column',''),
('suggestions_third_column',''),
('receiving_calculate_average_price','0'),


-- Setting liên quan đến internalization --
('company_logo',''), -- Logo cửa hàng
('country_codes','vn'), -- Mã quốc gia
('currency_code','VND'), -- Mã tiền tệ
('language','english'),
('language_code','en-US'),
('currency_decimals','0'), -- Số lượng số thập phân của tiền tệ
('currency_symbol','₫'), -- Ký hiệu tiền tệ
('dateformat','m/d/Y'), -- Định dạng ngày
('date_or_time_format',''), -- Định dạng ngày hoặc thời gian
('financial_year','1'), -- Ngày bắt đầu của năm tài chính: 1 -> 1/1, 2 -> 1/2, ..., 12 -> 1/12
('thousands_separator','1'),
('timeformat','H:i:s'),
('timezone','America/New_York'),
('tax_decimals','2'),
('number_locale','vn_VN'),
('payment_options_order','cashdebitcredit'),
('quantity_decimals','0'),


-- Setting liên quan đến customer reward --
('customer_reward_enable','1'), -- Bật chế độ tích điểm thưởng dành cho khách hàng

-- Setting liên quan đến thuế(tax)
('default_tax_1_name',''), -- Tên thuế 1
('default_tax_1_rate',''), -- Thuế 1 (tính theo %)
('default_tax_2_name',''), -- Tên thuế 2
('default_tax_2_rate',''), -- Thuế 2 (tính theo %)
('default_tax_category','Standard'), -- Loại thuế mặc định
('default_tax_code',''), -- Mã thuế mặc định
('default_tax_jurisdiction',''), -- Thuế pháp lý mặc định
('default_tax_rate','8'), -- Thuế mặc định
('tax_id',''),
('tax_included','0'),
('use_destination_based_tax','0'),

-- Setting liên quan đến table --
('dinner_table_enable','1'), -- Có bật chế độ bàn ăn hay không(kiểu dành cho các cửa hàng cafe, hay ăn uống gì đấy): 0 -> off, 1 -> on

-- Setting liên quan đến receipt --
('email_receipt_check_behaviour','last'), -- Mặc định là có gửi email cho khách hàng hay không: last -> lần cuối cùng chọn gì thì giữ nguyên cho lần tiếp theo, always -> luôn luôn là có , never -> luôn luôn là không
('receipt_font_size','12'),
('receipt_show_company_name','1'),
('receipt_show_description','1'),
('receipt_show_serialnumber','1'),
('receipt_show_taxes','0'),
('receipt_show_tax_ind','0'),
('receipt_show_total_discount','1'),
('receipt_template','receipt_default'),
('print_bottom_margin','0'),
('print_delay_autoreturn','0'),
('print_footer','0'),
('print_header','0'),
('print_left_margin','0'),
('print_receipt_check_behaviour','last'),
('print_right_margin','0'),
('print_silently','1'),
('print_top_margin','0'),


-- Setting liên quan đến invoice ---
('invoice_default_comments','This is a default comment'), -- Comment mặc định trong invoice
('invoice_email_message','Dear {CU}, In attachment the receipt for sale {ISEQ}'), 
('invoice_enable','1'), -- Có hỗ trợ invoice hay không
('invoice_type','invoice'),
('last_used_invoice_number','0'),
('last_used_quote_number','0'),
('last_used_work_order_number','0'),
('recv_invoice_format','{CO}'),
('sales_invoice_format','{CO}'),
('sales_quote_format','Q%y{QSEQ:6}'),
('quote_default_comments','This is a default quote comment'),
('work_order_enable','0'),
('work_order_format','W%y{WSEQ:6}');

-- Setting liên quan đến api để gửi email và sms --
('msg_msg',''),
('msg_pwd',''),
('msg_src',''),
('msg_uid',''),
('mailpath','/usr/sbin/sendmail'),
('protocol','mail'),
('smtp_crypto','ssl'),
('smtp_host',''),
('smtp_pass',''),
('smtp_port','465'),
('smtp_timeout','5'),
('smtp_user',''),
/*!40000 ALTER TABLE `app_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attribute_definitions`
--

--
-- Bảng này để lưu các thuộc tính của sản phẩm (ví dụ: màu sắc, kích thước, dung tích...)
--

DROP TABLE IF EXISTS `attribute_definitions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attribute_definitions` (
  `definition_id` int NOT NULL AUTO_INCREMENT,
  `definition_name` varchar(255) NOT NULL, -- Tên thuộc tính
  `definition_type` varchar(45) NOT NULL, -- Loại thuộc tính: GROUP -> nhóm thuộc tính, DECIMAL -> số thập phân, DATE -> ngày tháng, TEXT -> văn bản, DROPDOWN -> danh sách giá trị, CHECKBOX -> ô chọn. Ví dụ: GROUP: đồ uống; DROPDOWN: coca, pepsi, sprite; DECIMAL: 1.5, 2.5, 3.5;...
  `definition_unit` varchar(16) DEFAULT NULL, -- Đơn vị của thuộc tính(nếu có, ví dụ: cm, inch, L ...)
  `definition_flags` tinyint(1) NOT NULL, -- Cờ đánh dấu
  `definition_fk` int DEFAULT NULL, -- Khóa ngoại tham chiếu đến definition_id(nếu có, ví dụ: một DECIMAL attribute: Dung tích có thể thuộc về một GROUP attribute: Đồ uống)
  `deleted` tinyint(1) NOT NULL DEFAULT '0', -- Cờ đánh dấu xóa
  PRIMARY KEY (`definition_id`),
  KEY `definition_fk` (`definition_fk`),
  KEY `definition_name` (`definition_name`),
  KEY `definition_type` (`definition_type`),
  CONSTRAINT `fk_attribute_definitions_ibfk_1` FOREIGN KEY (`definition_fk`) REFERENCES `attribute_definitions` (`definition_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attribute_definitions`
--

LOCK TABLES `attribute_definitions` WRITE;
/*!40000 ALTER TABLE `attribute_definitions` DISABLE KEYS */;
INSERT INTO `attribute_definitions` VALUES (1,'low sell','GROUP',NULL,7,NULL,0);
/*!40000 ALTER TABLE `attribute_definitions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attribute_links`
--

--
-- Bảng này để lưu các liên kết giữa các thuộc tính, giá trị thuộc tính và sản phẩm
--

DROP TABLE IF EXISTS `attribute_links`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attribute_links` (
  `attribute_id` int DEFAULT NULL, -- Khóa ngoại tham chiếu đến giá trị thuộc tính trong bảng attribute_values
  `definition_id` int NOT NULL, -- Khóa ngoại tham chiếu đến thuộc tính trong bảng attribute_definitions
  `item_id` int DEFAULT NULL, -- Khóa ngoại tham chiếu đến sản phẩm trong bảng items
  `sale_id` int DEFAULT NULL, -- Khóa ngoại tham chiếu đến hóa đơn bán hàng trong bảng sales
  `receiving_id` int DEFAULT NULL, -- Khóa ngoại tham chiếu đến hóa đơn nhập hàng trong bảng receivings
  UNIQUE KEY `attribute_links_uq1` (`attribute_id`,`definition_id`,`item_id`,`sale_id`,`receiving_id`),
  UNIQUE KEY `attribute_links_uq2` (`item_id`,`sale_id`,`receiving_id`,`definition_id`,`attribute_id`),
  KEY `attribute_id` (`attribute_id`),
  KEY `definition_id` (`definition_id`),
  KEY `item_id` (`item_id`),
  KEY `sale_id` (`sale_id`),
  KEY `receiving_id` (`receiving_id`),
  CONSTRAINT `attribute_links_ibfk_1` FOREIGN KEY (`definition_id`) REFERENCES `attribute_definitions` (`definition_id`) ON DELETE CASCADE,
  CONSTRAINT `attribute_links_ibfk_2` FOREIGN KEY (`attribute_id`) REFERENCES `attribute_values` (`attribute_id`) ON DELETE CASCADE,
  CONSTRAINT `attribute_links_ibfk_3` FOREIGN KEY (`item_id`) REFERENCES `items` (`item_id`),
  CONSTRAINT `attribute_links_ibfk_4` FOREIGN KEY (`receiving_id`) REFERENCES `receivings` (`receiving_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `attribute_links_ibfk_5` FOREIGN KEY (`sale_id`) REFERENCES `sales` (`sale_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attribute_links`
--

LOCK TABLES `attribute_links` WRITE;
/*!40000 ALTER TABLE `attribute_links` DISABLE KEYS */;
/*!40000 ALTER TABLE `attribute_links` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attribute_values`
--

--
-- Bảng này để lưu các giá trị của thuộc tính
--

DROP TABLE IF EXISTS `attribute_values`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attribute_values` (
  `attribute_id` int NOT NULL AUTO_INCREMENT, 
  `attribute_value` varchar(255) DEFAULT NULL, -- Giá trị thuộc tính dành cho các loại thuộc tính như DROPDOWN, TEXT, CHECKBOX
  `attribute_date` date DEFAULT NULL, -- Giá trị thuộc tính dành cho loại thuộc tính như DATE(ví dụ: 2025-02-02)
  `attribute_decimal` decimal(7,3) DEFAULT NULL, -- Giá trị thuộc tính dành cho loại thuộc tính như DECIMAL(ví dụ: 1,2,3,...)
  PRIMARY KEY (`attribute_id`),
  UNIQUE KEY `attribute_value` (`attribute_value`),
  UNIQUE KEY `attribute_date` (`attribute_date`),
  UNIQUE KEY `attribute_decimal` (`attribute_decimal`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attribute_values`
--

LOCK TABLES `attribute_values` WRITE;
/*!40000 ALTER TABLE `attribute_values` DISABLE KEYS */;
/*!40000 ALTER TABLE `attribute_values` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cash_up`
--

--
-- Bảng này để lưu thông tin về việc đóng mở quỹ
--

DROP TABLE IF EXISTS `cash_up`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cash_up` (
  `cashup_id` int NOT NULL AUTO_INCREMENT,
  `open_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP, -- Thời gian mở quỹ
  `close_date` timestamp NULL DEFAULT NULL, -- Thời gian đóng quỹ
  `open_amount_cash` decimal(15,2) NOT NULL, -- Số tiền khi mở quỹ
  `transfer_amount_cash` decimal(15,2) NOT NULL, -- Số tiền chuyển quỹ
  `note` int NOT NULL, -- Có ghi chú hay không
  `closed_amount_cash` decimal(15,2) NOT NULL, -- Số tiền khi đóng quỹ (tiền mặt)
  `closed_amount_card` decimal(15,2) NOT NULL, -- Số tiền thanh toán bằng thẻ khi đóng quỹ
  `closed_amount_check` decimal(15,2) NOT NULL, -- Số tiền thanh toán bằng check khi đóng quỹ
  `closed_amount_total` decimal(15,2) NOT NULL, -- Tổng số tiền khi đóng quỹ
  `description` varchar(255) NOT NULL, -- Mô tả
  `open_user_id` int NOT NULL, -- Nhân viên mở quỹ
  `close_user_id` int NOT NULL, -- Nhân viên đóng quỹ
  `deleted` tinyint(1) NOT NULL DEFAULT '0', -- Cờ đánh dấu xóa
  `closed_amount_due` decimal(15,2) NOT NULL, -- Số tiền còn nợ khi đóng quỹ
  PRIMARY KEY (`cashup_id`),
  KEY `open_user_id` (`open_user_id`),
  KEY `close_user_id` (`close_user_id`),
  CONSTRAINT `cash_up_ibfk_1` FOREIGN KEY (`open_user_id`) REFERENCES `users` (`person_id`),
  CONSTRAINT `cash_up_ibfk_2` FOREIGN KEY (`close_user_id`) REFERENCES `users` (`person_id`)
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
  `company_name` varchar(255) DEFAULT NULL,
  `account_number` varchar(255) DEFAULT NULL,
  `taxable` tinyint(1) NOT NULL DEFAULT '1',
  `tax_id` varchar(32) NOT NULL DEFAULT '',
  `sales_tax_code_id` int DEFAULT NULL,
  `discount` decimal(15,2) NOT NULL DEFAULT '0.00',
  `discount_type` tinyint(1) NOT NULL DEFAULT '0',
  `package_id` int DEFAULT NULL,
  `points` int DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `user_id` int NOT NULL,
  `consent` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`person_id`),
  UNIQUE KEY `account_number` (`account_number`),
  KEY `person_id` (`person_id`),
  KEY `package_id` (`package_id`),
  KEY `sales_tax_code_id` (`sales_tax_code_id`),
  KEY `company_name` (`company_name`),
  CONSTRAINT `customers_ibfk_1` FOREIGN KEY (`person_id`) REFERENCES `people` (`person_id`),
  CONSTRAINT `customers_ibfk_2` FOREIGN KEY (`package_id`) REFERENCES `customers_packages` (`package_id`),
  CONSTRAINT `customers_ibfk_3` FOREIGN KEY (`sales_tax_code_id`) REFERENCES `tax_codes` (`tax_code_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` VALUES (2,NULL,'123',1,'89898',NULL,0.00,0,2,1400,0,'2025-02-02 17:28:36',1,1);
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customers_packages`
--

DROP TABLE IF EXISTS `customers_packages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customers_packages` (
  `package_id` int NOT NULL AUTO_INCREMENT,
  `package_name` varchar(255) DEFAULT NULL,
  `points_percent` float NOT NULL DEFAULT '0',
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`package_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers_packages`
--

LOCK TABLES `customers_packages` WRITE;
/*!40000 ALTER TABLE `customers_packages` DISABLE KEYS */;
INSERT INTO `customers_packages` VALUES (1,'Default',0,0),(2,'Bronze',10,0),(3,'Silver',20,0),(4,'Gold',30,0),(5,'Premium',50,0);
/*!40000 ALTER TABLE `customers_packages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customers_points`
--

DROP TABLE IF EXISTS `customers_points`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customers_points` (
  `id` int NOT NULL AUTO_INCREMENT,
  `person_id` int NOT NULL,
  `package_id` int NOT NULL,
  `sale_id` int NOT NULL,
  `points_earned` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `person_id` (`person_id`),
  KEY `package_id` (`package_id`),
  KEY `sale_id` (`sale_id`),
  CONSTRAINT `customers_points_ibfk_1` FOREIGN KEY (`person_id`) REFERENCES `customers` (`person_id`),
  CONSTRAINT `customers_points_ibfk_2` FOREIGN KEY (`package_id`) REFERENCES `customers_packages` (`package_id`),
  CONSTRAINT `customers_points_ibfk_3` FOREIGN KEY (`sale_id`) REFERENCES `sales` (`sale_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers_points`
--

LOCK TABLES `customers_points` WRITE;
/*!40000 ALTER TABLE `customers_points` DISABLE KEYS */;
/*!40000 ALTER TABLE `customers_points` ENABLE KEYS */;
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
INSERT INTO `dinner_tables` VALUES (1,'Bàn 1',0,0),(2,'Bàn 2',0,0);
/*!40000 ALTER TABLE `dinner_tables` ENABLE KEYS */;
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
  `managed_by `varchar(255) NOT NULL, 
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `hash_version` tinyint(1) NOT NULL DEFAULT '2',
  `language` varchar(48) DEFAULT NULL,
  `language_code` varchar(8) DEFAULT NULL,
  PRIMARY KEY (`username`),
  CONSTRAINT `users_ibfk_1` FOREIGN KEY (`person_id`) REFERENCES `people` (`person_id`),
  CONSTRAINT `users_ibfk_2` FOREIGN KEY (`managed_by`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('admin','$2y$10$vJBSMlD02EC7ENSrKfVQXuvq9tNRHMtcOA8MSK2NYS748HHWm.gcG',1,'admin',0,2,NULL,NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `expense_categories`
--

DROP TABLE IF EXISTS `expense_categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `expense_categories` (
  `expense_category_id` int NOT NULL AUTO_INCREMENT,
  `category_name` varchar(255) DEFAULT NULL,
  `category_description` varchar(255) NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`expense_category_id`),
  UNIQUE KEY `category_name` (`category_name`),
  KEY `category_description` (`category_description`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `expense_categories`
--

LOCK TABLES `expense_categories` WRITE;
/*!40000 ALTER TABLE `expense_categories` DISABLE KEYS */;
/*!40000 ALTER TABLE `expense_categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `expenses`
--

DROP TABLE IF EXISTS `expenses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `expenses` (
  `expense_id` int NOT NULL AUTO_INCREMENT,
  `date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `amount` decimal(15,2) NOT NULL,
  `payment_type` varchar(40) NOT NULL,
  `expense_category_id` int NOT NULL,
  `description` varchar(255) NOT NULL,
  `user_id` int NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `supplier_tax_code` varchar(255) DEFAULT NULL,
  `tax_amount` decimal(15,2) DEFAULT NULL,
  `supplier_id` int DEFAULT NULL,
  PRIMARY KEY (`expense_id`),
  KEY `expense_category_id` (`expense_category_id`),
  KEY `user_id` (`user_id`),
  KEY `expenses_ibfk_3` (`supplier_id`),
  KEY `date` (`date`),
  KEY `payment_type` (`payment_type`),
  KEY `amount` (`amount`),
  CONSTRAINT `expenses_ibfk_1` FOREIGN KEY (`expense_category_id`) REFERENCES `expense_categories` (`expense_category_id`),
  CONSTRAINT `expenses_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`person_id`),
  CONSTRAINT `expenses_ibfk_3` FOREIGN KEY (`supplier_id`) REFERENCES `suppliers` (`person_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `expenses`
--

LOCK TABLES `expenses` WRITE;
/*!40000 ALTER TABLE `expenses` DISABLE KEYS */;
/*!40000 ALTER TABLE `expenses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `giftcards`
--

DROP TABLE IF EXISTS `giftcards`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `giftcards` (
  `record_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `giftcard_id` int NOT NULL AUTO_INCREMENT,
  `giftcard_number` varchar(255) DEFAULT NULL,
  `value` decimal(15,2) NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `person_id` int DEFAULT NULL,
  PRIMARY KEY (`giftcard_id`),
  UNIQUE KEY `giftcard_number` (`giftcard_number`),
  KEY `person_id` (`person_id`),
  CONSTRAINT `giftcards_ibfk_1` FOREIGN KEY (`person_id`) REFERENCES `people` (`person_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `giftcards`
--

LOCK TABLES `giftcards` WRITE;
/*!40000 ALTER TABLE `giftcards` DISABLE KEYS */;
INSERT INTO `giftcards` VALUES ('2025-02-02 17:50:58',1,'1',5.00,0,2);
/*!40000 ALTER TABLE `giftcards` ENABLE KEYS */;
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
  KEY `grants_ibfk_2` (`person_id`),
  CONSTRAINT `grants_ibfk_1` FOREIGN KEY (`permission_id`) REFERENCES `permissions` (`permission_id`) ON DELETE CASCADE,
  CONSTRAINT `grants_ibfk_2` FOREIGN KEY (`user`) REFERENCES `users` (`username`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grants`
--

LOCK TABLES `grants` WRITE;
/*!40000 ALTER TABLE `grants` DISABLE KEYS */;
INSERT INTO `grants` VALUES 
    ('attributes', 'admin', 'office'),
    ('cashups', 'admin', 'home'),
    ('config', 'admin', 'office'),
    ('customers', 'admin', 'home'),
    ('users', 'admin', 'office'),
    ('expenses', 'admin', 'home'),
    ('expenses_categories', 'admin', 'office'),
    ('giftcards', 'admin', 'home'),
    ('home', 'admin', 'office'),
    ('items', 'admin', 'home'),
    ('items_stock', 'admin', 'home'),
    ('item_kits', 'admin', 'home'),
    ('messages', 'admin', 'home'),
    ('office', 'admin', 'home'),
    ('receivings', 'admin', 'home'),
    ('receivings_stock', 'admin', 'home'),
    ('reports', 'admin', 'home'),
    ('reports_categories', 'admin', 'home'),
    ('reports_customers', 'admin', 'home'),
    ('reports_discounts', 'admin', 'home'),
    ('reports_users', 'admin', 'home'),
    ('reports_expenses_categories', 'admin', 'home'),
    ('reports_inventory', 'admin', 'home'),
    ('reports_items', 'admin', 'home'),
    ('reports_payments', 'admin', 'home'),
    ('reports_receivings', 'admin', 'home'),
    ('reports_sales', 'admin', 'home'),
    ('reports_sales_taxes', 'admin', 'home'),
    ('reports_suppliers', 'admin', 'home'),
    ('reports_taxes', 'admin', 'home'),
    ('sales', 'admin', 'home'),
    ('sales_change_price', 'admin', '--'),
    ('sales_delete', 'admin', '--'),
    ('sales_stock', 'admin', 'home'),
    ('suppliers', 'admin', 'home'),
    ('taxes', 'admin', 'office');
/*!40000 ALTER TABLE `grants` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inventory`
--

DROP TABLE IF EXISTS `inventory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inventory` (
  `trans_id` int NOT NULL AUTO_INCREMENT,
  `trans_items` int NOT NULL DEFAULT '0',
  `trans_user` VARCHAR(255) NOT NULL DEFAULT '',
  `trans_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `trans_comment` text NOT NULL,
  `trans_location` int NOT NULL,
  `trans_inventory` decimal(15,3) NOT NULL DEFAULT '0.000',
  PRIMARY KEY (`trans_id`),
  KEY `trans_items` (`trans_items`),
  KEY `trans_user` (`trans_user`),
  KEY `trans_location` (`trans_location`),
  KEY `trans_date` (`trans_date`),
  CONSTRAINT `inventory_ibfk_1` FOREIGN KEY (`trans_items`) REFERENCES `items` (`item_id`),
  CONSTRAINT `inventory_ibfk_2` FOREIGN KEY (`trans_user`) REFERENCES `users` (`username`),
  CONSTRAINT `inventory_ibfk_3` FOREIGN KEY (`trans_location`) REFERENCES `stock_locations` (`location_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventory`
--

LOCK TABLES `inventory` WRITE;
/*!40000 ALTER TABLE `inventory` DISABLE KEYS */;
INSERT INTO `inventory` VALUES (1,1,'admin','2025-01-26 03:53:58','Manual Edit of Quantity',1,5);
/*!40000 ALTER TABLE `inventory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item_kit_items`
--

DROP TABLE IF EXISTS `item_kit_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item_kit_items` (
  `item_kit_id` int NOT NULL,
  `item_id` int NOT NULL,
  `quantity` decimal(15,3) NOT NULL,
  `kit_sequence` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`item_kit_id`,`item_id`,`quantity`),
  KEY `item_kit_items_ibfk_2` (`item_id`),
  CONSTRAINT `item_kit_items_ibfk_1` FOREIGN KEY (`item_kit_id`) REFERENCES `item_kits` (`item_kit_id`) ON DELETE CASCADE,
  CONSTRAINT `item_kit_items_ibfk_2` FOREIGN KEY (`item_id`) REFERENCES `items` (`item_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_kit_items`
--

LOCK TABLES `item_kit_items` WRITE;
/*!40000 ALTER TABLE `item_kit_items` DISABLE KEYS */;
/*!40000 ALTER TABLE `item_kit_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item_kits`
--

DROP TABLE IF EXISTS `item_kits`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item_kits` (
  `item_kit_id` int NOT NULL AUTO_INCREMENT,
  `item_kit_number` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `item_id` int NOT NULL DEFAULT '0',
  `kit_discount` decimal(15,2) NOT NULL DEFAULT '0.00',
  `kit_discount_type` tinyint(1) NOT NULL DEFAULT '0',
  `price_option` tinyint(1) NOT NULL DEFAULT '0',
  `print_option` tinyint(1) NOT NULL DEFAULT '0',
  `description` varchar(255) NOT NULL,
  PRIMARY KEY (`item_kit_id`),
  KEY `item_kit_number` (`item_kit_number`),
  KEY `name` (`name`,`description`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_kits`
--

LOCK TABLES `item_kits` WRITE;
/*!40000 ALTER TABLE `item_kits` DISABLE KEYS */;
/*!40000 ALTER TABLE `item_kits` ENABLE KEYS */;
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
INSERT INTO `item_quantities` VALUES (1,1,1.000),(2,1,1.000),(3,1,0.000),(4,1,0.000),(5,1,1.000);
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
  `item_number` varchar(255) DEFAULT NULL,
  `description` varchar(255) NOT NULL,
  `cost_price` decimal(15,2) NOT NULL,
  `unit_price` decimal(15,2) NOT NULL,
  `reorder_level` decimal(15,3) NOT NULL DEFAULT '0.000',
  `receiving_quantity` decimal(15,3) NOT NULL DEFAULT '1.000',
  `item_id` int NOT NULL AUTO_INCREMENT,
  `pic_filename` varchar(255) DEFAULT NULL,
  `allow_alt_description` tinyint(1) NOT NULL,
  `is_serialized` tinyint(1) NOT NULL,
  `stock_type` tinyint(1) NOT NULL DEFAULT '0',
  `item_type` tinyint(1) NOT NULL DEFAULT '0',
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `tax_category_id` int DEFAULT NULL,
  `qty_per_pack` decimal(15,3) NOT NULL DEFAULT '1.000',
  `pack_name` varchar(8) DEFAULT 'Each',
  `low_sell_item_id` int DEFAULT '0',
  `hsn_code` varchar(32) NOT NULL DEFAULT '',
  PRIMARY KEY (`item_id`),
  UNIQUE KEY `items_uq1` (`supplier_id`,`item_id`,`deleted`,`item_type`),
  KEY `item_number` (`item_number`),
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
INSERT INTO `items` VALUES ('Bimbim khoai tây','Đồ ăn vặt',NULL,'12345','',5000.00,4000.00,0.000,5.000,1,'Screenshot_2025-01-26_225710.jpg',0,0,0,0,0,0,1.000,'Each',1,''),('Bimbim Cay','Bimbim',NULL,NULL,'',5000.00,4800.00,0.000,5.000,2,NULL,0,0,0,0,0,0,1.000,'Each',2,''),('snack tôm','snack',NULL,NULL,'',1.00,1.00,1.000,5.000,3,NULL,0,0,0,0,1,0,1.000,'Each',3,''),('Tivi Samsung','Đồ điện tử',NULL,'8923123','',5000.00,5000.00,2.000,2.000,4,NULL,0,0,0,0,0,0,1.000,'Each',4,''),('Coca cola lon 10ml','Đồ uống',NULL,'89130232','',10000.00,10000.00,1.000,1.000,5,NULL,0,0,0,0,0,0,1.000,'Each',5,'');
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
INSERT INTO `items_taxes` VALUES (4,'',3.000);
/*!40000 ALTER TABLE `items_taxes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `migrations`
--

DROP TABLE IF EXISTS `migrations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `migrations` (
  `version` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `migrations`
--

LOCK TABLES `migrations` WRITE;
/*!40000 ALTER TABLE `migrations` DISABLE KEYS */;
INSERT INTO `migrations` VALUES (20210714140000);
/*!40000 ALTER TABLE `migrations` ENABLE KEYS */;
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
INSERT INTO `modules` VALUES ('module_attributes','module_attributes_desc',107,'attributes'),('module_cashups','module_cashups_desc',110,'cashups'),('module_config','module_config_desc',900,'config'),('module_customers','module_customers_desc',10,'customers'),('module_users','module_users_desc',80,'users'),('module_expenses','module_expenses_desc',108,'expenses'),('module_expenses_categories','module_expenses_categories_desc',109,'expenses_categories'),('module_giftcards','module_giftcards_desc',90,'giftcards'),('module_home','module_home_desc',1,'home'),('module_items','module_items_desc',20,'items'),('module_item_kits','module_item_kits_desc',30,'item_kits'),('module_messages','module_messages_desc',98,'messages'),('module_office','module_office_desc',999,'office'),('module_receivings','module_receivings_desc',60,'receivings'),('module_reports','module_reports_desc',50,'reports'),('module_sales','module_sales_desc',70,'sales'),('module_suppliers','module_suppliers_desc',40,'suppliers'),('module_taxes','module_taxes_desc',105,'taxes');
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
  `address_1` varchar(255) NOT NULL,
  `address_2` varchar(255) NOT NULL,
  `city` varchar(255) NOT NULL,
  `state` varchar(255) NOT NULL,
  `zip` varchar(255) NOT NULL,
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
INSERT INTO `people` VALUES ('John','Doe',NULL,'555-555-5555','changeme@example.com','Address 1','','','','','','',1),('Khang','Danh',1,'012345678','dkhang@gmail.com','HaNoi','','HaNoi','Abc','333','VietNam','',2);
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
INSERT INTO `permissions` VALUES ('attributes','attributes',NULL),('cashups','cashups',NULL),('config','config',NULL),('customers','customers',NULL),('users','users',NULL),('expenses','expenses',NULL),('expenses_categories','expenses_categories',NULL),('giftcards','giftcards',NULL),('home','home',NULL),('items','items',NULL),('items_stock','items',1),('item_kits','item_kits',NULL),('messages','messages',NULL),('office','office',NULL),('receivings','receivings',NULL),('receivings_stock','receivings',1),('reports','reports',NULL),('reports_categories','reports',NULL),('reports_customers','reports',NULL),('reports_discounts','reports',NULL),('reports_users','reports',NULL),('reports_expenses_categories','reports',NULL),('reports_inventory','reports',NULL),('reports_items','reports',NULL),('reports_payments','reports',NULL),('reports_receivings','reports',NULL),('reports_sales','reports',NULL),('reports_sales_taxes','reports',NULL),('reports_suppliers','reports',NULL),('reports_taxes','reports',NULL),('sales','sales',NULL),('sales_change_price','sales',NULL),('sales_delete','sales',NULL),('sales_stock','sales',1),('suppliers','suppliers',NULL),('taxes','taxes',NULL);
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
  `user_id` int NOT NULL DEFAULT '0',
  `comment` text,
  `receiving_id` int NOT NULL AUTO_INCREMENT,
  `payment_type` varchar(20) DEFAULT NULL,
  `reference` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`receiving_id`),
  KEY `supplier_id` (`supplier_id`),
  KEY `user_id` (`user_id`),
  KEY `reference` (`reference`),
  KEY `receiving_time` (`receiving_time`),
  CONSTRAINT `receivings_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`person_id`),
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
  CONSTRAINT `receivings_items_ibfk_1` FOREIGN KEY (`item_id`) REFERENCES `items` (`item_id`),
  CONSTRAINT `receivings_items_ibfk_2` FOREIGN KEY (`receiving_id`) REFERENCES `receivings` (`receiving_id`)
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
-- Table structure for table `sales`
--

DROP TABLE IF EXISTS `sales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sales` (
  `sale_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `customer_id` int DEFAULT NULL,
  `user_id` int NOT NULL DEFAULT '0',
  `comment` text,
  `invoice_number` varchar(32) DEFAULT NULL,
  `quote_number` varchar(32) DEFAULT NULL,
  `sale_id` int NOT NULL AUTO_INCREMENT,
  `sale_status` tinyint(1) NOT NULL DEFAULT '0',
  `dinner_table_id` int DEFAULT NULL,
  `work_order_number` varchar(32) DEFAULT NULL,
  `sale_type` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`sale_id`),
  UNIQUE KEY `invoice_number` (`invoice_number`),
  KEY `customer_id` (`customer_id`),
  KEY `user_id` (`user_id`),
  KEY `sale_time` (`sale_time`),
  KEY `dinner_table_id` (`dinner_table_id`),
  CONSTRAINT `sales_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`person_id`),
  CONSTRAINT `sales_ibfk_2` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`person_id`),
  CONSTRAINT `sales_ibfk_3` FOREIGN KEY (`dinner_table_id`) REFERENCES `dinner_tables` (`dinner_table_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sales`
--

LOCK TABLES `sales` WRITE;
/*!40000 ALTER TABLE `sales` DISABLE KEYS */;
INSERT INTO `sales` VALUES ('2025-01-26 04:00:49',NULL,1,'',NULL,NULL,1,0,NULL,NULL,0),('2025-01-31 21:32:04',NULL,1,'',NULL,NULL,2,0,NULL,NULL,0),('2025-01-31 21:33:25',NULL,1,'',NULL,NULL,3,0,NULL,NULL,0),('2025-02-02 17:40:16',2,1,'',NULL,NULL,4,0,NULL,NULL,0),('2025-02-02 17:48:29',2,1,'',NULL,NULL,5,0,NULL,NULL,0),('2025-02-02 17:50:12',2,1,'',NULL,NULL,6,0,NULL,NULL,0),('2025-02-02 19:46:48',NULL,1,'',NULL,NULL,7,0,NULL,NULL,0),('2025-02-08 14:02:14',NULL,1,'',NULL,NULL,8,0,1,NULL,0),('2025-02-08 14:32:07',NULL,1,'',NULL,NULL,9,1,1,NULL,4),('2025-02-08 14:48:56',2,1,'',NULL,NULL,10,0,1,NULL,0),('2025-02-08 14:51:55',2,1,'',NULL,NULL,11,0,1,NULL,0),('2025-02-08 14:55:06',2,1,'',NULL,NULL,12,0,1,NULL,0);
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
INSERT INTO `sales_items` VALUES (1,2,'','',1,1.000,1.00,1.00,0.00,0,1,0),(2,2,'','',1,1.000,1.00,1.00,0.00,0,1,0),(3,1,'','',1,3.000,1.00,1.00,0.00,0,1,0),(4,4,'','',1,1.000,5000.00,5000.00,0.00,0,1,0),(5,4,'','',1,2.000,5000.00,5000.00,50.00,0,1,0),(6,4,'','',1,1.000,5000.00,5000.00,50.00,0,1,0),(7,4,'','',1,1.000,5000.00,5000.00,0.00,0,1,0),(8,2,'','',2,1.000,5000.00,5000.00,0.00,0,1,0),(8,5,'','',1,1.000,10000.00,10000.00,0.00,0,1,0),(9,2,'','',1,-1.000,5000.00,5000.00,0.00,0,1,0),(10,5,'','',1,1.000,10000.00,10000.00,0.00,0,1,0),(11,2,'','',1,1.000,5000.00,4800.00,0.00,0,1,0),(11,5,'','',2,1.000,10000.00,10000.00,0.00,0,1,0),(12,1,'','',1,1.000,5000.00,4000.00,0.00,0,1,0),(12,5,'','',2,1.000,10000.00,10000.00,0.00,0,1,0);
/*!40000 ALTER TABLE `sales_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sales_items_taxes`
--

DROP TABLE IF EXISTS `sales_items_taxes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sales_items_taxes` (
  `sale_id` int NOT NULL,
  `item_id` int NOT NULL,
  `line` int NOT NULL DEFAULT '0',
  `name` varchar(255) NOT NULL,
  `percent` decimal(15,4) NOT NULL DEFAULT '0.0000',
  `tax_type` tinyint(1) NOT NULL DEFAULT '0',
  `rounding_code` tinyint(1) NOT NULL DEFAULT '0',
  `cascade_sequence` tinyint(1) NOT NULL DEFAULT '0',
  `item_tax_amount` decimal(15,4) NOT NULL DEFAULT '0.0000',
  `sales_tax_code_id` int DEFAULT NULL,
  `jurisdiction_id` int DEFAULT NULL,
  `tax_category_id` int DEFAULT NULL,
  PRIMARY KEY (`sale_id`,`item_id`,`line`,`name`,`percent`),
  KEY `sale_id` (`sale_id`),
  KEY `item_id` (`item_id`),
  CONSTRAINT `sales_items_taxes_ibfk_1` FOREIGN KEY (`sale_id`) REFERENCES `sales_items` (`sale_id`),
  CONSTRAINT `sales_items_taxes_ibfk_2` FOREIGN KEY (`item_id`) REFERENCES `items` (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sales_items_taxes`
--

LOCK TABLES `sales_items_taxes` WRITE;
/*!40000 ALTER TABLE `sales_items_taxes` DISABLE KEYS */;
INSERT INTO `sales_items_taxes` VALUES (4,4,1,'',3.0000,1,1,0,150.0000,NULL,NULL,NULL),(5,4,1,'',3.0000,1,1,0,150.0000,NULL,NULL,NULL),(6,4,1,'',3.0000,1,1,0,75.0000,NULL,NULL,NULL),(7,4,1,'',3.0000,1,1,0,150.0000,NULL,NULL,NULL);
/*!40000 ALTER TABLE `sales_items_taxes` ENABLE KEYS */;
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
INSERT INTO `sales_payments` VALUES (2,1,'Cash',1.00,0.00,0,1,'2025-01-26 16:00:49',''),(3,2,'Cash',1.00,0.00,0,1,'2025-02-01 09:32:04',''),(4,3,'Cash',3.00,0.00,0,1,'2025-02-01 09:33:25',''),(5,4,'Cash',5150.00,0.00,0,1,'2025-02-03 05:40:16',''),(6,5,'Cash',5150.00,0.00,0,1,'2025-02-03 05:48:29',''),(7,6,'Cash',2575.00,0.00,0,1,'2025-02-03 05:50:12',''),(8,7,'Cash',5150.00,0.00,0,1,'2025-02-03 07:46:48',''),(9,8,'Cash',15000.00,0.00,0,1,'2025-02-09 02:02:14',''),(10,10,'Cash',10000.00,0.00,0,1,'2025-02-09 02:48:56',''),(11,11,'Cash',14800.00,0.00,0,1,'2025-02-09 02:51:55',''),(12,12,'Cash',14000.00,0.00,0,1,'2025-02-09 02:55:06','');
/*!40000 ALTER TABLE `sales_payments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sales_reward_points`
--

DROP TABLE IF EXISTS `sales_reward_points`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sales_reward_points` (
  `id` int NOT NULL AUTO_INCREMENT,
  `sale_id` int NOT NULL,
  `earned` float NOT NULL,
  `used` float NOT NULL,
  PRIMARY KEY (`id`),
  KEY `sale_id` (`sale_id`),
  CONSTRAINT `sales_reward_points_ibfk_1` FOREIGN KEY (`sale_id`) REFERENCES `sales` (`sale_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sales_reward_points`
--

LOCK TABLES `sales_reward_points` WRITE;
/*!40000 ALTER TABLE `sales_reward_points` DISABLE KEYS */;
INSERT INTO `sales_reward_points` VALUES (1,12,1400,0);
/*!40000 ALTER TABLE `sales_reward_points` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sales_taxes`
--

DROP TABLE IF EXISTS `sales_taxes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sales_taxes` (
  `sales_taxes_id` int NOT NULL AUTO_INCREMENT,
  `sale_id` int NOT NULL,
  `jurisdiction_id` int DEFAULT NULL,
  `tax_category_id` int DEFAULT NULL,
  `tax_type` smallint NOT NULL,
  `tax_group` varchar(32) NOT NULL,
  `sale_tax_basis` decimal(15,4) NOT NULL,
  `sale_tax_amount` decimal(15,4) NOT NULL,
  `print_sequence` tinyint(1) NOT NULL DEFAULT '0',
  `name` varchar(255) NOT NULL,
  `tax_rate` decimal(15,4) NOT NULL,
  `sales_tax_code_id` int DEFAULT NULL,
  `rounding_code` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`sales_taxes_id`),
  KEY `print_sequence` (`sale_id`,`print_sequence`,`tax_group`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sales_taxes`
--

LOCK TABLES `sales_taxes` WRITE;
/*!40000 ALTER TABLE `sales_taxes` DISABLE KEYS */;
INSERT INTO `sales_taxes` VALUES (1,4,NULL,NULL,1,'',5000.0000,150.0000,1,'',3.0000,NULL,1),(2,5,NULL,NULL,1,'',5000.0000,150.0000,1,'',3.0000,NULL,1),(3,6,NULL,NULL,1,'',2500.0000,75.0000,1,'',3.0000,NULL,1),(4,7,NULL,NULL,1,'',5000.0000,150.0000,1,'',3.0000,NULL,1);
/*!40000 ALTER TABLE `sales_taxes` ENABLE KEYS */;
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
  `timestamp` int unsigned NOT NULL DEFAULT '0',
  `data` mediumblob NOT NULL,
  KEY `ci_sessions_timestamp` (`timestamp`),
  KEY `id` (`id`),
  KEY `ip_address` (`ip_address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sessions`
--

LOCK TABLES `sessions` WRITE;
/*!40000 ALTER TABLE `sessions` DISABLE KEYS */;
INSERT INTO `sessions` VALUES ('oarqq99j73fj1fu9vksid5k1m5jrbpfr','::1',1739003413,_binary '__ci_last_regenerate|i:1739003413;person_id|s:1:\"1\";menu_group|s:6:\"office\";allow_temp_items|i:0;item_location|s:1:\"1\";'),('oarqq99j73fj1fu9vksid5k1m5jrbpfr','127.0.0.1',1739002921,_binary '__ci_last_regenerate|i:1739002915;'),('s6kmepnd8d183tf2cgr1a1unff3kum9a','::1',1739006821,_binary '__ci_last_regenerate|i:1739006821;person_id|s:1:\"1\";menu_group|s:6:\"office\";allow_temp_items|i:0;item_location|s:1:\"1\";'),('1v7d8462edo3072sqvj17ftl2572iu2g','::1',1739006831,_binary '__ci_last_regenerate|i:1739006821;person_id|s:1:\"1\";menu_group|s:4:\"home\";allow_temp_items|i:0;item_location|s:1:\"1\";'),('cc5c20f8pt4ku8jhfpa4nh3vbkrk16lg','::1',1739066420,_binary '__ci_last_regenerate|i:1739066420;person_id|s:1:\"1\";menu_group|s:4:\"home\";sale_id|i:-1;allow_temp_items|i:0;cash_rounding|i:0;cash_mode|i:0;sales_cart|a:0:{}sales_customer|i:-1;sales_mode|s:4:\"sale\";dinner_table|i:1;sales_location|s:1:\"1\";sales_payments|a:0:{}item_location|s:1:\"1\";'),('cc5c20f8pt4ku8jhfpa4nh3vbkrk16lg','127.0.0.1',1739066131,_binary '__ci_last_regenerate|i:1739066121;'),('rj72k8ua0d0f76fnma0huhtmm2orsfki','::1',1739068152,_binary '__ci_last_regenerate|i:1739068152;person_id|s:1:\"1\";menu_group|s:4:\"home\";sale_id|i:-1;allow_temp_items|i:1;sales_location|s:1:\"1\";item_location|s:1:\"1\";cash_rounding|i:0;cash_mode|i:0;sales_cart|a:0:{}sales_customer|i:-1;sales_mode|s:4:\"sale\";dinner_table|i:1;sales_payments|a:0:{}'),('i0b40m102aqek3tqpkhhhsf1ps9oe44m','::1',1739068582,_binary '__ci_last_regenerate|i:1739068582;person_id|s:1:\"1\";menu_group|s:4:\"home\";sale_id|i:-1;allow_temp_items|i:1;sales_location|s:1:\"1\";item_location|s:1:\"1\";cash_rounding|i:0;cash_mode|i:0;sales_cart|a:0:{}sales_customer|i:-1;sales_mode|s:4:\"sale\";dinner_table|i:1;sales_payments|a:0:{}'),('e9a3f0gunonefrcvk5f27ql7tt9qosdk','::1',1739068883,_binary '__ci_last_regenerate|i:1739068883;person_id|s:1:\"1\";menu_group|s:4:\"home\";sale_id|i:-1;allow_temp_items|i:1;sales_location|s:1:\"1\";item_location|s:1:\"1\";cash_rounding|i:0;cash_mode|i:0;sales_cart|a:1:{i:1;a:25:{s:7:\"item_id\";s:1:\"1\";s:13:\"item_location\";s:1:\"1\";s:10:\"stock_name\";s:5:\"stock\";s:4:\"line\";i:1;s:4:\"name\";s:17:\"Bimbim khoai tây\";s:11:\"item_number\";s:5:\"12345\";s:16:\"attribute_values\";N;s:18:\"attribute_dtvalues\";N;s:11:\"description\";s:0:\"\";s:12:\"serialnumber\";s:0:\"\";s:21:\"allow_alt_description\";s:1:\"0\";s:13:\"is_serialized\";s:1:\"0\";s:8:\"quantity\";i:1;s:8:\"discount\";s:1:\"0\";s:13:\"discount_type\";s:1:\"0\";s:8:\"in_stock\";s:5:\"2.000\";s:5:\"price\";s:7:\"4000.00\";s:10:\"cost_price\";s:7:\"5000.00\";s:5:\"total\";s:7:\"4000.00\";s:16:\"discounted_total\";s:7:\"4000.00\";s:12:\"print_option\";i:0;s:10:\"stock_type\";s:1:\"0\";s:9:\"item_type\";s:1:\"0\";s:8:\"hsn_code\";s:0:\"\";s:15:\"tax_category_id\";s:1:\"0\";}}sales_customer|i:-1;sales_mode|s:4:\"sale\";dinner_table|i:1;recv_cart|a:0:{}recv_mode|s:7:\"receive\";recv_supplier|i:-1;recv_stock_source|s:1:\"1\";sales_payments|a:0:{}'),('lvg5sajt5dpg8hmuks9g4cmsplu5i6u8','::1',1739069203,_binary '__ci_last_regenerate|i:1739069203;person_id|s:1:\"1\";menu_group|s:6:\"office\";sale_id|i:-1;allow_temp_items|i:1;sales_location|s:1:\"1\";item_location|s:1:\"1\";cash_rounding|i:0;cash_mode|i:0;sales_cart|a:1:{i:1;a:25:{s:7:\"item_id\";s:1:\"1\";s:13:\"item_location\";s:1:\"1\";s:10:\"stock_name\";s:5:\"stock\";s:4:\"line\";i:1;s:4:\"name\";s:17:\"Bimbim khoai tây\";s:11:\"item_number\";s:5:\"12345\";s:16:\"attribute_values\";N;s:18:\"attribute_dtvalues\";N;s:11:\"description\";s:0:\"\";s:12:\"serialnumber\";s:0:\"\";s:21:\"allow_alt_description\";s:1:\"0\";s:13:\"is_serialized\";s:1:\"0\";s:8:\"quantity\";i:1;s:8:\"discount\";s:1:\"0\";s:13:\"discount_type\";s:1:\"0\";s:8:\"in_stock\";s:5:\"2.000\";s:5:\"price\";s:7:\"4000.00\";s:10:\"cost_price\";s:7:\"5000.00\";s:5:\"total\";s:7:\"4000.00\";s:16:\"discounted_total\";s:7:\"4000.00\";s:12:\"print_option\";i:0;s:10:\"stock_type\";s:1:\"0\";s:9:\"item_type\";s:1:\"0\";s:8:\"hsn_code\";s:0:\"\";s:15:\"tax_category_id\";s:1:\"0\";}}sales_customer|i:-1;sales_mode|s:4:\"sale\";dinner_table|i:1;recv_cart|a:0:{}recv_mode|s:7:\"receive\";recv_supplier|i:-1;recv_stock_source|s:1:\"1\";sales_payments|a:0:{}'),('mf7bdshtosatftdviomurp6tsl3rf9ru','::1',1739069505,_binary '__ci_last_regenerate|i:1739069505;person_id|s:1:\"1\";menu_group|s:4:\"home\";sale_id|i:-1;allow_temp_items|i:1;sales_location|s:1:\"1\";item_location|s:1:\"1\";recv_cart|a:0:{}recv_mode|s:7:\"receive\";recv_supplier|i:-1;recv_stock_source|s:1:\"1\";cash_rounding|i:0;cash_mode|i:0;sales_cart|a:2:{i:1;a:25:{s:7:\"item_id\";s:1:\"2\";s:13:\"item_location\";s:1:\"1\";s:10:\"stock_name\";s:5:\"stock\";s:4:\"line\";i:1;s:4:\"name\";s:10:\"Bimbim Cay\";s:11:\"item_number\";N;s:16:\"attribute_values\";N;s:18:\"attribute_dtvalues\";N;s:11:\"description\";s:0:\"\";s:12:\"serialnumber\";s:0:\"\";s:21:\"allow_alt_description\";s:1:\"0\";s:13:\"is_serialized\";s:1:\"0\";s:8:\"quantity\";i:1;s:8:\"discount\";s:4:\"0.00\";s:13:\"discount_type\";s:1:\"0\";s:8:\"in_stock\";s:5:\"2.000\";s:5:\"price\";s:7:\"4800.00\";s:10:\"cost_price\";s:7:\"5000.00\";s:5:\"total\";s:7:\"4800.00\";s:16:\"discounted_total\";s:7:\"4800.00\";s:12:\"print_option\";i:0;s:10:\"stock_type\";s:1:\"0\";s:9:\"item_type\";s:1:\"0\";s:8:\"hsn_code\";s:0:\"\";s:15:\"tax_category_id\";s:1:\"0\";}i:2;a:25:{s:7:\"item_id\";s:1:\"5\";s:13:\"item_location\";s:1:\"1\";s:10:\"stock_name\";s:5:\"stock\";s:4:\"line\";i:2;s:4:\"name\";s:18:\"Coca cola lon 10ml\";s:11:\"item_number\";s:8:\"89130232\";s:16:\"attribute_values\";N;s:18:\"attribute_dtvalues\";N;s:11:\"description\";s:0:\"\";s:12:\"serialnumber\";s:0:\"\";s:21:\"allow_alt_description\";s:1:\"0\";s:13:\"is_serialized\";s:1:\"0\";s:8:\"quantity\";i:1;s:8:\"discount\";s:4:\"0.00\";s:13:\"discount_type\";s:1:\"0\";s:8:\"in_stock\";s:5:\"3.000\";s:5:\"price\";s:8:\"10000.00\";s:10:\"cost_price\";s:8:\"10000.00\";s:5:\"total\";s:8:\"10000.00\";s:16:\"discounted_total\";s:8:\"10000.00\";s:12:\"print_option\";i:0;s:10:\"stock_type\";s:1:\"0\";s:9:\"item_type\";s:1:\"0\";s:8:\"hsn_code\";s:0:\"\";s:15:\"tax_category_id\";s:1:\"0\";}}sales_customer|s:1:\"2\";sales_mode|s:4:\"sale\";dinner_table|i:1;sales_payments|a:0:{}'),('jhpridroo624kumeuss1oifh1ev6ul9a','::1',1739070033,_binary '__ci_last_regenerate|i:1739070033;person_id|s:1:\"1\";menu_group|s:4:\"home\";sale_id|i:-1;allow_temp_items|i:1;sales_location|s:1:\"1\";item_location|s:1:\"1\";recv_cart|a:0:{}recv_mode|s:7:\"receive\";recv_supplier|i:-1;recv_stock_source|s:1:\"1\";cash_rounding|i:0;cash_mode|i:0;sales_cart|a:0:{}sales_customer|i:-1;sales_mode|s:4:\"sale\";dinner_table|i:1;sales_payments|a:0:{}'),('8h8hsl12atucgosfkck9497r6qnsh6sq','::1',1739070540,_binary '__ci_last_regenerate|i:1739070540;person_id|s:1:\"1\";menu_group|s:4:\"home\";sale_id|i:-1;allow_temp_items|i:1;sales_location|s:1:\"1\";item_location|s:1:\"1\";recv_cart|a:0:{}recv_mode|s:7:\"receive\";recv_supplier|i:-1;recv_stock_source|s:1:\"1\";cash_rounding|i:0;cash_mode|i:0;sales_cart|a:0:{}sales_customer|i:-1;sales_mode|s:4:\"sale\";dinner_table|i:1;sales_payments|a:0:{}'),('o4vu1o90ndg0hc5u2kjtvkgvq0qfnlig','::1',1739071046,_binary '__ci_last_regenerate|i:1739071046;person_id|s:1:\"1\";menu_group|s:4:\"home\";sale_id|i:-1;allow_temp_items|i:1;sales_location|s:1:\"1\";item_location|s:1:\"1\";recv_cart|a:0:{}recv_mode|s:7:\"receive\";recv_supplier|i:-1;recv_stock_source|s:1:\"1\";cash_rounding|i:0;cash_mode|i:0;sales_cart|a:0:{}sales_customer|i:-1;sales_mode|s:4:\"sale\";dinner_table|i:1;sales_payments|a:0:{}'),('ukfdrubiuvgngrkmhlfmtn7gjjdrjto1','::1',1739071051,_binary '__ci_last_regenerate|i:1739071046;person_id|s:1:\"1\";menu_group|s:4:\"home\";sale_id|i:-1;allow_temp_items|i:0;sales_location|s:1:\"1\";item_location|s:1:\"1\";recv_cart|a:0:{}recv_mode|s:7:\"receive\";recv_supplier|i:-1;recv_stock_source|s:1:\"1\";cash_rounding|i:0;cash_mode|i:0;sales_cart|a:0:{}sales_customer|i:-1;sales_mode|s:4:\"sale\";dinner_table|i:1;sales_payments|a:0:{}');
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
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`location_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stock_locations`
--

LOCK TABLES `stock_locations` WRITE;
/*!40000 ALTER TABLE `stock_locations` DISABLE KEYS */;
INSERT INTO `stock_locations` VALUES (1,'stock',0);
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
-- Table structure for table `tax_categories`
--

DROP TABLE IF EXISTS `tax_categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tax_categories` (
  `tax_category_id` int NOT NULL AUTO_INCREMENT,
  `tax_category` varchar(32) NOT NULL,
  `tax_group_sequence` tinyint(1) NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`tax_category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tax_categories`
--

LOCK TABLES `tax_categories` WRITE;
/*!40000 ALTER TABLE `tax_categories` DISABLE KEYS */;
/*!40000 ALTER TABLE `tax_categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tax_codes`
--

DROP TABLE IF EXISTS `tax_codes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tax_codes` (
  `tax_code_id` int NOT NULL AUTO_INCREMENT,
  `tax_code` varchar(32) NOT NULL,
  `tax_code_name` varchar(255) NOT NULL DEFAULT '',
  `city` varchar(255) NOT NULL DEFAULT '',
  `state` varchar(255) NOT NULL DEFAULT '',
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`tax_code_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tax_codes`
--

LOCK TABLES `tax_codes` WRITE;
/*!40000 ALTER TABLE `tax_codes` DISABLE KEYS */;
/*!40000 ALTER TABLE `tax_codes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tax_jurisdictions`
--

DROP TABLE IF EXISTS `tax_jurisdictions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tax_jurisdictions` (
  `jurisdiction_id` int NOT NULL AUTO_INCREMENT,
  `jurisdiction_name` varchar(255) DEFAULT NULL,
  `tax_group` varchar(32) NOT NULL,
  `tax_type` smallint NOT NULL,
  `reporting_authority` varchar(255) DEFAULT NULL,
  `tax_group_sequence` tinyint(1) NOT NULL DEFAULT '0',
  `cascade_sequence` tinyint(1) NOT NULL DEFAULT '0',
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`jurisdiction_id`),
  UNIQUE KEY `tax_jurisdictions_uq1` (`tax_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tax_jurisdictions`
--

LOCK TABLES `tax_jurisdictions` WRITE;
/*!40000 ALTER TABLE `tax_jurisdictions` DISABLE KEYS */;
/*!40000 ALTER TABLE `tax_jurisdictions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tax_rates`
--

DROP TABLE IF EXISTS `tax_rates`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tax_rates` (
  `tax_rate_id` int NOT NULL AUTO_INCREMENT,
  `rate_tax_code_id` int NOT NULL,
  `rate_tax_category_id` int NOT NULL,
  `rate_jurisdiction_id` int NOT NULL,
  `tax_rate` decimal(15,4) NOT NULL DEFAULT '0.0000',
  `tax_rounding_code` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`tax_rate_id`),
  KEY `rate_tax_category_id` (`rate_tax_category_id`),
  KEY `rate_tax_code_id` (`rate_tax_code_id`),
  KEY `rate_jurisdiction_id` (`rate_jurisdiction_id`),
  CONSTRAINT `tax_rates_ibfk_1` FOREIGN KEY (`rate_tax_category_id`) REFERENCES `tax_categories` (`tax_category_id`),
  CONSTRAINT `tax_rates_ibfk_2` FOREIGN KEY (`rate_tax_code_id`) REFERENCES `tax_codes` (`tax_code_id`),
  CONSTRAINT `tax_rates_ibfk_3` FOREIGN KEY (`rate_jurisdiction_id`) REFERENCES `tax_jurisdictions` (`jurisdiction_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tax_rates`
--

LOCK TABLES `tax_rates` WRITE;
/*!40000 ALTER TABLE `tax_rates` DISABLE KEYS */;
/*!40000 ALTER TABLE `tax_rates` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-02-09 10:28:11
