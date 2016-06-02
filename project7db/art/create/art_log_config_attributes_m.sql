DROP TABLE IF EXISTS `art_log_config_attributes_m`;

CREATE TABLE `art_log_config_attributes_m` ( `attributeId` varchar(64) NOT NULL , `logConfigId` varchar(64) NOT NULL , `attributeName` VARCHAR(256) DEFAULT NULL, `attributeValue` VARCHAR(256) DEFAULT NULL, `attributeComment` VARCHAR(1000) DEFAULT NULL, `attributeDisplayName` VARCHAR(256) DEFAULT NULL, `versionId` INT(11) DEFAULT NULL, `createdBy` INT(11) DEFAULT NULL, `createdDate` TIMESTAMP NULL DEFAULT NULL, `updatedBy` INT(11) DEFAULT NULL, `updatedDate` DATETIME DEFAULT NULL, `activeStatus` TINYINT(1) DEFAULT NULL, PRIMARY KEY (`attributeId`), KEY `LogFK` (`logConfigId`), CONSTRAINT `ArtLogFK` FOREIGN KEY (`logConfigId`) REFERENCES `art_log_config_m` (`logConfigId`) ) ;

