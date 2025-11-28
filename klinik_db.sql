-- AdminNeo 4.17.2 MySQL 8.4.3 dump

SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

SET NAMES utf8mb4;

CREATE DATABASE `klinik_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `klinik_db`;

DROP TABLE IF EXISTS `appointments`;
CREATE TABLE `appointments` (
  `id` int NOT NULL AUTO_INCREMENT,
  `patient_id` int DEFAULT NULL,
  `doctor_id` int DEFAULT NULL,
  `date` date DEFAULT NULL,
  `queue_number` int DEFAULT NULL,
  `weight` double DEFAULT NULL,
  `height` double DEFAULT NULL,
  `blood_pressure` varchar(20) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `patient_id` (`patient_id`),
  KEY `doctor_id` (`doctor_id`),
  CONSTRAINT `appointments_ibfk_1` FOREIGN KEY (`patient_id`) REFERENCES `patients` (`id`),
  CONSTRAINT `appointments_ibfk_2` FOREIGN KEY (`doctor_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `appointments` (`id`, `patient_id`, `doctor_id`, `date`, `queue_number`, `weight`, `height`, `blood_pressure`, `status`) VALUES
(1,	1,	2,	'2025-11-21',	1,	123,	123,	NULL,	'COMPLETED'),
(2,	2,	2,	'2025-11-23',	1,	50,	170,	NULL,	'COMPLETED'),
(3,	3,	2,	'2025-11-23',	2,	70,	169,	NULL,	'COMPLETED'),
(4,	4,	2,	'2025-11-23',	3,	12,	12,	NULL,	'COMPLETED'),
(5,	5,	2,	'2025-11-23',	4,	12,	12,	NULL,	'COMPLETED'),
(6,	6,	2,	'2025-11-24',	1,	12,	12,	NULL,	'COMPLETED'),
(7,	7,	2,	'2025-11-24',	2,	58,	170,	NULL,	'COMPLETED'),
(8,	8,	2,	'2025-11-24',	3,	12,	41,	NULL,	'COMPLETED'),
(9,	9,	2,	'2025-11-24',	4,	50,	159,	NULL,	'COMPLETED'),
(10,	10,	2,	'2025-11-24',	5,	154,	400,	NULL,	'WAITING');

