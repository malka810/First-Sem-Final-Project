
drop database if exists SunFlower_Food_Product;
create database if not exists SunFlower_Food_Product;

use SunFlower_Food_Product;
    -- SunFlower_Food_Product.Customer definition
CREATE TABLE `Customer` (
  `CustomerId` varchar(10) NOT NULL,
  `Name` varchar(100) NOT NULL,
  `Contact` varchar(50) DEFAULT NULL,
  `Address` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`CustomerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE Employee (
                          EmployeeId VARCHAR(5) PRIMARY KEY,
                          E_Name VARCHAR(100) NOT NULL,
                          Department VARCHAR(100),
                          Role VARCHAR(100)
);

-- SunFlower_Food_Product.OrderProductDetails definition

CREATE TABLE `OrderProductDetails` (
  `OrderDetailId` varchar(10) NOT NULL,
  `OrderId` varchar(10) DEFAULT NULL,
  `ProductId` varchar(10) DEFAULT NULL,
  `Quantity` int DEFAULT NULL,
  `Weight` decimal(10,0) DEFAULT NULL,
  `UnitPrice` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`OrderDetailId`),
  KEY `OrderId` (`OrderId`),
  KEY `ProductId` (`ProductId`),
  CONSTRAINT `OrderProductDetails_ibfk_1` FOREIGN KEY (`OrderId`) REFERENCES `Orders` (`OrderId`),
  CONSTRAINT `OrderProductDetails_ibfk_2` FOREIGN KEY (`ProductId`) REFERENCES `Product` (`ProductId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- SunFlower_Food_Product.Orders definition

CREATE TABLE `Orders` (
  `OrderId` varchar(10) NOT NULL,
  `OrderDate` date DEFAULT NULL,
  `Payment` decimal(10,2) DEFAULT NULL,
  `CustomerId` varchar(10) DEFAULT NULL,
  `UserId` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`OrderId`),
  KEY `CustomerId` (`CustomerId`),
  KEY `UserId` (`UserId`),
  CONSTRAINT `Orders_ibfk_1` FOREIGN KEY (`CustomerId`) REFERENCES `Customer` (`CustomerId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Orders_ibfk_2` FOREIGN KEY (`UserId`) REFERENCES `User` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- SunFlower_Food_Product.Customer definition

-- SunFlower_Food_Product.PackagingMaterial definition

CREATE TABLE `PackagingMaterial` (
  `PackagingMaterialId` varchar(10) NOT NULL,
  `Name` varchar(255) NOT NULL,
  `Date` date DEFAULT NULL,
  `Description` text,
  `StockQuantity` int DEFAULT '0',
  `qtyAvailable` int DEFAULT NULL,
  `SupplierId` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`PackagingMaterialId`),
  KEY `SupplierId` (`SupplierId`),
  CONSTRAINT `PackagingMaterial_ibfk_1` FOREIGN KEY (`SupplierId`) REFERENCES `Supplier` (`SupplierId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- SunFlower_Food_Product.Product definition

CREATE TABLE `Product` (
  `ProductId` varchar(10) NOT NULL,
  `ProductName` varchar(255) NOT NULL,
  `Description` text,
  `Category` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ProductId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- SunFlower_Food_Product.ProductPackagingMaterial definition

CREATE TABLE `ProductPackagingMaterial` (
  `ProductId` varchar(10) DEFAULT NULL,
  `PackagingMaterialId` varchar(10) DEFAULT NULL,
  `PackagingSize` varchar(10) DEFAULT NULL,
  `QualityChecks` varchar(10) DEFAULT NULL,
  `ReturnQuantity` varchar(10) DEFAULT NULL,
  KEY `ProductId` (`ProductId`),
  KEY `PackagingMaterialId` (`PackagingMaterialId`),
  CONSTRAINT `ProductPackagingMaterial_ibfk_1` FOREIGN KEY (`ProductId`) REFERENCES `Product` (`ProductId`),
  CONSTRAINT `ProductPackagingMaterial_ibfk_2` FOREIGN KEY (`PackagingMaterialId`) REFERENCES `PackagingMaterial` (`PackagingMaterialId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- SunFlower_Food_Product.ProductRawMaterial definition

CREATE TABLE `ProductRawMaterial` (
  `ProductId` varchar(10) DEFAULT NULL,
  `RawMaterialId` varchar(10) DEFAULT NULL,
  `Quantity` int DEFAULT NULL,
  `Weight` decimal(10,0) DEFAULT NULL,
  `Balance` decimal(10,2) DEFAULT NULL,
  `Production` int DEFAULT NULL,
  `OutQuantity` int DEFAULT NULL,
  KEY `ProductId` (`ProductId`),
  KEY `RawMaterialId` (`RawMaterialId`),
  CONSTRAINT `ProductRawMaterial_ibfk_1` FOREIGN KEY (`ProductId`) REFERENCES `Product` (`ProductId`),
  CONSTRAINT `ProductRawMaterial_ibfk_2` FOREIGN KEY (`RawMaterialId`) REFERENCES `RawMaterial` (`RawMaterialId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- SunFlower_Food_Product.RawMaterial definition

CREATE TABLE `RawMaterial` (
  `RawMaterialId` varchar(10) NOT NULL,
  `Name` varchar(255) NOT NULL,
  `Date` date DEFAULT NULL,
  `Description` text,
  `StockQuantity` int DEFAULT '0',
  `SupplierId` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`RawMaterialId`),
  KEY `SupplierId` (`SupplierId`),
  CONSTRAINT `RawMaterial_ibfk_1` FOREIGN KEY (`SupplierId`) REFERENCES `Supplier` (`SupplierId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- SunFlower_Food_Product.Supplier definition

CREATE TABLE `Supplier` (
  `SupplierId` varchar(10) NOT NULL,
  `Name` varchar(255) NOT NULL,
  `Contact` varchar(50) DEFAULT NULL,
  `Address` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`SupplierId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- SunFlower_Food_Product.`User` definition

CREATE TABLE `User` (
  `userId` varchar(5) NOT NULL,
  `password` varchar(8) DEFAULT NULL,
  `username` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


INSERT INTO User VALUES ('U001', '1234', 'malka');