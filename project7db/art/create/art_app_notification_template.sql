DROP TABLE IF EXISTS `art_app_notification_template`;

CREATE TABLE `art_app_notification_template` ( `template_id` VARCHAR (64) NOT NULL, `template_name` VARCHAR (256) NOT NULL, `template` LONGTEXT, `notification_type` INT(11) NOT NULL, `created_by` VARCHAR (64) DEFAULT '-1', `created_date` DATETIME DEFAULT NULL, `updated_by` VARCHAR (64) DEFAULT '-1', `updated_date` DATETIME DEFAULT NULL, `active_status` INT (1) DEFAULT NULL, `version_id` INT (11) DEFAULT NULL, PRIMARY KEY (`template_id`) ) ;

