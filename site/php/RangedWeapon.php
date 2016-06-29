<?php
include 'Weapon.php';

class RangedWeapon extends Weapon {
	public $trigger_type;
	public $alt_trigger_type;
	public $fire_rate;
	public $accuracy;
	public $mag_size;
	public $max_ammo;
	public $reload_time;
	public $impact;
	public $puncture;
	public $slash;
	public $crit_chance;
	public $crit_multiplier;
	public $status_chance;
	public $secondary_attack;
}