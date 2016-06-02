DROP TABLE IF EXISTS `art_log_domain`;

CREATE TABLE `art_log_domain` (
  `domainPkId` varchar(256) NOT NULL,
  `domain_name` varchar(256) NOT NULL,
  `domain_id` varchar(256) NOT NULL,
  `trace` tinyint(1) NOT NULL,
  `domain_criticality` int(10) NOT NULL,
  `connector_log_priority` varchar(256) NOT NULL,
  `created_by` varchar(64) DEFAULT '-1',
  `created_date` datetime DEFAULT '1900-01-01 00:00:00',
  `updated_by` varchar(64) DEFAULT '-1',
  `updated_date` datetime DEFAULT '1900-01-01 00:00:00',
  `version_id` int(11) DEFAULT '-1',
  `active_status` int(1) DEFAULT '1',
  `app_creator_id` varchar(64) NOT NULL,
  `boundedContextId` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`domainPkId`),
  KEY `fk_aws_log_domain_1_idx` (`boundedContextId`),
  CONSTRAINT `fk_aws_log_domain_1` FOREIGN KEY (`boundedContextId`) REFERENCES `art_log_bounded_context` (`boundedContextId`) ON DELETE NO ACTION ON UPDATE NO ACTION
);

