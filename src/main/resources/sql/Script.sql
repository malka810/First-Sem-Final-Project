CREATE DATABASE `SunFlower` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */

-- SunFlower.Customer definition

CREATE TABLE `Customer` (
  `customer_id` varchar(10) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `contact` int DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- SunFlower.Employee definition

CREATE TABLE `Employee` (
  `employee_id` varchar(5) NOT NULL,
  `e_name` varchar(100) DEFAULT NULL,
  `department` varchar(100) DEFAULT NULL,
  `role` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`employee_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- SunFlower.Orders definition

CREATE TABLE `Orders` (
  `order_id` varchar(10) NOT NULL,
  `order_date` date DEFAULT NULL,
  `payment` decimal(10,2) DEFAULT NULL,
  `customer_id` varchar(10) DEFAULT NULL,
  `user_id` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  KEY `customer_id` (`customer_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `Orders_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `Customer` (`customer_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Orders_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `User` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- SunFlower.OrderDetails definition

CREATE TABLE `OrderDetails` (
  `order_id` varchar(10) DEFAULT NULL,
  `product_id` varchar(10) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `weight` decimal(10,0) DEFAULT NULL,
  `unit_price` decimal(10,2) DEFAULT NULL,
  KEY `order_id` (`order_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `OrderDetails_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `Orders` (`order_id`),
  CONSTRAINT `OrderDetails_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `Product` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- SunFlower.PackagingMaterial definition

CREATE TABLE `PackagingMaterial` (
  `packaging_material_id` varchar(10) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `description` text,
  `stock_quantity` int DEFAULT NULL,
  `qty_available` varchar(255) DEFAULT NULL,
  `supplier_id` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`packaging_material_id`),
  KEY `supplier_id` (`supplier_id`),
  CONSTRAINT `PackagingMaterial_ibfk_1` FOREIGN KEY (`supplier_id`) REFERENCES `Supplier` (`supplier_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- SunFlower.Product definition

CREATE TABLE `Product` (
  `product_id` varchar(10) NOT NULL,
  `product_name` varchar(255) DEFAULT NULL,
  `description` text,
  `category` varchar(255) DEFAULT NULL,
  `qty_on_hand` int DEFAULT NULL,
  `weight` decimal(10,2) DEFAULT NULL,
  `unit_price` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- SunFlower.ProductPackagingMaterial definition

CREATE TABLE `ProductPackagingMaterial` (
  `product_id` varchar(10) DEFAULT NULL,
  `packaging_material_id` varchar(10) DEFAULT NULL,
  `packaging_size` varchar(10) DEFAULT NULL,
  `qualitychecks` varchar(10) DEFAULT NULL,
  `return_quantity` varchar(10) DEFAULT NULL,
  KEY `product_id` (`product_id`),
  KEY `packaging_material_id` (`packaging_material_id`),
  CONSTRAINT `ProductPackagingMaterial_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `Product` (`product_id`),
  CONSTRAINT `ProductPackagingMaterial_ibfk_2` FOREIGN KEY (`packaging_material_id`) REFERENCES `PackagingMaterial` (`packaging_material_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- SunFlower.ProductRawMaterial definition

CREATE TABLE `ProductRawMaterial` (
  `product_id` varchar(10) DEFAULT NULL,
  `raw_material_id` varchar(10) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `weight` decimal(10,0) DEFAULT NULL,
  `balance` decimal(10,2) DEFAULT NULL,
  `production` int DEFAULT NULL,
  `out_quantity` int DEFAULT NULL,
  KEY `product_id` (`product_id`),
  KEY `raw_material_id` (`raw_material_id`),
  CONSTRAINT `ProductRawMaterial_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `Product` (`product_id`),
  CONSTRAINT `ProductRawMaterial_ibfk_2` FOREIGN KEY (`raw_material_id`) REFERENCES `RawMaterial` (`raw_material_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- SunFlower.RawMaterial definition

CREATE TABLE `RawMaterial` (
  `raw_material_id` varchar(10) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `description` text,
  `stock_quantity` int DEFAULT NULL,
  `supplier_id` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`raw_material_id`),
  KEY `supplier_id` (`supplier_id`),
  CONSTRAINT `RawMaterial_ibfk_1` FOREIGN KEY (`supplier_id`) REFERENCES `Supplier` (`supplier_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- SunFlower.Supplier definition

CREATE TABLE `Supplier` (
  `supplier_id` varchar(10) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `contact` varchar(50) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`supplier_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- SunFlower.`User` definition

CREATE TABLE `User` (
  `user_id` varchar(5) NOT NULL,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

