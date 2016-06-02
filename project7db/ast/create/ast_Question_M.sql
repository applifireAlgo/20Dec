DROP TABLE IF EXISTS `ast_Question_M`;

CREATE TABLE `ast_Question_M` ( `questionId` VARCHAR(64) NOT NULL, `levelid` INT(11) NOT NULL, `question` VARCHAR(256) NOT NULL, `questionDetails` TEXT NULL DEFAULT NULL, `questionIcon` VARCHAR(64) NULL DEFAULT NULL, `createdBy` VARCHAR(64) NULL DEFAULT '-1', `createdDate` TIMESTAMP NULL DEFAULT '2000-01-01 10:10:10', `updatedBy` VARCHAR(64) NULL DEFAULT '-1', `updatedDate` TIMESTAMP NULL DEFAULT '2000-01-01 10:10:10', `versionId` INT(10) NULL DEFAULT '-1', `activeStatus` INT(1) NULL DEFAULT '1', `txnAccessCode` INT(10) NULL DEFAULT NULL, PRIMARY KEY (`questionId`));

