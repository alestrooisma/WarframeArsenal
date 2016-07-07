CREATE 
    ALGORITHM = UNDEFINED 
    DEFINER = `root`@`localhost` 
    SQL SECURITY DEFINER
VIEW `RangedWeapons` AS
    SELECT 
        `w`.`name` AS `name`,
        `w`.`mastery_level` AS `mastery_level`,
        `weapon_slots`.`name` AS `slot`,
        `weapon_types`.`name` AS `type`,
        `main_trigger`.`name` AS `trigger_type`,
        `alt_trigger`.`name` AS `alt_trigger_type`,
        `w`.`fire_rate` AS `fire_rate`,
        `w`.`accuracy` AS `accuracy`,
        `w`.`mag_size` AS `mag_size`,
        `w`.`max_ammo` AS `max_ammo`,
        `w`.`reload_time` AS `reload_time`,
        `w`.`impact` AS `impact`,
        `w`.`puncture` AS `puncture`,
        `w`.`slash` AS `slash`,
        `w`.`crit_chance` AS `crit_chance`,
        `w`.`crit_multiplier` AS `crit_multiplier`,
        `w`.`status_chance` AS `status_chance`,
        (`w`.`secondary_attack` + 0) AS `secondary_attack`
    FROM
        ((((`ranged_weapons` `w`
        JOIN `weapon_slots` ON ((`w`.`slot` = `weapon_slots`.`id`)))
        JOIN `weapon_types` ON ((`w`.`type` = `weapon_types`.`id`)))
        JOIN `trigger_types` `main_trigger` ON ((`w`.`trigger_type` = `main_trigger`.`id`)))
        LEFT JOIN `trigger_types` `alt_trigger` ON ((`w`.`alt_trigger_type` = `alt_trigger`.`id`)));
