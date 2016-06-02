DROP TABLE IF EXISTS `art_log_bounded_context`;

CREATE TABLE `art_log_bounded_context` (
  `boundedContextId` varchar(256) NOT NULL,
  `bounded_context_prefix` varchar(256) NOT NULL,
  `bounded_context_json` varchar(1000) NOT NULL,
  `created_by` varchar(64) DEFAULT '-1',
  `created_date` datetime DEFAULT '1900-01-01 00:00:00',
  `updated_by` varchar(64) DEFAULT '-1',
  `updated_date` datetime DEFAULT '1900-01-01 00:00:00',
  `version_id` int(11) DEFAULT '-1',
  `active_status` int(1) DEFAULT '1',
  `app_creator_id` varchar(64) NOT NULL,
  PRIMARY KEY (`boundedContextId`)
);

