CREATE TABLE `trigger_types` (
  `id` tinyint(3) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

CREATE TABLE `weapon_slots` (
  `id` tinyint(3) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(9) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `description_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

CREATE TABLE `weapon_types` (
  `id` tinyint(3) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

CREATE TABLE `ranged_weapons` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(48) NOT NULL,
  `mastery_level` tinyint(3) unsigned NOT NULL,
  `slot` tinyint(3) unsigned NOT NULL,
  `type` tinyint(3) unsigned NOT NULL,
  `trigger_type` tinyint(3) unsigned NOT NULL,
  `alt_trigger_type` tinyint(3) unsigned DEFAULT NULL,
  `fire_rate` float unsigned NOT NULL,
  `accuracy` float unsigned NOT NULL,
  `mag_size` int(10) unsigned NOT NULL,
  `max_ammo` int(10) unsigned NOT NULL,
  `reload_time` float unsigned NOT NULL,
  `impact` float unsigned NOT NULL,
  `puncture` float unsigned NOT NULL,
  `slash` float unsigned NOT NULL,
  `crit_chance` float unsigned NOT NULL,
  `crit_multiplier` float unsigned NOT NULL,
  `status_chance` float unsigned NOT NULL,
  `secondary_attack` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  KEY `fk_slot_idx` (`slot`),
  KEY `fk_trigger_type_idx` (`trigger_type`),
  KEY `fk_weapon_type_idx` (`type`),
  KEY `fk_alt_trigger_type_idx` (`alt_trigger_type`),
  CONSTRAINT `fk_alt_trigger_type` FOREIGN KEY (`alt_trigger_type`) REFERENCES `trigger_types` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_trigger_type` FOREIGN KEY (`trigger_type`) REFERENCES `trigger_types` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_weapon_slot` FOREIGN KEY (`slot`) REFERENCES `weapon_slots` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_weapon_type` FOREIGN KEY (`type`) REFERENCES `weapon_types` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=latin1;
