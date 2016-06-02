DROP TABLE IF EXISTS `art_log_event_action_m`;

CREATE TABLE `art_log_event_action_m` (`eventActionId` INT (11) NOT NULL, `eventAction` VARCHAR (256) NOT NULL, `versionId` INT (11) DEFAULT NULL, `createdBy` varchar (64) DEFAULT NULL, `createdDate` TIMESTAMP NULL DEFAULT NULL, `updatedBy` varchar (64) DEFAULT NULL, `updatedDate` DATETIME DEFAULT NULL, `activeStatus` TINYINT (1) DEFAULT NULL, PRIMARY KEY (`eventActionId`));