DROP TABLE IF EXISTS `bills`;
CREATE TABLE `bills` (
  `id` int NOT NULL AUTO_INCREMENT,
  `appointment_id` int DEFAULT NULL,
  `total_medication_cost` double DEFAULT NULL,
  `consultation_fee` double DEFAULT NULL,
  `total_amount` double DEFAULT NULL,
  `is_paid` tinyint(1) DEFAULT '0',
  `payment_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `appointment_id` (`appointment_id`),
  CONSTRAINT `bills_ibfk_1` FOREIGN KEY (`appointment_id`) REFERENCES `appointments` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `bills` (`id`, `appointment_id`, `total_medication_cost`, `consultation_fee`, `total_amount`, `is_paid`, `payment_date`) VALUES
(1,	1,	0,	50000,	50000,	1,	'2025-11-24 20:32:46'),
(2,	2,	0,	50000,	50000,	0,	NULL),
(3,	3,	0,	50000,	50000,	0,	NULL),
(4,	4,	0,	50000,	50000,	0,	NULL),
(5,	5,	0,	50000,	50000,	0,	NULL),
(6,	6,	0,	50000,	50000,	0,	NULL),
(7,	7,	0,	50000,	50000,	0,	NULL),
(8,	8,	0,	50000,	50000,	0,	NULL),
(9,	9,	0,	50000,	50000,	0,	NULL);

DROP TABLE IF EXISTS `medical_records`;
CREATE TABLE `medical_records` (
  `id` int NOT NULL AUTO_INCREMENT,
  `appointment_id` int DEFAULT NULL,
  `symptoms` text,
  `diagnosis` text,
  `treatment` text,
  `consultation_fee` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `appointment_id` (`appointment_id`),
  CONSTRAINT `medical_records_ibfk_1` FOREIGN KEY (`appointment_id`) REFERENCES `appointments` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `medical_records` (`id`, `appointment_id`, `symptoms`, `diagnosis`, `treatment`, `consultation_fee`) VALUES
(1,	1,	'sihat',	'no',	'sersan thundercock',	50000),
(2,	2,	'abc',	'aas',	'asd',	50000),
(3,	3,	'asd',	'asd',	'asd',	50000),
(4,	4,	'asd',	'asd',	'asd',	50000),
(5,	5,	'asd',	'asd',	'asd',	50000),
(6,	6,	'asd',	'asd',	'asd',	50000),
(7,	7,	'sehat',	'-',	'-',	50000),
(8,	8,	'sakit gigi',	'gigi berlubang',	'-',	50000),
(9,	9,	'asd',	'asd',	'asd',	50000);

DROP TABLE IF EXISTS `medications`;
CREATE TABLE `medications` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `category` varchar(50) DEFAULT NULL,
  `stock_quantity` int DEFAULT NULL,
  `price` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `medications` (`id`, `name`, `category`, `stock_quantity`, `price`) VALUES
(1,	'sihat',	'Injeksi',	2,	150000),
(2,	'Paradox',	'Tablet',	10,	15000);

DROP TABLE IF EXISTS `patients`;
CREATE TABLE `patients` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `nik` varchar(20) DEFAULT NULL,
  `birth_date` date DEFAULT NULL,
  `gender` char(1) DEFAULT NULL,
  `address` text,
  `phone_number` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `patients` (`id`, `name`, `nik`, `birth_date`, `gender`, `address`, `phone_number`) VALUES
(1,	'sasd',	'213213',	'2025-11-05',	'P',	'sadas',	'58674564'),
(2,	'pidi',	'123456',	'2025-11-12',	'P',	'jl amilin',	'081234567890'),
(3,	'ucup',	'12334',	'2025-11-04',	'P',	'sungai batak',	'085234567890'),
(4,	'afas',	'123',	'2025-11-06',	'P',	'sad',	'123'),
(5,	'azril',	'1234',	'2025-11-21',	'P',	'asdsad',	'123213'),
(6,	'asd',	'123',	'2025-11-06',	'P',	'asd',	'123'),
(7,	'nopal',	'123',	'2025-11-01',	'P',	'jalan parit indah',	'081234567890'),
(8,	'andre',	'213',	'2025-11-05',	'P',	'asd',	'123'),
(9,	'andriana',	'123',	'2025-11-20',	'P',	'asd',	'123'),
(10,	'pidi',	'21321',	'2025-11-04',	'P',	'asd',	'123');

DROP TABLE IF EXISTS `prescription_items`;
CREATE TABLE `prescription_items` (
  `id` int NOT NULL AUTO_INCREMENT,
  `medical_record_id` int DEFAULT NULL,
  `medication_id` int DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `price_at_time` double DEFAULT NULL,
  `instructions` varchar(255) DEFAULT NULL,
  `status` varchar(20) DEFAULT 'PENDING',
  PRIMARY KEY (`id`),
  KEY `medical_record_id` (`medical_record_id`),
  KEY `medication_id` (`medication_id`),
  CONSTRAINT `prescription_items_ibfk_1` FOREIGN KEY (`medical_record_id`) REFERENCES `medical_records` (`id`),
  CONSTRAINT `prescription_items_ibfk_2` FOREIGN KEY (`medication_id`) REFERENCES `medications` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `full_name` varchar(100) DEFAULT NULL,
  `role` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `users` (`id`, `username`, `password`, `full_name`, `role`) VALUES
(1,	'admin',	'admin123',	'Super Admin',	'SUPERADMIN'),
(2,	'arip',	'1234',	'aulia',	'DOCTOR'),
(3,	'tio',	'tio123',	'tio prtm',	'RECEPTIONIST'),
(4,	'wawa',	'123',	'wawa',	'PHARMACIST'),
(5,	'varel',	'123',	'varel',	'CASHIER');

-- 2025-11-28 14:30:30 UTC
