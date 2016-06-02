DROP TABLE IF EXISTS `art_log_exception_m`;

CREATE TABLE `art_log_exception_m` (`exceptionId` INT (11) NOT NULL, `exception` VARCHAR (256) NOT NULL, `rootException` VARCHAR (256) NOT NULL, `exceptionName` VARCHAR (256) NOT NULL, `versionId` INT (11) DEFAULT NULL, `createdBy` varchar (64) DEFAULT NULL, `createdDate` TIMESTAMP NULL DEFAULT NULL, `updatedBy` varchar (64) DEFAULT NULL, `updatedDate` DATETIME DEFAULT NULL, `activeStatus` TINYINT (1) DEFAULT NULL, PRIMARY KEY (`exceptionId`));

