DROP TABLE IF EXISTS `art_log_config_m`;

CREATE TABLE `art_log_config_m` ( `logConfigId` varchar(64) NOT NULL, `configData` longtext, `versionId` INT(11) DEFAULT NULL, `createdBy` INT(11) DEFAULT NULL, `createdDate` TIMESTAMP NULL DEFAULT NULL, `updatedBy` INT(11) DEFAULT NULL, `updatedDate` DATETIME DEFAULT NULL, `activeStatus` TINYINT(1) DEFAULT NULL, PRIMARY KEY (`logConfigId`) );

