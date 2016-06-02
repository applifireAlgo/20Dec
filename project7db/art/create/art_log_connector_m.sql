DROP TABLE IF EXISTS `art_log_connector_m`;

CREATE TABLE `art_log_connector_m` ( `connectorId`  varchar(64) NOT NULL , `logConfigId` varchar(64) NOT NULL , `connectorLogLevel` INT(11) NOT NULL, `cid` INT(11) NOT NULL, `id` VARCHAR(256) DEFAULT NULL, `enabled` TINYINT(1) NOT NULL, `connectorName` VARCHAR(256) NOT NULL, `className` VARCHAR(256) NOT NULL, `systemDefined` TINYINT(1) DEFAULT NULL, `versionId` INT(11) DEFAULT NULL, `createdBy` INT(11) DEFAULT NULL, `createdDate` TIMESTAMP NULL DEFAULT NULL, `updatedBy` INT(11) DEFAULT NULL, `updatedDate` DATETIME DEFAULT NULL, `activeStatus` TINYINT(1) DEFAULT NULL, PRIMARY KEY (`connectorId`), KEY `LogConfigFK` (`logConfigId`), CONSTRAINT `ArtLogConfigFK` FOREIGN KEY (`logConfigId`) REFERENCES `art_log_config_m` (`logConfigId`) ) ;

