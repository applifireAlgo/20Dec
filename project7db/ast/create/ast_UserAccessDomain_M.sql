DROP TABLE IF EXISTS `ast_UserAccessDomain_M`;

CREATE TABLE `ast_UserAccessDomain_M` ( `userAccessDomainId` VARCHAR(64) NOT NULL, `userAccessDomain` INT(11) NOT NULL, `domainName` VARCHAR(256) NOT NULL, `domainDescription` VARCHAR(256) NOT NULL, `domainHelp` VARCHAR(2048) NULL DEFAULT NULL, `domainIcon` VARCHAR(256) NULL DEFAULT NULL, `createdBy` VARCHAR(64) NULL DEFAULT '-1', `createdDate` TIMESTAMP NULL DEFAULT '2000-01-01 10:10:10', `updatedBy` VARCHAR(64) NULL DEFAULT '-1', `updatedDate` TIMESTAMP NULL DEFAULT '2000-01-01 10:10:10', `versionId` INT(10) NULL DEFAULT '-1', `activeStatus` INT(1) NULL DEFAULT '1', `txnAccessCode` INT(10) NULL DEFAULT NULL, PRIMARY KEY (`userAccessDomainId`), UNIQUE KEY (`userAccessDomain`));

