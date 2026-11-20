-- ===================================================================
-- Sadik Electrical Services - MySQL Database Schema
-- Database Name: sadik_electrical_db
-- Location: Chilakaluripet, Andhra Pradesh, India
-- ===================================================================

CREATE DATABASE IF NOT EXISTS `sadik_electrical_db` 
DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE `sadik_electrical_db`;

-- 1. Admin Table
CREATE TABLE IF NOT EXISTS `admin` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `username` VARCHAR(50) NOT NULL UNIQUE,
    `password` VARCHAR(255) NOT NULL,
    `full_name` VARCHAR(100) NOT NULL,
    `email` VARCHAR(100),
    `role` VARCHAR(20) NOT NULL DEFAULT 'ROLE_ADMIN',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 2. Services Table
CREATE TABLE IF NOT EXISTS `services` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(100) NOT NULL UNIQUE,
    `slug` VARCHAR(100) NOT NULL UNIQUE,
    `short_description` VARCHAR(255) NOT NULL,
    `description` TEXT NOT NULL,
    `icon` VARCHAR(50) NOT NULL,
    `image` VARCHAR(255) NOT NULL,
    `price_starting` DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    `active` BOOLEAN NOT NULL DEFAULT TRUE,
    `display_order` INT NOT NULL DEFAULT 0
);

-- 3. Customers Table
CREATE TABLE IF NOT EXISTS `customers` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(100) NOT NULL,
    `mobile` VARCHAR(15) NOT NULL,
    `email` VARCHAR(100),
    `address` TEXT NOT NULL,
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 4. Bookings Table
CREATE TABLE IF NOT EXISTS `bookings` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `booking_reference` VARCHAR(20) NOT NULL UNIQUE,
    `customer_id` BIGINT NOT NULL,
    `service_id` BIGINT NOT NULL,
    `preferred_date` DATE NOT NULL,
    `preferred_time` VARCHAR(50) NOT NULL,
    `problem_description` TEXT NOT NULL,
    `status` VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    `admin_notes` TEXT,
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT `fk_booking_customer` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`) ON DELETE CASCADE,
    CONSTRAINT `fk_booking_service` FOREIGN KEY (`service_id`) REFERENCES `services` (`id`) ON DELETE RESTRICT
);
