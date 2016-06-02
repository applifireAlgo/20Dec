DROP TABLE IF EXISTS `art_log_connector_attributes_m`;

CREATE TABLE `art_log_connector_attributes_m` ( `attributeId` varchar(64) NOT NULL, `connectorId` varchar(64) NOT NULL , `attributeName` VARCHAR(256) NOT NULL, `attributeValue` VARCHAR(256) NOT NULL, `attributeComment` VARCHAR(1024) DEFAULT NULL, `attributeDisplayName` VARCHAR(256) DEFAULT NULL, `versionId` INT(11) DEFAULT NULL, `createdBy` INT(11) DEFAULT NULL, `createdDate` TIMESTAMP NULL DEFAULT NULL, `updatedBy` INT(11) DEFAULT NULL, `updatedDate` DATETIME DEFAULT NULL, `activeStatus` TINYINT(1) DEFAULT NULL, PRIMARY KEY (`attributeId`), KEY `ConnectorFK` (`connectorId`), CONSTRAINT `ArtConnectorFK` FOREIGN KEY (`connectorId`) REFERENCES `art_log_connector_m` (`connectorId`) );

