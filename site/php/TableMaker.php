<?php

class TableMaker {

	private $weapons;

	public function __construct($weapons) {
		$this->weapons = $weapons;
	}

	public function write($class) {
		$this->begin($class);
		$this->writeImageRow();
		$this->writeHeaderRow();
		$this->writeRow('Mastery Level', 'mastery_level');
		$this->writeRow('Weapon Slot', 'slot');
		$this->writeRow('Weapon Type', 'type');
		$this->writeRow('Trigger Type', 'trigger_type');
		$this->writeRow('Fire Rate', 'fire_rate');
		$this->writeRow('Accuracy', 'accuracy');
		$this->writeRow('Magazine Size', 'mag_size');
		$this->writeRow('Max Ammo', 'max_ammo');
		$this->writeRow('Reload Time', 'reload_time');
		$this->writeRow('Impact', 'impact');
		$this->writeRow('Puncture', 'puncture');
		$this->writeRow('Slash', 'slash');
		$this->writeRow('Crit Chance', 'crit_chance');
		$this->writeRow('Crit Multiplier', 'crit_multiplier');
		$this->writeRow('Status Chance', 'status_chance');
		$this->end();
	}

	public function begin($class) {
		echo '<table class="' . $class . '">' . PHP_EOL;
	}

	public function end() {
		echo '</table>' . PHP_EOL;
	}

	public function writeImageRow() {
		echo '    <tr class="imagerow"><td class="topleft"></td>';
		foreach ($this->weapons as $weapon) {
			echo '<td><img src="images/'.$weapon->name.'"></td>';
		}
		echo '</tr>' . PHP_EOL;
	}

	public function writeHeaderRow() {
		echo '    <tr class="headerrow"><th class="topleft"></th>';
		foreach ($this->weapons as $weapon) {
			echo '<th>' . $weapon->name . '</th>';
		}
		echo '</tr>' . PHP_EOL;
	}

	public function writeRow($title, $col = null) {
		if ($col === null) {
			$col = $title;
		}
		echo '    <tr><th>' . $title . '</th>';
		foreach ($this->weapons as $weapon) {
			echo '<td>' . $weapon->$col . '</td>';
		}
		echo '</tr>' . PHP_EOL;
	}

	public function dump() {
		echo '<pre>';
		var_dump($this->weapons);
		echo '</pre>';
	}

}
